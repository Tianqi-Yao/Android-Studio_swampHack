package com.example.swamphack;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.swamphack.R;

import java.util.ArrayList;


public class ClassmateListAdapter extends BaseAdapter {

    private ClassmateDescriptionActivity mclassmateDescriptionActivity;
    private int mlayoutRes;
    private ArrayList<Classmate> mclassmateArrayList;


    private static class ViewHolder {
        TextView name;
        TextView email;
    }

    public ClassmateListAdapter(ClassmateDescriptionActivity classmateDescriptionActivity, int adapter_classmate, ArrayList<Classmate> classmateArrayList) {
        this.mclassmateDescriptionActivity = classmateDescriptionActivity;
        this.mlayoutRes = adapter_classmate;
        this.mclassmateArrayList = classmateArrayList;
    }

    @Override
    public int getCount() {

        return mclassmateArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String classmateName = mclassmateArrayList.get(position).getStudentName();
        String classmateEmail = mclassmateArrayList.get(position).getStudentEmail();

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mclassmateDescriptionActivity).inflate(mlayoutRes,parent,false);
            holder = new ViewHolder();
            holder.email = convertView.findViewById(R.id.student_name);
            holder.name = convertView.findViewById(R.id.student_email);

            convertView.setTag(holder);


        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(classmateName);
        holder.email.setText(classmateEmail);
        return convertView;

    }
}
