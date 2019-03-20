package com.bignerdranch.mcasey.geoquiz.Cheat;

import android.content.Context;
import android.content.Intent;

import com.bignerdranch.mcasey.geoquiz.R;

public class CheatPresenter implements CheatPresenterView{

    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.mcasey.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.mcasey.geoquiz.answer_shown";
    CheatActivity mActivity;
    CheatActivityView mView;

    public CheatPresenter(CheatActivity activity, CheatActivityView view) {
        mActivity = activity;
        mView = view;
    }

    public static Intent newIntent(Context context, boolean answerIsTrue) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    public void clickShowAnswer() {
        int message;
        if(mActivity.mAnswerIsTrue)
            message = R.string.true_button;
        else
            message = R.string.false_button;
        mView.showAnswerClick(message);
        setAnswerShownResult(true);
    }

    @Override
    public void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        mActivity.setResult(mActivity.RESULT_OK, data);
    }
}
