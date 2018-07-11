package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BMI_result extends AppCompatActivity {

    Context thisactivity;
    Button btn_exercise;
    TextView txt_bmiresult,txt_suggestion,txt_target;
    ImageView img_fireman;
    double weight,height,BMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);
        thisactivity = this;
        init();
        displayBMI();


    }
    void init(){
        btn_exercise = (Button)findViewById(R.id.bmiresultxml_btn_exercise);
        txt_bmiresult = (TextView)findViewById(R.id.bmiresultxml_txt_result);
        txt_suggestion = (TextView)findViewById(R.id.bmiresultxml_txt_suggestion);
        txt_target = (TextView)findViewById(R.id.bmiresultxml_txt_target);
        img_fireman = (ImageView)findViewById(R.id.bmiresultxml_img_fireman);
    }
    void displayBMI(){
        Intent i = getIntent();
        if (i != null){
            height = Double.valueOf(i.getStringExtra("user_height"));
            weight = Double.valueOf(i.getStringExtra("user_weight"));
        }
    }
}
