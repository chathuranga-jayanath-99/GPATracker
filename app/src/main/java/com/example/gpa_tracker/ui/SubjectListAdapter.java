package com.example.gpa_tracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gpa_tracker.R;
import com.example.gpa_tracker.data.model.Subject;

import java.util.List;

public class SubjectListAdapter extends ArrayAdapter<Subject> {
    private Context context;
    private int resource;

    public SubjectListAdapter(@NonNull Context context, int resource, List<Subject> subjects) {
        super(context, resource, subjects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String subjectName = getItem(position).getName();
        float subjectCredits = getItem(position).getCredits();
        String subjectResult = getItem(position).getResult();

        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        TextView tvModuleName = (TextView) convertView.findViewById(R.id.tvModuleName);
        TextView tvModuleCredits = (TextView) convertView.findViewById(R.id.tvModuleCredits);
        TextView tvModuleResult = (TextView) convertView.findViewById(R.id.tvModuleResult);

        tvModuleName.setText(subjectName);
        tvModuleCredits.setText(String.valueOf(subjectCredits));
        tvModuleResult.setText(subjectResult);
        return convertView;
    }
}
