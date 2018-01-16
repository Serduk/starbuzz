package com.serdyuk.starbuzz;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        ListView listDrinks = getListView();

        /**
         * should be send 3 params:
         *      1. Current activity
         *      2. layout of current activity
         *      3. Array for displaying in activity
         * */
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(
                this,
                android.R.layout.simple_list_item_1,
                Drink.drinks);

        /**
         * Array adapter connect with list view by mthod setAdapter() in class ListView
         * */
        listDrinks.setAdapter(listAdapter);
    }
}
