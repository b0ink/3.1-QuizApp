package com.example.quizapp;
import android.content.Context;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Quiz {
    ArrayList<Question> questions;

    public Quiz(Context context, String quizJsonFile){
        questions = new ArrayList<>();
        String json = JsonFileReader.loadJSONFromAsset(context, quizJsonFile);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray quizQuestions = jsonObject.getJSONArray("QuizQuestions");

            // Iterate through quiz questions
            for (int i = 0; i < quizQuestions.length(); i++) {
                JSONObject questionObject = quizQuestions.getJSONObject(i);
                String title = questionObject.getString("Title");
                JSONArray options = questionObject.getJSONArray("Options");
                String correctAnswer = questionObject.getString("CorrectAnswer");

                Question newQuestion = new Question(questionObject);
                questions.add(newQuestion);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void generateQuestions(String topic){

    }
}
