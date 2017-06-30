package cn.android.jkbd;

import android.app.Application;
import android.util.Log;

import java.util.List;

import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.bean.Result;
import cn.android.jkbd.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo examInfo;
    List<Qusetion> examQueList;
    private static ExamApplication istance;

    @Override
    public void onCreate() {
        super.onCreate();
        istance=this;
        intiData();
    }
    public static ExamApplication getInstance(){
               return istance;
    }
    private void intiData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();

    }

    public ExamInfo getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public List<Qusetion> getExamQueList() {
        return examQueList;
    }

    public void setExamQueList(List<Qusetion> examQueList) {
        this.examQueList = examQueList;
    }
}
