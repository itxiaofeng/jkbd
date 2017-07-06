package cn.android.jkbd;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.bean.Result;
import cn.android.jkbd.biz.ExamBiz;
import cn.android.jkbd.biz.IExamBiz;
import cn.android.jkbd.dao.ErrorExamHelper;
import cn.android.jkbd.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_EXAM_INFO="load_exam_info";
    public static String LOAD_EXAM_QUERSTON="load_exam_question";
    public static String LOAD_DATA_EXAM_SUCCESS="load_data_exam_seccess";
    public static String LOAD_DATA_QUESTION_SUCCESS="load_data_question_seccess";
    ExamInfo examInfo;
    List<Qusetion> examQueList;
    private static ExamApplication istance;
    ErrorExamHelper myHelper;
    SQLiteDatabase db;

    public SQLiteDatabase getDb() {

        return db;
    }
    public void  closeDb() {
        db.close();
        myHelper.close();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myHelper = new ErrorExamHelper(this, "my.db", null, 1);
        db = myHelper.getWritableDatabase();
        istance=this;
    }
    public static ExamApplication getInstance(){
               return istance;
    }
    public ExamInfo getExamInfo() { return examInfo;}

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public List<Qusetion> getExamQueList() { return examQueList;}

    public void setExamQueList(List<Qusetion> examQueList) {
        this.examQueList = examQueList;
    }


}
