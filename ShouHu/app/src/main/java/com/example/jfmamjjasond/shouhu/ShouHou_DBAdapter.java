package com.example.jfmamjjasond.shouhu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShouHou_DBAdapter {
    final static String KEY_USER_TABLE_NAME = "User_Table";
    final static String KEY_ID = "_id";
    final static String KEY_name = "user_name";
    //昨晚睡覺時間
    final static String KEY_SLEEP_TIME = "Sleep_Time";
    //今早起床時間
    final static String KEY_WAKE_TIME = "Wake_Time";
    final static String KEY_HEIGHT = "height";
    final static String KEY_WEIGHT = "weight";
    String setnull = null;

    Context thisContext;
    SQLiteDatabase mydb;
    ShouHou_DBHelper myDBHelper;
    ContentValues myValues;
    String[] column;

    //建構子
    ShouHou_DBAdapter(Context c){
        thisContext = c;
        open();
    }

    //打開資料庫
    void open(){
        myDBHelper = new ShouHou_DBHelper(thisContext);
        //取得寫入資料庫的權限
        mydb = myDBHelper.getWritableDatabase();
        column = new String[]{KEY_ID,KEY_name,KEY_SLEEP_TIME,KEY_WAKE_TIME,KEY_HEIGHT,KEY_WEIGHT};
    }
    //關閉資料庫
    void close(){
        if(myDBHelper != null){
            mydb.close();
        }
    }
    //新增至sleep table
    long add_to_sleep_table(String name,String sleep_time,String wake_time){
        myValues = new ContentValues();
        myValues.put(KEY_name,name);
        myValues.put(KEY_SLEEP_TIME,sleep_time);
        myValues.put(KEY_WAKE_TIME,wake_time);
        return mydb.insert(KEY_USER_TABLE_NAME,null,myValues);
    }
    //查詢sleep table的姓名
    Cursor querybyname_from_user_table(String name){
        Cursor mycursor = mydb.query(KEY_USER_TABLE_NAME,
                column,
                KEY_name + " == " + "\"" + name + "\"",
                null,
                null,
                null,
                null
        );

        if(mycursor != null){
            mycursor.moveToFirst();
        }

        return mycursor;
    }

    //修改sleep table
    long modify_sleep_table(String name,String sleep_time,String wake_time){
        myValues = new ContentValues();

        myValues.put(KEY_name,name);
        myValues.put(KEY_SLEEP_TIME,sleep_time);
        myValues.put(KEY_WAKE_TIME,wake_time);

        return mydb.update(KEY_USER_TABLE_NAME,myValues,
                KEY_name +" == "+"\""+name+"\"",null);
    }

    long add_user_for_bmi(String name,double h,double w){
        myValues = new ContentValues();
        myValues.put(KEY_name,name);
        myValues.put(KEY_SLEEP_TIME,setnull);
        myValues.put(KEY_WAKE_TIME,setnull);
        myValues.put(KEY_HEIGHT,h);
        myValues.put(KEY_WEIGHT,w);

        return mydb.insert(KEY_USER_TABLE_NAME,null,myValues);
    }

    long modify_user_weight(String name,double w){
        myValues = new ContentValues();
        myValues.put(KEY_WEIGHT,w);
        return mydb.update(KEY_USER_TABLE_NAME,myValues,KEY_name+" == "+"\""+name+"\"",null);
    }

    long modify_user_for_bmi(String name,double h,double w){
        myValues = new ContentValues();
        myValues.put(KEY_HEIGHT,h);
        myValues.put(KEY_WEIGHT,w);

        return mydb.update(KEY_USER_TABLE_NAME,myValues,
                KEY_name+" == "+"\""+name+"\"",null);
    }

    Cursor allcursor(){
        String[] columns = new String[]{KEY_ID,KEY_name,KEY_SLEEP_TIME,KEY_WAKE_TIME,KEY_HEIGHT,KEY_WEIGHT};
        Cursor mycursor = mydb.query(KEY_USER_TABLE_NAME,columns,null,null,null,null,null);
        if(mycursor != null){
            mycursor.moveToFirst();
        }
        return mycursor;
    }


}
