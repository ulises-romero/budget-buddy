package com.udromero.budget_buddy.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.TRANSPORTATION_TABLE)
public class Transportation {
    @PrimaryKey(autoGenerate = true)
    private int mTransportationId;

    // Non-null Attributes
    private String mTotal;
    private String mRecurring_total;

    // Other Attributes
    private String mAutoInsurance;
    private int mAutoInsuranceRecurring;
    private String mGasOil;
    private int mGasOilReccuring;
    private String mMaitenance;
    private int mMaitenanceRecurring;
    private String mCarPayment;
    private int mCarPaymentRecurring;
    private String mOther;
    private String mOtherRecurring;


    public Transportation(String total, String recurring_total, String autoInsurance, int autoInsuranceRecurring,
                          String gasOil, int gasOilReccuring, String maitenance, int maitenanceRecurring, String carPayment, int carPaymentRecurring, String other, String otherRecurring) {
        mTotal = total;
        mRecurring_total = recurring_total;
        mAutoInsurance = autoInsurance;
        mAutoInsuranceRecurring = autoInsuranceRecurring;
        mGasOil = gasOil;
        mGasOilReccuring = gasOilReccuring;
        mMaitenance = maitenance;
        mMaitenanceRecurring = maitenanceRecurring;
        mCarPayment = carPayment;
        mCarPaymentRecurring = carPaymentRecurring;
        mOther = other;
        mOtherRecurring = otherRecurring;
    }

    public void setTransportationId(int transportationId) {
        mTransportationId = transportationId;
    }

    public int getTransportationId() {
        return mTransportationId;
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

    public String getAutoInsurance() {
        return mAutoInsurance;
    }

    public void setAutoInsurance(String autoInsurance) {
        mAutoInsurance = autoInsurance;
    }

    public int getAutoInsuranceRecurring() {
        return mAutoInsuranceRecurring;
    }

    public void setAutoInsuranceRecurring(int autoInsuranceRecurring) {
        mAutoInsuranceRecurring = autoInsuranceRecurring;
    }

    public String getGasOil() {
        return mGasOil;
    }

    public void setGasOil(String gasOil) {
        mGasOil = gasOil;
    }

    public int getGasOilReccuring() {
        return mGasOilReccuring;
    }

    public void setGasOilReccuring(int gasOilReccuring) {
        mGasOilReccuring = gasOilReccuring;
    }

    public String getMaitenance() {
        return mMaitenance;
    }

    public void setMaitenance(String maitenance) {
        mMaitenance = maitenance;
    }

    public int getMaitenanceRecurring() {
        return mMaitenanceRecurring;
    }

    public void setMaitenanceRecurring(int maitenanceRecurring) {
        mMaitenanceRecurring = maitenanceRecurring;
    }

    public String getCarPayment() {
        return mCarPayment;
    }

    public void setCarPayment(String carPayment) {
        mCarPayment = carPayment;
    }

    public int getCarPaymentRecurring() {
        return mCarPaymentRecurring;
    }

    public void setCarPaymentRecurring(int carPaymentRecurring) {
        mCarPaymentRecurring = carPaymentRecurring;
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
        return "Transportation{" +
                "mTransportationId=" + mTransportationId +
                ", mTotal='" + mTotal + '\'' +
                ", mRecurring_total='" + mRecurring_total + '\'' +
                ", mAutoInsurance='" + mAutoInsurance + '\'' +
                ", mAutoInsuranceRecurring=" + mAutoInsuranceRecurring +
                ", mGasOil='" + mGasOil + '\'' +
                ", mGasOilReccuring=" + mGasOilReccuring +
                ", mMaitenance='" + mMaitenance + '\'' +
                ", mMaitenanceRecurring=" + mMaitenanceRecurring +
                ", mCarPayment='" + mCarPayment + '\'' +
                ", mCarPaymentRecurring=" + mCarPaymentRecurring +
                ", mOther='" + mOther + '\'' +
                ", mOtherRecurring='" + mOtherRecurring + '\'' +
                '}';
    }
}
