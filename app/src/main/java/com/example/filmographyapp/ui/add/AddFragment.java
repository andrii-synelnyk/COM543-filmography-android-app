package com.example.filmographyapp.ui.add;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
                String movieTitle = movieTitleInput.getText().toString();
                String director = directorInput.getText().toString();
                String releaseYear = releaseYearInput.getText().toString();
                String genre = genreInput.getText().toString();

                // Here, you would add the logic to insert a new record into the database
                // For example: openDatabase.addRecordToFilmsTable(database, movieTitle, director, releaseYear, genre);

                // Optionally, clear the EditText fields after adding the record
                movieTitleInput.setText("");
                directorInput.setText("");
                releaseYearInput.setText("");
                genreInput.setText("");
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
