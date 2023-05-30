package com.udromero.budget_buddy.login;

import static com.udromero.budget_buddy.Constants.LOGGED_IN_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udromero.budget_buddy.MainActivity;
import com.udromero.budget_buddy.R;
import com.udromero.budget_buddy.databinding.ActivityLoginBinding;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.entities.User;

public class LoginActivity extends AppCompatActivity {
    // User Shared Preferences
    SharedPreferences mSharedPreferences;

    // Binding Variables
    ActivityLoginBinding binding;

    TextView mErrorResponse;

    // Edit Texts
    private EditText mEmailField;
    private EditText mPasswordField;

    private User mUser;

    private String mEmail;
    private String mPassword;

    BudgetBuddyDAO mBudgetBuddyDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Activity Binding
        mEmailField = binding.loginEmailEditText;
        mPasswordField = binding.loginPasswordEditText;

        mErrorResponse = binding.loginErrorResponseTextView;

        // Buttons
        Button loginButton = binding.loginLoginButton;
        Button registerButton = binding.loginRegisterButton;

        // Retrieving Database
        getDataBase();

        // Get user shared preferences
        getPrefs();

        // Check if user is already logged in, if yes, send to landing page
        checkLoggedIn();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUserCredentials()){
                    checkForUserInDatabase();
                }
            }
        });
    }

    // Grab email and password from user populated fields
    private boolean getUserCredentials(){
        mEmail = mEmailField.getText().toString().trim();
        mPassword = mPasswordField.getText().toString().trim();

        // Check there's no empty fields
        if(mEmail.isEmpty() && mPassword.isEmpty()) {
            mErrorResponse.setText(R.string.missingAllRequiredFields);
            return false;
        } else if(mEmail.isEmpty()) {
            mErrorResponse.setText(R.string.missingEmailAddressField);
            return false;
        } else if(mPassword.isEmpty()){
            mErrorResponse.setText(R.string.missingPasswordField);
            return false;
        }

        // User credentials successfully pulled as non-empty strings
        return true;
    }

    private void checkForUserInDatabase(){
        mUser = mBudgetBuddyDAO.getUserByEmail(mEmail);
        if(mUser != null && verifyValidCredentials()){
            updateSharedPreferences();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            mErrorResponse.setText(R.string.invalidEmailPrompt);
        }
    }

    private boolean verifyValidCredentials(){
        if(mUser.getPassword_hash().equals(mPassword) && mUser.getEmail().equals(mEmail)){
            return true;
        }

        mErrorResponse.setText(R.string.invalidCredentialsPrompt);
        return false;
    }

    private void getPrefs() {
        mSharedPreferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void updateSharedPreferences(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(USER_ID_KEY, mUser.getUserId());
        editor.putBoolean(LOGGED_IN_KEY, true);
        editor.apply();
    }

    // Retrieve database
    private void getDataBase(){
        mBudgetBuddyDAO = Room.databaseBuilder(this, BudgetBuddyDatabase.class, BudgetBuddyDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .BudgetBuddyDAO();
    }

    private void checkLoggedIn(){
        boolean loggedIn = mSharedPreferences.getBoolean(LOGGED_IN_KEY, false);
        if(loggedIn){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}