package com.example.filmographyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        String selectedMovieId = getIntent().getStringExtra("selectedMovieId");

        // Database helper
        OpenDatabase openDatabase = new OpenDatabase(this);
        SQLiteDatabase database = openDatabase.getWritableDatabase();
        ArrayList<String> movieDetails = openDatabase.getMovieDetailsById(database, selectedMovieId);


        // Retrieve each EditText view
        EditText movieTitleModifyInput = findViewById(R.id.movieTitleModifyInput);
        EditText directorModifyInput = findViewById(R.id.directorModifyInput);
        EditText releaseYearModifyInput = findViewById(R.id.releaseYearModifyInput);
        EditText genreModifyInput = findViewById(R.id.genreModifyInput);

        Button modifyRecordButton = findViewById(R.id.modifyRecordButton);

        // Check if movieDetails is not null and has enough data
        if (movieDetails != null && movieDetails.size() >= 4) {
            // Set the text of each EditText
            movieTitleModifyInput.setText(movieDetails.get(1)); // movie name
            directorModifyInput.setText(movieDetails.get(2));  // director
            releaseYearModifyInput.setText(movieDetails.get(3)); // release year
            genreModifyInput.setText(movieDetails.get(4));      // genre
        }

        modifyRecordButton.setOnClickListener(v -> {
            // Collect updated values
            String id = movieDetails.get(0); // Assuming the first element is the ID
            String updatedTitle = movieTitleModifyInput.getText().toString().trim();
            String updatedDirector = directorModifyInput.getText().toString().trim();
            String updatedReleaseYear = releaseYearModifyInput.getText().toString().trim();
            String updatedGenre = genreModifyInput.getText().toString().trim();

            // Check if any field is empty
            if (updatedTitle.isEmpty() || updatedDirector.isEmpty() || updatedReleaseYear.isEmpty() || updatedGenre.isEmpty()) {
                Toast.makeText(ModifyMovieActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            } else {
                // All fields are filled, proceed to update
                openDatabase.updateMovieRecord(database, id, updatedTitle, updatedDirector, updatedReleaseYear, updatedGenre);
                Toast.makeText(ModifyMovieActivity.this, "Record modified successfully", Toast.LENGTH_SHORT).show();

                // After modifying the record successfully
                setResult(ModifyMovieActivity.RESULT_OK);

                // Close the activity
                finish();
            }
        });
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