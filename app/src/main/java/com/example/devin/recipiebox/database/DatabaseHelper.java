package com.example.devin.recipiebox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    //Database Name
    private static final String DATABASE_NAME = "databaseManager";

    private static final String TABLE_NAME = "recipie_table";
    private static final String TABLE_NAME2 = "ingredient_table";
    private static final String TABLE_NAME3 = "recipie_and_ingredient_table";
    private static final String TABLE_NAME4 = "shopping_cart_table";
    private static final String TABLE_NAME5 = "recipe_folder_table";


    private static final String COLUMN_RECIPIE_ID = "ID";
//    private static final String COLUMN_RECIPIE_ID = "ID";           //COL1
    private static final String COLUMN_RECIPIE_NAME = "RecipieName"; //COL2

    private static final String COLUMN_INGREDIENT_ID = "IngredientID"; //unique ID to Ingredient Tabl
//    private static final String COLUMN_INGREDIENT_ID = "ID";
//    private static final String COLUMN_INGREDIENT_ID = "COLUMN_RECIPIE_ID"; //COL3
    private static final String COLUMN_INGREDIENT_NAME = "IngredientName"; //COL4
    private static final String COLUMN_INGREDIENT_RECIPIE_ID = "ID"; //COL5 /passed in from recipie table
    private static final String COLUMN_INGREDIENT_QUANTITY = "Quantity";
    private static final String COLUMN_INGREDIENT_MEASUREMENT_TYPE = "MeasurementType";
    private static final String COLUMN_RECIPIE_FOLDER_NAME = "RecipieFolderName";
    private static final String COLUMN_RECIPIE_DESCRIPTION = "RecipieDescription";
    private static final String COLUMN_RECIPIE_FOLDER_ID = "FolderID";
    private static final String COLUMN_SHOPPING_CART_ID = "ShoppingCartID";
    private static final String COLUMN_SHOPPING_CART_FLAG = "ShoppingCartFlag";
    private static final String COLUMN_RECIPE_QUANTITY = "RecipieQuantity";
//    private static final String COLUMN_INGREDIENT_PRICE = "IngPrice";
//    private static final String COLUMN_RECIPIE_PRICE = "RecipiePrice";
//    private static final String COLUMN_SHOPPING_CART_PRICE = "ShoppingCartPrice";
//    private static final String COLUMN_SHOPPING_CART_PRICE_TOTAL = "ShoppingCartPriceTotal";

    /*
    private static final String COL1 = "ID";
    private static final String COL2 = "RecipieName";
    private static final String CO
    private static final String COL4 = "IngredientName";
    private static final String COL5 = ""
    */

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 42);
    }

    //Create Tables...

    private static final String createTable = "CREATE TABLE " + TABLE_NAME + " "
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RECIPIE_NAME +" TEXT, "
            + COLUMN_RECIPIE_DESCRIPTION +" TEXT, "
  //          + COLUMN_RECIPIE_PRICE + " REAL, "
            + COLUMN_RECIPIE_FOLDER_ID + " INTEGER)";

    private static final String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " "
            + "(IngredientID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INGREDIENT_NAME +" TEXT, " +
            COLUMN_INGREDIENT_QUANTITY + " REAL, " +
            COLUMN_INGREDIENT_MEASUREMENT_TYPE + " TEXT, " +
     //       COLUMN_INGREDIENT_PRICE + " REAL, " +
            COLUMN_SHOPPING_CART_FLAG + " TEXT, " +
            COLUMN_INGREDIENT_RECIPIE_ID + " INTEGER)";

    private static final String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " "
            + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPIE_NAME +" TEXT)";

    private static final String createTable4 = "CREATE TABLE " + TABLE_NAME4 + " "
            + "(ShoppingCartID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INGREDIENT_NAME +" TEXT, " +
            COLUMN_INGREDIENT_QUANTITY + " REAL, " +
  //          COLUMN_SHOPPING_CART_PRICE + " REAL, " +
  //          COLUMN_SHOPPING_CART_PRICE_TOTAL + " REAL, " +
            COLUMN_RECIPE_QUANTITY + " INTEGER, " +
            COLUMN_INGREDIENT_MEASUREMENT_TYPE + " TEXT)";

    private static final String createTable5 = "CREATE TABLE " + TABLE_NAME5 + " "
            + "(FolderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPIE_FOLDER_NAME +" TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        db.execSQL(createTable5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);
    }


    public boolean addRecipieData(String item, String description, /* double price, */ int FolderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPIE_NAME, item);
        contentValues.put(COLUMN_RECIPIE_DESCRIPTION, description);
   //     contentValues.put(COLUMN_RECIPIE_PRICE, price);
        contentValues.put(COLUMN_RECIPIE_FOLDER_ID, FolderID);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addIngredientData(String item, double quantity, String measurementType, String scf, /* double price, */ int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INGREDIENT_NAME, item);
        contentValues.put(COLUMN_INGREDIENT_QUANTITY, quantity);
        contentValues.put(COLUMN_INGREDIENT_MEASUREMENT_TYPE, measurementType);
 //       contentValues.put(COLUMN_INGREDIENT_PRICE, price);
        contentValues.put(COLUMN_SHOPPING_CART_FLAG, scf);
        contentValues.put(COLUMN_INGREDIENT_RECIPIE_ID, id);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME2);

        long result = db.insert(TABLE_NAME2, null, contentValues);

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addRecipieFolderData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPIE_FOLDER_NAME, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME5);
        long result = db.insert(TABLE_NAME5, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

/*
    public boolean addShoppingCartData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPIE_NAME, item);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME4);
        long result = db.insert(TABLE_NAME4, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
*/
 //TEMPORAIRLY USING THE ABOVE METHOD...
    public boolean addShoppingCartData(String item, double quantity, String measurementType, int recipeQuantity/*, double price, double priceTotal*/) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INGREDIENT_NAME, item);
        contentValues.put(COLUMN_INGREDIENT_QUANTITY, quantity);
        contentValues.put(COLUMN_INGREDIENT_MEASUREMENT_TYPE, measurementType);
        contentValues.put(COLUMN_RECIPE_QUANTITY, recipeQuantity);
 //       contentValues.put(COLUMN_SHOPPING_CART_PRICE, price);
 //       contentValues.put(COLUMN_SHOPPING_CART_PRICE_TOTAL, priceTotal);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME4);
        long result = db.insert(TABLE_NAME4, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void updateShoppingCartQuantity(double newQuantity, String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_INGREDIENT_QUANTITY +
                " = '" + newQuantity + "' WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting quantity to " + newQuantity);
        db.execSQL(query);
    }
    public void updateShoppingCartMeasurementType(String measurementType, String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_INGREDIENT_MEASUREMENT_TYPE +
                " = '" + measurementType + "' WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting quantity to " + measurementType);
        db.execSQL(query);
    }
