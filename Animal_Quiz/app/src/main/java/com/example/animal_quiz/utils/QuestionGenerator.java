package com.example.animal_quiz.utils;

import com.example.animal_quiz.models.Animal;
import com.example.animal_quiz.models.Question;
import com.example.animal_quiz.models.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Classe responsable de la génération de questions aléatoires pour le quiz sur les animaux.
 */
public class QuestionGenerator {

    private DatabaseHelper dbHelper; // Instance du gestionnaire de base de données

    /**
     * Constructeur : Initialise le générateur avec un accès à la base de données.
     * @param dbHelper L'instance de DatabaseHelper utilisée pour accéder aux données.
     */
    public QuestionGenerator(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * Génère une liste de questions aléatoires.
     * @param questionCount Le nombre de questions à générer.
     * @return Une liste de questions, ou une liste vide si aucune donnée n'est disponible.
     */
    public List<Question> generateRandomQuestions(int questionCount) {
        // Récupération des données de la base
        List<Animal> animals = dbHelper.getAllAnimals();
        List<QuestionType> questionTypes = dbHelper.getAllQuestionTypes();

        // Vérification que les données nécessaires existent et retourne une liste vide si ce n'est pas le cas
        if (animals.isEmpty() || questionTypes.isEmpty()) {
            return Collections.emptyList();
        }

        List<Question> questions = new ArrayList<>();
        Random random = new Random();

        // Génération des questions
        for (int i = 0; i < questionCount; i++) {
            // Sélection aléatoire d'un animal et d'un type de question
            Animal animal = animals.get(random.nextInt(animals.size()));
            QuestionType questionType = questionTypes.get(random.nextInt(questionTypes.size()));

            // Récupération de la réponse correcte en fonction du champ
            String correctAnswer = animal.getFieldValue(questionType.getField());

            // Génération des options de réponse (1 correcte + 3 distracteurs maximum)
            List<String> options = generateOptions(animals, correctAnswer, questionType.getField());

            // Ajout de la question à la liste
            questions.add(new Question(animal, questionType, options));
        }
        return questions;
    }

    /**
     * Génère une liste d'options de réponse pour une question.
     * @param animals La liste d'animaux disponibles.
     * @param correctAnswer La réponse correcte.
     * @param field Le champ utilisé pour les réponses.
     * @return Une liste mélangée contenant la réponse correcte et des distracteurs.
     */
    private List<String> generateOptions(List<Animal> animals, String correctAnswer, String field) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer); // Ajout de la réponse correcte

        int distinctCount = dbHelper.getDistinctCount(field); // Nombre de valeurs distinctes pour ce champ

        Random random = new Random();
        // Ajout de distracteurs aléatoires (jusqu'à 3 maximum)
        while (options.size() < 4 && options.size() < distinctCount) {
            Animal randomAnimal = animals.get(random.nextInt(animals.size()));
            String value = randomAnimal.getFieldValue(field);

            if (!options.contains(value)) { // Évite les doublons dans les options
                options.add(value);
            }
        }

        Collections.shuffle(options); // Mélange les options pour éviter un ordre prévisible
        return options;
    }
}
