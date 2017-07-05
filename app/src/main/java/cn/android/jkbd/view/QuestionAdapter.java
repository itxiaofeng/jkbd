package cn.android.jkbd.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.R;
import cn.android.jkbd.bean.Qusetion;

/**
 * Created by Administrator on 2017/7/5.
 */

public class QuestionAdapter extends BaseAdapter {
    Context context;
    List<Qusetion> list;
    public QuestionAdapter(Context context) {
        this.context = context;
        list = ExamApplication.getInstance().getExamQueList();
        Log.e("QuestionAdapter"," QuestionAdapter list = " + list);
        Log.e("QuestionAdapter"," QuestionAdapter list = " + list.size());
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_question,null);
        TextView txv_no = (TextView) view.findViewById(R.id.txv_no);
        ImageView img_que = (ImageView) view.findViewById(R.id.img_que);
        txv_no.setText("第" + ( position + 1) +"题");
        return view;
    }
}
