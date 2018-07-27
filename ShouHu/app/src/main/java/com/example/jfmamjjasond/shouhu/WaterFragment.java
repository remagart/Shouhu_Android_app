package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
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
    String ShouHu_user_name;
    private String name ;
    Context thisactivity;
    private WDBAdapter wdbAdapter;
    private ImageView waterView;
    private String date;


    private int mposition;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.arg1) {
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

            return false;
        }
    });

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
        this.thisactivity = getContext();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        waterView = getView().findViewById(R.id.waterView);

        //連接資料庫
        wdbAdapter = new WDBAdapter(thisactivity);
        get_from_activity();//取得用name
        //取得今日日期
        final Calendar c =Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        date = year+"-"+month+"-"+dayOfMonth;
        //查詢資料
        Cursor cursor = wdbAdapter.querydatachecked(name,date,"true");
        setImageview(cursor);//顯使水杯現在水量

        listview = (ListView) getView().findViewById(R.id.listView1);

        listShow = new ArrayList<Boolean>();//
        list = new ArrayList<String>();
        list.add("<500cc");
        list.add("501~1000cc");
        list.add("1001~1500cc");
        list.add("1501~2000cc");
        list.add("2001~2500cc");
        list.add(">2500cc");

        listAdapter = new  WaterListitemAdapter(getActivity(),list,listShow,name,handler);
        listview.setAdapter(listAdapter);
        //點圖片也會轉變至現在水量
        waterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = wdbAdapter.querydatachecked(name,date,"true");
                setImageview(cursor);
            }
        });

    }
    void get_from_activity(){
        Bundle mybundle = getArguments();
        ShouHu_user_name = mybundle.getString("user_name");
        name = ShouHu_user_name;
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





}
