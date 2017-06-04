package com.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.c.lifememo.R;

/**
 * Created by Nigel_xxY on 2017/6/3.
 */

public class HorizontalListViewAdapter extends BaseAdapter {
    private int[] colors;
    private Context mContext;
    private LayoutInflater mInflater;

    public HorizontalListViewAdapter(Context context, int[] ids){
        this.mContext = context;
        this.colors = ids;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return colors.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontal_list_item, null);
            holder.dididi = convertView.findViewById(R.id.rect);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.dididi.setBackgroundColor(colors[position]);
        return convertView;
    }

    private static class ViewHolder {
        private View dididi;
    }
}
