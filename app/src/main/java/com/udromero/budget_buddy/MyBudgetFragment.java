package com.udromero.budget_buddy;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.udromero.budget_buddy.budget.BudgetViewPageAdapter;

public class MyBudgetFragment extends Fragment {
    TabLayout mBudgetTabLayout;
    ViewPager2 mBudgetViewPager2;
    BudgetViewPageAdapter mBudgetViewPageAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_budget, container, false);

        mBudgetTabLayout = view.findViewById(R.id.budgetTabLayout);
        mBudgetViewPager2 = view.findViewById(R.id.budgetViewPager2);
        mBudgetViewPageAdapter = new BudgetViewPageAdapter(this.getActivity());
        mBudgetViewPager2.setAdapter(mBudgetViewPageAdapter);

        mBudgetTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBudgetViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBudgetViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mBudgetTabLayout.getTabAt(position).select();
            }
        });

        return view;
    }
}