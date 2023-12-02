package com.example.filmographyapp.ui.modify;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filmographyapp.ModifyMovieActivity;
import com.example.filmographyapp.OpenDatabase;
import com.example.filmographyapp.databinding.FragmentModifyBinding;

import java.util.ArrayList;

public class ModifyFragment extends Fragment {

    private FragmentModifyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentModifyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the database and get the data
        OpenDatabase openDatabase = new OpenDatabase(getContext());
        SQLiteDatabase database = openDatabase.getWritableDatabase();
        ArrayList<String> movieList = openDatabase.allRecordsInFilmsTable(database); // Implement this method
        ArrayList<String> onlyTitlesList = openDatabase.getAllMovies(database);

        // Set up the ListView
        ListView listView = binding.modifyListView;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, movieList);
        listView.setAdapter(adapter);

        // Set an item click listener on the ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Handle the item click here
            String selectedMovie = onlyTitlesList.get(position);
            ArrayList<String> movieDetails = openDatabase.getMovieDetails(database, selectedMovie);

            // Create an Intent to start ModifyMovieActivity
            Intent intent = new Intent(getContext(), ModifyMovieActivity.class);

            // Add movieDetails to the intent
            intent.putStringArrayListExtra("movieDetails", movieDetails);

            // Start the activity
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
