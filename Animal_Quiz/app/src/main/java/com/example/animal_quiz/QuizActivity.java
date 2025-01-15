package com.example.animal_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animal_quiz.models.Animal;
import com.example.animal_quiz.models.Question;
import com.example.animal_quiz.utils.DatabaseHelper;
import com.example.animal_quiz.utils.QuestionGenerator;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private ImageView animalImage;
    private TextView questionText;
    private RadioGroup answerOptions;
    private Button nextButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private final int questionsCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        animalImage = findViewById(R.id.animal_image);
        questionText = findViewById(R.id.question_text);
        answerOptions = findViewById(R.id.answer_options);
        nextButton = findViewById(R.id.next_button);

        // Initialiser les questions
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        QuestionGenerator questionGenerator = new QuestionGenerator(dbHelper);
        questions = questionGenerator.generateRandomQuestions(10);

        if (questions.isEmpty()) {
            Toast.makeText(this, "Aucune question disponible", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Récupérer l'index actuel de la question depuis VerifyAnswerActivity
            currentQuestionIndex = getIntent().getIntExtra("question_index", 0);
            loadQuestion();
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si une réponse a été sélectionnée
                int selectedId = answerOptions.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(QuizActivity.this, "Veuillez sélectionner une réponse.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Obtenir la réponse sélectionnée
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();

                // Obtenir la question actuelle
                Question currentQuestion = questions.get(currentQuestionIndex);

                String field = currentQuestion.getQuestionType().getField();
                Animal animal = currentQuestion.getAnimal();
                //récuperer la réponse correcte
                String correctAnswer = animal.getFieldValue(field);

                // Passer à l'activité VerifyAnswerActivity
                Intent intent = new Intent(QuizActivity.this, VerifyAnswerActivity.class);

                intent.putExtra("question_index", currentQuestionIndex +1);
                intent.putExtra("correct_answer", correctAnswer);
                intent.putExtra("selected_answer", selectedAnswer);
                intent.putExtra("questions_Count", questionsCount);
                intent.putExtra("description", animal.getDescription());

                startActivity(intent);
            }
        });
    }

    private void loadQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Charger l'image
        String imagePath = currentQuestion.getAnimal().getImagePath();
        int imageResId = getResources().getIdentifier(imagePath.replace(".png", ""), "drawable", getPackageName());
        if (imageResId != 0) {
            animalImage.setImageResource(imageResId);
        } else {
            Toast.makeText(this, "Image introuvable pour : " + imagePath, Toast.LENGTH_SHORT).show();
        }

        // Charger la question
        questionText.setText(currentQuestion.getQuestionText());

        // Charger les options de réponse
        answerOptions.removeAllViews();
        List<String> options = currentQuestion.getOptions();
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            radioButton.setTextSize(16);
            radioButton.setPadding(8, 8, 8, 8);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            ));
            ((RadioGroup.LayoutParams) radioButton.getLayoutParams()).setMargins(0, 8, 0, 8);
            radioButton.setGravity(android.view.Gravity.START);

            answerOptions.addView(radioButton);
        }
    }
}