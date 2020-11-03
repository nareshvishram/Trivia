package com.appwith.trivia.model;

public class Questions {
    private String answer;
    private boolean answerTrue;

    public Questions(String answer,boolean answerTrue) {
        this.answer = answer;
        this.answerTrue=answerTrue;
    }
    public Questions()
    {

    }
    public String getAnswer() {
        return answer;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
