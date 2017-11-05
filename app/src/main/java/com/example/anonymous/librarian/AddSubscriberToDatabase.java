package com.example.anonymous.librarian;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSubscriberToDatabase extends AppCompatActivity {

    private EditText mNewSubscriberName, mNewSubscriberId, mNewSubscriberPhone, mNewSubscriberEmail;
    private Button mSubmit, mReset, mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscriber_to_database);

        mNewSubscriberName = findViewById(R.id.new_subscriber_name_edit_text);
        mNewSubscriberId = findViewById(R.id.new_subscriber_id_edit_text);
        mNewSubscriberPhone = findViewById(R.id.new_subscriber_phone_edit_text);
        mNewSubscriberEmail = findViewById(R.id.new_subscriber_email_edit_text);

        mSubmit = findViewById(R.id.new_subscriber_submit_button);
        mCancel = findViewById(R.id.new_subscriber_cancel_button);
        mReset = findViewById(R.id.new_subscriber_reset_button);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
