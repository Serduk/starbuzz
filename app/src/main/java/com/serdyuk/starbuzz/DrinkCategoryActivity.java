package com.serdyuk.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        ListView listDrinks = getListView();

        /*
         * should be send 3 params:
         *      1. Current activity
         *      2. layout of current activity
         *      3. Array for displaying in activity
         * */
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks);

        /*
         * Array adapter connect with list view by mthod setAdapter() in class ListView
         * */
        listDrinks.setAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);

        /*
         * DrinkActivity.EXTRA_DRINKNO ==> Static variable
         * (int) id ==> add indificator of variant to Intent, which was clicked in app. He get index of drink in array
         */
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }
}
