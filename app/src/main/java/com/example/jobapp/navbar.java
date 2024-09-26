package com.example.jobapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class navbar extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_bar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_OpenDrawer, R.string.navigation_CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.settings) {
                    Toast.makeText(navbar.this, "Settings", Toast.LENGTH_SHORT).show();
                    loadFragment(new SettingsFragment());
                } else if (id == R.id.menu) {
                    Toast.makeText(navbar.this, "Home", Toast.LENGTH_SHORT).show();
                    loadFragment(new HomeFragment());
                } else if (id == R.id.client) {
                    Toast.makeText(navbar.this, "Client", Toast.LENGTH_SHORT).show();
                    loadFragment(new AdminResumeListFragment());
                } else if (id == R.id.profile) {
                    Toast.makeText(navbar.this, "Profile", Toast.LENGTH_SHORT).show();
                    loadFragment(new ProfileFragment());
                } else if (id == R.id.upload) {
                    Toast.makeText(navbar.this, "Upload", Toast.LENGTH_SHORT).show();
                    loadFragment(new UploadJobFragment());
                } else if (id == R.id.logout) {
                    Toast.makeText(navbar.this, "Logout", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(navbar.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else {
                    Toast.makeText(navbar.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        if (savedInstanceState == null) {
            // Load default fragment if no saved state
            loadFragment(new HomeFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void updateNavigationHeader() {
        String userEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();

        View headerView = navigationView.getHeaderView(0);
        TextView emailTextView = headerView.findViewById(R.id.userEmail);
        TextView nameTextView = headerView.findViewById(R.id.userName);

        if (userEmail != null) {
            emailTextView.setText(userEmail);
        }
        // Replace with actual user name if available
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationHeader();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
