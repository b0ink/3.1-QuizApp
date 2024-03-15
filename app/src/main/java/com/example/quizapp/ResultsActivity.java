package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultsActivity extends AppCompatActivity {

    public TextView tvResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int totalQuestions  = intent.getIntExtra(QuizActivity.EXTRA_TOTAL_QUESTIONS, -1);
        int wrongQuestions  = intent.getIntExtra(QuizActivity.EXTRA_WRONG_QUESTIONS, -1);
        int correctQuestions  = intent.getIntExtra(QuizActivity.EXTRA_CORRECT_QUESTIONS, -1);

        tvResults = findViewById(R.id.result);
        tvResults.setText("You got " + correctQuestions + "/"+totalQuestions + " questions correct!");

    }
}