package com.example.gpa_tracker.control;

import android.content.Context;

import com.example.gpa_tracker.data.impl.PersistentAccountDAO;
import com.example.gpa_tracker.data.impl.PersistentSemesterDAO;
import com.example.gpa_tracker.data.impl.PersistentSemesterSubjectDAO;
import com.example.gpa_tracker.data.impl.PersistentSubjectDAO;

public class PersistentGpaTracker extends GpaTracker {
    private Context context;

    public PersistentGpaTracker(Context context) {
        this.context = context;
        setup();
    }


    @Override
    public void setup() {
        PersistentAccountDAO persistentAccountDAO = new PersistentAccountDAO(this.context);
        setAccountDAO(persistentAccountDAO);

        PersistentSemesterDAO persistentSemesterDAO = new PersistentSemesterDAO(this.context);
        setSemesterDAO(persistentSemesterDAO);

        PersistentSemesterSubjectDAO persistentSemesterSubjectDAO = new PersistentSemesterSubjectDAO(this.context);
        setSemesterSubjectDAO(persistentSemesterSubjectDAO);

        PersistentSubjectDAO persistentSubjectDAO = new PersistentSubjectDAO(this.context);
        setSubjectDAO(persistentSubjectDAO);
    }
}
