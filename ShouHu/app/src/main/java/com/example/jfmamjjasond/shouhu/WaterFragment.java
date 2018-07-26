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
    String ShouHu_user_name;
    private String name ="Lulu";
    Context thisactivity;
    private WDBAdapter wdbAdapter;

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
        wdbAdapter = new WDBAdapter(thisactivity);
        listview = (ListView) getView().findViewById(R.id.listView1);

        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();
        list.add("<500cc");
        list.add("501~1000cc");
        list.add("1001~1500cc");
        list.add("1501~2000cc");
        list.add("2001~2500cc");
        list.add(">2500cc");

        listAdapter = new  WaterListitemAdapter(getActivity(),list,listShow);
        listview.setAdapter(listAdapter);
    }
    void get_from_activity(){
        Bundle mybundle = getArguments();
        ShouHu_user_name = mybundle.getString("user_name");
    }
}
