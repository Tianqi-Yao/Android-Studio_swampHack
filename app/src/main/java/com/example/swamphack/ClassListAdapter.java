package com.example.swamphack;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.example.swamphack.R;
import java.util.ArrayList;


public class ClassListAdapter extends  BaseAdapter {
    public static final String TAG = "Test";
    private MatchAcitivity myMatchListView;
    private int classAdapter;
    private ArrayList<ClassName> classNameArrayList;


    private static class ViewHolder {
        Button classname;
        Button classmate;
    }



    public ClassListAdapter(MatchAcitivity matchAcitivity, int adapter_class, ArrayList<ClassName> classNameArrayList) {
        this.myMatchListView = matchAcitivity;
        this.classAdapter = adapter_class;
        this.classNameArrayList = classNameArrayList;
    }

    @Override
    public int getCount() {

        return classNameArrayList.size();
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
        String classname = classNameArrayList.get(position).getClassName();

        Log.d(TAG, "getView: "+classname);

        ViewHolder holder;
        if(convertView == null){
            Log.d(TAG, "getView: inhere");
            convertView = LayoutInflater.from(myMatchListView).inflate(classAdapter,parent,false);
            holder = new ViewHolder();
            holder.classname = convertView.findViewById(R.id.addedBtn);
            holder.classmate = convertView.findViewById(R.id.classmateBtn);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.classname.setText(classname);
        return convertView;




    }

}
