package com.example.jobapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ApplyJobActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private EditText applicantName, applicantEmail, applicantPhone, applicantAddress;
    private TextView jobTitle, jobCompany, jobLocation, jobDescription, tvResumeFileName;
    private Button applyButton, btnUploadResume;
    private Uri resumeUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        applicantName = findViewById(R.id.applicantName);
        applicantEmail = findViewById(R.id.applicantEmail);
        applicantPhone = findViewById(R.id.applicantPhone);
        applicantAddress = findViewById(R.id.applicantAddress);
        jobTitle = findViewById(R.id.jobTitle);
        jobCompany = findViewById(R.id.jobCompany);
        jobLocation = findViewById(R.id.jobLocation);
        jobDescription = findViewById(R.id.jobDescription);
        tvResumeFileName = findViewById(R.id.tvResumeFileName);
        applyButton = findViewById(R.id.applyButton);
        btnUploadResume = findViewById(R.id.btnUploadResume);

        Intent intent = getIntent();
        String title = intent.getStringExtra("JOB_TITLE");
        String company = intent.getStringExtra("JOB_COMPANY");
        String location = intent.getStringExtra("JOB_LOCATION");
        String description = intent.getStringExtra("JOB_DESCRIPTION");

        jobTitle.setText(title);
        jobCompany.setText(company);
        jobLocation.setText(location);
        jobDescription.setText(description);

        btnUploadResume.setOnClickListener(v -> openFileChooser());
        applyButton.setOnClickListener(v -> applyForJob());
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allows all file types
        startActivityForResult(Intent.createChooser(intent, "Select Resume"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            resumeUri = data.getData();
            String fileName = getFileName(resumeUri);
            tvResumeFileName.setText(fileName);
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    cursor.moveToFirst();
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                } finally {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void applyForJob() {
        String name = applicantName.getText().toString().trim();
        String email = applicantEmail.getText().toString().trim();
        String phone = applicantPhone.getText().toString().trim();
        String address = applicantAddress.getText().toString().trim();
        String filePath = resumeUri != null ? getFileName(resumeUri) : "";

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || filePath.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean isInserted = databaseHelper.addResume(name, filePath);

        if (isInserted) {
            Toast.makeText(this, "Applied successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to apply", Toast.LENGTH_SHORT).show();
        }

        applicantName.setText("");
        applicantEmail.setText("");
        applicantPhone.setText("");
        applicantAddress.setText("");
        tvResumeFileName.setText("");
        resumeUri = null;
    }
}
