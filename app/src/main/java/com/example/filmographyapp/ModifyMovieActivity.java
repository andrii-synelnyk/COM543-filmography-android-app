package com.example.filmographyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class ModifyMovieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_movie);

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the movie details from the intent
        ArrayList<String> movieDetails = getIntent().getStringArrayListExtra("movieDetails");

        // Retrieve each EditText view
        EditText movieTitleModifyInput = findViewById(R.id.movieTitleModifyInput);
        EditText directorModifyInput = findViewById(R.id.directorModifyInput);
        EditText releaseYearModifyInput = findViewById(R.id.releaseYearModifyInput);
        EditText genreModifyInput = findViewById(R.id.genreModifyInput);

        // Check if movieDetails is not null and has enough data
        if (movieDetails != null && movieDetails.size() >= 4) {
            // Set the text of each EditText
            movieTitleModifyInput.setText(movieDetails.get(1)); // movie name
            directorModifyInput.setText(movieDetails.get(2));  // director
            releaseYearModifyInput.setText(movieDetails.get(3)); // release year
            genreModifyInput.setText(movieDetails.get(4));      // genre
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Respond to the action bar's Up/Home button
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}