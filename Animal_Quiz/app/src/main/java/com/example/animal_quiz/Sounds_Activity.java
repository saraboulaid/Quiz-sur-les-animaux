package com.example.animal_quiz;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


/**
 * Activité qui permet à l'utilisateur d'écouter les sons de différents animaux
 */
public class Sounds_Activity extends AppCompatActivity {
    /**
     * Objet MediaPlayer utilisé pour lire les fichiers audio.
     */
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds);

        // Initialisation des boutons associés aux animaux
        ImageButton kangro = (ImageButton) findViewById(R.id.img1);
        ImageButton donkey = (ImageButton) findViewById(R.id.img2);
        ImageButton yak = (ImageButton) findViewById(R.id.img3);
        ImageButton eagle = (ImageButton) findViewById(R.id.img4);
        ImageButton goat = (ImageButton) findViewById(R.id.img5);
        ImageButton camel = (ImageButton) findViewById(R.id.img6);
        ImageButton lion = (ImageButton) findViewById(R.id.img7);
        ImageButton zebra = (ImageButton) findViewById(R.id.img8);

        // Définition des actions pour chaque bouton
        kangro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("kangro");
            }
        });
        donkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("donkey");
            }
        });
        yak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("yak");
            }
        });
        eagle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("eagle");
            }
        });
        goat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("goat");
            }
        });
        camel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("camel");
            }
        });
        lion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("lion");
            }
        });
        zebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio("zebra");
            }
        });


    }

    /**
     * Joue un fichier audio correspondant à un animal.
     * Les sons sont identifiés par des noms passés en paramètre.
     *
     * @param _soundNames Le nom de l'animal pour lequel le son doit être joué.
     */
    public void playAudio(String _soundNames)
    {
        // Arrêter le lecteur audio actuel s'il existe
        if (player!= null)
        {
            stopPlayer();
            player = null;
        }

        // Sélectionner le fichier audio en fonction du nom de l'animal
        if(_soundNames == "kangro")
        {
            player = MediaPlayer.create(this,R.raw.kangro);
        }
        else if(_soundNames == "donkey")
        {
            player = MediaPlayer.create(this,R.raw.donkey);
        }
        else if(_soundNames == "yak")
        {
            player = MediaPlayer.create(this,R.raw.yak);
        }
        else if(_soundNames == "eagle")
        {
            player = MediaPlayer.create(this,R.raw.eagle);
        }
        else if(_soundNames == "goat")
        {
            player = MediaPlayer.create(this,R.raw.goat);
        }
        else if(_soundNames == "camel")
        {
            player = MediaPlayer.create(this,R.raw.camel);
        }
        else if(_soundNames == "lion")
        {
            player = MediaPlayer.create(this,R.raw.lion);
        }
        else if(_soundNames == "zebra")
        {
            player = MediaPlayer.create(this,R.raw.zebra);
        }

        else
        {
            player = null;
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.start();
            }
        });

        // Démarrer la lecture
        player.start();
    }

    /**
     * Arrête le lecteur audio s'il est en cours d'utilisation.
     */
    public void stopPlayer()
    {
        if (player!= null)
        {
            player.release();
            player = null;
        }
    }

    public void stop(View v)
    {
        stopPlayer();
    }
}


