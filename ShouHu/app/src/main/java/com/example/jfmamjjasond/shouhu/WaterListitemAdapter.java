package com.example.jfmamjjasond.shouhu;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.Calendar;
import java.util.List;

public class WaterListitemAdapter extends BaseAdapter {

    private Activity activity;
    private List<String> mList;
    private Cursor cursor;
    private WDBAdapter wdbAdapter;
    final Calendar c =Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH)+1;
    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    String date = year+"-"+month+"-"+dayOfMonth;

    private static LayoutInflater inflater = null;

    public WaterListitemAdapter(Activity a, List<String> list)
    {
        activity = a;
        mList = list;

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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        if(convertView==null)
        {
            vi = inflater.inflate(R.layout.water_list_item, null);
        }

        CheckedTextView chkBshow = (CheckedTextView) vi.findViewById(R.id.check1);

        chkBshow.setText(mList.get(position).toString());
        wdbAdapter = new WDBAdapter(activity);
        cursor = wdbAdapter.querydata(date, String.valueOf(position));
       try {


        if (cursor.getString(cursor.getColumnIndexOrThrow("checked")) != null) {
            Log.i("query", String.valueOf(position));
            chkBshow.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("checked"))));
        }
        }catch (Exception e ){
           e.printStackTrace();
       }
       chkBshow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //((CheckedTextView)view).toggle();


           }
       });
        return vi;
    }


}
