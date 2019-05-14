package com.example.devin.recipiebox.database.model;

public class RecipieDatabase {

    public static final String TABLE_NAME = "recipie";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPIENAME = "recipieName";
    public static final String COLUMN_INGREDIENTNAME = "ingredientName";
    public static final String COLUMN_INGREDIENTQUANTITY = "ingredientQuantity";
    public static final String COLUMN_RECIPIEPRICE = "recipiePrice";

    //Create table SQL Query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECIPIENAME + " TEXT, "
                + COLUMN_INGREDIENTNAME + " TEXT, "
                + COLUMN_INGREDIENTQUANTITY + "TEXT, "
                + COLUMN_RECIPIEPRICE + "TEXT "
                + ")";

    private int id;
    private String recipieName;
    private String ingredientName;
    private int ingredientQuantity;
    private String recipiePrice;

    public RecipieDatabase() {

    }

    public RecipieDatabase(int id, String recipieName, String ingredientName, int ingredientQuantity, String recipiePrice) {
        this.id = id;
        this.recipieName = recipieName;
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
        this.recipiePrice = recipiePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipieName() {
        return recipieName;
    }

    public void setRecipieName(String recipieName) {
        this.recipieName = recipieName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(int ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getRecipiePrice() {
        return recipiePrice;
    }

    public void setRecipiePrice(String recipiePrice) {
        this.recipiePrice = recipiePrice;
    }
}
