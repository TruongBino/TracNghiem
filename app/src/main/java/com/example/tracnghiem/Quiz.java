package com.example.tracnghiem;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int correctOptionIndex;
    private int currentQuestionIndex;
    private List<Integer> userAnswers;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
    }
    public int getTotalQuestions() {
        return questions.size();
    }
    public int getCorrectAnswersCount() {
        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i) == questions.get(i).getCorrectAnswerIndex()) {
                correctCount++;
            }
        }
        return correctCount;
    }
    public Question getCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex); // Make sure currentQuestionIndex is within the valid range
        } else {
            // Handle the case where currentQuestionIndex is out of range
            return null; // Or throw an exception, depending on your requirements
        }
    }

    public boolean checkAnswer(int selectedOptionIndex) {
        return selectedOptionIndex == getCurrentQuestion().getCorrectAnswerIndex();
    }

    public void moveToNextQuestion() {
        currentQuestionIndex++;
    }

    public boolean isQuizFinished() {
        return currentQuestionIndex >= questions.size();
    }
    public boolean isLastQuestion() {
        return currentQuestionIndex == questions.size() - 1;
    }
}
