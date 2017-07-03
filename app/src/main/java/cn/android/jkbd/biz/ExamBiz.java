package cn.android.jkbd.biz;

import java.util.List;

import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.dao.ExamDao;
import cn.android.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    int index = 0;
    List<Qusetion> list = null;
    public ExamBiz(){
        this.dao = new ExamDao();
    }
    @Override
    public void beginExam() {
        index = 0;
        dao.loadExamInfo();
        dao.LoadQuestionList();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Qusetion getQuestion() {
        list = ExamApplication.getInstance().getExamQueList();
        if(list!=null) {
            return list.get(index);
        }else {
            return null;
        }
    }

    @Override
    public Qusetion nextQuestion() {
        if(list!=null && index<list.size()-1) {
            return list.get(++index);
        }else {
            return null;
        }
    }

    @Override
    public Qusetion preQuestion() {
        if(list!=null && index>0) {
            return list.get(--index);
        }else {
            return null;
        }
    }

    @Override
    public void commitExam() {

    }
}
