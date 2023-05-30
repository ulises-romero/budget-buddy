package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.PERSONAL_TABLE)
public class Personal {
    @PrimaryKey(autoGenerate = true)
    private int mPersonalId;

    // Non-null Attributes
    private String mTotal;
    private String mRecurring_total;

    // Other Attributes
    private String mClothing;
    private int mClothingRecurring;
    private String mPhone;
    private int mPhoneRecurring;
    private String mEntertainment;
    private int mEntertainmentRecurring;
    private String mGifts;
    private int mGiftsRecurring;
    private String mSubscriptions;
    private int mSubscriptionsRecurring;
    private String mOther;
    private String mOtherRecurring;

    public Personal(String total, String recurring_total, String clothing, int clothingRecurring, String phone, int phoneRecurring, String entertainment,
                    int entertainmentRecurring, String gifts, int giftsRecurring, String subscriptions, int subscriptionsRecurring, String other, String otherRecurring) {
        mTotal = total;
        mRecurring_total = recurring_total;
        mClothing = clothing;
        mClothingRecurring = clothingRecurring;
        mPhone = phone;
        mPhoneRecurring = phoneRecurring;
        mEntertainment = entertainment;
        mEntertainmentRecurring = entertainmentRecurring;
        mGifts = gifts;
        mGiftsRecurring = giftsRecurring;
        mSubscriptions = subscriptions;
        mSubscriptionsRecurring = subscriptionsRecurring;
        mOther = other;
        mOtherRecurring = otherRecurring;
    }

    public int getPersonalId() {
        return mPersonalId;
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

    public String getClothing() {
        return mClothing;
    }

    public void setClothing(String clothing) {
        mClothing = clothing;
    }

    public int getClothingRecurring() {
        return mClothingRecurring;
    }

    public void setClothingRecurring(int clothingRecurring) {
        mClothingRecurring = clothingRecurring;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getPhoneRecurring() {
        return mPhoneRecurring;
    }

    public void setPhoneRecurring(int phoneRecurring) {
        mPhoneRecurring = phoneRecurring;
    }

    public String getEntertainment() {
        return mEntertainment;
    }

    public void setEntertainment(String entertainment) {
        mEntertainment = entertainment;
    }

    public int getEntertainmentRecurring() {
        return mEntertainmentRecurring;
    }

    public void setEntertainmentRecurring(int entertainmentRecurring) {
        mEntertainmentRecurring = entertainmentRecurring;
    }

    public String getGifts() {
        return mGifts;
    }

    public void setGifts(String gifts) {
        mGifts = gifts;
    }

    public int getGiftsRecurring() {
        return mGiftsRecurring;
    }

    public void setGiftsRecurring(int giftsRecurring) {
        mGiftsRecurring = giftsRecurring;
    }

    public String getSubscriptions() {
        return mSubscriptions;
    }

    public void setSubscriptions(String subscriptions) {
        mSubscriptions = subscriptions;
    }

    public int getSubscriptionsRecurring() {
        return mSubscriptionsRecurring;
    }

    public void setSubscriptionsRecurring(int subscriptionsRecurring) {
        mSubscriptionsRecurring = subscriptionsRecurring;
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

    @NonNull
    @Override
    public String toString() {
        return "Personal{" +
                "mPersonalId=" + mPersonalId +
                ", mTotal='" + mTotal + '\'' +
                ", mRecurring_total='" + mRecurring_total + '\'' +
                ", mClothing='" + mClothing + '\'' +
                ", mClothingRecurring=" + mClothingRecurring +
                ", mPhone='" + mPhone + '\'' +
                ", mPhoneRecurring=" + mPhoneRecurring +
                ", mEntertainment='" + mEntertainment + '\'' +
                ", mEntertainmentRecurring=" + mEntertainmentRecurring +
                ", mGifts='" + mGifts + '\'' +
                ", mGiftsRecurring=" + mGiftsRecurring +
                ", mSubscriptions='" + mSubscriptions + '\'' +
                ", mSubscriptionsRecurring=" + mSubscriptionsRecurring +
                ", mOther='" + mOther + '\'' +
                ", mOtherRecurring='" + mOtherRecurring + '\'' +
                '}';
    }
}
