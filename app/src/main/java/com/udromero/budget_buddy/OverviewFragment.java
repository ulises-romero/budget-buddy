package com.udromero.budget_buddy;

import static com.udromero.budget_buddy.Constants.BUDGET_ID_KEY;
import static com.udromero.budget_buddy.Constants.DEBT_ID_KEY;
import static com.udromero.budget_buddy.Constants.FOOD_ID_KEY;
import static com.udromero.budget_buddy.Constants.GIVING_ID_KEY;
import static com.udromero.budget_buddy.Constants.HEALTH_ID_KEY;
import static com.udromero.budget_buddy.Constants.HOUSING_ID_KEY;
import static com.udromero.budget_buddy.Constants.INSURANCE_ID_KEY;
import static com.udromero.budget_buddy.Constants.LIFESTYLE_ID_KEY;
import static com.udromero.budget_buddy.Constants.PERSONAL_ID_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.SAVINGS_ID_KEY;
import static com.udromero.budget_buddy.Constants.TRANSPORTATION_ID_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;
import static com.udromero.budget_buddy.Constants.nullInt;
import static com.udromero.budget_buddy.Constants.nullString;
import static com.udromero.budget_buddy.Constants.zeroString;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
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
import com.udromero.budget_buddy.overview.OverviewViewPageAdapter;

public class OverviewFragment extends Fragment {

    BudgetBuddyDAO mBudgetBuddyDAO;

    SharedPreferences mSharedPreferences;

    private int mUserId;
    private int mBudgetId;
    private int mGivingId;
    private int mSavingsId;
    private int mHousingId;
    private int mFoodId;
    private int mTransportationId;
    private int mPersonalId;
    private int mLifestyleId;
    private int mHealthId;
    private int mInsuranceId;
    private int mDebtId;

    private User mUser;
    private Budget mBudget;
    private Giving mGiving;
    private Savings mSavings;
    private Housing mHousing;
    private Food mFood;
    private Transportation mTransportation;
    private Personal mPersonal;
    private Lifestyle mLifestyle;
    private Health mHealth;
    private Insurance mInsurance;
    private Debt mDebt;

