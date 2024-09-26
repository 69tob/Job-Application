package com.example.jobapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditJobActivity extends AppCompatActivity {

    private EditText etJobTitle, etCompany, etLocation, etJobDescription;
    private Button btnSave;
    private JobDbHelper dbHelper;
    private int jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job); // Use the corrected layout

        etJobTitle = findViewById(R.id.etJobTitle);
        etCompany = findViewById(R.id.etCompany);
        etLocation = findViewById(R.id.etLocation);
        etJobDescription = findViewById(R.id.etJobDescription);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new JobDbHelper(this);

        // Get job ID from intent
        jobId = getIntent().getIntExtra("JOB_ID", -1);

        if (jobId != -1) {
            loadJobDetails(jobId);
        } else {
            Toast.makeText(this, "Invalid job ID", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if jobId is invalid
        }

        btnSave.setOnClickListener(v -> saveJob());
    }

    private void loadJobDetails(int jobId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                JobContract.JobEntry.COLUMN_TITLE,
                JobContract.JobEntry.COLUMN_COMPANY,
                JobContract.JobEntry.COLUMN_LOCATION,
                JobContract.JobEntry.COLUMN_DESCRIPTION
        };

        String selection = JobContract.JobEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(jobId) };

        Cursor cursor = db.query(
                JobContract.JobEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_TITLE));
            String company = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_COMPANY));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_LOCATION));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(JobContract.JobEntry.COLUMN_DESCRIPTION));

            etJobTitle.setText(title);
            etCompany.setText(company);
            etLocation.setText(location);
            etJobDescription.setText(description);
        } else {
            Toast.makeText(this, "Job not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if job is not found
        }
        cursor.close();
    }

    private void saveJob() {
        String title = etJobTitle.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String description = etJobDescription.getText().toString().trim();

        if (title.isEmpty() || company.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JobContract.JobEntry.COLUMN_TITLE, title);
        values.put(JobContract.JobEntry.COLUMN_COMPANY, company);
        values.put(JobContract.JobEntry.COLUMN_LOCATION, location);
        values.put(JobContract.JobEntry.COLUMN_DESCRIPTION, description);

        String selection = JobContract.JobEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(jobId) };

        int count = db.update(JobContract.JobEntry.TABLE_NAME, values, selection, selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Job updated successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        } else {
            Toast.makeText(this, "Failed to update job", Toast.LENGTH_SHORT).show();
        }
    }

}
