package com.cj_apps.gpa_tracker.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cj_apps.gpa_tracker.R;
import com.cj_apps.gpa_tracker.control.GpaTracker;
import com.cj_apps.gpa_tracker.control.PersistentGpaTracker;
import com.cj_apps.gpa_tracker.data.model.Account;

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
                clearInputFields();
            }
        });

        lvAccountsIdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Account clickedAccount = (Account) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, AccountSemesterActivity.class);
                intent.putExtra("keyAccountId", clickedAccount.getId());
                startActivity(intent);
            }
        });

        lvAccountsIdList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Account clickedAccount = (Account) adapterView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete account: " + clickedAccount.getName() + "-" + clickedAccount.getId() + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gpaTracker.deleteAccount(clickedAccount.getId());
                        showAvailableAccountsIds();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void clearInputFields() {
        etAccountId.setText("");
        etHolderName.setText("");
        etNumberOfSemesters.setText("");
        rgMaxGpa.clearCheck();
    }

    private void initiateLayoutItems() {
        etAccountId = findViewById(R.id.etAccountId);
        etHolderName = findViewById(R.id.etHolderName);
        rgMaxGpa = findViewById(R.id.rgMaxGpa);
        etNumberOfSemesters = findViewById(R.id.etNumberOfSemesters);
        btnAddAccount = findViewById(R.id.btnAddAccount);
        lvAccountsIdList = findViewById(R.id.lvAccountsIdList);
    }

    public void showAvailableAccountsIds() {
        List<Account> accountList = gpaTracker.getAccounts();
        AccountListAdapter accountListAdapter = new AccountListAdapter(MainActivity.this, R.layout.accounts_adapter_view_layout, accountList, gpaTracker, lvAccountsIdList);
        lvAccountsIdList.setAdapter(accountListAdapter);
    }
}
