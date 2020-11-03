package com.appwith.trivia.data;

import android.app.DownloadManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.appwith.trivia.controller.AppController;
import com.appwith.trivia.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    ArrayList<Questions> questionsArrayList=new ArrayList<>();
    String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
   public List<Questions> getQuestions(final AnswerAsyncResponse callBack)
   {
       JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, (JSONArray) null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               for(int i=0;i<response.length();i++)
               {
                   try {
                       Questions questions=new Questions();
                       questions.setAnswer(response.getJSONArray(i).get(0).toString());
                       questions.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
//                       Log.d("JSON ", "Questions: "+response.getJSONArray(i).get(0));
//                       Log.d("JSON ", "Answers: "+response.getJSONArray(i).get(1));
                      // Log.d("JSON", "getQuestions: "+questions);
                       //add questions in the list to return
                       questionsArrayList.add(questions);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
               if(callBack!=null)callBack.processFinished(questionsArrayList);
//               Log.d("JSON","JSON Response"+response);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });
     AppController.getInstance().addToRequestQueue(jsonArrayRequest);
       Log.d("JSON", "getQuestions: "+questionsArrayList);
       return questionsArrayList;
   }
}
