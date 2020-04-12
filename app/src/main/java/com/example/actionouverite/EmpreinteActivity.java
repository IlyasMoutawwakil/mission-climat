package com.example.actionouverite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.actionouverite.challenge.Action;

public class EmpreinteActivity extends AppCompatActivity {

    private MediaPlayer musique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empreinte);

        // Récupération de l'intent content les informations si l'activité a été réalisée ou pas
        // Et l'information concernant sur cette activité
        Intent infoSurAct = getIntent();
        Action action = (Action) infoSurAct.getSerializableExtra("action");
        boolean decision = (boolean) infoSurAct.getSerializableExtra("décision");

        // Affiche l'économe en carbone
        float impactCarbone = action.getImpactCO2();
        TextView texteEmpreinte = findViewById(R.id.texteEmpreinte);
        String message;
        if (decision) {
            // Affiche le texte du défi
            texteEmpreinte.setText(action.getTexteDefi());
            message = String.format(getString(R.string.ouiEmpreinteCarbone), impactCarbone);//va dans values pour chercher la chaîne de caractère afin de ne pas avoir le texte en dur

            // envoyer l'empreinte carbone à AccueilActivity
            SharedPreferences sp = getSharedPreferences("donnees", MODE_PRIVATE);
            float score = sp.getFloat("score", 0);
            score += impactCarbone;
            SharedPreferences.Editor editorEmpreinteCarbone = sp.edit();
            editorEmpreinteCarbone.putFloat("score", score);
            editorEmpreinteCarbone.apply();

            musique = MediaPlayer.create(this, R.raw.celebration);
            musique.start();
            musique.setLooping(true);
        } else {
            // on affiche ce que le joueur aurait pu économiser comme carbone s'il avait fait OUI
            message = String.format(getString(R.string.nonEmpreinteCarbone), impactCarbone);//va dans values pour chercher la chaîne de caractère afin de ne pas avoir le texte en dur
            musique = MediaPlayer.create(this, R.raw.mariopiano);
            musique.start();
            musique.setLooping(true);
        }
        texteEmpreinte.setText(message);
    }

    // bouton OK pour revenir à l'accueil
    public void changerActivity(View view) {
        Intent i = new Intent();
        i.setClass(this, AccueilActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onStop() {
        musique.pause();
        super.onStop();
    }

    @Override
    protected void onPause() {
        musique.pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        musique.start();
    }
}

