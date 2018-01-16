package com.serdyuk.starbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends Activity {
    public static String EXTRA_DRINKNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        /*
         * Get drink from data in Intent
         * */
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        Drink drink = Drink.drinks[drinkNo];

        /*
        *
        * Init all data from array in Drinks
        * Set image for activity
        * Image taken from array in Drinks
        * */
        ImageView photo = (ImageView) findViewById(R.id.photo);
        TextView name = (TextView) findViewById(R.id.name);
        TextView description = (TextView) findViewById(R.id.description);

        /*
        * Set Drink and description Name
        * */
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getDescription());

        name.setText(drink.getName());
        description.setText(drink.getDescription());
    }
}
