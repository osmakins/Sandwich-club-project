package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            //Create JSONObject of whole json string
            JSONObject root = new JSONObject(json);

            //Get the name JsonObject
            JSONObject name = root.getJSONObject("name");

            //Get the main name string
            String jMainName = name.optString("mainName");

            //Get the place of origin string
            String jPlaceOfOrigin = root.optString("placeOfOrigin");

            //Get the description string
            String jDescription = root.optString("description");

            //Get image path
            String jImagePath = root.optString("image");

            // Get the "also known as" and store in the list
            JSONArray AlsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            ArrayList<String> jAlsoKnownAsList = jsonArrayList(AlsoKnownAsArray);

            //Get the ingredient array and store in the list
            JSONArray IngredientArray = root.getJSONArray("ingredients");
            ArrayList<String> jIngredientList = jsonArrayList(IngredientArray);

            sandwich = new Sandwich(jMainName, jAlsoKnownAsList, jPlaceOfOrigin, jDescription, jImagePath, jIngredientList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    /*
     * Method for iterating through the json array and store as string list items
     */
    private static ArrayList<String> jsonArrayList (JSONArray jsonArray){
        // ArrayList stores the items in the list
        ArrayList<String> itemsList = new ArrayList<>();
        // add items to the ArrayList
        for (int i = 0; i<jsonArray.length(); i++ ) {
            itemsList.add(jsonArray.optString(i));
        }
        return itemsList;
    }
}
