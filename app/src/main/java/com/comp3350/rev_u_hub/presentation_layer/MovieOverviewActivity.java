package com.comp3350.rev_u_hub.presentation_layer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.R;
import com.comp3350.rev_u_hub.logic_layer.MovieAccess;

import java.util.List;

public class MovieOverviewActivity extends AppCompatActivity {

    private MovieAccess accessMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        accessMovies = Services.getMovieAccess();

        Intent intent = getIntent();
        String movieName = intent.getStringExtra("movieName");
        MovieObject movie = accessMovies.getMovie(movieName);
        movieName = movie.getTitle();

        //Set Movie Title
        TextView movieTextComponent = (TextView)findViewById(R.id.movieTitle);
        movieTextComponent.setText(movieName);

        //Set image for current movie
        String moviePictureName = movieName.replaceAll("\\s","");
        int resourceId = getResources().getIdentifier(moviePictureName.toLowerCase(), "drawable", getPackageName());
        ImageView moviePicture = (ImageView)findViewById(R.id.movieImage);
        moviePicture.setImageResource(resourceId);

        //Set Movie Cast
        movieTextComponent = (TextView)findViewById(R.id.movieCast);
        movieTextComponent.setText("Cast: " + movie.getCast());

        //Set Movie Synopsis
        movieTextComponent = (TextView)findViewById(R.id.movieSynopsis);
        movieTextComponent.setText(movie.getSynopsis());

        //Set Movie
        List<String> reviews = movie.getReviews();

        movieTextComponent = (TextView)findViewById(R.id.movieReview1);
        movieTextComponent.setText(reviews.get(0));
        movieTextComponent = (TextView)findViewById(R.id.movieReview2);
        movieTextComponent.setText(reviews.get(1));
        movieTextComponent = (TextView)findViewById(R.id.movieReview3);
        movieTextComponent.setText(reviews.get(2));
    }
}
