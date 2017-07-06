package cn.android.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.android.jkbd.ExamApplication;
import cn.android.jkbd.R;
import cn.android.jkbd.bean.ExamInfo;
import cn.android.jkbd.bean.Qusetion;
import cn.android.jkbd.biz.ExamBiz;
import cn.android.jkbd.biz.IExamBiz;
import cn.android.jkbd.view.QuestionAdapter;

//import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomExam extends AppCompatActivity {
    RadioButton[] rdbs = new RadioButton[4];
    QuestionAdapter adapter;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;
    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;
    String userAnswer = null;
    LoadBroadcast mLoadBroadcast;
    @BindView(R.id.dialog)
    ProgressBar dialog;
    @BindView(R.id.txv_load)
    TextView txv_load;
    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;
    @BindView(R.id.txv_examInfo)
    TextView txv_examInfo;
    @BindView(R.id.txv_time)
    TextView txv_time;
    @BindView(R.id.txv_question)
    TextView txv_ques;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.txv_item)
    TextView txv_ans;
    @BindView(R.id.rdobtn_a)
    RadioButton rdobtn_a;
    @BindView(R.id.rdobtn_b)
    RadioButton rdobtn_b;
    @BindView(R.id.rdobtn_c)
    RadioButton rdobtn_c;
    @BindView(R.id.rdobtn_d)
    RadioButton rdobtn_d;
    @BindView(R.id.gallery)
    Gallery gallert;
    @BindView(R.id.questionAnswer)
    TextView txv_questionAnswer;
    @BindView(R.id.questionExplains)
    TextView txv_questionExplains;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        ButterKnife.bind(this);

        mLoadBroadcast = new LoadBroadcast();
        biz = new ExamBiz();
        initView();
        setListener();
        loadData();
    }

    private void initView() {
        rdbs[0] = rdobtn_a;
        rdbs[1] = rdobtn_b;
        rdbs[2] = rdobtn_c;
        rdbs[3] = rdobtn_d;
        layoutLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void setListener() {
        registerReceiver(mLoadBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_QUERSTON));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        txv_load.setText("下载数据中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
        rdobtn_a.setOnCheckedChangeListener(listener);
        rdobtn_b.setOnCheckedChangeListener(listener);
        rdobtn_c.setOnCheckedChangeListener(listener);
        rdobtn_d.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked == false)
                return;
            switch (buttonView.getId()) {
                case R.id.rdobtn_a:
                    userAnswer = "1";
                    break;
                case R.id.rdobtn_b:
                    userAnswer = "2";
                    break;
                case R.id.rdobtn_c:
                    userAnswer = "3";
                    break;
                case R.id.rdobtn_d:
                    userAnswer = "4";
                    break;
            }
            if (Integer.valueOf(userAnswer) > 0) {
                resetOptions();
                rdbs[Integer.valueOf(userAnswer) - 1].setChecked(true);
            }
        }
    };

    private void initData() {
        if (isLoadExamInfoReceiver && isLoadQuestionsReceiver) {
            if (isLoadExamInfo && isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getExamInfo();
                if (examInfo != null) {
                    txv_examInfo.setText(examInfo.toString());
                    initTimer(examInfo);
                }
                initGallery();
                setQuestion(biz.getQuestion());

            } else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                txv_load.setText("下载失败，点击页面空白处重新下载！");
            }

        }
    }

    private void initGallery() {
        adapter = new QuestionAdapter(this);
        gallert.setAdapter(adapter);
        gallert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveUserAnswer();
                setQuestion(biz.getQuestion(position));
            }
        });
    }

    private void initTimer(ExamInfo examInfo) {
        int sum = examInfo.getLimitTime() * 60 * 1000;
        final long endTime = System.currentTimeMillis() + sum;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = endTime - System.currentTimeMillis();
                final long min = l / 1000 / 60;
                final long sec = l / 1000 % 60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txv_time.setText("剩余时间：" + min + "分" + sec + "秒");
                    }
                });
            }
        }, 0, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        }, sum);
    }


    protected void setQuestion(Qusetion qusetion) {
        if (qusetion != null) {
            txv_ques.setText(biz.getIndex() + 1 + "." + qusetion.getQuestion());
            //这里就是加载图片的代码
            //Glide.with(RandomExam.this).load(qusetion.getUrl()).into(image);
            if (qusetion.getUrl() != null && !qusetion.getUrl().equals("")) {
                image.setVisibility(View.VISIBLE);
                Picasso.with(RandomExam.this).load(qusetion.getUrl()).into(image);
            } else {
                image.setVisibility(View.GONE);
            }
            rdobtn_c.setVisibility(View.GONE);
            rdobtn_d.setVisibility(View.GONE);
            String c = "", d = "";
            if (qusetion.getItem3() != "") {
                c = "C." + qusetion.getItem3() + "\n";
                rdobtn_c.setVisibility(View.VISIBLE);
            }
            if (qusetion.getItem4() != "") {
                d = "D." + qusetion.getItem4();
                rdobtn_d.setVisibility(View.VISIBLE);
            }
            txv_ans.setText(
                    "A." + qusetion.getItem1() + "\n" +
                            "B." + qusetion.getItem2() + "\n" +
                            c + d
            );
            resetOptions();
            String userA = qusetion.getUserAnswer();
            String answ = qusetion.getAnswer();
            Log.e("setQuestion", "   qusetion = " + qusetion);
            Log.e("setQuestion", "   userA = " + userA);
            if (userA != null && !userA.equals("")) {
                rdbs[Integer.valueOf(userA) - 1].setChecked(true);
                ableOptions(false);
                String questionAnswer=null;
                if(qusetion.getAnswer().equals("1"))
                    questionAnswer="A";
                else if(qusetion.getAnswer().equals("2"))
                questionAnswer="B";
                else if(qusetion.getAnswer().equals("3"))
                    questionAnswer="C";
                else if(qusetion.getAnswer().equals("4"))
                    questionAnswer="D";
                txv_questionAnswer.setText("正确答案："+questionAnswer);
                txv_questionExplains.setText("答案解析："+qusetion.getExplains());
            } else {
                ableOptions(true);
            }

        }
    }

    private void resetOptions() {
        for (RadioButton rdb : rdbs) {
            rdb.setChecked(false);
        }
    }

    private void ableOptions(boolean bool) {
        for (RadioButton rdb : rdbs) {
            rdb.setClickable(bool);
        }
    }

    private void saveUserAnswer() {

        if (userAnswer != null && !userAnswer.equals("")) {
            biz.getQuestion().setUserAnswer(userAnswer);
        }
        ableOptions(false);
        adapter.notifyDataSetChanged();
        userAnswer = "";
    }

    public void nextQuestion(View view) {
        saveUserAnswer();
        setQuestion(biz.nextQuestion());

    }

    public void preQuestion(View view) {
        saveUserAnswer();
        setQuestion(biz.preQuestion());
    }
    public void commit(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("交卷")
            .setMessage("你还有剩余时间，是否交卷？")
            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    commit();
                }
            })
        .setNegativeButton("取消",null);
        builder.create().show();
    }
    public void commit() {
        saveUserAnswer();
        int sum = biz.commitExam();
        View inflate = View.inflate(this, R.layout.layour_result, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView txvResult = (TextView) inflate.findViewById(R.id.txv_result);
        txvResult.setText("你的分数\n" + sum + "分！");
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadBroadcast != null) {
            unregisterReceiver(mLoadBroadcast);
        }
    }


    class LoadBroadcast extends BroadcastReceiver {
        boolean isSuccessExam = false;
        boolean isSuccessQuestion = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isSuccessExam != true) {
                isSuccessExam = intent.getBooleanExtra(ExamApplication.LOAD_DATA_EXAM_SUCCESS, false);
            }
            if (isSuccessQuestion != true) {
                isSuccessQuestion = intent.getBooleanExtra(ExamApplication.LOAD_DATA_QUESTION_SUCCESS, false);

            }
            if (intent.getAction().equals(ExamApplication.LOAD_EXAM_INFO)) {
                isLoadExamInfoReceiver = true;
            }
            if (intent.getAction().equals(ExamApplication.LOAD_EXAM_QUERSTON)) {
                isLoadQuestionsReceiver = true;
            }
            Log.e("LoadBroadcast", "isSuccessExam = " + isSuccessExam);
            Log.e("LoadBroadcast", "isSuccessQuestion = " + isSuccessQuestion);
            Log.e("LoadBroadcast", "isLoadQuestionsReceiver = " + isLoadQuestionsReceiver + "   isLoadExamInfoReceiver = " + isLoadExamInfoReceiver);
            Log.e("intent.getAction()", intent.getAction());
            if (isSuccessExam) {
                isLoadExamInfo = true;
            }
            if (isSuccessQuestion) {
                isLoadQuestions = true;
            }
            initData();
        }
    }
}
