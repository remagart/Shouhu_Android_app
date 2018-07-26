package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BMI_information extends AppCompatActivity {

    Context thisactivity;
    Button btn_send;
    TextView txt_title;
    EditText edit_name,edit_height,edit_weight;
    String ShouHu_name;
    double height,weight;
    RadioGroup radioGroup_gender;  //因為需對整個group做事件處理
    RadioButton radiobtn_male,radiobtn_female;
    boolean isMale = true;  //true是男性,false是女性,layout中預設是男性

    ShouHou_DBAdapter myadapter;
    Cursor mycursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_information);

        thisactivity = this;  //此時thisactivity就是這個activity的context
        init();//初始化
        modify_toolbar();
        myadapter = new ShouHou_DBAdapter(thisactivity);

        //確認使用者有無登出過
        check_user_login();

        // 單選按鈕的事件處理
        radioGroup_gender.setOnCheckedChangeListener(mycheckevent);
        // 按鈕的事件處理
        btn_send.setOnClickListener(myclickevent);
        /* 程式寫到這裡，只要按按鈕後，所有的值都已經存成我們的變數了 */

    }
    void init(){
        btn_send = (Button)findViewById(R.id.bmiinfoxml_btn_send);
        edit_name = (EditText)findViewById(R.id.bmiinfoxml_edit_name);
        edit_height = (EditText)findViewById(R.id.bmiinfoxml_edit_height);
        edit_weight = (EditText)findViewById(R.id.bmiinfoxml_edit_weight);
        radioGroup_gender = (RadioGroup)findViewById(R.id.bmiinfoxml_radiogroup_gender);
        radiobtn_male = (RadioButton)findViewById(R.id.bmiinfoxml_radio_male);
        radiobtn_female = (RadioButton)findViewById(R.id.bmiinfoxml_radio_female);
    }

    //這邊的用法是匿名類別的另一種用法，可以獨立出來
    View.OnClickListener myclickevent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                //按按鈕後，將值存成我們的變數
                case R.id.bmiinfoxml_btn_send:
                    ShouHu_name = edit_name.getText().toString();
                    try {
                        height = Double.valueOf(edit_height.getText().toString());
                        weight = Double.valueOf(edit_weight.getText().toString());
                    }catch (NumberFormatException e){
                        height = 0;
                        weight = 0;
                    }
                    if(check_must(height,weight)){
                        //使用者儲存登入
                        user_login();
                        moveToNextPage();
                    }
                    else{
                        Toast.makeText(thisactivity, "請填好全部欄位喔~", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:

            }
        }
    };
    //這邊的用法是匿名類別的另一種用法，可以獨立出來
    RadioGroup.OnCheckedChangeListener mycheckevent = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 判斷性別
            switch(checkedId){
                case R.id.bmiinfoxml_radio_male:
                    isMale = true;
                    break;
                case R.id.bmiinfoxml_radio_female:
                    isMale = false;
                    break;
                default:

            }
        }
    };

    void moveToNextPage(){
        Intent i = new Intent();
//        i.putExtra("user_height",String.valueOf(height));
//        i.putExtra("user_weight",String.valueOf(weight));
//        i.putExtra("user_name",name);

        i.putExtra("user_name",ShouHu_name);
        i.setClass(thisactivity,MainActivity.class);
        startActivity(i);
        finish();
    }

    boolean check_must(double h,double w){
        if(h != 0 && w != 0){
            if(h >= 50 && h <= 250 && w >= 1 && w <= 300){
                return true;
            }
        }
        return false;
    }
    void modify_toolbar(){
        //取得自訂Layout_bartitle的TwxtVeiw物件，設定ToolBar的標題
        LayoutInflater inflater = getLayoutInflater();
        View myview = inflater.inflate(R.layout.bartitle,null);
        txt_title = myview.findViewById(R.id.tvtitle);
        txt_title.setText("登入");

        //設定自訂ToolBar樣式
        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); // 取得ActionBar物件
        //assert actionBar != null, says that ActionBar is not null,
        // and so calling any method on this variable doesn't produce
        // the warning of NullPointerException.
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false); //隱藏ToolBar左上標題
        actionBar.setLogo(R.mipmap.icon_96); //設定左上Icon
        actionBar.setDisplayUseLogoEnabled(true);//顯示LOGO(icon)
        actionBar.setDisplayShowHomeEnabled(true);//顯示左上Icon
        actionBar.setCustomView(myview); // 設置自訂layout(view)來顯示中間標題
        actionBar.setDisplayShowCustomEnabled(true);
    }

    //確認使用者有無登出過
    void check_user_login(){
        //產生並讀取ShouHu_log_data.xml的暫存檔，並只供此app用
        SharedPreferences settings = getSharedPreferences("ShouHu_log_data",MODE_PRIVATE);
        //從暫存檔取得鍵值，若鍵值無資料則預設為空字串
        String temp = settings.getString("user_data","");
        //查詢資料庫有無該筆資料，有就直接moveToNextPage
        mycursor = myadapter.querybyname_from_user_table(temp);
        if(mycursor.getCount() != 0){
            ShouHu_name = temp;
            moveToNextPage();
        }
    }

    //使用者儲存登入
    void user_login(){
        //判斷該筆資料是註冊還是登入，註冊就新增一筆資料，登入就修改資料
        mycursor = myadapter.querybyname_from_user_table(ShouHu_name);
        if(mycursor.getCount() == 0){
            myadapter.add_user_for_bmi(ShouHu_name,height,weight);
        }
        else{
            myadapter.modify_user_for_bmi(ShouHu_name,height,weight);
        }
        //讀取ShouHu_log_data.xml的暫存檔，並寫入資料
        SharedPreferences settings = getSharedPreferences("ShouHu_log_data",MODE_PRIVATE);
        settings.edit()
                .putString("user_data",ShouHu_name)
                .apply();
        Toast.makeText(thisactivity, "登入成功!!", Toast.LENGTH_SHORT).show();

    }

}
