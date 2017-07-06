package cn.android.jkbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.R;
import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Result;
import cn.android.jkbd.biz.ErrorQues;
import cn.android.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_errorques);
    }

    public void exam(View view) {
        Intent intent = new Intent(MainActivity.this,RandomExam.class);
        startActivity(intent);
    }

    public void setup(View view) {
        ErrorQues errorQues = new ErrorQues();
        errorQues.delete();
    }

    public void exit(View view) {
        ExamApplication.getInstance().closeDb();
        finish();
    }

    public void errorqueestion(View view) {
        Intent intent = new Intent(MainActivity.this,ErrorquestionActivity.class);
        startActivity(intent);
    }
}
