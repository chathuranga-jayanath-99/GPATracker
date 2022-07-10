package com.example.gpa_tracker.ui;

import static java.util.Objects.isNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private GpaTracker gpaTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_semester);

        String accountId = getIntent().getStringExtra("keyAccountId");

        // Initiate layout items
        tvCalculatedOverallGpa = findViewById(R.id.tvCalculatedOverallGpa);
        lvSemesterList = findViewById(R.id.lvSemesterList);

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
    }

    private void showSemesters(String accountId) {
        List<Semester> semestersOfAccount = gpaTracker.getSemestersOfAccount(accountId);

        if (semestersOfAccount == null) Log.i("showSemesters", " empty array");
        else Log.i("showSemesters", " not empty array" + semestersOfAccount.size());

        ArrayAdapter semestersArrayAdapter = new ArrayAdapter<Semester>(AccountSemesterActivity.this, android.R.layout.simple_list_item_1, semestersOfAccount);
        lvSemesterList.setAdapter(semestersArrayAdapter);
    }
}