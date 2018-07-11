package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class BMI_information extends AppCompatActivity {

    Context thisactivity;
    Button btn_send;
    EditText edit_name,edit_pwd,edit_mail,edit_height,edit_weight;
    String name,pwd,mail,height,weight;
    RadioGroup radioGroup_gender;  //因為需對整個group做事件處理
    RadioButton radiobtn_male,radiobtn_female;
    boolean isMale = true;  //true是男性,false是女性,layout中預設是男性


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_information);
        thisactivity = this;  //此時thisactivity就是這個activity的context
        init();//初始化
        // 單選按鈕的事件處理
        radioGroup_gender.setOnCheckedChangeListener(mycheckevent);
        // 按鈕的事件處理
        btn_send.setOnClickListener(myclickevent);
        /* 程式寫到這裡，只要按按鈕後，所有的值都已經存成我們的變數了 */

    }
    void init(){
        btn_send = (Button)findViewById(R.id.bmiinfoxml_btn_send);
        edit_name = (EditText)findViewById(R.id.bmiinfoxml_edit_name);
        edit_pwd = (EditText)findViewById(R.id.bmiinfoxml_edit_password);
        edit_mail = (EditText)findViewById(R.id.bmiinfoxml_edit_mail);
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
                    name = edit_name.getText().toString();
                    pwd = edit_pwd.getText().toString();
                    mail = edit_mail.getText().toString();
                    height = edit_height.getText().toString();
                    weight = edit_weight.getText().toString();
                    moveToNextPage();
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
        i.putExtra("user_height",height);
        i.putExtra("user_weight",weight);
        i.putExtra("user_name",name);
        i.setClass(thisactivity,BMI_result.class);
        startActivity(i);
    }

}
