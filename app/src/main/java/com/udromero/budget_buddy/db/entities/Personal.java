package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.PERSONAL_TABLE)
public class Personal {
    @PrimaryKey(autoGenerate = true)
    private int mPersonalId;

    private int mUserId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mClothingPlanned;
    private String mClothingSpent;
    private int mClothingRecurring;

    private String mPhonePlanned;
    private String mPhoneSpent;
    private int mPhoneRecurring;

    private String mFunMoneyPlanned;
    private String mFunMoneySpent;
    private int mFunMoneyRecurring;

    private String mHairCosmeticsPlanned;
    private String mHairCosmeticsSpent;
    private int mHairCosmeticsRecurring;

    private String mOtherPersonalExpenses;

    public Personal(int userId, String totalPlanned, String totalRecurring, String totalSpent, String clothingPlanned, String clothingSpent, int clothingRecurring, String phonePlanned, String phoneSpent, int phoneRecurring, String funMoneyPlanned, String funMoneySpent, int funMoneyRecurring, String hairCosmeticsPlanned, String hairCosmeticsSpent, int hairCosmeticsRecurring, String otherPersonalExpenses) {
        mUserId = userId;
        mTotalPlanned = totalPlanned;
        mTotalRecurring = totalRecurring;
        mTotalSpent = totalSpent;
        mClothingPlanned = clothingPlanned;
        mClothingSpent = clothingSpent;
        mClothingRecurring = clothingRecurring;
        mPhonePlanned = phonePlanned;
        mPhoneSpent = phoneSpent;
        mPhoneRecurring = phoneRecurring;
        mFunMoneyPlanned = funMoneyPlanned;
        mFunMoneySpent = funMoneySpent;
        mFunMoneyRecurring = funMoneyRecurring;
        mHairCosmeticsPlanned = hairCosmeticsPlanned;
        mHairCosmeticsSpent = hairCosmeticsSpent;
        mHairCosmeticsRecurring = hairCosmeticsRecurring;
        mOtherPersonalExpenses = otherPersonalExpenses;
    }

