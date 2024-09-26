package com.example.jobapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jobapp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_RESUMES = "resumes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_FILE_PATH = "file_path";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_RESUMES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_FILE_PATH + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESUMES);
        onCreate(db);
    }

    public boolean addResume(String name, String filePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_FILE_PATH, filePath);
        long result = db.insert(TABLE_RESUMES, null, values);
        return result != -1;
    }

    public Cursor getAllResumes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RESUMES, null);
    }
    public boolean deleteResume(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_RESUMES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
