package com.example.animal_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité qui permet de vérifier la réponse de l'utilisateur pour une question donnée.
 * Affiche si la réponse est correcte ou incorrecte, des informations sur l'animal, et permet
 * de passer à la question suivante ou d'afficher un résumé du quiz à la fin.
 */
public class VerifyAnswerActivity extends AppCompatActivity {

    // Index de la question actuelle
    private int questionIndex;

    // Nombre total de questions dans le quiz
    private int questionsCount;

    // Score total de l'utilisateur
    private static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_answer);

        // Composants de l'interface utilisateur
        ImageView resultIcon = findViewById(R.id.result_icon);
        TextView resultMessage = findViewById(R.id.result_message);
        TextView animalInfo = findViewById(R.id.animal_info);

        // récupérer  les informations nécessaires à partir de l'intent
        Intent intent = getIntent();
        questionIndex = intent.getIntExtra("question_index", 0);
        questionsCount = intent.getIntExtra("questions_count",10);
        String selectedAnswer = intent.getStringExtra("selected_answer");
        String correctAnswer = intent.getStringExtra("correct_answer");
        String description = intent.getStringExtra("description");

        //vérifier si la réponse est correcte
        if(selectedAnswer.equals(correctAnswer)){
            // Si la réponse est correcte, afficher un message de félicitations et des informations supplémentaires
            resultIcon.setImageResource(R.drawable.ic_check);
            resultMessage.setText(R.string.reponse_correct);
            animalInfo.setText(description);
            score ++;
        } else {
            // Si la réponse est incorrecte, afficher un message d'encouragement et des informations supplémentaires
            resultIcon.setImageResource(R.drawable.ic_cross);
            resultMessage.setText(R.string.reponse_fausse);
            animalInfo.setText(description);
        }
    }

    /**
     * Méthode appelée lorsque l'utilisateur clique sur le bouton "Suivant".
     * Passe à la question suivante ou affiche le résumé du quiz si toutes les questions ont été répondues.
     *
     * @param view La vue associée au bouton cliqué.
     */
    public void nextQuestion(View view) {
        // Si l'utilisateur a terminé le quiz, réinitialiser le score et retourner à l'accueil
        if(questionIndex == -1){
            score = 0;
            Intent intent = new Intent(VerifyAnswerActivity.this, MainActivity.class);
            startActivity(intent);
        }else{
            if(questionIndex < questionsCount) {
                // Passer à la question suivante
                Intent intent = new Intent(VerifyAnswerActivity.this, QuizActivity.class);
                intent.putExtra("question_index", questionIndex);
                startActivity(intent);
            }else{
                // Résumé du quiz
                ImageView resultIcon = findViewById(R.id.result_icon);
                TextView resultMessage = findViewById(R.id.result_message);
                TextView animalInfo = findViewById(R.id.animal_info);
                Button next = findViewById(R.id.next_button);

                if(score >= 5){
                    // Afficher un message de félicitations si le score est supérieur pu égal à 5
                    resultIcon.setImageResource(R.drawable.ic_congrat);
                    resultMessage.setText(R.string.congrat);
                }else{
                    // Afficher un message d'encouragement si le score est inférieur ou égal à 5
                    resultIcon.setImageResource(R.drawable.ic_sad);
                    resultMessage.setText(R.string.sad);
                }
                // Afficher le score final
                animalInfo.setText(getString(R.string.score) + score);

                // Modifier le texte du bouton par revenir à l'accueil
                next.setText(R.string.revenir_accueil);
                questionIndex = -1;
            }
        }
    }
}