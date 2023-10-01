package com.cj_apps.gpa_tracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cj_apps.gpa_tracker.R;
import com.cj_apps.gpa_tracker.control.GpaTracker;
import com.cj_apps.gpa_tracker.control.PersistentGpaTracker;
import com.cj_apps.gpa_tracker.data.model.Semester;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

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

        // load ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
                Log.i("SEMESTER_LIST_VIEW", "Item clicked at position: "+i);
                Semester semester = (Semester) adapterView.getItemAtPosition(i);
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

        String[] semesterNameList = this.getSemesterNameList(semestersOfAccount.size());
        SemesterListAdapter semesterListAdapter = new SemesterListAdapter(getApplicationContext(), semestersOfAccount);
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