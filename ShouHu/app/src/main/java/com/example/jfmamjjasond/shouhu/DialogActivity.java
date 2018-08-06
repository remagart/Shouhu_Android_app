package com.example.jfmamjjasond.shouhu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DialogActivity extends AppCompatActivity {
    String ShouHu_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        //設定自訂ToolBar樣式
        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); // 取得ActionBar物件
        assert actionBar != null;//如果actionBar不為空則向下執行
        actionBar.setDisplayShowTitleEnabled(false); //隱藏ToolBar左上標題
        actionBar.setLogo(R.mipmap.icon_96); //設定左上Icon
        actionBar.setDisplayUseLogoEnabled(true);//顯示LOGO(icon)
        actionBar.setDisplayShowHomeEnabled(true);//顯示左上Icon
        actionBar.setDisplayShowCustomEnabled(true);
        //alarmDialog
        Intent iname = getIntent();
        ShouHu_user_name = iname.getStringExtra("user_name");
        Log.i("name",ShouHu_user_name);
        AlertDialog.Builder builder; //Dialog Builder
        Dialog dialog;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("守護飲水提醒").setMessage("您設定的飲水時間到囉~")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle mybundle = new Bundle();
                        Intent intent = new Intent(DialogActivity.this,MainActivity.class);
                        mybundle.putString("type","waterDia");
                        mybundle.putString("user_name",ShouHu_user_name);
                        intent.putExtras(mybundle);
                        startActivity(intent);
                        DialogActivity.this.finish();
                    }
                });
        dialog= builder.create();
        dialog.show();
    }

}
