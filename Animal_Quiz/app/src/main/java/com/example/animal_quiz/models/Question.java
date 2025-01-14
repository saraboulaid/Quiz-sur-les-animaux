package com.example.animal_quiz.models;

import java.util.List;

public class Question {
    private Animal animal;
    private String questionText;
    private List<String> options;

    public Question(Animal animal, String questionText, List<String> options) {
        this.animal = animal;
        this.questionText = questionText;
        this.options = options;
    }

    public Animal getAnimal() {
        return animal;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }
}
