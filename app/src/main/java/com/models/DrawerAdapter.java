package com.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.c.lifememo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/14.
 */


public class DrawerAdapter extends BaseAdapter {
    List<String> menuItem = new ArrayList<String>();
    Context context;
    public DrawerAdapter(Context context){
        this.context = context;
        menuItem.add("Diary");
        menuItem.add("Analysis");
        menuItem.add("Keywords");
        menuItem.add("Setting");
        menuItem.add("Quit");
    }
    @Override
    public int getCount(){
        return menuItem.size();
    }
    @Override
    public String getItem(int position){
        return menuItem.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView , ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item,parent,false);
            ((TextView)view).setText(getItem(position));
        }
        return view;
    }

}
