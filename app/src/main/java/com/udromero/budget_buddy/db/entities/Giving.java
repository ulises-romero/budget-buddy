package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.GIVING_TABLE)
public class Giving {
    @PrimaryKey(autoGenerate = true)
    private int mGivingId;

    private int mUserId;

    // Non-null Attributes
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Other Attributes
    private String mChurchPlanned; // amount planned to be spent
    private String mChurchSpent; // total spent
    private int mChurchRecurring; // set to 1 if reoccurring else set 0 if not a recurring payment

    private String mCharityPlanned;
    private String mCharitySpent;
    private int mCharityRecurring;

    private String mOtherGivingExpenses; // split on "," then split each string on "-". EX: "donations-200.00,other-300.00-[r]"


    public Giving(int userId, String totalPlanned, String totalRecurring, String totalSpent, String churchPlanned, String churchSpent, int churchRecurring, String charityPlanned, String charitySpent,
                  int charityRecurring, String otherGivingExpenses) {
        mUserId = userId;
        mChurchPlanned = churchPlanned;
        mChurchRecurring = churchRecurring;
        mCharityPlanned = charityPlanned;
        mCharityRecurring = charityRecurring;
        mOtherGivingExpenses = otherGivingExpenses;

        // Populate remaining member variables accordingly
        mTotalPlanned = totalPlanned;
        mTotalRecurring = totalRecurring;

        // Set all spent values to "0"
        mCharitySpent = "0";
        mChurchSpent = "0";
        mTotalSpent = "0";
    }

//    public String calculateTotalPlanned(){
//        String result;
//
//        double church = 0;
//        double charity = 0;
//
//        if(!mChurchPlanned.isEmpty()){
//            church = Double.parseDouble(mChurchPlanned);
//        }
//
//        if(!mCharityPlanned.isEmpty()){
//            charity = Double.parseDouble(mCharityPlanned);
//        }
//
//        double total = church + charity;
//        result = String.valueOf(total);
//
//        return result;
//    }
//
//    public String calculateTotalRecurring(){
//        String result;
//
//        // Amount's for each sub-category
//        double charityAmount = 0;
//        double churchAmount = 0;
//
//        // For each sub-category check whether it's a reccuring expense
//        if(mCharityRecurring == 1){
//            charityAmount = Double.parseDouble(mCharityPlanned);
//        }
//
//        if(mChurchRecurring == 1){
//            churchAmount = Double.parseDouble(mChurchPlanned);
//        }
//
//        // Check for no reccuring fields being equal to 1, if so just set result = "0"
//        if(mChurchRecurring == 0 && mCharityRecurring == 0){
//            result = "0";
//        } else {
//            // add up all amounts into the total
//            double total = charityAmount + churchAmount;
//
//            // set result string to string value of total
//            result = String.valueOf(total);
//        }
//
//        return result;
//    }

    public int getGivingId() {
        return mGivingId;
    }

    public void setGivingId(int givingId) {
        mGivingId = givingId;
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

    public String getChurchPlanned() {
        return mChurchPlanned;
    }

    public void setChurchPlanned(String churchPlanned) {
        mChurchPlanned = churchPlanned;
    }

    public String getChurchSpent() {
        return mChurchSpent;
    }

    public void setChurchSpent(String churchSpent) {
        mChurchSpent = churchSpent;
    }

    public int getChurchRecurring() {
        return mChurchRecurring;
    }

    public void setChurchRecurring(int churchRecurring) {
        mChurchRecurring = churchRecurring;
    }

    public String getCharityPlanned() {
        return mCharityPlanned;
    }

    public void setCharityPlanned(String charityPlanned) {
        mCharityPlanned = charityPlanned;
    }

    public String getCharitySpent() {
        return mCharitySpent;
    }

    public void setCharitySpent(String charitySpent) {
        mCharitySpent = charitySpent;
    }

    public int getCharityRecurring() {
        return mCharityRecurring;
    }

    public void setCharityRecurring(int charityRecurring) {
        mCharityRecurring = charityRecurring;
    }

    public String getOtherGivingExpenses() {
        return mOtherGivingExpenses;
    }

    public void setOtherGivingExpenses(String otherGivingExpenses) {
        mOtherGivingExpenses = otherGivingExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Giving{" +
                "mGivingId=" + mGivingId +
                ", mUserId=" + mUserId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mChurchPlanned='" + mChurchPlanned + '\'' +
                ", mChurchSpent='" + mChurchSpent + '\'' +
                ", mChurchRecurring=" + mChurchRecurring +
                ", mCharityPlanned='" + mCharityPlanned + '\'' +
                ", mCharitySpent='" + mCharitySpent + '\'' +
                ", mCharityRecurring=" + mCharityRecurring +
                ", mOtherGivingExpenses='" + mOtherGivingExpenses + '\'' +
                '}';
    }
}
