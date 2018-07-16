package com.comp3350.rev_u_hub.presentation_layer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationFailedException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordMismatchException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationUsernameConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataWrongPasswordException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

public class CreateUserActivity extends AppCompatActivity {

    private Button createButton;
    private UserLogin userLoginOB;
    private UserObject userObject;
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        createButton = (Button)findViewById(R.id.createUserButton);
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText newUsername = (EditText)findViewById(R.id.createUsernameField);
                EditText newPassword = (EditText)findViewById(R.id.createPasswordField);
                EditText confirmPassword = (EditText)findViewById(R.id.confirmPasswordField);
                try {
                    accountManager = Services.getAccountManager();
                    accountManager.createUser(newUsername.getText().toString(), newPassword.getText().toString(), confirmPassword.getText().toString());
                    try {
                        userLoginOB = Services.getUserLogin();
                        userObject = userLoginOB.userLogin(newUsername.getText().toString(), newPassword.getText().toString());
                        try {
                            Intent homeIntent = new Intent(view.getContext(), HomeActivity.class);
                            startActivity(homeIntent);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    catch(UserDataException e) {
                        String userMessage;
                        if(e instanceof UserDataNotFoundException){
                            userMessage = "No user with that username was found.";
                        }
                        else if(e instanceof UserDataWrongPasswordException) {
                            userMessage = "Username or Password was incorrect.";
                        }
                        else {
                            userMessage = "Something went wrong on our end.";
                        }
                        Toast.makeText(CreateUserActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                        System.out.println(e.getMessage());
                    }
                }
                catch (UserCreationException e) {
                    String userMessage;
                    if(e instanceof UserCreationFailedException) {
                        userMessage = "Creation Failed.";
                    }
                    else if(e instanceof UserCreationDuplicateException) {
                        userMessage = "User already exists.";
                    }
                    else if(e instanceof UserCreationUsernameConstraintException) {
                        userMessage = "Username must not be empty.";
                    }
                    else if(e instanceof UserCreationPasswordConstraintException) {
                        userMessage = "Password must not be empty.";
                    }
                    else if(e instanceof UserCreationPasswordMismatchException) {
                        userMessage = "The passwords given do not match.";
                    }
                    else {
                        userMessage = "Something went wrong on our end.";
                    }
                    Toast.makeText(CreateUserActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        });
    }

}
