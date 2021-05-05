package com.appwith.trivia12.model;

public class Question {
    private String question;
    private boolean answerTrue;
    public Question(String answer, boolean answerTrue) {
        this.question = answer;
        this.answerTrue = answerTrue;
    }

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
}
