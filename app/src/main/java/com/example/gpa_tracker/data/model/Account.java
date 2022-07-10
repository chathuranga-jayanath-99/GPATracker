package com.example.gpa_tracker.data.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String id;
    private String name;
    private float maxGpa;
    private int noOfSemesters;

    private Semester[] semesters;
    private float overallGpa;

    public Account(String id, String name, float maxGpa, int noOfSemesters) {
        this.id = id;
        this.name = name;
        this.maxGpa = maxGpa;
        this.noOfSemesters = noOfSemesters;

        semesters = new Semester[noOfSemesters];
    }

    public float getOverallGpa() {
        float earnedMarks = 0.0f;
        float maxMarks = 0.0f;

        for (Semester semester : semesters) {
            for (Subject subject : semester.getSubjectList()){
                float subjectCredits = subject.getCredits();
                String earnResult = subject.getResult();

                float earnPoints = getEarnedPointsFromResult(earnResult);

                earnedMarks += earnPoints * subjectCredits;
                maxMarks += this.maxGpa * subjectCredits;
            }
        }

        if (maxMarks!=0.0f) {
            return earnedMarks / maxMarks;
        }
        return 0.0f;

    }

    private float getEarnedPointsFromResult(String result) {
        if (this.maxGpa == 4.2f && result.equals("A+")) return 4.2f;

        switch (result) {
            case "A+":
            case "A":
                return 4.0f;

            case "A-":
                return 3.7f;

            case "B+":
                return 3.3f;

            case "B":
                return 3.0f;

            case "B-":
                return 2.7f;

            case "C+":
                return 2.3f;

            case "C":
                return 2.0f;

            case "C-":
                return 1.5f;

            case "D":
                return 1.0f;

            case "F":
            default:
                return 0.0f;

        }
    }

    public void addSemester(Semester semester, int semesterNo) {
        semesters[semesterNo - 1] = semester;
    }

    public int getNoOfSemesters() {
        return noOfSemesters;
    }

    public void setNoOfSemesters(int noOfSemesters) {
        this.noOfSemesters = noOfSemesters;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMaxGpa() {
        return maxGpa;
    }

    public void setMaxGpa(float maxGpa) {
        this.maxGpa = maxGpa;
    }
}
