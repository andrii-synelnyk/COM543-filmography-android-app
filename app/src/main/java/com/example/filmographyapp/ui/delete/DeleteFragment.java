package com.example.filmographyapp.ui.delete;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filmographyapp.OpenDatabase;
import com.example.filmographyapp.R;
import com.example.filmographyapp.databinding.FragmentDeleteBinding;

import java.util.ArrayList;
import java.util.List;

public class DeleteFragment extends Fragment {

    private FragmentDeleteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeleteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Accessing the ListView and Delete Button
        ListView moviesListView = binding.moviesListView;
        Button deleteButton = binding.deleteButton;

        // Database helper
        OpenDatabase openDatabase = new OpenDatabase(getContext());
        SQLiteDatabase database = openDatabase.getWritableDatabase();

        // Fetch movies from the database
        List<String> moviesList = openDatabase.getAllMovieNames(database); // You'll need to implement this method

        // Setting up the ArrayAdapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, moviesList);
        moviesListView.setAdapter(adapter);
        moviesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Delete Button OnClickListener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a list to keep track of items to be deleted
                List<String> itemsToDelete = new ArrayList<>();

                // Get selected items from the ListView and add them to the itemsToDelete list
                for (int i = 0; i < moviesListView.getCount(); i++) {
                    if (moviesListView.isItemChecked(i)) {
                        itemsToDelete.add(moviesList.get(i));
                    }
                }

                // Delete the selected items
                for (String item : itemsToDelete) {
                    openDatabase.deleteRecord(database, item);
                    moviesList.remove(item);
                }

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();

                // Clear all selections
                moviesListView.clearChoices();

                // Set the ListView to CHOICE_MODE_NONE and then back to CHOICE_MODE_MULTIPLE to refresh the view
                moviesListView.setChoiceMode(ListView.CHOICE_MODE_NONE);
                moviesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                Toast.makeText(getContext(), "Selected movies deleted", Toast.LENGTH_SHORT).show();
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
