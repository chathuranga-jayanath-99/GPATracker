package com.cj_apps.gpa_tracker.data;

import com.cj_apps.gpa_tracker.data.model.Semester;

import java.util.List;

public interface SemesterDAO {

    List<Semester> getSemestersOfAccount(int accountId);

    Semester getSemester(int accountId, int semesterNo);

    boolean addSemester(Semester semester);

    boolean removeSemester(int accountId, int semesterNo);
}
