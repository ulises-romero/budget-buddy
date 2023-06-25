package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.INSURANCE_TABLE)
public class Insurance {
    @PrimaryKey(autoGenerate = true)
    private int mInsuranceId;

    private int mUserId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mHealthInsurancePlanned;
    private String mHealthInsuranceSpent;
    private int mHealthInsuranceRecurring;

    private String mLifeInsurancePlanned;
    private String mLifeInsuranceSpent;
    private int mLifeInsuranceRecurring;

    private String mAutoInsurancePlanned;
    private String mAutoInsuranceSpent;
    private int mAutoInsuranceRecurring;

    private String mHomeownerRenterPlanned;
    private String mHomeownerRenterSpent;
    private int mHomeownerRenterRecurring;

    private String mOtherInsuranceExpenses;

    public Insurance(int userId, String totalPlanned, String totalRecurring, String totalSpent, String healthInsurancePlanned, String healthInsuranceSpent, int healthInsuranceRecurring, String lifeInsurancePlanned, String lifeInsuranceSpent, int lifeInsuranceRecurring, String autoInsurancePlanned, String autoInsuranceSpent, int autoInsuranceRecurring, String homeownerRenterPlanned, String homeownerRenterSpent, int homeownerRenterRecurring, String otherInsuranceExpenses) {
        mUserId = userId;
        mTotalPlanned = totalPlanned;
        mTotalRecurring = totalRecurring;
        mTotalSpent = totalSpent;
        mHealthInsurancePlanned = healthInsurancePlanned;
        mHealthInsuranceSpent = healthInsuranceSpent;
        mHealthInsuranceRecurring = healthInsuranceRecurring;
        mLifeInsurancePlanned = lifeInsurancePlanned;
        mLifeInsuranceSpent = lifeInsuranceSpent;
        mLifeInsuranceRecurring = lifeInsuranceRecurring;
        mAutoInsurancePlanned = autoInsurancePlanned;
        mAutoInsuranceSpent = autoInsuranceSpent;
        mAutoInsuranceRecurring = autoInsuranceRecurring;
        mHomeownerRenterPlanned = homeownerRenterPlanned;
        mHomeownerRenterSpent = homeownerRenterSpent;
        mHomeownerRenterRecurring = homeownerRenterRecurring;
        mOtherInsuranceExpenses = otherInsuranceExpenses;
    }

