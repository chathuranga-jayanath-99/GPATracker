package com.example.gpa_tracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;

public class MarkResultActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private TextView tvModuleName;
    private TextView tvModuleCredits;
    private EditText etModuleResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_result);

        int subjectId = Integer.parseInt(getIntent().getStringExtra("keySubjectId"));
        String subjectName = getIntent().getStringExtra("keySubjectName");
        float subjectCredits = Float.parseFloat(getIntent().getStringExtra("keySubjectCredits"));
        String subjectResult = getIntent().getStringExtra("keySubjectResult");

        initiateLayoutItems();

        // initiate view
        tvModuleName.setText(subjectName);
        tvModuleCredits.setText(String.valueOf(subjectCredits));

    }

    private void initiateLayoutItems() {
        tvModuleName = findViewById(R.id.tvModuleName);
        tvModuleCredits = findViewById(R.id.tvModuleCredits);
        etModuleResult = findViewById(R.id.etModuleResult);
    }
}