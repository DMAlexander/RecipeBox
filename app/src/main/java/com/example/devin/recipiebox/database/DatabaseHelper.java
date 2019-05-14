package com.example.devin.recipiebox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.devin.recipiebox.database.model.IngredientDatabase;
import com.example.devin.recipiebox.database.model.RecipieDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    /*
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "recipie_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Table Names
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create recipies table
        db.execSQL(RecipieDatabase.CREATE_TABLE);
        db.execSQL(IngredientDatabase.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + RecipieDatabase.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + IngredientDatabase.TABLE_NAME);

        //Create tables again
        onCreate(db);
    }

    public long insertRecipie(String recipie) {
        //get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RecipieDatabase.COLUMN_RECIPIENAME, recipie);
        values.put(RecipieDatabase.COLUMN_INGREDIENTNAME, recipie);
        values.put(RecipieDatabase.COLUMN_INGREDIENTQUANTITY, recipie);
        values.put(RecipieDatabase.COLUMN_RECIPIEPRICE, recipie);

        //insert row
        long id = db.insert(RecipieDatabase.TABLE_NAME, null, values);

        //close db connection
        db.close();

        //return newly inserted row id
        return id;
    }

    public long insertIngredient(String ingredient) {
        //get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(IngredientDatabase.COLUMN_ID, ingredient);
        values.put(IngredientDatabase.COLUMN_INGREDIENTNAME, ingredient);
        values.put(IngredientDatabase.COLUMN_BULKPRICE, ingredient);
        values.put(IngredientDatabase.COLUMN_INDIVPRICE, ingredient);

        //insert row
        long id = db.insert(IngredientDatabase.TABLE_NAME, null, values);

        //close db connection
        db.close();

        //return newly inserted row id
        return id;
    }

    public RecipieDatabase getRecipie(long id) {
        //get readable database this time...
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RecipieDatabase.TABLE_NAME,
                new String[]{RecipieDatabase.COLUMN_ID, RecipieDatabase.COLUMN_RECIPIENAME,
                        RecipieDatabase.COLUMN_RECIPIEPRICE, RecipieDatabase.COLUMN_INGREDIENTQUANTITY,
                        RecipieDatabase.COLUMN_INGREDIENTNAME},
                RecipieDatabase.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        //prepare database option
        RecipieDatabase database = new RecipieDatabase(
                cursor.getInt(cursor.getColumnIndex(RecipieDatabase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(RecipieDatabase.COLUMN_RECIPIENAME)),
                cursor.getString(cursor.getColumnIndex(RecipieDatabase.COLUMN_RECIPIEPRICE)),
                cursor.getInt(cursor.getColumnIndex(RecipieDatabase.COLUMN_INGREDIENTQUANTITY)),
                cursor.getString(cursor.getColumnIndex(RecipieDatabase.COLUMN_INGREDIENTNAME)));

        //close the db connection
        cursor.close();

        return database;
    }

    public IngredientDatabase getIngredient(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(IngredientDatabase.TABLE_NAME,
                new String[]{IngredientDatabase.COLUMN_ID, IngredientDatabase.COLUMN_BULKPRICE,
                IngredientDatabase.COLUMN_INDIVPRICE, IngredientDatabase.COLUMN_INGREDIENTNAME},
                IngredientDatabase.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        //Prepare database option
        IngredientDatabase database = new IngredientDatabase(
                cursor.getInt(cursor.getColumnIndex(IngredientDatabase.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(IngredientDatabase.COLUMN_BULKPRICE)),
                cursor.getString(cursor.getColumnIndex(IngredientDatabase.COLUMN_INDIVPRICE)),
                cursor.getString(cursor.getColumnIndex(IngredientDatabase.COLUMN_INGREDIENTNAME)));

                //close the db connection
        cursor.close();

        return database;
    }

    public List<RecipieDatabase> getAllRecipies() {
        List<RecipieDatabase> databases = new ArrayList<>();

        //Select All Query
        String selectQuery = "SELECT * FROM " + RecipieDatabase.TABLE_NAME + " ORDER BY " +
                RecipieDatabase.COLUMN_RECIPIENAME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RecipieDatabase database = new RecipieDatabase();
                database.setId(cursor.getInt(cursor.getColumnIndex(RecipieDatabase.COLUMN_ID)));
                database.setRecipieName(cursor.getString(cursor.getColumnIndex(RecipieDatabase.COLUMN_RECIPIENAME)));

                databases.add(database);
            } while (cursor.moveToFirst());
        }

        //close db connection
        db.close();

        //return notes list
        return databases;
    }

    public List<IngredientDatabase> getAllIngredients() {
        List<IngredientDatabase> databases = new ArrayList<>();

        //Select All Query
        String selectQuery = "SELECT* FROM " + IngredientDatabase.TABLE_NAME + " ORDER BY " +
                IngredientDatabase.COLUMN_INGREDIENTNAME + " DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                IngredientDatabase database = new IngredientDatabase();
                database.setId(cursor.getInt(cursor.getColumnIndex(IngredientDatabase.COLUMN_ID)));
                database.setBulkPrice(cursor.getString(cursor.getColumnIndex(IngredientDatabase.COLUMN_BULKPRICE)));

                databases.add(database);
            } while (cursor.moveToFirst());
        }

        //close db connection
        db.close();

        //returns notes list
        return databases;
    }

    public int getRecipieCount() {
        String countQuery = "SELECT * FROM " + RecipieDatabase.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int getIngredientCount() {
        String countQuery = "SELECT * FROM " + IngredientDatabase.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int updateRecipie(RecipieDatabase recipie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipieDatabase.COLUMN_ID, recipie.getId());
        values.put(RecipieDatabase.COLUMN_RECIPIENAME, recipie.getRecipieName());
        values.put(RecipieDatabase.COLUMN_INGREDIENTNAME, recipie.getIngredientName());
        values.put(RecipieDatabase.COLUMN_INGREDIENTQUANTITY, recipie.getRecipiePrice());

        //updating row
        return  db.update(RecipieDatabase.TABLE_NAME, values, RecipieDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipie.getId())});
    }

    public int updateIngredient(IngredientDatabase ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IngredientDatabase.COLUMN_ID, ingredient.getId());
        values.put(IngredientDatabase.COLUMN_INGREDIENTNAME, ingredient.getIngredientName());
        values.put(IngredientDatabase.COLUMN_BULKPRICE, ingredient.getBulkPrice());
        values.put(IngredientDatabase.COLUMN_INDIVPRICE, ingredient.getIndivPrice());

        //updating row
        return db.update(IngredientDatabase.TABLE_NAME, values, IngredientDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(ingredient.getId())});
    }

    public void deleteRecipie(RecipieDatabase recipie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RecipieDatabase.TABLE_NAME, RecipieDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipie.getId())});
    }

    public void deleteIngredient(IngredientDatabase ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RecipieDatabase.TABLE_NAME, IngredientDatabase.COLUMN_ID + " = ?",
                new String[]{String.valueOf(ingredient.getId())});

    }
*/


}
