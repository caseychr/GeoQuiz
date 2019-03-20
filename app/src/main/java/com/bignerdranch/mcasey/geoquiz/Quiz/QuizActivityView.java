package com.bignerdranch.mcasey.geoquiz.Quiz;

import android.view.View;

import com.bignerdranch.mcasey.geoquiz.Question;
import com.bignerdranch.mcasey.geoquiz.R;

public interface QuizActivityView {

    Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    void clickPrev(View view);

    void clickNext(View view);

    void clickTrue(View view);

    void clickFalse(View view);

    void clickCheat(View view);

    void updateQuestion();

    void enableButtons(Question question);

    void startOver();

    void showToast(int message, int length);
}
