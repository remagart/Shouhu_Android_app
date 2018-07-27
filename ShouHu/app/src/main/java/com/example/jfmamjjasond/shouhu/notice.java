package com.example.jfmamjjasond.shouhu;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.jfmamjjasond.shouhu.R;

public class notice {


    private final String CHANNELID = "sleep_notice";
    public notice(Context c,String ShouHu_user_name) {

        sleep fragment_sleep = new sleep();
        Bundle mybundle = new Bundle();

        Intent i = new Intent(c,MainActivity.class);
        mybundle.putString("type","sleep");
        mybundle.putString("user_name",ShouHu_user_name);
        i.putExtras(mybundle);

        PendingIntent mypendingIntent = PendingIntent.getActivity(c,
                                                                0,
                                                                i,
                                                                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mynotice = new NotificationCompat.Builder(c,CHANNELID)
                .setSmallIcon(R.mipmap.icon)
                .setTicker("aaa")
                .setContentTitle("睡眠提醒")
                .setContentText("你該睡囉~")
                .setContentInfo("Sleep")
                .setContentIntent(mypendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        NotificationManager mymaganer = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        mymaganer.notify(1,mynotice);
    }
}
