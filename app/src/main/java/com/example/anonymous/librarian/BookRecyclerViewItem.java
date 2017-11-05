package com.example.anonymous.librarian;

/**
 * Created by ANONYMOUS on 04-Nov-17.
 */

public class BookRecyclerViewItem {

    private String mBookName, mBookId, mIsBookIssued, mBookNumberOfCopies;

    public BookRecyclerViewItem(String mBookName, String mBookId, String mIsBookIssued, String mBookNumberOfCopies) {
        this.mBookName = mBookName;
        this.mBookId = mBookId;
        this.mIsBookIssued = mIsBookIssued;
        this.mBookNumberOfCopies = mBookNumberOfCopies;
    }

    public String getmBookName() {
        return mBookName;
    }

    public String getmBookId() {
        return mBookId;
    }

    public String getmIsBookIssued() {
        return mIsBookIssued;
    }

    public String getmBookNumberOfCopies() {
        return mBookNumberOfCopies;
    }
}
