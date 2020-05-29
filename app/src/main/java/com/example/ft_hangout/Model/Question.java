package com.example.ft_hangout.Model;

import java.util.List;

public class Question {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnwserIndex;

    public Question(String question, List<String> choiceList, int anwserIndex) {
        this.setQuestion(question);
        this.setChoiceList(choiceList);
        this.setAnwserIndex(anwserIndex);
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        if (question == null)
            throw new IllegalArgumentException("No question gived !");
        mQuestion = question;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> choiceList) {
        if (choiceList == null || choiceList.size() > 4)
            throw new IllegalArgumentException("Bad list of question gived");
        mChoiceList = choiceList;
    }

    public int getAnwserIndex() {
        return mAnwserIndex;
    }

    public void setAnwserIndex(int anwserIndex) {
        if (anwserIndex > 3 || anwserIndex < 0 )
            throw new IllegalArgumentException("Bad answer gived");
        mAnwserIndex = anwserIndex;
    }
}
