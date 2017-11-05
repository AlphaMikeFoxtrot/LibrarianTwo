package com.example.anonymous.librarian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mMainActivityListView;
    private ArrayList<MainActivityListViewItem> mMainActivityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityListView = findViewById(R.id.main_activity_list_view);

        mMainActivityItems = new ArrayList<MainActivityListViewItem>();

        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.add_book_to_library, "add new book to library"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.add_new_subscriber, "add new subscriber"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.issue_book, "issue book to subscriber"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.book_returned, "book returned by subscriber"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.view_all_books, "view books in library"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.view_subscribers_list, "view subscribers list"));
        mMainActivityItems.add(new MainActivityListViewItem(R.drawable.issued_book_shelf, "view issued books list"));

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
//
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
//
                    case 5:
                        // view subscribers list item clicked
                        viewSubscribersListItemClicked();
                        break;

//                    case 6:
//                        // view issued books list item clicked
//                        viewIssuedBooksListItemClicked();
//                        break;

                }

            }
        });

    }

//    private void viewIssuedBooksListItemClicked() {
//
//    }

    private void viewSubscribersListItemClicked() {

        Intent toSubscribersList;
        toSubscribersList = new Intent(MainActivity.this, ViewSubscriberList.class);
        toSubscribersList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toSubscribersList);

    }
//
    private void viewBooksInLibraryItemClicked() {

        Intent toBooksList;
        toBooksList = new Intent(MainActivity.this, ViewBookList.class);
        toBooksList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toBooksList);

    }
//
//    private void bookReturnedItemClicked() {
//
//        Intent toBookReturned;
//        toBookReturned.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(toBookReturned);
//
//    }
//
    private void issueBookToSubscriberItemClicked() {

        Intent toIssueBook;
        toIssueBook = new Intent(MainActivity.this, IssueBook.class);
        toIssueBook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toIssueBook);

    }
//
    private void addNewSubscriberItemClicked() {

        Intent toNewSubscriber;
        toNewSubscriber = new Intent(MainActivity.this, AddSubscriberToDatabase.class);
        toNewSubscriber.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toNewSubscriber);

    }

    private void addNewBookToLibraryItemClicked() {

        Intent toNewBook;
        toNewBook = new Intent(MainActivity.this, AddBookToDatabase.class);
        toNewBook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toNewBook);

    }

}
