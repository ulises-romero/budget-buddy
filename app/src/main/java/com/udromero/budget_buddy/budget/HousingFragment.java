package com.udromero.budget_buddy.budget;

import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;
import static com.udromero.budget_buddy.Constants.nullString;
import static com.udromero.budget_buddy.Constants.zeroString;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udromero.budget_buddy.R;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.ListConverter;
import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Housing;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;
import com.udromero.budget_buddy.recyclerView.AdditionalSubCategoryAdapter;
import com.udromero.budget_buddy.recyclerView.AdditionalSubCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class HousingFragment extends Fragment {

    // Standard
    SharedPreferences mSharedPreferences;
    BudgetBuddyDAO mBudgetBuddyDAO;
    // Page Components
    Button mRefreshButton;
    TextView mRangeDisplay;
    TextView mCalculatedRangeDisplay;
    TextView mErrorDisplay;
    TextView mOtherErrorDisplay;
    Button mSaveButton;
    TextView mRangeCheckDisplay;
    Button mRangeColorButton;
    TextView mTotalDisplay;
    TextView mTestDisplay;
    EditText mOtherTitleInput;
    EditText mOtherPlannedAmountInput;
    CheckBox mOtherRecurring;
    Button mAddOtherButton;
    Button mUpdateButton;
    Button mDeleteButton;
    private RecyclerView mRecyclerView;
    private AdditionalSubCategoryAdapter.RecylcerViewClickListener mListener;
    private ArrayList<AdditionalSubCategoryModel> mAdditionalSubCategories;
    private String titleToUpdateOn = "";
    private int indexToUpdateOn;
    AdditionalSubCategoryAdapter mAdapter;

    // Standard Other global variables
    User mUser;
    int mUserId;
    Budget mBudget;
    String mOtherExpenses = "";
    String mTotalPlanned = zeroString;
    String mTotalRecurring = zeroString;

    // Unique Parts
    // CHANGE THIS, got each sub cat create edit text and checkbox
    EditText mMortgageRentInputEditText;
    CheckBox mMortgageRentRecurringCheckBox;

    EditText mWaterInputEditText;
    CheckBox mWaterRecurringCheckBox;

    EditText mGasInputEditText;
    CheckBox mGasRecurringCheckBox;

    EditText mElectricityInputEditText;
    CheckBox mElectricityRecurringCheckBox;

    EditText mCableInternetInputEditText;
    CheckBox mCableInternetGasRecurringCheckBox;

    // Unique Other Global Variables
    // CHANGE THIS, modify budget cat object class and budget range percent values
    Housing mBudgetCategory;
    double lowPercent = .25;
    double highPercent = .35;

    // create planned amount var for each sub cat
    String mMortgageRentPlanned = zeroString;
    String mWaterPlanned = zeroString;
    String mGasPlanned = zeroString;
    String mElectricityPlanned = zeroString;
    String mCableInternetPlanned = zeroString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_housing, container, false);

        // Standard
        mRefreshButton = view.findViewById(R.id.housing_RefreshButton);
        mRangeDisplay = view.findViewById(R.id.housing_IncomeTotalPercentageRangeDisplay);
        mCalculatedRangeDisplay = view.findViewById(R.id.housing_UserIncomeCalculatedRangeDisplay);
        mErrorDisplay = view.findViewById(R.id.housing_ErrorDisplay);
        mRangeCheckDisplay = view.findViewById(R.id.housing_RangeCheckDisplay);
        mRangeColorButton = view.findViewById(R.id.housing_RangeButton);
        mTotalDisplay = view.findViewById(R.id.housing_TotalDisplay);
        mOtherErrorDisplay = view.findViewById(R.id.housing_OtherErrorDisplay);
        mTestDisplay = view.findViewById(R.id.housing_TestDisplay);
        mSaveButton = view.findViewById(R.id.housing_SaveButton);
        mOtherTitleInput = view.findViewById(R.id.housing_OtherTitleInputEditText);
        mOtherPlannedAmountInput = view.findViewById(R.id.housing_OtherPlannedInputEditText);
        mOtherRecurring = view.findViewById(R.id.housing_OtherRecurringCheckBox);
        mAddOtherButton = view.findViewById(R.id.housing_AddButton);
        mUpdateButton = view.findViewById(R.id.housing_UpdateButton);
        mDeleteButton = view.findViewById(R.id.housing_DeleteButton);
        mRecyclerView = view.findViewById(R.id.housing_OtherRecyclerView);

        // CHANGE THIS, bind all sub cats edit texts and check boxes
        mMortgageRentInputEditText = view.findViewById(R.id.housing_MortgageRentPlannedInputEditText);
        mMortgageRentRecurringCheckBox = view.findViewById(R.id.housing_MortgageRentRecurringCheckBox);

        mWaterInputEditText = view.findViewById(R.id.housing_WaterPlannedInputEditText);
        mWaterRecurringCheckBox = view.findViewById(R.id.housing_WaterRecurringCheckBox);

        mGasInputEditText = view.findViewById(R.id.housing_GasPlannedInputEditText);
        mGasRecurringCheckBox = view.findViewById(R.id.housing_GasRecurringCheckBox);

        mElectricityInputEditText = view.findViewById(R.id.housing_ElectricityPlannedInputEditText);
        mElectricityRecurringCheckBox = view.findViewById(R.id.housing_ElectricityRecurringCheckBox);

        mCableInternetInputEditText = view.findViewById(R.id.housing_CableInternetPlannedInputEditText);
        mCableInternetGasRecurringCheckBox = view.findViewById(R.id.housing_CableInternetRecurringCheckBox);

        // Get database and shared preferences
        getDataBase();
        getPrefs();

        // Instantiate sub-category list
        mAdditionalSubCategories = new ArrayList<>();

        // Recycler View Adapter
        setOnClickListener();
        mAdapter = new AdditionalSubCategoryAdapter(mAdditionalSubCategories, mListener);
        setAdapter();

        if(checkForValidIncome()){
            updateCalculatedRangeDisplay(); // Unique: Change Percent Values

            populateScreen(); // Unique: Add all relevant subCat value inputs

            updateRecyclerView(true);
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkForValidIncome()){
                    return;
                }



                pullValuesIntoDatabase();
                populateScreen();
                populateSummarySection();
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

                setTestDisplay();
            }
        });

        mAddOtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkEmptyOtherFields()){
                    if(pullOtherValuesIntoDatabase()){
                        grabLatestUserInfo();
                        populateScreen();
                        populateSummarySection();
                        updateRecyclerView(false);
                        mOtherErrorDisplay.setText("");
                    }
                } else {
                    mOtherErrorDisplay.setText("ERROR: Missing some or all required fields.");
                }
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkEmptyOtherFields()){
                    mAddOtherButton.setVisibility(View.VISIBLE);
                    mUpdateButton.setVisibility(View.INVISIBLE);
                    mDeleteButton.setVisibility(View.INVISIBLE);
                    mOtherErrorDisplay.setText("");
                    updateOtherValues();
                    updateRecyclerView(false);
                } else {
                    mOtherErrorDisplay.setText("ERROR: Missing required fields.");
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mOtherTitleInput.getText().toString().equals("")){
                    mAddOtherButton.setVisibility(View.VISIBLE);
                    mUpdateButton.setVisibility(View.INVISIBLE);
                    mDeleteButton.setVisibility(View.INVISIBLE);
                    mOtherErrorDisplay.setText("");
                    deleteOtherValue();
                    updateRecyclerView(false);
                } else {
                    mOtherErrorDisplay.setText("ERROR: Missing title, now reverting...");
                    mAddOtherButton.setVisibility(View.VISIBLE);
                    mUpdateButton.setVisibility(View.INVISIBLE);
                    mDeleteButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        // CHANGE THIS, set on click listeners for all sub cat edit text inputs
        mMortgageRentInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMortgageRentInputEditText.setText("");
            }
        });

        mWaterInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaterInputEditText.setText("");
            }
        });

        mGasInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGasInputEditText.setText("");
            }
        });

        mElectricityInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mElectricityInputEditText.setText("");
            }
        });

        mCableInternetInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCableInternetInputEditText.setText("");
            }
        });

        return view;
    }

    private void updateCategoryInDatabase(){
        mBudgetBuddyDAO.update(mBudgetCategory);
    }

    // [UNIQUE]
    private void setOtherExpenses(String expenses){
        // CHANGE THIS
        mBudgetCategory.setOtherHousingExpenses(expenses);
    }

    // [UNIQUE]
    private void getAllPlannedAmounts(){
        // CHANGE THIS, add all planned amounts for each sub cat
        mMortgageRentPlanned = mBudgetCategory.getMortgageRentPlanned();
        mWaterPlanned = mBudgetCategory.getWaterPlanned();
        mGasPlanned = mBudgetCategory.getNaturalGasPlanned();
        mElectricityPlanned = mBudgetCategory.getElectricityPlanned();
        mCableInternetPlanned = mBudgetCategory.getCableInternetPlanned();
    }

    // [UNIQUE]
    private String getRecurringTotal() {
        // CHANGE THIS, create one for each sub cat and set to zero
        int mortgageRecurring = 0;
        int waterRecurring = 0;
        int gasRecurring = 0;
        int electricityRecurring = 0;
        int cableRecurring = 0;

        double currRecurringTotal = 0;

        getAllPlannedAmounts();

        // CHANGE THIS, add all if else conditions
        if(mMortgageRentRecurringCheckBox.isChecked() && !mMortgageRentPlanned.equals(zeroString)){
            mortgageRecurring = 1;
            currRecurringTotal += Double.parseDouble(mMortgageRentPlanned);
        } else {
            mMortgageRentRecurringCheckBox.setChecked(false);
        }

        if(mWaterRecurringCheckBox.isChecked() && !mWaterPlanned.equals(zeroString)){
            waterRecurring = 1;
            currRecurringTotal += Double.parseDouble(mWaterPlanned);
        } else {
            mWaterRecurringCheckBox.setChecked(false);
        }

        if(mGasRecurringCheckBox.isChecked() && !mGasPlanned.equals(zeroString)){
            gasRecurring = 1;
            currRecurringTotal += Double.parseDouble(mGasPlanned);
        } else {
            mGasRecurringCheckBox.setChecked(false);
        }

        if(mElectricityRecurringCheckBox.isChecked() && !mElectricityPlanned.equals(zeroString)){
            electricityRecurring = 1;
            currRecurringTotal += Double.parseDouble(mElectricityPlanned);
        } else {
            mElectricityRecurringCheckBox.setChecked(false);
        }

        if(mCableInternetGasRecurringCheckBox.isChecked() && !mCableInternetPlanned.equals(zeroString)){
            cableRecurring = 1;
            currRecurringTotal += Double.parseDouble(mCableInternetPlanned);
        } else {
            mCableInternetGasRecurringCheckBox.setChecked(false);
        }

        double recurringOtherTotal = getOtherSubCategoriesRecurringTotal();
        if(currRecurringTotal != currRecurringTotal + recurringOtherTotal){
            currRecurringTotal += recurringOtherTotal;
        }

        // CHANGE THIS, set all recurring
        mBudgetCategory.setMortgageRentRecurring(mortgageRecurring);
        mBudgetCategory.setWaterRecurring(waterRecurring);
        mBudgetCategory.setNaturalGasRecurring(gasRecurring);
        mBudgetCategory.setElectricityRecurring(electricityRecurring);
        mBudgetCategory.setCableInternetRecurring(cableRecurring);

        return String.valueOf(currRecurringTotal);
    }

    // [UNIQUE]
    private void getPrefs() {
        mSharedPreferences = this.getActivity().getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);

        if(mUser == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NO USER): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);

        if(mBudget == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NO BUDGET): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        // CHANGE THIS, use proper getter method
        mBudgetCategory = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);

        getOtherExpenses();

        if(mBudgetCategory == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NULL TABLE): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        setTestDisplay();
    }

    // [UNIQUE]
    private void pullValuesIntoDatabase(){
        // CHANGE THIS, add all sub cats
        String mortgage = mMortgageRentInputEditText.getText().toString().trim();
        String water = mWaterInputEditText.getText().toString().trim();
        String gas = mGasInputEditText.getText().toString().trim();
        String electricity = mElectricityInputEditText.getText().toString().trim();
        String cable = mCableInternetInputEditText.getText().toString().trim();

        // CHANGE THIS FOR EACH sub cat
        if(mortgage.equals("")){
            mortgage = zeroString;
        }

        if(water.equals(nullString)){
            water = zeroString;
        }

        if(gas.equals(nullString)){
            gas = zeroString;
        }

        if(electricity.equals(nullString)){
            electricity = zeroString;
        }

        if(cable.equals(nullString)){
            cable = zeroString;
        }

        // CHANGE THIS, for each sub cat
        mBudgetCategory.setMortgageRentPlanned(mortgage);
        mBudgetCategory.setWaterPlanned(water);
        mBudgetCategory.setNaturalGasPlanned(gas);
        mBudgetCategory.setElectricityPlanned(electricity);
        mBudgetCategory.setCableInternetPlanned(cable);

        updateCategoryInDatabase();

        getAllPlannedAmounts();

        // CHANGE THIS, for each default sub cat
        double mortgagePlanned = Double.parseDouble(mortgage);
        double waterPlanned = Double.parseDouble(water);
        double gasPlanned = Double.parseDouble(gas);
        double electricityPlanned = Double.parseDouble(electricity);
        double cablePlanned = Double.parseDouble(cable);

        getTotalPlanned();

        // CHANGE THIS add up all sub cats double amounts
        double total = mortgagePlanned + waterPlanned + gasPlanned + electricityPlanned + cablePlanned;

        // CHANGE THIS, subtract total from added up values of all sub cats planned amounts
        double totalWithoutDefaultCats = total - Double.parseDouble(mMortgageRentPlanned) - Double.parseDouble(mWaterPlanned) - Double.parseDouble(mGasPlanned) -
                Double.parseDouble(mElectricityPlanned) - Double.parseDouble(mCableInternetPlanned);

        double otherTotal = getOtherSubCategoriesTotal();

        if(totalWithoutDefaultCats != otherTotal){
            total += otherTotal;
        }

        String stringTotal = String.valueOf(total);

        String newRecurringTotal = getRecurringTotal();
        setTotalRecurring(newRecurringTotal);

        setTotalPlanned(stringTotal);

        updateCategoryInDatabase();

        grabLatestUserInfo();
    }

    // [UNIQUE]
    private void populateScreen(){
        if(!checkGivingHasBeenPopulated()){
            mSaveButton.setText("SAVE");
            mTotalDisplay.setText(getString(R.string.total, ""));
            mRangeCheckDisplay.setText(getString(R.string.rangeCheck, ""));

            // CHANGE THIS, ADD ALL INPUT EDIT TEXTS
            mMortgageRentInputEditText.setHint("Enter Amount");
            mWaterInputEditText.setHint("Enter Amount");
            mGasInputEditText.setHint("Enter Amount");
            mElectricityInputEditText.setHint("Enter Amount");
            mCableInternetInputEditText.setHint("Enter Amount");

            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.gray));
            return;
        }

        mSaveButton.setText("UPDATE");

        // CHANGE THIS
        if(mBudgetCategory.getMortgageRentRecurring() == 1){
            mMortgageRentRecurringCheckBox.setChecked(true);
        }

        if(mBudgetCategory.getWaterRecurring() == 1){
            mWaterRecurringCheckBox.setChecked(true);
        }

        if(mBudgetCategory.getNaturalGasRecurring() == 1){
            mGasRecurringCheckBox.setChecked(true);
        }

        if(mBudgetCategory.getElectricityRecurring() == 1){
            mElectricityRecurringCheckBox.setChecked(true);
        }

        if(mBudgetCategory.getCableInternetRecurring() == 1){
            mCableInternetGasRecurringCheckBox.setChecked(true);
        }

        // TODO: SET HINT TO PREVIOUS MONTHS AMOUNT
        // CHANGE THIS, ADD ALL INPUT EDIT TEXTS
        getAllPlannedAmounts();
        mMortgageRentInputEditText.setText(mMortgageRentPlanned);
        mMortgageRentInputEditText.setHint(getString(R.string.previous, "n/a"));

        mWaterInputEditText.setText(mWaterPlanned);
        mWaterInputEditText.setHint(getString(R.string.previous, "n/a"));

        mGasInputEditText.setText(mGasPlanned);
        mGasInputEditText.setHint(getString(R.string.previous, "n/a"));

        mElectricityInputEditText.setText(mElectricityPlanned);
        mElectricityInputEditText.setHint(getString(R.string.previous, "n/a"));

        mCableInternetInputEditText.setText(mCableInternetPlanned);
        mCableInternetInputEditText.setHint(getString(R.string.previous, "n/a"));

        populateSummarySection();
    }

    // [UNIQUE]
    private void grabLatestUserInfo(){
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        // CHANGE THIS
        mBudgetCategory = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);
    }

    // [UNIQUE]
    private void hideAllOnScreenItems(){
        mCalculatedRangeDisplay.setText(getString(R.string.givingCalculatedRangeDisplay, "0.00", "0.00"));

        // CHANGE THIS, use the set hint calls in the other function to do this
        mMortgageRentInputEditText.setHint(getString(R.string.previous, "n/a"));
        mWaterInputEditText.setHint(getString(R.string.previous, "n/a"));
        mGasInputEditText.setHint(getString(R.string.previous, "n/a"));
        mElectricityInputEditText.setHint(getString(R.string.previous, "n/a"));
        mCableInternetInputEditText.setHint(getString(R.string.previous, "n/a"));

        mRangeCheckDisplay.setText(getString(R.string.rangeCheck, ""));
        mTotalDisplay.setText(getString(R.string.total, ""));
    }

    private void getDataBase(){
        mBudgetBuddyDAO = Room.databaseBuilder(this.getActivity().getApplicationContext(), BudgetBuddyDatabase.class, BudgetBuddyDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .BudgetBuddyDAO();
    }


    private boolean checkForValidIncome(){
        if(mBudget.getIncome().equals(zeroString)){
            mErrorDisplay.setText(getString(R.string.givingErrorPrompt));
            hideAllOnScreenItems();
            return false;
        }

        mErrorDisplay.setText("");
        return true;
    }

    private boolean checkGivingHasBeenPopulated(){
        getTotalPlanned();
        if(mTotalPlanned.equals("0")){
            return false;
        }

        return true;
    }



    private void updateCalculatedRangeDisplay(){
        grabLatestUserInfo();
        if(mBudget.getIncome().equals(zeroString)){
            hideAllOnScreenItems();
            mErrorDisplay.setText(R.string.missingIncomeWarning);
        } else {
            String currIncome = mBudget.getIncome();
            double income = Double.parseDouble(currIncome);
            double low = income * lowPercent;
            double high = income * highPercent;

            mCalculatedRangeDisplay.setText(getString(R.string.givingCalculatedRangeDisplay, String.valueOf(low), String.valueOf(high)));
        }
    }



    private void populateSummarySection(){
        getTotalPlanned();
        mTotalDisplay.setText(getString(R.string.total, mTotalPlanned));
        String rangeCheck = calculateRangeCheck();
        mRangeCheckDisplay.setText(getString(R.string.rangeCheck, rangeCheck));
    }

    private String calculateRangeCheck() {
        grabLatestUserInfo();
        getTotalPlanned();

        double total = Double.parseDouble(mTotalPlanned);

        String stringIncome = mBudget.getIncome();
        double income = Double.parseDouble(stringIncome);

        double lower = income * lowPercent;
        double upper = income * highPercent;
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
            return "Over budget!";
        }

        return "ERROR...";
    }

    private void setOnClickListener() {
        mListener = new AdditionalSubCategoryAdapter.RecylcerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                mOtherTitleInput.setText(mAdditionalSubCategories.get(position).getTitle());
                mOtherPlannedAmountInput.setText(mAdditionalSubCategories.get(position).getPlannedAmount());
                if(mAdditionalSubCategories.get(position).getRecurring().equals("[r]")){
                    mOtherRecurring.setChecked(true);
                } else {
                    mOtherRecurring.setChecked(false);
                }

                titleToUpdateOn = mAdditionalSubCategories.get(position).getTitle();
                indexToUpdateOn = position;
                mAddOtherButton.setVisibility(View.INVISIBLE);
                mUpdateButton.setVisibility(View.VISIBLE);
                mDeleteButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void setAdapter() {
        setOnClickListener();
        mAdapter = new AdditionalSubCategoryAdapter(mAdditionalSubCategories, mListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void updateRecyclerView(boolean fromMenu){
        grabLatestUserInfo();
        getOtherExpenses();

        if(mOtherExpenses.equals("")){
            return;
        }

        List<String> currOtherValues = ListConverter.convertStringToList(mOtherExpenses);
        getOtherSubCategories(currOtherValues, fromMenu);
        cleanListOfDuplicates();
        mAdapter.updateData(mAdditionalSubCategories);
        mAdapter.notifyDataSetChanged();

        setAdapter();
    }

    private void getOtherSubCategories(List<String> currOtherValues, boolean fromMenu){
        if(currOtherValues.size() < 3){
            return;
        }

        int startIndex;
        if(fromMenu){
            startIndex = 0;
        } else {
            startIndex = currOtherValues.size() - 3;
        }

        for(int i = startIndex; i < currOtherValues.size(); i+=3){
            String r = "[]";
            if(currOtherValues.get(i + 2).equals("[r]")){
                r = "[r]";
            }

            AdditionalSubCategoryModel currSubCat = new AdditionalSubCategoryModel(currOtherValues.get(i), currOtherValues.get(i + 1), r);

            if(!mAdditionalSubCategories.contains(currSubCat)){
                mAdditionalSubCategories.add(currSubCat);
            }
        }
    }

    private void cleanListOfDuplicates() {
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < mAdditionalSubCategories.size(); i++){
            AdditionalSubCategoryModel currSubCat = mAdditionalSubCategories.get(i);
            if(i == 0){
                titles.add(currSubCat.getTitle());
            } else {
                if(titles.contains(currSubCat.getTitle())){
                    mAdditionalSubCategories.remove(i);
                } else {
                    titles.add(currSubCat.getTitle());
                }
            }
        }
    }

    private double getOtherSubCategoriesTotal(){
        getOtherExpenses();

        List<String> currOtherValues = ListConverter.convertStringToList(mOtherExpenses);
        double otherTotal = 0;

        if(mOtherExpenses.equals("") || currOtherValues.size() == 1){
            return otherTotal;
        }

        for(int i = 0; i < currOtherValues.size(); i+=3){
            otherTotal += Double.parseDouble(currOtherValues.get(i + 1));
        }

        return otherTotal;
    }


    private double getOtherSubCategoriesRecurringTotal(){
        grabLatestUserInfo();
        getOtherExpenses();

        List<String> currOtherValues = ListConverter.convertStringToList(mOtherExpenses);
        double recurringTotal = 0;

        if(mOtherExpenses.equals("") || currOtherValues.size() == 1){
            return recurringTotal;
        }

        for(int i = 0; i < currOtherValues.size(); i+=3){
            if(currOtherValues.get(i + 2).equals("[r]")){
                recurringTotal += Double.parseDouble(currOtherValues.get(i + 1));
            }
        }

        return recurringTotal;
    }

    private boolean checkEmptyOtherFields() {
        String title = mOtherTitleInput.getText().toString().trim();
        String plannedAmount = mOtherPlannedAmountInput.getText().toString().trim();
        return title.equals(nullString) || plannedAmount.equals(nullString);
    }

    private boolean pullOtherValuesIntoDatabase() {
        String title = mOtherTitleInput.getText().toString().trim();
        if(title.contains(",")){
            mOtherErrorDisplay.setText("ERROR: Please do not use the comma character in your titles.");
            return false;
        } else {
            mOtherErrorDisplay.setText("");
        }
        String plannedAmount = mOtherPlannedAmountInput.getText().toString().trim();
        boolean recurringBoolean = mOtherRecurring.isChecked();

        String recurring;

        if(recurringBoolean){
            recurring = "[r]";
        } else {
            recurring = "[]";
        }

        grabLatestUserInfo();
        getOtherExpenses();
        String otherExpenses = mOtherExpenses;

        if(!checkForDuplicateOtherEntry(title)){
            if(otherExpenses.equals(nullString)){
                otherExpenses = title + "-" + plannedAmount + "-" + recurring;
            } else {
                otherExpenses += "," + title + "-" + plannedAmount + "-" + recurring;
            }

            getTotalPlanned();
            double currTotal = Double.parseDouble(mTotalPlanned);

            currTotal += Double.parseDouble(plannedAmount);

            setTotalPlanned(String.valueOf(currTotal));

            if(recurringBoolean){
                getTotalRecurring();
                double currRecurringTotal = Double.parseDouble(mTotalRecurring);

                currRecurringTotal += Double.parseDouble(plannedAmount);

                setTotalRecurring(String.valueOf(currRecurringTotal));
            }

            mOtherErrorDisplay.setText("");

            setOtherExpenses(otherExpenses);

            updateCategoryInDatabase();

            return true;
        } else {
            mOtherErrorDisplay.setText("ERROR: A sub-category with that title already exists.");
            return false;
        }
    }

    private boolean checkForDuplicateOtherEntry(String title){
        grabLatestUserInfo();
        getOtherExpenses();

        List<String> currOtherValues = ListConverter.convertStringToList(mOtherExpenses);

        if(currOtherValues.contains(title)){
            mOtherErrorDisplay.setText("ERROR: An entry with that title already exists.");
            return true;
        }

        mOtherErrorDisplay.setText("");
        return false;
    }

    private void updateOtherValues(){
        String title = mOtherTitleInput.getText().toString().trim();

        if(!title.equals(titleToUpdateOn)){
            if(checkForDuplicateOtherEntry(title)){
                return;
            }
        }

        String plannedAmount = mOtherPlannedAmountInput.getText().toString().trim();
        String recurring = "[]";

        if(mOtherRecurring.isChecked()){
            recurring = "[r]";
        }

        AdditionalSubCategoryModel currSubCat = mAdditionalSubCategories.get(indexToUpdateOn);
        if(currSubCat.getTitle().equals(titleToUpdateOn)){
            currSubCat.setTitle(title);
            double oldPA = Double.parseDouble(currSubCat.getPlannedAmount());
            double newPA = Double.parseDouble(plannedAmount);
            currSubCat.setPlannedAmount(plannedAmount);

            getTotalPlanned();
            double currTotal = Double.parseDouble(mTotalPlanned);
            currTotal = currTotal - oldPA + newPA;
            setTotalPlanned(String.valueOf(currTotal));

            getTotalRecurring();
            double recurringTotal = Double.parseDouble(mTotalRecurring);
            if(currSubCat.getRecurring().equals("[r]") && recurring.equals("[r]")){
                recurringTotal = (recurringTotal - oldPA) + newPA;
            } else if (currSubCat.getRecurring().equals("[r]") && recurring.equals("[]")){
                recurringTotal = recurringTotal - oldPA;
            } else if(currSubCat.getRecurring().equals("[]") && recurring.equals("[r]")){
                recurringTotal += newPA;
            }

            currSubCat.setRecurring(recurring);
            setTotalRecurring(String.valueOf(recurringTotal));
        }

        mAdapter.updateData(mAdditionalSubCategories);
        setOtherExpenses(ListConverter.convertListToString(mAdditionalSubCategories));
        updateCategoryInDatabase();
        populateSummarySection();

        mOtherTitleInput.setText("");
        mOtherPlannedAmountInput.setText("");
        mOtherRecurring.setChecked(false);
    }

    private void deleteOtherValue() {
        String title = mOtherTitleInput.getText().toString().trim();

        for(int i = 0; i < mAdditionalSubCategories.size(); i++){
            AdditionalSubCategoryModel currSubCat = mAdditionalSubCategories.get(i);
            if(currSubCat.getTitle().equals(title)){
                double spa = Double.parseDouble(currSubCat.getPlannedAmount());

                getTotalPlanned();
                double currTotal = Double.parseDouble(mTotalPlanned);

                currTotal = currTotal - spa;

                setTotalPlanned(String.valueOf(currTotal));

                if(currSubCat.getRecurring().equals("[r]")){
                    getTotalRecurring();
                    double recurringTotal = Double.parseDouble(mTotalRecurring);

                    recurringTotal = recurringTotal - spa;

                    setTotalRecurring(String.valueOf(recurringTotal));
                }
                mAdditionalSubCategories.remove(i);
                break;
            }
        }


        setOtherExpenses(ListConverter.convertListToString(mAdditionalSubCategories));

        updateCategoryInDatabase();

        populateSummarySection();
        mOtherTitleInput.setText("");
        mOtherPlannedAmountInput.setText("");
        mOtherRecurring.setChecked(false);
    }

    private void setTestDisplay(){
        mTestDisplay.setText(mBudgetCategory.toString());
    }

    private void getTotalPlanned(){
        mTotalPlanned = mBudgetCategory.getTotalPlanned();
    }

    private void setTotalPlanned(String totalPlanned){
        mBudgetCategory.setTotalPlanned(totalPlanned);
    }

    private void getTotalRecurring(){
        mTotalRecurring = mBudgetCategory.getTotalRecurring();
    }

    private void setTotalRecurring(String total){
        mBudgetCategory.setTotalRecurring(total);
    }

    private void getOtherExpenses(){
        mOtherExpenses = mBudgetCategory.getOtherHousingExpenses();
    }
}