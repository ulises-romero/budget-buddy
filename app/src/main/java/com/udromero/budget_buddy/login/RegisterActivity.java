package com.udromero.budget_buddy.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.udromero.budget_buddy.R;
import com.udromero.budget_buddy.databinding.ActivityRegisterBinding;
import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.entities.User;

public class RegisterActivity extends AppCompatActivity {
    // Binding Variables
    ActivityRegisterBinding binding;

    TextView mErrorResponse;

    // Edit Texts
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private User mUser = null;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;

    BudgetBuddyDAO mBudgetBuddyDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Activity Binding
        mErrorResponse = binding.registerErrorResponseTextView;

        mFirstNameField = binding.registerFirstNameEditText;
        mLastNameField = binding.registerLastNameEditText;
        mEmailField = binding.registerEmailEditText;
        mPasswordField = binding.registerPasswordEditText;

        // Buttons
        Button registerButton = binding.registerButton;
        Button backButton = binding.registerBackButton;

        // Retrieving Database
        getDataBase();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getUserCredentials()){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean getUserCredentials(){
        mFirstName = mFirstNameField.getText().toString().trim();
        mLastName = mLastNameField.getText().toString().trim();
        mEmail = mEmailField.getText().toString().trim();
        mPassword = mPasswordField.getText().toString().trim();

        // Check there's no empty fields
        if(mEmail.isEmpty() && mPassword.isEmpty() && mFirstName.isEmpty() && mLastName.isEmpty()) {
            mErrorResponse.setText(R.string.missingAllRequiredFields);
            return false;
        } else if(mFirstName.isEmpty()){
            mErrorResponse.setText(R.string.missingFirstNameField);
            return false;
        } else if(mLastName.isEmpty()) {
            mErrorResponse.setText(R.string.missingLastNameField);
            return false;
        } else if(mEmail.isEmpty()) {
            mErrorResponse.setText(R.string.missingEmailAddressField);
            return false;
        } else if(mPassword.isEmpty()){
            mErrorResponse.setText(R.string.missingPasswordField);
            return false;
        }

        if(!checkExistingUser()){
            mUser = new User(mEmail, mPassword, mFirstName, mLastName, "y");
            insertNewUserIntoDatabase();
            return true;
        }

        // User credentials successfully pulled as non-empty strings
        mErrorResponse.setText(R.string.dupeEmailPrompt);
        return false;
    }

    private void getDataBase(){
        mBudgetBuddyDAO = Room.databaseBuilder(this, BudgetBuddyDatabase.class, BudgetBuddyDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .BudgetBuddyDAO();
    }

    private void insertNewUserIntoDatabase(){
        mBudgetBuddyDAO.insert(mUser);
    }

    private boolean checkExistingUser(){
        User dupeUserCheck = mBudgetBuddyDAO.getUserByEmail(mEmail);
        return dupeUserCheck != null;
    }
}