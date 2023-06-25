package com.udromero.budget_buddy;

import static com.udromero.budget_buddy.Constants.BUDGET_ID_KEY;
import static com.udromero.budget_buddy.Constants.EXPENSES_KEY;
import static com.udromero.budget_buddy.Constants.GIVING_ID_KEY;
import static com.udromero.budget_buddy.Constants.HOUSING_ID_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.SAVINGS_ID_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;
import static com.udromero.budget_buddy.Constants.nullString;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.ListConverter;
import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Debt;
import com.udromero.budget_buddy.db.entities.Food;
import com.udromero.budget_buddy.db.entities.Giving;
import com.udromero.budget_buddy.db.entities.Health;
import com.udromero.budget_buddy.db.entities.Housing;
import com.udromero.budget_buddy.db.entities.Insurance;
import com.udromero.budget_buddy.db.entities.Lifestyle;
import com.udromero.budget_buddy.db.entities.Personal;
import com.udromero.budget_buddy.db.entities.Savings;
import com.udromero.budget_buddy.db.entities.Transportation;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;
import com.udromero.budget_buddy.recyclerView.AdditionalSubCategoryAdapter;
import com.udromero.budget_buddy.recyclerView.ezExpenseRecyclerView.EzExpenseAdapter;
import com.udromero.budget_buddy.recyclerView.ezExpenseRecyclerView.EzExpenseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QuickExpenseFragment extends Fragment {

    private SharedPreferences mSharedPreferences;

    private BudgetBuddyDAO mBudgetBuddyDAO;

    private RecyclerView mRecyclerView;
    private EzExpenseAdapter.RecylcerViewClickListener mListener;
    private ArrayList<EzExpenseModel> mExpensesList;
    private EzExpenseAdapter mAdapter;

    // View Components
    TextView mPromptDisplay;

    Spinner mCategorySpinner;
    Spinner mSubCategorySpinner;

    EditText mAmountInput;
    EditText mDescriptionInput;

    Button mChargeButton;
    Button mDeleteButton;
    Button mCancelButton;

    // Other
    User mUser;
    int mUserId;

    Budget mBudget;
    int mBudgetId;

    String descToUpdateOn;
    String expenses;

    Giving mGiving; Savings mSavings; Housing mHousing; Food mFood; Transportation mTransportation; Personal mPersonal; Lifestyle mLifestyle; Health mHealth; Insurance mInsurance; Debt mDebt;
    int mGivingId; int mSavingsId; int mHousingId; int mFoodId; int mTransportationId; int mPersonalId; int mLifestyleId; int mHealthId; int mInsuranceId; int mDebtId;

    String mSelectedCat = "";
    String mSelectedSubCat = "";

    boolean userWipedExpenses;

    int currSelectedExpense = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quick_expense, container, false);

        // Page Layout Binding
        mPromptDisplay = view.findViewById(R.id.ezPromptDisplay);

        mCategorySpinner = view.findViewById(R.id.ezCategorySpinner);
        mSubCategorySpinner = view.findViewById(R.id.ezSubCatSpinner);

        mAmountInput = view.findViewById(R.id.ezExpenseAmountInputEditText);
        mDescriptionInput = view.findViewById(R.id.ezExpenseDescInputEditText);

        mChargeButton = view.findViewById(R.id.ezChargeButton);
        mDeleteButton = view.findViewById(R.id.ezDeleteButton);
        mCancelButton = view.findViewById(R.id.ezUpdateButton);

        mRecyclerView = view.findViewById(R.id.ezRecyclerView);

        // Grab room database and user shared preferences
        getDatabase();
        getPrefs();

        // Instantiate expenses list
        if (expenses.equals("")) {
            mExpensesList = new ArrayList<>();
            userWipedExpenses = false;
        } else {
            mExpensesList = ListConverter.convertExpensesStringToList(expenses);
        }


        // Set Adapter Up
        setOnClickListener();
        mAdapter = new EzExpenseAdapter(mExpensesList, mListener);
        setAdapter();

        // Populate Spinner Values
        List<String> categoryOptions = new ArrayList<>();
        categoryOptions.add("Giving");
        categoryOptions.add("Savings");
        categoryOptions.add("Housing");
        categoryOptions.add("Food");
        categoryOptions.add("Transportation");
        categoryOptions.add("Personal");
        categoryOptions.add("Lifestyle");
        categoryOptions.add("Health");
        categoryOptions.add("Insurance");
        categoryOptions.add("Debt");

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categoryOptions);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoriesAdapter);

        if (checkForValidIncome()) {
            updateRecyclerView();
        }

        mChargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkForValidIncome()){
                    return;
                } else {
                    mPromptDisplay.setText("");
                }

                if (!missingRequiredFields()) {
                    updateExpenses();
                    updateRecyclerView();
                    mPromptDisplay.setText("");
                    mAmountInput.setText("");
                    mDescriptionInput.setText("");
                    mCategorySpinner.setSelection(0);
                    mSubCategorySpinner.setSelection(0);
                } else {
                    mPromptDisplay.setText("Missing required fields.");
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!missingRequiredFields()) {
                    deleteExpense();

                    mPromptDisplay.setText("");
                    mChargeButton.setVisibility(View.VISIBLE);
                    mCancelButton.setVisibility(View.INVISIBLE);
                    mDeleteButton.setVisibility(View.INVISIBLE);
                    mAmountInput.setText("");
                    mDescriptionInput.setText("");
                    mCategorySpinner.setSelection(0);
                    mSubCategorySpinner.setSelection(0);
                } else {
                    mPromptDisplay.setText("Missing required fields.");
                }

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPromptDisplay.setText("");
                mChargeButton.setVisibility(View.VISIBLE);
                mCancelButton.setVisibility(View.INVISIBLE);
                mDeleteButton.setVisibility(View.INVISIBLE);
                mAmountInput.setText("");
                mDescriptionInput.setText("");
                mCategorySpinner.setSelection(0);
                mSubCategorySpinner.setSelection(0);
