package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WDBHelper extends SQLiteOpenHelper {
    public WDBHelper(Context context) {
        super(context, "water_list", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS water_list "+
                "(_id INTEGER PRIMARY KEY autoincrement ,name,date,position,checked)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS water_list");
        onCreate(db);
    }
}
