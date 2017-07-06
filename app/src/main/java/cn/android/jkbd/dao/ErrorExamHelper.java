package cn.android.jkbd.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2017/7/7.
 */

public class ErrorExamHelper extends SQLiteOpenHelper {

    public static final String DB_Name = "JKBDSQL";         //数据库名
    public static final String TABLE_NAME = "ErrorQuesTb"; //表名
    public static final String ID = "id";
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String ITEM1 = "item1";
    public static final String ITEM2 = "item2";
    public static final String ITEM3 = "item3";
    public static final String ITEM4 = "item4";
    public static final String EXPLAINS = "explains";
    public static final String URL = "url";
    public static final String USERANSWER = "UserAnswer";
    public ErrorExamHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "create table if not exists "+TABLE_NAME+ " ("
                + ID +" integer,"
                + QUESTION +" varchar,"
                + ANSWER +" varchar,"
                + ITEM1 +" varchar,"
                + ITEM2 +" varchar,"
                + ITEM3 +" varchar,"
                + ITEM4 +" varchar,"
                + EXPLAINS +" varchar,"
                + URL +" varchar,"
                + USERANSWER +" varchar )";


            try{
                db.execSQL(SQL);
                Log.e("ErrorExamHelper"," ErrorExamHelper onCreate 数据库创建成功" );
            }catch (SQLException e){
                Log.e("ErrorExamHelper"," ErrorExamHelper onCreate " + e);
            }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
