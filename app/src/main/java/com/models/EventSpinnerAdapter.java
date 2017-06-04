package com.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.c.lifememo.R;

import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/27.
 */

public class EventSpinnerAdapter extends BaseAdapter {
    private List<String> data;
    private Context mContext;
    public EventSpinnerAdapter(Context pContext,List<String> pList){
        this.mContext = pContext;
        this.data = pList;
    }
    @Override
    public int getCount(){
        return data.size();
    }
    @Override
    public String getItem(int position){
        return data.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        convertView = mInflater.inflate(R.layout.event_spinner_item,null);
        if(convertView!=null){
            TextView textView = (TextView)convertView.findViewById(R.id.spinner_item);
            textView.setText(data.get(position));
        }
        return convertView;
    }
}
