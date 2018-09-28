package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShouHou_DBHelper extends SQLiteOpenHelper {

    final static String KEY_DB_NAME = "ShouHu_myDB";
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


    public ShouHou_DBHelper(Context c){
        super(c,KEY_DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //共有id,暱稱,昨晚睡覺時間,今早睡覺時間,身高,體重六個欄位
        String sql = "CREATE TABLE IF NOT EXISTS "
                + KEY_USER_TABLE_NAME
                + " ( "
                + KEY_ID + " INTEGER, "
                + KEY_name + " VARCHAR(20) PRIMARY KEY NOT NULL, "
                + KEY_SLEEP_TIME + " VARCHAR(5), "
                + KEY_WAKE_TIME + " VARCHAR(5), "
                + KEY_HEIGHT + " REAL, "
                + KEY_WEIGHT + " REAL "
                + " ) ";
        String sql2 = "CREATE TABLE IF NOT EXISTS "
                + KEY_SLEEP_RECORD_TABLE
                + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_name + " VARCHAR(20), "
                + KEY_DATE + " VARCHAR(10), "
                + KEY_WAKE_TIME + " VARCHAR(5), "
                + KEY_YESTERDAY_SLEEP + " VARCHAR(5), "
                + KEY_DURING + " VARCHAR(10) "
                + " ) ";

        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 只有在更新DB版本會用到
        String sql = "DROP TABLE IF EXISTS " + KEY_USER_TABLE_NAME;
        String sql2 = "DROP TABLE IF EXISTS " + KEY_SLEEP_RECORD_TABLE;
        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);
    }
}
