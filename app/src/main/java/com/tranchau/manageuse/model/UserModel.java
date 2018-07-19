package com.tranchau.manageuse.model;

public class UserModel {
    private String mUserName;
    private String mID;

    public UserModel(String mUserName, String mID) {
        this.mUserName = mUserName;
        this.mID = mID;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }
}
