package com.example.animal_quiz.utils;

import com.example.animal_quiz.models.Animal;
import com.example.animal_quiz.models.Question;
import com.example.animal_quiz.models.QuestionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionGenerator {

    private DatabaseHelper dbHelper;

    public QuestionGenerator(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<Question> generateRandomQuestions(int questionCount) {
        List<Animal> animals = dbHelper.getAllAnimals();
        List<QuestionType> questionTypes = dbHelper.getAllQuestionTypes();

        if (animals.isEmpty() || questionTypes.isEmpty()) {
            return Collections.emptyList();
        }

        List<Question> questions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < questionCount; i++) {
            Animal animal = animals.get(random.nextInt(animals.size()));
            QuestionType questionType = questionTypes.get(random.nextInt(questionTypes.size()));

            String correctAnswer = animal.getFieldValue(questionType.getField());
            List<String> options = generateOptions(animals, correctAnswer, questionType.getField());
            questions.add(new Question(animal, questionType, options));
        }
        return questions;
    }

    private List<String> generateOptions(List<Animal> animals, String correctAnswer, String field) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        int distinctCount = dbHelper.getDistinctCount(field);

        Random random = new Random();
        while (options.size() < 4 && options.size() < distinctCount) { // Ajout de 3 distracteurs maximum
            Animal randomAnimal = animals.get(random.nextInt(animals.size()));
            String value = randomAnimal.getFieldValue(field);

            if (!options.contains(value)) {
                options.add(value);
            }
        }

        Collections.shuffle(options);
        return options;
    }
}
