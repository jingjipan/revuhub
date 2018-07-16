package com.comp3350.rev_u_hub.presentation_layer;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationFailedException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationNoUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoMovieException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataWrongUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;

import android.view.ViewGroup.MarginLayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import java.util.List;

public class MovieOverviewActivity extends ListActivity {

    private MovieSearch accessMovies;
    private ReviewManager reviewManager;
    private String movieName;
    List<String> reviews;

    private ListView lv;
    private ArrayAdapter<String> adapter;

    private String reviewText = "";
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);



        accessMovies = Services.getMovieSearch();

        Intent intent = getIntent();
        movieName = intent.getStringExtra("movieName");
        final MovieObject movie = accessMovies.getMovie(movieName);
        movieName = movie.getTitle();

        //Set Movie Title
        TextView movieTextComponent = (TextView)findViewById(R.id.movieTitle);
        movieTextComponent.setText(movieName);

        //Set image for current movie
        ImageView moviePicture = (ImageView)findViewById(R.id.movieImage);
        moviePicture.setImageURI(Uri.parse(movie.getPic()));

        //Set Movie Cast
        movieTextComponent = (TextView)findViewById(R.id.movieCast);
        movieTextComponent.setText("Cast: " + movie.getCast());

        //Set Movie Synopsis
        movieTextComponent = (TextView)findViewById(R.id.movieSynopsis);
        movieTextComponent.setText(movie.getSynopsis());

        //Set Movie
        reviews = null;
        setListViewContent(movie);

        lv = getListView();
        lv.setAdapter(adapter);


        // enable nested scrolling for the listview
        lv.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText review = (EditText)findViewById(R.id.movieReview);
                try{
                    UserLogin userLogin = Services.getUserLogin();
                    UserObject currUser = userLogin.getUser();

                    reviewText = review.getText().toString();

                    if(review != null && !reviewText.equals("")) {
                        reviewManager = Services.getReviewManager();
                        try {
                            reviewManager.createReview(reviewText, movieName, currUser.getUserName());
                            updateAdapter(movie);

                            review.setText(""); // clear the review field
                            Toast.makeText(MovieOverviewActivity.this,"Review Submitted", Toast.LENGTH_SHORT).show();
                        } catch(ReviewCreationException e) {
                            System.out.println(e.getMessage());
                            String userMessage;
                            if(e instanceof ReviewCreationDuplicateException) {
                                userMessage = "You have already submitted a review.";
                            }
                            else if(e instanceof ReviewCreationFailedException) {
                                userMessage = "The review could not be created.";
                            }
                            else if(e instanceof ReviewCreationNoUserException) {
                                userMessage = "The selected user is not logged in.";
                            }
                            else {
                                userMessage = "Something went wrong on our end.";
                            }
                            Toast.makeText(MovieOverviewActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch(UserDataNotFoundException e) {
                    System.out.println(e.getMessage());
                    Toast.makeText(MovieOverviewActivity.this, "No user with that username was found.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setListViewContent(MovieObject movie) {
        try {
            reviews = Services.getReviewSearch().getReviewsText(movie);
            adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, reviews);
        } catch (ReviewDataException e) {
            e.printStackTrace();
        }
    }

    private void updateAdapter(MovieObject movie) {
        adapter.clear();
        try{
            adapter.addAll(Services.getReviewSearch().getReviewsText(movie));
            adapter.notifyDataSetChanged();
        } catch(ReviewDataException e) {
            String userMessage;
            if(e instanceof ReviewDataNotFoundException) {
                userMessage = "No reviews exist for the selected movie.";
            }
            else if(e instanceof ReviewDataNoMovieException) {
                userMessage = "The selected movie does not exist.";
            }
            else if(e instanceof ReviewDataNoUserException) {
                userMessage = "The selected user does not exist.";
            }
            else if(e instanceof ReviewDataWrongUserException) {
                userMessage = "The selected user is not logged in.";
            }
            else {
                userMessage = "Something went wrong on our end.";
            }
            Toast.makeText(MovieOverviewActivity.this, userMessage, Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }
    }


}