package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class BMI_result extends AppCompatActivity {

    Context thisactivity;
    Button btn_exercise;
    TextView txt_bmiresult,txt_suggestion,txt_target,txt_name;
    ImageView img_fireman;
    double weight,height,BMI;
    String name;
    final double standard_bmi_max = 23.9;
    final double standard_bmi_min = 18.5;

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
        txt_name = (TextView)findViewById(R.id.bmiresultxml_txt_name);
    }
    void displayBMI(){
        Intent i = getIntent();
        if (i != null){
            height = Double.valueOf(i.getStringExtra("user_height"));
            weight = Double.valueOf(i.getStringExtra("user_weight"));
            name = i.getStringExtra("user_name");
            txt_name.setText(name);
            BMI = calculateBMI(height,weight);
            txt_bmiresult.setText(String.valueOf(BMI));
            suggestion(BMI);
            targetweight(height);
        }
    }

    double calculateBMI(double h,double w){
        h = h / 100;
        BigDecimal temph = new BigDecimal(String.valueOf(h));
        BigDecimal tempw = new BigDecimal(String.valueOf(w));

        temph = temph.multiply(temph);
        tempw = tempw.divide(temph,2,BigDecimal.ROUND_DOWN);

        return tempw.doubleValue();
    }
    void suggestion(double bmi_value){
        if(bmi_value < 18.5){
            txt_suggestion.setText("過輕");
        }
        else if(bmi_value >= 18.5 && bmi_value < 24){
            txt_suggestion.setText("正常");
        }
        else if(bmi_value >= 24 && bmi_value < 27){
            txt_suggestion.setText("過胖");
        }
        else if(bmi_value >= 27){
            txt_suggestion.setText("肥胖");
        }
    }
    void targetweight(double h){
        double temp = 0;
        h = h / 100;
        BigDecimal temp_bmi;
        BigDecimal temp_h = new BigDecimal(String.valueOf(h));
        if(BMI > standard_bmi_max){
            temp_bmi = new BigDecimal(String.valueOf(standard_bmi_max));
            temp_h = temp_h.multiply(temp_h);
            temp = (temp_bmi.multiply(temp_h)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
            txt_target.setText(String.valueOf(temp));
        }
        else if(BMI < standard_bmi_min){
            temp_bmi = new BigDecimal(String.valueOf(standard_bmi_min));
            temp_h = temp_h.multiply(temp_h);
            temp = (temp_bmi.multiply(temp_h)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
            txt_target.setText(String.valueOf(temp));
        }
        else{
            txt_target.setText("你已達成目標了!!\n繼續維持喔!!");
        }
    }

}
