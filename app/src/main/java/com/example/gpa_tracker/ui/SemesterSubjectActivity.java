package com.example.gpa_tracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.control.PersistentGpaTracker;
import com.example.gpa_tracker.data.model.Subject;

import java.util.List;

public class SemesterSubjectActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private TextView tvSemGpa;
    private TextView tvSemGpaCalculated;
    private EditText etAddModuleName;
    private EditText etAddModuleCredits;
    private Button btnAddModule;
    private ListView lvSemModulesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_subject);

        String accountId = getIntent().getStringExtra("keyAccountId");
        int semesterNo = Integer.parseInt(getIntent().getStringExtra("keySemesterNo"));

        initiateLayoutItems();

        this.gpaTracker = new PersistentGpaTracker(this);

        // fill layout
        if (semesterNo!=-1) this.tvSemGpa.setText("Semester "+semesterNo+ " GPA:");
        // view modules of semester
        showSemesterModules(accountId, semesterNo);

        lvSemModulesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subject subject = (Subject) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(SemesterSubjectActivity.this, MarkResultActivity.class);
//                intent.putExtra("keySubjectId", subject.getId());
//                intent.putExtra("keySubjectName", subject.getName());
//                intent.putExtra("keySubjectCredits", subject.getCredits());
//                intent.putExtra("keySubjectResult", subject.getResult());
                startActivity(intent);
            }
        });

        Log.i("SemesterSubjectActivity", "on create");
        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addModuleName = etAddModuleName.getText().toString();
                float addModuleCredits = Float.parseFloat(etAddModuleCredits.getText().toString());

                Log.i("btnAddModule", "clicked");

                gpaTracker.addSubject(addModuleName, addModuleCredits, accountId, semesterNo);

                // show new modules of semester
                showSemesterModules(accountId, semesterNo);
            }
        });

//        gpaTracker;
    }

    private void initiateLayoutItems() {
        tvSemGpa = findViewById(R.id.tvSemGpa);
        tvSemGpaCalculated = findViewById(R.id.tvSemGpaCalculated);
        etAddModuleName = findViewById(R.id.etAddModuleName);
        etAddModuleCredits = findViewById(R.id.etAddModuleCredits);
        btnAddModule = findViewById(R.id.btnAddModule);
        lvSemModulesList = findViewById(R.id.lvSemModulesList);
    }

    private void showSemesterModules(String accountId, int semesterNo) {
        List<Subject> accountSemesterSubjects = gpaTracker.getAccountSemesterSubjects(accountId, semesterNo);
        ArrayAdapter<Subject> subjectArrayAdapter = new ArrayAdapter<>(SemesterSubjectActivity.this, android.R.layout.simple_list_item_1, accountSemesterSubjects);
        lvSemModulesList.setAdapter(subjectArrayAdapter);
        Log.i("showSemesterModules", "added");
    }
}