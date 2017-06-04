package com.example.c.lifememo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dao.loginDao;

/**
 * Created by Nigel_xxY on 2017/5/27.
 */

public class LoginActivity extends Activity {
    private EditText username,password;
    private Button login,register;
    private int userid;
    private boolean loginFlag;
    final private int LOGINSUCCESS = 1;
    final private int LOGINFAIL = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == LOGINSUCCESS){
                loginFlag = true;
                userid = (int)msg.obj;
            }
        }
    };
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        initView();
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String psd = password.getText().toString();
                new Thread(){
                    public void run(){
                        loginDao lt = new loginDao();
                        int id =lt.loginin(name,psd);
                        Message msg = new Message();
                        if (id!=0){
                            msg.what = LOGINSUCCESS;
                            msg.obj = id;
                        }else
                            msg.what = LOGINFAIL;
                        mHandler.sendMessage(msg);
                    }
                }.start();
                if (loginFlag){
                    Intent intent =new Intent();
                    intent.putExtra("userid",String.valueOf(userid));
                    Log.e("loginid",userid+"");
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"login failure!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView(){
        username = (EditText)findViewById(R.id.input_username);
        password = (EditText)findViewById(R.id.input_password);
        login = (Button)findViewById(R.id.button_login);
        register = (Button)findViewById(R.id.button_register);
    }
}
