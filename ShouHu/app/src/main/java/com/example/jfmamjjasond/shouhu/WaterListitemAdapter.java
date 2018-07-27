package com.example.jfmamjjasond.shouhu;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.jfmamjjasond.shouhu.R.layout.water;

public class WaterListitemAdapter extends BaseAdapter {

    private Activity activity;
    private List<String> mList;
    private Cursor cursor;
    private WDBAdapter wdbAdapter;
    //取得今日日期
    final Calendar c =Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH)+1;
    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    String date = year+"-"+month+"-"+dayOfMonth;

    private String name ;
    private List<Boolean> listShow ;
    private static LayoutInflater inflater = null;
    private Handler handler;
    public static final String POSITION = "POSITION";
       
    public WaterListitemAdapter(Activity a, List<String> list,List<Boolean> listShow,String name,Handler handler)
    {
        activity = a;
        mList = list;
        this.listShow=listShow;
        this.name=name;
        this.handler=handler;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount()
    {
        return mList.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
             View vi = convertView;
        if(convertView==null)
        {

            vi = inflater.inflate(R.layout.water_list_item, null);

        }

        final CheckedTextView chkBshow = (CheckedTextView) vi.findViewById(R.id.check1);

        chkBshow.setText(mList.get(position).toString());
        wdbAdapter = new WDBAdapter(activity);
        cursor = wdbAdapter.querydata(name,date, String.valueOf(position));
       try {
        if (cursor.getString(cursor.getColumnIndexOrThrow("checked")) != null) {//有值才設定勾勾的布林值
            Log.i("query", String.valueOf(position));
            chkBshow.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("checked"))));
        }
        }catch (Exception e ){
           e.printStackTrace();
       }
       chkBshow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                   //讀取資料庫每個position的cursor值
                   Cursor wcursor0 = wdbAdapter.querydata(name,date, String.valueOf(0));
                   Cursor wcursor1 = wdbAdapter.querydata(name,date, String.valueOf(1));
                   Cursor wcursor2 = wdbAdapter.querydata(name,date, String.valueOf(2));
                   Cursor wcursor3 = wdbAdapter.querydata(name,date, String.valueOf(3));
                   Cursor wcursor4 = wdbAdapter.querydata(name,date, String.valueOf(4));
                   Cursor wcursor5 = wdbAdapter.querydata(name,date, String.valueOf(5));
                   //預設儲存布林初始值
                   listShow.add(0,false);
                   listShow.add(1,false);
                   listShow.add(2,false);
                   listShow.add(3,false);
                   listShow.add(4,false);
                   listShow.add(5,false);
                        try {//判斷今天是否有資料才將listShow加入布林值
                            if(wcursor0.getCount()!=0)listShow.add(0, Boolean.parseBoolean(wcursor0.getString(wcursor0.getColumnIndexOrThrow("checked"))));
                            if(wcursor1.getCount()!=0)listShow.add(1, Boolean.parseBoolean(wcursor1.getString(wcursor1.getColumnIndexOrThrow("checked"))));
                            if(wcursor2.getCount()!=0)listShow.add(2, Boolean.parseBoolean(wcursor2.getString(wcursor2.getColumnIndexOrThrow("checked"))));
                            if(wcursor3.getCount()!=0)listShow.add(3, Boolean.parseBoolean(wcursor3.getString(wcursor3.getColumnIndexOrThrow("checked"))));
                            if(wcursor4.getCount()!=0)listShow.add(4, Boolean.parseBoolean(wcursor4.getString(wcursor4.getColumnIndexOrThrow("checked"))));
                            if(wcursor5.getCount()!=0)listShow.add(5, Boolean.parseBoolean(wcursor5.getString(wcursor5.getColumnIndexOrThrow("checked"))));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

               switch (position){
                   case 0:
                       if(!listShow.get(1) && !listShow.get(2) && !listShow.get(3)&& !listShow.get(4) && !listShow.get(5)) {
                           chkBshow.setChecked(!chkBshow.isChecked());
                           listShow.set(position, chkBshow.isChecked());//給點選的項目設置打勾
                           if (wcursor0.getCount() == 0) {
                               wdbAdapter.createdata(name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));//新增資料
                           } else {
                               wdbAdapter.updatedata(Integer.parseInt(wcursor0.getString(0)), name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));
                           }
                             if(listShow.get(0)){
                                 Message message = new Message();
                                 message.arg1 = position;
                                 handler.sendMessage(message);
                           }else{
                                 Message message = new Message();
                                 message.arg1 = 100;
                                 handler.sendMessage(message);
                             }
                       }
                           break;
                   case 1:
                       if (listShow.get(0) && !listShow.get(2) && !listShow.get(3)&& !listShow.get(4) && !listShow.get(5) ) {
                           chkBshow.setChecked(!chkBshow.isChecked());
                           listShow.set(position, chkBshow.isChecked());//給點選的項目設置打勾
                           if (wcursor1.getCount() == 0) {
                               wdbAdapter.createdata(name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));//新增資料
                           } else {
                               wdbAdapter.updatedata(Integer.parseInt(wcursor1.getString(0)), name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));
                               }
                           }
                       if(listShow.get(1)){
                           Message message = new Message();
                           message.arg1 = position;
                           handler.sendMessage(message);
                       }else {
                               Message message = new Message();
                               message.arg1 = position-1;
                               handler.sendMessage(message);
                       }
                           break;
                   case 2:
                       if (listShow.get(0) &&listShow.get(1) &&!listShow.get(3)&& !listShow.get(4) && !listShow.get(5) ) {
                           chkBshow.setChecked(!chkBshow.isChecked());
                           listShow.set(position, chkBshow.isChecked());//給點選的項目設置打勾
                           if (wcursor2.getCount() == 0) {
                               wdbAdapter.createdata(name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));//新增資料
                           } else {
                               wdbAdapter.updatedata(Integer.parseInt(wcursor2.getString(0)), name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));
                           }
                           if(listShow.get(2)){
                               Message message = new Message();
                               message.arg1 = position;
                               handler.sendMessage(message);
                           }else {
                               Message message = new Message();
                               message.arg1 = position-1;
                               handler.sendMessage(message);
                           }
                       }
                       break;

                   case 3:
                       if (listShow.get(0) &&listShow.get(1) && listShow.get(2) &&!listShow.get(4) && !listShow.get(5) ) {
                           chkBshow.setChecked(!chkBshow.isChecked());
                           listShow.set(position, chkBshow.isChecked());//給點選的項目設置打勾
                           if (wcursor3.getCount() == 0) {
                               wdbAdapter.createdata(name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));//新增資料
                           } else {
                               wdbAdapter.updatedata(Integer.parseInt(wcursor3.getString(0)), name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));
                           }
                           if(listShow.get(3)){
                               Message message = new Message();
                               message.arg1 = position;
                               handler.sendMessage(message);
                           }else {
                               Message message = new Message();
                               message.arg1 = position-1;
                               handler.sendMessage(message);
                           }
                       }
                       break;
                   case 4:
                       if (listShow.get(0) &&listShow.get(1) && listShow.get(2) &&listShow.get(3) && !listShow.get(5) ) {
                           chkBshow.setChecked(!chkBshow.isChecked());
                           listShow.set(position, chkBshow.isChecked());//給點選的項目設置打勾
                           if (wcursor4.getCount() == 0) {
                               wdbAdapter.createdata(name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));//新增資料
                           } else {
                               wdbAdapter.updatedata(Integer.parseInt(wcursor4.getString(0)), name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));
                           }
                           if(listShow.get(4)){
                               Message message = new Message();
                               message.arg1 = position;
                               handler.sendMessage(message);
                           }else {
                               Message message = new Message();
                               message.arg1 = position-1;
                               handler.sendMessage(message);
                           }
                       }
                       break;
                   case 5:
                       if(listShow.get(1) && listShow.get(2) && listShow.get(3)&& listShow.get(4) && listShow.get(0)) {
                           chkBshow.setChecked(!chkBshow.isChecked());
                            listShow.set(position, chkBshow.isChecked());//給點選的項目設置打勾
                           if (wcursor5.getCount() == 0) {
                               wdbAdapter.createdata(name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));//新增資料

                           } else {
                               wdbAdapter.updatedata(Integer.parseInt(wcursor5.getString(0)), name, date, String.valueOf(position), String.valueOf(chkBshow.isChecked()));
                           }
                           if(listShow.get(5)){
                               Message message = new Message();
                               message.arg1 = position;
                               handler.sendMessage(message);
                           }else {
                               Message message = new Message();
                               message.arg1 = position-1;
                               handler.sendMessage(message);
                           }
                       }
                       break;
               }
               Log.i("listShow0=", String.valueOf(listShow.get(0)));
               Log.i("listShow1=", String.valueOf(listShow.get(1)));
               Log.i("listShow2=", String.valueOf(listShow.get(2)));
               Log.i("listShow3=", String.valueOf(listShow.get(3)));
               Log.i("listShow4=", String.valueOf(listShow.get(4)));
               Log.i("listShow5=", String.valueOf(listShow.get(5)));
               Log.i("position=", String.valueOf(chkBshow.isChecked()));

           }
       });
       // chkBshow.setOnClickListener(new OnItemChildClickListener(position));
        return vi;
    }

}