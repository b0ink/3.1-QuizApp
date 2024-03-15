package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    public Quiz quiz;

    public TextView tvName;
    public TextView tvQuizName;

    public TextView tvTitle;
    public Button btnOption1;
    public Button btnOption2;
    public Button btnOption3;
    public Button btnOption4;
    public Button btnSubmit;

    public ProgressBar pbProgress;
    public TextView tvQuestionsAnswered;


    public Question activeQuestion;
    public Button selectedButton;
    public Button correctAnswer;

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


        quiz = new Quiz(this, "QuizQuestions.json");
        tvTitle = findViewById(R.id.questionTitle);
        btnOption1 = findViewById(R.id.questionOption1);
        btnOption2 = findViewById(R.id.questionOption2);
        btnOption3 = findViewById(R.id.questionOption3);
        btnOption4 = findViewById(R.id.questionOption4);
        btnSubmit = findViewById(R.id.quizNextQuestion);
        tvQuestionsAnswered = findViewById(R.id.questionsAnswered);
        pbProgress = findViewById(R.id.progressBar);

        setQuestion(0);

        View.OnClickListener onOptionSelect = v -> {
            if(activeQuestion.guess != Question.QuestionState.UNANSWERED){
                // already answered
                return;
            }

            resetButtonColors();
            selectedButton = (Button)v;
            ((Button)v).setTextColor(Color.parseColor("#FFFFFF"));
            v.setBackgroundColor(Color.parseColor("#145f99"));

            btnSubmit.setBackgroundColor(Color.parseColor("#145f99"));
            btnSubmit.setTextColor(Color.parseColor("#FFFFFF"));

            // not ideal
            activeQuestion.selectedAnswer = ((Button)v).getText().toString();
        };

        btnSubmit.setOnClickListener(v->{


            if(activeQuestion.guess != Question.QuestionState.UNANSWERED){
                int nextQuestionIndex = quiz.questions.indexOf(activeQuestion)+1;
                if(nextQuestionIndex > 0 && nextQuestionIndex < quiz.questions.size()){
                    // set new question
//                    activeQuestion = quiz.questions.get(nextQuestionIndex);
                    setQuestion(nextQuestionIndex);
                }else{
                    // assume end of the quiz, show results page
                    btnSubmit.setText("SEE RESULTS");
                }
                return;
            }

            if(selectedButton == null){
                return;
            }

            if(activeQuestion.selectedAnswer.isEmpty()){
                // TODO: toast "Please choose an answer"
                return;
            }

            resetButtonColors();
            highlightCorrectButton();

            if(!activeQuestion.isAnswerCorrect()){
                highlightSelectedWrongButton();
                activeQuestion.guess = Question.QuestionState.WRONG;
            }else{
                activeQuestion.guess = Question.QuestionState.CORRECT;
            }
            pbProgress.setProgress(pbProgress.getProgress()+1);


            btnSubmit.setText("NEXT QUESTION");
            btnSubmit.setBackgroundColor(Color.parseColor("#145f99"));
            btnSubmit.setTextColor(Color.parseColor("#FFFFFF"));

        });

        btnOption1.setOnClickListener(onOptionSelect);
        btnOption2.setOnClickListener(onOptionSelect);
        btnOption3.setOnClickListener(onOptionSelect);
        btnOption4.setOnClickListener(onOptionSelect);


    }

    public void setQuestion(int index){

        activeQuestion = quiz.questions.get(index);
        pbProgress.setProgress(index);
        pbProgress.setMax(quiz.questions.size());
        tvQuestionsAnswered.setText(index+1 + "/" + quiz.GetTotalQuestions());
        btnSubmit.setText("SUBMIT");
        ArrayList<String> questions = new ArrayList<>();


        for(String question : activeQuestion.answerOptions){
            questions.add(question);
        }
        Collections.shuffle(questions);
        Collections.shuffle(questions);
        Collections.shuffle(questions);

        int correctIndex = questions.indexOf(activeQuestion.correctAnswer);


        if(questions.size() < 4){
            // error;
            return;
        }

        // randomise list

        tvTitle.setText(activeQuestion.title);

        btnOption1.setText(questions.remove(0));
        btnOption2.setText(questions.remove(0));
        btnOption3.setText(questions.remove(0));
        btnOption4.setText(questions.remove(0));

        //TODO: put btnOption's in an array
        switch(correctIndex+1){
            case 1: correctAnswer = btnOption1;break;
            case 2: correctAnswer = btnOption2;break;
            case 3: correctAnswer = btnOption3;break;
            case 4: correctAnswer = btnOption4;break;
        }

        selectedButton = null;

        resetButtonColors();

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

        // Disabled color

        btnSubmit.setBackgroundColor(Color.parseColor("#636b70"));
        btnSubmit.setTextColor(Color.parseColor("#2a2f33"));
    }

    public void highlightCorrectButton(){
        // green
        correctAnswer.setBackgroundColor(Color.parseColor("#00ff51"));
    }
    public void highlightSelectedWrongButton(){
        //red
        selectedButton.setBackgroundColor(Color.parseColor("#cf2200"));
        selectedButton.setTextColor(Color.parseColor("#FFFFFF"));
    }
}