package com.cj_apps.gpa_tracker.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GpaTracker gpaTracker;

    private EditText etProfileName;
    private RadioGroup rgMaxGpa;
    private EditText etNumberOfSemesters;
    private Button btnAddAccount;
    private Button btnExit;
    private ListView lvAccountsIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        initiateLayoutItems();

        this.gpaTracker = new PersistentGpaTracker(this);

        showAvailableAccountsIds();

        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inputs shouldn't be empty
                if (!etProfileName.getText().toString().equals("") && !etNumberOfSemesters.getText().toString().equals("")) {
                    String profileName = etProfileName.getText().toString();
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
                    gpaTracker.addAccount(profileName, maxGpa, noOfSemesters);
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

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lvAccountsIdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Account clickedAccount = (Account) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, AccountSemesterActivity.class);
                Log.i("keyAccountId", clickedAccount.toString());
                intent.putExtra("keyAccountId", String.valueOf(clickedAccount.getId()));
                startActivity(intent);
            }
        });

        lvAccountsIdList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Account clickedAccount = (Account) adapterView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmation");
                String message = "Are you sure you want to delete profile: <b>" + clickedAccount.getName() + "</b> ?";
                builder.setMessage(Html.fromHtml(message));
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
        etProfileName.setText("");
        etNumberOfSemesters.setText("");
        rgMaxGpa.clearCheck();
    }

    private void initiateLayoutItems() {
        etProfileName = findViewById(R.id.etProfileName);
        rgMaxGpa = findViewById(R.id.rgMaxGpa);
        etNumberOfSemesters = findViewById(R.id.etNumberOfSemesters);
        btnAddAccount = findViewById(R.id.btnAddAccount);
        btnExit = findViewById(R.id.btnExit);
        lvAccountsIdList = findViewById(R.id.lvAccountsIdList);
    }

    public void showAvailableAccountsIds() {
        List<Account> accountList = gpaTracker.getAccounts();
        AccountListAdapter accountListAdapter = new AccountListAdapter(MainActivity.this, R.layout.accounts_adapter_view_layout, accountList, gpaTracker, lvAccountsIdList);
        lvAccountsIdList.setAdapter(accountListAdapter);
    }
}
