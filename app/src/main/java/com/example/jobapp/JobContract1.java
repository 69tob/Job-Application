package com.example.jobapp;

import android.provider.BaseColumns;

public final class JobContract1 {

    // Private constructor to prevent instantiation
    private JobContract1() {}

    // Inner class defining the Jobs table
    public static class JobEntry implements BaseColumns {
        public static final String TABLE_NAME = "jobs_v1";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_ADMIN = "is_admin"; // 0 for normal users, 1 for admin users
    }

    // Inner class defining the Resumes table
    public static class ResumeEntry implements BaseColumns {
        public static final String TABLE_NAME = "resumes_v1";
        public static final String COLUMN_APPLICANT_NAME = "applicant_name";
        public static final String COLUMN_RESUME_FILE_PATH = "resume_file_path";
        public static final String COLUMN_JOB_ID = "job_id"; // Foreign key linking to the job
    }
}
