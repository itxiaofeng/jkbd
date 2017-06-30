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
        OkHttpUtils<ExamInfo> until=new OkHttpUtils<>(istance);
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        until.url(uri).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
            @Override
            public void onSuccess(ExamInfo result) {
                    Log.e("Application","result="+result);
                    examInfo=result;
            }
            @Override
            public void onError(String error) {
                    Log.e("main","error="+error);
            }
        });

        OkHttpUtils<Result> resUntil = new OkHttpUtils<>(istance);
        String queUrl ="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        resUntil.url(queUrl).targetClass(Result.class).execute(new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                Log.e("Application","result="+result.getReason());
                examQueList = result.getResult();
            }

            @Override
            public void onError(String error) {
                Log.e("main","error="+error);
            }
        });

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
