package com.example.animal_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class VerifyAnswerActivity extends AppCompatActivity {

    private int questionIndex;
    private int questionsCount;
    private static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_answer);

        ImageView resultIcon = findViewById(R.id.result_icon);
        TextView resultMessage = findViewById(R.id.result_message);
        TextView animalInfo = findViewById(R.id.animal_info);

        // récupérer  les informations nécessaires, stockées dans l'intent, pour vérifier la réponse de l'enfant
        Intent intent = getIntent();
        questionIndex = intent.getIntExtra("question_index", 0);
        questionsCount = intent.getIntExtra("questions_count",10);
        String selectedAnswer = intent.getStringExtra("selected_answer");
        String correctAnswer = intent.getStringExtra("correct_answer");
        String description = intent.getStringExtra("description");

        //vérifier si la réponse est correcte
        if(selectedAnswer.equals(correctAnswer)){
            //si la réponse est correcte, nous félicitons l'enfant et nous affichons des informations supplimentaires sur l'animal
            resultIcon.setImageResource(R.drawable.ic_check);
            resultMessage.setText(R.string.reponse_correct);
            animalInfo.setText(description);
            score ++;
        } else {
            //si la réponse est incorrec, nous encourrageons l'enfant est nous affichons des informations supplimentaires sur l'animal
            resultIcon.setImageResource(R.drawable.ic_cross);
            resultMessage.setText(R.string.reponse_fausse);
            animalInfo.setText(description);
        }
    }

    public void nextQuestion(View view) {
        if(questionIndex == -1){
            score = 0;
            Intent intent = new Intent(VerifyAnswerActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            if(questionIndex < questionsCount) {
                Intent intent = new Intent(VerifyAnswerActivity.this, QuizActivity.class);
                intent.putExtra("question_index", questionIndex);
                startActivity(intent);
            }else{
                ImageView resultIcon = findViewById(R.id.result_icon);
                TextView resultMessage = findViewById(R.id.result_message);
                TextView animalInfo = findViewById(R.id.animal_info);
                Button next = findViewById(R.id.next_button);

                if(score > 5){
                    resultIcon.setImageResource(R.drawable.ic_congrat);
                    resultMessage.setText(R.string.congrat);
                }else{
                    resultIcon.setImageResource(R.drawable.ic_sad);
                    resultMessage.setText(R.string.sad);
                }
                animalInfo.setText(getString(R.string.score) + score);

                next.setText(R.string.revenir_accueil);
                questionIndex = -1;
            }
        }
    }
}