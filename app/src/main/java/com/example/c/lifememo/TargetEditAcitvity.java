package com.example.c.lifememo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dao.TargetDao;

/**
 * Created by Nigel_xxY on 2017/5/26.
 */

public class TargetEditAcitvity extends Activity {
    private int userid ;
    private String date;
    private int isNewDay;
    @Override
    public void onCreate(Bundle saveInstanceStare){
        super.onCreate(saveInstanceStare);
        setContentView(R.layout.target_edit);
        Intent intent = getIntent();
        userid = Integer.valueOf(intent.getStringExtra("userid"));
        date = intent.getStringExtra("date");
        isNewDay = Integer.valueOf(intent.getStringExtra("isNewDay"));
        String tt1 = intent.getStringExtra("target1");
        String tt2 = intent.getStringExtra("target2");
        String tt3 = intent.getStringExtra("target3");

        final EditText t1 = (EditText)findViewById(R.id.edit_target1);
        final EditText t2 = (EditText)findViewById(R.id.edit_target2);
        final EditText t3 = (EditText)findViewById(R.id.edit_target3);

        t1.setText(tt1);
        t2.setText(tt2);
        t3.setText(tt3);

        Button button = (Button)findViewById(R.id.button_target_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target1 = t1.getText().toString();
                String target2 = t2.getText().toString();
                String target3 = t3.getText().toString();
                new Thread(){
                    public void run(){
                        if(isNewDay==1) {
                            TargetDao td = new TargetDao();
                            td.changetarget(userid,target1,target2,target3,date);
                        }else{
                            TargetDao td =new TargetDao();
                            td.inserttarget(userid,target1,target2,target3,date);
                        }
                    }
                }.start();
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("target1",target1);
                b.putString("target2",target2);
                b.putString("target3",target3);
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
