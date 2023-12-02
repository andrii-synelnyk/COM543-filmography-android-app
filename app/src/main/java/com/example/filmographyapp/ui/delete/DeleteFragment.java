package com.example.filmographyapp.ui.delete;

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

public class DeleteFragment extends Fragment{

    private FragmentAddBinding binding;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

