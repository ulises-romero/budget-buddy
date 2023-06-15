package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.FOOD_TABLE)
public class Food {
    @PrimaryKey(autoGenerate = true)
    private int mFoodId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mGroceriesPlanned;
    private String mGroceriesSpent;
    private int mGroceriesRecurring;

    private String mRestaurantsPlanned;
    private String mRestaurantsSpent;
    private int mRestaurantsRecurring;

    private String mOtherSavingsExpenses;

    public Food(String groceriesPlanned, int groceriesRecurring, String restaurantsPlanned, int restaurantsRecurring, String otherSavingsExpenses) {
        mGroceriesPlanned = groceriesPlanned;
        mGroceriesRecurring = groceriesRecurring;
        mRestaurantsPlanned = restaurantsPlanned;
        mRestaurantsRecurring = restaurantsRecurring;
        mOtherSavingsExpenses = otherSavingsExpenses;

        // Populate remaining member variables accordingly
        mTotalPlanned = calculateTotalPlanned();
        mTotalRecurring = calculateTotalRecurring();

        // Set all spent values to "0"
        mTotalSpent = "0";
        mGroceriesSpent = "0";
        mRestaurantsSpent = "0";
    }

    private String calculateTotalPlanned(){
        String result;

        double groceries = 0;
        double restaurants = 0;

        if(!mGroceriesPlanned.isEmpty()){
            groceries = Double.parseDouble(mGroceriesPlanned);
        }

        if(!mRestaurantsPlanned.isEmpty()){
            restaurants = Double.parseDouble(mRestaurantsPlanned);
        }

        double total = groceries + restaurants;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double groceriesAmount = 0;
        double restaurantAmount = 0;

        // For each sub-category check whether it's a recurring expense
        if(mGroceriesRecurring == 1){
            groceriesAmount = Double.parseDouble(mGroceriesPlanned);
        }

        if(mRestaurantsRecurring == 1){
            restaurantAmount = Double.parseDouble(mRestaurantsPlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mGroceriesRecurring == 0 && mRestaurantsRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = groceriesAmount + restaurantAmount;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getFoodId() {
        return mFoodId;
    }

    public void setFoodId(int foodId) {
        mFoodId = foodId;
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

    public String getGroceriesPlanned() {
        return mGroceriesPlanned;
    }

    public void setGroceriesPlanned(String groceriesPlanned) {
        mGroceriesPlanned = groceriesPlanned;
    }

    public String getGroceriesSpent() {
        return mGroceriesSpent;
    }

    public void setGroceriesSpent(String groceriesSpent) {
        mGroceriesSpent = groceriesSpent;
    }

    public int getGroceriesRecurring() {
        return mGroceriesRecurring;
    }

    public void setGroceriesRecurring(int groceriesRecurring) {
        mGroceriesRecurring = groceriesRecurring;
    }

    public String getRestaurantsPlanned() {
        return mRestaurantsPlanned;
    }

    public void setRestaurantsPlanned(String restaurantsPlanned) {
        mRestaurantsPlanned = restaurantsPlanned;
    }

    public String getRestaurantsSpent() {
        return mRestaurantsSpent;
    }

    public void setRestaurantsSpent(String restaurantsSpent) {
        mRestaurantsSpent = restaurantsSpent;
    }

    public int getRestaurantsRecurring() {
        return mRestaurantsRecurring;
    }

    public void setRestaurantsRecurring(int restaurantsRecurring) {
        mRestaurantsRecurring = restaurantsRecurring;
    }

    public String getOtherSavingsExpenses() {
        return mOtherSavingsExpenses;
    }

    public void setOtherSavingsExpenses(String otherSavingsExpenses) {
        mOtherSavingsExpenses = otherSavingsExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Food{" +
                "mFoodId=" + mFoodId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mGroceriesPlanned='" + mGroceriesPlanned + '\'' +
                ", mGroceriesSpent='" + mGroceriesSpent + '\'' +
                ", mGroceriesRecurring=" + mGroceriesRecurring +
                ", mRestaurantsPlanned='" + mRestaurantsPlanned + '\'' +
                ", mRestaurantsSpent='" + mRestaurantsSpent + '\'' +
                ", mRestaurantsRecurring=" + mRestaurantsRecurring +
                ", otherSavingsExpenses='" + mOtherSavingsExpenses + '\'' +
                '}';
    }
}
