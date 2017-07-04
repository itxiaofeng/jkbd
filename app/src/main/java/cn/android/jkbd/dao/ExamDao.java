package cn.android.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Result;
import cn.android.jkbd.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamDao implements IExamDao {
    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamInfo> until=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        until.url(uri).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
            @Override
            public void onSuccess(ExamInfo result) {
                //Log.e("Application","ExamResult="+result);
                ExamApplication.getInstance().setExamInfo(result);
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                .putExtra(ExamApplication.LOAD_DATA_EXAM_SUCCESS,true));
            }
            @Override
            public void onError(String error) {
                Log.e("Application","error="+error);
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                        .putExtra(ExamApplication.LOAD_DATA_EXAM_SUCCESS,false));
            }
        });
    }

    @Override
    public void LoadQuestionList() {
        OkHttpUtils<Result> resUntil = new OkHttpUtils<>(ExamApplication.getInstance());
        String queUrl ="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        resUntil.url(queUrl).targetClass(Result.class).execute(new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                boolean success=false;
               //Log.e("Application","Questionresult="+result);
                if(result!=null && result.getError_code()==0){
                    ExamApplication.getInstance().setExamQueList(result.getResult());
                   success = true;
                }
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUERSTON)
                        .putExtra(ExamApplication.LOAD_DATA_QUESTION_SUCCESS,success));
            }

            @Override
            public void onError(String error) {
                Log.e("Application","error="+error);
                ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUERSTON)
                        .putExtra(ExamApplication.LOAD_DATA_QUESTION_SUCCESS,false));
            }
        });
    }
}
