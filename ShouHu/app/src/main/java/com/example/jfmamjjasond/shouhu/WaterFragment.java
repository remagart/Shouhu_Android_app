package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WaterFragment extends android.support.v4.app.Fragment {
    private Context mContext;
    private List<String> list;
    private ListView listview;
    private List<Boolean> listShow;    // 這個用來記錄哪幾個 item 是被打勾的
    private WaterListitemAdapter listAdapter;
    private WDBAdapter wdbAdapter;
    private WaterListCheckAdapter waterListCheckAdapter;
    private String name ="Lulu";
    private String date;
    Context thisactivity;
    CheckedTextView chkItem;

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
        listview = (ListView) getView().findViewById(R.id.listView1);

        final Calendar c =Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        date = year+"-"+month+"-"+dayOfMonth;
        thisactivity = getContext();
//        wdbAdapter = new WDBAdapter(thisactivity);
//        Cursor wcursorposition = wdbAdapter.querydatapostion(date);
//        if (wcursorposition.getCount()!=0){
//            Log.i("goin", String.valueOf(wcursorposition.getColumnCount()));
//            Cursor wcursorchecked = wdbAdapter.querydatachecked("0");
//            Log.i("goin2", wcursorchecked.getString(0));
//        }

//        Log.i("date=",date);
//        thisactivity = getContext();
        wdbAdapter = new WDBAdapter(thisactivity);
        Cursor cursor = wdbAdapter.listshow(date);
        Log.e("黑黑","123456");
        Log.e("YOYO",cursor.getString(0));
        Log.e("YOYO2",cursor.getString(1));
        Log.e("YOYO3",cursor.getString(2));
        Log.e("YOYO4",cursor.getString(3));
        Log.e("YOYO5",cursor.getString(4));
        waterListCheckAdapter =new WaterListCheckAdapter(getContext(),cursor);
        cursor = wdbAdapter.listshow(date);
        listview.setAdapter(waterListCheckAdapter);
        cursor.close();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                {
                    Log.i("gg", String.valueOf(listShow.get(0)));
                    chkItem = (CheckedTextView) v.findViewById(R.id.check1);

                    switch (position){
                        case 0:
                            if(!listShow.get(1) && !listShow.get(2) && !listShow.get(3)&& !listShow.get(4) && !listShow.get(5)) {
                                listShow.set(0,false);
                                chkItem.setChecked(!chkItem.isChecked());
                                listShow.set(position, chkItem.isChecked());//給點選的項目設置打勾
                                Toast.makeText(getActivity(), String.valueOf(listShow.get(0)), Toast.LENGTH_SHORT).show();
                                //查詢是否已經有新增資料
                                Cursor wcursor = wdbAdapter.querydata(date,String.valueOf(position));
                                if(wcursor.getCount()==0) {
                                    wdbAdapter.createdata(name,date,String.valueOf(position), String.valueOf(listShow.get(position)));//新增資料
                                }else {
                                    wdbAdapter.updatedata(Integer.parseInt(wcursor.getString(0)), name, date, String.valueOf(position), String.valueOf(listShow.get(position)));
                                }
                                Log.i("try", String.valueOf(listShow.get(0)));
                                if(wcursor.getCount()!=0) {
                                    Log.i("query", wcursor.getString(0)); //id=1 (getString(0))
                                }
                            }
                            break;
                        case 1:
                            if (listShow.get(0) && !listShow.get(2) && !listShow.get(3)&& !listShow.get(4) && !listShow.get(5) ){
                               chkItem.setChecked(!chkItem.isChecked());
                                listShow.set(position, chkItem.isChecked());//給點選的項目設置打勾
                                Toast.makeText(getActivity(), String.valueOf(listShow.get(1)), Toast.LENGTH_SHORT).show();
                                Cursor wcursor = wdbAdapter.querydata(date,String.valueOf(position));
                                if(wcursor.getCount()==0) {
                                    wdbAdapter.createdata(name,date,String.valueOf(position), String.valueOf(listShow.get(position)));//新增資料
                                }else {
                                    wdbAdapter.updatedata(Integer.parseInt(wcursor.getString(0)), name, date, String.valueOf(position), String.valueOf(listShow.get(position)));
                                }
                                Log.i("try", String.valueOf(listShow.get(0)));
                                if(wcursor.getCount()!=0) {
                                    Log.i("query", wcursor.getString(0)); //id=1 (getString(0))
                                }
                            }
                            break;
                        case 2:
                            if (listShow.get(0) &&listShow.get(1) &&!listShow.get(3)&& !listShow.get(4) && !listShow.get(5) ) {
                                chkItem.setChecked(!chkItem.isChecked());
                                listShow.set(position, chkItem.isChecked());//給點選的項目設置打勾
                                Toast.makeText(getActivity(), String.valueOf(listShow.get(2)), Toast.LENGTH_SHORT).show();
                                Cursor wcursor = wdbAdapter.querydata(date,String.valueOf(position));
                                if(wcursor.getCount()==0) {
                                    wdbAdapter.createdata(name,date,String.valueOf(position), String.valueOf(listShow.get(position)));//新增資料
                                }else {
                                    wdbAdapter.updatedata(Integer.parseInt(wcursor.getString(0)), name, date, String.valueOf(position), String.valueOf(listShow.get(position)));
                                }
                                Log.i("try", String.valueOf(listShow.get(0)));
                                if(wcursor.getCount()!=0) {
                                    Log.i("query", wcursor.getString(0)); //id=1 (getString(0))
                                }
                            }
                            break;
                        case 3:
                            if (listShow.get(0) &&listShow.get(1) && listShow.get(2) &&!listShow.get(4) && !listShow.get(5) ) {
                                chkItem.setChecked(!chkItem.isChecked());
                                listShow.set(position, chkItem.isChecked());//給點選的項目設置打勾
                                Toast.makeText(getActivity(), String.valueOf(listShow.get(3)), Toast.LENGTH_SHORT).show();
                                Cursor wcursor = wdbAdapter.querydata(date,String.valueOf(position));
                                if(wcursor.getCount()==0) {
                                    wdbAdapter.createdata(name,date,String.valueOf(position), String.valueOf(listShow.get(position)));//新增資料
                                }else {
                                    wdbAdapter.updatedata(Integer.parseInt(wcursor.getString(0)), name, date, String.valueOf(position), String.valueOf(listShow.get(position)));
                                }
                                Log.i("try", String.valueOf(listShow.get(0)));
                                if(wcursor.getCount()!=0) {
                                    Log.i("query", wcursor.getString(0)); //id=1 (getString(0))
                                }
                            }
                            break;
                        case 4:
                            if (listShow.get(0) &&listShow.get(1) && listShow.get(2) &&listShow.get(3) && !listShow.get(5) ) {
                                chkItem.setChecked(!chkItem.isChecked());
                                listShow.set(position, chkItem.isChecked());//給點選的項目設置打勾
                                Toast.makeText(getActivity(), String.valueOf(listShow.get(4)), Toast.LENGTH_SHORT).show();
                                Cursor wcursor = wdbAdapter.querydata(date,String.valueOf(position));
                                if(wcursor.getCount()==0) {
                                    wdbAdapter.createdata(name,date,String.valueOf(position), String.valueOf(listShow.get(position)));//新增資料
                                }else {
                                    wdbAdapter.updatedata(Integer.parseInt(wcursor.getString(0)), name, date, String.valueOf(position), String.valueOf(listShow.get(position)));
                                }
                                Log.i("try", String.valueOf(listShow.get(0)));
                                if(wcursor.getCount()!=0) {
                                    Log.i("query", wcursor.getString(0)); //id=1 (getString(0))
                                }
                            }
                            break;
                        case  5:
                            if(listShow.get(1) && listShow.get(2) && listShow.get(3)&& listShow.get(4) && listShow.get(0)) {
                                chkItem.setChecked(!chkItem.isChecked());
                                listShow.set(position, chkItem.isChecked());//給點選的項目設置打勾
                                Toast.makeText(getActivity(), String.valueOf(listShow.get(5)), Toast.LENGTH_SHORT).show();
                                Cursor wcursor = wdbAdapter.querydata(date,String.valueOf(position));
                                if(wcursor.getCount()==0) {
                                    wdbAdapter.createdata(name,date,String.valueOf(position), String.valueOf(listShow.get(position)));//新增資料
                                }else {
                                    wdbAdapter.updatedata(Integer.parseInt(wcursor.getString(0)), name, date, String.valueOf(position), String.valueOf(listShow.get(position)));
                                }
                                Log.i("try", String.valueOf(listShow.get(0)));
                                if(wcursor.getCount()!=0) {
                                    Log.i("query", wcursor.getString(0)); //id=1 (getString(0))
                                }
                            }
                            break;

                    }
                    Log.i("gg", String.valueOf(listShow.get(0)));



                    if(listShow.get(position)){

                        Log.i("try", String.valueOf(listShow.get(0)));
                    }
                }
            }
        );

        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();
        list.add("<500cc");
        listShow.add(true);
        list.add("501~1000cc");
        listShow.add(false);
        list.add("1001~1500cc");
        listShow.add(false);
        list.add("1501~2000cc");
        listShow.add(false);
        list.add("2001~2500cc");
        listShow.add(false);
        list.add(">2500cc");
        listShow.add(false);
        listAdapter = new  WaterListitemAdapter(getActivity(),list);
        listview.setAdapter(listAdapter);

    }

}
