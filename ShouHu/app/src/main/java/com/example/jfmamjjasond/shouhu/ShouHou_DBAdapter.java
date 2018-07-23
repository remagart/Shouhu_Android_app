package com.example.jfmamjjasond.shouhu;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShouHou_DBAdapter {
    final static String KEY_SLEEP_TABLE_NAME = "Sleep_Table";
    final static String KEY_ID = "_id";
    final static String KEY_name = "user_name";
    //昨晚睡覺時間
    final static String KEY_SLEEP_TIME = "Sleep_Time";
    //今早起床時間
    final static String KEY_WAKE_TIME = "Wake_Time";

    Context thisContext;
    SQLiteDatabase mydb;
    ShouHou_DBHelper myDBHelper;
    ContentValues myValues;
    String[] column;

    ShouHou_DBAdapter(Context c){
        thisContext = c;
        open();
    }
    void open(){
        myDBHelper = new ShouHou_DBHelper(thisContext);
        mydb = myDBHelper.getWritableDatabase();
        column = new String[]{KEY_ID,KEY_name,KEY_SLEEP_TIME,KEY_WAKE_TIME};
    }
    void close(){
        if(myDBHelper != null){
            mydb.close();
        }
    }

    long add(String name,String sleep_time,String wake_time){
        myValues = new ContentValues();
        myValues.put(KEY_name,name);
        myValues.put(KEY_SLEEP_TIME,sleep_time);
        myValues.put(KEY_WAKE_TIME,wake_time);
        return mydb.insert(KEY_SLEEP_TABLE_NAME,null,myValues);
    }



}
