package com.example.filmographyapp.ui.list;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filmographyapp.OpenDatabase;
import com.example.filmographyapp.databinding.FragmentListBinding;

import java.io.IOException;
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

        // Accessing the TextView for results
        TextView resultTextView = binding.resultTextView;

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
                String result = openDatabase.searchInFilmsTable(database, title);
                resultTextView.setText(result);
            }
        });

        // List All Button OnClickListener
        listAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = openDatabase.allRecordsInFilmsTable(database);
                resultTextView.setText(result);
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