    private String calculateTotalPlanned(){
        String result;

        double health = 0;
        double life = 0;
        double auto = 0;
        double homeownerRenter = 0;

        if(mHealthInsurancePlanned.isEmpty()){
            health = Double.parseDouble(mHealthInsurancePlanned);
        }

        if(!mLifeInsurancePlanned.isEmpty()){
            life = Double.parseDouble(mLifeInsurancePlanned);
        }

        if(!mAutoInsurancePlanned.isEmpty()){
            auto = Double.parseDouble(mAutoInsurancePlanned);
        }

        if(!mHomeownerRenterPlanned.isEmpty()){
            homeownerRenter = Double.parseDouble(mHomeownerRenterPlanned);
        }

        double total = health + life + auto + homeownerRenter;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double health = 0;
        double life = 0;
        double auto = 0;
        double homeownerRenter = 0;

        // For each sub-category check whether it's a recurring expense
        if(mHealthInsuranceRecurring == 1){
            health = Double.parseDouble(mHealthInsurancePlanned);
        }

        if(mLifeInsuranceRecurring == 1){
            life = Double.parseDouble(mLifeInsurancePlanned);
        }

        if(mAutoInsuranceRecurring == 1){
            auto = Double.parseDouble(mAutoInsurancePlanned);
        }

        if(mHomeownerRenterRecurring == 1){
            homeownerRenter = Double.parseDouble(mHomeownerRenterPlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mHealthInsuranceRecurring == 0 && mLifeInsuranceRecurring == 0 &&
                mAutoInsuranceRecurring == 0 && mHomeownerRenterRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = health + life + auto + homeownerRenter;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getInsuranceId() {
        return mInsuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        mInsuranceId = insuranceId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
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

    public String getHealthInsurancePlanned() {
        return mHealthInsurancePlanned;
    }

    public void setHealthInsurancePlanned(String healthInsurancePlanned) {
        mHealthInsurancePlanned = healthInsurancePlanned;
    }

    public String getHealthInsuranceSpent() {
        return mHealthInsuranceSpent;
    }

    public void setHealthInsuranceSpent(String healthInsuranceSpent) {
        mHealthInsuranceSpent = healthInsuranceSpent;
    }

    public int getHealthInsuranceRecurring() {
        return mHealthInsuranceRecurring;
    }

    public void setHealthInsuranceRecurring(int healthInsuranceRecurring) {
        mHealthInsuranceRecurring = healthInsuranceRecurring;
    }

    public String getLifeInsurancePlanned() {
        return mLifeInsurancePlanned;
    }

    public void setLifeInsurancePlanned(String lifeInsurancePlanned) {
        mLifeInsurancePlanned = lifeInsurancePlanned;
    }

    public String getLifeInsuranceSpent() {
        return mLifeInsuranceSpent;
    }

    public void setLifeInsuranceSpent(String lifeInsuranceSpent) {
        mLifeInsuranceSpent = lifeInsuranceSpent;
    }

    public int getLifeInsuranceRecurring() {
        return mLifeInsuranceRecurring;
    }

    public void setLifeInsuranceRecurring(int lifeInsuranceRecurring) {
        mLifeInsuranceRecurring = lifeInsuranceRecurring;
    }

    public String getAutoInsurancePlanned() {
        return mAutoInsurancePlanned;
    }

    public void setAutoInsurancePlanned(String autoInsurancePlanned) {
        mAutoInsurancePlanned = autoInsurancePlanned;
    }

    public String getAutoInsuranceSpent() {
        return mAutoInsuranceSpent;
    }

    public void setAutoInsuranceSpent(String autoInsuranceSpent) {
        mAutoInsuranceSpent = autoInsuranceSpent;
    }

    public int getAutoInsuranceRecurring() {
        return mAutoInsuranceRecurring;
    }

    public void setAutoInsuranceRecurring(int autoInsuranceRecurring) {
        mAutoInsuranceRecurring = autoInsuranceRecurring;
    }

    public String getHomeownerRenterPlanned() {
        return mHomeownerRenterPlanned;
    }

    public void setHomeownerRenterPlanned(String homeownerRenterPlanned) {
        mHomeownerRenterPlanned = homeownerRenterPlanned;
    }

    public String getHomeownerRenterSpent() {
        return mHomeownerRenterSpent;
    }

    public void setHomeownerRenterSpent(String homeownerRenterSpent) {
        mHomeownerRenterSpent = homeownerRenterSpent;
    }

    public int getHomeownerRenterRecurring() {
        return mHomeownerRenterRecurring;
    }

    public void setHomeownerRenterRecurring(int homeownerRenterRecurring) {
        mHomeownerRenterRecurring = homeownerRenterRecurring;
    }

    public String getOtherInsuranceExpenses() {
        return mOtherInsuranceExpenses;
    }

    public void setOtherInsuranceExpenses(String otherInsuranceExpenses) {
        mOtherInsuranceExpenses = otherInsuranceExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Insurance{" +
                "mInsuranceId=" + mInsuranceId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mHealthInsurancePlanned='" + mHealthInsurancePlanned + '\'' +
                ", mHealthInsuranceSpent='" + mHealthInsuranceSpent + '\'' +
                ", mHealthInsuranceRecurring=" + mHealthInsuranceRecurring +
                ", mLifeInsurancePlanned='" + mLifeInsurancePlanned + '\'' +
                ", mLifeInsuranceSpent='" + mLifeInsuranceSpent + '\'' +
                ", mLifeInsuranceRecurring=" + mLifeInsuranceRecurring +
                ", mAutoInsurancePlanned='" + mAutoInsurancePlanned + '\'' +
                ", mAutoInsuranceSpent='" + mAutoInsuranceSpent + '\'' +
                ", mAutoInsuranceRecurring=" + mAutoInsuranceRecurring +
                ", mHomeownerRenterPlanned='" + mHomeownerRenterPlanned + '\'' +
                ", mHomeownerRenterSpent='" + mHomeownerRenterSpent + '\'' +
                ", mHomeownerRenterRecurring=" + mHomeownerRenterRecurring +
                ", mOtherInsuranceExpenses='" + mOtherInsuranceExpenses + '\'' +
                '}';
    }
}
