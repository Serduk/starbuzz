package com.serdyuk.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {
    private SQLiteDatabase database;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);


        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view,
                                    int position,
                                    long l) {
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this,
                            DrinkCategoryActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    System.out.println("Clicked on button FOOD");
                } else if (position == 2) {
                    System.out.println("something else");
                }
            }
        };

        //        Add Listener to list view in activity
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);

        /*
        * Connect to database and try get all favorites drinks
        * */
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            database = starbuzzDatabaseHelper.getReadableDatabase();
            favoritesCursor = database.query("DRINK",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);

            /*
             * Set all names of favorites drinks on screen
             * */
            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_list_item_1,
                    favoritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);

            listFavorites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

        /*
        * Switching to drinkActivity after choosing drink in favorites
        * */
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                /*
                * If user choose one of this variants of favorites list
                * Create Intent for start DrinkActivity an send identificator of drink
                * */
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        try {
            StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            database = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor newCursor = database.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);

            ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
            /*
            * Get adapter of List view
            * */
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
            /*
            * Replace cursor which used by adaptercursor, set him as new
            * */
            adapter.changeCursor(newCursor);
            favoritesCursor = newCursor;
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        favoritesCursor.close();
        database.close();
    }

    public void tapOnLogo(View view) {
        System.out.println("tap on logo");
    }
}