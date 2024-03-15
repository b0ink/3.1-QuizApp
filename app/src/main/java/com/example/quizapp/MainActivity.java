package com.example.quizapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    public TextView tvTitle;
    public TextView tvOption1;
    public TextView tvOption2;
    public TextView tvOption3;
    public TextView tvOption4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

