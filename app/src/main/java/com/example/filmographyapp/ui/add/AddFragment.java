package com.example.filmographyapp.ui.add;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filmographyapp.OpenDatabase;
import com.example.filmographyapp.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Accessing the EditText fields
        EditText movieTitleInput = binding.movieTitleInput;
        EditText directorInput = binding.directorInput;
        EditText releaseYearInput = binding.releaseYearInput;
        EditText genreInput = binding.genreInput;

        // Accessing the "Add Record" Button
        Button addRecordButton = binding.addRecordButton;

        // Database helper
        OpenDatabase openDatabase = new OpenDatabase(getContext());
        SQLiteDatabase database = openDatabase.getWritableDatabase();

        // "Add Record" Button OnClickListener
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = movieTitleInput.getText().toString().trim();
                String director = directorInput.getText().toString().trim();
                String releaseYear = releaseYearInput.getText().toString().trim();
                String genre = genreInput.getText().toString().trim();

                // Check if any field is empty
                if (movieTitle.isEmpty() || director.isEmpty() || releaseYear.isEmpty() || genre.isEmpty()) {
                    Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    // Call insert method and show success message
                    openDatabase.insertRecordFilmsTable(database, movieTitle, director, releaseYear, genre);
                    Toast.makeText(getContext(), "Record inserted successfully", Toast.LENGTH_SHORT).show();

                    // Clear the EditText fields after adding the record
                    movieTitleInput.setText("");
                    directorInput.setText("");
                    releaseYearInput.setText("");
                    genreInput.setText("");
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
