package com.cj_apps.gpa_tracker.data.model;

import static org.junit.Assert.*;

import com.cj_apps.gpa_tracker.data.model.Account;
import com.cj_apps.gpa_tracker.data.model.Semester;
import com.cj_apps.gpa_tracker.data.model.Subject;
import com.cj_apps.gpa_tracker.Utils;

import org.junit.Test;

import java.util.ArrayList;

public class AccountTest {

    @Test
    public void getOverallGpa() {
    }

    @Test
    public void getSemesterGpa() {
        Account account = new Account("1111", "1111", 4.2f, 8);
        Semester semester = new Semester("1111", 7);

        // max gpa
        float maxGpa = 4.2f;

        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("CS-100", 3));
        subjects.add(new Subject("CS-101", 2));
        subjects.add(new Subject("CS-102", 2));
        subjects.add(new Subject("CS-103", 3));
        subjects.add(new Subject("CS-104", 2));
        subjects.add(new Subject("CS-105", 3));
        subjects.add(new Subject("CS-106", 3));

        // mark results
        subjects.get(0).setResult("A+");
        subjects.get(1).setResult("B+");
        subjects.get(2).setResult("A");
        subjects.get(3).setResult("B+");
        subjects.get(4).setResult("B+");
        subjects.get(5).setResult("C+");
        subjects.get(6).setResult("D");

        // gained -> 3*4.2+2*3.3+2*4.0+3*3.3+2*3.3+3*2.3+3*1
        // should -> (3*4+2*3)
        float gained = 3*4.2f+2*3.3f+2*4.0f+3*3.3f+2*3.3f+3*2.3f+3*1f;
        float maxGained =  (3*4+2*3)*4.2f;
        float gpaCalculated = gained / maxGained * account.getMaxGpa();
        gpaCalculated = Utils.roundToTwoDecimalPlaces(gpaCalculated);

        for (int i = 0; i < subjects.size(); i++) {
            semester.addSubject(subjects.get(i));
        }

        assertTrue(account.getSemesterGpa(semester)==gpaCalculated);
    }
}