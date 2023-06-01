package com.udromero.budget_buddy;

import static com.udromero.budget_buddy.Constants.BUDGET_ID_KEY;
import static com.udromero.budget_buddy.Constants.FIRST_TIME_LOGIN_KEY;
import static com.udromero.budget_buddy.Constants.LOGGED_IN_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;

public class OverviewFragment extends Fragment {

    BudgetBuddyDAO mBudgetBuddyDAO;

    SharedPreferences mSharedPreferences;

    private int mUserId;
    private int mBudgetId;
    private User mUser;
    private Budget mBudget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_overview, container, false);

        getDataBase();

        getPrefs();

        if(checkFirstTimeLogin()){
            mBudgetBuddyDAO.updateUserFirstTimeLogin("n", mUserId);
            initUserBudget();
        }




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
        // Toast.makeText(this.getActivity().getApplicationContext(), "INIT USER BUDGET", Toast.LENGTH_SHORT).show();
        Budget budget = new Budget(mUserId);
        mBudgetBuddyDAO.insert(budget);

        mBudget = mBudgetBuddyDAO.getBudgetByUserId(mUserId);
        mBudgetId = mBudget.getBudgetId();
        updateSharedPrefs();
    }

    private void updateSharedPrefs(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(BUDGET_ID_KEY, mBudgetId);
        editor.apply();
    }
}