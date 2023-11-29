package com.example.filmographyapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.filmographyapp.databinding.FragmentListBinding;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Accessing the EditTexts
        EditText ipAddressInput = binding.titleInput;

        // Accessing the Buttons
        Button aboutButton = binding.aboutButton;
        Button calculateButton = binding.calculateButton;
        Button helpButton = binding.helpButton;

        // Accessing the TextView for results
        TextView resultTextView = binding.resultTextView;

        // Here, add any listeners or additional logic you need for the UI elements
        // For example, setting an OnClickListener for calculateButton

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}