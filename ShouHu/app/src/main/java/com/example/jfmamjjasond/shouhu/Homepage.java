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
    private ImageView pic;
    private ImageButton btnsleep,btnbmi,btnwater;
    private AnimationDrawable anim , animsleep ,animbmi ,animwater ;
    private Resources res;

    private java.util.Timer timeranimSleep,timeranimBmi,timeranimWater,timeranimAll,timer;
    private TimerTask timerTaskanimSleep,timerTaskanimBmi,timerTaskanimWater,timerTaskanimAll,timerTask;
    private int i =0, timewater,timebmi,timesleep,timeall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_homepage, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timewater =0; //配合start.java 設定開始時間只有一開始執行App會跑的方法
        timebmi=13000;
        timesleep=20500;
        timeall=27500;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        res = getResources();
        anim = (AnimationDrawable) res.getDrawable(R.drawable.allanim);
        pic.setImageDrawable(anim);
        pic.setAdjustViewBounds(true);
        pic.setMaxWidth(400);
        pic.setMaxHeight(400);
        findView();

        anim.start();//ImageView動畫開始
        Log.i("time=", String.valueOf(timewater));
        startanimSleep();
        timewater =0; //之後執行的時間設定
        timebmi=6000;
        timesleep=13500;
        timeall=21500;


        animsleep =(AnimationDrawable)res.getDrawable(R.drawable.sleep);
        btnsleep.setImageDrawable(animsleep);
        btnsleep.setAdjustViewBounds(true);
        btnsleep.setMaxHeight(200);
        btnsleep.setMaxWidth(200);
        btnsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("睡眠資訊");
                anim = (AnimationDrawable) res.getDrawable(R.drawable.sleep);
                pic.setImageDrawable(anim);
                pic.setAdjustViewBounds(true);
                pic.setMaxWidth(400);
                pic.setMaxHeight(400);
                anim.start();
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
                anim = (AnimationDrawable) res.getDrawable(R.drawable.run);
                pic.setImageDrawable(anim);
                pic.setAdjustViewBounds(true);
                pic.setMaxWidth(400);
                pic.setMaxHeight(400);
                anim.start();
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
                anim = (AnimationDrawable) res.getDrawable(R.drawable.water);
                pic.setImageDrawable(anim);
                pic.setAdjustViewBounds(true);
                pic.setMaxWidth(400);
                pic.setMaxHeight(400);
                anim.start();
            }
        });
    }

    public void findView(){
        pic = getView().findViewById(R.id.imageView);
        btnsleep =getView().findViewById(R.id.btnsleep);
        btnbmi =getView().findViewById(R.id.btnbmi);
        btnwater =getView().findViewById(R.id.btnwater);
        textView =getView().findViewById(R.id.textView);

    }
    private Handler myhandler  = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.arg1){
                case 100:
                    textView.setText("飲水資訊");
                    break;
                case 200:
                    textView.setText("BMI資訊");
                    break;
                case 300:
                    textView.setText("睡眠資訊");
                    break;
                case 400:
                    btnwater.setVisibility(View.VISIBLE);
                    btnbmi.setVisibility(View.VISIBLE);
                    btnsleep.setVisibility(View.VISIBLE);
                    break;

            }
            return false;
        }
    });
    public void startanimSleep(){
        Log.i("time2=", String.valueOf(timebmi));
        timeranimSleep = new Timer();
        timeranimBmi =new Timer();
        timeranimWater = new Timer();
        timeranimAll = new Timer();

        timerTaskanimSleep = new TimerTask() {
            @Override
            public void run() {
                i=300;
                Message message= myhandler.obtainMessage();
                message.arg1 = i;
                myhandler.sendMessage(message);
            }
        };
        timerTaskanimBmi = new TimerTask() {
            @Override
            public void run() {
                i=200;
                Message message= myhandler.obtainMessage();
                message.arg1 = i;
                myhandler.sendMessage(message);
            }
        };
        timerTaskanimWater = new TimerTask() {
            @Override
            public void run() {
                i=100;
                Message message= myhandler.obtainMessage();
                message.arg1 = i;
                myhandler.sendMessage(message);
            }
        };
        timerTaskanimAll = new TimerTask() {
            @Override
            public void run() {
                i=400;
                Message message= myhandler.obtainMessage();
                message.arg1 = i;
                myhandler.sendMessage(message);
            }
        };
        timeranimWater.schedule(timerTaskanimWater, timewater);
        timeranimBmi.schedule(timerTaskanimBmi,timebmi);
        timeranimSleep.schedule(timerTaskanimSleep,timesleep);
        timeranimAll.schedule(timerTaskanimAll,timeall);
    }
    //Fragment 銷毀時執行timer取消
       @Override
    public void onDestroy() {
        super.onDestroy();
        timeranimWater.cancel();
        timeranimBmi.cancel();
        timeranimSleep.cancel();
        timeranimAll.cancel();
    }
}
