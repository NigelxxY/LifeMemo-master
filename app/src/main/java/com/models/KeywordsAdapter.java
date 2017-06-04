package com.models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.VIew.Keywords_dialog;
import com.example.c.lifememo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/26.
 */

public class KeywordsAdapter extends BaseAdapter {
    private List<String> keywords_list = new ArrayList<>();
    private LayoutInflater mInflater;
    private Activity activity;

    public KeywordsAdapter(Activity activity,List<String> keywords_list){
        this.activity = activity;
        this.keywords_list = keywords_list;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return keywords_list.size();
    }
    @Override
    public String getItem(int position){
        return keywords_list.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View vi = convertView;
        if(vi==null) {
            vi = mInflater.inflate(R.layout.keywords_list_layout, null);
            TextView list = (TextView) vi.findViewById(R.id.keywords_lists);
            list.setText(keywords_list.get(position));
        }
        return vi;
    }
}
