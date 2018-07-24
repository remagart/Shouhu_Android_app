package com.example.jfmamjjasond.shouhu;

import android.content.res.Configuration;
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
		return inflater.inflate(R.layout.homepage, container, false);	

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        res = getResources(); //取得資源
        screensize(); //取得裝置螢幕大小的分類


        animsleep =(AnimationDrawable)res.getDrawable(R.drawable.sleep); //設定動畫為sleep
        btnsleep.setImageDrawable(animsleep);//將buttonView的動畫設定
        btnsleep.setAdjustViewBounds(true);
        btnsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("睡眠資訊");//顯示資料
                animsleep =(AnimationDrawable)res.getDrawable(R.drawable.sleep);
                btnsleep.setImageDrawable(animsleep);
                animsleep.start();//按下按鈕，動畫執行
            }
        });
        animbmi =(AnimationDrawable)res.getDrawable(R.drawable.run);
        btnbmi.setImageDrawable(animbmi);
        btnbmi.setAdjustViewBounds(true);
        btnbmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("BMI資訊");
                animbmi =(AnimationDrawable)res.getDrawable(R.drawable.run);
                btnbmi.setImageDrawable(animbmi);
                animbmi.start();
            }
        });
        animwater =(AnimationDrawable)res.getDrawable(R.drawable.water);
        btnwater.setImageDrawable(animwater);
        btnwater.setAdjustViewBounds(true);
        btnwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("飲水資訊");
                animwater =(AnimationDrawable)res.getDrawable(R.drawable.water);
                btnwater.setImageDrawable(animwater);
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
    //取得裝置螢幕大小的分類
    public void screensize(){
        switch (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                btnsleep.setMaxHeight(300);
                btnsleep.setMaxWidth(300);
                btnbmi.setMaxHeight(300);
                btnbmi.setMaxWidth(300);
                btnwater.setMaxHeight(300);
                btnwater.setMaxWidth(300);
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                btnsleep.setMaxHeight(500);
                btnsleep.setMaxWidth(500);
                btnbmi.setMaxHeight(500);
                btnbmi.setMaxWidth(500);
                btnwater.setMaxHeight(500);
                btnwater.setMaxWidth(500);
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                btnsleep.setMaxHeight(600);
                btnsleep.setMaxWidth(600);
                btnbmi.setMaxHeight(600);
                btnbmi.setMaxWidth(600);
                btnwater.setMaxHeight(600);
                btnwater.setMaxWidth(600);
                break;
            case  Configuration.SCREENLAYOUT_SIZE_XLARGE:
                btnsleep.setMaxHeight(700);
                btnsleep.setMaxWidth(700);
                btnbmi.setMaxHeight(700);
                btnbmi.setMaxWidth(700);
                btnwater.setMaxHeight(700);
                btnwater.setMaxWidth(700);
                break;
        }
    }

}
