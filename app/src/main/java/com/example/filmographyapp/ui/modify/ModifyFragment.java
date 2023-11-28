package com.example.filmographyapp.ui.modify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.filmographyapp.databinding.FragmentModifyBinding;

public class ModifyFragment extends Fragment {

    private FragmentModifyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ModifyViewModel modifyViewModel =
                new ViewModelProvider(this).get(ModifyViewModel.class);

        binding = FragmentModifyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textModify;
        modifyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}