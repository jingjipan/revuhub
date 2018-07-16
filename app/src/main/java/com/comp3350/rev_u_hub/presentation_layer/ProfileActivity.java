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
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieLists;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserMovieProfile;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends ListActivity {

    private String userName;
    private UserSearch accessUser;
    private MovieLists movieList;
    private List<MovieObject> topMovies;

    private ArrayAdapter<String> adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accessUser = Services.getUserSearch();

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        final UserObject user = accessUser.getUserSimple(userName);
        userName = user.getUserName();

        TextView userTextComponent = (TextView)findViewById(R.id.currentProfileName);
        userTextComponent.setText(userName);

        try {
            movieList = Services.getMovieLists();
            topMovies = movieList.getMovieList(3);
            List<String> topMovieNames = new ArrayList<>();
            for(int i=0; i<topMovies.size(); i++) {
                topMovieNames.add(topMovieNames.size(),topMovies.get(i).getTitle());
            }
            adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, topMovieNames);
        }
        catch (MovieDataNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(ProfileActivity.this,"No movies were found.", Toast.LENGTH_SHORT).show();
        }

        lv = getListView();
        lv.setAdapter(adapter);


    }

}