/*
    public void updateShoppingCartPrice(double price, String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_SHOPPING_CART_PRICE +
                " = '" + price + "' WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting price to " + price);
        db.execSQL(query);
    }
*/
/*
    public Cursor getRecipePrice(String recipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPIE_PRICE + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPIE_NAME + " = '" + recipeName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
*/

    public Cursor getRecipieData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIngredientData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getShoppingCartData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME4;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getShoppingCartDataRow(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME4 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return  data;
    }

    /*
    public Cursor getRecipeDataRow(String RecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPIE_NAME + " = '" + RecipeName + "'";
        Cursor data = db.rawQuery(query, null);
        return  data;
    } */

    /*
    public Cursor getRecipeDataRowSubstr(String recipieString) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT instr(COLUMN_RECIPIE_NAME, 'recipeString') recipeString FROM " + TABLE_NAME +
                " WHERE recipeString > 0";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    */
    /*
    public Cursor getRecipeDataRowSubstr(String recipieString) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPIE_ID + "," + COLUMN_RECIPIE_NAME + "," + COLUMN_RECIPIE_PRICE + " FROM " + TABLE_NAME +
                " WHERE INSTR(" + COLUMN_RECIPIE_NAME + ",'" + recipieString + "')>" + 0;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
*/
    public Cursor getRecipeDataRowSubstr(String recipieString) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPIE_ID + "," + COLUMN_RECIPIE_NAME + " FROM " + TABLE_NAME +
                " WHERE INSTR(" + COLUMN_RECIPIE_NAME + ",'" + recipieString + "')>" + 0;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipieFolderData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME5;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipieAndIngredientData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME3;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIngredientsBasedOnRecipieData(int RecipieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_INGREDIENT_RECIPIE_ID + " = '" + RecipieId + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipiesByFolder(int FolderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPIE_FOLDER_ID + " = '" + FolderID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipieItemID(String RecipieName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPIE_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPIE_NAME + " = '" + RecipieName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipieDescription(String RecipieName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPIE_DESCRIPTION + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPIE_NAME + " = '" + RecipieName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIngredientItemID(String IngredientName, int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_ID + " FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'"
                + " AND " + COLUMN_INGREDIENT_RECIPIE_ID + " = '" + ID + "'";
                ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /*
    public Cursor getIngredientRecipieItemID(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_RECIPIE_ID + " FROM " + TABLE_NAME2
    } */

    public Cursor getShoppingCartItemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_SHOPPING_CART_ID + " FROM " + TABLE_NAME4 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateRecipieName(String newRecipieName, int id, String oldRecipieName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPIE_NAME +
                " = '" + newRecipieName + "' WHERE " + COLUMN_RECIPIE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPIE_NAME + " = '" + oldRecipieName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newRecipieName);
        db.execSQL(query);
    }


    public void updateRecipieDescription(String newDescription, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String newDescriptionTwo = "";
/*        char apostraphe = ;
        int count = 0;

        for (int i=0; i<newDescription.length(); i++) {
            if(newDescription.charAt(i) == "'") {
                count++;
            }
        }
    */
        if(newDescription.contains("'")) {
            int firstIndex = newDescription.indexOf("'");
            String first = newDescription.substring(0, firstIndex);
     //       String slash = "\'";
            String second = newDescription.substring(firstIndex+1, newDescription.length());
            newDescriptionTwo = first+second;

            String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPIE_DESCRIPTION +
                    " = '" + newDescriptionTwo + "' WHERE " + COLUMN_RECIPIE_ID + " = '" + id + "'";
            Log.d(TAG, "updateName: query: " + query);
            Log.d(TAG, "updateName: Setting name to " + newDescriptionTwo);
            db.execSQL(query);

        } else {

            String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPIE_DESCRIPTION +
                    " = '" + newDescription + "' WHERE " + COLUMN_RECIPIE_ID + " = '" + id + "'";
            Log.d(TAG, "updateName: query: " + query);
            Log.d(TAG, "updateName: Setting name to " + newDescription);
            db.execSQL(query);
        }
    }

    public void updateIngredientName(String newIngredientName, int id, String oldIngredientName, int RecipieID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_INGREDIENT_NAME +
                " = '" + newIngredientName + "' WHERE " + COLUMN_INGREDIENT_ID + " = '" + id + "'" +
                " AND " + COLUMN_INGREDIENT_NAME + " = '" + oldIngredientName + "'" +
                " AND " + COLUMN_INGREDIENT_RECIPIE_ID + " = '" + RecipieID + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newIngredientName);
        db.execSQL(query);
    }

    //We're making a Select shopping cart name query here...
    public Cursor getShoppingCartIngredient(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_NAME + " FROM " + TABLE_NAME4 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /*
    public Cursor getIngredientItemID(String IngredientName, int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_ID + " FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'"
                + " AND " + COLUMN_INGREDIENT_RECIPIE_ID + " = '" + ID + "'";
        ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
*/
    public void updateShoppingCartName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_RECIPIE_NAME +
                " = '" + newName + "' WHERE " + COLUMN_RECIPIE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPIE_NAME + " =  '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: setting name to " + newName);
    }

    public void deleteRecipieName(int id, String RecipieName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_RECIPIE_ID + " = '" + id + "'";
    //            " AND " + COLUMN_RECIPIE_NAME + " = '" + RecipieName + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + RecipieName + " from database.");
        db.execSQL(query);
    }
    public void deleteIngredientName(int id, String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME2 + " WHERE "
                + COLUMN_INGREDIENT_ID + " = '" + id + "'" +
                " AND " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + IngredientName + " from database.");
        db.execSQL(query);
    }

    public void deleteRecipeIngredients(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME2 + " WHERE "
                + COLUMN_INGREDIENT_RECIPIE_ID + " = '" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting Ingredients for recipe ID: " + id + "from database.");
        db.execSQL(query);
    }

    public void deleteShoppingCartRecipie(int id, String RecipieName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME4 + " WHERE "
                + COLUMN_RECIPIE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPIE_NAME + " = '" + RecipieName + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + RecipieName + " from shopping cart database.");
        db.execSQL(query);
    }

    public void deleteShoppingCartIngredient(int id, String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME4 + " WHERE "
                + COLUMN_SHOPPING_CART_ID + " = '" + id + "'" +
                " AND " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + ingredientName + " from shopping cart database.");
        db.execSQL(query);
    }

    public void deleteShoppingCartRecipieData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME4;
        Log.d(TAG, "deleted all values of shopping cart list....");
        db.execSQL(query);
    }

    //Counts number of recipies in the list
    public int getRecipieCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    //Select individual recipie from the list
    public int getIndivRecipieCount(int id, String RecipieName) {
        String countQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_RECIPIE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPIE_NAME + " = '" + RecipieName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Select number of Ingredients in the Recipe
    public int getIngredientCount(int id) {
        String countQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE "
                + COLUMN_RECIPIE_ID + " = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Count instances where Shopping Cart Flag is "Y".
    public int getExportedRecipeCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE "
                + COLUMN_SHOPPING_CART_FLAG + " = '" + "Y" + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void updateShoppingCartRecipeCount(int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_RECIPE_QUANTITY +
                " = '" + quantity + "'";
        Log.d(TAG, "updateName: query: " + query);
    }

    public int getShoppingCartCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }
/*
    public void updateRecipePrice(double price, String recipeName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPIE_PRICE +
                " = '" + price + "' WHERE " + COLUMN_RECIPIE_NAME + " = '" + recipeName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting price to " + price);
        db.execSQL(query);
    }
    */
/*
    public void updateShoppingCartPriceTotal(double priceTotal) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_SHOPPING_CART_PRICE +
                " = '" + priceTotal + "'";
        Log.d(TAG, "update total price: query: " + query);
        Log.d(TAG, "update total price: Setting price to " + priceTotal);
    }
    */
/*
    public Cursor getShoppingCartPriceSum() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + " SUM(" + COLUMN_SHOPPING_CART_PRICE + ") " +  "FROM " + TABLE_NAME4;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
*/
}
