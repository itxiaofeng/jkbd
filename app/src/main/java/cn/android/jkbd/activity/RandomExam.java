package cn.android.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


//import com.bumptech.glide.Glide;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.R;
import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.bean.Result;
import cn.android.jkbd.biz.ExamBiz;
import cn.android.jkbd.biz.IExamBiz;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomExam extends AppCompatActivity {
    LinearLayout layoutLoading;
    TextView txv_examInfo,txv_ques,txv_ans,txv_load;
    ImageView image;
    ProgressBar dialog;
    int number = 0;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;
    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;

    LoadBroadcast mLoadBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        mLoadBroadcast = new LoadBroadcast();
        biz = new ExamBiz();
        setListener();
        loadData();
    }

    private void initView() {

        layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
        txv_load = (TextView) findViewById(R.id.txv_load);
        dialog = (ProgressBar) findViewById(R.id.dialog);
        txv_examInfo = (TextView) findViewById(R.id.txv_examInfo);
        txv_ques = (TextView) findViewById(R.id.txv_question);
        image = (ImageView) findViewById(R.id.image);
        txv_ans = (TextView) findViewById(R.id.txv_item);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void setListener() {
        registerReceiver(mLoadBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUERSTON));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        txv_load.setText("下载数据中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initData() {
        if(isLoadExamInfoReceiver && isLoadQuestionsReceiver){
            if(isLoadExamInfo && isLoadQuestions){
               layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo =  ExamApplication.getInstance().getExamInfo();
                if(examInfo!=null){
                    txv_examInfo.setText(examInfo.toString());
                }
               List<Qusetion> examQuelist = ExamApplication.getInstance().getExamQueList();
                if(examQuelist!=null){
                    setQuestion(examQuelist.get(number));
                }
            }else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                txv_load.setText("下载失败，点击页面空白处重新下载！");
            }

        }
    }

    

    protected boolean setQuestion(Qusetion qusetion){
        if(qusetion!=null){
           txv_ques.setText(number + 1 +"."+qusetion.getQuestion());
            //这里就是加载图片的代码
           //Glide.with(RandomExam.this).load(qusetion.getUrl()).into(image);
            Picasso.with(RandomExam.this).load(qusetion.getUrl()).into(image);
            txv_ans.setText(
                    "A."+qusetion.getItem1()+ "\n" +
                    "B."+qusetion.getItem2()+ "\n" +
                    "C."+qusetion.getItem3()+ "\n" +
                    "D."+qusetion.getItem4()
            );
        }
        return false;
    }

    public void nextQuestion(View view) {

    }

    public void preQuestion(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadBroadcast!=null){
            unregisterReceiver(mLoadBroadcast);
        }
    }

    class LoadBroadcast extends BroadcastReceiver {
        boolean isSuccessExam = false;
        boolean isSuccessQuestion = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isSuccessExam!=true){
                isSuccessExam = intent.getBooleanExtra(ExamApplication.LOAD_DATA_EXAM_SUCCESS, false);
            }
            if(isSuccessQuestion!=true){
                isSuccessQuestion = intent.getBooleanExtra(ExamApplication.LOAD_DATA_QUESTION_SUCCESS, false);

            }
            if(intent.getAction().equals(ExamApplication.LOAD_EXAM_INFO)){
                isLoadExamInfoReceiver = true;
            }
            if(intent.getAction().equals(ExamApplication.LOAD_EXAM_QUERSTON)){
                isLoadQuestionsReceiver = true;
            }
            Log.e("LoadBroadcast", "isSuccessExam = " + isSuccessExam);
            Log.e("LoadBroadcast", "isSuccessQuestion = " + isSuccessQuestion);
            Log.e("LoadBroadcast","isLoadQuestionsReceiver = "+isLoadQuestionsReceiver+"   isLoadExamInfoReceiver = "+isLoadExamInfoReceiver);
            Log.e("intent.getAction()",intent.getAction());
            if (isSuccessExam) {
                isLoadExamInfo = true;
            }
            if (isSuccessQuestion) {
                isLoadQuestions = true;
            }
            initData();
        }
    }
}
