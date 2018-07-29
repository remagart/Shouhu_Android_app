package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import java.util.Calendar;

/**
 * Created by juan on 2018/7/29.
 */

public class WaterFragment  extends android.support.v4.app.Fragment implements View.OnClickListener {
    private CheckedTextView cTV1 ,cTV2,cTV3,cTV4,cTV5,cTV6;
    private ImageView waterView;
    WDBAdapter wdbAdapter ;
    private String position;
    Cursor cursor ,wcursor0,wcursor1,wcursor2,wcursor3,wcursor4,wcursor5;
    private Context mContext;
    String ShouHu_user_name;
    private String name ;
    private String date;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //使用LayoutInflater inflater物件的inflater()方法，在此方法中取得介面布局檔並回傳給系統
        return inflater.inflate(R.layout.water, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment中獲取Context對象
        this.mContext = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        //連接資料庫
        wdbAdapter = new WDBAdapter(mContext);
        get_from_activity();//取得用戶name
        final Calendar c =Calendar.getInstance();
        //取得今日日期
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        date = year+"-"+month+"-"+dayOfMonth;
        //查詢checked為true的資料
        cursor= wdbAdapter.querydatachecked(name,date,"true");
        //顯示為true的打勾
        setchecked();
        //顯示為true的水杯
        setImageview(cursor);

    }
    public void findView(){
        cTV1 = getView().findViewById(R.id.checkedTextView1);
        cTV2 = getView().findViewById(R.id.checkedTextView2);
        cTV3 = getView().findViewById(R.id.checkedTextView3);
        cTV4 = getView().findViewById(R.id.checkedTextView4);
        cTV5 = getView().findViewById(R.id.checkedTextView5);
        cTV6 = getView().findViewById(R.id.checkedTextView6);
        cTV1.setOnClickListener(this);
        cTV2.setOnClickListener(this);
        cTV3.setOnClickListener(this);
        cTV4.setOnClickListener(this);
        cTV5.setOnClickListener(this);
        cTV6.setOnClickListener(this);
        waterView = getView().findViewById(R.id.waterView);
    }
    //每個checkedTextView的打勾事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkedTextView1:
                position="0";
                cTV1.setChecked(!cTV1.isChecked()); //選項一打勾或取消打勾，其他都取消打勾//單選的概念
                cTV2.setChecked(false);
                cTV3.setChecked(false);
                cTV4.setChecked(false);
                cTV5.setChecked(false);
                cTV6.setChecked(false);
                add_update();//新增或更新選項資料
                if(cTV1.isChecked()){//同步更新水杯圖
                    waterView.setImageResource(R.mipmap.w1);
                }else {
                    waterView.setImageResource(R.mipmap.w0);
                }
                break;
            case R.id.checkedTextView2:
                position="1";
                cTV1.setChecked(false);
                cTV2.setChecked(!cTV2.isChecked());
                cTV3.setChecked(false);
                cTV4.setChecked(false);
                cTV5.setChecked(false);
                cTV6.setChecked(false);
                add_update();
                if(cTV2.isChecked()){
                    waterView.setImageResource(R.mipmap.w2);
                }else {
                    waterView.setImageResource(R.mipmap.w0);
                }
                break;
            case R.id.checkedTextView3:
                position="2";
                cTV1.setChecked(false);
                cTV2.setChecked(false);
                cTV3.setChecked(!cTV3.isChecked());
                cTV4.setChecked(false);
                cTV5.setChecked(false);
                cTV6.setChecked(false);
                add_update();
                if(cTV3.isChecked()){
                    waterView.setImageResource(R.mipmap.w3);
                }else {
                    waterView.setImageResource(R.mipmap.w0);
                }
                break;
            case R.id.checkedTextView4:
                position="3";
                cTV1.setChecked(false);
                cTV2.setChecked(false);
                cTV3.setChecked(false);
                cTV4.setChecked(!cTV4.isChecked());
                cTV5.setChecked(false);
                cTV6.setChecked(false);
                add_update();
                if(cTV4.isChecked()){
                    waterView.setImageResource(R.mipmap.w4);
                }else {
                    waterView.setImageResource(R.mipmap.w0);
                }
                break;
            case R.id.checkedTextView5:
                position="4";
                cTV1.setChecked(false);
                cTV2.setChecked(false);
                cTV3.setChecked(false);
                cTV4.setChecked(false);
                cTV5.setChecked(!cTV5.isChecked());
                cTV6.setChecked(false);
                add_update();
                if(cTV5.isChecked()){
                    waterView.setImageResource(R.mipmap.w5);
                }else {
                    waterView.setImageResource(R.mipmap.w0);
                }
                break;
            case R.id.checkedTextView6:
                position="5";
                cTV1.setChecked(false);
                cTV2.setChecked(false);
                cTV3.setChecked(false);
                cTV4.setChecked(false);
                cTV5.setChecked(false);
                cTV6.setChecked(!cTV6.isChecked());
                add_update();
                if(cTV6.isChecked()){
                    waterView.setImageResource(R.mipmap.w6);
                }else {
                    waterView.setImageResource(R.mipmap.w0);
                }
                break;
        }

    }
    //讀取資料後設定每個選項布林值
    public void setchecked(){
        if (cursor.getCount()!=0) {
            Cursor wcursor0 = wdbAdapter.querydata(name, date, String.valueOf(0));
            Cursor wcursor1 = wdbAdapter.querydata(name, date, String.valueOf(1));
            Cursor wcursor2 = wdbAdapter.querydata(name, date, String.valueOf(2));
            Cursor wcursor3 = wdbAdapter.querydata(name, date, String.valueOf(3));
            Cursor wcursor4 = wdbAdapter.querydata(name, date, String.valueOf(4));
            Cursor wcursor5 = wdbAdapter.querydata(name, date, String.valueOf(5));
            cTV1.setChecked(Boolean.parseBoolean(wcursor0.getString(wcursor0.getColumnIndexOrThrow("checked"))));
            cTV2.setChecked(Boolean.parseBoolean(wcursor1.getString(wcursor1.getColumnIndexOrThrow("checked"))));
            cTV3.setChecked(Boolean.parseBoolean(wcursor2.getString(wcursor2.getColumnIndexOrThrow("checked"))));
            cTV4.setChecked(Boolean.parseBoolean(wcursor3.getString(wcursor3.getColumnIndexOrThrow("checked"))));
            cTV5.setChecked(Boolean.parseBoolean(wcursor4.getString(wcursor4.getColumnIndexOrThrow("checked"))));
            cTV6.setChecked(Boolean.parseBoolean(wcursor5.getString(wcursor5.getColumnIndexOrThrow("checked"))));
        }
    }
    //新增或更新打勾資料
    public void add_update(){
        Cursor wcursor0 = wdbAdapter.querydata(name, date, String.valueOf(0));
        Cursor wcursor1 = wdbAdapter.querydata(name, date, String.valueOf(1));
        Cursor wcursor2 = wdbAdapter.querydata(name, date, String.valueOf(2));
        Cursor wcursor3 = wdbAdapter.querydata(name, date, String.valueOf(3));
        Cursor wcursor4 = wdbAdapter.querydata(name, date, String.valueOf(4));
        Cursor wcursor5 = wdbAdapter.querydata(name, date, String.valueOf(5));
        Log.i("wcursor0.getCount=", String.valueOf(wcursor0.getCount()));
        Log.i("wcursor1.getCount=", String.valueOf(wcursor1.getCount()));
        Log.i("wcursor2.getCount=", String.valueOf(wcursor2.getCount()));
        Log.i("wcursor3.getCount=", String.valueOf(wcursor3.getCount()));
        Log.i("wcursor4.getCount=", String.valueOf(wcursor4.getCount()));
        Log.i("wcursor5.getCount=", String.valueOf(wcursor5.getCount()));

        if(wcursor0.getCount()==0){
            wdbAdapter.createdata(name, date, "0", String.valueOf(cTV1.isChecked()));//新增資料
        }else {
            wdbAdapter.updatedata(Integer.parseInt(wcursor0.getString(0)), name, date, "0", String.valueOf(cTV1.isChecked()));
        }
        if(wcursor1.getCount()==0){
            wdbAdapter.createdata(name, date, "1", String.valueOf(cTV2.isChecked()));//新增資料
        }else {
            wdbAdapter.updatedata(Integer.parseInt(wcursor1.getString(0)), name, date, "1", String.valueOf(cTV2.isChecked()));
        }
        if(wcursor2.getCount()==0){
            wdbAdapter.createdata(name, date, "2", String.valueOf(cTV3.isChecked()));//新增資料
        }else {
            wdbAdapter.updatedata(Integer.parseInt(wcursor2.getString(0)), name, date, "2", String.valueOf(cTV3.isChecked()));
        }
        if(wcursor3.getCount()==0){
            wdbAdapter.createdata(name, date, "3", String.valueOf(cTV4.isChecked()));//新增資料
        }else {
            wdbAdapter.updatedata(Integer.parseInt(wcursor3.getString(0)), name, date, "3", String.valueOf(cTV4.isChecked()));
        }
        if(wcursor4.getCount()==0){
            wdbAdapter.createdata(name, date, "4", String.valueOf(cTV5.isChecked()));//新增資料
        }else {
            wdbAdapter.updatedata(Integer.parseInt(wcursor4.getString(0)), name, date, "4", String.valueOf(cTV5.isChecked()));
        }
        if(wcursor5.getCount()==0){
            wdbAdapter.createdata(name, date, "5", String.valueOf(cTV6.isChecked()));//新增資料
        }else {
            wdbAdapter.updatedata(Integer.parseInt(wcursor5.getString(0)), name, date, "5", String.valueOf(cTV6.isChecked()));
        }
    }
    public void setImageview(Cursor cursor){
        if(cursor.getCount()!=0){
            switch (Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("position")))){
                case 0:
                    waterView.setImageResource(R.mipmap.w1);
                    break;
                case 1:
                    waterView.setImageResource(R.mipmap.w2);
                    break;
                case 2:
                    waterView.setImageResource(R.mipmap.w3);
                    break;
                case 3:
                    waterView.setImageResource(R.mipmap.w4);
                    break;
                case 4:
                    waterView.setImageResource(R.mipmap.w5);
                    break;
                case 5:
                    waterView.setImageResource(R.mipmap.w6);
                    break;
                default:
                    waterView.setImageResource(R.mipmap.w0);
                    break;
            }
        }
    }
    void get_from_activity(){
        Bundle mybundle = getArguments();
        ShouHu_user_name = mybundle.getString("user_name");
        name = ShouHu_user_name;
    }
}

