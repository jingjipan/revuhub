package com.comp3350.rev_u_hub.presentation_layer;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.UserMovieStats;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataWrongPasswordException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieLists;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserMovieProfile;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

import org.hsqldb.rights.User;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends ListActivity {

    private String username;
    private UserSearch accessUser;
    private MovieLists movieList;
    private List<MovieObject> topMovies;
    private UserLogin userLoginOB;
    private UserObject userObject;

    private ArrayAdapter<String> adapter;
    private ListView lv;

    private Button passwordBtn;
    private EditText passwordField;
    private TextView changePasswordText;

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accessUser = Services.getUserSearch();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        final UserObject user = accessUser.getUserSimple(username);
        username = user.getUserName();

        TextView userTextComponent = (TextView) findViewById(R.id.currentProfileName);
        userTextComponent.setText(username);

        try {
            movieList = Services.getMovieLists();
            topMovies = movieList.getMovieList(3);
            List<String> topMovieNames = new ArrayList<>();
            for (int i = 0; i < topMovies.size(); i++) {
                topMovieNames.add(topMovieNames.size(), topMovies.get(i).getTitle());
            }
            adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, topMovieNames);
        } catch (MovieDataNotFoundException e) {
            Toast.makeText(ProfileActivity.this, "No movies were found", Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

        lv = getListView();
        lv.setAdapter(adapter);

        try {
            userLoginOB = Services.getUserLogin();
            userObject = userLoginOB.getUser();
        } catch (UserDataNotFoundException e) {
            Toast.makeText(ProfileActivity.this, "No user currently logged in", Toast.LENGTH_SHORT).show();
        }

        // If this user profile is for the currently logged in user
        if (userObject != null && !userObject.isEmpty() && username.equals(userObject.getUserName())) {
            // Allow them to change their username and password
            passwordBtn = (Button) findViewById(R.id.passwordBtn);
            passwordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        accountManager = Services.getAccountManager();
                        String oldPass = ((EditText)findViewById(R.id.oldPassword)).getText().toString();
                        String newPass = ((EditText)findViewById(R.id.newPassword)).getText().toString();

                        accountManager.changePassword(username, oldPass, newPass);
                        Toast.makeText(ProfileActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    } catch(Exception e) {
                        // UserDataException e, UserCreationPasswordConstraintException f
                        if(e instanceof UserDataException) {
                            Toast.makeText(ProfileActivity.this, "Username/password combination could not be found", Toast.LENGTH_SHORT).show();
                        } else if(e instanceof UserCreationPasswordConstraintException) {
                            Toast.makeText(ProfileActivity.this, "New password is invalid", Toast.LENGTH_SHORT).show();
                        }
                        System.out.println(e.getMessage());
                    }
                }
            });

        } else {
            // Remove the options to change a users password
            changePasswordText = (TextView) findViewById(R.id.ChangePassword);
            changePasswordText.setVisibility(View.GONE);

            passwordBtn = (Button) findViewById(R.id.passwordBtn);
            passwordBtn.setVisibility(View.GONE);

            passwordField = (EditText) findViewById(R.id.oldPassword);
            passwordField.setVisibility(View.GONE);

            passwordField = (EditText) findViewById(R.id.newPassword);
            passwordField.setVisibility(View.GONE);
        }
    }
}
