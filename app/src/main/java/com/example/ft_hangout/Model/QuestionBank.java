package com.example.ft_hangout.Model;

import android.util.Log;
import android.view.ViewDebug;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        Question tmp;
        int j;

        Random rand = new Random();
        mNextQuestionIndex = 0;
        for(int i=0; i < questionList.size();i++) {

            j = rand.nextInt(questionList.size());
            tmp = questionList.get(i);
            questionList.set(i, questionList.get(j));
            questionList.set(j, tmp);
        }
            mQuestionList = questionList;
    }
    public Question getQuestion()
    {
        mNextQuestionIndex = mNextQuestionIndex == mQuestionList.size() ? 0 : mNextQuestionIndex;
        return (mQuestionList.get(mNextQuestionIndex++));
    }
    public int getSize()
    {
        return (mQuestionList.size());
    }
}
