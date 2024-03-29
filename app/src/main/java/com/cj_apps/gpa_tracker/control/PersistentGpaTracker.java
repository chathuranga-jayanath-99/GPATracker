package com.cj_apps.gpa_tracker.control;

import android.content.Context;

import com.cj_apps.gpa_tracker.data.impl.PersistentAccountDAO;
import com.cj_apps.gpa_tracker.data.impl.PersistentSemesterDAO;
import com.cj_apps.gpa_tracker.data.impl.PersistentSemesterSubjectDAO;
import com.cj_apps.gpa_tracker.data.impl.PersistentSubjectDAO;
import com.cj_apps.gpa_tracker.data.model.Validator;

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

        Validator validator = new Validator();
        setValidator(validator);
    }
}
