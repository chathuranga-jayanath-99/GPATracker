package com.example.gpa_tracker.ui;

import static java.util.Objects.isNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.control.PersistentGpaTracker;
import com.example.gpa_tracker.data.model.Semester;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AccountSemesterActivity extends AppCompatActivity {

    private TextView tvCalculatedOverallGpa;
    private ListView lvSemesterList;
    private Button btnGoBack;

    private GpaTracker gpaTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_semester);

        String accountId = getIntent().getStringExtra("keyAccountId");

        // Initiate layout items
        tvCalculatedOverallGpa = findViewById(R.id.tvCalculatedOverallGpa);
        lvSemesterList = findViewById(R.id.lvSemesterList);
        btnGoBack = findViewById(R.id.btnGoBack);

        this.gpaTracker = new PersistentGpaTracker(this);

        float overallGpaOfAccount = gpaTracker.getOverallGpaOfAccount(accountId);
        tvCalculatedOverallGpa.setText(String.valueOf(overallGpaOfAccount));
        Log.i("overallGpaOfAccount", String.valueOf(overallGpaOfAccount));

        showSemesters(accountId);

        lvSemesterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Semester semester = (Semester) adapterView.getItemAtPosition(i);
//                Log.i("debug", semester.toString());
                Intent intent = new Intent(AccountSemesterActivity.this, SemesterSubjectActivity.class);
                intent.putExtra("keyAccountId", accountId);
                intent.putExtra("keySemesterNo", String.valueOf(semester.getSemesterNo()));
                startActivity(intent);
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSemesterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showSemesters(String accountId) {
        List<Semester> semestersOfAccount = gpaTracker.getSemestersOfAccount(accountId);

        if (semestersOfAccount == null) Log.i("showSemesters", " empty array");
        else Log.i("showSemesters", " not empty array" + semestersOfAccount.size());

//        ArrayAdapter semestersArrayAdapter = new ArrayAdapter<Semester>(AccountSemesterActivity.this, android.R.layout.simple_list_item_1, semestersOfAccount);
        String[] semesterNameList = this.getSemesterNameList(semestersOfAccount.size());
        SemesterListAdapter semesterListAdapter = new SemesterListAdapter(getApplicationContext(), semesterNameList);
        lvSemesterList.setAdapter(semesterListAdapter);
    }

    private String[] getSemesterNameList(int noOfSemesters){
        String[] semesterNameList = new String[noOfSemesters];
        for (int i = 0; i < noOfSemesters; i++) {
            semesterNameList[i] = "Semester: " + String.valueOf(i+1);
        }
        return semesterNameList;
    }
}