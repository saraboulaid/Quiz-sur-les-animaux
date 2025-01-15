package com.example.animal_quiz.models;

/**
 * Définit un type de question dans le quiz.
 * Contient le texte de la question et le champ de données de l'animal auquel elle se réfère.
 */
public class QuestionType {
    private String questionText;
    private String field;

    public QuestionType(String questionText, String field) {
        this.questionText = questionText;
        this.field = field;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getField() {
        return field;
    }
}
