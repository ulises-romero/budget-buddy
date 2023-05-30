package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.HOUSING_TABLE)
public class Housing {
    @PrimaryKey(autoGenerate = true)
    private int mHousingId;

    // Non-null Attributes
    private String mTotal;
    private String mRecurring_total;

    // Other Attributes
    private String mMortgageRent;
    private int mMortgageRentRecurring;
    private String mUtilities;
    private int mUtilitiesRecurring;
    private String mOther;
    private String mOtherRecurring;

    public Housing(String total, String recurring_total, String mortgageRent, int mortgageRentRecurring, String utilities, int utilitiesRecurring, String other, String otherRecurring) {
        mTotal = total;
        mRecurring_total = recurring_total;
        mMortgageRent = mortgageRent;
        mMortgageRentRecurring = mortgageRentRecurring;
        mUtilities = utilities;
        mUtilitiesRecurring = utilitiesRecurring;
        mOther = other;
        mOtherRecurring = otherRecurring;
    }

    public void setHousingId(int housingId) {
        mHousingId = housingId;
    }

    public int getHousingId() {
        return mHousingId;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getRecurring_total() {
        return mRecurring_total;
    }

    public void setRecurring_total(String recurring_total) {
        mRecurring_total = recurring_total;
    }

    public String getMortgageRent() {
        return mMortgageRent;
    }

    public void setMortgageRent(String mortgageRent) {
        mMortgageRent = mortgageRent;
    }

    public int getMortgageRentRecurring() {
        return mMortgageRentRecurring;
    }

    public void setMortgageRentRecurring(int mortgageRentRecurring) {
        mMortgageRentRecurring = mortgageRentRecurring;
    }

    public String getUtilities() {
        return mUtilities;
    }

    public void setUtilities(String utilities) {
        mUtilities = utilities;
    }

    public int getUtilitiesRecurring() {
        return mUtilitiesRecurring;
    }

    public void setUtilitiesRecurring(int utilitiesRecurring) {
        mUtilitiesRecurring = utilitiesRecurring;
    }

    public String getOther() {
        return mOther;
    }

    public void setOther(String other) {
        mOther = other;
    }

    public String getOtherRecurring() {
        return mOtherRecurring;
    }

    public void setOtherRecurring(String otherRecurring) {
        mOtherRecurring = otherRecurring;
    }

    @NonNull
    @Override
    public String toString() {
        return "Housing{" +
                "mHousingId=" + mHousingId +
                ", mTotal='" + mTotal + '\'' +
                ", mRecurring_total='" + mRecurring_total + '\'' +
                ", mMortgageRent='" + mMortgageRent + '\'' +
                ", mMortgageRentRecurring=" + mMortgageRentRecurring +
                ", mUtilities='" + mUtilities + '\'' +
                ", mUtilitiesRecurring=" + mUtilitiesRecurring +
                ", mOther='" + mOther + '\'' +
                ", mOtherRecurring='" + mOtherRecurring + '\'' +
                '}';
    }
}
