package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.Toast;

public class WaterListCheckAdapter extends CursorAdapter {
    LayoutInflater inflater;
    public WaterListCheckAdapter(Context context, Cursor c) {
        super(context, c,0);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Log.e("火侯","123456");
        Log.e("YOYO",c.getString(0));
        Log.e("YOYO2",c.getString(1));
        Log.e("YOYO3",c.getString(2));
        Log.e("YOYO4",c.getString(3));
        Log.e("YOYO5",c.getString(4));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.water_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.e("Magic","123456");
        CheckedTextView chkBshow = (CheckedTextView) view.findViewById(R.id.check1);
        chkBshow.setChecked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("checked"))));
        Log.e("安安", cursor.getString(cursor.getColumnIndexOrThrow("checked")));

    }
}
