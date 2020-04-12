package com.example.actionouverite.challenge;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class Data {
    private JSONObject jsonData;
    private Iterator<String> categories;


    public Data(Context context) {
        String stringData = loadJSONFromAsset(context);
        this.jsonData = null;
        try {
            this.jsonData = new JSONObject(stringData);
            this.categories = jsonData.keys();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public Action getRandomActionByCategory(String categorie) {
        try {
            JSONArray jsonActions = jsonData.getJSONArray(categorie);
            JSONObject jsonAction = jsonActions.getJSONObject(new Random().nextInt(jsonActions.length()));
            String titre = jsonAction.getString("Titre");
            String mission = jsonAction.getString("Mission");
            float impact = jsonAction.getInt("Réduction d'empreinte CO2");
            int duree = jsonAction.getInt("Durée");
            Action randomAction = new Action(titre, mission, categorie, impact, duree);
            return randomAction;
        }
        catch (Exception e) {
            Log.i("Error", "Error in loading actions");
            return null;
        }
    }

    public Iterator<String> getCategories() {
        return this.categories;
    }

    public String loadJSONFromAsset(Context context) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open("JsonData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
            return jsonString;
        } catch (IOException ex) {
            Log.i("Error", "Error in reading JSON file");
            return null;
        }
    }
}