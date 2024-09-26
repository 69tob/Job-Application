package com.example.jobapp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class UploadJobFragment extends Fragment implements JobAdapter.JobActionListener {

    private EditText etJobTitle, etCompany, etLocation, etJobDescription;
    private Button btnUploadJob;
    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private JobDbHelper dbHelper;
    private ProgressBar progressBar;
    private static final int REQUEST_EDIT_JOB = 1;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_job, container, false);

        // Initialize UI components
        etJobTitle = view.findViewById(R.id.etJobTitle);
        etCompany = view.findViewById(R.id.etCompany);
        etLocation = view.findViewById(R.id.etLocation);
        etJobDescription = view.findViewById(R.id.etJobDescription);
        btnUploadJob = view.findViewById(R.id.btnUploadJob);
        recyclerView = view.findViewById(R.id.recyclerViewAdmin);
        progressBar = view.findViewById(R.id.progressBar);

        // Initialize database helper
        dbHelper = new JobDbHelper(getActivity());

        // Set layout manager and load jobs initially
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadJobs();

        // Set onClick listener for the upload button
        btnUploadJob.setOnClickListener(v -> uploadJob());

        return view;
    }

    private void loadJobs() {
        progressBar.setVisibility(View.VISIBLE);

        // Fetch jobs from the database and set them in the RecyclerView
        List<Job> jobList = fetchJobsFromDatabase();
        if (jobAdapter == null) {
            jobAdapter = new JobAdapter(jobList, getActivity(), true, this);
            recyclerView.setAdapter(jobAdapter);
        } else {
            jobAdapter.updateJobs(jobList);
        }
        progressBar.setVisibility(View.GONE);
    }

    private List<Job> fetchJobsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Job> jobList = new ArrayList<>();
        Cursor cursor = db.query(JobContract.JobEntry.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(JobContract.JobEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_TITLE));
            String company = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_COMPANY));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_LOCATION));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_DESCRIPTION));
            jobList.add(new Job(id, title, company, location, description));
        }
        cursor.close();
        return jobList;
    }

    private void uploadJob() {
        String title = etJobTitle.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String description = etJobDescription.getText().toString().trim();

        // Validate input fields
        if (title.isEmpty() || company.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert job details into the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JobContract.JobEntry.COLUMN_TITLE, title);
        values.put(JobContract.JobEntry.COLUMN_COMPANY, company);
        values.put(JobContract.JobEntry.COLUMN_LOCATION, location);
        values.put(JobContract.JobEntry.COLUMN_DESCRIPTION, description);

        long newRowId = db.insert(JobContract.JobEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(getActivity(), "Job uploaded successfully", Toast.LENGTH_SHORT).show();
            loadJobs();
        } else {
            Toast.makeText(getActivity(), "Failed to upload job", Toast.LENGTH_SHORT).show();
        }

        // Clear input fields after upload
        etJobTitle.setText("");
        etCompany.setText("");
        etLocation.setText("");
        etJobDescription.setText("");
    }



    @Override
    public void onDeleteJob(int jobId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = JobContract.JobEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(jobId) };
        int deletedRows = db.delete(JobContract.JobEntry.TABLE_NAME, selection, selectionArgs);
        if (deletedRows > 0) {
            Toast.makeText(getActivity(), "Job deleted successfully", Toast.LENGTH_SHORT).show();
            loadJobs();
        } else {
            Toast.makeText(getActivity(), "Failed to delete job", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onSaveJob(Job job) {
        // Implement logic to save job, e.g., update job details in the database
        // This method could be used if you have an edit job functionality

        // For now, let's just reload jobs and show a success message
        jobAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Job saved successfully", Toast.LENGTH_SHORT).show();
        loadJobs();
    }

    @Override
    public void onApplyJob(Job job) {
        Toast.makeText(getActivity(), "Job applied successfully", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onEditJob(Job job) {
        Intent intent = new Intent(getActivity(), EditJobActivity.class);
        intent.putExtra("JOB_ID", job.getId());
        startActivityForResult(intent, REQUEST_EDIT_JOB); // Use startActivityForResult to get result back
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_JOB && resultCode == RESULT_OK) {
            loadJobs(); // Reload jobs after editing
        }
    }

}
