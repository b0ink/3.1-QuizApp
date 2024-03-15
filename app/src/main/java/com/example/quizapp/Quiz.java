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


    public int GetTotalQuestions(){
        return questions.size();

    }

    /**
     *
     * @param state match questions containing this state
     * @param isNotEqual if true, match questions that does not equal `state` param
     * @return Integer number of questions that match param conditions
     */
    public int GetQuestionsWithState(Question.QuestionState state, Boolean isNotEqual){
        int count = 0;
        for(Question q : questions){
            if((q.guess == state && !isNotEqual) || (q.guess != state && isNotEqual)){
                count ++;
            }
        }
        return count;
    }


    public void generateQuestions(String topic){

    }
}
