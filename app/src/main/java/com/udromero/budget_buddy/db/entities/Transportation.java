package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.TRANSPORTATION_TABLE)
public class Transportation {
    @PrimaryKey(autoGenerate = true)
    private int mTransportationId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mGasPlanned;
    private String mGasSpent;
    private int mGasRecurring;

    private String mMaintenancePlanned;
    private String mMaintenanceSpent;
    private int mMaintenanceRecurring;

    private String mOtherTransportationExpenses;

    public Transportation(String totalPlanned, String totalRecurring, String totalSpent, String gasPlanned, String gasSpent, int gasRecurring, String maintenancePlanned, String maintenanceSpent, int maintenanceRecurring, String otherTransportationExpenses) {
        mGasPlanned = gasPlanned;
        mGasRecurring = gasRecurring;
        mMaintenancePlanned = maintenancePlanned;
        mMaintenanceRecurring = maintenanceRecurring;
        mOtherTransportationExpenses = otherTransportationExpenses;

        // Populate remaining member variables accordingly
        mTotalPlanned = calculateTotalPlanned();
        mTotalRecurring = calculateTotalRecurring();

        // Other
        mTotalSpent = "0";
        mGasSpent = zeroString;
        mMaintenanceSpent = zeroString;
    }

    private String calculateTotalPlanned(){
        String result;

        double gas = 0;
        double maitenance = 0;

        if(!mGasPlanned.isEmpty()){
            gas = Double.parseDouble(mGasPlanned);
        }

        if(!mMaintenancePlanned.isEmpty()){
            maitenance = Double.parseDouble(mMaintenancePlanned);
        }

        double total = gas + maitenance;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double gasAmount = 0;
        double maintenanceAmount = 0;

        // For each sub-category check whether it's a recurring expense
        if(mGasRecurring == 1){
            gasAmount = Double.parseDouble(mGasPlanned);
        }

        if(mMaintenanceRecurring == 1){
            maintenanceAmount = Double.parseDouble(mMaintenancePlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mGasRecurring == 0 && mMaintenanceRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = gasAmount + maintenanceAmount;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getTransportationId() {
        return mTransportationId;
    }

    public void setTransportationId(int transportationId) {
        mTransportationId = transportationId;
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

    public String getGasPlanned() {
        return mGasPlanned;
    }

    public void setGasPlanned(String gasPlanned) {
        mGasPlanned = gasPlanned;
    }

    public String getGasSpent() {
        return mGasSpent;
    }

    public void setGasSpent(String gasSpent) {
        mGasSpent = gasSpent;
    }

    public int getGasRecurring() {
        return mGasRecurring;
    }

    public void setGasRecurring(int gasRecurring) {
        mGasRecurring = gasRecurring;
    }

    public String getMaintenancePlanned() {
        return mMaintenancePlanned;
    }

    public void setMaintenancePlanned(String maintenancePlanned) {
        mMaintenancePlanned = maintenancePlanned;
    }

    public String getMaintenanceSpent() {
        return mMaintenanceSpent;
    }

    public void setMaintenanceSpent(String maintenanceSpent) {
        mMaintenanceSpent = maintenanceSpent;
    }

    public int getMaintenanceRecurring() {
        return mMaintenanceRecurring;
    }

    public void setMaintenanceRecurring(int maintenanceRecurring) {
        mMaintenanceRecurring = maintenanceRecurring;
    }

    public String getOtherTransportationExpenses() {
        return mOtherTransportationExpenses;
    }

    public void setOtherTransportationExpenses(String otherTransportationExpenses) {
        mOtherTransportationExpenses = otherTransportationExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Transportation{" +
                "mTransportationId=" + mTransportationId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mGasPlanned='" + mGasPlanned + '\'' +
                ", mGasSpent='" + mGasSpent + '\'' +
                ", mGasRecurring=" + mGasRecurring +
                ", mMaintenancePlanned='" + mMaintenancePlanned + '\'' +
                ", mMaintenanceSpent='" + mMaintenanceSpent + '\'' +
                ", mMaintenanceRecurring=" + mMaintenanceRecurring +
                ", mOtherTransportationExpenses='" + mOtherTransportationExpenses + '\'' +
                '}';
    }
}
