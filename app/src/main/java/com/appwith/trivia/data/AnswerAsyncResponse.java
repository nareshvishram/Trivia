package com.appwith.trivia.data;

import com.appwith.trivia.model.Questions;

import java.util.ArrayList;

public interface AnswerAsyncResponse {
    void processFinished(ArrayList<Questions> questions);

}
