package com.cj_apps.gpa_tracker.data;

import com.cj_apps.gpa_tracker.data.model.Semester;

import java.util.List;

public interface SemesterDAO {

    List<Semester> getSemestersOfAccount(String accountId);

    Semester getSemester(String accountId, int semesterNo);

    boolean addSemester(Semester semester);

    boolean removeSemester(String accountId, int semesterNo);
}
