package com.example.animal_quiz.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.animal_quiz.models.Animal;
import com.example.animal_quiz.models.QuestionType;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "animalQuiz.db";
    private static final int DATABASE_VERSION = 1;

    // Table Animals
    private static final String TABLE_ANIMALS = "Animals";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE_PATH = "imagePath";
    private static final String COLUMN_SOUND_PATH = "soundPath";
    private static final String COLUMN_DIET = "diet";
    private static final String COLUMN_IS_MAMMAL = "isMammal";
    private static final String COLUMN_HABITAT = "habitat";

    // Table QuestionTypes
    private static final String TABLE_QUESTION_TYPES = "QuestionTypes";
    private static final String COLUMN_QUESTION_ID = "id";
    private static final String COLUMN_QUESTION_TEXT = "questionText";
    private static final String COLUMN_FIELD = "field";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table Animals
        String CREATE_ANIMALS_TABLE = "CREATE TABLE " + TABLE_ANIMALS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_IMAGE_PATH + " TEXT NOT NULL, " +
                COLUMN_SOUND_PATH + " TEXT NOT NULL, " +
                COLUMN_DIET + " TEXT NOT NULL, " +
                COLUMN_IS_MAMMAL + " INTEGER NOT NULL, " +
                COLUMN_HABITAT + " TEXT NOT NULL)";
        db.execSQL(CREATE_ANIMALS_TABLE);

        // Création de la table QuestionTypes
        String CREATE_QUESTION_TYPES_TABLE = "CREATE TABLE " + TABLE_QUESTION_TYPES + " (" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_TEXT + " TEXT NOT NULL, " +
                COLUMN_FIELD + " TEXT NOT NULL)";
        db.execSQL(CREATE_QUESTION_TYPES_TABLE);

        // Remplir les tables avec des données initiales
        seedDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_TYPES);
        onCreate(db);
    }

    private void seedDatabase(SQLiteDatabase db) {
        // Ajouter 10 animaux
        addAnimal(db, "Lion", "lion.png", "lion_roar.mp3", "Carnivore", 1, "Savane");
        addAnimal(db, "Loup", "wolf.png", "wolf_howl.mp3", "Carnivore", 1, "Forêt");
        addAnimal(db, "Dauphin", "dolphin.png", "dolphin_sound.mp3", "Carnivore", 1, "Océan");
        addAnimal(db, "Éléphant", "elephant.png", "elephant_trumpet.mp3", "Herbivore", 1, "Savane");
        addAnimal(db, "Aigle", "eagle.png", "eagle_screech.mp3", "Carnivore", 0, "Montagnes");
        addAnimal(db, "Tigre", "tiger.png", "tiger_roar.mp3", "Carnivore", 1, "Forêt");
        addAnimal(db, "Perroquet", "parrot.png", "parrot_chirp.mp3", "Herbivore", 0, "Forêt tropicale");
        addAnimal(db, "Lapin", "rabbit.png", "rabbit_sound.mp3", "Herbivore", 1, "Forêt");
        addAnimal(db, "Panda", "panda.png", "panda_sound.mp3", "Herbivore", 1, "Forêt");
        addAnimal(db, "Grenouille", "frog.png", "frog_croak.mp3", "Herbivore", 0, "Marais");

        // Ajouter des types de questions
        addQuestionType(db, "Quel est le nom de cet animal ?", "name");
        addQuestionType(db, "Quel est le régime alimentaire de cet animal ?", "diet");
        addQuestionType(db, "Cet animal est-il un mammifère ?", "isMammal");
        addQuestionType(db, "Où vit cet animal ?", "habitat");
    }

    private void addAnimal(SQLiteDatabase db, String name, String imagePath, String soundPath, String diet, int isMammal, String habitat) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_SOUND_PATH, soundPath);
        values.put(COLUMN_DIET, diet);
        values.put(COLUMN_IS_MAMMAL, isMammal);
        values.put(COLUMN_HABITAT, habitat);
        db.insert(TABLE_ANIMALS, null, values);
    }

    private void addQuestionType(SQLiteDatabase db, String questionText, String field) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_TEXT, questionText);
        values.put(COLUMN_FIELD, field);
        db.insert(TABLE_QUESTION_TYPES, null, values);
    }

    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ANIMALS, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));
                String soundPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOUND_PATH));
                String diet = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIET));
                int isMammal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_MAMMAL));
                String habitat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HABITAT));

                animals.add(new Animal(id, name, imagePath, soundPath, diet, isMammal, habitat));
            }
            cursor.close();
        }
        return animals;
    }

    public List<QuestionType> getAllQuestionTypes() {
        List<QuestionType> questionTypes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUESTION_TYPES, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String questionText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION_TEXT));
                String field = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIELD));

                questionTypes.add(new QuestionType(questionText, field));
            }
            cursor.close();
        }
        return questionTypes;
    }

    public int getDistinctCount(String field) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(DISTINCT " + field + ") AS distinctCount FROM " + TABLE_ANIMALS;
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(cursor.getColumnIndexOrThrow("distinctCount"));
            }
            cursor.close();
        }
        return count;
    }

}
