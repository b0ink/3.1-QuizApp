package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
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
    public ProgressBar pbResults;

    private Handler handler = new Handler();
    int pStatus = 0;
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

        tvResults = findViewById(R.id.results_percentage);
        pbResults = findViewById(R.id.resultsProgressBar);


        int actualResults = (int)(((double)correctQuestions/(double)totalQuestions) * 100.0);
        pbResults.setProgress(0);
        pbResults.setMax(100);
        tvResults.setText("");

//        https://stackoverflow.com/questions/21333866/how-to-create-a-circular-progressbar-in-android-which-rotates-on-it
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Sleep for 200 millisecondsz
                    // Just to display the progress slowly
                    Thread.sleep(1000); //thread will take approx 1.5 seconds to finish
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // TODO Auto-generated method stub
                while (pStatus < actualResults) {
                    pStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbResults.setProgress(pStatus);
                            tvResults.setText(pStatus + "%");
                        }
                    });
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}