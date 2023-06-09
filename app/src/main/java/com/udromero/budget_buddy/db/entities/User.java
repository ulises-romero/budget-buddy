package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mEmail;
    private String mPassword_hash;
    private String mFirstName;
    private String mLastName;
    private String mFirstTimeLogin; // 1 = new user, 0 = existing user (used to decide whether to take through initialization process)

    public User(String email, String password_hash, String firstName, String lastName, String firstTimeLogin) {
        mEmail = email;
        mPassword_hash = password_hash;
        mFirstName = firstName;
        mLastName = lastName;
        mFirstTimeLogin = firstTimeLogin;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getUserId() {
        return mUserId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword_hash() {
        return mPassword_hash;
    }

    public void setPassword_hash(String password_hash) {
        mPassword_hash = password_hash;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getFirstTimeLogin() {
        return mFirstTimeLogin;
    }

    public void setFirstTimeLogin(String firstTimeLogin) {
        mFirstTimeLogin = firstTimeLogin;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "mUserId=" + mUserId +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword_hash='" + mPassword_hash + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mFirstTimeLogin='" + mFirstTimeLogin + '\'' +
                '}';
    }
}
