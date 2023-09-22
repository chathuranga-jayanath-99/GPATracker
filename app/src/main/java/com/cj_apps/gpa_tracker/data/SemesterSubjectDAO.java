package com.cj_apps.gpa_tracker.data;

import com.cj_apps.gpa_tracker.data.model.Semester;
import com.cj_apps.gpa_tracker.data.model.SemesterSubject;

import java.util.List;

public interface SemesterSubjectDAO {

    List<SemesterSubject> getSemesterSubjects();

    SemesterSubject getSemesterSubject(String accountId, int semesterNo, String subjectId);

    boolean addSemesterSubject(SemesterSubject semesterSubject);

    boolean removeSemesterSubject(String accountId, int semesterNo, String subjectId);

    boolean updateSemesterSubject(String accountId, int semesterNo, String subjectId, String result);

    Semester getSemesterWithSubjects(String accountId, int semesterNo);
}
