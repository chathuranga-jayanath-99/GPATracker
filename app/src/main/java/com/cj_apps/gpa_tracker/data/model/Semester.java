package com.cj_apps.gpa_tracker.data.model;

import java.util.ArrayList;
import java.util.List;

public class Semester {
    private int accountId;
    private int semesterNo;
    private String title;

    private List<Subject> subjectList;

    public Semester(int accountId, int semesterNo) {
        this.accountId = accountId;
        this.semesterNo = semesterNo;
        this.title = "Sem "+semesterNo;

        subjectList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void addSubject(Subject subject) {
        subjectList.add(subject);
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public int getSemesterNo() {
        return semesterNo;
    }

    @Override
    public String toString() {
        return "Semester: " + this.semesterNo;
    }
}
