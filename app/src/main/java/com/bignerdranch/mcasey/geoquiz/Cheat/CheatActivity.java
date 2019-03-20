package com.bignerdranch.mcasey.geoquiz.Cheat;

import static com.bignerdranch.mcasey.geoquiz.Cheat.CheatPresenter.EXTRA_ANSWER_IS_TRUE;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.mcasey.geoquiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheatActivity extends AppCompatActivity implements CheatActivityView{

    private CheatPresenter mPresenter;
    public boolean mAnswerIsTrue;
    public int mMessage;

    @BindView(R.id.answer_text_view) TextView mAnswerTV;
    @BindView(R.id.show_answer_button) Button mShowAnswerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mPresenter = new CheatPresenter(this, this);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.show_answer_button)
    public void showAnswerClick(){
        mPresenter.clickShowAnswer();
        mAnswerTV.setText(mMessage);
    }
}
