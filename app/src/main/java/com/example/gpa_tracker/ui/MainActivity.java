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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.control.PersistentGpaTracker;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private EditText etAccountId;
    private EditText etHolderName;
    private RadioGroup rgMaxGpa;
    private EditText etNumberOfSemesters;
    private Button btnAddAccount;
    private ListView lvAccountsIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateLayoutItems();

        this.gpaTracker = new PersistentGpaTracker(this);

        showAvailableAccountsIds();

        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inputs shouldn't be empty
                if (!etAccountId.getText().toString().equals("") && !etHolderName.getText().toString().equals("") && !etNumberOfSemesters.getText().toString().equals("")) {
                    String id = etAccountId.getText().toString();
                    String name = etHolderName.getText().toString();
                    int noOfSemesters = Integer.parseInt(etNumberOfSemesters.getText().toString());
                    float maxGpa;

                    int checkedRadioButtonId = rgMaxGpa.getCheckedRadioButtonId();
                    switch (checkedRadioButtonId) {
                        case R.id.rbGpaHigh:
                            maxGpa=4.2f;
                            break;
                        case R.id.rbGpaLow:
                            maxGpa=4.0f;
                            break;
                        default:
                            maxGpa=4.0f;
                            break;
                    }

                    gpaTracker.addAccount(id, name, maxGpa, noOfSemesters);

                }
                else {
                    Log.i("invalid-input", "inputs are empty");
                    Toast.makeText(MainActivity.this, "Inputs should be filled", Toast.LENGTH_SHORT).show();
                }
                // show new accounts
                showAvailableAccountsIds();

            }
        });

        lvAccountsIdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedAccountId = (String) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, AccountSemesterActivity.class);
                intent.putExtra("keyAccountId", clickedAccountId);
                startActivity(intent);

            }
        });
    }

    private void initiateLayoutItems() {
        etAccountId = findViewById(R.id.etAccountId);
        etHolderName = findViewById(R.id.etHolderName);
        rgMaxGpa = findViewById(R.id.rgMaxGpa);
        etNumberOfSemesters = findViewById(R.id.etNumberOfSemesters);
        btnAddAccount = findViewById(R.id.btnAddAccount);
        lvAccountsIdList = findViewById(R.id.lvAccountsIdList);
    }

    private void showAvailableAccountsIds() {
        List<String> accountIdsList = gpaTracker.getAccountIdsList();
        ArrayAdapter accountsIdArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, accountIdsList);
        lvAccountsIdList.setAdapter(accountsIdArrayAdapter);
    }

}