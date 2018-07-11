package com.example.jfmamjjasond.shouhu;

import android.content.Context;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_information);
        thisactivity = this;
        init();
        btn_send.setOnClickListener(myclickevent);

    }
    void init(){
        btn_send = (Button)findViewById(R.id.bmiinfoxml_btn_send);
        edit_name = (EditText)findViewById(R.id.bmiinfoxml_edit_name);
        edit_pwd = (EditText)findViewById(R.id.bmiinfoxml_edit_password);
        edit_mail = (EditText)findViewById(R.id.bmiinfoxml_edit_mail);
        edit_height = (EditText)findViewById(R.id.bmiinfoxml_edit_height);
        edit_weight = (EditText)findViewById(R.id.bmiinfoxml_edit_weight);}

    View.OnClickListener myclickevent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bmiinfoxml_btn_send:
                    name = edit_name.getText().toString();
                    pwd = edit_pwd.getText().toString();
                    mail = edit_mail.getText().toString();
                    height = edit_height.getText().toString();
                    weight = edit_weight.getText().toString();

                    break;
                default:

            }
        }
    };


}
