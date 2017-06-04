package com.VIew;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.example.c.lifememo.R;
import com.models.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/15.
 */

public class DrawView extends View {
    Context context = null;
    List<event> data = new ArrayList<event>();
    public DrawView(Context context){
        super(context);
        this.context = context;
    }
    public DrawView(Context context, List<event> data){
        super(context);
        this.context = context;
        this.data = data;
    }
    @Override
    protected void onDraw(Canvas canvas){
        final float top  = 0;
        final float bottom = dip2px(180);
            int size = data.size();
            //Log.e("size",size+"");
            for (int i = 0; i<size;i++){
                event tmp = data.get(i);
                Paint p = new Paint();
                p.setColor(getResources().getColor(getEventColor(tmp.getEventclass())));
                p.setStyle(Paint.Style.FILL);
                float left = getPoint(tmp.getSttime());
                //Log.e("i"+i,left+"");
                float right = getPoint(tmp.getEndtime());
                //Log.e("i"+i,right+"");
                canvas.drawRect(left,top,right,bottom,p);
            }
    }
    private float getPoint(String time){
        float point = 0;
        String[] splitstr = time.split(":");
        int hour = Integer.valueOf(splitstr[0]);
        //Log.e(time+"hour",hour+"");
        int minute = Integer.valueOf(splitstr[1]);
        //Log.e(time+"minute",minute+"");
        int tmp_n = hour*4+minute/15;
        //Log.e(time+"tmp_n",tmp_n+"");
        point = point + tmp_n*dip2px((float)3.5);
        return point;
    }
    private int dip2px(float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private int getEventColor(String event_class){
        switch (event_class) {
            case "sleep":
                return R.color.colorSleep;
            case "eat":
                return R.color.colorEat;
            case "study":
                return R.color.colorStudy;
            case "boring":
                return R.color.colorBoring;
            default:
                return R.color.colorTT;
        }
    }
}
