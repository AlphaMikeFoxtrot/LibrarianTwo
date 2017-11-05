package com.example.anonymous.librarian;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

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

import static com.example.anonymous.librarian.ViewBookList.adapter;

public class ViewSubscriberList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView mSubscribersRecyclerView;
    private ProgressDialog progressDialog;
    public ArrayList<SubscribersListItem> subscribers;
    public SubscribersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subscriber_list);

        mSubscribersRecyclerView = findViewById(R.id.subscribers_list_recycler_view);
        mSubscribersRecyclerView.setHasFixedSize(true);
        mSubscribersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        progressDialog = new ProgressDialog(this);

        SearchView searchView = findViewById(R.id.action_search_subscribers_list);
        searchView.setOnQueryTextListener(this);

        GetSubscriberListAsyncTask getSubscriberListAsyncTask = new GetSubscriberListAsyncTask();
        getSubscriberListAsyncTask.execute();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<SubscribersListItem> newList = new ArrayList<>();
        for(SubscribersListItem subscribersListItem : subscribers){

            String bookName = subscribersListItem.getmSubName().toLowerCase();
            if(bookName.contains(newText)){
                newList.add(subscribersListItem);
            }

        }
        adapter.setFilter(newList);

        return true;

    }

    private class GetSubscriberListAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            progressDialog.setMax(100);
            progressDialog.setMessage("Patching data received from database.....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String GET_SUBSCRIBERS_LIST_URL = "https://stigmatic-searchlig.000webhostapp.com/get_subscribers_from_db.php";

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            String jsonResponse;

            try {

                URL url = new URL(GET_SUBSCRIBERS_LIST_URL);
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

                if(!(responseBuilder.toString().isEmpty())) {
                    jsonResponse = responseBuilder.toString();
                    return jsonResponse;
                } else {
                    return null;
                }

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
            progressDialog.dismiss();
            subscribers = readFromJson(s);
            adapter = new SubscribersListAdapter(ViewSubscriberList.this, subscribers);
            mSubscribersRecyclerView.setAdapter(adapter);
        }
    }

    private ArrayList<SubscribersListItem> readFromJson(String s) {

        ArrayList<SubscribersListItem> subscribers = new ArrayList<>();
        String subName, subId, subEmail, subPhone;

        try {

            JSONArray root = new JSONArray(s);

            for(int i = 0; i < root.length(); i++){

                JSONObject nthObject = root.getJSONObject(i);
                subName = nthObject.getString("subscriber_name");
                subId = nthObject.getString("subscriber_id");
                subPhone = nthObject.getString("subscriber_phone_number");
                subEmail = nthObject.getString("subscriber_email");

                SubscribersListItem subscribersListItem = new SubscribersListItem(subName, subId, subPhone, subEmail);

                subscribers.add(subscribersListItem);

            }

            return subscribers;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
