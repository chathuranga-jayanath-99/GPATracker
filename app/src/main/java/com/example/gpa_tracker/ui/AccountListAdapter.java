package com.example.gpa_tracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.control.GpaTracker;
import com.example.gpa_tracker.data.model.Account;

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
        tvAccountId.setText(accountId);
        tvAccountName.setText(accountName);
        return convertView;
    }
}
