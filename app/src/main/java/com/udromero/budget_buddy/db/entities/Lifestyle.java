package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.LIFESTYLE_TABLE)
public class Lifestyle {
    @PrimaryKey(autoGenerate = true)
    private int mLifestyleId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mChildCarePlanned;
    private String mChildCareSpent;
    private int mChildCareRecurring;

    private String mPetCarePlanned;
    private String mPetCareSpent;
    private int mPetCareRecurring;

    private String mEntertainmentPlanned;
    private String mEntertainmentSpent;
    private int mEntertainmentRecurring;

    private String mVacationPlanned;
    private String mVacationSpent;
    private int mVacationRecurring;

    private String mEducationTuitionPlanned;
    private String mEducationTuitionSpent;
    private int mEducationTuitionRecurring;

    private String mOtherLifestyleExpenses;

    public Lifestyle(String childCarePlanned, int childCareRecurring, String petCarePlanned, int petCareRecurring, String entertainmentPlanned, int entertainmentRecurring, String vacationPlanned, int vacationRecurring, String educationTuitionPlanned, int educationTuitionRecurring, String otherLifestyleExpenses) {
        mChildCarePlanned = childCarePlanned;
        mChildCareRecurring = childCareRecurring;
        mPetCarePlanned = petCarePlanned;
        mPetCareRecurring = petCareRecurring;
        mEntertainmentPlanned = entertainmentPlanned;
        mEntertainmentRecurring = entertainmentRecurring;
        mVacationPlanned = vacationPlanned;
        mVacationRecurring = vacationRecurring;
        mEducationTuitionPlanned = educationTuitionPlanned;
        mEducationTuitionRecurring = educationTuitionRecurring;
        mOtherLifestyleExpenses = otherLifestyleExpenses;

        // Populate remaining member variables accordingly
        mTotalPlanned = calculateTotalPlanned();
        mTotalRecurring = calculateTotalRecurring();

        // Set all spent values to "0"
        mTotalSpent = zeroString;
        mChildCareSpent = zeroString;
        mPetCareSpent = zeroString;
        mEntertainmentSpent = zeroString;
        mVacationSpent = zeroString;
        mEducationTuitionSpent = zeroString;
    }

