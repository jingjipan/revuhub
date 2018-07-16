package com.comp3350.rev_u_hub.presentation_layer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SearchView movieSearchBar;
    private SearchView userSearchBar;
    private MovieSearch movieSearch;
    private UserSearch userSearch;
    private UserLogin userLoginOB;
    private UserObject userObject;
    private TextView currentUsername;
    private Button userProfileButton;
    private String currentUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieSearch = Services.getMovieSearch();

        currentUsername = (TextView)findViewById(R.id.currentUsername);

        try {
            userLoginOB = Services.getUserLogin();
            userObject = userLoginOB.getUser();
            currentUserString = userObject.getUserName();
            currentUsername.setText("Welcome " + currentUserString + "!");
        }
        catch (UserDataNotFoundException e) {
            Toast.makeText(HomeActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
        }

        movieSearchBar = (SearchView)findViewById(R.id.movieSearch);
        movieSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String movieName) {
                if(!movieSearch.getMovie(movieName).isEmpty()) {
                    showMovieOverview(movieName);
                } else {
                    Toast.makeText(HomeActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        // User Search
        userSearch = Services.getUserSearch();

        userSearchBar = (SearchView)findViewById(R.id.userSearch);
        userSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String username) {
                if(!userSearch.getUserSimple(username).isEmpty()) {
                    showUserProfile(username);
                } else {
                    Toast.makeText(HomeActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        userProfileButton = (Button)findViewById(R.id.goToProfile);
        userProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(view.getContext(), ProfileActivity.class);
                profileIntent.putExtra("username", currentUserString);
                startActivity(profileIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showMovieOverview(String movieName) {
        Intent movieIntent = new Intent(this, MovieOverviewActivity.class);
        movieIntent.putExtra("movieName", movieName);
        this.startActivity(movieIntent);
    }

    private void showUserProfile(String username) {
        Intent userIntent = new Intent(this, ProfileActivity.class);
        userIntent.putExtra("username", username);
        this.startActivity(userIntent);
    }

}

