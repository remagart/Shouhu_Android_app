package com.example.jfmamjjasond.shouhu;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener{
    private BottomNavigationView navigation;
    private ViewPager viewPager;

    private Homepage homepage = new Homepage();
    private Fragment2 fragment2 = new Fragment2();
    private Fragment3 fragment3 = new Fragment3();
    private Fragment4 fragment4 = new Fragment4();
    private  TimerActivity timerActivity = new TimerActivity();

    private TextView tvtitle; //宣告ToorBar的標題Textview



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得自訂Layout_bartitle的TwxtVeiw物件，設定ToolBar的標題
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.bartitle,null);
        tvtitle =(TextView)view.findViewById(R.id.tvtitle);

        //設定自訂ToolBar樣式
        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); // 取得ActionBar物件
        actionBar.setDisplayShowTitleEnabled(false); //隱藏ToolBar左上標題
        actionBar.setLogo(R.mipmap.icon_96); //設定左上Icon
        actionBar.setDisplayUseLogoEnabled(true);//顯示LOGO(icon)
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setCustomView(view); // 設置自訂layout(view)來顯示中間標題
        actionBar.setDisplayShowCustomEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //添加viewPager事件監聽
        viewPager.addOnPageChangeListener(this);

        //宣告首頁下方按鈕區物件，和設定監聽事件
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode( navigation ); //使用自訂BottomNavigationViewHelper類別中的方法去除navigation動畫

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return homepage;
                    case 1:
                        return fragment2;
                    case 2:
                        return fragment3;
                    case 3:
                        return fragment4;
                    case 4:
                        return timerActivity;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
    }
    //首頁下方按鈕區監聽處理事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //設定Toolbar顯示
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder; //Dialog Builder
        Dialog dialogSign; //登入用dialog
        switch (item.getItemId()){
            case R.id.signin: //登入事件
                //inflate目的是把自己設計xml的Layout轉成View，作用類似於findViewById，它用於一個沒有被載入或者想要動態
                //對於一個沒有被載入或者想要動態載入的界面，都需要使用LayoutInflater.inflate()來載入
                LayoutInflater inflaterIn = LayoutInflater.from(MainActivity.this);
                final View viewIn = inflaterIn.inflate(R.layout.dialog_signin,null);
                builder = new AlertDialog.Builder(this);
                builder.setTitle("登入")
                        .setView(viewIn)
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText etid_sign = (EditText) viewIn.findViewById(R.id.etid_sign);
                                EditText etpass_sign = (EditText) viewIn.findViewById(R.id.etpass_sign);

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialogSign= builder.create();
                dialogSign.show();
                break;
            case R.id.use:

                break;
            case R.id.set:

                break;
            case R.id.report:

                break;
            case R.id.version:

                break;
            case R.id.signout: //登出事件
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("登出")
                        .setMessage("確定要登出嗎?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //頁面滑動的时候，改變BottomNavigationView的Item高亮
        navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
