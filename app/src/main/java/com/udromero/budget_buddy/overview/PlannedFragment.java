package com.udromero.budget_buddy.overview;

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
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.udromero.budget_buddy.R;
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

import java.util.ArrayList;
import java.util.List;

public class PlannedFragment extends Fragment {

    BudgetBuddyDAO mBudgetBuddyDAO;

    SharedPreferences mSharedPreferences;

    private int mUserId;
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

    PieChart mPieChart;
    TextView mPromptDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planned, container, false);

        mPieChart = view.findViewById(R.id.pieChart);
        mPromptDisplay = view.findViewById(R.id.overviewPromptDisplay);

        getDatabase();
        getPrefs();

        getAllCats();

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        double income = Double.parseDouble(mBudget.getIncome());
        double percent;
        int decimalPlaces = 2;
        float roundedValue;

        float giving = Float.parseFloat(mGiving.getTotalPlanned());
        if(giving != 0){
            percent = (giving / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(giving, "Giving (" + roundedValue + "%)"));
        }

        float savings = Float.parseFloat(mSavings.getTotalPlanned());
        if(savings != 0){
            percent = (savings / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(savings, "Savings (" + roundedValue + "%)"));
        }

        float housing = Float.parseFloat(mHousing.getTotalPlanned());
        if (housing != 0) {
            percent = (housing / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .25 && percent/100 <= .35){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .25){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(housing, "Housing (" + roundedValue + "%)"));
        }

        float food = Float.parseFloat(mFood.getTotalPlanned());
        if(food != 0){
            percent = (food / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .05 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .05){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(food, "Food (" + roundedValue + "%)"));
        }

        float trans = Float.parseFloat(mTransportation.getTotalPlanned());
        if(trans != 0){
            percent = (trans / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(trans, "Transportation (" + roundedValue + "%)"));
        }

        float personal = Float.parseFloat(mPersonal.getTotalPlanned());
        if(personal != 0){
            percent = (personal / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .05 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .05){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(personal, "Personal (" + roundedValue + "%)"));
        }

        float lifestyle = Float.parseFloat(mLifestyle.getTotalPlanned());
        if(lifestyle != 0){
            percent = (lifestyle / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .05 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .05){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(lifestyle, "Lifestyle (" + roundedValue + "%)"));
        }

        float health = Float.parseFloat(mHealth.getTotalPlanned());
        if(health != 0){
            percent = (health / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .05 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .05){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(health, "Health (" + roundedValue + "%)"));
        }

        float insurance = Float.parseFloat(mInsurance.getTotalPlanned());
        if(insurance != 0){
            percent = (insurance / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(insurance, "Insurance (" + roundedValue + "%)"));
        }

        float debt = Float.parseFloat(mDebt.getTotalPlanned());
        if(debt != 0){
            percent = (debt / income) * 100;
            roundedValue = Math.round(percent * (float) Math.pow(10, decimalPlaces)) / (float) Math.pow(10, decimalPlaces);
            if(percent/100 >= .05 && percent/100 <= .15){
                colors.add(Color.GREEN);
            } else {
                if(percent/100 < .05){
                    colors.add(Color.DKGRAY);
                } else {
                    colors.add(Color.RED);
                }
            }
            entries.add(new PieEntry(debt, "Debt (" + roundedValue + "%)"));
        }

        if(mBudget.getTotalPlannedExpenses().equals("")){
            mPromptDisplay.setText("Missing required budget information. Please enter your information on the 'Budget' tab.");
            mPromptDisplay.setTextColor(Color.RED);
        } else {
            mPromptDisplay.setText("Green = Within Budget, Red = Over Budget, Gray = Under Budget");
            mPromptDisplay.setTextColor(Color.BLACK);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColors(colors);

        PieData data = new PieData(dataSet);

        mPieChart.setData(data);
        mPieChart.invalidate();
        mPromptDisplay.setText("Green = Within Budget, Red = Over Budget, Gray = Under Budget");
        mPromptDisplay.setTextColor(Color.BLACK);


        return view;
    }

    private void getDatabase(){
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

        if(mUser == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR: Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void getAllCats() {
        mGiving = mBudgetBuddyDAO.getGivingExpensesByUserId(mUserId);
        mSavings = mBudgetBuddyDAO.getSavingsExpensesByUserId(mUserId);
        mHousing = mBudgetBuddyDAO.getHousingExpensesByUserId(mUserId);
        mFood = mBudgetBuddyDAO.getFoodExpensesByUserId(mUserId);
        mTransportation = mBudgetBuddyDAO.getTransportationExpensesByUserId(mUserId);
        mPersonal = mBudgetBuddyDAO.getPersonalExpensesByUserId(mUserId);
        mLifestyle = mBudgetBuddyDAO.getLifestyleExpensesByUserId(mUserId);
        mHealth = mBudgetBuddyDAO.getHealthExpensesByUserId(mUserId);
        mInsurance = mBudgetBuddyDAO.getInsuranceExpensesByUserId(mUserId);
        mDebt = mBudgetBuddyDAO.getDebtExpensesByUserId(mUserId);
    }
}