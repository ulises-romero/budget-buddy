package com.udromero.budget_buddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.udromero.budget_buddy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Launch app on overview activity by default
        switchFragment(new OverviewFragment());

        // On bottom nav item select
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.overview){
                switchFragment(new OverviewFragment());
            } else if(id == R.id.monthly){
                switchFragment(new MonthlyViewFragment());
            } else if(id == R.id.budget){
                switchFragment(new MyBudgetFragment());
            } else if(id == R.id.settings){
                switchFragment(new SettingsFragment());
            }

            return true;
        });
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}