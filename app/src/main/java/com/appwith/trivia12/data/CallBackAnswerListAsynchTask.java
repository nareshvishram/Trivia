package com.appwith.trivia12.data;

import com.appwith.trivia12.model.Question;

import java.util.List;

public interface CallBackAnswerListAsynchTask {
    void isProcessFinished(List<Question> questions);
}
