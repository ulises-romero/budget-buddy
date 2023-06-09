package com.udromero.budget_buddy.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

@Dao
public interface BudgetBuddyDAO {

    @Query("DELETE FROM " + BudgetBuddyDatabase.USER_TABLE)
    void nukeUserTable();

    @Query("DELETE FROM " + BudgetBuddyDatabase.BUDGET_TABLE)
    void nukeBudgetTable();

    @Query("DELETE FROM " + BudgetBuddyDatabase.GIVING_TABLE)
    void nukeGivingTable();

    // Users
    @Insert
    void insert(User...users);

    @Update
    void update(User...users);

    @Delete
    void delete(User...users);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.USER_TABLE + " WHERE mEmail = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.USER_TABLE + " WHERE mUserId =:userId")
    User getUserByUserId(int userId);

    @Query("DELETE FROM " + BudgetBuddyDatabase.USER_TABLE + " WHERE mUserId=:userId")
    void deleteUserById(int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.USER_TABLE + " SET mFirstName=:firstName WHERE mUserId=:userId")
    void updateUserFirstNameById(String firstName, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.USER_TABLE + " SET mLastName=:lastName WHERE mUserId=:userId")
    void updateUserLastNameById(String lastName, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.USER_TABLE + " SET mEmail=:email WHERE mUserId=:userId")
    void updateUserEmailById(String email, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.USER_TABLE + " SET mPassword_hash=:password WHERE mUserId=:userId")
    void updateUserPasswordById(String password, int userId);

    @Query("SELECT mFirstTimeLogin FROM " + BudgetBuddyDatabase.USER_TABLE + " WHERE mUserId=:userId")
    String getUserFirstTimeLogin(int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.USER_TABLE + " SET mFirstTimeLogin=:firstTimeLogin WHERE mUserId=:userId")
    void updateUserFirstTimeLogin(String firstTimeLogin, int userId);

    @Query("DELETE FROM " + BudgetBuddyDatabase.USER_TABLE + " WHERE mUserId=:userId")
    void deleteUserByUserId(int userId);

    // Budget
    @Insert
    void insert(Budget...budgets);

    @Update
    void update(Budget...budgets);

