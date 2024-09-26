package com.example.jobapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.jobapp.R.id.btnUploadJob;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class admin_job extends AppCompatActivity {

    private EditText etJobTitle, etCompany, etLocation, etJobDescription;
    private Button btnUploadJob;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job);  // Ensure the layout name matches your XML file

        // Initialize views
        etJobTitle = findViewById(R.id.etJobTitle);
        etCompany = findViewById(R.id.etCompany);
        etLocation = findViewById(R.id.etLocation);
        etJobDescription = findViewById(R.id.etJobDescription);
        btnUploadJob = findViewById(R.id.btnUploadJob);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewAdmin = findViewById(R.id.recyclerViewAdmin);

        // Set up click listener for the upload button
        btnUploadJob.setOnClickListener(v -> {
            // Handle job upload action here, such as saving to a database or Firebase
        });

        // Optionally, set up the RecyclerView for displaying jobs

    }
}
