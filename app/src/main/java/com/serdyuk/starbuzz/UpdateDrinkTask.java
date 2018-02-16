/*
package com.serdyuk.starbuzz;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.Toast;

*/
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
 *//*


public class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
    ContentValues drinkValues;

    protected void onPreExecute() {
        CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        drinkValues = new ContentValues(); drinkValues.put("FAVORITE", favorite.isChecked());
    }

    @Override
    protected Boolean doInBackground(Integer... drinks) {
        int drinkNo = drinks[0];
        SQLiteOpenHelper starbuzzDatabaseHelper =
                new StarbuzzDatabaseHelper(DrinkActivity.this);

        */
/*
        * Method update() use object drinkValues which was created by method onPreExecute
        * *//*

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

    */
/*
    * We should take same param as in doInBackground method, in this case it will be Boolean
    * *//*

    protected void onPostExecute(Boolean success) {
        if (!success) {
            Toast toast = Toast.makeText(DrinkActivity.this,
                    "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

//    This method is show progressbar and % of uploading
//    protected void onProgressUpdate(Integer... progress) {
//        setProgress(progress[0]);
//    }
}
*/
