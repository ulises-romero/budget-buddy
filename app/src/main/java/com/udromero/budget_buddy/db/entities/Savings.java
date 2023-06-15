package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.SAVINGS_TABLE)
public class Savings {
    @PrimaryKey(autoGenerate = true)
    private int mSavingsId;

    private int mUserId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mEmergencyFundPlanned;
    private String mEmergencyFundSpent;
    private int mEmergencyFundRecurring;

    private String mOtherSavingsExpenses;

    public Savings(int userId, String totalPlanned, String totalRecurring, String totalSpent, String emergencyFundPlanned, String emergencyFundSpent, int emergencyFundRecurring, String otherSavingsExpenses) {
        mUserId = userId;
        mTotalPlanned = totalPlanned;
        mTotalRecurring = totalRecurring;
        mTotalSpent = totalSpent;
        mEmergencyFundPlanned = emergencyFundPlanned;
        mEmergencyFundSpent = emergencyFundSpent;
        mEmergencyFundRecurring = emergencyFundRecurring;
        mOtherSavingsExpenses = otherSavingsExpenses;
    }

    private String calculateTotalPlanned(){
        String result;

        double emergencyFund= 0;

        if(!mEmergencyFundPlanned.isEmpty()){
            emergencyFund = Double.parseDouble(mEmergencyFundPlanned);
        }

        result = String.valueOf(emergencyFund);

        return result;
    }

    public String calculateTotalReccuring(){
        String result;

        // Amount's for each sub-category
        double emergencyFundAmount = 0;

        // For each sub-category check whether it's a reccuring expense
        if(mEmergencyFundRecurring == 1){
            emergencyFundAmount = Double.parseDouble(mEmergencyFundPlanned);
        }

        // Check for no reccuring fields being equal to 1, if so just set result = "0"
        if(mEmergencyFundRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = emergencyFundAmount;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getSavingsId() {
        return mSavingsId;
    }

    public void setSavingsId(int savingsId) {
        mSavingsId = savingsId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getTotalPlanned() {
        return mTotalPlanned;
    }

    public void setTotalPlanned(String totalPlanned) {
        mTotalPlanned = totalPlanned;
    }

    public String getTotalRecurring() {
        return mTotalRecurring;
    }

    public void setTotalRecurring(String totalRecurring) {
        mTotalRecurring = totalRecurring;
    }

    public String getTotalSpent() {
        return mTotalSpent;
    }

    public void setTotalSpent(String totalSpent) {
        mTotalSpent = totalSpent;
    }

    public String getEmergencyFundPlanned() {
        return mEmergencyFundPlanned;
    }

    public void setEmergencyFundPlanned(String emergencyFundPlanned) {
        mEmergencyFundPlanned = emergencyFundPlanned;
    }

    public String getEmergencyFundSpent() {
        return mEmergencyFundSpent;
    }

    public void setEmergencyFundSpent(String emergencyFundSpent) {
        mEmergencyFundSpent = emergencyFundSpent;
    }

    public int getEmergencyFundRecurring() {
        return mEmergencyFundRecurring;
    }

    public void setEmergencyFundRecurring(int emergencyFundRecurring) {
        mEmergencyFundRecurring = emergencyFundRecurring;
    }

    public String getOtherSavingsExpenses() {
        return mOtherSavingsExpenses;
    }

    public void setOtherSavingsExpenses(String otherSavingsExpenses) {
        mOtherSavingsExpenses = otherSavingsExpenses;
    }

    @NonNull

    @Override
    public String toString() {
        return "Savings{" +
                "mSavingsId=" + mSavingsId +
                ", mUserId=" + mUserId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mEmergencyFundPlanned='" + mEmergencyFundPlanned + '\'' +
                ", mEmergencyFundSpent='" + mEmergencyFundSpent + '\'' +
                ", mEmergencyFundRecurring=" + mEmergencyFundRecurring +
                ", mOtherSavingsExpenses='" + mOtherSavingsExpenses + '\'' +
                '}';
    }
}
