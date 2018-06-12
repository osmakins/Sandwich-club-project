package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.image_iv)ImageView ingredientsIv;

    @BindView(R.id.also_known_tv) TextView mAlsoKnownAsText;
    @BindView(R.id.description_tv) TextView mDescriptionText;
    @BindView(R.id.origin_tv) TextView mPlaceOfOriginText;
    @BindView(R.id.ingredients_tv) TextView mIngredientsText;

    @BindString(R.string.not_available) String Unavailable;
    @BindString(R.string.detail_error_message) String errorMsg;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        //-------------------------------AlsoKnownAs------------------------------------------------
        String alsoKnownAs;
        // Get the "also known as" data and store as a list
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        sandwich.setAlsoKnownAs(alsoKnownAsList);

        // Check if the "also known as" list has items
        if (alsoKnownAsList.size() >= 1) {

            // Iterate and build the list items separated by comma
            StringBuilder alsoKnownAsBuilder = new StringBuilder();
            String comma = "";
            for (String alias : alsoKnownAsList) {
                // no comma before the first word and after the last word
                alsoKnownAsBuilder.append(comma);
                comma = ", ";
                alsoKnownAsBuilder.append(alias);
            }
            alsoKnownAs = alsoKnownAsBuilder.toString();

        } else {
            // the list is empty (no items)!
            alsoKnownAs = Unavailable;
        }
        // Set the TextView
        mAlsoKnownAsText.setText(alsoKnownAs);

        //-------------------------------Description------------------------------------------------

        // Get the description data and store as a string
        String Description = sandwich.getDescription();
        sandwich.setDescription(Description);

        // Check if the string is empty
        if ("".equals(Description)) {
            Description = Unavailable;
        }
        // Set the TextView
        mDescriptionText.setText(Description);

        //---------------------------------PlaceOfOrigin--------------------------------------------

        // Get the "place of origin" string and store in placeOfOrigin
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        sandwich.setPlaceOfOrigin(placeOfOrigin);

        // Check if the string is empty
        if ("".equals(placeOfOrigin)) {
            placeOfOrigin = Unavailable;
        }
        // Set the TextView
        mPlaceOfOriginText.setText(placeOfOrigin);

        //--------------------------------Ingredients-----------------------------------------------
        String Ingredients;
        // Get the ingredients data and store as a list
        List<String> ingredientsList = sandwich.getIngredients();
        sandwich.setIngredients(ingredientsList);

        // Check if the list has items
        if (ingredientsList.size() >= 1) {
            // Iterate and build the string list with bullets
            StringBuilder ingredientsBuilder = new StringBuilder();
            for (String ingredient : ingredientsList) {
                ingredientsBuilder.append("\u25AA  " + ingredient + "\n");
            }
            Ingredients = ingredientsBuilder.toString();

        } else {
            // the list is empty (no items)!
            Ingredients = Unavailable;
        }
        // Set the TextView
        mIngredientsText.setText(Ingredients);
    }
}
