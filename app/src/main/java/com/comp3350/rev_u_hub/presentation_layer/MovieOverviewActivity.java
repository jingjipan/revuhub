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
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
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
                            Toast.makeText(MovieOverviewActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch(UserDataNotFoundException e) {
                    System.out.println(e.getMessage());
                }

            }
        });



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserLogin userLogin = Services.getUserLogin();
//
//                try{
//                    UserObject currUser = userLogin.getUser();
//
//                    if(!alreadyReviewed(currUser)) {
//                        // if a new review is made
//                        newReviewCreated = getNewReview();
//                        if(newReviewCreated) {
//
//                        }
//
//
//                        reviewManager = Services.getReviewManager();
//                        try {
//                            reviewManager.createReview(reviewText, movieName, currUser.getUserName());
//                        } catch(ReviewCreationException e) {
//                            System.out.println(e.getMessage());
//                        }
//                    } else {
//                        // User has already reviewed it, show toast
//                        Toast.makeText(MovieOverviewActivity.this, "You have already reviewed this movie", Toast.LENGTH_SHORT).show();
//                    }
//                } catch(UserDataNotFoundException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        });
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
            System.out.println(e.getMessage());
        }
    }


//    public boolean getNewReview() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MovieOverviewActivity.this);
//        builder.setTitle("New Review");
//
//        // Set up the input
//        final EditText input = new EditText(MovieOverviewActivity.this);
//        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(input);
//
//        newReviewCreated = false;
//        // Set up the buttons
//        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                newReviewCreated = true;
//                reviewText = input.getText().toString();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                newReviewCreated = true;
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//        return newReviewCreated;
//    }

}