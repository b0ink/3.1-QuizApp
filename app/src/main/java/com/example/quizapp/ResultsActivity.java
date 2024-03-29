package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;

public class ResultsActivity extends AppCompatActivity {

    public TextView tvResults;
    public TextView tvTitle;
    public ProgressBar pbResults;

    public Button btnNewQuiz;
    public Button btnFinish;

    private final Handler handler = new Handler();
    int pStatus = 0;
    int pCount = 0;

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
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        int totalQuestions = intent.getIntExtra(QuizActivity.EXTRA_TOTAL_QUESTIONS, -1);
//        int wrongQuestions = intent.getIntExtra(QuizActivity.EXTRA_WRONG_QUESTIONS, -1);
        int correctQuestions = intent.getIntExtra(QuizActivity.EXTRA_CORRECT_QUESTIONS, -1);

        tvTitle = findViewById(R.id.title_name);
        tvTitle.setText("Congratulations,\n" + name + "!");

        tvResults = findViewById(R.id.results_percentage);
        pbResults = findViewById(R.id.resultsProgressBar);

        btnNewQuiz = findViewById(R.id.button_new_quiz);
        btnFinish = findViewById(R.id.button_finish);


        int actualResults = (int) (((double) correctQuestions / (double) totalQuestions) * 100.0);
        pbResults.setProgress(0);
        pbResults.setMax(100);

//        tvResults.setText("");
        tvResults.setText("0/" + totalQuestions);

        btnNewQuiz.setOnClickListener(v -> {
            if (pStatus < actualResults) return;
            Intent mainIntent = new Intent(ResultsActivity.this, MainActivity.class);
            mainIntent.putExtra(MainActivity.EXTRA_NAME, name);
            finish();
            startActivity(mainIntent);
        });

        btnFinish.setOnClickListener(v -> {
            if (pStatus < actualResults) return;
            System.exit(0);
            finish();
        });

//        https://stackoverflow.com/questions/21333866/how-to-create-a-circular-progressbar-in-android-which-rotates-on-it
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double msDelay = 1500.0 / (double) actualResults;

            while (pStatus < actualResults) {
                pStatus += 1;
                if (pStatus % Math.floor(((1.0 / ((double) totalQuestions)) * 100)) == 0) {
                    pCount++;
                    if (pCount > correctQuestions) {
                        pCount = correctQuestions;
                    }
                }
                handler.post(() -> {
                    pbResults.setProgress(pStatus);
//                            tvResults.setText(pStatus + "%");
                    tvResults.setText(pCount + "/" + totalQuestions);
                });
                try {
                    Thread.sleep((int) msDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}