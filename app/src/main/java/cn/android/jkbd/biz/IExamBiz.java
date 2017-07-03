package cn.android.jkbd.biz;

import cn.android.jkbd.bean.Qusetion;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Qusetion getQuestion();
    Qusetion nextQuestion();
    Qusetion preQuestion();
    void commitExam();
    int getIndex();
}
