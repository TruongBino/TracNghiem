package com.example.tracnghiem;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;
    private int answerIndex;
    private int correctAnswerIndex;
    private String questionText;

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }

    public Question(String question, List<String> options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    public String getQuestionText() {
        return questionText;
    }

}
