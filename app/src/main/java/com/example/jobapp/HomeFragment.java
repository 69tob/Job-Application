package com.example.jobapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomePage";
    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private JobDbHelper dbHelper;
    private ProgressBar progressBar;
    private EditText etSearch;
    private List<Job> jobList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewUser);
        progressBar = view.findViewById(R.id.progressBar);
        etSearch = view.findViewById(R.id.etSearch);

        dbHelper = new JobDbHelper(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load jobs initially
        loadJobs();

        // Set up search functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterJobs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void loadJobs() {
        // Show the progress bar while loading jobs
        progressBar.setVisibility(View.VISIBLE);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                JobContract.JobEntry._ID,
                JobContract.JobEntry.COLUMN_TITLE,
                JobContract.JobEntry.COLUMN_COMPANY,
                JobContract.JobEntry.COLUMN_LOCATION,
                JobContract.JobEntry.COLUMN_DESCRIPTION
        };

        Cursor cursor = null;

        try {
            cursor = db.query(
                    JobContract.JobEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            jobList.clear();  // Clear the existing list
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(JobContract.JobEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_TITLE));
                String company = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_COMPANY));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_LOCATION));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_DESCRIPTION));

                jobList.add(new Job(id, title, company, location, description));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading jobs", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Hide the progress bar after jobs are loaded
        progressBar.setVisibility(View.GONE);

        // Initialize and set the adapter with JobActionListener
        jobAdapter = new JobAdapter(jobList, getContext(), false, new JobAdapter.JobActionListener() {
            @Override
            public void onEditJob(Job job) {
                // Handle edit job
            }

            @Override
            public void onDeleteJob(int jobId) {
                // Handle delete job
            }

            @Override
            public void onSaveJob(Job job) {
                // Handle save job
            }

            @Override
            public void onApplyJob(Job job) {
                Intent intent = new Intent(getContext(), ApplyJobActivity.class);
                intent.putExtra("JOB_ID", job.getId());
                intent.putExtra("JOB_TITLE", job.getTitle());
                intent.putExtra("JOB_COMPANY", job.getCompany());
                intent.putExtra("JOB_LOCATION", job.getLocation());
                intent.putExtra("JOB_DESCRIPTION", job.getDescription());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(jobAdapter);
    }

    private void filterJobs(String query) {
        List<Job> filteredList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    job.getCompany().toLowerCase().contains(query.toLowerCase()) ||
                    job.getLocation().toLowerCase().contains(query.toLowerCase()) ||
                    job.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(job);
            }
        }
        jobAdapter.updateJobs(filteredList);
    }
}
