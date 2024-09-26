package com.example.jobapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UserJob extends AppCompatActivity {

    private TextView tvJobTitle, tvCompany, tvLocation, tvDescription;
    private Job job;
    private boolean isAdmin;
    private JobDbHelper dbHelper;
    private Button btnApply;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job);  // Make sure the layout name matches your XML file

        // Initialize views
        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvCompany = findViewById(R.id.tvCompany);
        tvLocation = findViewById(R.id.tvLocation);
        tvDescription = findViewById(R.id.tvDescription);
        Button btnApply = findViewById(R.id.btnApply);
        dbHelper = new JobDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Set up click listener for the apply button
        btnApply.setOnClickListener(v -> {
            // Implement your apply action here
            // For example, you can navigate to an application form or send data to a server

            // Handle the apply action here, such as navigating to an application form or sending data


        });

    }
}
