package com.udromero.budget_buddy.budget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.udromero.budget_buddy.db.entities.Food;
import com.udromero.budget_buddy.db.entities.Insurance;

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
                return new GivingFragment();
            case 2:
                return new OtherFragment();
//                return new SavingsFragment();
            case 3:
                return new HousingFragment();
            case 4:
                return new FoodFragment();
            case 5:
                return new TransportationFragment();
            case 6:
                return new PersonalFragment();
            case 7:
                return new LifestyleFragment();
            case 8:
                return new HealthFragment();
            case 9:
                return new InsuranceFragment();
            case 10:
                return new DebtFragment();
            default:
                return new IncomeFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 11;
    }
}
