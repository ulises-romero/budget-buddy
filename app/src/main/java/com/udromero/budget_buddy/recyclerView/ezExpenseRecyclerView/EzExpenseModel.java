package com.udromero.budget_buddy.recyclerView.ezExpenseRecyclerView;

import androidx.annotation.NonNull;

public class EzExpenseModel {

    String mDate;
    String mDescription;
    String mCategory;
    String mSubCategory;
    String mPlannedAmount;

    public EzExpenseModel(String date, String description, String category, String subCategory, String plannedAmount) {
        mDate = date;
        mDescription = description;
        mCategory = category;
        mSubCategory = subCategory;
        mPlannedAmount = plannedAmount;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getSubCategory() {
        return mSubCategory;
    }

    public void setSubCategory(String subCategory) {
        mSubCategory = subCategory;
    }

    public String getPlannedAmount() {
        return mPlannedAmount;
    }

    public void setPlannedAmount(String plannedAmount) {
        mPlannedAmount = plannedAmount;
    }

    @NonNull
    @Override
    public String toString() {
        return "EzExpenseModel{" +
                "mDate='" + mDate + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mSubCategory='" + mSubCategory + '\'' +
                ", mPlannedAmount='" + mPlannedAmount + '\'' +
                '}';
    }
}
