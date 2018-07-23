package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

public class BMI_information extends android.support.v4.app.Fragment {

    Context thisactivity;
    Button btn_send;
    EditText edit_name,edit_pwd,edit_mail,edit_height,edit_weight;
    String name,pwd,mail;
    double height,weight;
    RadioGroup radioGroup_gender;  //因為需對整個group做事件處理
    RadioButton radiobtn_male,radiobtn_female;
    boolean isMale = true;  //true是男性,false是女性,layout中預設是男性

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bmi_information, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        thisactivity = getContext();  //此時thisactivity就是這個activity的context
        init();//初始化
        // 單選按鈕的事件處理
        radioGroup_gender.setOnCheckedChangeListener(mycheckevent);
        // 按鈕的事件處理
        btn_send.setOnClickListener(myclickevent);
        /* 程式寫到這裡，只要按按鈕後，所有的值都已經存成我們的變數了 */

    }
    void init(){
        btn_send = (Button)getView().findViewById(R.id.bmiinfoxml_btn_send);
        edit_name = (EditText)getView().findViewById(R.id.bmiinfoxml_edit_name);
        edit_pwd = (EditText)getView().findViewById(R.id.bmiinfoxml_edit_password);
        edit_mail = (EditText)getView().findViewById(R.id.bmiinfoxml_edit_mail);
        edit_height = (EditText)getView().findViewById(R.id.bmiinfoxml_edit_height);
        edit_weight = (EditText)getView().findViewById(R.id.bmiinfoxml_edit_weight);
        radioGroup_gender = (RadioGroup)getView().findViewById(R.id.bmiinfoxml_radiogroup_gender);
        radiobtn_male = (RadioButton)getView().findViewById(R.id.bmiinfoxml_radio_male);
        radiobtn_female = (RadioButton)getView().findViewById(R.id.bmiinfoxml_radio_female);
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
                    try {
                        height = Double.valueOf(edit_height.getText().toString());
                        weight = Double.valueOf(edit_weight.getText().toString());
                    }catch (NumberFormatException e){
                        height = 0;
                        weight = 0;
                    }
                    if(check_must(height,weight)){
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
        i.putExtra("user_height",String.valueOf(height));
        i.putExtra("user_weight",String.valueOf(weight));
        i.putExtra("user_name",name);
        i.setClass(thisactivity,BMI_result.class);
        startActivity(i);
    }

    boolean check_must(double h,double w){
        if(h != 0 && w != 0){
            if(h >= 50 && h <= 250 && w >= 1 && w <= 150){
                return true;
            }
        }
        return false;
    }

}
