package com.bignerdranch.mcasey.geoquiz.Quiz;

import static com.bignerdranch.mcasey.geoquiz.Quiz.QuizActivity.REQUEST_CODE_CHEAT;
import static com.bignerdranch.mcasey.geoquiz.Quiz.QuizActivity.TAG;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bignerdranch.mcasey.geoquiz.Cheat.CheatActivity;
import com.bignerdranch.mcasey.geoquiz.Cheat.CheatPresenter;
import com.bignerdranch.mcasey.geoquiz.Question;
import com.bignerdranch.mcasey.geoquiz.R;

public class QuizPresenter implements QuizPresenterView {

    public int mCurrentIndex = 0;
    public int numberCorrect = 0;
    public boolean mIsCheater = false;

    public Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    QuizActivityView mView;

    public QuizPresenter(QuizActivityView view){
        mView = view;
    }

    @Override
    public void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        mIsCheater = mQuestionBank[mCurrentIndex].isCheatedOn();
    }

    @Override
    public void prevQuestion() {
        mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
        mIsCheater = mQuestionBank[mCurrentIndex].isCheatedOn();
    }

    @Override
    public void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mQuestionBank[mCurrentIndex].setAnswered(true);
        int messageResId = 0;

        if(mIsCheater)
            messageResId = R.string.judgment_toast;
        else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
                numberCorrect++;
            }
            else
                messageResId = R.string.incorrect_toast;
        }
        mView.showToast(messageResId, Toast.LENGTH_SHORT);
        if(mCurrentIndex == mQuestionBank.length-1){
            mView.showToast(R.string.quiz_score,
                    Toast.LENGTH_SHORT);
            numberCorrect = 0;
        }
        mView.enableButtons(mQuestionBank[mCurrentIndex]);
    }

    @Override
    public void startOver() {
        for(Question question:mQuestionBank){
            question.setCheatedOn(false);
            question.setAnswered(false);
            mCurrentIndex = 0;
            mView.updateQuestion();
        }
    }

    @Override
    public void cheatResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK)
            return;
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null)
                return;
            mIsCheater = CheatPresenter.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheatedOn(true);
            Log.d(TAG, String.valueOf(mIsCheater));
        }
    }
}
