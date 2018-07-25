package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class BMI_result extends android.support.v4.app.Fragment {

    Context thisactivity;
    AlertDialog dialog_window;
    TextView txt_bmiresult,txt_suggestion,txt_target,txt_name;
    ImageButton imgbtn_stickman,imgbtn_check;
    double weight,height,BMI,renew_weight;
    String name;
    final double standard_bmi_max = 23.9;
    final double standard_bmi_min = 18.5;
    int image_count = 0;
    //為了dialog自製layout
    LayoutInflater inflater_renew_weight;
    //做個view存實體化後的畫面
    View view_renew_weight;
    EditText edit_renew_weight;

    //使用者的名字
    String ShouHu_user_name;
    //資料庫的橋接器
    ShouHou_DBAdapter myadapter;
    //取得欲搜尋姓名的cursor
    Cursor mycursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bmi_result, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        thisactivity = getContext();

        //初始化UI和java
        init();
        //初始化資料庫橋接器和建立資料庫連接
        myadapter = new ShouHou_DBAdapter(thisactivity);
        //需要知道是哪位使用者
        get_from_activity();

        //主要BMI程式
        displayBMI();

        //按鍵事件處理，為了顯示dialog
        imgbtn_stickman.setOnClickListener(myClickevent);
        txt_bmiresult.setOnClickListener(myClickevent);
        txt_suggestion.setOnClickListener(myClickevent);
        txt_target.setOnClickListener(myClickevent);

    }
    void init(){
        txt_bmiresult = (TextView)getView().findViewById(R.id.bmiresultxml_txt_result);
        txt_suggestion = (TextView)getView().findViewById(R.id.bmiresultxml_txt_suggestion);
        txt_target = (TextView)getView().findViewById(R.id.bmiresultxml_txt_target);
        txt_name = (TextView)getView().findViewById(R.id.bmiresultxml_txt_name);
        imgbtn_stickman = (ImageButton)getView().findViewById(R.id.bmiresultxml_imgbtn_stickman);
        //設置的MaxWidth和MaxHeight能夠生效需滿足以下2點
        //setAdjustViewBounds(true)要設成true，API文件有特別說
        //要將imageview的xml寬高設成wrap_content
        imgbtn_stickman.setAdjustViewBounds(true);
        imgbtn_stickman.setMaxWidth(400);
        imgbtn_stickman.setMaxHeight(400);

    }
    void displayBMI(){
        String display_name="暱稱：";
        name = ShouHu_user_name;
        //將要查詢的資料存成cursor
        mycursor = myadapter.querybyname_from_user_table(name);
        //判斷有無資料
        if (mycursor.getCount() != 0){
            //資料庫欄位: _id | user_name | Sleep_Time | Wake_Time | height | weight
            height = mycursor.getDouble(4);
            weight = mycursor.getDouble(5);
            //顯示暱稱
            display_name = display_name + name;
            txt_name.setText(display_name);
            //計算BMI
            BMI = calculateBMI(height,weight);
            txt_bmiresult.setText(String.valueOf(BMI));
            //守護建議
            suggestion(BMI);
            //目標體重
            targetweight(height);
        }
    }

    //計算BMI，使用精度計算
    double calculateBMI(double h,double w){
        h = h / 100;
        BigDecimal temph = new BigDecimal(String.valueOf(h));
        BigDecimal tempw = new BigDecimal(String.valueOf(w));

        temph = temph.multiply(temph);
        tempw = tempw.divide(temph,2,BigDecimal.ROUND_DOWN);

        return tempw.doubleValue();
    }
    //守護建議
    void suggestion(double bmi_value){
        if(bmi_value < 18.5){
            txt_suggestion.setText("過瘦可能有營養不良、骨質疏鬆、猝死等健康問題!");
        }
        else if(bmi_value >= 18.5 && bmi_value < 24){
            txt_suggestion.setText("你屬於正常體位!請你繼續保持！");
        }
        else if(bmi_value >= 24 && bmi_value < 27){
            txt_suggestion.setText("有點過重喔！您得控制一下飲食了，請加油！");
        }
        else if(bmi_value >= 27){
            txt_suggestion.setText("肥胖是糖尿病,心血管疾病,惡性腫瘤等慢性病的風險因素!");
        }
    }
    //目標體重
    void targetweight(double h){
        String display_kg = "公斤";
        double temp = 0;
        h = h / 100;
        BigDecimal temp_bmi;
        BigDecimal temp_h = new BigDecimal(String.valueOf(h));
        if(BMI > standard_bmi_max){
            temp_bmi = new BigDecimal(String.valueOf(standard_bmi_max));
            temp_h = temp_h.multiply(temp_h);
            temp = (temp_bmi.multiply(temp_h)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
            display_kg = String.valueOf(temp) + display_kg;
            txt_target.setText(display_kg);
        }
        else if(BMI < standard_bmi_min){
            temp_bmi = new BigDecimal(String.valueOf(standard_bmi_min));
            temp_h = temp_h.multiply(temp_h);
            temp = (temp_bmi.multiply(temp_h)).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
            display_kg = String.valueOf(temp) + display_kg;
            txt_target.setText(display_kg);
        }
        else{
            txt_target.setText("你已達成目標了!!");
        }
    }

    //按鍵事件處理
    View.OnClickListener myClickevent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.bmiresultxml_imgbtn_stickman:
                    if(image_count == 0){
                        imgbtn_stickman.setImageResource(R.mipmap.fat_onetime2);
                        Toast.makeText(thisactivity, "很好喔!你需要動起來!", Toast.LENGTH_SHORT).show();
                        image_count++;
                    }
                    else if(image_count == 1){
                        imgbtn_stickman.setImageResource(R.mipmap.fat_twotime2);
                        Toast.makeText(thisactivity, "你做得很好!!持續加油!!", Toast.LENGTH_SHORT).show();
                        image_count++;
                    }
                    else if(image_count == 2){
                        imgbtn_stickman.setImageResource(R.mipmap.fat_threetime2);
                        Toast.makeText(thisactivity, "最後了!!再動一次!!", Toast.LENGTH_SHORT).show();
                        image_count++;
                    }
                    else if(image_count > 2){
                        pop_out_dialog();
                        image_count = 0;
                    }
                    break;
                //以下3個觸發都是要打開dialog
                case R.id.bmiresultxml_txt_result:
                case R.id.bmiresultxml_txt_suggestion:
                case R.id.bmiresultxml_txt_target:
                    pop_out_dialog();
                    break;
                case R.id.renew_weightxml_imgbtn_check:
                    try{
                        renew_weight = Double.valueOf(edit_renew_weight.getText().toString());
                    }catch (NumberFormatException e){
                        renew_weight = 0;
                        Toast.makeText(thisactivity, "你需要輸入喔~", Toast.LENGTH_SHORT).show();
                    }
                    if(renew_weight  >= 1 && renew_weight <= 300) {
                        myadapter.modify_user_weight(name,renew_weight);
                        dialog_window.dismiss();
                        displayBMI();

                    }else {
                        Toast.makeText(thisactivity, "要輸入正常數值喔~", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
            }

        }
    };

    //對話框的方法
    void pop_out_dialog(){
        inflater_renew_weight = getLayoutInflater();
        view_renew_weight = inflater_renew_weight.inflate(R.layout.bmi_result_renew_weight,null);

        imgbtn_check = (ImageButton)view_renew_weight.findViewById(R.id.renew_weightxml_imgbtn_check);
        edit_renew_weight = (EditText)view_renew_weight.findViewById(R.id.renew_weightxml_edit_weight);

        //設定對話框中圖片長寬
        imgbtn_check.setAdjustViewBounds(true);
        imgbtn_check.setMaxWidth(500);
        imgbtn_check.setMaxHeight(500);

        dialog_window = new AlertDialog.Builder(thisactivity)
                .setView(view_renew_weight)
                .show();
        imgbtn_check.setOnClickListener(myClickevent);
    }

    //從MainActivity取得使用者名稱
    void get_from_activity(){
        Bundle mybundle = getArguments();
        ShouHu_user_name = mybundle.getString("user_name");
    }


}
