package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.GIVING_TABLE)
public class Giving {
    @PrimaryKey(autoGenerate = true)
    private int mGivingId;

    // Non-null Attributes
    private String mTotal;
    private String mRecurring_total;

    // Other Attributes
    private String mChurch;
    private int mChurchRecurring; // set to 1 if reoccurring else set 0 if not a recurring payment

    private String mCharity;
    private int mCharityReccuring;

    private String mOther;
    private String mOtherReccurring;

    public Giving(String total, String recurring_total, String church, int churchRecurring, String charity, int charityReccuring, String other, String otherReccurring) {
        mTotal = total;
        mRecurring_total = recurring_total;
        mChurch = church;
        mChurchRecurring = churchRecurring;
        mCharity = charity;
        mCharityReccuring = charityReccuring;
        mOther = other;
        mOtherReccurring = otherReccurring;
    }

    public void setGivingId(int givingId) {
        mGivingId = givingId;
    }

    public int getGivingId() {
        return mGivingId;
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

    public String getChurch() {
        return mChurch;
    }

    public void setChurch(String church) {
        mChurch = church;
    }

    public int getChurchRecurring() {
        return mChurchRecurring;
    }

    public void setChurchRecurring(int churchRecurring) {
        mChurchRecurring = churchRecurring;
    }

    public String getCharity() {
        return mCharity;
    }

    public void setCharity(String charity) {
        mCharity = charity;
    }

    public int getCharityReccuring() {
        return mCharityReccuring;
    }

    public void setCharityReccuring(int charityReccuring) {
        mCharityReccuring = charityReccuring;
    }

    public String getOther() {
        return mOther;
    }

    public void setOther(String other) {
        mOther = other;
    }

    public String getOtherReccurring() {
        return mOtherReccurring;
    }

    public void setOtherReccurring(String otherReccurring) {
        mOtherReccurring = otherReccurring;
    }

    @NonNull
    @Override
    public String toString() {
        return "Giving{" +
                "mGivingId=" + mGivingId +
                ", mTotal='" + mTotal + '\'' +
                ", mRecurring_total='" + mRecurring_total + '\'' +
                ", mChurch='" + mChurch + '\'' +
                ", mChurchRecurring=" + mChurchRecurring +
                ", mCharity='" + mCharity + '\'' +
                ", mCharityReccuring=" + mCharityReccuring +
                ", mOther='" + mOther + '\'' +
                ", mOtherReccurring='" + mOtherReccurring + '\'' +
                '}';
    }
}