    @Delete
    void delete(Budget...budgets);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.BUDGET_TABLE + " WHERE mBudgetId = :budgetId")
    Budget getBudgetById(int budgetId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.BUDGET_TABLE + " WHERE mUserId = :userId")
    Budget getBudgetByUserId(int userId);

    @Query("DELETE FROM " + BudgetBuddyDatabase.BUDGET_TABLE + " WHERE mBudgetId=:budgetId")
    void deleteBudgetByBudgetId(int budgetId);

    @Query("UPDATE " + BudgetBuddyDatabase.BUDGET_TABLE + " SET mPaycheckFrequency=:paycheckFreq WHERE mBudgetId=:budgetId")
    void updateBudgetPayCheckFrequency(int paycheckFreq, int budgetId);

    @Query("UPDATE " + BudgetBuddyDatabase.BUDGET_TABLE + " SET mIncome=:income WHERE mUserId=:userId")
    void updateBudgetIncomeByUserId(String income, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.BUDGET_TABLE + " SET mGivingId=:givingId WHERE mUserId=:userId")
    void updateBudgetGivingIdByUserId(int givingId, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.BUDGET_TABLE + " SET mTotalSpent=:totalSpent WHERE mUserId=:userId")
    void updateBudgetTotalSpentByUserId(String totalSpent, int userId);

    // [TABLES/ENTITIES/EXPENSE CATEGORIES]

    // [GIVING]
    @Insert
    void insert(Giving...givings);

    @Update
    void update(Giving...givings);

    @Delete
    void delete(Giving...givings);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.GIVING_TABLE + " WHERE mUserId = :userId")
    Giving getGivingExpensesByUserId(int userId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.GIVING_TABLE + " WHERE mGivingId = :givingId")
    Giving getGivingExpensesByGivingId(int givingId);

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mTotalSpent=:totalSpent WHERE mGivingId=:givingId")
    void updateGivingTotalSpentByGivingId(String totalSpent, int givingId);

    // Church

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mChurchPlanned=:churchPlanned WHERE mUserId=:userId")
    void updateChurchPlannedByUserId(String churchPlanned, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mChurchRecurring=:churchRecurring WHERE mUserId=:userId")
    void updateChurchRecurringByUserId(int churchRecurring, int userId);

    // Charity

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mCharityPlanned=:charityPlanned WHERE mUserId=:userId")
    void updateCharityPlannedByUserId(String charityPlanned, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mCharityRecurring=:charityRecurring WHERE mUserId=:userId")
    void updateCharityRecurringByUserId(int charityRecurring, int userId);

    // Giving Total

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mTotalPlanned=:totalPlanned WHERE mUserId=:userId")
    void updateGivingTotalPlannedByUserId(String totalPlanned, int userId);

    @Query("UPDATE " + BudgetBuddyDatabase.GIVING_TABLE + " SET mTotalRecurring=:totalRecurring WHERE mUserId=:userId")
    void updateGivingRecurringTotalByUserId(String totalRecurring, int userId);

    // [SAVINGS]
    @Insert
    void insert(Savings...savings);

    @Update
    void update(Savings...savings);

    @Delete
    void delete(Savings...savings);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.SAVINGS_TABLE + " WHERE mSavingsId = :savingsId")
    Savings getSavingsExpensesById(int savingsId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.SAVINGS_TABLE + " WHERE mUserId = :userId")
    Savings getSavingsExpensesByUserId(int userId);


    // Food
    @Insert
    void insert(Food...foods);

    @Update
    void update(Food...foods);

    @Delete
    void delete(Food...foods);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.FOOD_TABLE + " WHERE mFoodId = :foodId")
    Food getFoodExpensesById(int foodId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.FOOD_TABLE + " WHERE mUserId= :userId")
    Food getFoodExpensesByUserId(int userId);

    // Housing
    @Insert
    void insert(Housing...housings);

    @Update
    void update(Housing...housings);

    @Delete
    void delete(Housing...housings);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.HOUSING_TABLE + " WHERE mHousingId = :housingId")
    Housing getHousingExpensesById(int housingId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.HOUSING_TABLE + " WHERE mUserId= :userId")
    Housing getHousingExpensesByUserId(int userId);

    // Personal
    @Insert
    void insert(Personal...personals);

    @Update
    void update(Personal...personals);

    @Delete
    void delete(Personal...personals);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.PERSONAL_TABLE + " WHERE mPersonalId= :personalId")
    Personal getPersonalExpensesById(int personalId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.PERSONAL_TABLE + " WHERE mUserId= :userId")
    Personal getPersonalExpensesByUserId(int userId);

    // Transportation
    @Insert
    void insert(Transportation...transportations);

    @Update
    void update(Transportation...transportations);

    @Delete
    void delete(Transportation...transportations);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.TRANSPORTATION_TABLE + " WHERE mTransportationId= :transportationId")
    Transportation getTransportationExpensesById(int transportationId);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.TRANSPORTATION_TABLE + " WHERE mUserId= :userId")
    Transportation getTransportationExpensesByUserId(int userId);

    // LIFESTYLE

    @Insert
    void insert(Lifestyle...lifestyles);

    @Update
    void update(Lifestyle...lifestyles);

    @Delete
    void delete(Lifestyle...lifestyles);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.LIFESTYLE_TABLE + " WHERE mUserId= :userId")
    Lifestyle getLifestyleExpensesByUserId(int userId);


    // HEALTH
    @Insert
    void insert(Health...healths);

    @Update
    void update(Health...healths);

    @Delete
    void delete(Health...healths);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.HEALTH_TABLE + " WHERE mUserId= :userId")
    Health getHealthExpensesByUserId(int userId);

    // INSURANCE
    @Insert
    void insert(Insurance...insurances);

    @Update
    void update(Insurance...insurances);

    @Delete
    void delete(Insurance...insurances);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.INSURANCE_TABLE + " WHERE mUserId= :userId")
    Insurance getInsuranceExpensesByUserId(int userId);

    // DEBT
    @Insert
    void insert(Debt...debts);

    @Update
    void update(Debt...debts);

    @Delete
    void delete(Debt...debts);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.DEBT_TABLE + " WHERE mUserId= :userId")
    Debt getDebtExpensesByUserId(int userId);

}
