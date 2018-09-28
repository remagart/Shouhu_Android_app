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

    /**************分隔線************/
    final static String KEY_SLEEP_RECORD_TABLE = "Sleep_Record_Table";
    final static String KEY_DATE = "Date";
    final static String KEY_YESTERDAY_SLEEP = "Yesterday_Sleep";
    final static String KEY_DURING = "During_Time";

    String setnull = null;

    Context thisContext;
    SQLiteDatabase mydb;
    ShouHou_DBHelper myDBHelper;
    ContentValues myValues;
    String[] column;
    String[] column_for_SleepRecord;

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
        column_for_SleepRecord = new String[]{KEY_ID,KEY_name,KEY_DATE,KEY_WAKE_TIME,KEY_YESTERDAY_SLEEP,KEY_DURING};
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
/***分隔線，以下為sleep record資料表的****/
    Cursor checkcursor(String name,String day){
        Cursor mycursor = mydb.query(KEY_SLEEP_RECORD_TABLE,
                                    column_for_SleepRecord,
                                    KEY_name + " == " + "\"" + name + "\"" + " AND "
                                            + KEY_DATE + " == " + "\"" + day + "\"",
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

    long add_sleep(String name,String day,String sleep_time){
        myValues = new ContentValues();
        myValues.put(KEY_name,name);
        myValues.put(KEY_DATE,day);
        myValues.put(KEY_YESTERDAY_SLEEP,sleep_time);
        return mydb.insert(KEY_SLEEP_RECORD_TABLE,null,myValues);
    }

    long modify_sleep(String name,String day,String sleep_time){
        myValues = new ContentValues();
        myValues.put(KEY_DATE,day);
        myValues.put(KEY_YESTERDAY_SLEEP,sleep_time);
        return mydb.update(KEY_SLEEP_RECORD_TABLE,myValues,"user_name=? AND Date=? ",new String[]{name,day});
    }

    long add_wake(String name,String day,String wake_time){
        myValues = new ContentValues();
        myValues.put(KEY_name,name);
        myValues.put(KEY_DATE,day);
        myValues.put(KEY_WAKE_TIME,wake_time);
        return mydb.insert(KEY_SLEEP_RECORD_TABLE,null,myValues);
    }

    long modify_wake(String name,String day,String wake_time){
        myValues = new ContentValues();
        myValues.put(KEY_DATE,day);
        myValues.put(KEY_WAKE_TIME,wake_time);
        return mydb.update(KEY_SLEEP_RECORD_TABLE,myValues,KEY_name + " =? AND "
                + KEY_DATE + " =?",new String[]{name,day});

    }

    long modify_during(String name,int id,int[] during_time){
        myValues = new ContentValues();
        myValues.put(KEY_DURING,
                String.valueOf(during_time[0])+" hr "+String.valueOf(during_time[1])+" min ");
        return mydb.update(KEY_SLEEP_RECORD_TABLE,
                          myValues,
                         KEY_name + " =? AND " + KEY_ID + " =? ",
                          new String[]{name,String.valueOf(id)}
                );
    }

    Cursor search_byID(int id){
        Cursor mycursor = mydb.query(KEY_SLEEP_RECORD_TABLE,
                                    column_for_SleepRecord,
                                    KEY_ID + " =? ",
                                    new String[]{String.valueOf(id)},
                                    null,
                                    null,
                                    null);
        if(mycursor != null){
            mycursor.moveToFirst();
        }

        return mycursor;
    }

    Cursor allcursor(String user_name){
        Cursor mycursor = mydb.query(KEY_SLEEP_RECORD_TABLE,
                                    column_for_SleepRecord,
                                    KEY_name + " =? ",
                                    new String[]{user_name},
                                    null,
                                    null,
                                    null);
        if(mycursor != null){
            mycursor.moveToFirst();
        }
        return mycursor;
    }


}
