package com.example.animal_quiz.models;

import java.util.List;

public class Question {
    private Animal animal;
    private QuestionType questionType;
    private List<String> options;

    public Question(Animal animal, QuestionType questionType, List<String> options) {
        this.animal = animal;
        this.questionType = questionType;
        this.options = options;
    }

    public Animal getAnimal() {
        return animal;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public List<String> getOptions() {
        return options;
    }
}
