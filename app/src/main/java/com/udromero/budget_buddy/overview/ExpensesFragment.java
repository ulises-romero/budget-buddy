package com.udromero.budget_buddy.overview;

import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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


public class ExpensesFragment extends Fragment {

    BudgetBuddyDAO mBudgetBuddyDAO;
    SharedPreferences mSharedPreferences;
    BarChart barChart;
    TextView mPromptDisplay;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        barChart = view.findViewById(R.id.barChart);
        mPromptDisplay = view.findViewById(R.id.expensesPromptDisplay);

        getDatabase();
        getPrefs();
        getAllCats();

        BarData barData = new BarData();

        List<BarEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        mPromptDisplay.setText("Expenses by category. Max value is 35% of income, no category should exceed this. If so, the bar will overflow.");
        mPromptDisplay.setTextColor(Color.BLACK);

        int xValue = 0;

        double income = Double.parseDouble(mBudget.getIncome());
        double percent;

        float giving = Float.parseFloat(mGiving.getTotalSpent());
        if(giving != 0){
            percent = (giving / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Giving");
            entries.add(new BarEntry(xValue, giving));
            xValue++;
        }

        float savings = Float.parseFloat(mSavings.getTotalSpent());
        if(savings != 0){
            percent = (savings / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Savings");
            entries.add(new BarEntry(xValue, savings));
            xValue++;
        }

        float housing = Float.parseFloat(mHousing.getTotalSpent());
        if(housing != 0){
            percent = (housing / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Housing");
            entries.add(new BarEntry(xValue, housing));
            xValue++;
        }

        float food = Float.parseFloat(mFood.getTotalSpent());
        if(food != 0){
            percent = (food / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Food");
            entries.add(new BarEntry(xValue, food));
            xValue++;
        }

        float trans = Float.parseFloat(mTransportation.getTotalSpent());
        if(trans != 0){
            percent = (trans / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Transport");
            entries.add(new BarEntry(xValue, trans));
            xValue++;
        }

        float personal = Float.parseFloat(mPersonal.getTotalSpent());
        if(personal != 0){
            percent = (personal / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Personal");
            entries.add(new BarEntry(xValue, personal));
            xValue++;
        }

        float lifestyle = Float.parseFloat(mLifestyle.getTotalSpent());
        if(lifestyle != 0){
            percent = (lifestyle / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Lifestyle");
            entries.add(new BarEntry(xValue, lifestyle));
            xValue++;
        }

        float health = Float.parseFloat(mHealth.getTotalSpent());
        if(health != 0){
            percent = (health / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Health");
            entries.add(new BarEntry(xValue, health));
            xValue++;
        }

        float insurance = Float.parseFloat(mInsurance.getTotalSpent());
        if(insurance != 0){
            percent = (insurance / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Insurance");
            entries.add(new BarEntry(xValue, insurance));
            xValue++;
        }

        float debt = Float.parseFloat(mDebt.getTotalSpent());
        if(debt != 0){
            percent = (debt / income) * 100;
            if(percent/100 >= .1 && percent/100 <= .15){
                colors.add(Color.YELLOW);
            } else {
                if(percent/100 < .1){
                    colors.add(Color.GREEN);
                } else {
                    colors.add(Color.RED);
                }
            }

            labels.add("Debt");
            entries.add(new BarEntry(xValue, debt));
            xValue++;
        }

        if(entries.size() == 0){
            mPromptDisplay.setText("Missing required budget information. Please enter your information on the 'Budget' tab.");
            mPromptDisplay.setTextColor(Color.RED);
        } else {
            mPromptDisplay.setText("Expenses by category. Max value is 35% of income, no category should exceed this. If so, the bar will overflow.");
            mPromptDisplay.setTextColor(Color.BLACK);
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColor(Color.RED);  // Set the color of bars
        dataSet.setValueTextColor(Color.BLACK);  // Set the color of values inside bars
        dataSet.setValueTextSize(12f);
        barData.addDataSet(dataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setDrawGridLines(false);
        dataSet.setBarBorderWidth(2f);

        YAxis yAxis = barChart.getAxisLeft(); // Assuming you want to set the maximum value for the left Y-axis
        float maxValue = (float) (income * 0.35); // Specify your desired maximum value
        yAxis.setAxisMaximum(maxValue);

        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setDrawGridLines(false);


        barChart.setData(barData);
        barChart.invalidate();

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