package com.example.animal_quiz.models;

/**
 * Représente un animal avec ses caractéristiques principales.
 * Contient des informations telles que le nom, l'image, le son, le régime alimentaire,
 * mammifère ou non, et son habitat.
 */
public class Animal {
    private int id;
    private String name;
    private String imagePath;
    private String soundPath;
    private String diet;
    private int isMammal;
    private String habitat;
    private String description;

    public Animal(int id, String name, String imagePath, String soundPath, String diet, int isMammal, String habitat, String description) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.diet = diet;
        this.isMammal = isMammal;
        this.habitat = habitat;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public String getDiet() {
        return diet;
    }

    public int isMammal() {
        return isMammal;
    }

    public String getHabitat() {
        return habitat;
    }

    public String getDescription(){
        return description;
    }

    public String getFieldValue(String field) {
        switch (field) {
            case "name": return this.getName();
            case "diet": return this.getDiet();
            case "isMammal": return this.isMammal() == 1 ? "Oui" : "Non";
            case "habitat": return this.getHabitat();
            default: return "";
        }
    }
}

