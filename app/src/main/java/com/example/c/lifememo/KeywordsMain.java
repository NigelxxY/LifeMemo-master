package com.example.c.lifememo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class KeywordsMain extends AppCompatActivity {
    private int userid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keywords_main);
        Intent intent = getIntent();
        userid = Integer.valueOf(intent.getStringExtra("userid"));
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.keywords_sleep:
                Intent intent1 = new Intent();
                intent1.putExtra("type","sleep");
                intent1.putExtra("userid",String.valueOf(userid));
                intent1.setClass(KeywordsMain.this,KeywordsEdit.class);
                startActivity(intent1);
                break;
            case R.id.keywords_eat:
                Intent intent2 = new Intent();
                intent2.putExtra("type","eat");
                intent2.putExtra("userid",String.valueOf(userid));
                intent2.setClass(KeywordsMain.this,KeywordsEdit.class);
                startActivity(intent2);
                break;
            case R.id.keywords_boring:
                Intent intent3 = new Intent();
                intent3.putExtra("type","boring");
                intent3.putExtra("userid",String.valueOf(userid));
                intent3.setClass(KeywordsMain.this,KeywordsEdit.class);
                startActivity(intent3);
                break;
            case R.id.keywords_study:
                Intent intent = new Intent();
                intent.putExtra("type","study");
                intent.putExtra("userid",String.valueOf(userid));
                intent.setClass(KeywordsMain.this,KeywordsEdit.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
