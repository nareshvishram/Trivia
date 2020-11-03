package com.appwith.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appwith.trivia.data.AnswerAsyncResponse;
import com.appwith.trivia.data.QuestionBank;
import com.appwith.trivia.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView questionCounter,questionTextView;
    private Button falseButton;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private TextView correctQuestions;
    private int currQuestionIndex=0;
    private int numberOfCorrectQuestions=0;
    private int numberOfWrongQuestions;
    private  List<Questions> questions;
    private   boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton=findViewById(R.id.next_button);
        prevButton=findViewById(R.id.prev_button);
        questionCounter=findViewById(R.id.counter_questions);
        questionTextView=findViewById(R.id.question_textView);
        falseButton=findViewById(R.id.false_button);
        trueButton=findViewById(R.id.true_button);
        correctQuestions=findViewById(R.id.correct_count);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);

       questions= new QuestionBank().getQuestions(new AnswerAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Questions> questions) {
                questionTextView.setText(questions.get(currQuestionIndex).getAnswer());
                questionCounter.setText(currQuestionIndex+" / "+questions.size());
                correctQuestions.setText("Correct : "+numberOfCorrectQuestions);
                Log.d("Questions", "onCreate: "+questions);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.prev_button:
                currQuestionIndex=currQuestionIndex-1;
                if(currQuestionIndex<0)
                    currQuestionIndex=0;
                updateQuestions();
                questionCounter.setText(currQuestionIndex+" / "+questions.size());
                //flag=true;
                break;
            case R.id.next_button:
                currQuestionIndex=(currQuestionIndex+1)%questions.size();
                questionCounter.setText(currQuestionIndex+" / "+questions.size());
                flag=true;
                updateQuestions();
                break;
            case R.id.true_button:
                checkAnswer(true);
                updateQuestions();
                //Toast.makeText(this, "Answer is "+res, Toast.LENGTH_SHORT).show();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestions();
                //Toast.makeText(this, "Answer is "+ans, Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void updateQuestions()
    {
        questionTextView.setText(questions.get(currQuestionIndex).getAnswer());

    }
    public void checkAnswer(boolean ans)
    {

        boolean correct= questions.get(currQuestionIndex).isAnswerTrue();
        if(correct==ans)
        {
            Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
            fadeView();
            if(flag==true)
            numberOfCorrectQuestions=numberOfCorrectQuestions+1;
            correctQuestions.setText("Correct : "+numberOfCorrectQuestions);
            flag=false;
        }
        else
        {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
            shakeAnimation();
        }
    }
    private void fadeView()
    {
        final CardView cardView=findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(200);
        alphaAnimation.setRepeatCount(3);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void shakeAnimation()
    {
        Animation animation= AnimationUtils.loadAnimation(this.getApplicationContext(),
                R.anim.shake_animation);
        final CardView cardView=findViewById(R.id.cardView);
        cardView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
              cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
