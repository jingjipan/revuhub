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

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SearchView searchBar;
    private MovieSearch movieSearch;
    private UserLogin userLoginOB;
    private UserObject userObject;
    private TextView currentUsername;
    private Button userProfileButton;
    private String currentUserString;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up temporary fake database
        movieSearch = Services.getMovieSearch();

        currentUsername = (TextView)findViewById(R.id.currentUsername);

        try {
            userLoginOB = Services.getUserLogin();
            userObject = userLoginOB.getUser();
            currentUserString = userObject.getUserName();
            currentUsername.setText("Welcome " + currentUserString + "!");
        }
        catch (UserDataNotFoundException e) {
            Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        searchBar = (SearchView)findViewById(R.id.movieSearch);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        userProfileButton = (Button)findViewById(R.id.goToProfile);
        userProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(view.getContext(), ProfileActivity.class);
                profileIntent.putExtra("userName", currentUserString);
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

}

