package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.HOUSING_TABLE)
public class Housing {
    @PrimaryKey(autoGenerate = true)
    private int mHousingId;

    private int mUserId;

    // Non-null Attributes
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Other Attributes
    private String mMortgageRentPlanned;
    private String mMortgageRentSpent;
    private int mMortgageRentRecurring;

    private String mWaterPlanned;
    private String mWaterSpent;
    private int mWaterRecurring;

    private String mNaturalGasPlanned;
    private String mNaturalGasSpent;
    private int mNaturalGasRecurring;

    private String mElectricityPlanned;
    private String mElectricitySpent;
    private int mElectricityRecurring;

    private String mCableInternetPlanned;
    private String mCableInternetSpent;
    private int mCableInternetRecurring;

    private String mOtherHousingExpenses;

    public Housing(int userId, String totalPlanned, String totalRecurring, String totalSpent, String mortgageRentPlanned, String mortgageRentSpent, int mortgageRentRecurring, String waterPlanned, String waterSpent, int waterRecurring, String naturalGasPlanned, String naturalGasSpent, int naturalGasRecurring, String electricityPlanned, String electricitySpent, int electricityRecurring, String cableInternetPlanned, String cableInternetSpent, int cableInternetRecurring, String otherHousingExpenses) {
        mUserId = userId;
        mTotalPlanned = totalPlanned;
        mTotalRecurring = totalRecurring;
        mTotalSpent = totalSpent;
        mMortgageRentPlanned = mortgageRentPlanned;
        mMortgageRentSpent = mortgageRentSpent;
        mMortgageRentRecurring = mortgageRentRecurring;
        mWaterPlanned = waterPlanned;
        mWaterSpent = waterSpent;
        mWaterRecurring = waterRecurring;
        mNaturalGasPlanned = naturalGasPlanned;
        mNaturalGasSpent = naturalGasSpent;
        mNaturalGasRecurring = naturalGasRecurring;
        mElectricityPlanned = electricityPlanned;
        mElectricitySpent = electricitySpent;
        mElectricityRecurring = electricityRecurring;
        mCableInternetPlanned = cableInternetPlanned;
        mCableInternetSpent = cableInternetSpent;
        mCableInternetRecurring = cableInternetRecurring;
        mOtherHousingExpenses = otherHousingExpenses;
    }

