package com.udromero.budget_buddy.budget;

import static com.udromero.budget_buddy.Constants.BUDGET_ID_KEY;
import static com.udromero.budget_buddy.Constants.DAY_PAYDAY_INT_KEY;
import static com.udromero.budget_buddy.Constants.LOGGED_IN_KEY;
import static com.udromero.budget_buddy.Constants.MONTH_PAYDAY_INT_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;
import static com.udromero.budget_buddy.Constants.YEAR_PAYDAY_INT_KEY;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udromero.budget_buddy.R;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;

import java.util.Calendar;
import java.util.Date;

public class IncomeFragment extends Fragment {

    private TextView mIncomePromptDisplay, mPayDayDisplay, mSwitchDisplay, mEnterIncomePromptDisplay;
    private TextView mCurrentIncomeDisplay, mCurrentPaymentFrequencyDisplay, mCurrentPaydayDisplay;

    private RadioGroup mWeekBasedRadioGroup, mMonthBasedRadioGroup;
    private RadioButton mRadioButtonWeekly, mRadioButtonBiWeekly, mRadioButtonMonthly;
    private RadioButton mSunday, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday;
    private RadioButton mSOM, mEOM, mSetDate;

    private EditText mIncomeField;

    private Button mSaveButton;
    private Button mNextButton;
    private Button mSetDateButton;

    SharedPreferences mSharedPreferences;

    BudgetBuddyDAO mBudgetBuddyDAO;

    int mUserId;
    int mBudgetId;
    User mUser;
    Budget mBudget;

    int mNextPayDayMonth;
    int mNextPaydayDay;
    int mNextPaydayYear;

    String mPaymentFrequency;

    boolean next = false;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        getDataBase();

        getPrefs();

        mSwitchDisplay = view.findViewById(R.id.incomeWhatToDisplay);
        mPayDayDisplay = view.findViewById(R.id.incomePaydayDisplay);
        mIncomePromptDisplay = view.findViewById(R.id.incomePromptDisplay);
        mCurrentIncomeDisplay = view.findViewById(R.id.incomeIncomeDisplay);
        mCurrentPaymentFrequencyDisplay = view.findViewById(R.id.incomePaymentFrequencyDisplay);
        mCurrentPaydayDisplay = view.findViewById(R.id.incomePaydayInfoDisplay);
        mEnterIncomePromptDisplay = view.findViewById(R.id.incomeIncomeInputDisplay);

        // Radio Groups
        mWeekBasedRadioGroup = view.findViewById(R.id.incomeWeekBasedPayDayRadioGroup);
        mMonthBasedRadioGroup = view.findViewById(R.id.incomeMonthlyPaydayRadioGroup);

        // Radio Buttons
        mRadioButtonWeekly = view.findViewById(R.id.radioButtonWeekly);
        mRadioButtonBiWeekly = view.findViewById(R.id.radioButtonBiWeekly);
        mRadioButtonMonthly = view.findViewById(R.id.radioButtonMonthly);

        mSunday = view.findViewById(R.id.radioButtonSunday);
        mMonday = view.findViewById(R.id.radioButtonMonday);
        mTuesday = view.findViewById(R.id.radioButtonTuesday);
        mWednesday = view.findViewById(R.id.radioButtonWednesday);
        mThursday = view.findViewById(R.id.radioButtonThursday);
        mFriday = view.findViewById(R.id.radioButtonFriday);
        mSaturday = view.findViewById(R.id.radioButtonSaturday);

        mSOM = view.findViewById(R.id.radioButtonStart);
        mEOM = view.findViewById(R.id.radioButtonEnd);
        mSetDate = view.findViewById(R.id.radioButtonSpecificDate);

        mSaveButton = view.findViewById(R.id.incomeSaveButton);
        mNextButton = view.findViewById(R.id.incomeNextButton);
        mSetDateButton = view.findViewById(R.id.incomeSetPayDateButton);

        mIncomeField = view.findViewById(R.id.incomeIncomeInputEditText);

        populateCurrentInformation();

        if(mBudget.getPaycheckFrequency() == 0){
            mNextButton.setVisibility(View.VISIBLE);
            mSaveButton.setVisibility(View.INVISIBLE);
            mIncomePromptDisplay.setVisibility(View.INVISIBLE);
            mIncomeField.setVisibility(View.INVISIBLE);
            next = true;
        } else {
            setIncomeField();
            mNextButton.setVisibility(View.INVISIBLE);
            mSaveButton.setVisibility(View.VISIBLE);
            next = false;
        }

        updatePaydayRadioGroupsToShow();

//        mIncomePromptDisplay.setText(getString(R.string.settingsEmailDisplay, mBudget.toString()));

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
                grabLatestUserInfo();

