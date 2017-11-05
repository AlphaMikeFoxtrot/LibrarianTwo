package com.example.anonymous.librarian;

/**
 * Created by ANONYMOUS on 05-Nov-17.
 */

public class IssuedBookListItem {

    private String mIssuedBookName, mIssuedBookId, mIssuedBookToName, mIssuedBookOnDate;

    public IssuedBookListItem(String mIssuedBookName, String mIssuedBookId, String mIssuedBookToName, String mIssuedBookOnDate) {
        this.mIssuedBookName = mIssuedBookName;
        this.mIssuedBookId = mIssuedBookId;
        this.mIssuedBookToName = mIssuedBookToName;
        this.mIssuedBookOnDate = mIssuedBookOnDate;
    }

    public String getmIssuedBookName() {
        return mIssuedBookName;
    }

    public String getmIssuedBookId() {
        return mIssuedBookId;
    }

    public String getmIssuedBookToName() {
        return mIssuedBookToName;
    }

    public String getmIssuedBookOnDate() {
        return mIssuedBookOnDate;
    }
}
