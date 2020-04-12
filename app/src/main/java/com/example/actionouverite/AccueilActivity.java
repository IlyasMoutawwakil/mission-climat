package com.example.actionouverite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class AccueilActivity extends AppCompatActivity {
    private MediaPlayer musique;
    private float score;
    private SharedPreferences sp;
    private TextView textScore;
    private PopupWindow popupWindow;
    private View btnOpenPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        musique = MediaPlayer.create(this, R.raw.calmstart);
        musique.start();
        musique.setLooping(true);

        //O bouton
        btnOpenPopup = findViewById(R.id.imageScore);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.pop_up, null);
                popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //Update TextView in PopupWindow dynamically
                textScore = popupView.findViewById(R.id.textScore);
                sp = getSharedPreferences("donnees", MODE_PRIVATE);
                score = sp.getFloat("score", 0);
                final String scoreMessage;
                if (score > 0) {
                    scoreMessage = String.format(getString(R.string.score), score);
                } else {scoreMessage = getString(R.string.noScore);}
                textScore.setText(scoreMessage);

                Button btnDismiss = popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {popupWindow.dismiss();
                    }});

                Button btnReset = popupView.findViewById(R.id.reset);
                btnReset.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        score = 0;
                        SharedPreferences.Editor editorEmpreinteCarbone = sp.edit();
                        editorEmpreinteCarbone.putFloat("score", score);
                        editorEmpreinteCarbone.apply();
                        textScore.setText(getString(R.string.noScore));
                        popupWindow.dismiss();
                    }});
                popupWindow.showAsDropDown(btnOpenPopup);
            }});

    }

    //bouton START à cliquer
    public void commencer(View view) {
        Intent versCategoriesActivity = new Intent();
        versCategoriesActivity.setClass(this, CategoriesActivity.class);//categories est l'activité de la page 2
        startActivity(versCategoriesActivity);

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