package com.example.gpa_tracker.data.model;

import java.util.ArrayList;
import java.util.List;

public class Semester {
    private String accountId;
    private int semesterNo;
    private String title;

    private List<Subject> subjectList;

    public Semester(String accountId, int semesterNo) {
        this.accountId = accountId;
        this.semesterNo = semesterNo;
        this.title = "Sem "+semesterNo;

        subjectList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAccountId() {
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
        return "Semester{" +
                "accountId='" + accountId + '\'' +
                ", semesterNo=" + semesterNo +
                ", title='" + title + '\'' +
                ", subjectList=" + subjectList +
                '}';
    }
}
