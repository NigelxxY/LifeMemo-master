package com.example.c.lifememo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dao.registerDao;

/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class RegisterActivity extends Activity {
    private EditText input_username,input_password;
    private Button submit,clear;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.registor);

        initView();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = input_username.getText().toString();
                String password = input_password.getText().toString();
                new Thread(){
                    public void run(){
                        registerDao rd = new registerDao();
                        boolean flag=rd.registerInsert(username,password);
                        if (flag)
                            finish();
                    }
                }.start();
            }
        });
    }
    private void initView(){
        input_username = (EditText)findViewById(R.id.register_username);
        input_password = (EditText)findViewById(R.id.register_password);
        submit = (Button)findViewById(R.id.register_submit);
        clear = (Button)findViewById(R.id.register_clear);
    }
}
