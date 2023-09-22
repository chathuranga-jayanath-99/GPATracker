package com.cj_apps.gpa_tracker.ui;

import androidx.annotation.NonNull;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.cj_apps.gpa_tracker.data.model.Subject;

import java.util.ArrayList;

public class ModulesAdapterViewLayout extends ArrayAdapter<Subject> {

    public ModulesAdapterViewLayout(@NonNull Context context, int resource, ArrayList<Subject> objects) {
        super(context, resource, objects);
    }
}
