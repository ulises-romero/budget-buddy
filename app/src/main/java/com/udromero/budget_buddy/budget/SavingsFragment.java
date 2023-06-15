package com.udromero.budget_buddy.budget;

import static com.udromero.budget_buddy.Constants.BUDGET_ID_KEY;
import static com.udromero.budget_buddy.Constants.GIVING_ID_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.SAVINGS_ID_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;
import static com.udromero.budget_buddy.Constants.nullString;
import static com.udromero.budget_buddy.Constants.zeroString;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udromero.budget_buddy.R;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Giving;
import com.udromero.budget_buddy.db.entities.Savings;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;

public class SavingsFragment extends Fragment {

    SharedPreferences mSharedPreferences;

    BudgetBuddyDAO mBudgetBuddyDAO;

    // Page Components
    ImageView mSavingsPageLogo;

    Button mRefreshButton;

    TextView mRangeDisplay;
    TextView mCalculatedRangeDisplay;

    TextView mErrorDisplay;

    TextView mPlannedDisplay;
    TextView mRecurringDisplay;

    TextView mEmergencyFundPlannedPromptDisplay;
    EditText mEmergencyFundPlannedInputEditText;
    CheckBox mEmergencyFundRecurringCheckBox;

    Button mSaveButton;

    TextView mRangeCheckDisplay;
    Button mRangeColorButton;
    TextView mTotalDisplay;

    TextView mTestDisplay;

    // Other global variables
    User mUser;
    int mUserId;

    Budget mBudget;
    int mBudgetId;

