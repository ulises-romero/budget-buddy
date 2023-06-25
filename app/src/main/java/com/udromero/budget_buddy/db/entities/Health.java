package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.HEALTH_TABLE)
public class Health {
    @PrimaryKey(autoGenerate = true)
    private int mHealthId;

    private int mUserId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mGymPlanned;
    private String mGymSpent;
    private int mGymRecurring;

    private String mMedicineVitaminsPlanned;
    private String mMedicineVitaminsSpent;
    private int mMedicineVitaminsRecurring;

    private String mDoctorVisitsPlanned;
    private String mDoctorVisitsSpent;
    private int mDoctorVisitsRecurring;

    private String mOtherHealthExpenses;

    public Health(int userId, String totalPlanned, String totalRecurring, String totalSpent, String gymPlanned, String gymSpent, int gymRecurring, String medicineVitaminsPlanned, String medicineVitaminsSpent, int medicineVitaminsRecurring, String doctorVisitsPlanned, String doctorVisitsSpent, int doctorVisitsRecurring, String otherHealthExpenses) {
        mUserId = userId;
        mTotalPlanned = totalPlanned;
        mTotalRecurring = totalRecurring;
        mTotalSpent = totalSpent;
        mGymPlanned = gymPlanned;
        mGymSpent = gymSpent;
        mGymRecurring = gymRecurring;
        mMedicineVitaminsPlanned = medicineVitaminsPlanned;
        mMedicineVitaminsSpent = medicineVitaminsSpent;
        mMedicineVitaminsRecurring = medicineVitaminsRecurring;
        mDoctorVisitsPlanned = doctorVisitsPlanned;
        mDoctorVisitsSpent = doctorVisitsSpent;
        mDoctorVisitsRecurring = doctorVisitsRecurring;
        mOtherHealthExpenses = otherHealthExpenses;
    }

    private String calculateTotalPlanned(){
        String result;

        double gym = 0;
        double medicineVitamins = 0;
        double doctorVisits = 0;

        if(!mGymPlanned.isEmpty()){
            gym = Double.parseDouble(mGymPlanned);
        }

        if(!mMedicineVitaminsPlanned.isEmpty()){
            medicineVitamins = Double.parseDouble(mMedicineVitaminsPlanned);
        }

        if(!mDoctorVisitsPlanned.isEmpty()){
            doctorVisits = Double.parseDouble(mDoctorVisitsPlanned);
        }

        double total = gym + medicineVitamins + doctorVisits;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double gymAmount = 0;
        double medicineVitaminsAmount = 0;
        double doctorVisitsAmount = 0;

        // For each sub-category check whether it's a recurring expense
        if(mGymRecurring == 1){
            gymAmount = Double.parseDouble(mGymPlanned);
        }

        if(mMedicineVitaminsRecurring == 1){
            medicineVitaminsAmount = Double.parseDouble(mMedicineVitaminsPlanned);
        }

        if(mDoctorVisitsRecurring == 1){
            doctorVisitsAmount = Double.parseDouble(mDoctorVisitsPlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mGymRecurring == 0 && mMedicineVitaminsRecurring == 0 && mDoctorVisitsRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = gymAmount + medicineVitaminsAmount + doctorVisitsAmount;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getHealthId() {
        return mHealthId;
    }

    public void setHealthId(int healthId) {
        mHealthId = healthId;
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

    public String getGymPlanned() {
        return mGymPlanned;
    }

    public void setGymPlanned(String gymPlanned) {
        mGymPlanned = gymPlanned;
    }

    public String getGymSpent() {
        return mGymSpent;
    }

    public void setGymSpent(String gymSpent) {
        mGymSpent = gymSpent;
    }

    public int getGymRecurring() {
        return mGymRecurring;
    }

    public void setGymRecurring(int gymRecurring) {
        mGymRecurring = gymRecurring;
    }

    public String getMedicineVitaminsPlanned() {
        return mMedicineVitaminsPlanned;
    }

    public void setMedicineVitaminsPlanned(String medicineVitaminsPlanned) {
        mMedicineVitaminsPlanned = medicineVitaminsPlanned;
    }

    public String getMedicineVitaminsSpent() {
        return mMedicineVitaminsSpent;
    }

    public void setMedicineVitaminsSpent(String medicineVitaminsSpent) {
        mMedicineVitaminsSpent = medicineVitaminsSpent;
    }

    public int getMedicineVitaminsRecurring() {
        return mMedicineVitaminsRecurring;
    }

    public void setMedicineVitaminsRecurring(int medicineVitaminsRecurring) {
        mMedicineVitaminsRecurring = medicineVitaminsRecurring;
    }

    public String getDoctorVisitsPlanned() {
        return mDoctorVisitsPlanned;
    }

    public void setDoctorVisitsPlanned(String doctorVisitsPlanned) {
        mDoctorVisitsPlanned = doctorVisitsPlanned;
    }

    public String getDoctorVisitsSpent() {
        return mDoctorVisitsSpent;
    }

    public void setDoctorVisitsSpent(String doctorVisitsSpent) {
        mDoctorVisitsSpent = doctorVisitsSpent;
    }

    public int getDoctorVisitsRecurring() {
        return mDoctorVisitsRecurring;
    }

    public void setDoctorVisitsRecurring(int doctorVisitsRecurring) {
        mDoctorVisitsRecurring = doctorVisitsRecurring;
    }

    public String getOtherHealthExpenses() {
        return mOtherHealthExpenses;
    }

    public void setOtherHealthExpenses(String otherHealthExpenses) {
        mOtherHealthExpenses = otherHealthExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Health{" +
                "mHealthId=" + mHealthId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mGymPlanned='" + mGymPlanned + '\'' +
                ", mGymSpent='" + mGymSpent + '\'' +
                ", mGymRecurring=" + mGymRecurring +
                ", mMedicineVitaminsPlanned='" + mMedicineVitaminsPlanned + '\'' +
                ", mMedicineVitaminsSpent='" + mMedicineVitaminsSpent + '\'' +
                ", mMedicineVitaminsRecurring=" + mMedicineVitaminsRecurring +
                ", mDoctorVisitsPlanned='" + mDoctorVisitsPlanned + '\'' +
                ", mDoctorVisitsSpent='" + mDoctorVisitsSpent + '\'' +
                ", mDoctorVisitsRecurring=" + mDoctorVisitsRecurring +
                ", mOtherHealthExpenses='" + mOtherHealthExpenses + '\'' +
                '}';
    }
}
