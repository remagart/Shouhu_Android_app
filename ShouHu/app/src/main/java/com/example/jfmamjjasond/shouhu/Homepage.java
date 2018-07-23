package com.example.jfmamjjasond.shouhu;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Homepage extends android.support.v4.app.Fragment {
    private TextView textView;
    private ImageButton btnsleep,btnbmi,btnwater;
    private AnimationDrawable animsleep ,animbmi ,animwater ;
    private Resources res;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_homepage, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        res = getResources(); //取得資源
        animsleep =(AnimationDrawable)res.getDrawable(R.drawable.sleep); //設定動畫為sleep
        btnsleep.setImageDrawable(animsleep);//將buttonView的動畫設定
        btnsleep.setAdjustViewBounds(true);
        btnsleep.setMaxHeight(200);
        btnsleep.setMaxWidth(200);
        btnsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("睡眠資訊");//顯示資料
                animsleep =(AnimationDrawable)res.getDrawable(R.drawable.sleep);
                btnsleep.setImageDrawable(animsleep);
                btnsleep.setAdjustViewBounds(true);
                btnsleep.setMaxHeight(200);
                btnsleep.setMaxWidth(200);
                animsleep.start();//按下按鈕，動畫執行
            }
        });
        animbmi =(AnimationDrawable)res.getDrawable(R.drawable.run);
        btnbmi.setImageDrawable(animbmi);
        btnbmi.setAdjustViewBounds(true);
        btnbmi.setMaxHeight(200);
        btnbmi.setMaxWidth(200);
        btnbmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("BMI資訊");
                animbmi =(AnimationDrawable)res.getDrawable(R.drawable.run);
                btnbmi.setImageDrawable(animbmi);
                btnbmi.setAdjustViewBounds(true);
                btnbmi.setMaxHeight(200);
                btnbmi.setMaxWidth(200);
                animbmi.start();
            }
        });
        animwater =(AnimationDrawable)res.getDrawable(R.drawable.water);
        btnwater.setImageDrawable(animwater);
        btnwater.setAdjustViewBounds(true);
        btnwater.setMaxHeight(200);
        btnwater.setMaxWidth(200);
        btnwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("飲水資訊");
                animwater =(AnimationDrawable)res.getDrawable(R.drawable.water);
                btnwater.setImageDrawable(animwater);
                btnwater.setAdjustViewBounds(true);
                btnwater.setMaxHeight(200);
                btnwater.setMaxWidth(200);
                animwater.start();
            }
        });
    }

    public void findView(){

        btnsleep =getView().findViewById(R.id.btnsleep);
        btnbmi =getView().findViewById(R.id.btnbmi);
        btnwater =getView().findViewById(R.id.btnwater);
        textView =getView().findViewById(R.id.textView);

    }

}
