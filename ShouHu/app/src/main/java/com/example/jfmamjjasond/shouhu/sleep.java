package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static java.util.Calendar.HOUR_OF_DAY;

public class sleep extends android.support.v4.app.Fragment {

    Context thisactivity;
    Button btn_sleep,btn_wake;
    TextView txt_sleep,txt_wake,txt_reminder;
    ShouHou_DBAdapter myadapter;
    Calendar myCalendar;
    String test_name = "Haha";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        thisactivity = getContext();

        init();
        myadapter = new ShouHou_DBAdapter(thisactivity);

        Cursor mycursor = myadapter.querybyname(test_name);
        if(mycursor.getCount() != 0){
            txt_sleep.setText(mycursor.getString(2));
            txt_wake.setText(mycursor.getString(3));
            reminder(mycursor);
        }
        btn_sleep.setOnClickListener(myclickevent);
        btn_wake.setOnClickListener(myclickevent);

    }

    void init(){
        btn_sleep = (Button)getView().findViewById(R.id.sleepxml_btn_sleep);
        btn_wake = (Button)getView().findViewById(R.id.sleepxml_btn_wake);
        txt_sleep = (TextView)getView().findViewById(R.id.sleepxml_txt_sleep);
        txt_wake = (TextView)getView().findViewById(R.id.sleepxml_txt_wake);
        txt_reminder = (TextView)getView().findViewById(R.id.sleepxml_txt_reminder);
    }

    View.OnClickListener myclickevent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nowtime;
            Cursor mycursor;
            switch(v.getId()){
                case R.id.sleepxml_btn_sleep:
                    mycursor = myadapter.querybyname(test_name);
                    nowtime = gettime();
                    if(mycursor.getCount() == 0){
                        add_sleep_time(nowtime);
                    }
                  else{
                        modify_sleep_time(nowtime,mycursor);
                   }
                   Toast.makeText(thisactivity, "你今晚睡眠時間為 "+nowtime, Toast.LENGTH_SHORT).show();

                    break;
                case R.id.sleepxml_btn_wake:
                    mycursor = myadapter.querybyname(test_name);
                    nowtime = gettime();
                    if(mycursor.getCount() == 0){
                        add_wake_time(nowtime);
                    }
                    else{
                        modify_wake_time(nowtime,mycursor);
                    }
                    mycursor = myadapter.querybyname(test_name);
                    txt_sleep.setText(mycursor.getString(2));
                    txt_wake.setText(mycursor.getString(3));
                    reminder(mycursor);
                    Toast.makeText(thisactivity, "早安阿!祝你有美好的一天^ ^ ", Toast.LENGTH_SHORT).show();
                    break;
                default:

            }
        }
    };

    String gettime(){
        String t;
        myCalendar = Calendar.getInstance();
        t = String.valueOf(myCalendar.get(HOUR_OF_DAY))+":"
                + String.valueOf(myCalendar.get(Calendar.MINUTE));
        return t;
    }
    void add_sleep_time(String t){
        myadapter.add(test_name,t,"");
    }
    void add_wake_time(String t){
        myadapter.add(test_name,"",t);
    }

    void modify_sleep_time(String t,Cursor c){
        myadapter.modify(test_name,t,c.getString(3));
    }
    void modify_wake_time(String t,Cursor c){
        myadapter.modify(test_name,c.getString(2),t);
    }
    void reminder(Cursor c){
        int during_hour;
        String[] start,end;
        int[] start_num = new int[2];
        int[] end_num = new int[2];

        start = c.getString(2).split(":");
        end = c.getString(3).split(":");

        start_num[0] = Integer.valueOf(start[0]);
        start_num[1] = Integer.valueOf(start[1]);
        end_num[0] = Integer.valueOf(end[0]);
        end_num[1] = Integer.valueOf(end[1]);

        if(end_num[0] < start_num[0]){
            during_hour = 24 - start_num[0];
            during_hour = during_hour + end_num[0];
        }
        else if(end_num[0] > start_num[0]){
            during_hour = end_num[0] - start_num[0];
        }
        else{
            during_hour = 0;
        }

        if(during_hour > 8){
            txt_reminder.setText("你好像有點睡過量囉!!");
        }
        else if(during_hour < 6){
            txt_reminder.setText("歐歐~~你睡不太夠欸!!");
        }
        else{
            txt_reminder.setText("太棒了!!你睡得剛剛好喔!!");
        }

    }

}
