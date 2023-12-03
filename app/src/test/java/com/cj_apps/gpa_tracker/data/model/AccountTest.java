package com.cj_apps.gpa_tracker.data.model;

import static org.junit.Assert.*;

import com.cj_apps.gpa_tracker.data.model.Account;
import com.cj_apps.gpa_tracker.data.model.Semester;
import com.cj_apps.gpa_tracker.data.model.Subject;
import com.cj_apps.gpa_tracker.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountTest {

    @Test
    public void getOverallGpa() {
        Account account = new Account(1111, "1111", 4.2f, 8);

        ArrayList<Semester> semesters = new ArrayList<>();
        Semester semester1 = new Semester(1111, 1);
        Semester semester2 = new Semester(1111, 2);
        Semester semester3 = new Semester(1111, 3);
        Semester semester4 = new Semester(1111, 4);
        Semester semester5 = new Semester(1111, 5);
        Semester semester6 = new Semester(1111, 6);
        Semester semester7 = new Semester(1111, 7);
        Semester semester8 = new Semester(1111, 8);

        semesters.add(semester1);
        semesters.add(semester2);
        semesters.add(semester3);
        semesters.add(semester4);
        semesters.add(semester5);
        semesters.add(semester6);
        semesters.add(semester7);
        semesters.add(semester8);

        // max gpa
        float maxGpa = 4.2f;

        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("CS-100", 3)); // 0
        subjects.add(new Subject("CS-101", 3)); // 1
        subjects.add(new Subject("CS-102", 3)); // 2
        subjects.add(new Subject("CS-103", 3)); // 3
        subjects.add(new Subject("CS-104", 2)); // 4
        subjects.add(new Subject("CS-105", 2)); // 5
        subjects.add(new Subject("CS-106", 2.5f)); // 6
        subjects.add(new Subject("CS-107", 4)); // 7
        subjects.add(new Subject("CS-108", 3)); // 8
        subjects.add(new Subject("CS-109", 3)); // 9
        subjects.add(new Subject("CS-110", 3)); // 10
        subjects.add(new Subject("CS-111", 3)); // 11
        subjects.add(new Subject("CS-112", 3)); // 12
        subjects.add(new Subject("CS-113", 1.5f)); // 13
        subjects.add(new Subject("CS-114", 1)); // 14

        // mark results
        subjects.get(0).setResult("A+");
        subjects.get(1).setResult("A");
        subjects.get(2).setResult("A-");
        subjects.get(3).setResult("B+");
        subjects.get(4).setResult("B");
        subjects.get(5).setResult("B-");
        subjects.get(6).setResult("C+");
        subjects.get(7).setResult("C");
        subjects.get(8).setResult("C-");
        subjects.get(9).setResult("D");
        subjects.get(10).setResult("F");
        subjects.get(11).setResult("I-we");
        subjects.get(12).setResult("I-ca");
        subjects.get(13).setResult("N");
        subjects.get(14).setResult("W");

        ArrayList<Subject> semester1Subjects = new ArrayList<>(Arrays.asList(subjects.get(0), subjects.get(1))); // 0 1
        ArrayList<Subject> semester2Subjects = new ArrayList<>(Arrays.asList(subjects.get(2), subjects.get(3))); // 2 3
        ArrayList<Subject> semester3Subjects = new ArrayList<>(Arrays.asList(subjects.get(4), subjects.get(5))); // 4 5
        ArrayList<Subject> semester4Subjects = new ArrayList<>(Arrays.asList(subjects.get(6), subjects.get(7))); // 6 7
        ArrayList<Subject> semester5Subjects = new ArrayList<>(Arrays.asList(subjects.get(8), subjects.get(9))); // 8 9
        ArrayList<Subject> semester6Subjects = new ArrayList<>(Arrays.asList(subjects.get(10), subjects.get(11))); // 10 11
        ArrayList<Subject> semester7Subjects = new ArrayList<>(Arrays.asList(subjects.get(12), subjects.get(13))); // 12 13
        ArrayList<Subject> semester8Subjects = new ArrayList<>(Arrays.asList(subjects.get(14))); // 14

        ArrayList<ArrayList<Subject>> eachSemesterSubjects = new ArrayList<>();
        eachSemesterSubjects.add(semester1Subjects);
        eachSemesterSubjects.add(semester2Subjects);
        eachSemesterSubjects.add(semester3Subjects);
        eachSemesterSubjects.add(semester4Subjects);
        eachSemesterSubjects.add(semester5Subjects);
        eachSemesterSubjects.add(semester6Subjects);
        eachSemesterSubjects.add(semester7Subjects);
        eachSemesterSubjects.add(semester8Subjects);

        for (int i = 0; i < 8; i++) {
            Semester semester = semesters.get(i);
            ArrayList<Subject> semesterSubjects = eachSemesterSubjects.get(i);
            for (Subject subject : semesterSubjects) {
                semester.addSubject(subject);
            }
            account.addSemester(semester, i+1);
        }

        float gained =  3    * 4.2f + // 0
                3    * 4.0f + // 1
                3    * 3.7f + // 2
                3    * 3.3f + // 3
                2    * 3.0f + // 4
                2    * 2.7f + // 5
                2.5f * 2.3f + // 6
                4    * 2.0f + // 7
                3    * 1.5f + // 8
                3    * 1.0f + // 9
                3    * 0.0f + // 10
                3    * 0.0f + // 11
                3    * 0.0f;  // 12

        float maxGained =   3    * maxGpa +
                3    * maxGpa +
                3    * maxGpa +
                3    * maxGpa +
                2    * maxGpa +
                2    * maxGpa +
                2.5f * maxGpa +
                4    * maxGpa +
                3    * maxGpa +
                3    * maxGpa +
                3    * maxGpa +
                3    * maxGpa +
                3    * maxGpa;

        float gpaCalculated = gained / maxGained * account.getMaxGpa();
        gpaCalculated = Utils.roundToTwoDecimalPlaces(gpaCalculated);

        assertTrue(account.getOverallGpa() == gpaCalculated);
    }

    @Test
    public void getSemesterGpa() {
        Account account = new Account(1111, "1111", 4.2f, 8);
        Semester semester = new Semester(1111, 7);

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

    @Test
    public void getSemesterGpaSubjectCombination1() {
        Account account = new Account(1111, "1111", 4.2f, 8);
        Semester semester = new Semester(1111, 7);

        // max gpa
        float maxGpa = 4.2f;

        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("CS-100", 3)); // 0
        subjects.add(new Subject("CS-101", 3)); // 1
        subjects.add(new Subject("CS-102", 3)); // 2
        subjects.add(new Subject("CS-103", 3)); // 3
        subjects.add(new Subject("CS-104", 2)); // 4
        subjects.add(new Subject("CS-105", 2)); // 5
        subjects.add(new Subject("CS-106", 2.5f)); // 6
        subjects.add(new Subject("CS-107", 4)); // 7
        subjects.add(new Subject("CS-108", 3)); // 8
        subjects.add(new Subject("CS-109", 3)); // 9
        subjects.add(new Subject("CS-110", 3)); // 10
        subjects.add(new Subject("CS-111", 3)); // 11
        subjects.add(new Subject("CS-112", 3)); // 12
        subjects.add(new Subject("CS-113", 1.5f)); // 13
        subjects.add(new Subject("CS-114", 1)); // 14

        // mark results
        subjects.get(0).setResult("A+");
        subjects.get(1).setResult("A");
        subjects.get(2).setResult("A-");
        subjects.get(3).setResult("B+");
        subjects.get(4).setResult("B");
        subjects.get(5).setResult("B-");
        subjects.get(6).setResult("C+");
        subjects.get(7).setResult("C");
        subjects.get(8).setResult("C-");
        subjects.get(9).setResult("D");
        subjects.get(10).setResult("F");
        subjects.get(11).setResult("I-we");
        subjects.get(12).setResult("I-ca");
        subjects.get(13).setResult("N");
        subjects.get(14).setResult("W");

        float gained =  3    * 4.2f + // 0
                        3    * 4.0f + // 1
                        3    * 3.7f + // 2
                        3    * 3.3f + // 3
                        2    * 3.0f + // 4
                        2    * 2.7f + // 5
                        2.5f * 2.3f + // 6
                        4    * 2.0f + // 7
                        3    * 1.5f + // 8
                        3    * 1.0f + // 9
                        3    * 0.0f + // 10
                        3    * 0.0f + // 11
                        3    * 0.0f;  // 12

        float maxGained =   3    * maxGpa +
                            3    * maxGpa +
                            3    * maxGpa +
                            3    * maxGpa +
                            2    * maxGpa +
                            2    * maxGpa +
                            2.5f * maxGpa +
                            4    * maxGpa +
                            3    * maxGpa +
                            3    * maxGpa +
                            3    * maxGpa +
                            3    * maxGpa +
                            3    * maxGpa;

        float gpaCalculated = gained / maxGained * account.getMaxGpa();
        gpaCalculated = Utils.roundToTwoDecimalPlaces(gpaCalculated);

        for (int i = 0; i < subjects.size(); i++) {
            semester.addSubject(subjects.get(i));
        }

        assertTrue(account.getSemesterGpa(semester)==gpaCalculated);
    }
}