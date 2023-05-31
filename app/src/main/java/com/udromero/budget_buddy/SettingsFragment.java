package com.udromero.budget_buddy;

import static com.udromero.budget_buddy.Constants.LOGGED_IN_KEY;
import static com.udromero.budget_buddy.Constants.PREFERENCES_KEY;
import static com.udromero.budget_buddy.Constants.USER_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udromero.budget_buddy.db.BudgetBuddyDAO;
import com.udromero.budget_buddy.db.BudgetBuddyDatabase;
import com.udromero.budget_buddy.db.entities.User;
import com.udromero.budget_buddy.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    SharedPreferences userSharedPreferences;

    BudgetBuddyDAO mBudgetBuddyDAO;

    TextView userNameDisplay;
    TextView userEmailDisplay;
    TextView userPasswordDisplay;

    EditText firstNameField;
    EditText lastNameField;
    EditText emailField;
    EditText passwordField;

    String newFirstName;
    String newLastName;
    String newEmail;
    String newPassword;

    int mUserId;
    User mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        getDataBase();

        getPrefs();

        // TODO: make sure to add dialog alerts when doing things like resetting budget and changing info

        // Binding
        Button updateUserInfoButton = view.findViewById(R.id.settingsUpdateUserInfo);
        Button resetBudgetButton = view.findViewById(R.id.settingsResetBudgetButton);
        Button logoutButton = view.findViewById(R.id.settingsLogoutButton);

        userNameDisplay = view.findViewById(R.id.settingsNameDisplay);
        userEmailDisplay = view.findViewById(R.id.settingsEmailDisplay);
        userPasswordDisplay = view.findViewById(R.id.settingsPasswordDisplay);

        firstNameField = view.findViewById(R.id.settingsFirstNameEditText);
        lastNameField = view.findViewById(R.id.settingsLastNameEditText);
        emailField = view.findViewById(R.id.settingsEmailEditText);
        passwordField = view.findViewById(R.id.settingsPasswordEditText);

        populateUserInfo();

        updateUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabInfoFromFields();
                updateUserInDatabase();
            }
        });

        resetBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        return view;
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    private void logoutUser(){
        SharedPreferences.Editor editor = userSharedPreferences.edit();
        editor.putInt(USER_ID_KEY, -1);
        editor.putBoolean(LOGGED_IN_KEY, false);
        editor.apply();

        Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void getPrefs() {
        userSharedPreferences = this.getActivity().getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        mUserId = userSharedPreferences.getInt(USER_ID_KEY, -1);
        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);

        if(mUser == null){
            Toast.makeText(this.getActivity().getApplicationContext(), "FATAL ERROR: Logging out...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        newFirstName = mUser.getFirstName();
        newLastName = mUser.getLastName();
        newEmail = mUser.getEmail();
        newPassword = mUser.getPassword_hash();
    }

    private void populateUserInfo(){
        String name = mUser.getFirstName() + " " + mUser.getLastName();
        userNameDisplay.setText(getString(R.string.settingsNameDisplay, name));
        userEmailDisplay.setText(getString(R.string.settingsEmailDisplay, mUser.getEmail()));
        userPasswordDisplay.setText(getString(R.string.settingsPasswordDisplay, mUser.getPassword_hash()));
    }

    private void getDataBase(){
        mBudgetBuddyDAO = Room.databaseBuilder(this.getActivity().getApplicationContext(), BudgetBuddyDatabase.class, BudgetBuddyDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .BudgetBuddyDAO();
    }

    private void grabInfoFromFields(){
        String firstName = firstNameField.getText().toString().trim();
        if(!firstName.equals("")){
            newFirstName = firstName;
        }

        String lastName = lastNameField.getText().toString().trim();
        if(!lastName.equals("")){
            newLastName = lastName;
        }

        String email = emailField.getText().toString().trim();
        if(!email.equals("")){
            newEmail = email;
        }

        String password = passwordField.getText().toString().trim();
        if(!password.equals("")){
            newPassword = password;
        }
    }

    private void updateUserInDatabase(){
        if(!newFirstName.equals(mUser.getFirstName())){
            mBudgetBuddyDAO.updateUserFirstNameById(newFirstName, mUserId);
        }

        if(!newLastName.equals(mUser.getLastName())){
            mBudgetBuddyDAO.updateUserLastNameById(newLastName, mUserId);
        }

        if(!newEmail.equals(mUser.getLastName())){
            mBudgetBuddyDAO.updateUserEmailById(newEmail, mUserId);
        }

        if(!newPassword.equals(mUser.getPassword_hash())){
            mBudgetBuddyDAO.updateUserPasswordById(newPassword, mUserId);
        }

        mUser = mBudgetBuddyDAO.getUserByUserId(mUserId);
        populateUserInfo();
    }
}