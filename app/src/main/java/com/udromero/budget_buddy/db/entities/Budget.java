package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.nullInt;
import static com.udromero.budget_buddy.Constants.nullString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.BUDGET_TABLE)
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private int mBudgetId;

    // Foreign Keys (pass in -1 to show a table needs to be added or not)
    private int mUserId;
    private int mHousingId;
    private int mGivingId;
    private int mPersonalId;
    private int mTransportationId;
    private int mFoodId;
    private int mOtherId;

    // Non-null Attributes
    private int mPaycheckFrequency; // must be value of 7, 14, or 30
    private String mTotalRecurringExpenses;

    // Other Optional Attributes
    private String mIncome = nullString;
    private String mExpenseCategories = nullString;
    private String mMonthlyPayday = nullString;
    private String mBiweeklyPayday = nullString;
    private String mWeeklyPayday = nullString;

    public Budget(int userId) {
        mUserId = userId;
        mHousingId = nullInt;
        mGivingId = nullInt;
        mPersonalId = nullInt;
        mTransportationId = nullInt;
        mFoodId = nullInt;
        mOtherId = nullInt;
        mPaycheckFrequency = 0;
        mTotalRecurringExpenses = "0";
    }

    public int getBudgetId() {
        return mBudgetId;
    }

    public void setBudgetId(int budgetId) {
        mBudgetId = budgetId;
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

    public int getGivingId() {
        return mGivingId;
    }

    public void setGivingId(int givingId) {
        mGivingId = givingId;
    }

    public int getPersonalId() {
        return mPersonalId;
    }

    public void setPersonalId(int personalId) {
        mPersonalId = personalId;
    }

    public int getTransportationId() {
        return mTransportationId;
    }

    public void setTransportationId(int transportationId) {
        mTransportationId = transportationId;
    }

    public int getFoodId() {
        return mFoodId;
    }

    public void setFoodId(int foodId) {
        mFoodId = foodId;
    }

    public int getOtherId() {
        return mOtherId;
    }

    public void setOtherId(int otherId) {
        mOtherId = otherId;
    }

    public int getPaycheckFrequency() {
        return mPaycheckFrequency;
    }

    public void setPaycheckFrequency(int paycheckFrequency) {
        mPaycheckFrequency = paycheckFrequency;
    }

    public String getTotalRecurringExpenses() {
        return mTotalRecurringExpenses;
    }

    public void setTotalRecurringExpenses(String totalRecurringExpenses) {
        mTotalRecurringExpenses = totalRecurringExpenses;
    }

    public String getIncome() {
        return mIncome;
    }

    public void setIncome(String income) {
        mIncome = income;
    }

    public String getExpenseCategories() {
        return mExpenseCategories;
    }

    public void setExpenseCategories(String expenseCategories) {
        mExpenseCategories = expenseCategories;
    }

    public String getMonthlyPayday() {
        return mMonthlyPayday;
    }

    public void setMonthlyPayday(String monthlyPayday) {
        mMonthlyPayday = monthlyPayday;
    }

    public String getBiweeklyPayday() {
        return mBiweeklyPayday;
    }

    public void setBiweeklyPayday(String biweeklyPayday) {
        mBiweeklyPayday = biweeklyPayday;
    }

    public String getWeeklyPayday() {
        return mWeeklyPayday;
    }

    public void setWeeklyPayday(String weeklyPayday) {
        mWeeklyPayday = weeklyPayday;
    }

    @NonNull
    @Override
    public String toString() {
        return "Budget{" +
                "mBudgetId=" + mBudgetId +
                ", mUserId=" + mUserId +
                ", mHousingId=" + mHousingId +
                ", mGivingId=" + mGivingId +
                ", mPersonalId=" + mPersonalId +
                ", mTransportationId=" + mTransportationId +
                ", mFoodId=" + mFoodId +
                ", mOtherId=" + mOtherId +
                ", mPaycheckFrequency=" + mPaycheckFrequency +
                ", mTotalRecurringExpenses='" + mTotalRecurringExpenses + '\'' +
                ", mIncome='" + mIncome + '\'' +
                ", mExpenseCategories='" + mExpenseCategories + '\'' +
                ", mMonthlyPayday='" + mMonthlyPayday + '\'' +
                ", mBiweeklyPayday='" + mBiweeklyPayday + '\'' +
                ", mWeeklyPayday='" + mWeeklyPayday + '\'' +
                '}';
    }
}
