package com.example.anonymous.librarian;

/**
 * Created by ANONYMOUS on 04-Nov-17.
 */

public class MainActivityListViewItem {

    private int mImageResourceId;
    private String mButtonName;

    public MainActivityListViewItem(int mImageResourceId, String mButtonName) {
        this.mImageResourceId = mImageResourceId;
        this.mButtonName = mButtonName;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public String getmButtonName() {
        return mButtonName;
    }
}
