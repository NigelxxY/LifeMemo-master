package com.example.c.lifememo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.VIew.Keywords_dialog;
import com.dao.EventDao;
import com.dao.KeywordsDao;
import com.models.KeywordsAdapter;

import java.util.ArrayList;
import java.util.List;

public class KeywordsEdit extends AppCompatActivity implements View.OnClickListener{
    private KeywordsAdapter keywordsAdapter;
    private ListView keywords_list;
    private TextView title;
    private Button finish_button,edit_button,add_button;
    private List<String> testkeywords = new ArrayList<>();
    private int userid;
    private String type;
    private int nowSelectedPosition;

    Handler getHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what>0){
                testkeywords = (List<String>)msg.obj;
                Log.e("list_size",testkeywords.size()+"");
                keywordsAdapter = new KeywordsAdapter(KeywordsEdit.this,testkeywords);
                keywords_list.setAdapter(keywordsAdapter);
                keywords_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        nowSelectedPosition = position;
                    }
                });
            }
        }
    };
    Handler deleteHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            if (message.what>0){
                testkeywords.remove(nowSelectedPosition);
                keywordsAdapter = new KeywordsAdapter(KeywordsEdit.this,testkeywords);
                keywords_list.setAdapter(keywordsAdapter);
            }
        }
    };
    Handler editHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            if (message.what>0){
                testkeywords.set(nowSelectedPosition,(String)message.obj);
                keywordsAdapter = new KeywordsAdapter(KeywordsEdit.this,testkeywords);
                keywords_list.setAdapter(keywordsAdapter);
            }
        }
    };
    Handler addHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            if (message.what>0){
                testkeywords.add((String)message.obj);
                keywordsAdapter = new KeywordsAdapter(KeywordsEdit.this,testkeywords);
                keywords_list.setAdapter(keywordsAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keywords_edit_eat);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        userid = Integer.valueOf(intent.getStringExtra("userid"));

        title = (TextView)findViewById(R.id.keywords_edit_title);
        finish_button= (Button)findViewById(R.id.edit_delete);
        finish_button.setOnClickListener(this);
        edit_button = (Button)findViewById(R.id.edit_edit);
        edit_button.setOnClickListener(this);
        add_button = (Button)findViewById(R.id.edit_add);
        add_button.setOnClickListener(this);
        keywords_list = (ListView)findViewById(R.id.keywords_eat_list);

        new Thread(){
            public void run(){
                KeywordsDao kd = new KeywordsDao();
                List<String> tmp= kd.getKeywords(userid,type);
                Message msg = new Message();
                msg.what = tmp.size();
                msg.obj = tmp;
                getHandler.sendMessage(msg);
            }
        }.start();

        title.setText(type+" Keywords");
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.edit_delete:
                Log.e("str",testkeywords.get(nowSelectedPosition)+"+++"+userid);
                new Thread(){
                    public void run(){
                        KeywordsDao kd = new KeywordsDao();
                        int num = kd.deleteKeyword(userid,testkeywords.get(nowSelectedPosition));
                        Message message = new Message();
                        message.what = num;
                        deleteHandler.sendMessage(message);
                    }
                }.start();
                break;
            case R.id.edit_edit:
                Keywords_dialog.OnCloseListener listener = new Keywords_dialog.OnCloseListener() {
                    @Override
                    public void getText(String str) {
                        new Thread(){
                            public void run() {
                                KeywordsDao kd_edit = new KeywordsDao();
                                int num = kd_edit.UpdateKeyword(userid,testkeywords.get(nowSelectedPosition),str);
                                Message message_edit = new Message();
                                message_edit.what = num;
                                message_edit.obj = str;
                                editHandler.sendMessage(message_edit);
                            }
                        }.start();
                    }
                };
                Keywords_dialog dialog = new Keywords_dialog(KeywordsEdit.this,testkeywords.get(nowSelectedPosition),listener);
                dialog.show();
                break;
            default:
                Keywords_dialog.OnCloseListener listener_add = new Keywords_dialog.OnCloseListener() {
                    @Override
                    public void getText(String str) {
                        Toast.makeText(getApplicationContext(),str+"!!!",Toast.LENGTH_SHORT).show();
                        new Thread(){
                            public void run() {
                                KeywordsDao kd_add = new KeywordsDao();
                                int num = kd_add.InsertKeyword(userid,str,type);
                                Message message_add = new Message();
                                message_add.what = num;
                                message_add.obj = str;
                                addHandler.sendMessage(message_add);
                            }
                        }.start();
                    }
                };
                Keywords_dialog dialog_add = new Keywords_dialog(KeywordsEdit.this,null,listener_add);
                dialog_add.show();
                break;

        }
    }

}
