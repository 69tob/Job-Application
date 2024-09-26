package com.example.jobapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminResumeListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ResumeAdapter resumeAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_resume_list, container, false);

        recyclerView = view.findViewById(R.id.listViewResumes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseHelper = new DatabaseHelper(getActivity());

        loadResumes();

        return view;
    }

    private void loadResumes() {
        Cursor cursor = databaseHelper.getAllResumes();
        if (cursor != null) {
            resumeAdapter = new ResumeAdapter(getActivity(), cursor);
            recyclerView.setAdapter(resumeAdapter);
        } else {
            Toast.makeText(getActivity(), "Failed to load resumes", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadResumes(); // Reload resumes when fragment is resumed
    }
}