    Savings mSavings = null;
    int mSavingsId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_savings, container, false);

        // Page binding (in order from top to bottom)s
        mSavingsPageLogo = view.findViewById(R.id.savingsPageLogo);
        mRefreshButton = view.findViewById(R.id.savingsRefreshButton);

        mRangeDisplay = view.findViewById(R.id.savingsIncomeTotalPercentageRangeDisplay);
        mCalculatedRangeDisplay = view.findViewById(R.id.savingsUserIncomeCalculatedRangeDisplay);
        mErrorDisplay = view.findViewById(R.id.savingsErrorDisplay);
        mPlannedDisplay = view.findViewById(R.id.savingsPlannedAmountDisplay);
        mRecurringDisplay = view.findViewById(R.id.savingsRecurringDisplay);

        mEmergencyFundPlannedPromptDisplay = view.findViewById(R.id.savingsEmergencyFundDisplay);
        mEmergencyFundPlannedInputEditText = view.findViewById(R.id.savingsEmergencyFundPlannedInputEditText);
        mEmergencyFundRecurringCheckBox = view.findViewById(R.id.savingsEmergencyFundRecurringCheckBox);

        mSaveButton = view.findViewById(R.id.savingsSaveButton);

        mRangeCheckDisplay = view.findViewById(R.id.savingsRangeCheckDisplay);
        mRangeColorButton = view.findViewById(R.id.savingsRangeButton);
        mTotalDisplay = view.findViewById(R.id.savingsTotalDisplay);

        mTestDisplay = view.findViewById(R.id.savingsTestDisplay);

        // Get database and shared preferences
        getDataBase();
        getPrefs();

        if(checkForValidIncome()){
            // Populate range display
            updateCalculatedRangeDisplay();

            populateScreen();
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkForValidIncome() || checkAllEmptyFields()){
                    return;
                }

                pullValuesIntoDatabase();
                grabLatestUserInfo();
                populateScreen();
                populateSummarySection(mSavings.getTotalPlanned());

                mTestDisplay.setText(mBudget.toString() + mUser.toString() + mSavings.toString());
            }
        });

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabLatestUserInfo();
                if(checkForValidIncome()){
                    // Populate range display
                    updateCalculatedRangeDisplay();

                    populateScreen();
                }
            }
        });

        return view;
    }

    private void getDataBase(){
        mBudgetBuddyDAO = Room.databaseBuilder(this.getActivity().getApplicationContext(), BudgetBuddyDatabase.class, BudgetBuddyDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .BudgetBuddyDAO();
    }

    private void getPrefs() {
        mSharedPreferences = this.getActivity().getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);

        if(mUser == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR: Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mBudgetId = mSharedPreferences.getInt(BUDGET_ID_KEY, -1);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);

        // Check for valid giving ID
        mSavingsId = mSharedPreferences.getInt(SAVINGS_ID_KEY, -1);
        if(mSavingsId == -1){
            Toast.makeText(getActivity().getApplicationContext(), "FATAL ERROR: Logging out...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);

        if(mSavings == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NULL GIVING TABLE): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mTestDisplay.setText(mBudget.toString() + mUser.toString() + mSavings.toString());
    }

    private boolean checkForValidIncome(){
        if(mBudget.getIncome().equals(nullString)){
            mErrorDisplay.setText(getString(R.string.givingErrorPrompt));
            hideAllOnScreenItems();
            return false;
        }

        mErrorDisplay.setText("");
        return true;
    }

    private boolean checkGivingHasBeenPopulated(){
        if(mSavings.getTotalPlanned().equals("0")){
            return false;
        }

        return true;
    }

    private void grabLatestUserInfo(){
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        mBudgetId = mSharedPreferences.getInt(BUDGET_ID_KEY, -1);
        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
        mSavingsId = mSharedPreferences.getInt(GIVING_ID_KEY, -1);
    }

    private void updateCalculatedRangeDisplay(){
        // 1. Check user has an income in the database
        grabLatestUserInfo();
        if(mBudget.getIncome().equals(nullString)){
            hideAllOnScreenItems();
            mErrorDisplay.setText(R.string.missingIncomeWarning);
        } else {
            String currIncome = mBudget.getIncome();
            double income = Double.parseDouble(currIncome);
            double low = income * .10;
            double high = income * .15;
            mCalculatedRangeDisplay.setText(getString(R.string.givingCalculatedRangeDisplay, String.valueOf(low), String.valueOf(high)));
        }
    }

    private void hideAllOnScreenItems(){
        mCalculatedRangeDisplay.setText(getString(R.string.givingCalculatedRangeDisplay, "0.00", "0.00"));
        mEmergencyFundPlannedInputEditText.setHint("n/a");
        mRangeCheckDisplay.setText(getString(R.string.rangeCheck, ""));
        mTotalDisplay.setText(getString(R.string.total, ""));
    }

    private boolean checkAllEmptyFields(){
        return mEmergencyFundRecurringCheckBox.getText().toString().equals(zeroString) && !mEmergencyFundRecurringCheckBox.isChecked();
    }

    private void pullValuesIntoDatabase(){
        String emergencyFundPlanned = mEmergencyFundPlannedInputEditText.getText().toString();

        double emergencyFund = Double.parseDouble(mSavings.getEmergencyFundPlanned());

        grabLatestUserInfo();

        if(emergencyFundPlanned.isEmpty() || emergencyFundPlanned.equals(zeroString)){
            emergencyFund = 0;
            mSavings.setEmergencyFundPlanned(zeroString);
        } else if(!emergencyFundPlanned.equals(mSavings.getEmergencyFundPlanned())){
            emergencyFund = Double.parseDouble(emergencyFundPlanned);
            mSavings.setEmergencyFundPlanned(emergencyFundPlanned);
        }

        double total = emergencyFund;
        String stringTotal = String.valueOf(total);

        int emergencyFundRecurring = 0;

        String stringCurrRecurringTotal = mSavings.getTotalRecurring();
        double currRecurringTotal = Double.parseDouble(stringCurrRecurringTotal);
        double currTotal = 0;

        if(mEmergencyFundRecurringCheckBox.isChecked()){
            emergencyFundRecurring = 1;
            currTotal = emergencyFund + currRecurringTotal;
            mSavings.setTotalRecurring(String.valueOf(currTotal));
        } else {
            currTotal = currRecurringTotal - emergencyFund;
            if(currRecurringTotal > 0){
                mSavings.setTotalRecurring(String.valueOf(currTotal));
            }
        }

        mSavings.setEmergencyFundRecurring(emergencyFundRecurring);
        mSavings.setTotalPlanned(stringTotal);

        mBudgetBuddyDAO.update(mSavings);
    }

    private void populateSummarySection(String total){
        mTotalDisplay.setText(getString(R.string.total, total));
        String rangeCheck = calculateRangeCheck(total);
        mRangeCheckDisplay.setText(getString(R.string.rangeCheck, rangeCheck));
    }

    private String calculateRangeCheck(String stringTotal) {
        double total = Double.parseDouble(stringTotal);
        String stringIncome = mBudget.getIncome();
        double income = Double.parseDouble(stringIncome);
        double lower = income * .10;
        double upper = income * .15;
        double range = upper - lower;
        double third = range / 3;

        if(total < lower){
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.gray));
            return "Under budget.";
        }

        if (total >= lower && total < lower + third) {
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.green));
            return "Great!";
        }

        if(total >= lower + third && total < lower + third * 2){
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.yellow));

            return "Average.";
        }

        if(total >= lower + third * 2 && total <= upper){
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.orange));
            return "Maxing out.";
        }

        if(total > upper){
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.red));
            return "WARNING\n\t- Over budget.";
        }

        return "ERROR...";
    }

    private void populateScreen(){
        grabLatestUserInfo();
        if(!checkGivingHasBeenPopulated()){
            mSaveButton.setText("SAVE");
            mTotalDisplay.setText(getString(R.string.total, ""));
            mRangeCheckDisplay.setText(getString(R.string.rangeCheck, ""));
            mEmergencyFundPlannedInputEditText.setHint("Enter Amount");
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.gray));
            return;
        }

        mSaveButton.setText("UPDATE");

        if(mSavings.getEmergencyFundRecurring() == 1){
            mEmergencyFundRecurringCheckBox.setChecked(true);
        }

        // TODO: SET HINT TO PREVIOUS MONTHS AMOUNT
        String emergencyFundPlanned = mSavings.getEmergencyFundPlanned();
        mEmergencyFundPlannedInputEditText.setText(emergencyFundPlanned);
        mEmergencyFundPlannedInputEditText.setHint(getString(R.string.previous, "n/a"));

        populateSummarySection(mSavings.getTotalPlanned());
    }
}