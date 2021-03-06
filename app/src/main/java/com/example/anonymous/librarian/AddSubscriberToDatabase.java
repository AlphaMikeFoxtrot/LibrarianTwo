package com.example.anonymous.librarian;

import android.app.ProgressDialog;
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

public class AddSubscriberToDatabase extends AppCompatActivity {

    private EditText mNewSubscriberName, mNewSubscriberId, mNewSubscriberPhone, mNewSubscriberEmail;
    private Button mSubmit, mReset, mCancel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscriber_to_database);

        mNewSubscriberName = findViewById(R.id.new_subscriber_name_edit_text);
        mNewSubscriberId = findViewById(R.id.new_subscriber_id_edit_text);
        mNewSubscriberPhone = findViewById(R.id.new_subscriber_phone_edit_text);
        mNewSubscriberEmail = findViewById(R.id.new_subscriber_email_edit_text);

        progressDialog = new ProgressDialog(this);

        mSubmit = findViewById(R.id.new_subscriber_submit_button);
        mCancel = findViewById(R.id.new_subscriber_cancel_button);
        mReset = findViewById(R.id.new_subscriber_reset_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mNewSubscriberEmail.getText().toString().isEmpty() && mNewSubscriberPhone.getText().toString().isEmpty() &&
                        mNewSubscriberId.getText().toString().isEmpty() && mNewSubscriberName.getText().toString().isEmpty()){

                    // Toast.makeText(AddSubscriberToDatabase.this, "Please make sure that empty fields are filled", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "please make sure there are no empty fields", Toast.LENGTH_LONG).show();

                } else {
                    InsertNewSubscriberAsyncTask insertNewSubscriberAsyncTask = new InsertNewSubscriberAsyncTask();
                    insertNewSubscriberAsyncTask.execute(mNewSubscriberName.getText().toString(),
                            mNewSubscriberId.getText().toString(),
                            mNewSubscriberPhone.getText().toString(),
                            mNewSubscriberEmail.getText().toString());
                }

            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewSubscriberEmail.setText("");
                mNewSubscriberId.setText("");
                mNewSubscriberName.setText("");
                mNewSubscriberPhone.setText("");
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class InsertNewSubscriberAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(AddSubscriberToDatabase.this);
            progressDialog.setMessage("Uploading schematics to database...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String newSubName = strings[0];
            String newSubId = strings[1];
            String newSubPhone = strings[2];
            String newSubEmail = strings[3];

            final String INSERT_NEW_SUBSCRIBER_URL = "https://stigmatic-searchlig.000webhostapp.com/add_subscriber_to_db.php";

            HttpURLConnection httpURLConnection = null;
            BufferedWriter bufferedWriter = null;

            try {

                URL url = new URL(INSERT_NEW_SUBSCRIBER_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

                bufferedWriter = new BufferedWriter(outputStreamWriter);

                String dataToWrite = URLEncoder.encode("newSubName", "UTF-8") +"="+ URLEncoder.encode(newSubName, "UTF-8") +"&"+
                        URLEncoder.encode("newSubId", "UTF-8") +"="+ URLEncoder.encode(newSubId, "UTF-8") +"&"+
                        URLEncoder.encode("newSubPhone", "UTF-8") +"="+ URLEncoder.encode(newSubPhone, "UTF-8") +"&"+
                        URLEncoder.encode("newSubEmail", "UTF-8") +"="+ URLEncoder.encode(newSubEmail, "UTF-8");

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

            if (s.toLowerCase().contains("success")) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "New subscriber has been successfully registered", Toast.LENGTH_LONG).show();
                finish();

            } else {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry something went wrong", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
