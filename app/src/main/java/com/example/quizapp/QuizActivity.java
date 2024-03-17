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
import androidx.core.content.ContextCompat;

public class QuizActivity extends AppCompatActivity {

    public Quiz quiz;

    public TextView tvName;
    public TextView tvQuizName;

    public TextView tvTitle;
    public Button btnOption1;
    public Button btnOption2;
    public Button btnOption3;
    public Button btnOption4;

    public Button[] btnOptions;
    public Button btnSubmit;

    public ProgressBar pbProgress;
    public TextView tvQuestionsAnswered;


    public Question activeQuestion;
    public Button selectedButton;
    public Button correctAnswer;

    public static final String EXTRA_TOTAL_QUESTIONS = "total_questions";
    public static final String EXTRA_CORRECT_QUESTIONS = "correct_questions";
    public static final String EXTRA_WRONG_QUESTIONS = "wrong_questions";


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

        btnOptions = new Button[4];
        quiz = new Quiz(this, "QuizQuestions.json");
        tvTitle = findViewById(R.id.questionTitle);
        btnOption1 = findViewById(R.id.questionOption1);
        btnOption2 = findViewById(R.id.questionOption2);
        btnOption3 = findViewById(R.id.questionOption3);
        btnOption4 = findViewById(R.id.questionOption4);
        btnOptions[0] = btnOption1;
        btnOptions[1] = btnOption2;
        btnOptions[2] = btnOption3;
        btnOptions[3] = btnOption4;

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

            ((Button)v).setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_selected));
            ((Button)v).setTextColor(ContextCompat.getColor(this, R.color.button_text_selected));

            btnSubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_submit));
            btnSubmit.setTextColor(ContextCompat.getColor(this, R.color.button_text_submit));


            // not ideal
            activeQuestion.selectedAnswer = ((Button)v).getText().toString();
        };

        btnSubmit.setOnClickListener(v->{
            if(((Button)v).getText().equals("SEE RESULTS")){
                Intent resultsIntent = new Intent(QuizActivity.this, ResultsActivity.class);
                resultsIntent.putExtra(EXTRA_TOTAL_QUESTIONS, quiz.GetTotalQuestions());
                resultsIntent.putExtra(EXTRA_WRONG_QUESTIONS, quiz.GetQuestionsWithState(Question.QuestionState.WRONG, false));
                resultsIntent.putExtra(EXTRA_CORRECT_QUESTIONS, quiz.GetQuestionsWithState(Question.QuestionState.CORRECT, false));
                startActivity(resultsIntent);
                return;
            }

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
            if(quiz.questions.indexOf(activeQuestion)+1 == quiz.questions.size()){
                btnSubmit.setText("SEE RESULTS");
            }


            //TODO: make highlightSubmitButton() etc
            btnSubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_submit));
            btnSubmit.setTextColor(ContextCompat.getColor(this, R.color.button_text_submit));


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
        for (Button btnOption : btnOptions) {
            btnOption.setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_default));
            btnOption.setTextColor(ContextCompat.getColor(this, R.color.button_text_default));
        }
        // Disabled color
        btnSubmit.setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_disabled));
        btnSubmit.setTextColor(ContextCompat.getColor(this, R.color.button_text_disabled));
    }


    public void highlightCorrectButton(){
        // green
        correctAnswer.setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_correct));
        correctAnswer.setTextColor(ContextCompat.getColor(this, R.color.button_text_correct));
    }
    public void highlightSelectedWrongButton(){
        //red
        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_background_wrong));
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.button_text_wrong));
    }
}