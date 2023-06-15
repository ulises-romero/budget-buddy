package com.udromero.budget_buddy.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Debt;
import com.udromero.budget_buddy.db.entities.Food;
import com.udromero.budget_buddy.db.entities.Giving;
import com.udromero.budget_buddy.db.entities.Health;
import com.udromero.budget_buddy.db.entities.Housing;
import com.udromero.budget_buddy.db.entities.Insurance;
import com.udromero.budget_buddy.db.entities.Lifestyle;
import com.udromero.budget_buddy.db.entities.Other;
import com.udromero.budget_buddy.db.entities.Personal;
import com.udromero.budget_buddy.db.entities.Savings;
import com.udromero.budget_buddy.db.entities.Transportation;
import com.udromero.budget_buddy.db.entities.User;

@Database(entities = {User.class, Budget.class, Giving.class, Savings.class, Housing.class, Food.class,
        Transportation.class, Personal.class, Lifestyle.class, Health.class, Insurance.class, Debt.class}, version = 7)
public abstract class BudgetBuddyDatabase extends RoomDatabase {
    // Database Name
    public static final String DATABASE_NAME = "BUDGET_BUDDY_DATABASE";

    // TABLE NAMES

    // Other tables
    public static final String USER_TABLE = "USER_TABLE";
    public static final String BUDGET_TABLE = "BUDGET_TABLE";

    // Expense Categories
    public static final String GIVING_TABLE = "GIVING_TABLE";
    public static final String SAVINGS_TABLE = "SAVINGS_TABLE";
    public static final String HOUSING_TABLE = "HOUSING_TABLE";
    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String TRANSPORTATION_TABLE = "TRANSPORTATION_TABLE";
    public static final String PERSONAL_TABLE = "PERSONAL_TABLE";
    public static final String LIFESTYLE_TABLE = "LIFESTYLE_TABLE";
    public static final String HEALTH_TABLE = "HEALTH_TABLE";
    public static final String INSURANCE_TABLE = "INSURANCE_TABLE";
    public static final String DEBT_TABLE = "DEBT_TABLE";
    public static final String OTHER_TABLE = "OTHER_TABLE";

    public abstract BudgetBuddyDAO BudgetBuddyDAO();
}
