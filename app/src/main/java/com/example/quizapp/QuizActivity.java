package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    public Button btnOption1;
    public Button btnOption2;
    public Button btnOption3;
    public Button btnOption4;


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
        tvName.setText("Welcome, " + name + "!");
        tvQuizName.setText(quizName);


        Quiz newQuiz = new Quiz(this, "QuizQuestions.json");
        tvTitle = findViewById(R.id.questionTitle);
        btnOption1 = findViewById(R.id.questionOption1);
        btnOption2 = findViewById(R.id.questionOption2);
        btnOption3 = findViewById(R.id.questionOption3);
        btnOption4 = findViewById(R.id.questionOption4);

        int index = 0;
        tvTitle.setText(newQuiz.questions.get(index).title);
        btnOption1.setText(newQuiz.questions.get(index).wrongAnswers.get(0));
        btnOption2.setText(newQuiz.questions.get(index).wrongAnswers.get(1));
        btnOption3.setText(newQuiz.questions.get(index).wrongAnswers.get(2));
        btnOption4.setText(newQuiz.questions.get(index).correctAnswer);

        View.OnClickListener onOptionSelect = v -> {
            resetButtonColors();

            ((Button)v).setTextColor(Color.parseColor("#E3EDF4"));
            v.setBackgroundColor(Color.parseColor("#214E71"));
        };

        btnOption1.setOnClickListener(onOptionSelect);
        btnOption2.setOnClickListener(onOptionSelect);
        btnOption3.setOnClickListener(onOptionSelect);
        btnOption4.setOnClickListener(onOptionSelect);


    }

    public void resetButtonColors(){
        btnOption1.setBackgroundColor(Color.parseColor("#E3EDF4"));
        btnOption2.setBackgroundColor(Color.parseColor("#E3EDF4"));
        btnOption3.setBackgroundColor(Color.parseColor("#E3EDF4"));
        btnOption4.setBackgroundColor(Color.parseColor("#E3EDF4"));
        btnOption1.setTextColor(Color.parseColor("#214E71"));
        btnOption2.setTextColor(Color.parseColor("#214E71"));
        btnOption3.setTextColor(Color.parseColor("#214E71"));
        btnOption4.setTextColor(Color.parseColor("#214E71"));
    }
}