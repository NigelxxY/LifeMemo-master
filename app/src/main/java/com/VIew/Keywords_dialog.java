package com.VIew;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.c.lifememo.R;

/**
 * Created by Nigel_xxY on 2017/5/26.
 */

public class Keywords_dialog extends Dialog  {
    private EditText keyword_edit;
    private Button save_button;
    private Button cancel_button;

    private Context mContext;
    private String keywords;
    private OnCloseListener listener;

    public Keywords_dialog(Context context,String str,OnCloseListener listener){
        super(context);
        this.mContext = context;
        this.keywords = str;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        View bv = this.findViewById(android.R.id.title);
        bv.setVisibility(View.GONE);
        setContentView(R.layout.keywords_edit);
        setCanceledOnTouchOutside(false);
        initView();
    }
    private void initView(){
        keyword_edit = (EditText)findViewById(R.id.keywords_edit);
        save_button = (Button)findViewById(R.id.keywords_edit_save);
        cancel_button = (Button)findViewById(R.id.keywords_edit_cancel);
        keyword_edit.setText(keywords);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getText(keyword_edit.getText().toString());
                dismiss();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getText(keyword_edit.getText().toString());
                dismiss();
            }
        });
    }

    public interface OnCloseListener{
        void getText(String str);
    }
}
