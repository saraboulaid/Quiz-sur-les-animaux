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

/**
 * Classe helper pour gérer la base de données SQLite utilisée dans l'application.
 * Permet de gérer les tables "Animals" et "QuestionTypes", ainsi que leurs données associées.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom de la base de données et sa version
    private static final String DATABASE_NAME = "animalQuiz.db";
    private static final int DATABASE_VERSION = 1;

    // Définition des constantes pour la table Animals
    private static final String TABLE_ANIMALS = "Animals";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE_PATH = "imagePath";
    private static final String COLUMN_SOUND_PATH = "soundPath";
    private static final String COLUMN_DIET = "diet";
    private static final String COLUMN_IS_MAMMAL = "isMammal";
    private static final String COLUMN_HABITAT = "habitat";
    private static final String COLUMN_DESCRIPTION = "description";

    // Définition des constantes pour la table QuestionTypes
    private static final String TABLE_QUESTION_TYPES = "QuestionTypes";
    private static final String COLUMN_QUESTION_ID = "id";
    private static final String COLUMN_QUESTION_TEXT = "questionText";
    private static final String COLUMN_FIELD = "field";

    // Constructeur : Initialise la base de données
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
                COLUMN_HABITAT + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL)";
        db.execSQL(CREATE_ANIMALS_TABLE);

        // Création de la table QuestionTypes
        String CREATE_QUESTION_TYPES_TABLE = "CREATE TABLE " + TABLE_QUESTION_TYPES + " (" +
                COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_TEXT + " TEXT NOT NULL, " +
                COLUMN_FIELD + " TEXT NOT NULL)";
        db.execSQL(CREATE_QUESTION_TYPES_TABLE);

        // Remplissage des tables avec des données initiales
        seedDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression des tables existantes lors d'une mise à jour de la version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_TYPES);
        onCreate(db); // Recréation des tables
    }

    /**
     * Remplissage initial des tables avec des données par défaut.
     */
    private void seedDatabase(SQLiteDatabase db) {
        // Ajouter 10 animaux
        addAnimal(db, "Lion", "lion.png", "lion_roar.mp3", "Carnivore", 1, "Savane",
                "Le lion est appelé le roi des animaux. Il vit dans la savane avec sa famille appelée meute. Son rugissement peut s'entendre à plusieurs kilomètres.");
        addAnimal(db, "Loup", "wolf.png", "wolf_howl.mp3", "Carnivore", 1, "Forêt",
                "Le loup vit en meute et chasse en groupe. Il communique en hurlant la nuit. Il est très intelligent et rapide.");
        addAnimal(db, "Dauphin", "dolphin.png", "dolphin_sound.mp3", "Carnivore", 1, "Océan",
                "Le dauphin est un mammifère marin qui adore jouer. Il saute hors de l'eau et nage très vite. Il utilise des sons pour parler avec ses amis.");
        addAnimal(db, "Éléphant", "elephant.png", "elephant_trumpet.mp3", "Herbivore", 1, "Savane",
                "L'éléphant est le plus grand animal terrestre. Il a une longue trompe pour boire et ramasser de la nourriture. Il aime vivre en groupe.");
        addAnimal(db, "Aigle", "eagle.png", "eagle_screech.mp3", "Carnivore", 0, "Montagnes",
                "L'aigle est un oiseau majestueux qui vole haut dans le ciel. Il a une vue perçante pour repérer ses proies. Il vit souvent dans les montagnes.");
        addAnimal(db, "Tigre", "tiger.png", "tiger_roar.mp3", "Carnivore", 1, "Forêt",
                "Le tigre a un pelage rayé unique pour se cacher dans la forêt. Il est un excellent chasseur et adore nager. Il vit souvent seul.");
        addAnimal(db, "Perroquet", "parrot.png", "parrot_chirp.mp3", "Herbivore", 0, "Forêt tropicale",
                "Le perroquet a des plumes colorées et peut imiter les voix. Il vit dans la forêt tropicale et mange des fruits. C'est un oiseau très sociable.");
        addAnimal(db, "Lapin", "rabbit.png", "rabbit_sound.mp3", "Herbivore", 1, "Forêt",
                "Le lapin a de longues oreilles et aime manger des carottes. Il court très vite pour échapper aux prédateurs. Il vit dans des terriers.");
        addAnimal(db, "Panda", "panda.png", "panda_sound.mp3", "Herbivore", 1, "Forêt",
                "Le panda adore manger du bambou toute la journée. Il a une fourrure blanche et noire. Il vit dans les forêts de montagnes.");
        addAnimal(db, "Grenouille", "frog.png", "frog_croak.mp3", "Herbivore", 0, "Marais",
                "La grenouille saute très haut et vit près de l'eau. Elle mange de petits insectes. Sa peau est souvent verte ou colorée pour se camoufler.");

        // Ajouter des types de questions
        addQuestionType(db, "Quel est le nom de cet animal ?", "name");
        addQuestionType(db, "Quel est le régime alimentaire de cet animal ?", "diet");
        addQuestionType(db, "Cet animal est-il un mammifère ?", "isMammal");
        addQuestionType(db, "Où vit cet animal ?", "habitat");
    }

    /**
     * Méthode pour ajouter un animal dans la table Animals.
     */
    private void addAnimal(SQLiteDatabase db, String name, String imagePath, String soundPath, String diet, int isMammal, String habitat, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        values.put(COLUMN_SOUND_PATH, soundPath);
        values.put(COLUMN_DIET, diet);
        values.put(COLUMN_IS_MAMMAL, isMammal);
        values.put(COLUMN_HABITAT, habitat);
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(TABLE_ANIMALS, null, values);
    }

    /**
     * Méthode pour ajouter un type de question dans la table QuestionTypes.
     */
    private void addQuestionType(SQLiteDatabase db, String questionText, String field) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_TEXT, questionText);
        values.put(COLUMN_FIELD, field);
        db.insert(TABLE_QUESTION_TYPES, null, values);
    }

    /**
     * Récupère tous les animaux de la table Animals.
     */
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
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

                animals.add(new Animal(id, name, imagePath, soundPath, diet, isMammal, habitat, description));
            }
            cursor.close();
        }
        return animals;
    }

    /**
     * Récupère tous les types de questions de la table QuestionTypes.
     */
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

    /**
     * Compte le nombre distinct de valeurs pour un champ donné dans la table Animals.
     */
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