    private String calculateTotalPlanned(){
        String result;

        double mortgageRent = 0;
        double water = 0;
        double naturalGas = 0;
        double electricity = 0;
        double cableInternent = 0;

        if(!mMortgageRentPlanned.isEmpty()){
            mortgageRent = Double.parseDouble(mMortgageRentPlanned);
        }

        if(!mWaterPlanned.isEmpty()){
            water = Double.parseDouble(mWaterPlanned);
        }

        if(!mNaturalGasPlanned.isEmpty()){
            naturalGas = Double.parseDouble(mNaturalGasPlanned);
        }

        if(!mElectricityPlanned.isEmpty()){
            electricity = Double.parseDouble(mElectricityPlanned);
        }

        if(!mCableInternetPlanned.isEmpty()){
            cableInternent = Double.parseDouble(mCableInternetPlanned);
        }

        double total = mortgageRent + water + naturalGas + electricity + cableInternent;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double mortgageRentAmount = 0;
        double waterAmount = 0;
        double naturalGasAmount = 0;
        double electricityAmount = 0;
        double cableInternetAmount = 0;

        // For each sub-category check whether it's a reccuring expense
        if(mMortgageRentRecurring == 1){
            mortgageRentAmount = Double.parseDouble(mMortgageRentPlanned);
        }

        if(mWaterRecurring == 1){
            waterAmount = Double.parseDouble(mWaterPlanned);
        }

        if(mNaturalGasRecurring == 1){
            naturalGasAmount = Double.parseDouble(mWaterPlanned);
        }

        if(mElectricityRecurring == 1){
            electricityAmount = Double.parseDouble(mElectricityPlanned);
        }

        if(mCableInternetRecurring == 1){
            cableInternetAmount = Double.parseDouble(mCableInternetPlanned);
        }

        // Check for no reccuring fields being equal to 1, if so just set result = "0"
        if(mMortgageRentRecurring == 0 && mWaterRecurring == 0 && mNaturalGasRecurring == 0 &&
        mElectricityRecurring == 0 && mCableInternetRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = mortgageRentAmount + waterAmount + naturalGasAmount + electricityAmount +
                    cableInternetAmount;

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

    public int getHousingId() {
        return mHousingId;
    }

    public void setHousingId(int housingId) {
        mHousingId = housingId;
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

    public String getMortgageRentPlanned() {
        return mMortgageRentPlanned;
    }

    public void setMortgageRentPlanned(String mortgageRentPlanned) {
        mMortgageRentPlanned = mortgageRentPlanned;
    }

    public String getMortgageRentSpent() {
        return mMortgageRentSpent;
    }

    public void setMortgageRentSpent(String mortgageRentSpent) {
        mMortgageRentSpent = mortgageRentSpent;
    }

    public int getMortgageRentRecurring() {
        return mMortgageRentRecurring;
    }

    public void setMortgageRentRecurring(int mortgageRentRecurring) {
        mMortgageRentRecurring = mortgageRentRecurring;
    }

    public String getWaterPlanned() {
        return mWaterPlanned;
    }

    public void setWaterPlanned(String waterPlanned) {
        mWaterPlanned = waterPlanned;
    }

    public String getWaterSpent() {
        return mWaterSpent;
    }

    public void setWaterSpent(String waterSpent) {
        mWaterSpent = waterSpent;
    }

    public int getWaterRecurring() {
        return mWaterRecurring;
    }

    public void setWaterRecurring(int waterRecurring) {
        mWaterRecurring = waterRecurring;
    }

    public String getNaturalGasPlanned() {
        return mNaturalGasPlanned;
    }

    public void setNaturalGasPlanned(String naturalGasPlanned) {
        mNaturalGasPlanned = naturalGasPlanned;
    }

    public String getNaturalGasSpent() {
        return mNaturalGasSpent;
    }

    public void setNaturalGasSpent(String naturalGasSpent) {
        mNaturalGasSpent = naturalGasSpent;
    }

    public int getNaturalGasRecurring() {
        return mNaturalGasRecurring;
    }

    public void setNaturalGasRecurring(int naturalGasRecurring) {
        mNaturalGasRecurring = naturalGasRecurring;
    }

    public String getElectricityPlanned() {
        return mElectricityPlanned;
    }

    public void setElectricityPlanned(String electricityPlanned) {
        mElectricityPlanned = electricityPlanned;
    }

    public String getElectricitySpent() {
        return mElectricitySpent;
    }

    public void setElectricitySpent(String electricitySpent) {
        mElectricitySpent = electricitySpent;
    }

    public int getElectricityRecurring() {
        return mElectricityRecurring;
    }

    public void setElectricityRecurring(int electricityRecurring) {
        mElectricityRecurring = electricityRecurring;
    }

    public String getCableInternetPlanned() {
        return mCableInternetPlanned;
    }

    public void setCableInternetPlanned(String cableInternetPlanned) {
        mCableInternetPlanned = cableInternetPlanned;
    }

    public String getCableInternetSpent() {
        return mCableInternetSpent;
    }

    public void setCableInternetSpent(String cableInternetSpent) {
        mCableInternetSpent = cableInternetSpent;
    }

    public int getCableInternetRecurring() {
        return mCableInternetRecurring;
    }

    public void setCableInternetRecurring(int cableInternetRecurring) {
        mCableInternetRecurring = cableInternetRecurring;
    }

    public String getOtherHousingExpenses() {
        return mOtherHousingExpenses;
    }

    public void setOtherHousingExpenses(String otherHousingExpenses) {
        mOtherHousingExpenses = otherHousingExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Housing{" +
                "mHousingId=" + mHousingId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mMortgageRentPlanned='" + mMortgageRentPlanned + '\'' +
                ", mMortgageRentSpent='" + mMortgageRentSpent + '\'' +
                ", mMortgageRentRecurring=" + mMortgageRentRecurring +
                ", mWaterPlanned='" + mWaterPlanned + '\'' +
                ", mWaterSpent='" + mWaterSpent + '\'' +
                ", mWaterRecurring=" + mWaterRecurring +
                ", mNaturalGasPlanned='" + mNaturalGasPlanned + '\'' +
                ", mNaturalGasSpent='" + mNaturalGasSpent + '\'' +
                ", mNaturalGasRecurring=" + mNaturalGasRecurring +
                ", mElectricityPlanned='" + mElectricityPlanned + '\'' +
                ", mElectricitySpent='" + mElectricitySpent + '\'' +
                ", mElectricityRecurring=" + mElectricityRecurring +
                ", mCableInternetPlanned='" + mCableInternetPlanned + '\'' +
                ", mCableInternetSpent='" + mCableInternetSpent + '\'' +
                ", mCableInternetRecurring=" + mCableInternetRecurring +
                ", mOtherHousingExpenses='" + mOtherHousingExpenses + '\'' +
                '}';
    }
}