    TabLayout mOverviewTabLayout;
    ViewPager2 mOverviewViewPager2;
    OverviewViewPageAdapter mOverviewViewPageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_overview, container, false);

        mOverviewTabLayout = view.findViewById(R.id.overviewTabLayout);
        mOverviewViewPager2 = view.findViewById(R.id.overviewViewPager2);
        mOverviewViewPageAdapter = new OverviewViewPageAdapter(this.getActivity());
        mOverviewViewPager2.setAdapter(mOverviewViewPageAdapter);

        getDataBase();

        getPrefs();

        if(checkFirstTimeLogin()){
            mBudgetBuddyDAO.updateUserFirstTimeLogin("n", mUserId);
            initUserBudget();
        }

        mOverviewTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mOverviewViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mOverviewViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mOverviewTabLayout.getTabAt(position).select();
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

        if(mUser == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR: Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private boolean checkFirstTimeLogin(){
        String ftl = mBudgetBuddyDAO.getUserFirstTimeLogin(mUserId);

        return ftl.equals("y");
    }

    private void initUserBudget(){
        Giving giving = new Giving(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, 0, zeroString, zeroString, 0, nullString);
        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
        if(mGiving != null){
            mBudgetBuddyDAO.delete(mGiving);
            mBudgetBuddyDAO.insert(giving);
        } else {
            mBudgetBuddyDAO.insert(giving);
        }
        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
        mGivingId = giving.getGivingId();


        Savings savings = new Savings(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, nullInt, nullString);
        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
        if(mSavings != null){
            mBudgetBuddyDAO.delete(mSavings);
            mBudgetBuddyDAO.insert(savings);
        } else {
            mBudgetBuddyDAO.insert(savings);
        }
        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
        mSavingsId = savings.getSavingsId();


        Housing housing = new Housing(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString,
                0, zeroString, zeroString, 0, nullString);
        mHousing = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);
        if(mHousing != null){
            mBudgetBuddyDAO.delete(mHousing);
            mBudgetBuddyDAO.insert(housing);
        } else {
            mBudgetBuddyDAO.insert(housing);
        }
        mHousing = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);
        mHousingId = housing.getHousingId();

        Food food = new Food(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, 0,
                zeroString, zeroString, 0, nullString);
        mFood = mBudgetBuddyDAO.getFoodExpensesByUserId(mUserId);
        if(mFood != null){
            mBudgetBuddyDAO.delete(mFood);
            mBudgetBuddyDAO.insert(food);

        } else {
            mBudgetBuddyDAO.insert(food);
        }
        mFood = mBudgetBuddyDAO.getFoodExpensesByUserId(mUserId);
        mFoodId = mFood.getFoodId();

        Transportation transportation = new Transportation(mUserId, zeroString, zeroString, zeroString, zeroString , zeroString, 0,
                zeroString, zeroString, 0, nullString);
        mTransportation = mBudgetBuddyDAO.getTransportationExpensesByUserId(mUserId);
        if(mTransportation != null){
            mBudgetBuddyDAO.delete(mTransportation);
            mBudgetBuddyDAO.insert(transportation);
        } else {
            mBudgetBuddyDAO.insert(transportation);
        }
        mTransportation = mBudgetBuddyDAO.getTransportationExpensesByUserId(mUserId);
        mTransportationId = mTransportation.getTransportationId();

        Personal personal = new Personal(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString,
                0, zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString, 0, nullString);
        mPersonal = mBudgetBuddyDAO.getPersonalExpensesByUserId(mUserId);
        if(mPersonal != null){
            mBudgetBuddyDAO.delete(mPersonal);
            mBudgetBuddyDAO.insert(personal);
        } else {
            mBudgetBuddyDAO.insert(personal);
        }
        mPersonal = mBudgetBuddyDAO.getPersonalExpensesByUserId(mUserId);
        mPersonalId = mPersonal.getPersonalId();

        Lifestyle lifestyle = new Lifestyle(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, 0,
                zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString,
                0, zeroString, zeroString, 0, nullString);
        mLifestyle = mBudgetBuddyDAO.getLifestyleExpensesByUserId(mUserId);
        if(mLifestyle != null){
            mBudgetBuddyDAO.delete(mLifestyle);
            mBudgetBuddyDAO.insert(lifestyle);
        } else {
            mBudgetBuddyDAO.insert(lifestyle);
        }
        mLifestyle = mBudgetBuddyDAO.getLifestyleExpensesByUserId(mUserId);
        mLifestyleId = mLifestyle.getLifestyleId();

        Health health = new Health(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, 0,
                zeroString, zeroString, 0, zeroString, zeroString, 0, nullString);
        mHealth = mBudgetBuddyDAO.getHealthExpensesByUserId(mUserId);
        if(mHealth != null){
            mBudgetBuddyDAO.delete(mHealth);
            mBudgetBuddyDAO.insert(health);
        } else {
            mBudgetBuddyDAO.insert(health);
        }
        mHealth = mBudgetBuddyDAO.getHealthExpensesByUserId(mUserId);
        mHealthId = mHealth.getHealthId();

        Insurance insurance = new Insurance(mUserId, zeroString, zeroString ,zeroString, zeroString, zeroString, 0,
                zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString,
                0, nullString);
        mInsurance = mBudgetBuddyDAO.getInsuranceExpensesByUserId(mUserId);
        if(mInsurance != null){
            mBudgetBuddyDAO.delete(mInsurance);
            mBudgetBuddyDAO.insert(insurance);
        } else {
            mBudgetBuddyDAO.insert(insurance);
        }
        mInsurance = mBudgetBuddyDAO.getInsuranceExpensesByUserId(mUserId);
        mInsuranceId = mInsurance.getInsuranceId();

        Debt debt = new Debt(mUserId, zeroString, zeroString, zeroString, zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString, 0, zeroString, zeroString, 0, nullString);
        mDebt = mBudgetBuddyDAO.getDebtExpensesByUserId(mUserId);
        if(mDebt != null){
            mBudgetBuddyDAO.delete(mDebt);
            mBudgetBuddyDAO.insert(debt);
        } else {
            mBudgetBuddyDAO.insert(debt);
        }
        mDebt = mBudgetBuddyDAO.getDebtExpensesByUserId(mUserId);
        mDebtId = mDebt.getDebtId();

        Budget budget = new Budget(mUserId, mGivingId, mSavingsId, mHousingId, mFoodId,
                mTransportationId, mPersonalId, mLifestyleId, mHealthId, mInsuranceId, mDebtId, -1, 0,
                zeroString, zeroString, zeroString, zeroString, nullString, nullString, nullString, nullString);
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        if(mBudget != null){
            mBudgetBuddyDAO.delete(mBudget);
            mBudgetBuddyDAO.insert(budget);
        } else {
            mBudgetBuddyDAO.insert(budget);
        }
        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        mBudgetId = budget.getBudgetId();

        updateSharedPrefs();
    }

    private void updateSharedPrefs(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(BUDGET_ID_KEY, mBudgetId);
        editor.putInt(GIVING_ID_KEY, mGivingId);
        editor.putInt(SAVINGS_ID_KEY, mSavingsId);
        editor.putInt(HOUSING_ID_KEY, mHousingId);
        editor.putInt(FOOD_ID_KEY, mFoodId);
        editor.putInt(TRANSPORTATION_ID_KEY, mTransportationId);
        editor.putInt(PERSONAL_ID_KEY, mPersonalId);
        editor.putInt(LIFESTYLE_ID_KEY, mLifestyleId);
        editor.putInt(HEALTH_ID_KEY, mHealthId);
        editor.putInt(INSURANCE_ID_KEY, mInsuranceId);
        editor.putInt(DEBT_ID_KEY, mDebtId);
        editor.apply();
    }
}