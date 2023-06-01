package com.udromero.budget_buddy.budget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BudgetViewPageAdapter extends FragmentStateAdapter {

    public BudgetViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new IncomeFragment();
            case 1:
                // TODO: Please enter income before continuing
                return new HousingFragment();
            case 2:
                return new FoodFragment();
            case 3:
                return new PersonalFragment();
            case 4:
                return new GivingFragment();
            case 5:
                return new TransportationFragment();
            case 6:
                return new OtherFragment();
            default:
                return new IncomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
