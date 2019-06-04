package com.example.devin.recipiebox.view.Ingredient;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;

import java.util.ArrayList;
import java.util.Collections;

//EditDataActivity's future home...
public class IngredientScreen extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete,btnIngredientAdd;
    private EditText editable_recipie_item, editable_ingredient_item;
    private ListView mListView;

    DatabaseHelper mDatabaseHelper;

    private String selectedRecipieName;
    private int selectedRecipieID;
    private int selectedIngredientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //      setContentView(R.layout.activity_ingredient_screen);
        setContentView(R.layout.activity_ingredient_screen);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnIngredientAdd = (Button) findViewById(R.id.btnIngredientAdd);
        editable_recipie_item = (EditText) findViewById(R.id.editable_recipie_item);
        editable_ingredient_item = (EditText) findViewById(R.id.editable_ingredient_item);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        populateIngredientListView();

        //get intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //get the itemID we passed as extra (From main screen)
        selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);

        //get name we passed as an extra (From main screen)
  //      selectedRecipieName = receivedIntent.getStringExtra("RecipieName");

    //    //get the itemId we passed as an extra (From Ingredient Info screen)
        selectedIngredientID = receivedIntent.getIntExtra("IngredientId", -1);

        //set text to show current selected name
        editable_recipie_item.setText(selectedRecipieName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_recipie_item.getText().toString();
                if (!item.equals("")) {
                    mDatabaseHelper.updateRecipieName(item, selectedRecipieID, selectedRecipieName);
                    Intent intent = new Intent(IngredientScreen.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    toastMessage("Enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteRecipieName(selectedRecipieID, selectedRecipieName);
                editable_recipie_item.setText("");
                toastMessage("removed from database");
                Intent intent = new Intent(IngredientScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnIngredientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editable_ingredient_item.getText().toString();
                if (editable_ingredient_item.length() != 0) {
                    mDatabaseHelper.addIngredientData(newEntry, selectedRecipieID);
                    toastMessage("Data successfully inserted!");
                    finish();
                    startActivity(getIntent());
                } else {
                    toastMessage("Put something in the text field!");
                }
            }
        });
    }
    //Everything in this view should be ingredient related....
    private void populateIngredientListView() {
        Intent receivedIntent = getIntent();
        //get the itemID we passed as extra
        selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);
        //get name we passed as an extra
        selectedRecipieName = receivedIntent.getStringExtra("RecipieName");

        Log.d(TAG, "populate ingredient listview: Displaying data in the ingredient listview");
 //       Log.d(TAG, "This data is based on recipieID: " + selectedRecipieID + " And recipieName: " + selectedRecipieName);
        Log.d(TAG, "This data is based on recipieID: " + selectedRecipieID + " and recipieName: " + selectedRecipieName);
        //get data and append to a list
        Cursor data = mDatabaseHelper.getIngredientsBasedOnRecipieData(selectedRecipieID);
//        Cursor data = mDatabaseHelper.getIngredientData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get value from database in column then add it to arraylist...
            listData.add(data.getString(1));
        }
        Collections.sort(listData);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
        //set onItemClickListener to the listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String IngredientName = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemCLick: you clicked on " + IngredientName);
                Cursor data = mDatabaseHelper.getIngredientItemID(IngredientName, selectedRecipieID); //get id associated with IngredientName;
                int itemID = -1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1) {
                    Log.d(TAG, "onItemClick: The IngredientID is " + itemID);
                    Intent ingredientScreenintent = new Intent(IngredientScreen.this, IngredientInfo.class);
                    ingredientScreenintent.putExtra("IngredientId",itemID);
                    ingredientScreenintent.putExtra("IngredientName",IngredientName);
                    ingredientScreenintent.putExtra("RecipieId", selectedRecipieID);
                    startActivity(ingredientScreenintent);
                } else {
                    toastMessage("No ID associated with that IngredientName");
                }
            }
        });

    }
/*
    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addIngredientData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    } */

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
