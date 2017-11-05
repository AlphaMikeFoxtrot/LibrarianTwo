package com.example.anonymous.librarian;

/**
 * Created by ANONYMOUS on 04-Nov-17.
 */

public class SubscribersListItem {

    private String mSubName, mSubId, mSubPhone, mSubEmail;

    public SubscribersListItem(String mSubName, String mSubId, String mSubPhone, String mSubEmail) {
        this.mSubName = mSubName;
        this.mSubId = mSubId;
        this.mSubPhone = mSubPhone;
        this.mSubEmail = mSubEmail;
    }

    public String getmSubName() {
        return mSubName;
    }

    public String getmSubId() {
        return mSubId;
    }

    public String getmSubPhone() {
        return mSubPhone;
    }

    public String getmSubEmail() {
        return mSubEmail;
    }
}
