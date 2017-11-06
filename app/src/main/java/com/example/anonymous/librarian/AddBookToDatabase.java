package com.example.anonymous.librarian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AddBookToDatabase extends AppCompatActivity {

    private EditText mNewBookId, mNewBookName, mNewBookNumberOfCopies;;
    private Button mSubmit, mCancel, mReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_database);

        mNewBookId = findViewById(R.id.new_book_id_edit_text);
        mNewBookName = findViewById(R.id.new_book_name_edit_text);
        mNewBookNumberOfCopies = findViewById(R.id.new_book_number_of_copies_edit_text);

        mSubmit = findViewById(R.id.new_book_submit_button);
        mCancel = findViewById(R.id.new_book_cancel_button);
        mReset = findViewById(R.id.new_book_reset_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mNewBookName.getText().toString().isEmpty() && mNewBookId.getText().toString().isEmpty() && mNewBookNumberOfCopies.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please make sure that empty fields are filled!", Toast.LENGTH_SHORT).show();

                } else {

                    InsertBookAsyncTask insertBookAsyncTask = new InsertBookAsyncTask();
                    insertBookAsyncTask.execute(mNewBookId.getText().toString(), mNewBookName.getText().toString(), mNewBookNumberOfCopies.getText().toString());

                }

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNewBookId.setText("");
                mNewBookName.setText("");
                mNewBookNumberOfCopies.setText("");

            }
        });

    }

    private class InsertBookAsyncTask extends AsyncTask<String, Void, String>{

        ProgressDialog progressDoalog;

        @Override
        protected void onPreExecute() {
            progressDoalog = new ProgressDialog(AddBookToDatabase.this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Uploading schematics to database...");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String newBookId = strings[0];
            String newBookName = strings[1];
            String newBookNumberOfCopies = strings[2];

            final String INSERT_BOOK_URL = "https://stigmatic-searchlig.000webhostapp.com/add_book_to_db.php";

            HttpURLConnection httpURLConnection = null;
            BufferedWriter bufferedWriter = null;

            try {

                URL url = new URL(INSERT_BOOK_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                String dataToWrite = URLEncoder.encode("newBookId", "UTF-8") +"="+ URLEncoder.encode(newBookId, "UTF-8") +"&"+
                        URLEncoder.encode("newBookName", "UTF-8") +"="+ URLEncoder.encode(newBookName, "UTF-8") +"&"+
                        URLEncoder.encode("newBookNumberOfCopies", "UTF-8") +"="+ URLEncoder.encode(newBookNumberOfCopies, "UTF-8");

                bufferedWriter.write(dataToWrite);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {

                    response.append(line);

                }

                return response.toString();

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
                if(bufferedWriter != null){
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if(s.toLowerCase().contains("success")){// == "success"){

                Toast.makeText(getApplicationContext(), "New book was successfully added to the database", Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
                finish();

            } else {

                Toast.makeText(getApplicationContext(), "Sorry! An Error occured\n" + s, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();

            }
        }
    }
}
