package com.example.anonymous.librarian;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;
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

public class ViewBookList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView mBooksListRecyclerView;
    public static BookRecyclerViewAdapter adapter;
    public static ArrayList<BookRecyclerViewItem> mBooks;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_book_list);

        mBooksListRecyclerView = findViewById(R.id.book_list_recycler_view);
        mBooksListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mBooksListRecyclerView.setLayoutManager(linearLayoutManager);

        GetBooksFromDbAsyncTask getBooksFromDbAsyncTask = new GetBooksFromDbAsyncTask();
        getBooksFromDbAsyncTask.execute();

        SearchView searchView = findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String newText = s.toLowerCase();
        ArrayList<BookRecyclerViewItem> newList = new ArrayList<>();
        for(BookRecyclerViewItem bookRecyclerViewItem : mBooks){

            String bookName = bookRecyclerViewItem.getmBookName().toLowerCase();
            if(bookName.contains(newText)){
                newList.add(bookRecyclerViewItem);
            }

        }
        adapter.setFilter(newList);

        return true;
    }

    private class GetBooksFromDbAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            progressDoalog = new ProgressDialog(ViewBookList.this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Patching data received from database...");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String GET_BOOKS_URL = "https://stigmatic-searchlig.000webhostapp.com/get_book_details_from_db.php";

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String jsonResponseFromServer;

            try {

                URL url = new URL(GET_BOOKS_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder responseBuilder = new StringBuilder();

                String line;

                while((line = bufferedReader.readLine()) != null){

                    responseBuilder.append(line);

                }

                return jsonResponseFromServer = responseBuilder.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
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
        protected void onPostExecute(String s) {
            if(!(s.isEmpty())){

                mBooks = readFromJSON(s);
                adapter = new BookRecyclerViewAdapter(mBooks, getApplicationContext());
                mBooksListRecyclerView.setAdapter(adapter);
                progressDoalog.dismiss();

            } else {

                progressDoalog.dismiss();
                Toast.makeText(ViewBookList.this, "No books to show!", Toast.LENGTH_SHORT).show();
                finish();

            }
        }
    }

    public ArrayList<BookRecyclerViewItem> readFromJSON(String json){

        ArrayList<BookRecyclerViewItem> books = new ArrayList<BookRecyclerViewItem>();

        String bookName, bookId, bookIsIssued, numberOfCopies;

        try {

            JSONArray root = new JSONArray(json);

            for(int i = 0; i < root.length(); i++){

                JSONObject nthObject = root.getJSONObject(i);
                bookName = nthObject.getString("book_name");
                bookId = nthObject.getString("book_id");
                bookIsIssued = nthObject.getString("is_issued");
                numberOfCopies = nthObject.getString("number_of_copies");

                BookRecyclerViewItem book = new BookRecyclerViewItem(bookName, bookId, bookIsIssued, numberOfCopies);
                books.add(book);

            }
            return books;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
