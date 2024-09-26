package com.example.jobapp;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class JobViewModel extends ViewModel {
    // MutableLiveData holding a list of Job objects
    // MutableLiveData holding a list of Job objects
    private final MutableLiveData<List<Job>> jobs = new MutableLiveData<>(new ArrayList<>());

    // Public method to get the LiveData of the job list
    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    // Method to add a new Job to the list
    public void addJob(Job job) {
        List<Job> currentJobs = jobs.getValue(); // Get the current list of jobs
        if (currentJobs != null) {
            currentJobs.add(job); // Add the new job to the list
            jobs.setValue(currentJobs); // Update the MutableLiveData with the new list
        }
    }
}
