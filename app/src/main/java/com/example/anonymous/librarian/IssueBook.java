package com.example.anonymous.librarian;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.os.AsyncTask;
import android.os.HandlerThread;
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
import java.nio.Buffer;
import java.util.Calendar;

public class IssueBook extends AppCompatActivity {

    private EditText mIssuedBookId, mIssuedBookToId;
    private Button mSubmit, mReset, mCancel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);

        mIssuedBookId = findViewById(R.id.issued_book_id_edit_text);
        mIssuedBookToId = findViewById(R.id.issued_book_to_id_edit_text);

        mSubmit = findViewById(R.id.issue_book_submit_button);
        mReset = findViewById(R.id.issue_book_reset_button);
        mCancel = findViewById(R.id.issue_book_cancel_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(mIssuedBookToId.getText().toString().isEmpty()) && !(mIssuedBookId.getText().toString().isEmpty())){

                    String issuedBookId = mIssuedBookId.getText().toString();
                    String issuedBookToId = mIssuedBookToId.getText().toString();

                    IssueBookAsyncTask issueBookAsyncTask = new IssueBookAsyncTask();
                    issueBookAsyncTask.execute(issuedBookId, issuedBookToId);

                } else {

                    Toast.makeText(getApplicationContext(), "Please make sure all the empty fields are filled", Toast.LENGTH_LONG).show();

                }

            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIssuedBookId.setText("");
                mIssuedBookToId.setText("");
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public class IssueBookAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(IssueBook.this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Executing required Operations");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String issuedBookId = strings[0];
            String issuedBookToId = strings[1];

            final String ISSUE_BOOK_URL = "https://stigmatic-searchlig.000webhostapp.com/issue_book.php";

            HttpURLConnection httpURLConnection = null;
            BufferedWriter bufferedWriter = null;

            try {

                URL url = new URL(ISSUE_BOOK_URL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                Calendar c = Calendar.getInstance();

                String bookIssuedOnDate = c.get(Calendar.YEAR) + "-"
                        + c.get(Calendar.MONTH)
                        + "-" + c.get(Calendar.DAY_OF_MONTH)
                        + " at " + c.get(Calendar.HOUR_OF_DAY)
                        + ":" + c.get(Calendar.MINUTE);

                String dataToWrite = URLEncoder.encode("issuedBookId", "UTF-8") +"="+ URLEncoder.encode(issuedBookId, "UTF-8") +"&"+
                        URLEncoder.encode("issuedBookToId", "UTF-8") +"="+ URLEncoder.encode(issuedBookToId, "UTF-8") +"&"+
                URLEncoder.encode("issuedBookOnDate", "UTF-8") +"="+ URLEncoder.encode(bookIssuedOnDate, "UTF-8");

                bufferedWriter.write(dataToWrite);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder responseFromServer = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){

                    responseFromServer.append(line);

                }
                String response = responseFromServer.toString();
                return response;

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
            if(s.contains("success")){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Issued Book entry has been successfully registered", Toast.LENGTH_LONG).show();
                finish();
            } else {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sorry! An error occured\n" + s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
