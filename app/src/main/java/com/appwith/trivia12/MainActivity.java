package com.appwith.trivia12;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.appwith.trivia12.data.CallBackAnswerListAsynchTask;
import com.appwith.trivia12.data.Repository;
import com.appwith.trivia12.databinding.ActivityMainBinding;
import com.appwith.trivia12.model.Question;
import com.appwith.trivia12.model.Score;
import com.appwith.trivia12.util.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static com.appwith.trivia12.R.anim.shake_animation;
import static com.appwith.trivia12.R.id.about;
import static com.appwith.trivia12.R.id.cardView;
import static com.appwith.trivia12.R.id.share_menu;
import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {
    private Button shareBtn;
    private ActivityMainBinding binding;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private Score score = new Score();
    private int scoreCounter = 0;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        prefs = new Prefs(this);
        currentQuestionIndex = prefs.getQuestionIndex();
        questionList = new Repository().getQuestions(new CallBackAnswerListAsynchTask() {
            @Override
            public void isProcessFinished(List<Question> questions) {
                binding.questionText.setText(questions.get(currentQuestionIndex).getQuestion());
                binding.questionOutOf.setText(format(getString(R.string.question_out_of_data), currentQuestionIndex, questionList.size()));
                binding.score.setText(format(getString(R.string.score), score.getScore()));
            }
        });
        binding.highestScore.setText(String.format(getString(R.string.highest_score), prefs.getHighestScore()));
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
            }
        });

        binding.falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        binding.trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        binding.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backwardUpdateQuestion();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case about:
                Toast.makeText(this, R.string.about_text, Toast.LENGTH_LONG).show();
                return true;
            case share_menu:
                shareScore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void shareScore() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "I am playing Trivia");
        String msg = "My current score: " + score.getScore() + "\n" + "My highest score: " + prefs.getHighestScore() + "\n\n" +
                "Wanna play ? Download from : https://github.com/nareshvishram/Todoister-App";
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(intent);
    }

    private void backwardUpdateQuestion() {
        currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
        if (currentQuestionIndex < 0)
            currentQuestionIndex = 0;
        binding.questionText.setText(questionList.get(currentQuestionIndex).getQuestion());
        binding.questionOutOf.setText(format(getString(R.string.question_out_of_data), currentQuestionIndex, questionList.size()));
    }

    public void changeQuestion() {
        binding.questionText.setText(questionList.get(currentQuestionIndex).getQuestion());
    }

    private void updateQuestion() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        binding.questionText.setText(questionList.get(currentQuestionIndex).getQuestion());
        binding.questionOutOf.setText(format(getString(R.string.question_out_of_data), currentQuestionIndex, questionList.size()));
    }

    private void checkAnswer(boolean b) {
        String text;
        if (b == questionList.get(currentQuestionIndex).isAnswerTrue()) {
            text = "Correct";
            changeQuestion();
            fadeAnimation();
            addScore();
        } else {
            text = "Wrong";
            shakeAnimation();
            changeQuestion();
            deductScore();
        }

        Snackbar.make(binding.cardView, text, Snackbar.LENGTH_LONG).show();

    }

    private void deductScore() {
        scoreCounter -= 100;
        if (scoreCounter < 0) {
            scoreCounter = 0;
        }
        score.setScore(scoreCounter);
        binding.score.setText(format(getString(R.string.score), score.getScore()));
    }

    private void addScore() {
        scoreCounter += 100;
        score.setScore(scoreCounter);
        Log.d("score", "addScore: " + score.getScore());
        binding.score.setText(format(getString(R.string.score), score.getScore()));


    }


    //shake animations for every wrong answers with red text color of the question
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
                updateQuestion();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //fade animations for every correct answers with green text color of the question
    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatMode(AlphaAnimation.REVERSE);
        alphaAnimation.setRepeatCount(2);

        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //best way to save data in the android is in the onPause state
    //save maximum score and question index in shared preferences
    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.saveQuestionIndex(currentQuestionIndex);
        Log.d("high_score_in_on_pause", "onPause: " + prefs.getHighestScore());
        super.onPause();
    }
}
