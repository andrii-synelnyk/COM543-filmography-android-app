package com.example.filmographyapp.ui.modify;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
        ArrayList<String> movieList = openDatabase.getAllMovies(database); // Implement this method

        // Set up the ListView
        ListView listView = binding.modifyListView;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, movieList);
        listView.setAdapter(adapter);

        // Set an item click listener on the ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Handle the item click here. For example, open a new fragment or activity
            // to modify the selected item.
            String selectedMovie = movieList.get(position);
            // Implement the logic to modify the selectedMovie
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
