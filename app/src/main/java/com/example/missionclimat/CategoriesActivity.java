package com.example.missionclimat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.missionclimat.challenge.Data;

import java.util.Iterator;


public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer musique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        LinearLayout categoriesLayout = findViewById(R.id.categoriesLayout);

        Data data = new Data(this);
        Iterator<String> categories = data.getCategories();

        while (categories.hasNext()) {
            String categorie = categories.next();
            Button b = new Button(this);
            b.setText(categorie);
            b.setOnClickListener(this);
            b.setTextSize(30);
            categoriesLayout.addView(b);
        }

        musique = MediaPlayer.create(this, R.raw.vivaldi);
        musique.start();
        musique.setLooping(true);
    }

    public void onClick(View v) {
        Button clickedButton = (Button) v;
        String categorie = clickedButton.getText().toString();
        Intent versMissionActivity = new Intent();
        versMissionActivity.putExtra("categorie", categorie);
        versMissionActivity.setClass(this, MissionActivity.class);
        startActivity(versMissionActivity);
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


