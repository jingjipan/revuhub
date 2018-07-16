package com.comp3350.rev_u_hub.presentation_layer;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.comp3350.rev_u_hub.Application.Main;
import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataWrongPasswordException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button createNavButton;
    private UserLogin userLoginOB;
    private UserObject currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        copyDatabaseToDevice();

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText currentUsername = (EditText)findViewById(R.id.loginUsernameField);
                EditText currentPassword = (EditText)findViewById(R.id.loginPasswordField);

                try {
                    userLoginOB = Services.getUserLogin();
                    currentUser = userLoginOB.userLogin(currentUsername.getText().toString(), currentPassword.getText().toString());
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
                    Toast.makeText(LoginActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                    System.out.println(e.getMessage());
                }
            }
        });

        createNavButton = (Button)findViewById(R.id.createNavButton);
        createNavButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent createUserIntent = new Intent(view.getContext(), CreateUserActivity.class);
                startActivity(createUserIntent);
            }
        });

    }


    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            System.out.println("Unable to access application data: "+ ioe.getMessage());
        }
    }


    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }


}


