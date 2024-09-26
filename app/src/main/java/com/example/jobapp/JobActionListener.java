package com.example.jobapp;

public interface JobActionListener {
    void onEditJob(Job job);
    void onDeleteJob(int jobId);
    void onSaveJob(Job job);
    void onApplyJob(Job job);
}
