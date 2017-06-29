package cn.android.jkbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.android.jkbd.R;
import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exam(View view) {

        OkHttpUtils<ExamInfo> okHttpUtils = new OkHttpUtils<>(getApplicationContext());
        String url = "http://101.251.196.90:8080/JztkServer/examInfo";
        okHttpUtils.url(url).
                targetClass(ExamInfo.class).
                execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result = "+result);

                        ExamInfo  examInfo = (ExamInfo) result;
                        Intent intent = new Intent(MainActivity.this,RandomExam.class);
                        intent.putExtra("ExamInfo",examInfo);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error = "+error);

                    }
                });
    }
}