    private String calculateTotalPlanned(){
        String result;

        double childCare = 0;
        double petCare = 0;
        double entertainment = 0;
        double vacation = 0;
        double educationTuition = 0;

        if(!mChildCarePlanned.isEmpty()){
            childCare = Double.parseDouble(mChildCarePlanned);
        }

        if(!mPetCarePlanned.isEmpty()){
            petCare = Double.parseDouble(mPetCarePlanned);
        }

        if(!mEntertainmentPlanned.isEmpty()){
            entertainment = Double.parseDouble(mEntertainmentPlanned);
        }

        if(!mVacationPlanned.isEmpty()){
            vacation = Double.parseDouble(mVacationPlanned);
        }

        if(!mEducationTuitionPlanned.isEmpty()){
            educationTuition = Double.parseDouble(mEducationTuitionPlanned);
        }

        double total = childCare + petCare + entertainment + vacation + educationTuition;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double childCareAmount = 0;
        double petCareAmount = 0;
        double entertainmentAmount = 0;
        double vacationAmount = 0;
        double educationTuitionAmount = 0;

        // For each sub-category check whether it's a recurring expense
        if(mChildCareRecurring == 1){
            childCareAmount = Double.parseDouble(mChildCarePlanned);
        }

        if(mPetCareRecurring == 1){
            petCareAmount = Double.parseDouble(mPetCarePlanned);
        }

        if(mEntertainmentRecurring == 1){
            entertainmentAmount = Double.parseDouble(mEntertainmentPlanned);
        }

        if(mVacationRecurring == 1){
            vacationAmount = Double.parseDouble(mVacationPlanned);
        }

        if(mEducationTuitionRecurring == 1){
            educationTuitionAmount = Double.parseDouble(mEducationTuitionPlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mChildCareRecurring == 0 && mPetCareRecurring == 0 && mEducationTuitionRecurring == 0 &&
                mVacationRecurring == 0 && mEducationTuitionRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = childCareAmount + petCareAmount + entertainmentAmount + vacationAmount +
                    educationTuitionAmount;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getLifestyleId() {
        return mLifestyleId;
    }

    public void setLifestyleId(int lifestyleId) {
        mLifestyleId = lifestyleId;
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

    public String getChildCarePlanned() {
        return mChildCarePlanned;
    }

    public void setChildCarePlanned(String childCarePlanned) {
        mChildCarePlanned = childCarePlanned;
    }

    public String getChildCareSpent() {
        return mChildCareSpent;
    }

    public void setChildCareSpent(String childCareSpent) {
        mChildCareSpent = childCareSpent;
    }

    public int getChildCareRecurring() {
        return mChildCareRecurring;
    }

    public void setChildCareRecurring(int childCareRecurring) {
        mChildCareRecurring = childCareRecurring;
    }

    public String getPetCarePlanned() {
        return mPetCarePlanned;
    }

    public void setPetCarePlanned(String petCarePlanned) {
        mPetCarePlanned = petCarePlanned;
    }

    public String getPetCareSpent() {
        return mPetCareSpent;
    }

    public void setPetCareSpent(String petCareSpent) {
        mPetCareSpent = petCareSpent;
    }

    public int getPetCareRecurring() {
        return mPetCareRecurring;
    }

    public void setPetCareRecurring(int petCareRecurring) {
        mPetCareRecurring = petCareRecurring;
    }

    public String getEntertainmentPlanned() {
        return mEntertainmentPlanned;
    }

    public void setEntertainmentPlanned(String entertainmentPlanned) {
        mEntertainmentPlanned = entertainmentPlanned;
    }

    public String getEntertainmentSpent() {
        return mEntertainmentSpent;
    }

    public void setEntertainmentSpent(String entertainmentSpent) {
        mEntertainmentSpent = entertainmentSpent;
    }

    public int getEntertainmentRecurring() {
        return mEntertainmentRecurring;
    }

    public void setEntertainmentRecurring(int entertainmentRecurring) {
        mEntertainmentRecurring = entertainmentRecurring;
    }

    public String getVacationPlanned() {
        return mVacationPlanned;
    }

    public void setVacationPlanned(String vacationPlanned) {
        mVacationPlanned = vacationPlanned;
    }

    public String getVacationSpent() {
        return mVacationSpent;
    }

    public void setVacationSpent(String vacationSpent) {
        mVacationSpent = vacationSpent;
    }

    public int getVacationRecurring() {
        return mVacationRecurring;
    }

    public void setVacationRecurring(int vacationRecurring) {
        mVacationRecurring = vacationRecurring;
    }

    public String getEducationTuitionPlanned() {
        return mEducationTuitionPlanned;
    }

    public void setEducationTuitionPlanned(String educationTuitionPlanned) {
        mEducationTuitionPlanned = educationTuitionPlanned;
    }

    public String getEducationTuitionSpent() {
        return mEducationTuitionSpent;
    }

    public void setEducationTuitionSpent(String educationTuitionSpent) {
        mEducationTuitionSpent = educationTuitionSpent;
    }

    public int getEducationTuitionRecurring() {
        return mEducationTuitionRecurring;
    }

    public void setEducationTuitionRecurring(int educationTuitionRecurring) {
        mEducationTuitionRecurring = educationTuitionRecurring;
    }

    public String getOtherLifestyleExpenses() {
        return mOtherLifestyleExpenses;
    }

    public void setOtherLifestyleExpenses(String otherLifestyleExpenses) {
        mOtherLifestyleExpenses = otherLifestyleExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Lifestyle{" +
                "mLifestyleId=" + mLifestyleId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mChildCarePlanned='" + mChildCarePlanned + '\'' +
                ", mChildCareSpent='" + mChildCareSpent + '\'' +
                ", mChildCareRecurring=" + mChildCareRecurring +
                ", mPetCarePlanned='" + mPetCarePlanned + '\'' +
                ", mPetCareSpent='" + mPetCareSpent + '\'' +
                ", mPetCareRecurring=" + mPetCareRecurring +
                ", mEntertainmentPlanned='" + mEntertainmentPlanned + '\'' +
                ", mEntertainmentSpent='" + mEntertainmentSpent + '\'' +
                ", mEntertainmentRecurring=" + mEntertainmentRecurring +
                ", mVacationPlanned='" + mVacationPlanned + '\'' +
                ", mVacationSpent='" + mVacationSpent + '\'' +
                ", mVacationRecurring=" + mVacationRecurring +
                ", mEducationTuitionPlanned='" + mEducationTuitionPlanned + '\'' +
                ", mEducationTuitionSpent='" + mEducationTuitionSpent + '\'' +
                ", mEducationTuitionRecurring=" + mEducationTuitionRecurring +
                ", mOtherLifestyleExpenses='" + mOtherLifestyleExpenses + '\'' +
                '}';
    }
}
