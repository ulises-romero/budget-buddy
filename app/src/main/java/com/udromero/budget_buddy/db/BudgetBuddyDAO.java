package com.udromero.budget_buddy.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.udromero.budget_buddy.db.entities.Budget;
import com.udromero.budget_buddy.db.entities.Food;
import com.udromero.budget_buddy.db.entities.Giving;
import com.udromero.budget_buddy.db.entities.Housing;
import com.udromero.budget_buddy.db.entities.Other;
import com.udromero.budget_buddy.db.entities.Personal;
import com.udromero.budget_buddy.db.entities.Transportation;
import com.udromero.budget_buddy.db.entities.User;

@Dao
public interface BudgetBuddyDAO {
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

    // Budget
    @Insert
    void insert(Budget...budgets);

    @Update
    void update(Budget...budgets);

    @Delete
    void delete(Budget...budgets);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.BUDGET_TABLE + " WHERE mBudgetId = :budgetId")
    Budget getBudgetById(int budgetId);

    // [TABLES/ENTITIES/EXPENSE CATEGORIES]

    // Food
    @Insert
    void insert(Food...foods);

    @Update
    void update(Food...foods);

    @Delete
    void delete(Food...foods);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.FOOD_TABLE + " WHERE mFoodId = :foodId")
    Food getFoodExpensesById(int foodId);

    // Giving
    @Insert
    void insert(Giving...givings);

    @Update
    void update(Giving...givings);

    @Delete
    void delete(Giving...givings);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.GIVING_TABLE + " WHERE mGivingId = :givingId")
    Giving getGivingExpensesById(int givingId);

    // Housing
    @Insert
    void insert(Housing...housings);

    @Update
    void update(Housing...housings);

    @Delete
    void delete(Housing...housings);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.HOUSING_TABLE + " WHERE mHousingId = :housingId")
    Housing getHousingExpensesById(int housingId);

    // Other
    @Insert
    void insert(Other...others);

    @Update
    void update(Other...others);

    @Delete
    void delete(Other...others);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.OTHER_TABLE + " WHERE mOtherId = :otherId")
    Other getOtherExpensesById(int otherId);

    // Personal
    @Insert
    void insert(Personal...personals);

    @Update
    void update(Personal...personals);

    @Delete
    void delete(Personal...personals);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.PERSONAL_TABLE + " WHERE mPersonalId= :personalId")
    Personal getPersonalExpensesById(int personalId);

    // Transportation
    @Insert
    void insert(Transportation...transportations);

    @Update
    void update(Transportation...transportations);

    @Delete
    void delete(Transportation...transportations);

    @Query("SELECT * FROM " + BudgetBuddyDatabase.TRANSPORTATION_TABLE + " WHERE mTransportationId= :transportationId")
    Transportation getTransportationExpensesById(int transportationId);


}
