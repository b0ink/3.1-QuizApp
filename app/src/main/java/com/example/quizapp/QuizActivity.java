package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuizActivity extends AppCompatActivity {

    public Quiz quiz;

    public TextView tvName;
    public TextView tvQuizName;

    public TextView tvTitle;
    public TextView tvOption1;
    public TextView tvOption2;
    public TextView tvOption3;
    public TextView tvOption4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvName = findViewById(R.id.name);
        tvQuizName = findViewById(R.id.quizName);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String quizName = intent.getStringExtra(MainActivity.EXTRA_QUIZ_NAME);
        tvName.setText(name);
        tvQuizName.setText(quizName);


        Quiz newQuiz = new Quiz(this, "QuizQuestions.json");
        tvTitle = findViewById(R.id.questionTitle);
        tvOption1 = findViewById(R.id.questionOption1);
        tvOption2 = findViewById(R.id.questionOption2);
        tvOption3 = findViewById(R.id.questionOption3);
        tvOption4 = findViewById(R.id.questionOption4);

        int index = 0;
        tvTitle.setText(newQuiz.questions.get(index).title);
        tvOption1.setText(newQuiz.questions.get(index).wrongAnswers.get(0));
        tvOption2.setText(newQuiz.questions.get(index).wrongAnswers.get(1));
        tvOption3.setText(newQuiz.questions.get(index).wrongAnswers.get(2));
        tvOption4.setText(newQuiz.questions.get(index).correctAnswer);
    }
}