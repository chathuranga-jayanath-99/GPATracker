package com.cj_apps.gpa_tracker.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;

    private DatabaseHelper(@Nullable Context context) {
        super(context, "gpa_tracker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PersistentAccountDAO.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(PersistentSemesterDAO.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(PersistentSubjectDAO.CREATE_TABLE_QUERY);
        sqLiteDatabase.execSQL(PersistentSemesterSubjectDAO.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static DatabaseHelper getInstance(@Nullable Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
}
