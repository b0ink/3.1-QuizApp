package com.example.quizapp;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
   public enum QuestionState{
        UNANSWERED,
        CORRECT,
        WRONG
    }
    public String title;
    public ArrayList<String> wrongAnswers;
    public String correctAnswer;

    public QuestionState guess;

    public Question(JSONObject data){
        wrongAnswers = new ArrayList<>();

//         TODO: ideally i dont wanna be doing any json work in here
        try{
            title = data.getString("Title");
            JSONArray options = data.getJSONArray("Options");
            correctAnswer = data.getString("CorrectAnswer");

            for(int i = 0; i<options.length();i++){
                if(options.getString(i).equals(correctAnswer)){
                    continue; // dont add correct answer to wrongAnswers array
                }
                wrongAnswers.add(options.getString(i));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        guess = QuestionState.UNANSWERED;

    }

}
