package cn.android.jkbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import cn.android.jkbd.R;
import cn.android.jkbd.bean.ExamInfo;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomExam extends AppCompatActivity {
    private TextView txv_examInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        /**/

        Intent intent = this.getIntent();
        ExamInfo examInfo= (ExamInfo)getIntent().getSerializableExtra("ExamInfo");
        this.txv_examInfo = (TextView) findViewById(R.id.txv_examInfo);
        this.txv_examInfo.setText(examInfo.toString());
    }
}
