package com.example.gpa_tracker.data.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class AccountTest {

    @Test
    public void getOverallGpa() {
    }

    @Test
    public void getSemesterGpa() {
        Account account = new Account("1111", "1111", 4.2f, 8);
        Semester semester = new Semester("1111", 8);

        // max gpa
        float maxGpa = 4.2f;

        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("CS-100", 3));
        subjects.add(new Subject("CS-101", 2));
        subjects.add(new Subject("CS-102", 2));
        subjects.add(new Subject("CS-103", 3));

        // mark results
        subjects.get(0).setResult("A+");
        subjects.get(1).setResult("A+");
        subjects.get(2).setResult("A+");
        subjects.get(3).setResult("A+");

        for (int i = 0; i < 4; i++) {
            semester.addSubject(subjects.get(i));
        }

        assertTrue(account.getSemesterGpa(semester)==4.2f);
    }
}