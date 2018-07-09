package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class start extends AppCompatActivity {

    Context thisactivity;
    Thread t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        thisactivity = this;

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);  //等3秒
                    moveToHomePage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t1.start();

    }

    void moveToHomePage(){
        Intent i = new Intent();
        //merge後要將MainActivity換成主頁的Activity
        i.setClass(thisactivity,MainActivity.class);  //跳到主頁
        startActivity(i);
        finish();
    }

}
