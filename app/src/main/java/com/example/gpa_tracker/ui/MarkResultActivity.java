package com.example.gpa_tracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.control.PersistentGpaTracker;

public class MarkResultActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private TextView tvModuleName;
    private TextView tvModuleCredits;
    private RadioGroup rgResults;
    private TextView tvModuleCurrentCredits;
    private Button btnMarkResult;
    private String selectedResult;

    private Spinner spinnerResults;
    private String[] results = {"A+","A","A-","B+","B","B-","C+","C","C-","D"};

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

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MarkResultActivity.this, android.R.layout.simple_spinner_item, results);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResults.setAdapter(adapter);

        this.gpaTracker = new PersistentGpaTracker(this);

        // initiate view
        tvModuleName.setText(subjectName);
        tvModuleCredits.setText(String.valueOf(subjectCredits));
        tvModuleCurrentCredits.setText(subjectResult);

        spinnerResults.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String result = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MarkResultActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        rgResults.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rbAPlus:
//                        setSelectedResult("A+");
//                        break;
//                    case R.id.rbA:
//                        setSelectedResult("A");
//                        break;
//                    case R.id.rbAMinus:
//                        setSelectedResult("A-");
//                        break;
//
//                    case R.id.rbBPlus:
//                        setSelectedResult("B+");
//                        break;
//                    case R.id.rbB:
//                        setSelectedResult("B");
//                        break;
//                    case R.id.rbBMinus:
//                        setSelectedResult("B-");
//                        break;
//
//                    case R.id.rbCPlus:
//                        setSelectedResult("C+");
//                        break;
//                    case R.id.rbC:
//                        setSelectedResult("C");
//                        break;
//                    case R.id.rbCMinus:
//                        setSelectedResult("C-");
//                        break;
//
//                    case R.id.rbD:
//                        setSelectedResult("D");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });



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
        tvModuleCurrentCredits = findViewById(R.id.tvModuleCurrentCredits);
//        rgResults = findViewById(R.id.rgResults);
        btnMarkResult = findViewById(R.id.btnMarkResult);
        spinnerResults = findViewById(R.id.spinnerResults);
    }
}