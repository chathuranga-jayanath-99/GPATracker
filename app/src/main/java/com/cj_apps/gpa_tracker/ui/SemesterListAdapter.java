package com.cj_apps.gpa_tracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cj_apps.gpa_tracker.R;
import com.cj_apps.gpa_tracker.data.model.Semester;

import java.util.List;

public class SemesterListAdapter extends BaseAdapter {
    private Context context;
    private List<Semester> semesterList;
    private LayoutInflater inflater;

    public SemesterListAdapter(Context context, List<Semester> semesterList) {
        this.context = context;
        this.semesterList = semesterList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return semesterList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.semesterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_semester_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.tvSem);
        textView.setText(semesterList.get(i).toString());
        return view;
    }

}
