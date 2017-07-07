package cn.android.jkbd.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.android.jkbd.R;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.biz.ErrorQues;

/**
 * Created by Administrator on 2017/7/7.
 */

public class ErrorquestionActivity extends ListActivity {
    Cursor cursor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ErrorquestionActivity", " 11111");
        //setContentView(R.layout.error_question);
        SimpleAdapter adapter  = new SimpleAdapter(this,getData(),R.layout.error_question,
                new String[]{"txv_question","image","txv_item","questionAnswer","questionExplains"},
                new int[]{R.id.txv_question,R.id.image,R.id.txv_item,R.id.questionAnswer,R.id.questionExplains}
        );
        setListAdapter(adapter);


    }
    private List<Map<String, Object>> getData(){
        ErrorQues errorQues = new ErrorQues();
        Cursor cursor = errorQues.quert();
        List<Qusetion> queList = new ArrayList<Qusetion>();
        Qusetion ques = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                ques = new Qusetion();
                ques.setQuestion(cursor.getString(cursor
                        .getColumnIndex("question")));
                Log.e("question", "question = " + ques.getQuestion());

            }
        }else {
            Log.e("question", "question = qqqqq");
        }



        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){


                    Map<String, Object> map = new HashMap<String, Object>();

                    Log.e("ErrorquestionActivity", cursor.getString(cursor.getColumnIndexOrThrow("question")));

                    map.put("txv_question", cursor.getString(cursor.getColumnIndexOrThrow("question")));
                    map.put("image", cursor.getString(cursor.getColumnIndexOrThrow("url")));
                    String item,c = "",d = "";
                    if(cursor.getString(cursor.getColumnIndexOrThrow("item3"))!=null && cursor.getString(cursor.getColumnIndexOrThrow("item3")) !=""){
                        c = "C:"+cursor.getString(cursor.getColumnIndexOrThrow("item3"));
                    }
                    if(cursor.getString(cursor.getColumnIndexOrThrow("item4"))!=null && cursor.getString(cursor.getColumnIndexOrThrow("item4")) !=""){
                        d = "D:"+cursor.getString(cursor.getColumnIndexOrThrow("item4"));
                    }
                    item = "A:"+cursor.getString(cursor.getColumnIndexOrThrow("item1"))+"\n"
                            +"B:"+cursor.getString(cursor.getColumnIndexOrThrow("item2"))+"\n"
                            + c +"\n"
                            + d   ;
                    map.put("txv_item",  item);
                        String s = cursor.getString( cursor.getColumnIndexOrThrow("answer"));
                    switch (s){
                        case "1":s="正确答案A";break;
                        case "2":s="正确答案B";break;
                        case "3":s="正确答案C";break;
                        case "4":s="正确答案D";break;

                    }
                    map.put("questionAnswer",  s);
                    map.put("questionExplains", cursor.getString(cursor.getColumnIndexOrThrow("explains")));
                    list.add(map);
                    cursor.moveToNext();
                }
            }
        }
        return list;
    }
}
