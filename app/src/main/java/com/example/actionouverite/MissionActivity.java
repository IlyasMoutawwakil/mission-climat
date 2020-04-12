package com.example.actionouverite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.actionouverite.challenge.Action;
import com.example.actionouverite.challenge.Data;


public class MissionActivity extends AppCompatActivity {
    private  Intent versEmpreinte;
    private Action action;
    private Data data;
    private MediaPlayer musique;
    private String categorie;
    private TextView titreMission;
    private TextView texteMission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

       // on récupère l'action générée par actionVerite.class
        Intent depuisCategories = getIntent();
        categorie = depuisCategories.getStringExtra("categorie");
        data = new Data(this);
        titreMission = findViewById(R.id.titreMission);
        texteMission = findViewById(R.id.textMission);
        changerMission(new View(this));

        versEmpreinte = new Intent();  // creation d'un intent pour être envoyé a la classe empreinte
        versEmpreinte.setClass(this, EmpreinteActivity.class);
        musique = MediaPlayer.create(this, R.raw.missionimpossible);
        musique.start();
        musique.setLooping(true);
    }


    public void accepterMission(View view) {
        versEmpreinte.putExtra("décision", true); //on informe empreinte.class que l'utilisateur accepte le défi
        versEmpreinte.putExtra("action", action);
        startActivity(versEmpreinte);
    }

    public void refuserMission(View view) {
        versEmpreinte.putExtra("décision", false); //on informe empreinte.class que l'utilisateur refuse le défi
        versEmpreinte.putExtra("action", action);
        startActivity(versEmpreinte);
        finish();
    }


    public void changerMission(View view) {
        action = data.getRandomActionByCategory(categorie);
        titreMission.setText(String.format(getString(R.string.missionInfo) ,action.getTitreDefi(), action.getDuree()));
        texteMission.setText(String.format(getString(R.string.missionDetails),action.getTexteDefi()));
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
