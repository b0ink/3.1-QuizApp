package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    public EditText etName;
    public Button btnStartQuiz;
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_QUIZ_NAME = "quiz_name";

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

        etName = findViewById(R.id.name);
        btnStartQuiz = findViewById(R.id.startQuiz);
        btnStartQuiz.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String quizName = "TODO";

            if (name.isEmpty()) {
                //TODO; toast message "please enter your name first"
                return;
            }
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
//            doConversion(true)
            intent.putExtra(EXTRA_NAME, name);
            intent.putExtra(EXTRA_QUIZ_NAME, quizName);
            startActivity(intent);
        });

    }
}

