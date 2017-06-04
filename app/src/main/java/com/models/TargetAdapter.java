package com.models;

import android.app.Activity;
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


public class TargetAdapter extends BaseAdapter {
    private List<event> toshow= new ArrayList();
    private LayoutInflater mInflater;
    private Activity activity;

    public TargetAdapter(Activity activity, List<event> toshow){
        this.activity = activity;
        this.toshow = toshow;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return toshow.size();
    }
    @Override
    public event getItem(int position){
        return toshow.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        View vi = convertView;
        if(vi==null){
            vi = mInflater.inflate(R.layout.main_event_item,null);
            viewHolder = new ViewHolder();
            viewHolder.time = (TextView)vi.findViewById(R.id.event_time);
            viewHolder.event = (TextView)vi.findViewById(R.id.event_event);
            vi.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) vi.getTag();
        }
        event tmp = toshow.get(position);
        viewHolder.time.setText("Begin Time : "+tmp.getSttime()+"                                                        End Time: "+tmp.getEndtime());
        viewHolder.event.setText("Event: "+tmp.getEventhing());
        return vi;
    }

    public static class ViewHolder{
        public TextView time;
        public TextView event;
    }
}

