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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.filmographyapp.ModifyMovieActivity;
import com.example.filmographyapp.OpenDatabase;
import com.example.filmographyapp.databinding.FragmentModifyBinding;

import java.util.ArrayList;

public class ModifyFragment extends Fragment {

    private FragmentModifyBinding binding;
    private ArrayAdapter<String> adapter;
    private OpenDatabase openDatabase;
    private SQLiteDatabase database;

    private final ActivityResultLauncher<Intent> modifyMovieActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == ModifyMovieActivity.RESULT_OK) {
                    // Refresh the ListView
                    ArrayList<String> updatedMovieList = openDatabase.allRecordsInFilmsTable(database);
                    adapter.clear();
                    adapter.addAll(updatedMovieList);
                    adapter.notifyDataSetChanged();
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentModifyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        openDatabase = new OpenDatabase(getContext());
        database = openDatabase.getWritableDatabase();
        ArrayList<String> movieList = openDatabase.allRecordsInFilmsTable(database);
        ArrayList<String> onlyIdsList = openDatabase.getAllIds(database);

        ListView listView = binding.modifyListView;
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, movieList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMovieId = onlyIdsList.get(position);

            Intent intent = new Intent(getContext(), ModifyMovieActivity.class);
            intent.putExtra("selectedMovieId", selectedMovieId);
            modifyMovieActivityResultLauncher.launch(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
