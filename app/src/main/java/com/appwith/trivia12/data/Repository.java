package com.appwith.trivia12.data;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.appwith.trivia12.controller.AppController;
import com.appwith.trivia12.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<Question> questions=new ArrayList<>();
    public List<Question> getQuestions(final CallBackAnswerListAsynchTask callBackAnswerListAsynchTask){
        String apiUrl = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(JsonArrayRequest.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        questions.add(new Question(response.getJSONArray(i).get(0).toString(),response.getJSONArray(i).getBoolean(1)));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //when all the objects are added to the list then we have to call the callback.
                if(callBackAnswerListAsynchTask!=null)callBackAnswerListAsynchTask.isProcessFinished(questions);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("questions", "onErrorResponse: "+error);
            }
        });


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questions;
    }

}
