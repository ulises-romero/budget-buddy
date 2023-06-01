package com.udromero.budget_buddy.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Food;
import com.udromero.budget_buddy.db.entities.Giving;
import com.udromero.budget_buddy.db.entities.Housing;
import com.udromero.budget_buddy.db.entities.Other;
import com.udromero.budget_buddy.db.entities.Personal;
import com.udromero.budget_buddy.db.entities.Transportation;
import com.udromero.budget_buddy.db.entities.User;

@Database(entities = {Budget.class, Food.class, Giving.class, Housing.class, Other.class, Personal.class, Transportation.class, User.class}, version = 4)
@TypeConverters({Converters.class})
public abstract class BudgetBuddyDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "BUDGET_BUDDY_DATABASE";
    // TABLE NAMES

    public static final String USER_TABLE = "USER_TABLE";
    public static final String BUDGET_TABLE = "BUDGET_TABLE";

    // Expense Categories
    public static final String HOUSING_TABLE = "HOUSING_TABLE";
    public static final String FOOD_TABLE = "FOOD_TABLE";
    public static final String TRANSPORTATION_TABLE = "TRANSPORTATION_TABLE";
    public static final String PERSONAL_TABLE = "PERSONAL_TABLE";
    public static final String GIVING_TABLE = "GIVING_TABLE";
    public static final String OTHER_TABLE = "OTHER_TABLE";

    public abstract BudgetBuddyDAO BudgetBuddyDAO();
}
