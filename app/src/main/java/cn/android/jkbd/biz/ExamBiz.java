package cn.android.jkbd.biz;

import cn.android.jkbd.dao.ExamDao;
import cn.android.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    public ExamBiz(){
        this.dao = new ExamDao();
    }
    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.LoadQuestionList();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
