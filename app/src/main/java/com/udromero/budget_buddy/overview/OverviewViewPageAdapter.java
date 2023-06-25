package com.udromero.budget_buddy.overview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.udromero.budget_buddy.budget.DebtFragment;

public class OverviewViewPageAdapter extends FragmentStateAdapter {

    public OverviewViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new PlannedFragment();
            case 1:
                return new ExpensesFragment();
            default:
                return new PlannedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
