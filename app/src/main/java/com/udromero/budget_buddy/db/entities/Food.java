package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.FOOD_TABLE)

public class Food {
    @PrimaryKey(autoGenerate = true)
    private int mFoodId;

    // Non-null Attributes
    private String mTotal;
    private String mRecurring_total;

    // Other Attributes
    private String mGroceries;
    private int mGroceriesRecurring;
    private String mRestaurants;
    private int mRestaurantsRecurring;
    private String mOther;
    private String mOtherReccuring;

    public Food(String total, String recurring_total, String groceries, int groceriesRecurring, String restaurants, int restaurantsRecurring, String other, String otherReccuring) {
        mTotal = total;
        mRecurring_total = recurring_total;
        mGroceries = groceries;
        mGroceriesRecurring = groceriesRecurring;
        mRestaurants = restaurants;
        mRestaurantsRecurring = restaurantsRecurring;
        mOther = other;
        mOtherReccuring = otherReccuring;
    }

    public void setFoodId(int foodId) {
        mFoodId = foodId;
    }

    public int getFoodId() {
        return mFoodId;
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

    public String getGroceries() {
        return mGroceries;
    }

    public void setGroceries(String groceries) {
        mGroceries = groceries;
    }

    public int getGroceriesRecurring() {
        return mGroceriesRecurring;
    }

    public void setGroceriesRecurring(int groceriesRecurring) {
        mGroceriesRecurring = groceriesRecurring;
    }

    public String getRestaurants() {
        return mRestaurants;
    }

    public void setRestaurants(String restaurants) {
        mRestaurants = restaurants;
    }

    public int getRestaurantsRecurring() {
        return mRestaurantsRecurring;
    }

    public void setRestaurantsRecurring(int restaurantsRecurring) {
        mRestaurantsRecurring = restaurantsRecurring;
    }

    public String getOther() {
        return mOther;
    }

    public void setOther(String other) {
        mOther = other;
    }

    public String getOtherReccuring() {
        return mOtherReccuring;
    }

    public void setOtherReccuring(String otherReccuring) {
        mOtherReccuring = otherReccuring;
    }

    @NonNull
    @Override
    public String toString() {
        return "Food{" +
                "mFoodId=" + mFoodId +
                ", mTotal='" + mTotal + '\'' +
                ", mRecurring_total='" + mRecurring_total + '\'' +
                ", mGroceries='" + mGroceries + '\'' +
                ", mGroceriesRecurring=" + mGroceriesRecurring +
                ", mRestaurants='" + mRestaurants + '\'' +
                ", mRestaurantsRecurring=" + mRestaurantsRecurring +
                ", mOther='" + mOther + '\'' +
                ", mOtherReccuring='" + mOtherReccuring + '\'' +
                '}';
    }
}
