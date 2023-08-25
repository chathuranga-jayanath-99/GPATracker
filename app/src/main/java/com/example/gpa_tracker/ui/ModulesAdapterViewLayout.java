package com.example.gpa_tracker.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.gpa_tracker.data.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class ModulesAdapterViewLayout extends ArrayAdapter<Subject> {

    public ModulesAdapterViewLayout(@NonNull Context context, int resource, ArrayList<Subject> objects) {
        super(context, resource, objects);
    }
}
