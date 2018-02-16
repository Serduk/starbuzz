package com.serdyuk.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {
    public static String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        /*
         * Get drink from data in Intent
         * */
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);

        try {
//            connection to database
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();

//            cursor creating
            Cursor cursor = db.query ("DRINK",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkNo)},
                    null, null,null);

//            Go to first row from cursor
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);

//                Filling drink name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

//                Filling drink description
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);

//                Filling drink image
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                /*
                * Set state for checkBox favorite
                * */
                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onFavoriteClicked(View view) {
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);

//        this code was commited because we will be use UpdateDrinkTask class,
//        which will be update all data on backend
/*        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        SQLiteOpenHelper starbuzzDatabaseHelper
                = new StarbuzzDatabaseHelper(DrinkActivity.this);

        drinkValues.put("FAVORITE", favorite.isChecked());

        try {
            System.out.println("try insert checkBox data to database");
            System.out.println("CheckBox is " + favorite.isChecked());
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("DRINK",
                    drinkValues,
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)});
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }*/

        new UpdateDrinkTask().execute(drinkNo);
    }

    /*
    * Задача AsyncTask добавляется как внутренний класс в активность,
    * которая должна ее использовать.
    * Мы добавим свой класс UpdateDrinkTask как внутренний класс DrinkActivity.java.
    * Задача будет выполняться в методе onFavoriteClicked() класса DrinkActivity,
    * чтобы база данных обновлялась в фоновом режиме, когда пользо
    * */

    /**
     * Class for async work
     *
     * In this class we load database
     * and get data from there
     *
     * in AsyncTask we set first param as Integer, because we get param Integer in method doInBackground;
     * Also, we add Boolean, because doInBackground -> should return boolean;
     *
     * in asyncTask next datas:
     * (first) Integer - to doInBackground();
     * (Second) Void - to onProgressUpdate();
     * (Third) Boolean - onPostExecute();
     *
     * Created by sserdiuk on 2/16/18.
     */
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues drinkValues;

        /*
        * Before we will run code for database for executing, we add checks to object ContentValues
        * with name drinkValues
        * */
        protected void onPreExecute() {
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            drinkValues = new ContentValues();
            drinkValues.put("FAVORITE", favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkNo = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper =
                    new StarbuzzDatabaseHelper(DrinkActivity.this);

        /*
        * Method update() use object drinkValues which was created by method onPreExecute
        * */
            try {
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("DRINK", drinkValues,
                        "_id = ?", new String[]{Integer.toString(drinkNo)});
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        /*
        * We should take same param as in doInBackground method, in this case it will be Boolean
        * */
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DrinkActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
