package com.example.gpa_tracker.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.gpa_tracker.data.SemesterSubjectDAO;
import com.example.gpa_tracker.data.model.Semester;
import com.example.gpa_tracker.data.model.SemesterSubject;
import com.example.gpa_tracker.data.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class PersistentSemesterSubjectDAO implements SemesterSubjectDAO {

    public static final String SEMESTER_SUBJECT_TABLE = "semester_subject_table";
    public static final String ACCOUNT_ID_COLUMN = "account_id";
    public static final String SEMESTER_NO_COLUMN = "semester_no";
    public static final String SUBJECT_ID_COLUMN = "subject_id";
    public static final String RESULT_COLUMN = "result";

    public static final String  CREATE_TABLE_QUERY = "create table " + SEMESTER_SUBJECT_TABLE + " ( " +
            ACCOUNT_ID_COLUMN + " text not null, " +
            SEMESTER_NO_COLUMN + " integer not null, " +
            SUBJECT_ID_COLUMN + " int not null, " +
            RESULT_COLUMN + " text);";

    private DatabaseHelper databaseHelper;

    public PersistentSemesterSubjectDAO(@Nullable Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public List<SemesterSubject> getSemesterSubjects() {
        ArrayList<SemesterSubject> returnList = new ArrayList<>();

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + SEMESTER_SUBJECT_TABLE,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                SemesterSubject semesterSubject = getOneSemesterSubjectFromCursor(cursor);
                returnList.add(semesterSubject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        readableDatabase.close();
        return returnList;
    }

    @Override
    public SemesterSubject getSemesterSubject(String accountId, int semesterNo, String subjectId) {
        SemesterSubject semesterSubject = null;

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + SEMESTER_SUBJECT_TABLE + " " +
                        "where "+ACCOUNT_ID_COLUMN+"=? and "+SEMESTER_NO_COLUMN+"=? and "+SUBJECT_ID_COLUMN+"=? ",
                new String[]{accountId, String.valueOf(semesterNo), subjectId}
        );

        if (cursor.moveToFirst()) {
            semesterSubject = getOneSemesterSubjectFromCursor(cursor);
        }

        cursor.close();
        readableDatabase.close();
        return semesterSubject;
    }

    @Override
    public boolean addSemesterSubject(SemesterSubject semesterSubject) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ACCOUNT_ID_COLUMN, semesterSubject.getAccountId());
        contentValues.put(SEMESTER_NO_COLUMN, semesterSubject.getSemesterNo());
        contentValues.put(SUBJECT_ID_COLUMN, semesterSubject.getSubjectId());
        contentValues.put(RESULT_COLUMN, semesterSubject.getResult());

        long insert = writableDatabase.insert(SEMESTER_SUBJECT_TABLE, null, contentValues);
        if (insert == -1) return false;
        return true;
    }

    @Override
    public boolean removeSemesterSubject(String accountId, int semesterNo, String subjectId) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();

        int delete = writableDatabase.delete(SEMESTER_SUBJECT_TABLE, "account_id=? and semester_no=? and subject_id=?", new String[]{accountId, String.valueOf(semesterNo), subjectId});
        if (delete == -1) return false;
        return true;
    }

    @Override
    public boolean updateSemesterSubject(String accountId, int semesterNo, String subjectId, String result) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.i("debug", "updateSemesterSubject");
        contentValues.put(RESULT_COLUMN, result);

        int update = writableDatabase.update(SEMESTER_SUBJECT_TABLE, contentValues, "account_id=? and semester_no=? and subject_id=?", new String[]{accountId, String.valueOf(semesterNo), subjectId});
        if (update == -1) return false;
        return true;

    }

    @Override
    public Semester getSemesterWithSubjects(String accountId, int semesterNo) {
        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();

        Cursor cursor = readableDatabase.rawQuery(
                "select s.id as subject_id, s.name, s.credits, ss.result from semester_subject_table ss " +
                        "join subject_table s on ss.subject_id=s.id " +
                        "where account_id=? and semester_no=?",
                new String[]{accountId, String.valueOf(semesterNo)}
        );

        Semester semester = new Semester(accountId, semesterNo);

        if (cursor.moveToFirst()) {
            do {
                int subjectId = cursor.getInt(cursor.getColumnIndexOrThrow("subject_id"));
                String subjectName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                float subjectCredits = (float) cursor.getDouble(cursor.getColumnIndexOrThrow("credits"));
                String subjectResult = cursor.getString(cursor.getColumnIndexOrThrow("result"));

                Subject subject = new Subject(subjectId, subjectName, subjectCredits);
                subject.setResult(subjectResult);

                semester.addSubject(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        readableDatabase.close();
        return semester;
    }

    private SemesterSubject getOneSemesterSubjectFromCursor(Cursor cursor) {
        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_ID_COLUMN));
        int semesterNo = cursor.getInt(cursor.getColumnIndexOrThrow(SEMESTER_NO_COLUMN));
        int subjectId = cursor.getInt(cursor.getColumnIndexOrThrow(SUBJECT_ID_COLUMN));
        String result = cursor.getString(cursor.getColumnIndexOrThrow(RESULT_COLUMN));

        return new SemesterSubject(accountId, semesterNo, subjectId, result);
    }
}
