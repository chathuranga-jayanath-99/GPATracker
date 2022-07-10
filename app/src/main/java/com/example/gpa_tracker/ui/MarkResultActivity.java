package com.example.gpa_tracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.control.PersistentGpaTracker;

public class MarkResultActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private TextView tvModuleName;
    private TextView tvModuleCredits;
    private EditText etModuleResult;
    private TextView tvModuleCurrentCredits;
    private Button btnMarkResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_result);

        int subjectId = Integer.parseInt(getIntent().getStringExtra("keySubjectId"));
        String subjectName = getIntent().getStringExtra("keySubjectName");
        float subjectCredits = Float.parseFloat(getIntent().getStringExtra("keySubjectCredits"));
        String subjectResult = getIntent().getStringExtra("keySubjectResult");
        String accountId = getIntent().getStringExtra("keyAccountId");
        int semesterNo = Integer.parseInt(getIntent().getStringExtra("keySemesterNo"));

        initiateLayoutItems();

        this.gpaTracker = new PersistentGpaTracker(this);

        // initiate view
        tvModuleName.setText(subjectName);
        tvModuleCredits.setText(String.valueOf(subjectCredits));
        tvModuleCurrentCredits.setText(subjectResult);

        btnMarkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = etModuleResult.getText().toString();

                if (!result.equals("")) {

                    gpaTracker.markSubjectResult(accountId, semesterNo,subjectId, result);
                }
                else {
                    Toast.makeText(MarkResultActivity.this, "Enter a result!", Toast.LENGTH_SHORT).show();
                    Log.i("debug", result);
                }
            }
        });

    }

    private void initiateLayoutItems() {
        tvModuleName = findViewById(R.id.tvModuleName);
        tvModuleCredits = findViewById(R.id.tvModuleCredits);
        tvModuleCurrentCredits = findViewById(R.id.tvModuleCurrentCredits);
        etModuleResult = findViewById(R.id.etModuleResult);
        btnMarkResult = findViewById(R.id.btnMarkResult);
    }
}