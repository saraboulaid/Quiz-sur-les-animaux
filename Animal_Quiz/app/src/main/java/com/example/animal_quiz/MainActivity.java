package com.example.animal_quiz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animal_quiz.utils.DatabaseHelper;
import com.example.animal_quiz.databinding.ActivityMainBinding;

/**
 * Classe principale de l'application qui représente l'activité principale.
 * Elle permet à l'utilisateur de naviguer vers différentes activités,
 * telles que le quiz ou l'activité des sons.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Création d'une instance de DatabaseHelper pour gérer la base de données.
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Initialisation et configuration du bouton "Commencer le quiz".
        Button startQuizButton = findViewById(R.id.start_quiz_button);

        /**
         * Définit une action lorsque l'utilisateur clique sur le bouton "Commencer le quiz".
         * L'action consiste à démarrer l'activité QuizActivity.
         */
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Méthode appelée lorsque le bouton "Commencer le quiz" est cliqué.
             *
             * @param view Vue associée au bouton cliqué.
             */
            @Override
            public void onClick(View view) {
                // Démarrage de l'activité QuizActivity.
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        // Initialisation et configuration du bouton "Commencer l'activité des sons".
        Button startSoundActivityButton = findViewById(R.id.start_sounds_activity_button);
        /**
         * Définit une action lorsque l'utilisateur clique sur le bouton "Commencer l'activité des sons".
         * L'action consiste à démarrer l'activité Sounds_Activity.
         */
        startSoundActivityButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Méthode appelée lorsque le bouton "Commencer l'activité des sons" est cliqué.
             *
             * @param view Vue associée au bouton cliqué.
             */
            @Override
            public void onClick(View view) {
                // Démarrage de l'activité Sounds_Activity.
                Intent intent = new Intent(MainActivity.this, Sounds_Activity.class);
                startActivity(intent);
            }
        });
    }
}