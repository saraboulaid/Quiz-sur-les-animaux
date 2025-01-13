package com.example.animal_quiz;

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
