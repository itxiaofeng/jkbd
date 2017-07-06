package cn.android.jkbd.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.dao.ErrorExamHelper;

/**
 * Created by Administrator on 2017/7/7.
 */

public class ErrorQues {

    public void insert(Qusetion ques){

        SQLiteDatabase db = ExamApplication.getInstance().getDb();
        ContentValues values = new ContentValues();
        values.put("id",ques.getId());
        values.put("question", ques.getQuestion());
        values.put("answer",ques.getAnswer() );
        values.put("item1", ques.getItem1());
        values.put("item2", ques.getItem2() );
        values.put("item3", ques.getItem3());
        values.put("item4", ques.getItem4());
        values.put("explains", ques.getExplains());
        values.put("url", ques.getUrl());
        values.put("UserAnswer", ques.getUserAnswer());
        long e = db.insert("ErrorQuesTb",null,values);
    }
    public  void delete(){
        SQLiteDatabase db = ExamApplication.getInstance().getDb();
        db.execSQL("DELETE FROM errorquestb");

    }
    public Cursor quert(){
        SQLiteDatabase db = ExamApplication.getInstance().getDb();
        Cursor qc = db.query("ErrorQuesTb",new String[]{"id","question","answer","item1","item2","item3","item4","explains","url","UserAnswer"}
                ,null,null,null,null,null,null);
        return qc;
    }
}
