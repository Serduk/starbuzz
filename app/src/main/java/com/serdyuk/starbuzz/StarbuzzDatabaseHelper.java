package com.serdyuk.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class helper for working with DataBase
 *
 * Created by sserdiuk on 2/11/18.
 */

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    /**
     * Constructor for database creating.
     * If we don't set name for DB -> she will be live only one session
     *
     * from basic constructor we remove basic atributes like: name, factory, version
     * */
    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateMyDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceID) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceID);
        db.insert("DRINK", null, drinkValues);
    }

    /**
     * Helper method for updating and creating database
     * if we create new DB, we mark oldVersion as 0
     * if we update current DB,
     *      we replace name of current version and use some another code in another condition
     * */
    private static void updateMyDatabase(SQLiteDatabase sqLiteDatabase,
                                         int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            sqLiteDatabase.execSQL("CREATE TABLE DRINK ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");

            insertDrink(sqLiteDatabase, "Latte",
                    "Espresso and steamed milk", R.drawable.latte);
            insertDrink(sqLiteDatabase, "Cappuccino",
                    "Espressohot milk and steamed-milk foam", R.drawable.cappuccino);
            insertDrink(sqLiteDatabase, "Filter",
                    "Our best drip coffee", R.drawable.filter);
        }

//        add new column in current table
        if (oldVersion < 2) {
            sqLiteDatabase.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    /*
    * method example for getting data from database
    *
    * public Cursor query(String table, String[] columns, String selection,
    * String[] selectionArgs, String groupBy,
    * String having,
    * String orderBy)
    * */
    public Cursor getDrinkNames(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.query("DRINK", new String[] {"NAME", "DESCRIPTION"},
                null, null, null, null, null);
        return cursor;
    }
}
