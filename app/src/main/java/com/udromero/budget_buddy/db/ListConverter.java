package com.udromero.budget_buddy.db;

import static com.udromero.budget_buddy.Constants.DEBT;
import static com.udromero.budget_buddy.Constants.FOOD;
import static com.udromero.budget_buddy.Constants.GIVING;
import static com.udromero.budget_buddy.Constants.HEALTH;
import static com.udromero.budget_buddy.Constants.HOUSING;
import static com.udromero.budget_buddy.Constants.INSURANCE;
import static com.udromero.budget_buddy.Constants.LIFESTYLE;
import static com.udromero.budget_buddy.Constants.OTHER;
import static com.udromero.budget_buddy.Constants.PERSONAL;
import static com.udromero.budget_buddy.Constants.SAVINGS;
import static com.udromero.budget_buddy.Constants.TRANSPORTATION;
import static com.udromero.budget_buddy.Constants.nullString;

import com.udromero.budget_buddy.recyclerView.AdditionalSubCategoryModel;
import com.udromero.budget_buddy.recyclerView.ezExpenseRecyclerView.EzExpenseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListConverter {
    static final List<String> validExpenseCategories = Arrays.asList(GIVING, SAVINGS, HOUSING, FOOD, TRANSPORTATION, PERSONAL, LIFESTYLE, HEALTH, INSURANCE, DEBT, OTHER);

    static final List<Character> validExpenseCharacters = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.');

    static String expensesErrorCause = nullString;

    public static String convertExpenseListToString(ArrayList<EzExpenseModel> expenseList){
        String result = "";

        for(int i = 0; i < expenseList.size(); i++){
            EzExpenseModel currExpense = expenseList.get(i);

            // Not a date section
            if(!currExpense.getDate().equals(" ")){
                if(i + 1 < expenseList.size()){
                    if(!expenseList.get(i + 1).getDate().equals(" ")){
                        expenseList.remove(i);
                    } else {
                        if(result.equals("")){
                            result = currExpense.getDate() + "- - - - ";
                        } else {
                            result += "," + currExpense.getDate() + "- - - - ";
                        }
                    }
                } else {
                    expenseList.remove(i);
                }
            } else {
                if(result.equals("")){
                    result = " -" + currExpense.getDescription() + "-" + currExpense.getCategory() + "-" + currExpense.getSubCategory() + "-" + currExpense.getPlannedAmount();
                } else {
                    result += ", -" + currExpense.getDescription() + "-" + currExpense.getCategory() + "-" + currExpense.getSubCategory() + "-" + currExpense.getPlannedAmount();
                }
            }
        }

        return result;
    }

    public static List<String> convertOtherExpensesToSubCatTitlesList(String otherExpenses){
        List<String> results = new ArrayList<>();
        List<String> other = convertStringToList(otherExpenses);

        if(otherExpenses.equals("")){
            return results;
        }

        for(int i = 0; i < other.size(); i+=3){
            results.add(other.get(i));
        }

        return results;
    }

    public static String convertListToString(List<AdditionalSubCategoryModel> currList){
        String result = "";
        for(int i = 0; i < currList.size(); i++){
            AdditionalSubCategoryModel currSubCat = currList.get(i);
            if(result.equals("")){
                result = currSubCat.getTitle() + "-" + currSubCat.getPlannedAmount() + "-" + currSubCat.getRecurring();
            } else {
                result += "," + currSubCat.getTitle() + "-" + currSubCat.getPlannedAmount() + "-" + currSubCat.getRecurring();
            }
        }

        return result;
    }

    public static List<String> convertStringToList(String expenses){
        // 1. Parsing the list, place all non reccuring before the [R] string everything after is reccuring cats follwoed by their expenses\
        List<String> results = new ArrayList<>();

//        if(!checkValidCategory(category)){
//            results.add("INVALID CATEGORY: " + expensesErrorCause);
//            expensesErrorCause = Constants.nullString;
//            return results;
//        }

        if(!checkValidString(expenses)){
            // results.add("BAD EXPENSES INPUT: " + expensesErrorCause);
            results.add(expensesErrorCause);
            expensesErrorCause = nullString;
            return results;
        }

        String[] expenseValues = expenses.split(",");

        for(String value : expenseValues){
            String[] values = value.split("-");
            results.add(values[0]);
            results.add(values[1]);
            results.add(values[2]);
        }

        return results;
    }

    public static ArrayList<EzExpenseModel> convertExpensesStringToList(String expenses){
        // 1. Parsing the list, place all non reccuring before the [R] string everything after is reccuring cats follwoed by their expenses\
        List<String> results = new ArrayList<>();
        ArrayList<EzExpenseModel> resultsFinal = new ArrayList<>();

        String[] expenseValues = expenses.split(",");

        if(expenseValues.length == 1){
            return resultsFinal;
        }

        for(String value : expenseValues){
            String[] values = value.split("-");
            results.add(values[0]); // date
            results.add(values[1]); // desc
            results.add(values[2]); // cat
            results.add(values[3]); // sub cat
            // planned amount

            EzExpenseModel currExpense = new EzExpenseModel(values[0], values[1], values[2], values[3], values[4]);
            resultsFinal.add(currExpense);
        }

        return resultsFinal;
    }

    private static boolean checkValidString(String expenses){
        String[] arr = expenses.split(",");

        // check for categories followed by expense amount
        int currValueIndex = 0; // 1 = expense cat, 2 = expense amount, 3 = reccuring
        for(String value : arr){

            if(!checkForDashes(value)){
                return false;
            }

            String[] currValues = value.split("-");

            if(currValues.length != 3){
                expensesErrorCause += "MISSING EXPENSE INFORMATION: " + value;
                return false;
            }

            String doubleValue = currValues[1];
            String recurringValue = currValues[2];

            if(!checkDouble(doubleValue)){
                expensesErrorCause += "FROM INVALID DOUBLE VALUE - " + doubleValue + " --- ";
                return false;
            }


            if(!recurringValue.equals("[]") && !recurringValue.equals("[r]")){
                expensesErrorCause += "INVALID RECCURING VALUE: " + recurringValue + " --- ";
            }
        }

        return true;
    }

    private static boolean checkDouble(String expense){
        for (int i = 0; i < expense.length(); i++) {
            if(!validExpenseCharacters.contains(expense.charAt(i))){
                expensesErrorCause += "INVALID DOUBLE CHAR - " + expense.charAt(i) + " | ";
                return false;
            }
        }

        return true;
    }

    private static boolean checkForDashes(String expense){

        if(!expense.contains("-")){
            expensesErrorCause += "INVALID EXPENSE STRING (" + expense + ") - CONTAINS NO DASHES TO SPLIT ON ";
            return false;
        }

        int dashCount = 0;
        for (int i = 0; i < expense.length(); i++) {
            if(expense.charAt(i) == '-'){
                dashCount++;
            }
        }

        if(dashCount != 2){
            expensesErrorCause += "INVALID EXPENSE STRING (" + expense + ") - A BAD DASH COUNT OF " + dashCount;
            return false;
        }

        return true;
    }

//    private boolean checkValidCategory(String category){
//        if(validExpenseCategories.contains(category)){
//            return true;
//        }
//
//        expensesErrorCause += category;
//        return false;
//    }
}
