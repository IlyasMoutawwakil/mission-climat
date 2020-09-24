package com.example.missionclimat.challenge;

import java.io.Serializable;

public class Action implements Serializable {
    String titreDefi;
    String missionDefi;
    String categorie;
    float impactCO2;
    int duree;

    public Action(String titre, String mission, String categorie, float impact, int duree) {
        this.titreDefi = titre;
        this.missionDefi = mission;
        this.categorie = categorie;
        this.impactCO2 = impact;
        this.duree = duree;
    }

    public String getTitreDefi() {
        return titreDefi;
    }

    public String getTexteDefi() {
        return missionDefi;
    }

    public String getCategorie() {
        return categorie;
    }

    public float getImpactCO2() {
        return impactCO2;
    }

    public int getDuree() {
        return duree;
    }
}
