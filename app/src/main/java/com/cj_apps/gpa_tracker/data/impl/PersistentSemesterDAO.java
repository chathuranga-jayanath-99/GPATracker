package com.cj_apps.gpa_tracker.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cj_apps.gpa_tracker.data.SemesterDAO;
import com.cj_apps.gpa_tracker.data.model.Semester;

import java.util.ArrayList;
import java.util.List;

public class PersistentSemesterDAO implements SemesterDAO {

    public static final String SEMESTER_TABLE = "semester_table";
    public static final String ACCOUNT_ID_COLUMN = "account_id";
    public static final String SEMESTER_NO_COLUMN = "semester_no";
    public static final String TITLE_COLUMN = "title";

    public static final String CREATE_TABLE_QUERY = "create table " + SEMESTER_TABLE + " (" +
            ACCOUNT_ID_COLUMN + " text not null, " +
            SEMESTER_NO_COLUMN + " integer not null, " +
            TITLE_COLUMN + " text not null, " +
            "foreign key (" + ACCOUNT_ID_COLUMN + ") " +
            "   references account_table(id) " +
            "   on update cascade);";

    private DatabaseHelper databaseHelper;

    public PersistentSemesterDAO(@Nullable Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public List<Semester> getSemestersOfAccount(String accountId) {
        ArrayList<Semester> returnList = new ArrayList<>();

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from semester_table " +
                        "where account_id=?",
                new String[]{accountId}
        );

        if (cursor.moveToFirst()) {
            do {
                Semester semester = getSemesterFromCursor(cursor);
                returnList.add(semester);
            } while (cursor.moveToNext());
        }

        // close both cursor and readableDatabase
        cursor.close();
        readableDatabase.close();
        return returnList;

    }

    @Override
    public Semester getSemester(String accountId, int semesterNo) {
        Semester semester = null;

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + SEMESTER_TABLE + " " +
                        "where " + ACCOUNT_ID_COLUMN + "=? and " + SEMESTER_NO_COLUMN + "=?",
                new String[]{accountId, String.valueOf(semesterNo)}
        );

        if (cursor.moveToFirst()) {
            semester = getSemesterFromCursor(cursor);
        }

        // close both cursor and readableDatabase
        cursor.close();
        readableDatabase.close();
        return semester;
    }

    @Override
    public boolean addSemester(Semester semester) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ACCOUNT_ID_COLUMN, semester.getAccountId());
        contentValues.put(SEMESTER_NO_COLUMN, semester.getSemesterNo());
        contentValues.put(TITLE_COLUMN, semester.getTitle());

        long insert = writableDatabase.insert(SEMESTER_TABLE, null, contentValues);
        if (insert == -1) return false;
        return true;
    }

    @Override
    public boolean removeSemester(String accountId, int semesterNo) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();

        int delete = writableDatabase.delete(SEMESTER_TABLE, "account_id=? and semester_no=?", new String[]{accountId, String.valueOf(semesterNo)});
        if (delete == -1) return false;
        return true;
    }

    private Semester getSemesterFromCursor(Cursor cursor) {
        String accountIdSel = cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_ID_COLUMN));
        int semesterNo = cursor.getInt(cursor.getColumnIndexOrThrow(SEMESTER_NO_COLUMN));

        return new Semester(accountIdSel, semesterNo);
    }

}
