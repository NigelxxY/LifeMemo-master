package com.example.c.lifememo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.dao.EventDao;
import com.dao.NoteDao;
import com.dao.TargetDao;
import com.models.NotesAdapter;
import com.models.TargetAdapter;
import com.models.event;
import com.models.note;
import com.models.target;
import com.models.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RadioGroup main_eventsornotes;
    private int userid = 3;
    private String date;
    private int isNewDay = 1;
    private target targetList;
    private List<event> eventList = new ArrayList<>();
    private List<note> noteList = new ArrayList<>();

    final private int LOGININ = 1;
    final private int EDITTARGET = 0;
    final private int EDITEVENT = 2;
    final private int EDITNOTES = 3;
    final private int DIARY = 4;

    private int main_chooseid = 0;
    private ListView mainList;
    private TargetAdapter targetAdapter;
    private NotesAdapter notesAdapter;
    private TextView tt1;
    private TextView tt2;
    private TextView tt3;
    private TextView main_date ;
    private RelativeLayout layout_event;
    private RelativeLayout layout_note;
    private DrawerLayout mainDrawer;
    private Button user_system;
    private NavigationView navigationView;
    private ImageView touxiang;

    private Handler targetHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what>0){
                targetList = (target) msg.obj;
                tt1.setText(targetList.getTarget().get(0));
                tt2.setText(targetList.getTarget().get(1));
                tt3.setText(targetList.getTarget().get(2));
            }else{
                isNewDay = 0;
            }

        }
    };
    private Handler eventHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what>0)
                eventList = (List<event>) msg.obj;
                mainList = (ListView) findViewById(R.id.main_event_listview);
                targetAdapter = new TargetAdapter(MainActivity.this, eventList);
                mainList.setAdapter(targetAdapter);

        }
    };
    private Handler noteHandler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what>0)
                noteList = (List<note>) msg.obj;
            mainList = (ListView) findViewById(R.id.main_note_listview);
            notesAdapter = new NotesAdapter(MainActivity.this, noteList);
            mainList.setAdapter(notesAdapter);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer);
        initView();
        //z这个是要获取的
        date = "2017-05-29";
        if(userid!=0){
            getTarget();
            getEvent();
        }
        user_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDrawer.openDrawer(Gravity.LEFT);
            }
        });
        View headview = navigationView.inflateHeaderView(R.layout.nav_header);
        touxiang = (ImageView)headview.findViewById(R.id.touxiang);
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,LOGININ);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                switch (item.getItemId()) {
                    case R.id.diary:
                        intent = new Intent(MainActivity.this, Diary.class);
                        startActivityForResult(intent,DIARY);
                        break;
                    case R.id.analysis:
                        intent = new Intent();
                        intent.setClass(MainActivity.this,AnalysisDay.class);
                        intent.putExtra("eventlist",(Serializable)eventList);
                        intent.putExtra("now_date",date);
                        intent.putExtra("userid",String.valueOf(userid));
                        startActivity(intent);
                        break;
                    case R.id.keywords:
                        intent = new Intent(MainActivity.this, KeywordsMain.class);
                        intent.putExtra("userid",String.valueOf(userid));
                        startActivity(intent);
                        break;
                    case R.id.setting:
                        break;
                    case R.id.quit:
                        finish();
                }
                return false;
            }
        });


        //显示日期
        main_date.setText(main_GetYear() + "." + main_GetMonth() + "." + main_GetDays());
        //设置日期格式

        RelativeLayout.LayoutParams main_date_params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        main_date_params.addRule(RelativeLayout.RIGHT_OF, R.id.button_usersystem);
        main_date_params.setMargins(20, 0, 0, 0);
        main_date_params.addRule(RelativeLayout.CENTER_VERTICAL);
        main_date.setLayoutParams(main_date_params);
        //设置日期相对位置关系

        //RadioGroup的工作
        final RadioButton rb1 = (RadioButton) findViewById(R.id.event);
        final RadioButton rb2 = (RadioButton) findViewById(R.id.notes);

        main_eventsornotes = (RadioGroup) findViewById(R.id.eventsornotes);
        main_eventsornotes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedid) {
                int now_id = group.getCheckedRadioButtonId();
                if (now_id == rb1.getId())
                    clickEvent();
                else if (now_id == rb2.getId())
                    clickNote();
            }
        });


        FloatingActionButton main_floatingbutton_notes = (FloatingActionButton) findViewById(R.id.floatadd_button);
        main_floatingbutton_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userid==0){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,LOGININ);
                }
                switch (main_chooseid) {
                    case 0:
                        Intent intent = new Intent();
                        intent.putExtra("userid",String.valueOf(userid));
                        intent.setClass(MainActivity.this, EventEdit.class);
                        startActivityForResult(intent,EDITEVENT);
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.putExtra("userid",String.valueOf(userid));
                        intent1.putExtra("date",date);
                        intent1.setClass(MainActivity.this, NoteEdit.class);
                        startActivityForResult(intent1,EDITNOTES);
                        break;
                }
            }
        });//悬浮按钮响应

        RelativeLayout target = (RelativeLayout)findViewById(R.id.main_target);
        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"!!!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TargetEditAcitvity.class);
                intent.putExtra("userid",String.valueOf(userid));
                intent.putExtra("date",date);
                intent.putExtra("isNewDay",String.valueOf(isNewDay));
                intent.putExtra("target1",tt1.getText());
                intent.putExtra("target2",tt2.getText());
                intent.putExtra("target3",tt3.getText());
                startActivityForResult(intent,EDITTARGET);
            }
        });

    }

    private void initView(){
        tt1 = (TextView)findViewById(R.id.main_firsttarget);
        tt2 = (TextView)findViewById(R.id.main_secondtarget);
        tt3 =(TextView)findViewById(R.id.main_thirdtarget);
        main_date = (TextView) findViewById(R.id.date);
        layout_event =(RelativeLayout)findViewById(R.id.layout_event);
        layout_note = (RelativeLayout)findViewById(R.id.layout_notes);
        mainDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        user_system = (Button)findViewById(R.id.button_usersystem);
        navigationView=(NavigationView)findViewById(R.id.navigation);
    }


    private String main_GetYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String s = sdf.format(new Date());
        return s;
    }
    private String main_GetMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String s = sdf.format(new Date());
        return s;
    }
    private String main_GetDays(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String s = sdf.format(new Date());
        return s;
    }

    private void clickEvent(){
        main_chooseid = 0;
        layout_event.setVisibility(View.VISIBLE);
        layout_note.setVisibility(View.GONE);
        if (userid==0) {
            Toast.makeText(getApplicationContext(), "Please login first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent,LOGININ);
        }else {
            getTarget();
            getEvent();
            mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"别点我",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void clickNote(){
        main_chooseid = 1;
        layout_event.setVisibility(View.GONE);
        layout_note.setVisibility(View.VISIBLE);
        if (userid ==0){
            Toast.makeText(getApplicationContext(), "Please login first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivityForResult(intent,LOGININ);
        }else{
            getNotes();
            mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(),"totototo",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case EDITTARGET:
                if(resultCode!=RESULT_OK)
                    break;
                Bundle b  = data .getExtras();
                String target1 = b.getString("target1");
                String target2 = b.getString("target2");
                String target3 = b.getString("target3");
                tt1.setText(target1);
                tt2.setText(target2);
                tt3.setText(target3);
                break;
            case LOGININ:
                if(resultCode!=RESULT_OK)
                    break;
                userid = Integer.valueOf(data.getStringExtra("userid"));
                getTarget();
                getEvent();
                if (userid==0)
                    Toast.makeText(getApplicationContext(),"login failure!Login again Please",Toast.LENGTH_SHORT).show();
                break;
            case EDITEVENT:
                if(resultCode!=RESULT_OK)
                    break;
                getEvent();
                break;
            case EDITNOTES:
                if(resultCode!=RESULT_OK)
                    break;
                getNotes();
                break;
            case DIARY:
                date = data.getStringExtra("selectedDate");
                main_date.setText(date);
                getTarget();
                getEvent();
                break;
            default:
                break;
        }
    }
    private void getTarget(){
        new Thread(){
            public void run(){
                TargetDao td = new TargetDao();
                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //日期要选的！！！！
                Date date = new Date();
                String datestr = sdf.format(date);*/
                target tmp_target = td.getTargetList(userid,date);
                Message message = new Message();
                message.what = tmp_target.getTargetnum();
                message.obj = tmp_target;
                targetHandler.sendMessage(message);
            }
        }.start();
    }
    private void getEvent(){
        new Thread(){
            @Override
            public void run(){
                EventDao ed = new EventDao();
                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //日期要选的！！！！
                Date date = new Date();
                String datestr = sdf.format(date);*/
                List<event> tmp_event = ed.getEventList(userid,date);
                Message message = new Message();
                message.what = 1;
                message.obj = tmp_event;
                eventHandler.sendMessage(message);
            }
        }.start();
    }
    private void getNotes(){
        new Thread(){
            public void run(){
                NoteDao nd = new NoteDao();
                List<note> tmp_note = nd.getNote(userid,date);
                Log.e("date",date+"--------"+tmp_note.size()+"");
                Message message = new Message();
                message.what = tmp_note.size();
                message.obj = tmp_note;
                noteHandler.sendMessage(message);
            }
        }.start();
    }
}




