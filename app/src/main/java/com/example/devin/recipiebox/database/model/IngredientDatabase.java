package com.example.devin.recipiebox.database.model;

public class IngredientDatabase {

    public static final String TABLE_NAME = "ingredient";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_INGREDIENTNAME = "ingredientName";
    public static final String COLUMN_INDIVPRICE = "indivPrice";
    public static final String COLUMN_BULKPRICE = "bulkPrice";

    public static final String CREATE_TABLE =
            "CREATE TALBE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_INGREDIENTNAME + " TEXT, "
                + COLUMN_INDIVPRICE + " TEXT, "
                + COLUMN_BULKPRICE + " TEXT "
                + ")";


    private int id;
    private String ingredientName;
    private String indivPrice;
    private String bulkPrice;

    public IngredientDatabase() {

    }

    public IngredientDatabase(int id, String ingredientName, String indivPrice, String bulkPrice) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.indivPrice = indivPrice;
        this.bulkPrice = bulkPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
