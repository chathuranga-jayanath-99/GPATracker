package com.cj_apps.gpa_tracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cj_apps.gpa_tracker.R;
import com.cj_apps.gpa_tracker.control.GpaTracker;
import com.cj_apps.gpa_tracker.control.PersistentGpaTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MarkResultActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private TextView tvModuleName;
    private TextView tvModuleCredits;
    private Button btnMarkResult;
    private Button btnCancel;
    private String selectedResult;

    private Spinner spinnerResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_result);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdViewTop = findViewById(R.id.adViewTop);
        AdRequest adRequestTop = new AdRequest.Builder().build();
        mAdViewTop.loadAd(adRequestTop);

        AdView mAdViewBottom = findViewById(R.id.adViewBottom);
        AdRequest adRequestBottom = new AdRequest.Builder().build();
        mAdViewBottom.loadAd(adRequestBottom);

        int subjectId = Integer.parseInt(getIntent().getStringExtra("keySubjectId"));
        String subjectName = getIntent().getStringExtra("keySubjectName");
        float subjectCredits = Float.parseFloat(getIntent().getStringExtra("keySubjectCredits"));
        String subjectResult = getIntent().getStringExtra("keySubjectResult");
        String accountId = getIntent().getStringExtra("keyAccountId");
        int semesterNo = Integer.parseInt(getIntent().getStringExtra("keySemesterNo"));

        initiateLayoutItems();

        // show drop down list
        showResultsDropDownList(spinnerResults);
        // set current result in drop down list
        spinnerResults.setSelection(getSelectedResultIndex(spinnerResults, subjectResult));

        this.gpaTracker = new PersistentGpaTracker(this);

        // initiate view
        tvModuleName.setText(subjectName);
        tvModuleCredits.setText(String.valueOf(subjectCredits));

        spinnerResults.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String result = adapterView.getItemAtPosition(i).toString();
                setSelectedResult(result);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnMarkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = getSelectedResult();

                if (!result.equals("")) {

                    gpaTracker.markSubjectResult(accountId, semesterNo,subjectId, result);

                    Intent intent = new Intent(MarkResultActivity.this, SemesterSubjectActivity.class);
                    intent.putExtra("keyAccountId", accountId);
                    intent.putExtra("keySemesterNo", String.valueOf(semesterNo));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MarkResultActivity.this, "Enter a result!", Toast.LENGTH_SHORT).show();
                    Log.i("debug", result);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkResultActivity.this, SemesterSubjectActivity.class);
                intent.putExtra("keyAccountId", accountId);
                intent.putExtra("keySemesterNo", String.valueOf(semesterNo));
                startActivity(intent);
            }
        });

    }

    private int getSelectedResultIndex(Spinner spinnerResults, String subjectResult) {
        for (int i = 0; i < spinnerResults.getCount(); i++) {
            if (spinnerResults.getItemAtPosition(i).equals(subjectResult)){
                return i;
            }
        }
        return 0;
    }

    private void setSelectedResult(String result) {
        this.selectedResult = result;
    }

    private String getSelectedResult() {
        return this.selectedResult;
    }

    private void initiateLayoutItems() {
        tvModuleName = findViewById(R.id.tvModuleName);
        tvModuleCredits = findViewById(R.id.tvModuleCredits);
        btnMarkResult = findViewById(R.id.btnMarkResult);
        btnCancel = findViewById(R.id.btnCancel);
        spinnerResults = findViewById(R.id.spinnerResults);
    }

    private void showResultsDropDownList(Spinner spinner) {
        String[] results = getResources().getStringArray(R.array.results);;
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MarkResultActivity.this, android.R.layout.simple_spinner_item, results);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}