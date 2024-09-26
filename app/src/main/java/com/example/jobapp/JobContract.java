package com.example.jobapp;

import android.provider.BaseColumns;

public final class JobContract {

    public static Object ResumeEntry;

    // Private constructor to prevent instantiation
    private JobContract() {
    }
    public static final String DATABASE_NAME = "jobs.db";
    public static final int DATABASE_VERSION = 1;


    // Inner class that defines the table contents
    public static class JobEntry implements BaseColumns {
        public static final String TABLE_NAME = "jobs";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_ADMIN = "is_admin";


    }
}
