package com.example.c.lifememo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.Date;

public class Diary extends Activity implements OnDateSelectedListener,OnMonthChangedListener {

    private TextView textView;
    private CalendarDay currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_main);


        final MaterialCalendarView widget = (MaterialCalendarView) findViewById(R.id.diary_main_interface);
        //设置点击选择日期改变事件
        widget.setOnDateChangedListener(this);
        //设置滑动选择改变月份事件
        widget.setOnMonthChangedListener(this);

        FloatingActionButton gettoday = (FloatingActionButton)findViewById(R.id.diary_floating);
        gettoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widget.setSelectedDate(new Date());
            }
        });

    }

    /**
     * 监听日期改变函数
     * @param widget the view associated with this listener
     * @param date   the new date. May be null if selection was cleared
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean t) {
        if(date == null) {
            textView.setText("Choose The Date");
        }
        else {
            currentDate = date;
            showdate();
        }
    }
    private void showdate(){
        if (currentDate==null){
            Toast.makeText(getApplicationContext(),"nodata",Toast.LENGTH_SHORT).show();
            return;
        }
        int year = currentDate.getYear();
        String year_str = new DecimalFormat("0000").format(year);
        int month = currentDate.getMonth();
        String month_str = new DecimalFormat("00").format(month);
        int day = currentDate.getDay();
        String day_str = new DecimalFormat("00").format(day);
        Intent intent = new Intent();
        intent.putExtra("selectedDate",year_str+"-"+month_str+"-"+day_str);
        setResult(RESULT_OK,intent);
        finish();
        //Toast.makeText(getApplicationContext(),"The choice is "+ year_str+". "+ month_str+". "+ day_str,Toast.LENGTH_LONG).show();
    }

    /**
     * 监听月份改变函数
     * @param widget the view associated with this listener
     * @param date   the month picked, as the first day of the month
     */
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

    }
}
