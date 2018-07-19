package com.example.jfmamjjasond.shouhu;

import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends android.support.v4.app.Fragment {
    Button btnstart,btnstop,btnreset;
    TextView tvshow,timeTv;
    EditText ethot,etrest,etwork,ettimes;
    int hot,rest,work,times;
    int h =0 ,r =0 , w =0 ,t =0;
    private java.util.Timer timer = null , timer1 =null , timer2 =null ;
    private boolean one = true;
    private boolean start = true;
    private boolean first = true;

    public TimerTask timerTask = null;
    public TimerTask timerTask1 =null;
    public TimerTask timerTask2 =null;
/*
    private SoundPool soundPool; //SoundPool類別設定音效用
    private int[] soundID = new int[1];//宣告整數陣列放音效ID
*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //使用LayoutInflater物件，在此方法中產生畫面元件並回傳
        return inflater.inflate(R.layout.timer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        hot = Integer.valueOf(ethot.getText().toString());
        rest = Integer.valueOf(etrest.getText().toString());
        work =Integer.valueOf(etwork.getText().toString());
        times = Integer.valueOf(ettimes.getText().toString());
//        //等找到適合的聲音
//        //soundPool =new SoundPool(5, AudioManager.STREAM_MUSIC,0);//使用SoundPool類別設定音效，參數(最大串流音效數,串流類型,取樣值轉換(設定為0預設值即可))
//        //soundID[0]=soundPool.load(this,R.raw.glass,1); //將音效放在res/raw目錄 ，使用音效必須先LOAD來設定音效ID
        setListener();
 }
    //物件初始化
    public void findView(){
        tvshow = getView().findViewById(R.id.tvshow);
        timeTv = getView().findViewById(R.id.timeTv);

        btnstart = getView().findViewById(R.id.btnstart);//暫停與開始同一個按鈕
        btnreset = getView().findViewById(R.id.btnreset);

        ethot = getView().findViewById(R.id.ethot);
        etrest = getView().findViewById(R.id.etrest);
        etwork = getView().findViewById(R.id.etwork);
        ettimes = getView().findViewById(R.id.ettimes);

    }
    //按鈕監聽事件
    public void setListener() {
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hot > 0 && rest > 0 && work > 0 && times > 0)  {
                    if (start) { //判斷是否是是在開始狀態，不是為true，是為false，避免發生按下兩次開始發生BUG
                        if (first) {//數值初始化
                            hot = Integer.valueOf(ethot.getText().toString()); //讀取用戶設定在textview上的數值
                            rest = Integer.valueOf(etrest.getText().toString());
                            work = Integer.valueOf(etwork.getText().toString());
                            times = Integer.valueOf(ettimes.getText().toString());
                            h = hot + 1; //因一開始執行畫面數值顯示為 hot-1 ，希望顯示為hot所以加一補回
                            r = rest + 1;
                            w = work + 1;
                            t = times;
                            one = true; // 循環才執行暖身判斷
                        }
                        start = false;//按下開始後判定為開始狀態所以為false
                        btnstart.setText("暫停");
                        startTime(); //使用開始方法
                    } else if (start == false) {
                        btnstart.setText("開始");
                        stopTime();
                    }
                }else if(String.valueOf(hot).equals(null) || String.valueOf(rest).equals(null) || String.valueOf(work).equals(null)|| String.valueOf(times).equals(null)){
                    timeTv.setText("表單不能為空喔~");
                }
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTime();
            }
        });
    }
    //利用Hendler改變TextView秒數
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String time = String.valueOf(message.arg1);
            tvshow.setText(time);//TextView只能承载字符串类型的操作
            startTime();//重複執行startTime()方法
            return false;
        }
    });

    public void startTime(){

        //java.util.Timer定时器，實際上是個Thread
        timer = new Timer(); //宣告新的Timer
        timer1 = new Timer();
        timer2 = new Timer();
        //TimerTask實際上就是一個擁有run方法的類別，需要定時執行的代碼放到run方法內
        timerTask =new TimerTask() {
            @Override
            public void run() {
                h--; //暖身時間-1
                Message message= mHandler.obtainMessage();
                message. arg1= h;             //傳送暖身時間給handler    //arg1和arg2都是Message自帶的用來傳遞一些輕量級存儲int類型的數據，比如進度條的數據等。
                mHandler.sendMessage(message);    //傳送訊息
            }
        };
        timerTask1 =new TimerTask() {
            @Override
            public void run() {
                r--;
                Message message= mHandler.obtainMessage();
                message. arg1= r;
                mHandler.sendMessage(message);
            }
        };
        timerTask2 = new TimerTask(){
            @Override
            public void run() {
                w--;
                Message message= mHandler.obtainMessage();
                message. arg1= w;
                mHandler.sendMessage(message);
            }
        };
        //啟動Timer(以秒為單位的倒計時)
        if(one) {
            if(h==hot)timeTv.setText("暖身");//顯示暖身兩字
            timer.schedule(timerTask,1000); // timer 去執行TimerTask的run一秒後執行
        }
        if(h==0) { //當暖身秒數=0時執行
            /*f(r==(rest+1) )//當休息時間=rest+1時產生音效
                //soundPool.play(soundID[0], 1.0f, 1.0f, 0, 0, 1.0f); //播放音效
                play()的參數(逾放的音效ID,左聲道音量(0.0~1.0f),右聲道音量(0.0~1.0f),優先撥放順序,是否重複,取樣值) */
            timer.cancel();
            one=false;//暖身只跑一次，所以跑完一次後,one設為false
            timer1.schedule(timerTask1, 1000);
            if(r==rest)timeTv.setText("休息");//顯示休息兩字
            if(r==0) {
                timer1.cancel();
               /*if(w==(work+1) )//當動作時間=work+1時產生音效
                                    soundPool.play(soundID[0], 1.0f, 1.0f, 0, 0, 1.0f);*/
                timer2.schedule(timerTask2, 1000);
                if(w==work)timeTv.setText("訓練");//顯示訓練兩字
                if(w==0) {  //當動作時間結束
                    timer2.cancel();
                    r = rest+1;   //再將休息、動作時間重新計算
                    w = work+1;
                    t--; //跑完一次，次數就減一
                    if(t!=0) startTime();//次數還沒等於零就重新執行startTime
                    if(t==0) // 循環次數結束，想起最後一鈴聲
                    {
                       // soundPool.play(soundID[0], 1.0f, 1.0f, 0, 0, 1.0f);
                        start=true;
                        first=true;
                    }
                }
            }
        }
    }
    public void stopTime(){//暫停 暫時將timer取消，將判定是否為開始狀態的start設為ture代表現在不是開始狀態
        timer.cancel();
        timer1.cancel();
        timer2.cancel();
        start = true;
        first = false ;
    }
    public void restTime(){//重置 將所有timer取消，所有判斷設定回歸初值
        timer.cancel();
        timer1.cancel();
        timer2.cancel();
        tvshow.setText("0");
        first = true;
        one = true;
        start = true;
        timeTv.setText(" ");
        btnstart.setText("開始");
    }
    //當Fragment已不在畫面中時，呼叫此方法
    @Override
    public void onDestroy() {
        stopTime();
        super.onDestroy();
    }
    //當Fragment要被清除前，會執行此方法
    @Override
    public void onDestroyView() {
        restTime();
        super.onDestroyView();
    }
}