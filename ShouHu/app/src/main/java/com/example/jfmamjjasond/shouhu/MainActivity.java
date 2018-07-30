package com.example.jfmamjjasond.shouhu;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener{ //實作ViewPaper 頁面轉換監聽事件

    private BottomNavigationView navigation; //宣告下方選單

    private ViewPager viewPager; //宣告ViewPager搭配Fragment
    //宣告五個Fragment物件(class)讓MainActivity找到Fragment
    private Homepage homepage = new Homepage();
    private BMI_result fragment_bmi = new BMI_result();
    private sleep fragment_sleep = new sleep();
    private WaterFragment waterFragment = new WaterFragment();
    private TimerFragment timerFragment = new TimerFragment();


    private TextView tvtitle; //宣告ToorBar的標題Textview
    MenuItem menuItem_name;
    String ShouHu_user_name;

    notice mynotice;
    Context thisactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisactivity = this;

        //在介面有一個 viewPager 用來控制Fragment
        viewPager = findViewById(R.id.viewPager);
        //傳名字給fragment
        send_to_fragment();



        //取得自訂Layout_bartitle的TwxtVeiw物件，設定ToolBar的標題
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.bartitle,null);
        tvtitle =view.findViewById(R.id.tvtitle);
/* -------------------------------------------------------------------------------------------------------------------------------*/
        //設定自訂ToolBar樣式
        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); // 取得ActionBar物件
        assert actionBar != null;//如果actionBar不為空則向下執行
        actionBar.setDisplayShowTitleEnabled(false); //隱藏ToolBar左上標題
        actionBar.setLogo(R.mipmap.icon_96); //設定左上Icon
        actionBar.setDisplayUseLogoEnabled(true);//顯示LOGO(icon)
        actionBar.setDisplayShowHomeEnabled(true);//顯示左上Icon
        actionBar.setCustomView(view); // 設置自訂layout(view)來顯示中間標題
        actionBar.setDisplayShowCustomEnabled(true);
/* -------------------------------------------------------------------------------------------------------------------------------*/
        //宣告首頁下方按鈕區物件，和設定監聽事件
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode( navigation ); //使用自訂BottomNavigationViewHelper類別中的方法去除navigation動畫
        page_transfer();
        ShouHu_notice();
/* -------------------------------------------------------------------------------------------------------------------------------*/

    }
    //首頁下方按鈕區監聽處理事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        //點擊BottomNavigationView的Item项，切换ViewPager页面
        //menu/navigation.xml裡加的android:orderInCategory属性就是下面item.getOrder()取的值
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };
    /* -------------------------------------------------------------------------------------------------------------------------------*/
    //Toolbar_Menu設定
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //設定Toolbar顯示
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        menuItem_name = menu.findItem(R.id.signin);
        menuItem_name.setTitle("暱稱: " + ShouHu_user_name);
        return super.onCreateOptionsMenu(menu);
    }
    //Toolbar_Menu設定監聽事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder; //Dialog Builder
        Dialog dialogSign; //登入用dialog
        switch (item.getItemId()){
            case R.id.signin: //登入事件

                break;
            case R.id.use: //使用說明

                break;
            case R.id.setsleep://設定睡眠提醒
                Toast.makeText(this,"setsleep",Toast.LENGTH_LONG).show();
                break;
            case R.id.report://報表

                break;
            case R.id.version://版本

                break;
            case R.id.signout: //登出事件
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("登出守護")
                        .setMessage("親愛的" + ShouHu_user_name +" ,您確定要登出守護嗎?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //將存取資料設成空值
                                SharedPreferences settings = getSharedPreferences("ShouHu_log_data",MODE_PRIVATE);
                                settings.edit()
                                        .putString("user_data","")
                                        .apply();
                                Intent intent_to_BMIinfo = new Intent();
                                intent_to_BMIinfo.setClass(MainActivity.this,BMI_information.class);
                                startActivity(intent_to_BMIinfo);
                                Toast.makeText(MainActivity.this, "已登出~~期待下次相遇~", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogSign= builder.create();
                dialogSign.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    /* -------------------------------------------------------------------------------------------------------------------------------*/
    //實作Fragment方法
    //必須實作的方法1,當頁面在滑動的時候會使用此方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    //必須實作的方法2
    @Override
    public void onPageSelected(int position) { //選擇時事件監聽
        navigation.getMenu().getItem(position).setChecked(true); //頁面滑動的时候，改變BottomNavigationView的Item高亮
        switch (position){//改變標題
            case 0:
                tvtitle.setText(R.string.title_home);
                break;
            case 1:
                tvtitle.setText(R.string.title_bmi);
                break;
            case 2:
                tvtitle.setText(R.string.title_sleep);
                break;
            case 3:
                tvtitle.setText(R.string.title_water);
                break;
            case 4:
                tvtitle.setText(R.string.title_timer);
                break;
        }
    }
    //必須實作的方法3, 此方法是在狀態改變的時候使用
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //傳名字給fragment
    void send_to_fragment(){
        Intent i = getIntent();
        ShouHu_user_name = i.getStringExtra("user_name");
        Bundle mybundle = new Bundle();
        mybundle.putString("user_name",ShouHu_user_name);
        fragment_bmi.setArguments(mybundle);
        fragment_sleep.setArguments(mybundle);
        homepage.setArguments(mybundle);
        waterFragment.setArguments(mybundle);
    }

    void ShouHu_notice(){
        mynotice = new notice(thisactivity,ShouHu_user_name);
        send_to_fragment();
        Intent i = getIntent();
        Bundle mybundle = i.getExtras();


        if(mybundle.getString("type") != null){
            Log.e("hihi", String.valueOf(mybundle.getString("type")));
            if(i.getStringExtra("type").equals("sleep")){
                viewPager.setCurrentItem(2);
            }
        }

    }

    void page_transfer(){
        //除了本頁外還可以容許到其他四頁還活著
        viewPager.setOffscreenPageLimit(4);
        // 添加viewPager事件監聽
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return homepage;
                    case 1:
                        return fragment_bmi;
                    case 2:
                        return fragment_sleep;
                    case 3:
                        return waterFragment;
                    case 4:
                        return timerFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
        //viewPager.setCurrentItem(3);
    }

}
