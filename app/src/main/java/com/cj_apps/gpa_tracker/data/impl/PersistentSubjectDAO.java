package com.cj_apps.gpa_tracker.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cj_apps.gpa_tracker.data.SubjectDAO;
import com.cj_apps.gpa_tracker.data.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class PersistentSubjectDAO implements SubjectDAO {

    public static final String SUBJECT_TABLE = "subject_table";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CREDITS = "credits";

    public static final String CREATE_TABLE_QUERY = "create table " + SUBJECT_TABLE + " (" +
            ID + " integer primary key autoincrement, " +
            NAME + " text not null, " +
            CREDITS + " real not null);";

    private DatabaseHelper databaseHelper;

    public PersistentSubjectDAO(@Nullable Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public List<Subject> getSubjects() {
        ArrayList<Subject> returnList = new ArrayList<>();

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + SUBJECT_TABLE,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Subject subject = getSubjectFromCursor(cursor);
                returnList.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        readableDatabase.close();
        return returnList;
    }

    @Override
    public Subject getSubject(int id) {
        Subject subject = null;

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + SUBJECT_TABLE + " " +
                        "where "+ID+"=?",
                new String[] {String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            subject = getSubjectFromCursor(cursor);
        }

        cursor.close();
        readableDatabase.close();
        return subject;
    }

    @Override
    public int addSubject(Subject subject) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, subject.getName());
        contentValues.put(CREDITS, subject.getCredits());

        long insert = writableDatabase.insert(SUBJECT_TABLE, null, contentValues);
        if (insert == -1) return -1;

        Cursor cursor = writableDatabase.rawQuery(
                "select last_insert_rowid() as last_insert_rowid",
                null
        );

        if (cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndexOrThrow("last_insert_rowid"));
        }
        return -1;
    }

    @Override
    public boolean removeSubject(int id) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();

        int delete = writableDatabase.delete(SUBJECT_TABLE, "id=?", new String[]{String.valueOf(id)});
        if (delete == -1) return false;
        return true;
    }

    private Subject getSubjectFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
        float credits = (float)cursor.getDouble(cursor.getColumnIndexOrThrow(CREDITS));

        return new Subject(id, name, credits);
    }
}

