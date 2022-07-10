package com.example.gpa_tracker.data;

import com.example.gpa_tracker.data.model.Subject;

import java.util.List;

public interface SubjectDAO {

    List<Subject> getSubjects();

    Subject getSubject(int id);

    int addSubject(Subject subject);

    boolean removeSubject(int id);
}