//                if (!missingRequiredFields()) {
//                    updateExpense();
//
//                    mPromptDisplay.setText("");
//                    mChargeButton.setVisibility(View.VISIBLE);
//                    mCancelButton.setVisibility(View.INVISIBLE);
//                    mDeleteButton.setVisibility(View.INVISIBLE);
//                } else {
//                    mPromptDisplay.setText("Missing required fields.");
//                }
            }
        });

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCat = (String) parent.getItemAtPosition(position);
                populateSubCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedCat = "";
            }
        });

        mSubCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSubCat = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedSubCat = "";
            }
        });

        return view;
    }

    private void getDatabase(){
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
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NO USER FOUND): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mBudgetId = mSharedPreferences.getInt(BUDGET_ID_KEY, -1);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);

        expenses = mSharedPreferences.getString(EXPENSES_KEY, "");

        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
        mGivingId = mGiving.getGivingId();

        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
        mSavingsId = mSavings.getSavingsId();

        mHousing = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);

        mFood = mBudgetBuddyDAO.getFoodExpensesByUserId(mUserId);

        mTransportation = mBudgetBuddyDAO.getTransportationExpensesByUserId(mUserId);

        mPersonal = mBudgetBuddyDAO.getPersonalExpensesByUserId(mUserId);

        mLifestyle = mBudgetBuddyDAO.getLifestyleExpensesByUserId(mUserId);

        mHealth = mBudgetBuddyDAO.getHealthExpensesByUserId(mUserId);

        mInsurance = mBudgetBuddyDAO.getInsuranceExpensesByUserId(mUserId);

        mDebt = mBudgetBuddyDAO.getDebtExpensesByUserId(mUserId);

        // mPromptDisplay.setText("Budget ID: " + mBudgetId + ", User ID: " + mUserId + ", " + mBudget.toString());
    }

    private void setOnClickListener() {
        mListener = new EzExpenseAdapter.RecylcerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(mAdapter.getExpensesList().size() < 1 || !mExpensesList.get(position).getDate().equals(" ")){
                    return;
                }
                currSelectedExpense = position;
                mAmountInput.setText(mExpensesList.get(position).getPlannedAmount());
                mDescriptionInput.setText(mExpensesList.get(position).getDescription());
                // TODO: Populate Spinner Selections
//                mCategorySpinner.setSelection();

                descToUpdateOn = mExpensesList.get(position).getDescription();
                mChargeButton.setVisibility(View.INVISIBLE);
                mCancelButton.setVisibility(View.VISIBLE);
                mDeleteButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void setAdapter() {
//        setOnClickListener();
        mAdapter = new EzExpenseAdapter(mExpensesList, mListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private boolean checkForValidIncome() {
        // grabLatestUserInfo();
        if(mBudget.getIncome().equals(nullString)){
            mPromptDisplay.setText(getString(R.string.givingErrorPrompt));
            return false;
        }

        mPromptDisplay.setText("");
        return true;
    }

    private void grabLatestUserInfo(){
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);

        mBudgetId = mSharedPreferences.getInt(BUDGET_ID_KEY, -1);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);

        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
        mGivingId = mGiving.getGivingId();

        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
        mSavingsId = mSavings.getSavingsId();
    }

    private void updateRecyclerView(){
        grabLatestUserInfo();

        // If no expenses to list, return
        if(expenses.equals("") && !userWipedExpenses){
            // Toast.makeText(getActivity().getApplicationContext(), "No expenses..returning", Toast.LENGTH_LONG).show();
            return;
        }

        mExpensesList = ListConverter.convertExpensesStringToList(expenses);
        mAdapter.updateData(mExpensesList);
        mAdapter.notifyDataSetChanged();

        setAdapter();
    }

    private boolean missingRequiredFields() {
        String amount = mAmountInput.getText().toString().trim();
        String desc = mDescriptionInput.getText().toString().trim();
        String cat = mCategorySpinner.getSelectedItem().toString();
        String subCat = mCategorySpinner.getSelectedItem().toString();

        return amount.equals(nullString) || desc.equals(nullString) || cat.equals(nullString) || subCat.equals(nullString);
    }

    private void updateExpenses() {
        String amount = mAmountInput.getText().toString().trim();
        String desc = mDescriptionInput.getText().toString().trim();
        String cat = mCategorySpinner.getSelectedItem().toString();
        String subCat = mSubCategorySpinner.getSelectedItem().toString();

        if(expenses.equals("")){
            expenses = getCurrentDate() + "- - - - " + ", -" + desc + "-" + cat + "-" + subCat + "-" + amount;
        } else {
            boolean dateExists = false;
            ArrayList<EzExpenseModel> currExpenses = ListConverter.convertExpensesStringToList(expenses);
            for(EzExpenseModel currExpense : currExpenses){
                if(currExpense.getDate().equals(getCurrentDate())){
                    dateExists = true;
                }
            }

            if(dateExists){
                expenses += ", -" + desc + "-" + cat + "-" + subCat + "-" + amount;
            } else {
                expenses += "," + getCurrentDate() + "- - - - " + ", -" + desc + "-" + cat + "-" + subCat + "-" + amount;
            }
        }

        String totalSpent = mBudget.getTotalSpent();
        double currentTotalSpent = Double.parseDouble(totalSpent);
        double currAmountCharged = Double.parseDouble(amount);
        double newTotalSpent = currAmountCharged + currentTotalSpent;

        mBudgetBuddyDAO.updateBudgetTotalSpentByUserId(String.valueOf(newTotalSpent), mUserId);

        updateTotalSpentByCategory(cat, currAmountCharged);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(EXPENSES_KEY, expenses);
        editor.apply();
    }

    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int currDay = c.get(Calendar.DAY_OF_MONTH);
        int currMonth = c.get(Calendar.MONTH);
        int currYear = c.get(Calendar.YEAR);

        return currMonth + "/" + currDay + "/" + currYear;
    }

    private void populateSubCategory() {
        // Populate Spinner Values
        List<String> subCategoryOptions = new ArrayList<>();
        ArrayAdapter<String> adapter = null;

        if(mSelectedCat.equals("Giving")){
            mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
            String givingOther = mGiving.getOtherGivingExpenses();
            subCategoryOptions.add("Church");
            subCategoryOptions.add("Charity");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(givingOther));
        }

        if(mSelectedCat.equals("Savings")){
            mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
            String savingsOther = mSavings.getOtherSavingsExpenses();
            subCategoryOptions.add("Emergency Fund");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(savingsOther));
        }

        if(mSelectedCat.equals("Housing")){
            mHousing = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);
            String housingOther = mHousing.getOtherHousingExpenses();
            subCategoryOptions.add("Mortgage/Rent");
            subCategoryOptions.add("Water");
            subCategoryOptions.add("Natural Gas");
            subCategoryOptions.add("Electricity");
            subCategoryOptions.add("Cable/Internet");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(housingOther));
        }

        if(mSelectedCat.equals("Food")){
            mFood = mBudgetBuddyDAO.getFoodExpensesByUserId(mUserId);
            String foodOther = mFood.getOtherSavingsExpenses();
            subCategoryOptions.add("Groceries");
            subCategoryOptions.add("Restaurant");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(foodOther));
        }

        if(mSelectedCat.equals("Transportation")){
            mTransportation = mBudgetBuddyDAO.getTransportationExpensesByUserId(mUserId);
            String transOther = mTransportation.getOtherTransportationExpenses();
            subCategoryOptions.add("Gas");
            subCategoryOptions.add("Maintenance");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(transOther));
        }

        if(mSelectedCat.equals("Personal")){
            mPersonal = mBudgetBuddyDAO.getPersonalExpensesByUserId(mUserId);
            String personalOther = mPersonal.getOtherPersonalExpenses();
            subCategoryOptions.add("Clothing");
            subCategoryOptions.add("Phone");
            subCategoryOptions.add("Fun Money");
            subCategoryOptions.add("Hair/Cosmetics");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(personalOther));
        }

        if(mSelectedCat.equals("Lifestyle")){
            mLifestyle = mBudgetBuddyDAO.getLifestyleExpensesByUserId(mUserId);
            String lifeOther = mLifestyle.getOtherLifestyleExpenses();
            subCategoryOptions.add("Child Care");
            subCategoryOptions.add("Pet Care");
            subCategoryOptions.add("Entertainment");
            subCategoryOptions.add("Vacation");
            subCategoryOptions.add("Education/Tuition");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(lifeOther));
        }

        if(mSelectedCat.equals("Health")){
            mHealth = mBudgetBuddyDAO.getHealthExpensesByUserId(mUserId);
            String healthOther = mHealth.getOtherHealthExpenses();
            subCategoryOptions.add("Gym");
            subCategoryOptions.add("Medicine/Vitamins");
            subCategoryOptions.add("Doctor Visits");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(healthOther));
        }

        if(mSelectedCat.equals("Insurance")){
            mInsurance = mBudgetBuddyDAO.getInsuranceExpensesByUserId(mUserId);
            String insuranceOther = mInsurance.getOtherInsuranceExpenses();
            subCategoryOptions.add("Health Insurance");
            subCategoryOptions.add("Life Insurance");
            subCategoryOptions.add("Auto Insurance");
            subCategoryOptions.add("Homeowner/Renter");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(insuranceOther));
        }

        if(mSelectedCat.equals("Debt")){
            mDebt = mBudgetBuddyDAO.getDebtExpensesByUserId(mUserId);
            String other = mDebt.getOtherDebtExpenses();
            subCategoryOptions.add("Car Payment");
            subCategoryOptions.add("Credit Card");
            subCategoryOptions.add("Student Loan");
            subCategoryOptions.add("Medical Bill");
            subCategoryOptions.add("Personal Loan");
            subCategoryOptions.addAll(ListConverter.convertOtherExpensesToSubCatTitlesList(other));
        }

        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, subCategoryOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubCategorySpinner.setAdapter(adapter);
    }

    private void updateTotalSpentByCategory(String category, double amount) {
        grabLatestUserInfo();
        double currentTotalSpent;

        if(category.equals("Giving")){
            String currTotal = mGiving.getTotalSpent();
            currentTotalSpent = Double.parseDouble(currTotal);
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mGiving.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mGiving);
        }

        if(category.equals("Savings")){
            currentTotalSpent = Double.parseDouble(mSavings.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mSavings.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mSavings);
        }

        if(category.equals("Housing")){
            currentTotalSpent = Double.parseDouble(mHousing.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mHousing.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mHousing);
        }

        if(category.equals("Food")){
            currentTotalSpent = Double.parseDouble(mFood.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mFood.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mFood);
        }

        if(category.equals("Transportation")){
            currentTotalSpent = Double.parseDouble(mTransportation.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mTransportation.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mTransportation);
        }

        if(category.equals("Personal")){
            currentTotalSpent = Double.parseDouble(mPersonal.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mPersonal.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mPersonal);
        }

        if(category.equals("Lifestyle")){
            currentTotalSpent = Double.parseDouble(mLifestyle.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mLifestyle.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mLifestyle);
        }

        if(category.equals("Health")){
            currentTotalSpent = Double.parseDouble(mHealth.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mHealth.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mHealth);
        }

        if(category.equals("Insurance")){
            currentTotalSpent = Double.parseDouble(mInsurance.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mInsurance.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mInsurance);
        }

        if(category.equals("Debt")){
            currentTotalSpent = Double.parseDouble(mDebt.getTotalSpent());
            currentTotalSpent += amount;
            String newTotalSpent = String.valueOf(currentTotalSpent);

            mDebt.setTotalSpent(newTotalSpent);
            mBudgetBuddyDAO.update(mDebt);
        }

        grabLatestUserInfo();
    }

    private void deleteExpense() {
        if(currSelectedExpense == -1){
            return;
        }

        double negativeValue = Double.parseDouble(mExpensesList.get(currSelectedExpense).getPlannedAmount());
        negativeValue = -negativeValue;

        double currentTotalSpent = Double.parseDouble(mBudget.getTotalSpent());
        double newTotalSpent = currentTotalSpent + negativeValue;
        mBudget.setTotalSpent(String.valueOf(newTotalSpent));
        mBudgetBuddyDAO.update(mBudget);

        updateTotalSpentByCategory(mExpensesList.get(currSelectedExpense).getCategory(), negativeValue);

        mExpensesList.remove(currSelectedExpense);

        if(mExpensesList.size() == 1){
            expenses = "";
            userWipedExpenses = true;
        } else {
            userWipedExpenses = false;
            expenses = ListConverter.convertExpenseListToString(mExpensesList);
        }

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(EXPENSES_KEY, expenses);
        editor.apply();

        mAmountInput.setText("");
        mDescriptionInput.setText("");


        updateRecyclerView();
    }


    private void updateExpense() {
        if(currSelectedExpense == -1){
            return;
        }

        String amount = mAmountInput.getText().toString().trim();
        String desc = mDescriptionInput.getText().toString().trim();
        String cat = mCategorySpinner.getSelectedItem().toString().trim();
        String subCat = mSubCategorySpinner.getSelectedItem().toString().trim();

        double oldAmount = Double.parseDouble(mExpensesList.get(currSelectedExpense).getPlannedAmount());
        double newAmount = Double.parseDouble(amount);
        double newAmountToAdd = newAmount - oldAmount;

        double currentTotalSpent = Double.parseDouble(mBudget.getTotalSpent());
//        double newTotalSpent = currentTotalSpent + newAmountToAdd;
        double newTotalSpent = (currentTotalSpent - oldAmount) + newAmount;

        mBudget.setTotalSpent(String.valueOf(newTotalSpent));
        mBudgetBuddyDAO.update(mBudget);

        String oldCat = mExpensesList.get(currSelectedExpense).getCategory();
        if(oldCat.equals(cat)){
            updateTotalSpentByCategory(cat, newAmountToAdd);
        } else {
            // TODO: test this case where change in category
            updateTotalSpentByCategory(oldCat, -(oldAmount));
            updateTotalSpentByCategory(cat, newAmount);
        }


        EzExpenseModel newExpense = new EzExpenseModel(" ", desc, cat, subCat, amount);
        mExpensesList.set(currSelectedExpense, newExpense);

        expenses = ListConverter.convertExpenseListToString(mExpensesList);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(EXPENSES_KEY, expenses);
        editor.apply();

        mAmountInput.setText("");
        mDescriptionInput.setText("");

        updateRecyclerView();
    }
}