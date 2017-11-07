package com.example.anonymous.librarian;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewIssuedBooksList extends AppCompatActivity{

    private RecyclerView mIssuedBookRecyclerView;
    public IssuedBookRecyclerViewAdapter adapter;
    public static ArrayList<IssuedBookListItem> issuedBooks;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_books_list);

        mIssuedBookRecyclerView = findViewById(R.id.issued_books_list_recycler_view);
        mIssuedBookRecyclerView.setHasFixedSize(true);
        mIssuedBookRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        SearchView searchView = findViewById(R.id.action_search_issued_books_list);
//        searchView.setOnQueryTextListener(this);

        GetIssuedBooksAsyncTask getIssuedBooksAsyncTask = new GetIssuedBooksAsyncTask();
        getIssuedBooksAsyncTask.execute();
    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//
//        ArrayList<IssuedBookListItem> newList = new ArrayList<>();
//        for(IssuedBookListItem issuedBookListItem : issuedBooks){
//
//            String bookName = issuedBookListItem.getmIssuedBookName().toLowerCase();
//            if(bookName.contains(newText.toLowerCase())){
//                newList.add(issuedBookListItem);
//            }
//
//        }
//        adapter.setIssuedBookFilter(newList);
//
//        return true;
//    }

    public class GetIssuedBooksAsyncTask extends AsyncTask<String, Void, ArrayList<IssuedBookListItem>> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ViewIssuedBooksList.this);
            progressDialog.setMessage("Fetching data from server");
            progressDialog.show();
        }

        @Override
        protected ArrayList<IssuedBookListItem> doInBackground(String... strings) {
            String issuedBookName, issuedBookId, issuedBookOnDate, issuedBookToName;

            ArrayList<IssuedBookListItem> books = new ArrayList<>();

            final String ISSUED_BOOKS_URL = "https://stigmatic-searchlig.000webhostapp.com/get_issued_book_details.php";

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {

                URL bookListUrl = new URL(ISSUED_BOOKS_URL);
                httpURLConnection = (HttpURLConnection) bookListUrl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                StringBuilder jsonResponse = new StringBuilder();

                while((line = bufferedReader.readLine()) != null){

                    jsonResponse.append(line);

                }

                JSONArray root = new JSONArray(jsonResponse.toString());
                for(int i = 0; i< root.length(); i++){

                    JSONObject nthObject = root.getJSONObject(i);
                    issuedBookId = nthObject.getString("book_id");
                    issuedBookOnDate = nthObject.getString("issued_on_date");
                    issuedBookToName = nthObject.getString("issued_to_name");
                    issuedBookName = nthObject.getString("issued_book_name");

                    IssuedBookListItem issuedBookListItem = new IssuedBookListItem(issuedBookName, issuedBookId, issuedBookToName, issuedBookOnDate);

                    books.add(issuedBookListItem);

                }

                return books;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } finally {
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
                if(bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<IssuedBookListItem> issuedBookListItems) {
            if(issuedBookListItems.size() > 0){

                progressDialog.dismiss();
                issuedBooks = issuedBookListItems;
                adapter = new IssuedBookRecyclerViewAdapter(ViewIssuedBooksList.this, issuedBooks);
                mIssuedBookRecyclerView.setAdapter(adapter);

            } else {

                Toast.makeText(ViewIssuedBooksList.this, "Sorry! An Error occurred while fetching data", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView mSearchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<IssuedBookListItem> newList = new ArrayList<>();
                for (IssuedBookListItem issuedBookListItem : issuedBooks) {

                    String bookName = issuedBookListItem.getmIssuedBookName().toLowerCase();
                    if (bookName.contains(newText.toLowerCase())) {
                        newList.add(issuedBookListItem);
                    }

                }
                adapter.setIssuedBookFilter(newList);

                return true;
            }
        });

        return true;
    }
}
