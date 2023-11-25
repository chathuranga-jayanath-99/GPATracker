package com.cj_apps.gpa_tracker.ui;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cj_apps.gpa_tracker.R;
import com.cj_apps.gpa_tracker.control.GpaTracker;
import com.cj_apps.gpa_tracker.control.PersistentGpaTracker;
import com.cj_apps.gpa_tracker.data.model.Subject;
import com.cj_apps.gpa_tracker.data.model.Validator;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class SemesterSubjectActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private TextView tvSemGpa;
    private TextView tvSemGpaCalculated;
    private EditText etAddModuleName;
    private EditText etAddModuleCredits;
    private Spinner spinnerResults;
    private Button btnAddModule;
    private Button btnGoBack;
    private ListView lvSemModulesList;

    private String selectedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_subject);

        // load ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        int accountId = Integer.parseInt(getIntent().getStringExtra("keyAccountId"));
        int semesterNo = Integer.parseInt(getIntent().getStringExtra("keySemesterNo"));

        initiateLayoutItems();
        showResultsDropDownList(spinnerResults);

        this.gpaTracker = new PersistentGpaTracker(this);
        // fill layout
        if (semesterNo!=-1) {
            float semesterGpaOfAccount = this.gpaTracker.getSemesterGpaOfAccount(accountId, semesterNo);
            this.tvSemGpa.setText("Semester "+semesterNo+ " GPA: ");
            this.tvSemGpaCalculated.setText(String.valueOf(semesterGpaOfAccount));
        }
        // view modules of semester
        showSemesterModules(accountId, semesterNo);

        lvSemModulesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Subject subject = (Subject) adapterView.getItemAtPosition(i);
                Log.i("debug", subject.toString());
                Intent intent = new Intent(SemesterSubjectActivity.this, MarkResultActivity.class);
                intent.putExtra("keySubjectId", String.valueOf(subject.getId()));
                intent.putExtra("keySubjectName", subject.getName());
                intent.putExtra("keySubjectCredits", String.valueOf(subject.getCredits()));
                intent.putExtra("keySubjectResult", subject.getResult());
                intent.putExtra("keyAccountId", String.valueOf(accountId));
                intent.putExtra("keySemesterNo", String.valueOf(semesterNo));
                startActivity(intent);
                finish();
            }
        });

        spinnerResults.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedResult = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validator.validateSubjectDetails(etAddModuleName.getText().toString(), etAddModuleCredits.getText().toString())){
                    String addModuleName = etAddModuleName.getText().toString();
                    float addModuleCredits = Float.parseFloat(etAddModuleCredits.getText().toString());
                    int newSubjectId = gpaTracker.addSubject(addModuleName, addModuleCredits, accountId, semesterNo);
                    if (!selectedResult.equals("--") && newSubjectId >= 0){
                        gpaTracker.markSubjectResult(accountId, semesterNo, newSubjectId, selectedResult);
                        float newSemesterGpaOfAccount = gpaTracker.getSemesterGpaOfAccount(accountId, semesterNo);
                        tvSemGpaCalculated.setText(String.valueOf(newSemesterGpaOfAccount));
                    }
                    clearInputs();
                }
                else {
                    Log.i("invalid-input", "Credits should be a float");
                    Toast.makeText(SemesterSubjectActivity.this, "Module Name can't be empty and Credits should be a float", Toast.LENGTH_SHORT).show();
                }
                showSemesterModules(accountId, semesterNo);
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SemesterSubjectActivity.this, AccountSemesterActivity.class);
                intent.putExtra("keyAccountId", String.valueOf(accountId));
                intent.putExtra("keySemesterNo", String.valueOf(semesterNo));
                startActivity(intent);
                finish();
            }
        });

    }

    private void clearInputs() {
        etAddModuleName.setText("");
        etAddModuleCredits.setText("");
        spinnerResults.setSelection(0);
    }

    private void initiateLayoutItems() {
        tvSemGpa = findViewById(R.id.tvSemGpa);
        tvSemGpaCalculated = findViewById(R.id.tvSemGpaCalculated);
        etAddModuleName = findViewById(R.id.etAddModuleName);
        etAddModuleCredits = findViewById(R.id.etAddModuleCredits);
        spinnerResults = findViewById(R.id.spinnerResults);
        btnAddModule = findViewById(R.id.btnAddModule);
        btnGoBack = findViewById(R.id.goBack);
        lvSemModulesList = findViewById(R.id.lvSemModulesList);
    }

    private void showSemesterModules(int accountId, int semesterNo) {
        List<Subject> accountSemesterSubjects = gpaTracker.getAccountSemesterSubjects(accountId, semesterNo);
        SubjectListAdapter subjectListAdapter = new SubjectListAdapter(SemesterSubjectActivity.this, R.layout.modules_adapter_view_layout, accountSemesterSubjects);
        lvSemModulesList.setAdapter(subjectListAdapter);
    }

    private void showResultsDropDownList(Spinner spinner) {
        String[] results = getResources().getStringArray(R.array.results);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SemesterSubjectActivity.this, android.R.layout.simple_spinner_item, results);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}