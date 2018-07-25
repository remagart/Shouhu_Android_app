package com.example.jfmamjjasond.shouhu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ScaleGestureDetector;

public class WDBAdapter {
    public static final String KEY_ID ="_id";
    public static final String KEY_NANE  = "name";
    public static final String KEY_DATE ="date";
    public static final String KEY_POSITION ="position";
    public static final String KEY_CHECKED ="checked";
    public static final String TABLE_NAME ="water_list2";
    private SQLiteDatabase mdb;
    private WDBHelper wdbHelper;
    private Context mCte;
    private Intent intent ;
    private ContentValues values ;

    public WDBAdapter(Context mCte) {
        this.mCte = mCte;
        open();
    }
    private void open(){   //開啟連結資料庫
        wdbHelper = new WDBHelper(mCte);
        mdb = wdbHelper.getWritableDatabase();
    }
    public void close() {
        if (wdbHelper != null)
            wdbHelper.close();
    }
    public Cursor listshow (String date){
        Cursor wcursor = mdb.query(TABLE_NAME,new String[]{KEY_ID,KEY_NANE,KEY_DATE,KEY_POSITION,KEY_CHECKED},KEY_DATE + " == " + "\"" + date + "\"",null,null,null,null);
        if(wcursor!=null)
            wcursor.moveToFirst();
        return wcursor;
    }
    //第一次選取才新增資料
    public long createdata (String name,String date, String position,String checked){
        try{
            values= new ContentValues();
            values.put(KEY_NANE,name);
            values.put(KEY_DATE,date);
            values.put(KEY_POSITION,position);
            values.put(KEY_CHECKED,checked);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mdb.insert(TABLE_NAME,null,values);
    }
    //更新選取框
   public long updatedata(int id,String name,String date, String position,String checked){
           values = new ContentValues();
           values.put(KEY_NANE,name);
           values.put(KEY_DATE,date);
           values.put(KEY_POSITION,position);
           values.put(KEY_CHECKED,checked);
           return mdb.update(TABLE_NAME,values, "_id="+id,null);
   }
    public  Cursor  querydata(String date,String position){ //使用日期和position查詢
        String sql ="SELECT * FROM "+TABLE_NAME+" WHERE date = ?  AND position = ?";
        Cursor wcursor =mdb.rawQuery(sql,new String[]{date,position});
        //Cursor wcursor = mdb.query(TABLE_NAME,new String[]{KEY_CHECKED},"date=? , position=?",new String[]{date,position},null,null,null,null);
        if(wcursor!=null) {
            wcursor.moveToFirst();
        }
        return wcursor;
    }
    public  Cursor  querydatapostion(String date) { //使用日期查詢
        String sql = "SELECT position FROM " + TABLE_NAME + " WHERE date = ? ";
        Cursor wcursor = mdb.rawQuery(sql, new String[]{date});
        //Cursor wcursor = mdb.query(TABLE_NAME,new String[]{KEY_CHECKED},"date=? , position=?",new String[]{date,position},null,null,null,null);
        if (wcursor != null) {
            wcursor.moveToFirst();
        }
        return wcursor;
    }
    public  Cursor  querydatachecked(String position) { //使用postion查詢
        String sql = "SELECT checked FROM " + TABLE_NAME + " WHERE position = ? ";
        Cursor wcursor = mdb.rawQuery(sql, new String[]{position});
        //Cursor wcursor = mdb.query(TABLE_NAME,new String[]{KEY_CHECKED},"date=? , position=?",new String[]{date,position},null,null,null,null);
        if (wcursor != null) {
            wcursor.moveToFirst();
        }
        return wcursor;
    }
}