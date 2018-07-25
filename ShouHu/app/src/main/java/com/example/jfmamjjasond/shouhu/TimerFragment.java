package com.example.jfmamjjasond.shouhu;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
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

import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends android.support.v4.app.Fragment {
    private Context mContext;
    private Button btnstart,btnreset;
    private TextView tvshow,timeTv,tvhot2,tvrest2,tvwork2,tvtimes2;
    private EditText ethot,etrest,etwork,ettimes;
    int hot,rest,work,times;
    int h ,r , w ,t ;
    private Timer timer = null , timer1 =null , timer2 =null ; //分別管暖身、休息、動作的timer
    private boolean one = true; // 判斷第1次循環才啟動暖身計時
    private boolean start = true; // 判斷是否為開始狀態
    private boolean first = true; // 判斷是否為第一次按下開始按鈕

    public TimerTask timerTask = null;
    public TimerTask timerTask1 =null;
    public TimerTask timerTask2 =null;

    private SoundPool soundPool; //　SoundPool類別設定音效用
    private int[] soundID = new int[1];//宣告整數陣列用來放音效ID


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //使用LayoutInflater inflater物件的inflater()方法，在此方法中取得介面布局檔並回傳給系統
        return inflater.inflate(R.layout.timer, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fragment中獲取Context對象
        this.mContext = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findView();
        usesoundPool();//使用音效方法
        setListener(); // 事件監聽
 }
    //物件初始化，必須先呼叫getView方法取得程式畫面物件，然後才能呼叫它findViewById取得物件
    public void findView(){

        tvshow = getView().findViewById(R.id.tvshow);
        timeTv = getView().findViewById(R.id.timeTv);

        btnstart = getView().findViewById(R.id.btnstart);//暫停與開始同一個按鈕
        btnreset = getView().findViewById(R.id.btnreset);

        ethot = getView().findViewById(R.id.ethot);
        etrest = getView().findViewById(R.id.etrest);
        etwork = getView().findViewById(R.id.etwork);
        ettimes = getView().findViewById(R.id.ettimes);

        tvhot2 = getView().findViewById(R.id.tvhot2);
        tvrest2  = getView().findViewById(R.id.tvrest2);
        tvwork2 = getView().findViewById(R.id.tvwork2);
        tvtimes2 = getView().findViewById(R.id.tvtimes2);
    }
    public void usesoundPool(){
        if(Build.VERSION.SDK_INT > 21){//API>21時使用 SoundPool.Builder
            SoundPool.Builder builder = new SoundPool.Builder();
            //傳入音频数量
            builder.setMaxStreams(5);
            //AudioAttributes是一个封裝音频各各屬性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //設置音频流的合適的屬性，STREAM_SYSTEM(系統聲音)，AudioManager控制系統聲音的對象
           attrBuilder.setLegacyStreamType(AudioManager.STREAM_SYSTEM);
            //加載一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        }else {//API<21時
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);//使用SoundPool類別設定音效，參數(最大串流音效數,串流類型,取樣值轉換(設定為0預設值即可))
        }
        if(getActivity()!=null) soundID[0]=soundPool.load(getActivity(),R.raw.pianoc2,1); //將音效放在res/raw目錄 ，使用音效必須先LOAD來設定音效ID
    }
    //按鈕監聽事件
    public void setListener() {
        //開始與暫停按鈕監聽
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    hot = Integer.valueOf(ethot.getText().toString()); //讀取用戶設定在textview上的數值
                    rest = Integer.valueOf(etrest.getText().toString());
                    work = Integer.valueOf(etwork.getText().toString());
                    times = Integer.valueOf(ettimes.getText().toString());
                }catch(Exception e){ //預防用戶設定空值
                    hot=0;rest=0;work=0;times=0;
                }
                if(hot>0 && rest >0 && work>0 && times>0) { // 設定值要大於0才執行
                    if (start) { //判斷是否是是在開始狀態，不是為true，是為false，避免發生按下兩次開始發生BUG
                        if (first) {// timer傳送數值初始化
                            setVisibility();
                            h = hot + 1; //因一開始執行畫面數值顯示為 hot-1 ，希望顯示為hot所以加一補回
                            r = rest + 1;
                            w = work + 1;
                            t = times;
                            one = true; // 第一次循環才執行暖身判斷
                        }
                        start = false;//按下開始後判定為開始狀態所以為false
                        btnstart.setText("暫停");
                        startTime(); //使用開始方法
                    } else if (!start) {
                        btnstart.setText("開始");
                        stopTime();
                    }
                }else {
                    timeTv.setText("請輸入大於0的數值");
                }
            }
        });
        //重置按鈕監聽
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!first)restTime(); //執行過時間才能使用重設方法
                ethot.setText(R.string.timerhot);
                etrest.setText(R.string.timerrest);
                etwork.setText(R.string.timerwork);
                ettimes.setText(R.string.timertimes);
                timeTv.setText(" ");
            }
        });
    }
    //隱藏Eidtview顯示Textview
    public void setVisibility(){
        ethot.setVisibility(View.INVISIBLE);
        etrest.setVisibility(View.INVISIBLE);
        etwork.setVisibility(View.INVISIBLE);
        ettimes.setVisibility(View.INVISIBLE);

        tvhot2.setVisibility(View.VISIBLE);
        tvrest2.setVisibility(View.VISIBLE);
        tvwork2.setVisibility(View.VISIBLE);
        tvtimes2.setVisibility(View.VISIBLE);
        //Eidtview數據給Textview顯示
        tvhot2.setText(ethot.getText().toString());
        tvrest2.setText(etrest.getText().toString());
        tvwork2.setText(etwork.getText().toString());
        tvtimes2.setText(ettimes.getText().toString());
    }
    //回復隱藏設定
    public  void restsetVisibility(){
        ethot.setVisibility(View.VISIBLE);
        etrest.setVisibility(View.VISIBLE);
        etwork.setVisibility(View.VISIBLE);
        ettimes.setVisibility(View.VISIBLE);

        tvhot2.setVisibility(View.INVISIBLE);
        tvrest2.setVisibility(View.INVISIBLE);
        tvwork2.setVisibility(View.INVISIBLE);
        tvtimes2.setVisibility(View.INVISIBLE);
    }

    //利用Hendler改變TextView秒數
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String time = String.valueOf(message.arg1);
            tvshow.setText(time);//TextView只能承載字符串類型的操作
            startTime();//重複執行startTime()方法
            return false;
        }
    });
    //啟動計時器方法
    public void startTime(){
        //java.util.Timer定時器，實際上是個Thread
        timer = new Timer(); //宣告新的Timer
        timer1 = new Timer();
        timer2 = new Timer();
        //TimerTask實際上就是一個擁有run方法的類別，需要定時執行的代碼放到run方法內
        timerTask =new TimerTask() { //暖身用
            @Override
            public void run() {
                h--; //暖身時間-1
                Message message= mHandler.obtainMessage();//handler.obtainMessage() 的作用是從當前的Handler中獲取指定的Message以供再次使用，避免在監測線程中如果不斷的new Message() 可能出現錯誤
                message. arg1= h;  //傳送暖身時間給handler    //arg1和arg2都是Message自帶的用來傳遞一些輕量級存儲int類型的數據，比如進度條的數據等。
                mHandler.sendMessage(message);    //傳送訊息
            }
        };
        timerTask1 =new TimerTask() {//休息用
            @Override
            public void run() {
                r--;
                Message message= mHandler.obtainMessage();
                message. arg1= r;
                mHandler.sendMessage(message);
            }
        };
        timerTask2 = new TimerTask(){//動作用
            @Override
            public void run() {
                w--;
                Message message= mHandler.obtainMessage();
                message. arg1= w;
                mHandler.sendMessage(message);
            }
        };
        //啟動Timer(以毫秒為單位的倒計時)
        if(one) {
            if(h==hot)timeTv.setText("暖身");//顯示暖身兩字
            timer.schedule(timerTask,1000); // timer 去執行TimerTask的run一秒後執行
        }
        //當暖身秒數=0時執行
        if(h==0) {
            if(r==(rest+1))//當休息時間=rest+1時產生音效
                soundPool.play(soundID[0], 1.0f, 1.0f, 0, 0, 1.0f); //播放音效  play()的參數(逾放的音效ID,左聲道音量(0.0~1.0f),右聲道音量(0.0~1.0f),優先撥放順序,是否重複,取樣值)
            timer.cancel();
            one=false;//暖身只跑一次，所以跑完一次後,one設為false
            timer1.schedule(timerTask1, 1000);
            if(r==rest)timeTv.setText("休息");//顯示休息兩字
            //當休息秒數=0時執行
            if(r==0) {
                timer1.cancel();
               if(w==(work+1))//當動作時間=work+1時產生音效
                    soundPool.play(soundID[0], 1.0f, 1.0f, 0, 0, 1.0f);
                timer2.schedule(timerTask2, 1000);
                if(w==work)timeTv.setText("訓練");//顯示訓練兩字
                //當動作秒數=0時執行
                if(w==0) {
                    timer2.cancel();
                    r = rest+1;   //再將休息、動作時間重新計算
                    w = work+1;
                    t--; //跑完一次，次數就減一
                    if(t!=0) startTime();//次數還沒等於零就重新執行startTime再繼續循環
                    if(t==0) // 循環次數結束，想起最後一鈴聲
                    {
                        soundPool.play(soundID[0], 1.0f, 1.0f, 0, 0, 1.0f);
                        btnstart.setText("開始");
                        timeTv.setText("完成，厲害喔!");
                        start=true;
                        first=true;
                        restsetVisibility();//回復顯示Editview
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
        btnstart.setText("開始");
        restsetVisibility();
    }
    //當Fragment要被清除前，會執行此方法
    @Override
    public void onDestroyView() {
        if(!first ) restTime(); //當執行過timer就要重置數據
        if(!start) restTime(); //當正在執行timer時，Fragment被清除時Timer還在運行，所以需要一起重置
        super.onDestroyView();
    }
}