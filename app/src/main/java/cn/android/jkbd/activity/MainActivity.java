package cn.android.jkbd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
        final ErrorQues errorQues = new ErrorQues();

        if(errorQues.quert().getCount()<=0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错题清空")
                    .setMessage("当前没错题")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //errorQues.delete();
                        }
                    });
            builder.create().show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错题清空")
                    .setMessage("你已成功清空错题")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            errorQues.delete();
                        }
                    });
            builder.create().show();
        }


    }

    public void exit(View view) {
        ExamApplication.getInstance().closeDb();
        finish();
    }

    public void errorqueestion(View view) {
        final ErrorQues errorQues = new ErrorQues();

        if(errorQues.quert().getCount()<=0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错题集合")
                    .setMessage("当前没错题")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //errorQues.delete();
                        }
                    });
            builder.create().show();
        }else {
            Intent intent = new Intent(MainActivity.this,ErrorquestionActivity.class);
            startActivity(intent);
        }


    }
}
