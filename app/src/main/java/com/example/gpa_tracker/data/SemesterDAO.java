package com.example.gpa_tracker.data;

import com.example.gpa_tracker.data.model.Semester;

import java.util.List;

public interface SemesterDAO {

    List<Semester> getSemestersOfAccount(String accountId);

    Semester getSemester(String accountId, int semesterNo);

    boolean addSemester(Semester semester);

    boolean removeSemester(String accountId, int semesterNo);
}
