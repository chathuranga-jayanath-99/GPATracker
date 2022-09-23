package com.example.gpa_tracker.data.model;

public class Validator {
    public boolean validateSubject(Subject subject){
        if (subject.getName().equals("")){
            return false;
        }
        else if (subject.getCredits()<=0.0f){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validateSemester(Semester semester){
        if (semester.getAccountId().equals("")) {
            return false;
        }
        else if (semester.getSemesterNo()<=0) {
            return false;
        }
        else {
            return true;
        }
    }
}
