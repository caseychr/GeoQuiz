package com.bignerdranch.mcasey.geoquiz.Quiz;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.mcasey.geoquiz.Cheat.CheatActivity;
import com.bignerdranch.mcasey.geoquiz.Cheat.CheatPresenter;
import com.bignerdranch.mcasey.geoquiz.Question;
import com.bignerdranch.mcasey.geoquiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity implements QuizActivityView{

    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    public static final int REQUEST_CODE_CHEAT = 0;
    QuizPresenter mPresenter;

    //Butterknife
    @BindView(R.id.true_button) Button mTrueButton;
    @BindView(R.id.false_button) Button mFalseButton;
    @BindView(R.id.prev_button) Button mPrevButton;
    @BindView(R.id.next_button) Button mNextButton;
    @BindView(R.id.question_text_view) TextView mQuestionTextView;
    @BindView(R.id.cheat_button) Button mCheatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        // Inflates the resource assigned below
        setContentView(R.layout.activity_quiz);
        mPresenter = new QuizPresenter(this);
        if(savedInstanceState != null){
            mPresenter.mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        ButterKnife.bind(this);
        updateQuestion();
        enableButtons(mQuestionBank[mPresenter.mCurrentIndex]);
    }

    @Override
    @OnClick(R.id.prev_button)
    public void clickPrev(View view) {
        mPresenter.prevQuestion();
        updateQuestion();
        enableButtons(mQuestionBank[mPresenter.mCurrentIndex]);
    }

    @Override
    @OnClick(R.id.next_button)
    public void clickNext(View view) {
        mPresenter.nextQuestion();
        updateQuestion();
        enableButtons(mQuestionBank[mPresenter.mCurrentIndex]);
    }

    @Override
    @OnClick(R.id.true_button)
    public void clickTrue(View view) {
        mPresenter.checkAnswer(true);
    }

    @Override
    @OnClick(R.id.false_button)
    public void clickFalse(View view) {
        mPresenter.checkAnswer(false);
    }

    @Override
    @OnClick(R.id.cheat_button)
    public void clickCheat(View view) {
        Intent intent = CheatPresenter.newIntent(QuizActivity.this, mQuestionBank[mPresenter.mCurrentIndex].isAnswerTrue());
        startActivityForResult(intent, REQUEST_CODE_CHEAT);
    }

    @Override
    public void updateQuestion() {
        int question = mQuestionBank[mPresenter.mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    @Override
    public void enableButtons(Question question) {
        if(question.isAnswered() || question.isCheatedOn()){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }

        if(mPresenter.mCurrentIndex == 0)
            mPrevButton.setEnabled(false);
        else if(mPresenter.mCurrentIndex == mQuestionBank.length-1)
            mNextButton.setEnabled(false);
        else{
            mPrevButton.setEnabled(true);
            mNextButton.setEnabled(true);
        }

        if(mPresenter.mCurrentIndex == mQuestionBank.length-1 && (question.isCheatedOn() || question.isAnswered()))
            startOver();
    }

    @Override
    public void startOver() {
        mCheatButton.setText(R.string.start_over_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startOver();
            }
        });
    }

    @Override
    public void showToast(int message, int length) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mPresenter.mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mPresenter.cheatResult(requestCode, resultCode, data);
    }
}
