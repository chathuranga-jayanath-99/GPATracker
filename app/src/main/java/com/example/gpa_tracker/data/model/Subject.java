package com.example.gpa_tracker.data.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subject {
    private int id;
    private String name;
    private float credits;

    private String result;

    private static List<String> results = new ArrayList<String>(Arrays.asList("A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D"));

    public Subject(int id, String name, float credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public Subject(String name, float credits) {
        this.name = name;
        this.credits = credits;
    }

    public void setResult(String result) {
        if (results.contains(result)) {
            this.result = result;
        }
        else {
            Log.i("error", "invalid result is provided");
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCredits() {
        return credits;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return name + " | credits: " + credits + " | result: " + result;
    }
}
