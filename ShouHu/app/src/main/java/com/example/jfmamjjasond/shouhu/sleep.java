package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TextView txt_sleep,txt_wake;
    ShouHou_DBAdapter myadapter;
    Calendar myCalendar;
    String sleep_time,wake_time;
    String test_name = "Amy";

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

        btn_sleep.setOnClickListener(myclickevent);
        btn_wake.setOnClickListener(myclickevent);

    }

    void init(){
        btn_sleep = (Button)getView().findViewById(R.id.sleepxml_btn_sleep);
        btn_wake = (Button)getView().findViewById(R.id.sleepxml_btn_wake);
        txt_sleep = (TextView)getView().findViewById(R.id.sleepxml_txt_sleep);
        txt_wake = (TextView)getView().findViewById(R.id.sleepxml_txt_wake);
    }

    View.OnClickListener myclickevent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.sleepxml_btn_sleep:
                    myCalendar = Calendar.getInstance();
                    sleep_time = String.valueOf(myCalendar.get(HOUR_OF_DAY))+":"
                            + String.valueOf(myCalendar.get(Calendar.MINUTE));
                    myadapter.add(test_name,sleep_time,"0:0");
                    Toast.makeText(thisactivity, sleep_time, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.sleepxml_btn_wake:
                    Cursor mycursor = myadapter.querybyname(test_name);
                    txt_sleep.setText(mycursor.getString(2));

                    myCalendar = Calendar.getInstance();
                    wake_time = String.valueOf(myCalendar.get(HOUR_OF_DAY))+":"
                            + String.valueOf(myCalendar.get(Calendar.MINUTE));
                    myadapter.modify(test_name,mycursor.getString(2),wake_time);
                    mycursor = myadapter.querybyname(test_name);
                    txt_wake.setText(mycursor.getString(3));
                    Toast.makeText(thisactivity, wake_time, Toast.LENGTH_SHORT).show();

                    break;
                default:

            }
        }
    };

}
