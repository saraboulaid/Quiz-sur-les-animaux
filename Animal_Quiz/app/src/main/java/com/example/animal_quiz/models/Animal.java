package com.example.animal_quiz.models;

public class Animal {
    private int id;
    private String name;
    private String imagePath;
    private String soundPath;
    private String diet;
    private int isMammal;
    private String habitat;

    public Animal(int id, String name, String imagePath, String soundPath, String diet, int isMammal, String habitat) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.diet = diet;
        this.isMammal = isMammal;
        this.habitat = habitat;
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
}
