package com.udromero.budget_buddy.budget;

import static com.udromero.budget_buddy.Constants.BUDGET_ID_KEY;
import static com.udromero.budget_buddy.Constants.GIVING;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.TAB_INDEX_KEY;
import static com.udromero.budget_buddy.Constants.TAB_NAME_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;
import static com.udromero.budget_buddy.Constants.nullString;
import static com.udromero.budget_buddy.Constants.zeroString;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.udromero.budget_buddy.MyBudgetFragment;
import com.udromero.budget_buddy.R;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.ListConverter;
import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Giving;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;
import com.udromero.budget_buddy.recyclerView.AdditionalSubCategoryAdapter;
import com.udromero.budget_buddy.recyclerView.AdditionalSubCategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GivingFragment extends Fragment {

    SharedPreferences mSharedPreferences;

    BudgetBuddyDAO mBudgetBuddyDAO;

    // Page Components
    ImageView mPageLogo;

    Button mRefreshButton;

    TextView mRangeDisplay;
    TextView mCalculatedRangeDisplay;

    TextView mErrorDisplay;

    TextView mPlannedDisplay;
    TextView mRecurringDisplay;

    Button mSaveButton;

    TextView mRangeCheckDisplay;
    Button mRangeColorButton;
    TextView mTotalDisplay;

    TextView mTestDisplay;

    Button mAddOtherButton;
    Button mUpdateButton;
    Button mDeleteButton;

    EditText mOtherTitleInput, mOtherPlannedAmountInput;
    CheckBox mOtherRecurring;

    TextView mOtherErrorDisplay;

    // Other global variables
    User mUser;
    int mUserId;

    Budget mBudget;
    int mBudgetId;

    private RecyclerView mRecyclerView;
    private AdditionalSubCategoryAdapter.RecylcerViewClickListener mListener;
    private ArrayList<AdditionalSubCategoryModel> mAdditionalSubCategories;
    private String titleToUpdateOn = "";

    AdditionalSubCategoryAdapter mAdapter;

    // Unique View Components & Variables
    TextView mChurchPlannedPromptDisplay;
    EditText mChurchPlannedInputEditText;
    CheckBox mChurchRecurringCheckBox;

    TextView mCharityPlannedPromptDisplay;
    EditText mCharityPlannedInputEditText;
    CheckBox mCharityRecurringCheckbox;

    Giving mGiving = null;
    int mGivingId;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_giving, container, false);

        // Page binding (in order from top to bottom)s
        mPageLogo = view.findViewById(R.id.givingPageLogo);
        mRefreshButton = view.findViewById(R.id.givingRefreshButton);

        mRangeDisplay = view.findViewById(R.id.givingIncomeTotalPercentageRangeDisplay);
        mCalculatedRangeDisplay = view.findViewById(R.id.givingUserIncomeCalculatedRangeDisplay);
        mErrorDisplay = view.findViewById(R.id.givingErrorDisplay);
        mPlannedDisplay = view.findViewById(R.id.givingPlannedAmountDisplay);
        mRecurringDisplay = view.findViewById(R.id.givingRecurringDisplay);

        mSaveButton = view.findViewById(R.id.givingSaveButton);

        mRangeCheckDisplay = view.findViewById(R.id.givingRangeCheckDisplay);
        mRangeColorButton = view.findViewById(R.id.givingRangeButton);
        mTotalDisplay = view.findViewById(R.id.givingTotalDisplay);

        mTestDisplay = view.findViewById(R.id.givingTestDisplay);

        mOtherTitleInput = view.findViewById(R.id.givingOtherTitleInputEditText);
        mOtherPlannedAmountInput = view.findViewById(R.id.givingOtherPlannedInputEditText);
        mOtherRecurring = view.findViewById(R.id.givingOtherRecurringCheckBox);

        mAddOtherButton = view.findViewById(R.id.givingAddButton);
        mUpdateButton = view.findViewById(R.id.givingUpdateButton);
        mDeleteButton = view.findViewById(R.id.givingDeleteButton);

        mOtherErrorDisplay = view.findViewById(R.id.givingOtherErrorDisplay);

        mRecyclerView = view.findViewById(R.id.givingOtherRecyclerView);

        // Unique Binding Components
        mChurchPlannedPromptDisplay = view.findViewById(R.id.givingChurchDisplay);
        mChurchPlannedInputEditText = view.findViewById(R.id.givingChurchPlannedInputEditText);
        mChurchRecurringCheckBox = view.findViewById(R.id.givingChurchRecurringCheckBox);

        mCharityPlannedPromptDisplay = view.findViewById(R.id.givingCharityDisplay);
        mCharityPlannedInputEditText = view.findViewById(R.id.givingCharityPlannedInputEditText);
        mCharityRecurringCheckbox = view.findViewById(R.id.givingCharityRecurringCheckBox);

        // Get database and shared preferences
        getDataBase();
        getPrefs();

        // Instantiate sub-category list
        mAdditionalSubCategories = new ArrayList<>();

        // Recycler View Adapter
        setOnClickListener();
        mAdapter = new AdditionalSubCategoryAdapter(mAdditionalSubCategories, mListener);
        setAdapter();

        // Check user has entered income amount
        if(checkForValidIncome()){
            updateCalculatedRangeDisplay();

            populateScreen();

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

                mTestDisplay.setText("Budget ID: " + mBudgetId + ", User ID: " + mUserId + ", " + mGiving.toString());
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

                mTestDisplay.setText("Budget ID: " + mBudgetId + ", User ID: " + mUserId + ", " + mGiving.toString());
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

                mTestDisplay.setText("Budget ID: " + mBudgetId + ", User ID: " + mUserId + ", " + mGiving.toString());
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

        // Unique OnClickListener's
        mChurchPlannedInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChurchPlannedInputEditText.setText("");
            }
        });

        mCharityPlannedInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCharityPlannedInputEditText.setText("");
            }
        });

        return view;
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

        for(int i = 0; i < mAdditionalSubCategories.size(); i++){
            AdditionalSubCategoryModel currSubCat = mAdditionalSubCategories.get(i);
            if(currSubCat.getTitle().equals(titleToUpdateOn)){
                currSubCat.setTitle(title);
                double oldPA = Double.parseDouble(currSubCat.getPlannedAmount());
                double newPA = Double.parseDouble(plannedAmount);
                currSubCat.setPlannedAmount(plannedAmount);
                currSubCat.setRecurring(recurring);

                double currTotal = Double.parseDouble(mGiving.getTotalPlanned());
                currTotal = currTotal - oldPA + newPA;
                mGiving.setTotalPlanned(String.valueOf(currTotal));

                if(currSubCat.getRecurring().equals("[r]")){
                    double recurringTotal = Double.parseDouble(mGiving.getTotalRecurring());
                    recurringTotal = recurringTotal - oldPA + newPA;
                    mGiving.setTotalRecurring(String.valueOf(recurringTotal));
                }

            }
        }

        mGiving.setOtherGivingExpenses(ListConverter.convertListToString(mAdditionalSubCategories));
        mBudgetBuddyDAO.update(mGiving);
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
                double currTotal = Double.parseDouble(mGiving.getTotalPlanned());
                currTotal = currTotal - spa;
                mGiving.setTotalPlanned(String.valueOf(currTotal));

                if(currSubCat.getRecurring().equals("[r]")){
                    double recurringTotal = Double.parseDouble(mGiving.getTotalRecurring());
                    recurringTotal = recurringTotal - spa;
                    mGiving.setTotalRecurring(String.valueOf(recurringTotal));
                }
                mAdditionalSubCategories.remove(i);
                break;
            }
        }

        mGiving.setOtherGivingExpenses(ListConverter.convertListToString(mAdditionalSubCategories));
        mBudgetBuddyDAO.update(mGiving);
        populateSummarySection();

        mOtherTitleInput.setText("");
        mOtherPlannedAmountInput.setText("");
        mOtherRecurring.setChecked(false);
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
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NO USER FOUND): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mBudgetId = mSharedPreferences.getInt(BUDGET_ID_KEY, -1);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);

        // Check for valid giving ID
        mGivingId = mBudget.getGivingId();
        if(mGivingId == -1){
            Toast.makeText(getActivity().getApplicationContext(), "FATAL ERROR(BAD GIVING ID): Logging out...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);

        if(mGiving == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR (NULL GIVING TABLE): Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        mTestDisplay.setText("Budget ID: " + mBudgetId + ", User ID: " + mUserId + ", " + mGiving.toString());
    }

    private boolean checkForValidIncome(){
        grabLatestUserInfo();
        if(mBudget.getIncome().equals(nullString)){
            mErrorDisplay.setText(getString(R.string.givingErrorPrompt));
            hideAllOnScreenItems();
            return false;
        }

        mErrorDisplay.setText("");
        return true;
    }

    private boolean checkGivingHasBeenPopulated(){
        if(mGiving.getTotalPlanned().equals(zeroString) && mGiving.getTotalRecurring().equals(zeroString)){
            return false;
        }

        return true;
    }

    private void grabLatestUserInfo(){
        mUserId = mSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        mBudgetId = mSharedPreferences.getInt(BUDGET_ID_KEY, -1);
        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
        mGivingId = mBudget.getGivingId();
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
        mChurchPlannedInputEditText.setHint("n/a");
        mCharityPlannedInputEditText.setHint("n/a");
        mRangeCheckDisplay.setText(getString(R.string.rangeCheck, ""));
        mTotalDisplay.setText(getString(R.string.total, ""));
    }

    private void pullValuesIntoDatabase(){
        String churchPlanned = mChurchPlannedInputEditText.getText().toString().trim();
        String charityPlanned = mCharityPlannedInputEditText.getText().toString().trim();

        double church = Double.parseDouble(mGiving.getChurchPlanned());
        double charity = Double.parseDouble(mGiving.getCharityPlanned());

        double total = church + charity;
        double totalWithoutDefaultCats = total - Double.parseDouble(mGiving.getChurchPlanned()) - Double.parseDouble(mGiving.getCharityPlanned());
        double otherTotal = getOtherSubCategoriesTotal();

        if(totalWithoutDefaultCats != otherTotal){
            total += otherTotal;
        }

        String stringTotal = String.valueOf(total);

        String newRecurringTotal = getRecurringTotal();
        mGiving.setTotalRecurring(newRecurringTotal);

        mGiving.setCharityPlanned(charityPlanned);
        mGiving.setChurchPlanned(churchPlanned);
        mGiving.setTotalPlanned(stringTotal);

        mBudgetBuddyDAO.update(mGiving);

        grabLatestUserInfo();

        populateSummarySection();
    }

    private String getRecurringTotal() {
        int churchRecurring = 0;
        int charityRecurring = 0;

        double currRecurringTotal = 0;

        if(mChurchRecurringCheckBox.isChecked() && !mGiving.getChurchPlanned().equals(zeroString)){
            churchRecurring = 1;
            currRecurringTotal += Double.parseDouble(mGiving.getChurchPlanned());
        } else {
            mChurchRecurringCheckBox.setChecked(false);
        }

        if(mCharityRecurringCheckbox.isChecked() && !mGiving.getCharityPlanned().equals(zeroString)){
            charityRecurring = 1;
            currRecurringTotal += Double.parseDouble(mGiving.getCharityPlanned());
        } else {
            mCharityRecurringCheckbox.setChecked(false);
        }


        double recurringOtherTotal = getOtherSubCategoriesRecurringTotal();

        if(currRecurringTotal != currRecurringTotal + recurringOtherTotal){
            currRecurringTotal += recurringOtherTotal;
        }

        mGiving.setChurchRecurring(churchRecurring);
        mGiving.setCharityRecurring(charityRecurring);

        return String.valueOf(currRecurringTotal);
    }

    private void populateSummarySection(){
        mTotalDisplay.setText(getString(R.string.total, mGiving.getTotalPlanned()));
        String rangeCheck = calculateRangeCheck();
        mRangeCheckDisplay.setText(getString(R.string.rangeCheck, rangeCheck));
    }

    private String calculateRangeCheck() {
        grabLatestUserInfo();
        double total = Double.parseDouble(mGiving.getTotalPlanned());
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
            return "Over budget!";
        }

        return "ERROR...";
    }

    private void populateScreen(){
        grabLatestUserInfo();
        if(!checkGivingHasBeenPopulated()){
            mSaveButton.setText("SAVE");
            mTotalDisplay.setText(getString(R.string.total, ""));
            mRangeCheckDisplay.setText(getString(R.string.rangeCheck, ""));
            mChurchPlannedInputEditText.setHint("Enter Amount");
            mCharityPlannedInputEditText.setHint("Enter Amount");
            mRangeColorButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(), R.color.gray));
            return;
        }

        mSaveButton.setText("UPDATE");

        boolean currValue;
        currValue = mGiving.getCharityRecurring() == 1;
        mCharityRecurringCheckbox.setChecked(currValue);

        if(mGiving.getChurchRecurring() == 1){
            mChurchRecurringCheckBox.setChecked(true);
        }

        // TODO: SET HINT TO PREVIOUS MONTHS AMOUNT

        String churchPlanned = mGiving.getChurchPlanned();
        mChurchPlannedInputEditText.setText(churchPlanned);
         mChurchPlannedInputEditText.setHint(getString(R.string.previous, "n/a"));

        String charityPlanned = mGiving.getCharityPlanned();
        mCharityPlannedInputEditText.setText(charityPlanned);
        mCharityPlannedInputEditText.setHint(getString(R.string.previous, "n/a"));

        populateSummarySection();
    }

    private void updateRecyclerView(boolean fromMenu){
        grabLatestUserInfo();

        if(fromMenu){
            if(mGiving.getOtherGivingExpenses().equals("")){
                return;
            }
        }

        List<String> currOtherValues = ListConverter.convertStringToList(mGiving.getOtherGivingExpenses());
        getOtherSubCategories(currOtherValues, fromMenu);
        cleanListOfDuplicates();
        mAdapter.updateData(mAdditionalSubCategories);
        mAdapter.notifyDataSetChanged();

        setAdapter();
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

    private void getOtherSubCategories(List<String> currOtherValues, boolean fromMenu){
        if(currOtherValues.size() < 3){
             // mOtherErrorDisplay.setText("ERROR: TEXT TO LIST CONVERSION INVALID");
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

//            double total = Double.parseDouble(mGiving.getTotalPlanned());
//            double totalWithoutDefaultCats = total - Double.parseDouble(mGiving.getChurchPlanned()) - Double.parseDouble(mGiving.getCharityPlanned());
//            double otherTotal = getOtherSubCategoriesTotal();

//            if(totalWithoutDefaultCats != otherTotal){
//                total += Double.parseDouble(currOtherValues.get(i + 1));
//                mGiving.setTotalPlanned(String.valueOf(total));
//                mBudgetBuddyDAO.update(mGiving);
//                populateSummarySection();
//            }
//
//            // TODO: FIX THIS
//            double otherRecurringTotal = getOtherSubCategoriesRecurringTotal();
//            if(Double.parseDouble(mGiving.getTotalRecurring()) != otherRecurringTotal){
//                grabLatestUserInfo();
//                double currTotalRecurring = Double.parseDouble(mGiving.getTotalRecurring());
//                currTotalRecurring += getOtherSubCategoriesRecurringTotal();
//                mGiving.setTotalRecurring(String.valueOf(currTotalRecurring));
//                mBudgetBuddyDAO.update(mGiving);
//                grabLatestUserInfo();
//            }

            AdditionalSubCategoryModel currSubCat = new AdditionalSubCategoryModel(currOtherValues.get(i), currOtherValues.get(i + 1), r);

            if(!mAdditionalSubCategories.contains(currSubCat)){
                mAdditionalSubCategories.add(currSubCat);
            }
        }
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
        String recurring = nullString;

        if(recurringBoolean){
            recurring = "[r]";
        } else {
            recurring = "[]";
        }

        grabLatestUserInfo();

        String otherExpenses = mGiving.getOtherGivingExpenses();

        if(!checkForDuplicateOtherEntry(title)){
            if(otherExpenses.equals(nullString)){
                otherExpenses = title + "-" + plannedAmount + "-" + recurring;
            } else {
                otherExpenses += "," + title + "-" + plannedAmount + "-" + recurring;
            }

            double currTotal = Double.parseDouble(mGiving.getTotalPlanned());
            currTotal += Double.parseDouble(plannedAmount);
            mGiving.setTotalPlanned(String.valueOf(currTotal));

            if(recurring.equals("[r]")){
                double currRecurringTotal = Double.parseDouble(mGiving.getTotalRecurring());
                currRecurringTotal += Double.parseDouble(plannedAmount);
                mGiving.setTotalRecurring(String.valueOf(currRecurringTotal));
            }

            mOtherErrorDisplay.setText("");
            mGiving.setOtherGivingExpenses(otherExpenses);
            mBudgetBuddyDAO.update(mGiving);
            return true;
        } else {
            mOtherErrorDisplay.setText("ERROR: A sub-category with that title already exists.");
            return false;
        }
    }

    private boolean checkEmptyOtherFields() {
        String title = mOtherTitleInput.getText().toString().trim();
        String plannedAmount = mOtherPlannedAmountInput.getText().toString().trim();
        return title.equals(nullString) || plannedAmount.equals(nullString);
    }

    private void setAdapter() {
        setOnClickListener();
        mAdapter = new AdditionalSubCategoryAdapter(mAdditionalSubCategories, mListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
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
                mAddOtherButton.setVisibility(View.INVISIBLE);
                mUpdateButton.setVisibility(View.VISIBLE);
                mDeleteButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private boolean checkForDuplicateOtherEntry(String title){
        grabLatestUserInfo();

        List<String> currOtherValues = ListConverter.convertStringToList(mGiving.getOtherGivingExpenses());

        if(currOtherValues.contains(title)){
            mOtherErrorDisplay.setText("ERROR: An entry with that title already exists.");
            return true;
        }

        mOtherErrorDisplay.setText("");
        return false;
    }

    private double getOtherSubCategoriesTotal(){
        List<String> currOtherValues = ListConverter.convertStringToList(mGiving.getOtherGivingExpenses());
        double otherTotal = 0;

        if(mGiving.getOtherGivingExpenses().equals("")){
            return otherTotal;
        }

        for(int i = 0; i < currOtherValues.size(); i+=3){
            otherTotal += Double.parseDouble(currOtherValues.get(i + 1));
        }

        return otherTotal;
    }

    private double getOtherSubCategoriesRecurringTotal(){
        grabLatestUserInfo();
        List<String> currOtherValues = ListConverter.convertStringToList(mGiving.getOtherGivingExpenses());
        double recurringTotal = 0;

        if(mGiving.getOtherGivingExpenses().equals("")){
            return recurringTotal;
        }

        for(int i = 0; i < currOtherValues.size(); i+=3){
            if(currOtherValues.get(i + 2).equals("[r]")){
                recurringTotal += Double.parseDouble(currOtherValues.get(i + 1));
            }
        }

        return recurringTotal;
    }
}