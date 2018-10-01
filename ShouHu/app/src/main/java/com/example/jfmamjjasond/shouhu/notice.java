package com.example.jfmamjjasond.shouhu;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class notice extends BroadcastReceiver {


    private final String CHANNELID = "sleep_notice";
    String ShouHu_user_name;
    @Override
    public void onReceive(Context c, Intent intent) {
        sleep fragment_sleep = new sleep();
        Bundle mybundle = new Bundle();
        Bitmap myLargePic = BitmapFactory.decodeResource(c.getResources(),R.mipmap.sleep64);

        //這太厲害了!!這樣就可以使用Activity中的方法
        //以下取得使用者暱稱
        //Intent intent_rec = ((Activity)c).getIntent();
        Bundle bundle_rec = intent.getExtras();
        if(bundle_rec.getString("user_name") != null){
            ShouHu_user_name = intent.getStringExtra("user_name");
        }

        Intent i = new Intent(c,MainActivity.class);
        mybundle.putString("source","from_sleep");
        mybundle.putString("user_name",ShouHu_user_name);
        i.putExtras(mybundle);

        PendingIntent mypendingIntent = PendingIntent.getActivity(c,
                0,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mynotice = new NotificationCompat.Builder(c,CHANNELID)
                .setSmallIcon(R.mipmap.shou_smallicon)
                .setLargeIcon(myLargePic)
                .setTicker("你該睡囉")
                .setContentTitle("睡眠提醒")
                .setContentText("你該睡囉~")
                .setContentInfo("Sleep")
                .setColor(Color.parseColor("#FFB13D"))
                .setContentIntent(mypendingIntent)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        NotificationManager mymaganer = (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
        mymaganer.notify(1,mynotice);
    }
}
