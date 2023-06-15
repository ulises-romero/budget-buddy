package com.udromero.budget_buddy.db.entities;

import static com.udromero.budget_buddy.Constants.zeroString;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.udromero.budget_buddy.db.BudgetBuddyDatabase;

@Entity(tableName = BudgetBuddyDatabase.DEBT_TABLE)
public class Debt {
    @PrimaryKey(autoGenerate = true)
    private int mDebtId;

    // Budget Totals
    private String mTotalPlanned;
    private String mTotalRecurring;
    private String mTotalSpent;

    // Attributes
    private String mCarPaymentPlanned;
    private String mCarPaymentSpent;
    private int mCarPaymentRecurring;

    private String mCreditCardPlanned;
    private String mCreditCardSpent;
    private int mCreditCardRecurring;

    private String mStudentLoanPlanned;
    private String mStudentLoanSpent;
    private int mStudentLoanRecurring;

    private String mMedicalBillPlanned;
    private String mMedicalBillSpent;
    private int mMedicalBillRecurring;

    private String mPersonalLoanPlanned;
    private String mPersonalLoanSpent;
    private int mPersonalLoanRecurring;

    private String mOtherDebtExpenses;

    public Debt(String totalPlanned, String totalRecurring, String totalSpent, String carPaymentPlanned, String carPaymentSpent, int carPaymentRecurring, String creditCardPlanned, String creditCardSpent, int creditCardRecurring, String studentLoanPlanned, String studentLoanSpent, int studentLoanRecurring, String medicalBillPlanned, String medicalBillSpent, int medicalBillRecurring, String personalLoanPlanned, String personalLoanSpent, int personalLoanRecurring, String otherDebtExpenses) {
        mCarPaymentPlanned = carPaymentPlanned;
        mCarPaymentRecurring = carPaymentRecurring;
        mCreditCardPlanned = creditCardPlanned;
        mCreditCardRecurring = creditCardRecurring;
        mStudentLoanPlanned = studentLoanPlanned;
        mStudentLoanRecurring = studentLoanRecurring;
        mMedicalBillPlanned = medicalBillPlanned;
        mMedicalBillRecurring = medicalBillRecurring;
        mPersonalLoanPlanned = personalLoanPlanned;
        mPersonalLoanRecurring = personalLoanRecurring;
        mOtherDebtExpenses = otherDebtExpenses;

        // Populate remaining member variables accordingly
        mTotalPlanned = calculateTotalPlanned();
        mTotalRecurring = calculateTotalRecurring();

        // Set all spent values to "0"
        mTotalSpent = "0";
        mCarPaymentSpent = zeroString;
        mCreditCardSpent = zeroString;
        mStudentLoanSpent = zeroString;
        mMedicalBillSpent = zeroString;
        mPersonalLoanSpent = zeroString;
    }

    private String calculateTotalPlanned(){
        String result;

        double carPayment = 0;
        double creditCard = 0;
        double studentLoan = 0;
        double medicalBill = 0;
        double personalLoan = 0;

        if(mCarPaymentPlanned.isEmpty()){
            carPayment = Double.parseDouble(mCarPaymentPlanned);
        }

        if(!mCreditCardPlanned.isEmpty()){
            creditCard = Double.parseDouble(mCreditCardPlanned);
        }

        if(!mStudentLoanPlanned.isEmpty()){
            studentLoan = Double.parseDouble(mStudentLoanPlanned);
        }

        if(!mMedicalBillPlanned.isEmpty()){
            medicalBill = Double.parseDouble(mMedicalBillPlanned);
        }

        if(!mPersonalLoanPlanned.isEmpty()){
            personalLoan = Double.parseDouble(mPersonalLoanPlanned);
        }

        double total = carPayment + creditCard + studentLoan + medicalBill + personalLoan;
        result = String.valueOf(total);

        return result;
    }

    public String calculateTotalRecurring(){
        String result;

        // Amount's for each sub-category
        double carPayment = 0;
        double creditCard = 0;
        double studentLoan = 0;
        double medicalBill = 0;
        double personalLoan = 0;

        // For each sub-category check whether it's a recurring expense
        if(mCarPaymentRecurring == 1){
            carPayment = Double.parseDouble(mCarPaymentPlanned);
        }

        if(mCreditCardRecurring == 1){
            creditCard = Double.parseDouble(mCreditCardPlanned);
        }

        if(mStudentLoanRecurring == 1){
            studentLoan = Double.parseDouble(mStudentLoanPlanned);
        }

        if(mMedicalBillRecurring == 1){
            medicalBill = Double.parseDouble(mMedicalBillPlanned);
        }

        if(mPersonalLoanRecurring == 1){
            personalLoan = Double.parseDouble(mPersonalLoanPlanned);
        }

        // Check for no recurring fields being equal to 1, if so just set result = "0"
        if(mCarPaymentRecurring == 0 && mCreditCardRecurring == 0 &&
                mStudentLoanRecurring == 0 && mMedicalBillRecurring == 0 && mPersonalLoanRecurring == 0){
            result = "0";
        } else {
            // add up all amounts into the total
            double total = carPayment + creditCard + studentLoan + medicalBill + personalLoan;

            // set result string to string value of total
            result = String.valueOf(total);
        }

        return result;
    }

    public int getDebtId() {
        return mDebtId;
    }

