package com.udromero.budget_buddy.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.OTHER_TABLE)
public class Other {
    @PrimaryKey(autoGenerate = true)
    private int mOtherId;

    // Non-null Attributes
    private String mTotal;
    private String mRecurring_total;

    // Other Attributes
    private String mOther;
    private String mOtherRecurring;

    public Other(String total, String recurring_total, String other, String otherRecurring) {
        mTotal = total;
        mRecurring_total = recurring_total;
        mOther = other;
        mOtherRecurring = otherRecurring;
    }

    public int getOtherId() {
        return mOtherId;
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

    @Override
    public String toString() {
        return "Other{" +
                "mOtherId=" + mOtherId +
                ", mTotal='" + mTotal + '\'' +
                ", mRecurring_total='" + mRecurring_total + '\'' +
                ", mOther='" + mOther + '\'' +
                ", mOtherRecurring='" + mOtherRecurring + '\'' +
                '}';
    }
}
