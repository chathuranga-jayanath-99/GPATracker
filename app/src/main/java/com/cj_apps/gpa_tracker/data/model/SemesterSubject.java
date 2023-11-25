package com.cj_apps.gpa_tracker.data.model;

public class SemesterSubject {
    private int accountId;
    private int semesterNo;
    private int subjectId;
    private String result;

    public SemesterSubject(int accountId, int semesterNo, int subjectId, String result) {
        this.accountId = accountId;
        this.semesterNo = semesterNo;
        this.subjectId = subjectId;
        this.result = result;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getSemesterNo() {
        return semesterNo;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getResult() {
        return result;
    }
}
