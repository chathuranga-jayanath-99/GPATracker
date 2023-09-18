package com.example.gpa_tracker.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.data.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountListAdapter extends ArrayAdapter<Account> {
    private Context context;
    private int resource;
    private GpaTracker gpaTracker;
    private ListView lvAccountsIdList;

    public AccountListAdapter(@NonNull Context context, int resource, List<Account> accounts, GpaTracker gpaTracker, ListView lvAccountsIdList) {
        super(context, resource, accounts);
        this.context = context;
        this.resource = resource;
        this.gpaTracker = gpaTracker;
        this.lvAccountsIdList = lvAccountsIdList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);
        String accountId = getItem(position).getId();
        String accountName = getItem(position).getName();

        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        TextView tvAccountId = (TextView) convertView.findViewById(R.id.tvAccountId);
        TextView tvAccountName = (TextView) convertView.findViewById(R.id.tvAccountName);
        ImageButton imgAccountDeleteButton = (ImageButton) convertView.findViewById(R.id.imgAccountDeleteBtn);
        imgAccountDeleteButton.setTag(account);

        imgAccountDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccountDeleteConfirmation(view);
                refresh();
            }
        });
        tvAccountId.setText(accountId);
        tvAccountName.setText(accountName);
        return convertView;
    }

    private void showAccountDeleteConfirmation(View view) {
        Account selectedAccount = (Account) view.getTag();
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete account: " + selectedAccount.getName() + "-" + selectedAccount.getId() + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gpaTracker.deleteAccount(selectedAccount.getId());
                refresh();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                
            }
        });
        builder.create().show();
    }

    private void refresh() {
        List<Account> accountList = gpaTracker.getAccounts();
        AccountListAdapter accountListAdapter = new AccountListAdapter(context, R.layout.accounts_adapter_view_layout, accountList, gpaTracker, lvAccountsIdList);
        lvAccountsIdList.setAdapter(accountListAdapter);
    }
}
