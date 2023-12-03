package com.example.filmographyapp.ui.list;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filmographyapp.AboutActivity;
import com.example.filmographyapp.HelpActivity;
import com.example.filmographyapp.OpenDatabase;
import com.example.filmographyapp.databinding.FragmentListBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Accessing the EditText
        EditText titleInput = binding.titleInput;

        // Accessing the Buttons
        Button searchButton = binding.searchButton;
        Button listAllButton = binding.listAllButton;
        Button aboutButton = binding.aboutButton;
        Button helpButton = binding.helpButton;

        // Accessing the ListView
        ListView resultsListView = binding.resultsListView;

        try {
            OpenDatabase.copyDatabase(requireContext());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error
        }

        // Database helper
        OpenDatabase openDatabase = new OpenDatabase(getContext());
        SQLiteDatabase database = openDatabase.getWritableDatabase();

        // Search Button OnClickListener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                ArrayList<String> results = openDatabase.searchInFilmsTable(database, title);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
                resultsListView.setAdapter(adapter);
            }
        });

        // List All Button OnClickListener
        listAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> results = openDatabase.allRecordsInFilmsTable(database);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, results);
                resultsListView.setAdapter(adapter);
            }
        });

        // About Button OnClickListener
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

        // Help Button OnClickListener
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent = new Intent(getActivity(), HelpActivity.class);
                startActivity(helpIntent);
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