    public void setDebtId(int debtId) {
        mDebtId = debtId;
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

    public String getCarPaymentPlanned() {
        return mCarPaymentPlanned;
    }

    public void setCarPaymentPlanned(String carPaymentPlanned) {
        mCarPaymentPlanned = carPaymentPlanned;
    }

    public String getCarPaymentSpent() {
        return mCarPaymentSpent;
    }

    public void setCarPaymentSpent(String carPaymentSpent) {
        mCarPaymentSpent = carPaymentSpent;
    }

    public int getCarPaymentRecurring() {
        return mCarPaymentRecurring;
    }

    public void setCarPaymentRecurring(int carPaymentRecurring) {
        mCarPaymentRecurring = carPaymentRecurring;
    }

    public String getCreditCardPlanned() {
        return mCreditCardPlanned;
    }

    public void setCreditCardPlanned(String creditCardPlanned) {
        mCreditCardPlanned = creditCardPlanned;
    }

    public String getCreditCardSpent() {
        return mCreditCardSpent;
    }

    public void setCreditCardSpent(String creditCardSpent) {
        mCreditCardSpent = creditCardSpent;
    }

    public int getCreditCardRecurring() {
        return mCreditCardRecurring;
    }

    public void setCreditCardRecurring(int creditCardRecurring) {
        mCreditCardRecurring = creditCardRecurring;
    }

    public String getStudentLoanPlanned() {
        return mStudentLoanPlanned;
    }

    public void setStudentLoanPlanned(String studentLoanPlanned) {
        mStudentLoanPlanned = studentLoanPlanned;
    }

    public String getStudentLoanSpent() {
        return mStudentLoanSpent;
    }

    public void setStudentLoanSpent(String studentLoanSpent) {
        mStudentLoanSpent = studentLoanSpent;
    }

    public int getStudentLoanRecurring() {
        return mStudentLoanRecurring;
    }

    public void setStudentLoanRecurring(int studentLoanRecurring) {
        mStudentLoanRecurring = studentLoanRecurring;
    }

    public String getMedicalBillPlanned() {
        return mMedicalBillPlanned;
    }

    public void setMedicalBillPlanned(String medicalBillPlanned) {
        mMedicalBillPlanned = medicalBillPlanned;
    }

    public String getMedicalBillSpent() {
        return mMedicalBillSpent;
    }

    public void setMedicalBillSpent(String medicalBillSpent) {
        mMedicalBillSpent = medicalBillSpent;
    }

    public int getMedicalBillRecurring() {
        return mMedicalBillRecurring;
    }

    public void setMedicalBillRecurring(int medicalBillRecurring) {
        mMedicalBillRecurring = medicalBillRecurring;
    }

    public String getPersonalLoanPlanned() {
        return mPersonalLoanPlanned;
    }

    public void setPersonalLoanPlanned(String personalLoanPlanned) {
        mPersonalLoanPlanned = personalLoanPlanned;
    }

    public String getPersonalLoanSpent() {
        return mPersonalLoanSpent;
    }

    public void setPersonalLoanSpent(String personalLoanSpent) {
        mPersonalLoanSpent = personalLoanSpent;
    }

    public int getPersonalLoanRecurring() {
        return mPersonalLoanRecurring;
    }

    public void setPersonalLoanRecurring(int personalLoanRecurring) {
        mPersonalLoanRecurring = personalLoanRecurring;
    }

    public String getOtherDebtExpenses() {
        return mOtherDebtExpenses;
    }

    public void setOtherDebtExpenses(String otherDebtExpenses) {
        mOtherDebtExpenses = otherDebtExpenses;
    }

    @NonNull
    @Override
    public String toString() {
        return "Debt{" +
                "mDebtId=" + mDebtId +
                ", mTotalPlanned='" + mTotalPlanned + '\'' +
                ", mTotalRecurring='" + mTotalRecurring + '\'' +
                ", mTotalSpent='" + mTotalSpent + '\'' +
                ", mCarPaymentPlanned='" + mCarPaymentPlanned + '\'' +
                ", mCarPaymentSpent='" + mCarPaymentSpent + '\'' +
                ", mCarPaymentRecurring=" + mCarPaymentRecurring +
                ", mCreditCardPlanned='" + mCreditCardPlanned + '\'' +
                ", mCreditCardSpent='" + mCreditCardSpent + '\'' +
                ", mCreditCardRecurring=" + mCreditCardRecurring +
                ", mStudentLoanPlanned='" + mStudentLoanPlanned + '\'' +
                ", mStudentLoanSpent='" + mStudentLoanSpent + '\'' +
                ", mStudentLoanRecurring=" + mStudentLoanRecurring +
                ", mMedicalBillPlanned='" + mMedicalBillPlanned + '\'' +
                ", mMedicalBillSpent='" + mMedicalBillSpent + '\'' +
                ", mMedicalBillRecurring=" + mMedicalBillRecurring +
                ", mPersonalLoanPlanned='" + mPersonalLoanPlanned + '\'' +
                ", mPersonalLoanSpent='" + mPersonalLoanSpent + '\'' +
                ", mPersonalLoanRecurring=" + mPersonalLoanRecurring +
                ", mOtherDebtExpenses='" + mOtherDebtExpenses + '\'' +
                '}';
    }
}
