package com.example.c.lifememo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dao.EventDao;
import com.dao.KeywordsDao;
import com.models.EventSpinnerAdapter;
import com.models.event;
import com.timerpicker.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.view.Menu;
import android.app.Activity;
import android.widget.Spinner;
import android.widget.Toast;


public class EventEdit extends Activity {

    private RadioGroup eventedit_class_select;
    private int userid;
    TimePicker hour_tp;
    TimePicker minute_tp;
    private EventSpinnerAdapter testAdapter;
    private EditText eventEdit;
    private Button finis_button,st_button,et_button;
    private Spinner keywords_select;
    private String selected_keyword;
    private String selected_class;
    private String startHour,endHour,startMinutes,endMinutes;
    private List<String> Keyword_list = new ArrayList<>();
    private List<String> eat_list,study_list,sleep_list,boring_list;

    Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what>0){
                Keyword_list = (List<String>)msg.obj;
                testAdapter = new EventSpinnerAdapter(EventEdit.this,Keyword_list);
                keywords_select.setAdapter(testAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit);

        initView();
        getData("eat");
        Intent in_d = getIntent();
        userid = Integer.valueOf(in_d.getStringExtra("userid"));

        //设置下拉菜单的点击事件
        keywords_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_keyword=Keyword_list.get(position);
                Toast.makeText(getApplicationContext(),"Selecet Spinner is :"+selected_keyword,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_keyword = null;
                Toast.makeText(getApplicationContext(),"Nothing selected!!!",Toast.LENGTH_LONG).show();
            }
        });
        //类别的点击事件！！
        eventedit_class_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.button_sleep:
                        selected_class = "sleep";
                        break;
                    case R.id.button_meals:
                        selected_class = "eat";
                        break;
                    case R.id.button_work:
                        selected_class = "study";
                        break;
                    case R.id.button_play:
                        selected_class = "boring";
                        break;
                    case R.id.button_note:
                        Intent intent = new Intent();
                        intent.setClass(EventEdit.this,NoteEdit.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                getData(selected_class);
                Toast.makeText(getApplicationContext(),"Select class is "+selected_class,Toast.LENGTH_LONG).show();
            }
        });

        //选择开始时间和结束时间
        st_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour_tp.setOnSelectListener(new TimePicker.onSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        startHour = text;
                    }
                });
                minute_tp.setOnSelectListener(new TimePicker.onSelectListener(){
                    @Override
                    public void onSelect(String text){
                        startMinutes = text;
                    }
                });
                Toast.makeText(getApplicationContext(),"hour:"+startHour+" minutes "+startMinutes,Toast.LENGTH_SHORT).show();
            }
        });
        et_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour_tp.setOnSelectListener(new TimePicker.onSelectListener() {
                    @Override
                    public void onSelect(String text) {
                        endHour = text;
                    }
                });
                minute_tp.setOnSelectListener(new TimePicker.onSelectListener(){
                    @Override
                    public void onSelect(String text){
                        endMinutes = text;
                    }
                });
                Toast.makeText(getApplicationContext(),"hours "+endHour+" minutes "+endMinutes,Toast.LENGTH_LONG).show();
            }
        });
        finis_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event data = new event();
                String sttime = startHour+":"+startMinutes;
                String endtime = endHour+":"+endMinutes;
                data.setSttime(sttime);
                data.setEndtime(endtime);
                data.setEventclass(selected_class);
                data.setEventkeyword(selected_keyword);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                data.setEventdate(sdf.format(new Date()));
                data.setUserid(userid);
                new Thread(){
                    public void run(){
                        EventDao ed = new EventDao();
                        int num = ed.insertEvent(data);
                        if(num>0){
                            //Toast.makeText(getApplicationContext(),"new event has been built",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(EventEdit.this,MainActivity.class);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Something wrong maybe",Toast.LENGTH_SHORT).show();
                        }
                    }
                }.start();
            }
        });
    }
    private void initView(){
        eventEdit = (EditText)findViewById(R.id.editText_event);
        finis_button = (Button)findViewById(R.id.editevent_finishbutton);
        st_button = (Button)findViewById(R.id.event_bt);
        et_button = (Button)findViewById(R.id.event_et);
        keywords_select = (Spinner)findViewById(R.id.pulldownlist);
        eventedit_class_select = (RadioGroup)findViewById(R.id.class_select_event);

        //初始化选择时间的
        hour_tp = (TimePicker) findViewById(R.id.timepicker_hour);
        minute_tp = (TimePicker) findViewById(R.id.timepicker_minute);
        List<String> hours = new ArrayList<String>();
        List<String> minutes = new ArrayList<String>();

        for (int i = 0; i < 24; i++) {
            hours.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 4; i++) {
            minutes.add(i==0 ? "00": "" + i*15);
        }

        hour_tp.setData(hours);
        minute_tp.setData(minutes);
    }
    private void getData(String classstr){
        new Thread(){
            public void run(){
                KeywordsDao kd = new KeywordsDao();
                List<String> tmp = kd.getKeywords(userid,classstr);
                Message message = new Message();
                message.what = tmp.size();
                message.obj = tmp;
                messageHandler.sendMessage(message);
            }
        }.start();
    }
}