    private String calculateTotalPlanned(){
        String result;

        double clothing = 0;
        double phone = 0;
        double funMoney = 0;
        double hairCosmetics = 0;

        if(!mClothingPlanned.isEmpty()){
            clothing = Double.parseDouble(mClothingPlanned);
        }

        if(!mPhonePlanned.isEmpty()){
            phone = Double.parseDouble(mPhonePlanned);
        }

        if(!mFunMoneyPlanned.isEmpty()){
            funMoney = Double.parseDouble(mFunMoneyPlanned);
        }

        if(!mHairCosmeticsPlanned.isEmpty()){
            hairCosmetics = Double.parseDouble(mHairCosmeticsPlanned);
        }

        double total = clothing + phone + funMoney + hairCosmetics;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double clothingAmount = 0;
        double phoneAmount = 0;
        double entertainmentAmount = 0;
        double hairCosmeticsAmount = 0;

        // For each sub-category check whether it's a recurring expense
        if(mClothingRecurring == 1){
            clothingAmount = Double.parseDouble(mClothingPlanned);
        }

        if(mPhoneRecurring == 1){
            phoneAmount = Double.parseDouble(mPhonePlanned);
        }

        if(mFunMoneyRecurring == 1){
            entertainmentAmount = Double.parseDouble(mFunMoneyPlanned);
        }

        if(mHairCosmeticsRecurring == 1){
            hairCosmeticsAmount = Double.parseDouble(mHairCosmeticsPlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mClothingRecurring == 0 && mPhoneRecurring == 0 &&
        mFunMoneyRecurring == 0 && mHairCosmeticsRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = clothingAmount + phoneAmount + entertainmentAmount + hairCosmeticsAmount;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getPersonalId() {
        return mPersonalId;
    }

    public void setPersonalId(int personalId) {
        mPersonalId = personalId;
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

    public String getClothingPlanned() {
        return mClothingPlanned;
    }

    public void setClothingPlanned(String clothingPlanned) {
        mClothingPlanned = clothingPlanned;
    }

    public String getClothingSpent() {
        return mClothingSpent;
    }

    public void setClothingSpent(String clothingSpent) {
        mClothingSpent = clothingSpent;
    }

    public int getClothingRecurring() {
        return mClothingRecurring;
    }

    public void setClothingRecurring(int clothingRecurring) {
        mClothingRecurring = clothingRecurring;
    }

    public String getPhonePlanned() {
        return mPhonePlanned;
    }

    public void setPhonePlanned(String phonePlanned) {
        mPhonePlanned = phonePlanned;
    }

    public String getPhoneSpent() {
        return mPhoneSpent;
    }

    public void setPhoneSpent(String phoneSpent) {
        mPhoneSpent = phoneSpent;
    }

    public int getPhoneRecurring() {
        return mPhoneRecurring;
    }

    public void setPhoneRecurring(int phoneRecurring) {
        mPhoneRecurring = phoneRecurring;
    }

    public String getFunMoneyPlanned() {
        return mFunMoneyPlanned;
    }

    public void setFunMoneyPlanned(String funMoneyPlanned) {
        mFunMoneyPlanned = funMoneyPlanned;
    }

    public String getFunMoneySpent() {
        return mFunMoneySpent;
    }

    public void setFunMoneySpent(String funMoneySpent) {
        mFunMoneySpent = funMoneySpent;
    }

    public int getFunMoneyRecurring() {
        return mFunMoneyRecurring;
    }

    public void setFunMoneyRecurring(int funMoneyRecurring) {
        mFunMoneyRecurring = funMoneyRecurring;
    }

    public String getHairCosmeticsPlanned() {
        return mHairCosmeticsPlanned;
    }

    public void setHairCosmeticsPlanned(String hairCosmeticsPlanned) {
        mHairCosmeticsPlanned = hairCosmeticsPlanned;
    }

    public String getHairCosmeticsSpent() {
        return mHairCosmeticsSpent;
    }

    public void setHairCosmeticsSpent(String hairCosmeticsSpent) {
        mHairCosmeticsSpent = hairCosmeticsSpent;
    }

    public int getHairCosmeticsRecurring() {
        return mHairCosmeticsRecurring;
    }

    public void setHairCosmeticsRecurring(int hairCosmeticsRecurring) {
        mHairCosmeticsRecurring = hairCosmeticsRecurring;
    }

    public String getOtherPersonalExpenses() {
        return mOtherPersonalExpenses;
    }

    public void setOtherPersonalExpenses(String otherPersonalExpenses) {
        mOtherPersonalExpenses = otherPersonalExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Personal{" +
                "mPersonalId=" + mPersonalId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mClothingPlanned='" + mClothingPlanned + '\'' +
                ", mClothingSpent='" + mClothingSpent + '\'' +
                ", mClothingRecurring=" + mClothingRecurring +
                ", mPhonePlanned='" + mPhonePlanned + '\'' +
                ", mPhoneSpent='" + mPhoneSpent + '\'' +
                ", mPhoneRecurring=" + mPhoneRecurring +
                ", mFunMoneyPlanned='" + mFunMoneyPlanned + '\'' +
                ", mFunMoneySpent='" + mFunMoneySpent + '\'' +
                ", mFunMoneyRecurring=" + mFunMoneyRecurring +
                ", mHairCosmeticsPlanned='" + mHairCosmeticsPlanned + '\'' +
                ", mHairCosmeticsSpent='" + mHairCosmeticsSpent + '\'' +
                ", mHairCosmeticsRecurring=" + mHairCosmeticsRecurring +
                ", mOtherPersonalExpenses='" + mOtherPersonalExpenses + '\'' +
                '}';
    }
}
