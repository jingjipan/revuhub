package com.comp3350.rev_u_hub.PresentationLayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorage;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private SearchView movieSearch;

    //Set up temporary fake database
    protected fakeStorage db = new fakeStorage();

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

        movieSearch = (SearchView)findViewById(R.id.movieSearch);
        movieSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String movieName) {
                if(db.searchMovie(movieName) != null) {
                    showMovieOverview(movieName);
                } else {
                    Toast.makeText(MainActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
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

