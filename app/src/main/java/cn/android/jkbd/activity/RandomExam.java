package cn.android.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//import com.bumptech.glide.Glide;

import com.squareup.picasso.Picasso;

import java.util.List;

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
    int number = 0;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;
    //LoadExamBroadcast mLoadExamBroadcast;
    //LoadQuestionBroadcast mLoadQuestionBroadcast;
    LoadBroadcast mLoadBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadBroadcast = new LoadBroadcast();
        loadData();
        setListener();
    }

    private void setListener() {
        registerReceiver(mLoadBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUERSTON));
    }

    private void loadData() {
        biz = new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initData() {
       if(isLoadExamInfo && isLoadQuestions){
            ExamInfo examInfo =  ExamApplication.getInstance().getExamInfo();
            if(examInfo!=null){
                TextView txv_examInfo = (TextView) findViewById(R.id.txv_examInfo);
                txv_examInfo.setText(examInfo.toString());

            }
           List<Qusetion> examQuelist = ExamApplication.getInstance().getExamQueList();
            if(examQuelist!=null){
                setQuestion(examQuelist.get(number));
            }
        }
    }

    

    protected boolean setQuestion(Qusetion qusetion){
        if(qusetion!=null){
            TextView txv_ques = (TextView) findViewById(R.id.txv_question);
           txv_ques.setText(number + 1 +"."+qusetion.getQuestion());


            ImageView image = (ImageView) findViewById(R.id.image);
            //这里就是加载图片的代码
           //Glide.with(RandomExam.this).load(qusetion.getUrl()).into(image);
            Picasso.with(RandomExam.this).load(qusetion.getUrl()).into(image);
            TextView txv_ans = (TextView) findViewById(R.id.txv_item);
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

    class LoadBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadBroadcast","LoadBroadcast isSuccess = "+isSuccess);
            if(isSuccess){
                isLoadExamInfo = true;
                isLoadQuestions = true;
            }
            initData();
        }
    }
}
