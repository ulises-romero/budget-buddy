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
    private int mGivingId;
    private int mSavingsId;
    private int mHousingId;
    private int mFoodId;
    private int mTransportationId;
    private int mPersonalId;
    private int mLifestyleId;
    private int mHealthId;
    private int mInsuranceId;
    private int mDebtId;

    private int mOtherId;

    // Non-null Attributes
    private int mPaycheckFrequency; // must be value of 7, 14, or 30
    private String mTotalRecurringExpenses;
    private String mTotalPlannedExpenses;
    private String mTotalSpent;

    // Other Optional Attributes
    private String mIncome = nullString;
    private String mExpenseCategories = nullString;
    private String mMonthlyPayday = nullString;
    private String mBiweeklyPayday = nullString;
    private String mWeeklyPayday = nullString;

    public Budget(int userId, int givingId, int savingsId, int housingId, int foodId, int transportationId, int personalId, int lifestyleId, int healthId, int insuranceId, int debtId) {
        mUserId = userId;
        mGivingId = givingId;
        mSavingsId = savingsId;
        mHousingId = housingId;
        mFoodId = foodId;
        mTransportationId = transportationId;
        mPersonalId = personalId;
        mLifestyleId = lifestyleId;
        mHealthId = healthId;
        mInsuranceId = insuranceId;
        mDebtId = debtId;
        mOtherId = nullInt;

        mPaycheckFrequency = 0;
        mTotalRecurringExpenses = "0";
        mTotalPlannedExpenses = "0";
        mTotalSpent = "0";
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

    public int getGivingId() {
        return mGivingId;
    }

    public void setGivingId(int givingId) {
        mGivingId = givingId;
    }

    public int getSavingsId() {
        return mSavingsId;
    }

    public void setSavingsId(int savingsId) {
        mSavingsId = savingsId;
    }

    public int getHousingId() {
        return mHousingId;
    }

    public void setHousingId(int housingId) {
        mHousingId = housingId;
    }

    public int getFoodId() {
        return mFoodId;
    }

    public void setFoodId(int foodId) {
        mFoodId = foodId;
    }

    public int getTransportationId() {
        return mTransportationId;
    }

    public void setTransportationId(int transportationId) {
        mTransportationId = transportationId;
    }

    public int getPersonalId() {
        return mPersonalId;
    }

    public void setPersonalId(int personalId) {
        mPersonalId = personalId;
    }

    public int getLifestyleId() {
        return mLifestyleId;
    }

    public void setLifestyleId(int lifestyleId) {
        mLifestyleId = lifestyleId;
    }

    public int getHealthId() {
        return mHealthId;
    }

    public void setHealthId(int healthId) {
        mHealthId = healthId;
    }

    public int getInsuranceId() {
        return mInsuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        mInsuranceId = insuranceId;
    }

    public int getDebtId() {
        return mDebtId;
    }

    public void setDebtId(int debtId) {
        mDebtId = debtId;
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

    public String getTotalPlannedExpenses() {
        return mTotalPlannedExpenses;
    }

    public void setTotalPlannedExpenses(String totalPlannedExpenses) {
        mTotalPlannedExpenses = totalPlannedExpenses;
    }

    public String getTotalSpent() {
        return mTotalSpent;
    }

    public void setTotalSpent(String totalSpent) {
        mTotalSpent = totalSpent;
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
                ", mGivingId=" + mGivingId +
                ", mSavingsId=" + mSavingsId +
                ", mHousingId=" + mHousingId +
                ", mFoodId=" + mFoodId +
                ", mTransportationId=" + mTransportationId +
                ", mPersonalId=" + mPersonalId +
                ", mLifestyleId=" + mLifestyleId +
                ", mHealthId=" + mHealthId +
                ", mInsuranceId=" + mInsuranceId +
                ", mDebtId=" + mDebtId +
                ", mOtherId=" + mOtherId +
                ", mPaycheckFrequency=" + mPaycheckFrequency +
                ", mTotalRecurringExpenses='" + mTotalRecurringExpenses + '\'' +
                ", mTotalPlannedExpenses='" + mTotalPlannedExpenses + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mIncome='" + mIncome + '\'' +
                ", mExpenseCategories='" + mExpenseCategories + '\'' +
                ", mMonthlyPayday='" + mMonthlyPayday + '\'' +
                ", mBiweeklyPayday='" + mBiweeklyPayday + '\'' +
                ", mWeeklyPayday='" + mWeeklyPayday + '\'' +
                '}';
    }
}
