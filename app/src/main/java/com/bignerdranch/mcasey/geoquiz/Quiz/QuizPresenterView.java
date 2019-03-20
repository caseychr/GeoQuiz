package com.bignerdranch.mcasey.geoquiz.Quiz;

import android.content.Intent;
import android.support.annotation.Nullable;

public interface QuizPresenterView {

    void nextQuestion();

    void prevQuestion();

    void checkAnswer(boolean userPressedTrue);

    void startOver();

    void cheatResult(int requestCode, int resultCode, @Nullable Intent data);
}
