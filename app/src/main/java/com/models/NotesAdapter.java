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
 * Created by Nigel_xxY on 2017/5/27.
 */

public class NotesAdapter extends BaseAdapter {
    private List<note> data = new ArrayList<>();
    private Activity activity ;
    private LayoutInflater minflater;
    public NotesAdapter(Activity activity,List<note> data){
        this.activity = activity;
        this.data = data;
        this.minflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return data.size();
    }
    @Override
    public note getItem(int position){
        return data.get(position);
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
            vi = minflater.inflate(R.layout.main_notes_item,null);
            viewHolder = new ViewHolder();
            viewHolder.times = (TextView)vi.findViewById(R.id.note_time);
            viewHolder.notes = (TextView)vi.findViewById(R.id.note_notes);
            vi.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) vi.getTag();
        }
        viewHolder.times.setText(data.get(position).getDate()+"");
        viewHolder.notes.setText(data.get(position).getNotes());
        return vi;
    }
    public static class ViewHolder{
        public TextView times;
        public TextView notes;
    }
}