                populateCurrentInformation();
                updatePaydayRadioGroupsToShow();
                mNextButton.setVisibility(View.INVISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);
                setIncomeField();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
                grabLatestUserInfo();

                updatePaydayRadioGroupsToShow();
                populateCurrentInformation();
                setIncomeField();
            }
        });

        mSetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return view;
    }

    private void getDataBase(){
        mBudgetBuddyDAO = Room.databaseBuilder(this.getActivity().getApplicationContext(), BudgetBuddyDatabase.class, BudgetBuddyDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .BudgetBuddyDAO();
    }

    private void getPrefs() {
        mSharedPreferences = this.getActivity().getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);

        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        mBudgetId = mBudget.getBudgetId();

        if(mUser == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR: Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    public void checkButton() {
        if(mRadioButtonWeekly.isChecked()){
            mBudgetBuddyDAO.updateBudgetPayCheckFrequency(7, mBudgetId);
        } else if(mRadioButtonBiWeekly.isChecked()){
            mBudgetBuddyDAO.updateBudgetPayCheckFrequency(14, mBudgetId);
        } else if(mRadioButtonMonthly.isChecked()){
            mBudgetBuddyDAO.updateBudgetPayCheckFrequency(30, mBudgetId);
        }
    }

    private void updatePaydayRadioGroupsToShow(){
        if(mBudget.getPaycheckFrequency() == 0){
            mPayDayDisplay.setVisibility(View.INVISIBLE);
            mWeekBasedRadioGroup.setVisibility(View.INVISIBLE);
            mMonthBasedRadioGroup.setVisibility(View.INVISIBLE);
            next = true;
            mSaveButton.setText("NEXT");
        } else if(mBudget.getPaycheckFrequency() == 7 || mBudget.getPaycheckFrequency() == 14){
            mPayDayDisplay.setVisibility(View.VISIBLE);
            mWeekBasedRadioGroup.setVisibility(View.VISIBLE);
            mMonthBasedRadioGroup.setVisibility(View.INVISIBLE);
            mSaveButton.setText("SAVE");
        } else if(mBudget.getPaycheckFrequency() == 30){
            mPayDayDisplay.setVisibility(View.VISIBLE);
            mMonthBasedRadioGroup.setVisibility(View.VISIBLE);
            mWeekBasedRadioGroup.setVisibility(View.INVISIBLE);
            mSaveButton.setText("SAVE");
        }
    }

//    private void reloadFragment(){
////        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
//
//
//        Fragment frg = null;
//        frg = getActivity().getSupportFragmentManager().findFragmentByTag("IncomeFragment");
//        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
//    }

    private void populateCurrentInformation(){
        // Populate Income
        mCurrentIncomeDisplay.setText(getString(R.string.incomeIncomeDisplay, mBudget.getIncome()));

        // Populate Payment Frequency
        if(mBudget.getPaycheckFrequency() == 7){
            mPaymentFrequency = "Weekly";
        } else if(mBudget.getPaycheckFrequency() == 14){
            mPaymentFrequency = "Bi-Weekly";
        } else if(mBudget.getPaycheckFrequency() == 30){
            mPaymentFrequency = "Monthly";
        } else {
            mPaymentFrequency = "Please provide below...";
        }

        mCurrentPaymentFrequencyDisplay.setText(getString(R.string.incomePaymentFrequencyDisplay, mPaymentFrequency.toString()));

        // Populate Next Payday

    }

    private void grabLatestUserInfo(){
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        mBudgetId = mBudget.getBudgetId();
    }

    private void updateUserInfo(){
        // Update Income (if changed)
        if(!mIncomeField.getText().toString().equals("")){
            String income = String.valueOf(mIncomeField.getText());
            mBudgetBuddyDAO.updateBudgetIncomeByUserId(income, mUserId);
        }

        // Update Payment Frequency (if changed)
        checkButton();

        // Update Payday (if changed)

        // Populate this new info on screen
        populateCurrentInformation();
    }

    private void setIncomeField(){
        mEnterIncomePromptDisplay.setText(getString(R.string.incomeIncomeInputPrompt, mPaymentFrequency));
    }

    private void openDialog(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this.getActivity().getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putInt(MONTH_PAYDAY_INT_KEY, month);
                editor.putInt(DAY_PAYDAY_INT_KEY, day);
                editor.putInt(YEAR_PAYDAY_INT_KEY, year);
                editor.apply();

                mNextPayDayMonth = month;
                mNextPaydayDay = day;
                mNextPaydayYear = year;
            }
        }, year, month, day);

        mDatePickerDialog.show();
    }
}