package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class start extends AppCompatActivity {

    Context thisactivity;
    Thread t1;
    final int MOVE_TO_HOME = 100;  //自訂message的編號而已
    Handler myhandle;  //用於傳遞消息
    Message m = new Message();  //定義消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        thisactivity = this;  //此時thisactivity就是這個activity的context

        slogandelay();  // 搭配Thread處理等3秒
        moveToHomePage();  // 搭配Handle移到下一頁
        t1.start();

    }
    //@SuppressLint("HandlerLeak")
    void moveToHomePage(){
        //若沒有Handle.Callback()，則需要這行@SuppressLint("HandlerLeak")
        //否則會有黃色警告代表
        myhandle = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MOVE_TO_HOME) {
                    Intent i = new Intent();
                    //merge後要將MainActivity換成主頁的Activity
                    i.setClass(thisactivity, MainActivity.class);  //跳到主頁
                    startActivity(i);
                    finish();
                    // 下面這行overridePendingTransition的用途是Activity間的動畫
                    // 第一個參數是下個activity的最初動畫
                    // 第二個參數是這個activity的最後動畫
                    // 若沒有動畫可以寫0
                    // 這方法只能放在finish()或startActivity(Intent)後
                    // 若搭配Thread，則需使用Handle傳訊息
                    start.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
                return false;
            }
        });

    }

    void slogandelay(){
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);  //睡3秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                m.what = MOVE_TO_HOME;  //指定此組訊息編號給m這message
                myhandle.sendMessage(m);
            }
        });
    }

}
