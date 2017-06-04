package com.example.c.lifememo;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dao.NoteDao;
import com.models.note;

public class NoteEdit extends Activity {

    private EditText note_edit;
    private Button button;
    private int userid;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit);
        initView();
        Intent intent = getIntent();
        userid = Integer.valueOf(intent.getStringExtra("userid"));
        date = intent.getStringExtra("date");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = note_edit.getText().toString();
                note data = new note();
                data.setUserid(userid);
                data.setDate(date);
                data.setNotes(notes);
                new Thread(){
                    public void run(){
                        NoteDao nd = new NoteDao();
                        int num = nd.insertNote(data);
                        if(num > 0){
                            Intent intent = new Intent();
                            //intent.putExtra("note",data);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"INSERT WRONG",Toast.LENGTH_SHORT).show();
                        }
                    }
                }.start();
            }
        });
    }
    private void initView(){
        note_edit = (EditText)findViewById(R.id.editText_note);
        button = (Button)findViewById(R.id.note_finishbutton);
    }
}

