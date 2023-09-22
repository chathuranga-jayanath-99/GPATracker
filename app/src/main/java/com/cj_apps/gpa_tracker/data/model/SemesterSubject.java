package com.cj_apps.gpa_tracker.data.model;

public class SemesterSubject {
    private String accountId;
    private int semesterNo;
    private int subjectId;
    private String result;

    public SemesterSubject(String accountId, int semesterNo, int subjectId, String result) {
        this.accountId = accountId;
        this.semesterNo = semesterNo;
        this.subjectId = subjectId;
        this.result = result;
    }

    public String getAccountId() {
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
