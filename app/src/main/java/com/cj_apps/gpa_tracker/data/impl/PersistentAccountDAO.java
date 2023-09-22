package com.cj_apps.gpa_tracker.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cj_apps.gpa_tracker.data.AccountDAO;
import com.cj_apps.gpa_tracker.data.model.Account;

import java.util.ArrayList;
import java.util.List;

public class PersistentAccountDAO implements AccountDAO {

    public static final String ACCOUNT_TABLE = "account_table";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String MAX_GPA_COLUMN = "max_gpa";
    public static final String NO_OF_SEMESTERS_COLUMN = "no_of_semesters";

    public static final String  CREATE_TABLE_QUERY = "create table " + ACCOUNT_TABLE + " (" +
            ID_COLUMN + " text primary key, " +
            NAME_COLUMN + " text not null, " +
            MAX_GPA_COLUMN + " real not null, " +
            NO_OF_SEMESTERS_COLUMN + " integer not null);";

    private DatabaseHelper databaseHelper;

    public PersistentAccountDAO(@Nullable Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public List<String> getAccountIdsList() {
        ArrayList<String> returnList = new ArrayList<>();

        String queryString = "select id from account_table";

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                returnList.add(id);
            } while (cursor.moveToNext());
        }

        // close both cursor and readableDatabase
        cursor.close();
        readableDatabase.close();
        return returnList;
    }

    @Override
    public List<Account> getAccountsList() {
        ArrayList<Account> returnList = new ArrayList<>();

        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from account_table", null);

        if (cursor.moveToFirst()) {
         do {
             String id = cursor.getString(cursor.getColumnIndexOrThrow(ID_COLUMN));
             String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN));
             float maxGpa = cursor.getFloat(cursor.getColumnIndexOrThrow(MAX_GPA_COLUMN));
             int noOfSemesters = cursor.getInt(cursor.getColumnIndexOrThrow(NO_OF_SEMESTERS_COLUMN));

             Account account = new Account(id, name, maxGpa, noOfSemesters);
             returnList.add(account);
         } while (cursor.moveToNext());
        }

        // close cursor and readableDatabase
        cursor.close();
        readableDatabase.close();
        return returnList;
    }

    @Override
    public Account getAccount(String accountId) {
        Account account = null;

        String queryString = "select * from account_table where id=?";

        SQLiteDatabase readableDatabase = this.databaseHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(queryString, new String[]{accountId});

        if (cursor.moveToFirst()) {
            String accountIdSel = cursor.getString(cursor.getColumnIndexOrThrow(ID_COLUMN));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COLUMN));
            float maxGpa = (float)cursor.getDouble(cursor.getColumnIndexOrThrow(MAX_GPA_COLUMN));
            int noOfSemesters = cursor.getInt(cursor.getColumnIndexOrThrow(NO_OF_SEMESTERS_COLUMN));

            account = new Account(accountIdSel, name, maxGpa, noOfSemesters);
        }

        // close cursor and readableDatabase
        cursor.close();
        readableDatabase.close();

        return account;
    }

    @Override
    public boolean addAccount(Account account) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_COLUMN, account.getId());
        contentValues.put(NAME_COLUMN, account.getName());
        contentValues.put(MAX_GPA_COLUMN, account.getMaxGpa());
        contentValues.put(NO_OF_SEMESTERS_COLUMN, account.getNoOfSemesters());

        long insert = writableDatabase.insert(ACCOUNT_TABLE, null, contentValues);
        if (insert == -1) return false;
        return true;
    }

    @Override
    public boolean removeAccount(String accountId) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();

        Cursor cursor = writableDatabase.rawQuery(
                "delete from account_table " +
                        "where id=?",
                new String[]{accountId}
        );

        if (cursor.moveToFirst()) return true;
        return false;
    }
}
