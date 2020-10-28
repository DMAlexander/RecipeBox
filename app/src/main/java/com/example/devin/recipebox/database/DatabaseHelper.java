package com.example.devin.recipebox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "databaseManager";

    private static final String TABLE_NAME = "recipe_table";
    private static final String TABLE_NAME2 = "ingredient_table";
    private static final String TABLE_NAME3 = "recipe_and_ingredient_table";
    private static final String TABLE_NAME4 = "shopping_cart_table";
    private static final String TABLE_NAME5 = "recipe_folder_table";
    private static final String TABLE_NAME6 = "exported_recipes_table";

    private static final String COLUMN_RECIPE_ID = "ID";
    private static final String COLUMN_RECIPE_NAME = "RecipeName";
    private static final String COLUMN_RECIPE_HAS_INGREDIENTS = "RecipeHasIngredients";
    private static final String COLUMN_INGREDIENT_ID = "IngredientID";
    private static final String COLUMN_INGREDIENT_NAME = "IngredientName";
    private static final String COLUMN_INGREDIENT_RECIPE_ID = "ID";
    private static final String COLUMN_INGREDIENT_QUANTITY = "Quantity";
    private static final String COLUMN_INGREDIENT_MEASUREMENT_TYPE = "MeasurementType";
    private static final String COLUMN_RECIPE_FOLDER_NAME = "RecipeFolderName";
    private static final String COLUMN_RECIPE_DESCRIPTION = "RecipeDescription";
    private static final String COLUMN_RECIPE_FOLDER_ID = "FolderID";
    private static final String COLUMN_SHOPPING_CART_ID = "ShoppingCartID";
    private static final String COLUMN_SHOPPING_CART_FLAG = "ShoppingCartFlag";
    private static final String COLUMN_RECIPE_QUANTITY = "RecipeQuantity";
    private static final String COLUMN_EXPORTED_RECIPE_ID = "ExportedRecipeID";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 57);

    }

    private static final String createTable = "CREATE TABLE " + TABLE_NAME + " "
            + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RECIPE_NAME + " TEXT, "
            + COLUMN_RECIPE_DESCRIPTION + " TEXT, "
            + COLUMN_RECIPE_HAS_INGREDIENTS + " TEXT, "
            + COLUMN_RECIPE_FOLDER_ID + " INTEGER )";

    private static final String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " "
            + "( IngredientID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_INGREDIENT_NAME + " TEXT, " +
            COLUMN_INGREDIENT_QUANTITY + " REAL, " +
            COLUMN_INGREDIENT_MEASUREMENT_TYPE + " TEXT, " +
            COLUMN_SHOPPING_CART_FLAG + " TEXT, " +
            COLUMN_INGREDIENT_RECIPE_ID + " INTEGER )";

    private static final String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " "
            + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPE_NAME + " TEXT )";

    private static final String createTable4 = "CREATE TABLE " + TABLE_NAME4 + " "
            + "( ShoppingCartID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPE_NAME + " TEXT, " +
            COLUMN_INGREDIENT_NAME + " TEXT, " +
            COLUMN_INGREDIENT_QUANTITY + " REAL, " +
            COLUMN_RECIPE_QUANTITY + " INTEGER, " +
            COLUMN_INGREDIENT_MEASUREMENT_TYPE + " TEXT, " +
            COLUMN_RECIPE_HAS_INGREDIENTS + " TEXT ) ";


    private static final String createTable5 = "CREATE TABLE " + TABLE_NAME5 + " "
            + "(" + COLUMN_RECIPE_FOLDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPE_FOLDER_NAME + " TEXT ) ";

    private static final String createTable6 = "CREATE TABLE " + TABLE_NAME6 + " "
            + "(" + COLUMN_EXPORTED_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_RECIPE_NAME + " TEXT, " +
            COLUMN_INGREDIENT_NAME + " TEXT, " +
            COLUMN_INGREDIENT_QUANTITY + " TEXT, " +
            COLUMN_INGREDIENT_MEASUREMENT_TYPE + " TEXT )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        db.execSQL(createTable5);
        db.execSQL(createTable6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME5);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME6);
        onCreate(db);
    }

    public boolean addRecipeData(String item, String description, String hasIngredients, int FolderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_NAME, item);
        contentValues.put(COLUMN_RECIPE_DESCRIPTION, description);
        contentValues.put(COLUMN_RECIPE_HAS_INGREDIENTS, hasIngredients);
        contentValues.put(COLUMN_RECIPE_FOLDER_ID, FolderID);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addIngredientData(String item, double quantity, String measurementType, String scf, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_INGREDIENT_NAME, item);
        contentValues.put(COLUMN_INGREDIENT_QUANTITY, quantity);
        contentValues.put(COLUMN_INGREDIENT_MEASUREMENT_TYPE, measurementType);
        contentValues.put(COLUMN_SHOPPING_CART_FLAG, scf);
        contentValues.put(COLUMN_INGREDIENT_RECIPE_ID, id);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME2);

        long result = db.insert(TABLE_NAME2, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addRecipeFolderData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_FOLDER_NAME, item);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME5);
        long result = db.insert(TABLE_NAME5, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addExportedRecipeData(String item, String ingredientName, String ingredientQuantity, String measurementType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_NAME, item);
        contentValues.put(COLUMN_INGREDIENT_NAME, ingredientName);
        contentValues.put(COLUMN_INGREDIENT_QUANTITY, ingredientQuantity);
        contentValues.put(COLUMN_INGREDIENT_MEASUREMENT_TYPE, measurementType);
        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME6);
        long result = db.insert(TABLE_NAME6, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getExportedShoppingCartData(String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME6 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getExportedShoppingCartInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME6;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteExportedRecipeRow(String recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME6 + " WHERE "
                + COLUMN_EXPORTED_RECIPE_ID + " = '" + recipeId + "'";
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }

    public boolean addShoppingCartData(String recipe, String item, double quantity, String measurementType, int recipeQuantity, String hasIngredients) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_NAME, recipe);
        contentValues.put(COLUMN_INGREDIENT_NAME, item);
        contentValues.put(COLUMN_INGREDIENT_QUANTITY, quantity);
        contentValues.put(COLUMN_RECIPE_QUANTITY, recipeQuantity);
        contentValues.put(COLUMN_INGREDIENT_MEASUREMENT_TYPE, measurementType);
        contentValues.put(COLUMN_RECIPE_HAS_INGREDIENTS, hasIngredients);
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

    public Cursor getRecipeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIngredientData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getShoppingCartData() {
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
        return data;
    }

    public Cursor getRecipeFolderDataRow(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME5;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipeDataRowSubstr(String recipeString) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_ID + "," + COLUMN_RECIPE_NAME + " FROM " + TABLE_NAME +
                " WHERE INSTR(" + COLUMN_RECIPE_NAME + ",'" + recipeString + "')>" + 0;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipeFolderData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME5;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipeAndIngredientData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME3;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIngredientsBasedOnRecipeData(int RecipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_INGREDIENT_RECIPE_ID + " = '" + RecipeId + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipesByFolder(int FolderID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPE_FOLDER_ID + " = '" + FolderID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipesByIngredientID(int recipeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_INGREDIENT_RECIPE_ID + " = '" + recipeID + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipeItemID(String RecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPE_NAME + " = '" + RecipeName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipeHasIngredients(String RecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT " + COLUMN_RECIPE_HAS_INGREDIENTS + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPE_NAME + " = '" + RecipeName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecipeDescription(String RecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_DESCRIPTION + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_RECIPE_NAME + " = '" + RecipeName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getIngredientItemID(String IngredientName, int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_ID + " FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'"
                + " AND " + COLUMN_INGREDIENT_RECIPE_ID + " = '" + ID + "'";
        ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Get Ingredient/Recipe ID from Ingredient Table for a Given Ingredient Name (used in Shopping Cart dialog box)
     *
     * @param IngredientName
     * @return
     */
    public Cursor getIngredientRecipeItemID(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_RECIPE_ID + " FROM " + TABLE_NAME2 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Select number of Ingredients in the Recipe
     *
     * @param IngredientName
     * @return
     */
    public int getIngredientRecipeItemIDCount(String IngredientName) {
        String countQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE "
                + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * Select number of rows in Table5
     *
     * @return
     */
    public int getRecipeFolderTableCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME5;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getShoppingCartItemID(String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_SHOPPING_CART_ID + " FROM " + TABLE_NAME4 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateRecipeName(String newRecipeName, int id, String oldRecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPE_NAME +
                " = '" + newRecipeName + "' WHERE " + COLUMN_RECIPE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPE_NAME + " = '" + oldRecipeName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newRecipeName);
        db.execSQL(query);
    }

    public void updateRecipeDescription(String newDescription, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String newDescriptionTwo = "";

        if (newDescription.contains("'")) {
            int firstIndex = newDescription.indexOf("'");
            String first = newDescription.substring(0, firstIndex);
            String second = newDescription.substring(firstIndex + 1, newDescription.length());
            newDescriptionTwo = first + second;

            String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPE_DESCRIPTION +
                    " = '" + newDescriptionTwo + "' WHERE " + COLUMN_RECIPE_ID + " = '" + id + "'";
            Log.d(TAG, "updateName: query: " + query);
            Log.d(TAG, "updateName: Setting name to " + newDescriptionTwo);
            db.execSQL(query);

        } else {

            String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_RECIPE_DESCRIPTION +
                    " = '" + newDescription + "' WHERE " + COLUMN_RECIPE_ID + " = '" + id + "'";
            Log.d(TAG, "updateName: query: " + query);
            Log.d(TAG, "updateName: Setting name to " + newDescription);
            db.execSQL(query);
        }
    }

    public void updateIngredientName(String newIngredientName, int id, String oldIngredientName, int RecipeID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_INGREDIENT_NAME +
                " = '" + newIngredientName + "' WHERE " + COLUMN_INGREDIENT_ID + " = '" + id + "'" +
                " AND " + COLUMN_INGREDIENT_NAME + " = '" + oldIngredientName + "'" +
                " AND " + COLUMN_INGREDIENT_RECIPE_ID + " = '" + RecipeID + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newIngredientName);
        db.execSQL(query);
    }

    public Cursor getShoppingCartIngredient(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_INGREDIENT_NAME + " FROM " + TABLE_NAME4 +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateShoppingCartName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_RECIPE_NAME +
                " = '" + newName + "' WHERE " + COLUMN_RECIPE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPE_NAME + " =  '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: setting name to " + newName);
    }

    public void deleteRecipeName(int id, String RecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_RECIPE_ID + " = '" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + RecipeName + " from database.");
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
                + COLUMN_INGREDIENT_RECIPE_ID + " = '" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting Ingredients for recipe ID: " + id + "from database.");
        db.execSQL(query);
    }

    public void deleteShoppingCartRecipe(int id, String RecipeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME4 + " WHERE "
                + COLUMN_RECIPE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPE_NAME + " = '" + RecipeName + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + RecipeName + " from shopping cart database.");
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

    public void deleteShoppingCartRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME4 + " WHERE "
                + COLUMN_SHOPPING_CART_ID + " = '" + id + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + id + " from shopping cart database.");
        db.execSQL(query);
    }

    public void deleteShoppingCartRecipeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME4;
        Log.d(TAG, "deleted all values of shopping cart list....");
        db.execSQL(query);
    }

    //Counts number of recipes in the list
    public int getRecipeCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Select individual recipe from the list
    public int getIndivRecipeCount(int id, String RecipeName) {
        String countQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_RECIPE_ID + " = '" + id + "'" +
                " AND " + COLUMN_RECIPE_NAME + " = '" + RecipeName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Select number of Ingredients in the Recipe
    public int getIngredientCount(int id) {
        String countQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE "
                + COLUMN_RECIPE_ID + " = '" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getExportedRecipeCount(String recipeName) {
        String countQuery = "SELECT * FROM " + TABLE_NAME6 + " WHERE "
                + COLUMN_RECIPE_NAME + " = '" + recipeName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor getShoppingCartDialogList(String IngredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_NAME + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + IngredientName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateShoppingCartRecipeCount(int quantity, String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME4 + " SET " + COLUMN_RECIPE_QUANTITY +
                " = '" + quantity + "'" + " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Log.d(TAG, "updateName: query: " + query);
        db.execSQL(query);
    }

    public int getShoppingCartCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getRecipeHasIngredientCount(String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientName + "'";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
