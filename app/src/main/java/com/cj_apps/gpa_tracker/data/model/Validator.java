package com.cj_apps.gpa_tracker.data.model;

import java.util.ArrayList;

public class Validator {
    private ArrayList<Float> gpaList;

    public Validator() {
        gpaList = new ArrayList<Float>();
        gpaList.add(4.2f);
        gpaList.add(4.0f);
    }

    public static boolean validateSubjectDetails(String name, String credits){
        // String name, float credits
        if (!name.equals("") && !credits.equals("")) {
            try {
                Float.parseFloat(credits);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }

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

    public boolean validateAccount(Account account) {
        if (account.getId().equals("")){
            return false;
        }
        else if (account.getName().equals("")){
            return false;
        }
        else if (!this.gpaList.contains(account.getMaxGpa())){
            return false;
        }
        else if (account.getNoOfSemesters() <= 0){
            return false;
        }
        else{
            return  true;
        }
    }
}
