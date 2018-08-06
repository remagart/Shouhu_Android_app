package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Homepage extends android.support.v4.app.Fragment {
    private TextView textView;
    private ImageButton btnsleep,btnbmi,btnwater;
    private AnimationDrawable animsleep ,animbmi ,animwater ;
    private Resources res;
    //使用者姓名
    String ShouHu_user_name;
    String user_name;
    //為了上方資訊列設定鍵值
    final String KEY_SLEEP = "sleep";
    final String KEY_BMI = "bmi";
    final String KEY_WATER = "water";

    //設定資料庫相關變數
    ShouHou_DBAdapter myadapter;
    WDBAdapter wdbAdapter;
    Cursor mycursor,wcursor;
    Context thisactivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.homepage, container, false);	

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //取得本頁context
        thisactivity = getContext();
        //初始化UI和java
        findView();
        //從MainActivity取得使用者名稱
        get_from_activity();
        //設定資料庫
        myadapter = new ShouHou_DBAdapter(thisactivity);
        wdbAdapter = new WDBAdapter(thisactivity);

        res = getResources(); //取得資源

        screensize(); //取得裝置螢幕大小的分類


        animsleep =(AnimationDrawable)res.getDrawable(R.drawable.sleep); //設定動畫為sleep
        btnsleep.setImageDrawable(animsleep);//將buttonView的動畫設定
        btnsleep.setAdjustViewBounds(true);
        btnsleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //顯示上方資訊列
                textView.setText(show_Homepaeg_bar("你昨晚睡眠資訊為 ",KEY_SLEEP));//顯示資料
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
                //顯示上方資訊列
                textView.setText(show_Homepaeg_bar("你目前BMI資訊為 ",KEY_BMI));
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
                textView.setText(show_Homepaeg_bar("你今天飲水資訊為 ",KEY_WATER));
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
    public void screensize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //取的螢幕解析度大小設定圖片大小
        switch (metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                screenPixel(250);//取得螢幕寬高的方法
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                screenPixel(275);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                screenPixel(300);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                screenPixel(325);
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                screenPixel(350);
                break;
            default:
                screenPixel(400);
                break;
        }

    }
    //取得螢幕寬高的方法並設置圖片大小
    public void screenPixel(int w){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        //int height = metrics.heightPixels;
        if (width < 760) {
            btnsleep.setMaxHeight(w-50);
            btnsleep.setMaxWidth(w-50);
            btnbmi.setMaxHeight(w-50);
            btnbmi.setMaxWidth(w-50);
            btnwater.setMaxHeight(w-50);
            btnwater.setMaxWidth(w-50);
        }
        if (width > 760 && width<1024){
            btnsleep.setMaxHeight(w);
            btnsleep.setMaxWidth(w);
            btnbmi.setMaxHeight(w);
            btnbmi.setMaxWidth(w);
            btnwater.setMaxHeight(w);
            btnwater.setMaxWidth(w);
        }
        if (width>1024 && width<1440) {
            btnsleep.setMaxHeight(w+50);
            btnsleep.setMaxWidth(w+50);
            btnbmi.setMaxHeight(w+50);
            btnbmi.setMaxWidth(w+50);
            btnwater.setMaxHeight(w+50);
            btnwater.setMaxWidth(w+50);
        }
//        if (width>1440) {
//            btnsleep.setMaxHeight(500);
//            btnsleep.setMaxWidth(500);
//            btnbmi.setMaxHeight(500);
//            btnbmi.setMaxWidth(500);
//            btnwater.setMaxHeight(500);
//            btnwater.setMaxWidth(500);
//        }
    }

    //顯示上方資訊列
    String show_Homepaeg_bar(String title,String key){
        String temp = title;
        //先取得姓名在資料庫的資訊
        mycursor = myadapter.querybyname_from_user_table(user_name);
        if(mycursor.getCount() != 0){
            if(key.equals(KEY_SLEEP)){
                //為了要使用sleep中的方法
                sleep temp_sleep = new sleep();
                String temp_sleeptime = mycursor.getString(2);
                String temp_waketime = mycursor.getString(3);
                int[] temp_during_time;
                //有睡眠時間和醒來時間才要顯示
                if(temp_sleeptime != null && temp_waketime != null){
                    //使用sleep中的方法
                    temp_during_time = temp_sleep.calculate_during_time(mycursor);

                    temp = temp + temp_sleeptime + " ~ " + temp_waketime
                            + "\n共睡了 " + String.valueOf(temp_during_time[0] + " 小時 "
                            + String.valueOf(temp_during_time[1]) + "分鐘");
                }
                else{
                    temp = "記得做睡眠記錄喔~";
                }
                return temp;
            }
            else if(key.equals(KEY_BMI)){
                //為了要使用BMI_result中的方法
                BMI_result temp_bmi_result = new BMI_result();
                double temp_h = mycursor.getDouble(4);
                double temp_w = mycursor.getDouble(5);
                //使用BMI_result中的方法
                double temp_bmi = temp_bmi_result.calculateBMI(temp_h,temp_w);
                temp = temp + String.valueOf(temp_bmi);
            }
            else if(key.equals(KEY_WATER)){
                    String date = getdate();
                    wcursor = wdbAdapter.querydatachecked(user_name,date,"true");
                    if(wcursor.getCount()!=0){
                        switch (Integer.valueOf(wcursor.getString(wcursor.getColumnIndexOrThrow("position")))){
                            case 0:
                                temp="今日飲水量<500cc喔";
                                break;
                            case 1:
                               temp="今日飲水量501~1000cc喔";
                                break;
                            case 2:
                                temp="今日飲水量1001~1500cc喔";
                                break;
                            case 3:
                                temp="今日飲水量1501~2000cc喔";
                                break;
                            case 4:
                                temp="今日飲水量2001~2500cc喔,很棒喔!";
                                break;
                            case 5:
                                temp="今日飲水量>2500cc,很厲害喔!";
                                break;
                            default:
                                temp="今日還沒喝水喔!!";
                                break;
                        }
                    }else {temp="今天還未做飲水記錄喔";}
            }
        }
        else {
            Toast.makeText(thisactivity, "沒有該使用者喔~", Toast.LENGTH_SHORT).show();
        }
        return temp;
    }

    //從MainActivity取得使用者名稱
    void get_from_activity(){
        Bundle mybundle = getArguments();
        ShouHu_user_name = mybundle.getString("user_name");
        user_name = ShouHu_user_name;
    }
    public String getdate(){
        final Calendar c =Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        String date = year+"-"+month+"-"+dayOfMonth;
        return date;
    }
}
