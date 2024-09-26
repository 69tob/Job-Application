package com.example.jobapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JobDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jobs.db";
    private static final int DATABASE_VERSION = 1;

    public JobDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + JobContract.JobEntry.TABLE_NAME + " ("
                + JobContract.JobEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JobContract.JobEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + JobContract.JobEntry.COLUMN_COMPANY + " TEXT NOT NULL, "
                + JobContract.JobEntry.COLUMN_LOCATION + " TEXT NOT NULL, "
                + JobContract.JobEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + JobContract.JobEntry.TABLE_NAME);
        onCreate(db);
    }
}
