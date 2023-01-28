package com.example.gpa_tracker.data.model;

public class Subject {
    private int id;
    private String name;
    private float credits;

    private String result;

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
        this.result = result;
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
