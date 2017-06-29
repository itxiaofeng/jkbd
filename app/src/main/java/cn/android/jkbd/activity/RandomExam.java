package cn.android.jkbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.android.jkbd.R;
import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.bean.Result;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomExam extends AppCompatActivity {
    private int number=0;
    private Result result;
    private ExamInfo examInfo;
    private List<Qusetion> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Intent intent = this.getIntent();
        /*
        获取avtivity间传的值
         */
        examInfo= (ExamInfo)getIntent().getSerializableExtra("examInfo");
        result = (Result)getIntent().getSerializableExtra("result") ;
        if(examInfo==null || result==null){
            Log.e("Exam","页面传值为空，出现异常");
            return;
        }
        list = result.getResult();
        /*初始化赋值*/

        TextView txv_examInfo = (TextView) findViewById(R.id.txv_examInfo);
        txv_examInfo.setText(examInfo.toString());




    }
    protected boolean setQuestion(Qusetion qusetion){
        if(qusetion!=null){
            TextView txv_ques = (TextView) findViewById(R.id.txv_question);
            txv_ques.setText(number+"."+list.get(number).getQuestion());

            TextView txv_ans = (TextView) findViewById(R.id.txv_item);
            txv_ans.setText(
                    "A."+qusetion.getItem1()+
                    "B."+qusetion.getItem2()+
                    "C."+qusetion.getItem3()+
                    "D."+qusetion.getItem4()
            );

        }
        return false;
    }

    public void nextQuestion(View view) {

    }

    public void preQuestion(View view) {
    }
}
