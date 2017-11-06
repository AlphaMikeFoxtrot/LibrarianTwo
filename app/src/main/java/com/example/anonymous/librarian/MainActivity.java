package com.example.anonymous.librarian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mMainActivityListView;
    private ArrayList<MainActivityListViewItem> mMainActivityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        mMainActivityListView = findViewById(R.id.main_activity_list_view);

        mMainActivityItems = new ArrayList<MainActivityListViewItem>();

        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.add_book_to_library, "add new book to library"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.add_new_subscriber, "add new subscriber"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.issue_book, "issue book to subscriber"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.book_returned, "book returned by subscriber"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.view_all_books, "view books in library"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.view_subscribers_list, "view subscribers list"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.issued_book_shelf, "view issued books list"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.delete, "Delete Book(s) from Database"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.delete, "Delete Subscriber(s) from Database"));

        MainActivityListViewAdapter adapter = new MainActivityListViewAdapter(getApplicationContext(), mMainActivityItems);
        mMainActivityListView.setAdapter(adapter);

        mMainActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){

                    case 0:
                        // add new book to library item clicked
                        addNewBookToLibraryItemClicked();
                        break;

                    case 1:
                        // add new subscriber item clicked
                        addNewSubscriberItemClicked();
                        break;

                    case 2:
                        // issue book to subscriber item clicked
                        issueBookToSubscriberItemClicked();
                        break;
//
//                    case 3:
//                        // return book item clicked
//                        bookReturnedItemClicked();
//                        break;
//
                    case 4:
                        // view books item clicked
                        viewBooksInLibraryItemClicked();
                        break;

                    case 5:
                        // view subscribers list item clicked
                        viewSubscribersListItemClicked();
                        break;

                    case 6:
                        // view issued books list item clicked
                        viewIssuedBooksListItemClicked();
                        break;

                    case 7:
                        // delete books item clicked
                        deleteBookItemClicked();
                        break;

                    case 8:
                        // delete subscribers item clicked
                        deleteSubscribersItemClicked();
                        break;

                }

            }
        });

    }

    private void deleteSubscribersItemClicked() {

    }

    private void deleteBookItemClicked() {

    }

    private void viewIssuedBooksListItemClicked() {

        if(isNetworkConnected()) {
            Intent toIssuedBooksList = new Intent(MainActivity.this, ViewIssuedBooksList.class);
            toIssuedBooksList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toIssuedBooksList);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            //MainActivity.this.finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setView(view);
            alertDialog.show();

        }

    }

    private void viewSubscribersListItemClicked() {

        if(isNetworkConnected()) {
            Intent toSubscribersList;
            toSubscribersList = new Intent(MainActivity.this, ViewSubscriberList.class);
            toSubscribersList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toSubscribersList);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            //MainActivity.this.finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setView(view);
            alertDialog.show();

        }

    }

    private void viewBooksInLibraryItemClicked() {

        if(isNetworkConnected()) {
            Intent toBooksList;
            toBooksList = new Intent(MainActivity.this, ViewBookList.class);
            toBooksList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toBooksList);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            //MainActivity.this.finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setView(view);
            alertDialog.show();

        }

    }

//    private void bookReturnedItemClicked() {
//
//        Intent toBookReturned;
//        toBookReturned.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(toBookReturned);

//    if(isNetworkConnected()) {
//        Intent toIssuedBooksList = new Intent(MainActivity.this, ViewIssuedBooksList.class);
//        toIssuedBooksList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(toIssuedBooksList);
//    } else {
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                MainActivity.this);
//
//        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);
//
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        dialog.dismiss();
//                        //MainActivity.this.finish();
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setView(view);
//        alertDialog.show();
//
//    }
//
//    }

    private void issueBookToSubscriberItemClicked() {

        if(isNetworkConnected()) {
            Intent toIssueBook;
            toIssueBook = new Intent(MainActivity.this, IssueBook.class);
            toIssueBook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toIssueBook);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            //MainActivity.this.finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setView(view);
            alertDialog.show();

        }

    }

    private void addNewSubscriberItemClicked() {

        if(isNetworkConnected()) {
            Intent toNewSubscriber;
            toNewSubscriber = new Intent(MainActivity.this, AddSubscriberToDatabase.class);
            toNewSubscriber.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toNewSubscriber);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            //MainActivity.this.finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setView(view);
            alertDialog.show();

        }

    }

    private void addNewBookToLibraryItemClicked() {

        if(isNetworkConnected()) {
            Intent toNewBook;
            toNewBook = new Intent(MainActivity.this, AddBookToDatabase.class);
            toNewBook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toNewBook);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_dialog,null);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.dismiss();
                            //MainActivity.this.finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setView(view);
            alertDialog.show();

        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
