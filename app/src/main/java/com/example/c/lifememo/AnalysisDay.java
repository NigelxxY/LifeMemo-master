package com.example.c.lifememo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.VIew.DrawView;
import com.dao.EventDao;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.models.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisDay extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener{

    private BarChart mBarChart;
    private List<event> data = new ArrayList<>();
    private List<String> date_List = new ArrayList<>();
    private Map<String,float[]> event_day = new HashMap<>();
    private TextView analysis_date,sleep_time,eat_time,study_time,boring_time;
    private Button analysis_button;
    private DrawView view;
    private RadioGroup rg;
    private RadioButton week_button,month_button;
    private String date;
    private int date_num = 7;
    private int userid = 0;
    private Handler eventHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what>0) {
                Map<String,float[]> tmp_map = (Map<String, float[]>)msg.obj;
                event_day.putAll(tmp_map);
                Log.e("mapsize",event_day.size()+"");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_day);
        Intent intent = getIntent();
        data = (List<event>) intent.getSerializableExtra("eventlist");
        date = intent.getStringExtra("now_date");
        userid = Integer.valueOf(intent.getStringExtra("userid"));
        getMinutes();
        Log.e("date",date);
        initView();
    }
    private void initView(){
        sleep_time =  (TextView)findViewById(R.id.sleep_day_time);
        eat_time = (TextView)findViewById(R.id.eat_day_time);
        study_time = (TextView)findViewById(R.id.study_day_time);
        boring_time = (TextView)findViewById(R.id.boring_day_time);
        analysis_button = (Button)findViewById(R.id.analysis_button);
        mBarChart = (BarChart)findViewById(R.id.chart1);
        rg = (RadioGroup)findViewById(R.id.analysis_radiobutton);
        week_button = (RadioButton)findViewById(R.id.button_week);
        month_button = (RadioButton)findViewById(R.id.button_month);
        analysis_date = (TextView)findViewById(R.id.analysis_date);

        if(data!=null) {
            analysis_date.setText(date);
            RelativeLayout layout = (RelativeLayout)findViewById(R.id.analysis_day_timelist);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip2px(getApplicationContext(),240));
            params.addRule(RelativeLayout.BELOW,R.id.analysis_title);
            params.setMargins(dip2px(getApplicationContext(),12),dip2px(getApplicationContext(),10),dip2px(getApplicationContext(),12),dip2px(getApplicationContext(),25));
            view  = new DrawView(this,data);
            view.invalidate();
            view.setLayoutParams(params);
            layout.addView(view);
            int size = data.size();
            int eat_minutes=0,study_minutes=0,sleeo_minutes=0,boring_minutes=0;
            for(int i=0;i<size;i++){
                switch (data.get(i).getEventclass()){
                    case "eat":
                        eat_minutes=eat_minutes+data.get(i).getTimeLength();
                        break;
                    case "study":
                        study_minutes=study_minutes+data.get(i).getTimeLength();
                        break;
                    case "sleep":
                        sleeo_minutes = sleeo_minutes+data.get(i).getTimeLength();
                        break;
                    case "boring":
                        boring_minutes = boring_minutes +data.get(i).getTimeLength();
                        break;
                    default:
                        break;
                }
            }
            Log.e("tttt",eat_minutes+"+"+study_minutes+"+"+sleeo_minutes+"+"+boring_minutes+"+");
            eat_time.setText(ChangeStr(eat_minutes));
            study_time.setText(ChangeStr(study_minutes));
            sleep_time.setText(ChangeStr(sleeo_minutes));
            boring_time.setText(ChangeStr(boring_minutes));
        }
        analysis_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
                analysis_button.setVisibility(View.GONE);
                rg.setVisibility(View.VISIBLE);
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == week_button.getId()) {
                    date_num = 7;
                    getChartVisiable();
                }
                else if (checkedId == month_button.getId()) {
                    date_num = 30;
                    getChartVisiable();
                }
            }
        });
    }
    private void getChartVisiable(){
        mBarChart.setVisibility(View.VISIBLE);
        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(40);

        //拓展只能在x轴和y轴
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setHighlightFullBarEnabled(false);

        //改变y轴坐标的位置
        YAxis leftAxis = mBarChart.getAxisLeft();
        //leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f);
        mBarChart.getAxisRight().setEnabled(false);

        XAxis xlabels = mBarChart.getXAxis();
        xlabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xlabels.setValueFormatter(new CustomYAxisValueFormatter(date_List));
        xlabels.setGranularity(1f);
        xlabels.setTextSize(8f);


        Legend legend = mBarChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setFormSize(8f);
        legend.setFormToTextSpace(4f);
        legend.setXEntrySpace(6f);
        setData(date_num);
        mBarChart.animateXY(2000,2000);
    }
    private List<String> getDateList(String date,int num){
        List<String> tmp = new ArrayList<>();
        String tmpdate = date;
        for(int i=0;i<num;i++){
            tmp.add(tmpdate);
            tmpdate = priordate(tmpdate);
        }
        Collections.reverse(tmp);
        return  tmp;
    }
    private String priordate(String date){
        String[] tmp = date.split("-");
        int date_year = Integer.valueOf(tmp[0]);
        int date_month = Integer.valueOf(tmp[1]);
        int date_day = Integer.valueOf(tmp[2]);
        date_day = date_day - 1;
        if (date_day==0){
            date_month = date_month - 1;
            if (date_month==0){
                date_day = 31;
                date_month = 12;
                date_year = date_year - 1;
            }else {
                date_day  = getMonthEndDay(date_year,date_month);
            }
        }
        return String.format("%04d",date_year)+"-"+String.format("%02d",date_month)+"-"+String.format("%02d",date_day);
    }
    private int getMonthEndDay(int year_num,int month_num){
        switch (month_num){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (year_num%4==0&&year_num%400==0||year_num%100!=0)
                    return 29;
                else
                    return 28;
            default:
                return 30;
        }
    }
    private String ChangeStr(int minutes){
        String hour = String.valueOf(minutes/60);
        String minute = String.valueOf(minutes%60);
        return hour+":"+minute;
    }
    private int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setData(int num) {
        ArrayList<BarEntry> yValsl = new ArrayList<BarEntry>();

        for (int i = 0; i < num; i++) {
            float[] val = event_day.get(date_List.get(i));
            float val1=0,val2=0,val3=0,val4=0;
            if (val!=null) {
                val1 = event_day.get(date_List.get(i))[0];
                val2 = event_day.get(date_List.get(i))[1];
                val3 = event_day.get(date_List.get(i))[2];
                val4 = event_day.get(date_List.get(i))[3];
            }
            yValsl.add(new BarEntry(i, new float[]{val1, val2, val3,val4}));
        }
        BarDataSet set1;
        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yValsl);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        }else {
            set1 = new BarDataSet(yValsl,"(Eventclass)");
            set1.setColors(getColors());
            set1.setStackLabels(new String[]{"eat","study","sleep","boring"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data  = new BarData(dataSets);
            //data.setValueFormatter(new MyValueFormatter());
            data.setValueTextColor(Color.WHITE);

            mBarChart.setData(data);
        }
        mBarChart.setFitBars(true);
        mBarChart.invalidate();
    }
    private int[] getColors(){
        //int stacksize = 4;
        //有尽可能多的颜色每项堆栈值
        int[] colors = new int[]{getResources().getColor(R.color.colorEat),
                                 getResources().getColor(R.color.colorStudy),
                                 getResources().getColor(R.color.colorSleep),
                                 getResources().getColor(R.color.colorBoring)};
        return colors;
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        /*if (mBarChart.getData() != null) {
            mBarChart.getData().setHighlightEnabled(!mBarChart.getData().isHighlightEnabled());
            mBarChart.invalidate();
        }*/
    }

    @Override
    public void onNothingSelected() {

    }
    @Override
    public void onClick(View v) {
    }
    class CustomYAxisValueFormatter implements IAxisValueFormatter{
        private List<String> mStrs;
        public CustomYAxisValueFormatter(List<String> strs){
            this.mStrs = strs;
        }
        @Override
        public String getFormattedValue(float v, AxisBase axisBase){
            return mStrs.get((int)v);
        }
    }
    private void getMinutes(){
        date_List = getDateList(date,date_num);
        for (int i =0 ;i<date_num;i++){
            String date_tmp = date_List.get(i);
            new Thread(){
                public void run(){
                    float eat_minute=0,study_minute=0,sleep_minute=0,boring_minute=0;
                    EventDao ed = new EventDao();
                    List<event> list_tmp = ed.getEventList(userid,date_tmp);
                    int tmp_size = list_tmp.size();
                    for (int j=0 ;j<tmp_size;j++){
                        float minute_tmp = list_tmp.get(j).getTimeLength();
                        switch (list_tmp.get(j).getEventclass()){
                            case "eat":
                                eat_minute = eat_minute + minute_tmp;
                                break;
                            case "study":
                                study_minute = study_minute + minute_tmp;
                                break;
                            case "sleep":
                                sleep_minute = sleep_minute + minute_tmp;
                                break;
                            case "boring":
                                boring_minute = boring_minute + minute_tmp;
                                break;
                            default:
                                break;
                        }
                    }
                    float[] minutes = new float[]{eat_minute,study_minute,sleep_minute,boring_minute};
                    Map<String,float[]> tmp_map = new HashMap<String, float[]>();
                    tmp_map.put(date_tmp,minutes);
                    Message msg = new Message();
                    msg.what = tmp_map.size();
                    msg.obj = tmp_map;
                    eventHandler.sendMessage(msg);
                }
            }.start();
        }
    }

}
