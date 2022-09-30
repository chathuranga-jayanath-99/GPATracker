package com.example.gpa_tracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gpa_tracker.R;

public class SemesterListAdapter extends BaseAdapter {
    private Context context;
    private String[] semesterList;
    private LayoutInflater inflater;

    public SemesterListAdapter(Context context, String[] semesterList) {
        this.context = context;
        this.semesterList = semesterList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return semesterList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_semester_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.tvSem);
        textView.setText(semesterList[i]);
        return view;
    }

}
