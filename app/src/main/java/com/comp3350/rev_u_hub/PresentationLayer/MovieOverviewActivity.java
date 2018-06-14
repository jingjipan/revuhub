package com.comp3350.rev_u_hub.PresentationLayer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.comp3350.rev_u_hub.R;

public class MovieOverviewActivity extends AppCompatActivity {

    private ImageView moviePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        // Example of how to get passed in movie name:
        Intent intent = getIntent();
        String movieName = intent.getStringExtra("movieName");
        moviePicture = (ImageView)findViewById(R.id.movieImage);

        int resourceId = getResources().getIdentifier(movieName.toLowerCase(), "drawable", getPackageName());
        moviePicture.setImageResource(resourceId);

    }
}
