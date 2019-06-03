package com.example.devin.recipiebox.view.Ingredient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;

//Class that allows for deleting and saving information-related data...
public class IngredientInfo extends AppCompatActivity {

    private static final String TAG = "IngredientInfo";

    DatabaseHelper mDatabaseHelper;
    private Button btnSave,btnDelete;
    private EditText editable_item;

    private String selectedIngredientName;
    private int selectedIngredientID;
    private int selectedRecipieID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_info);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        //get intent extra from IngredientScreenActivity
        Intent recievedIntent = getIntent();
        //get the itemID we passed in as an extra
        selectedIngredientID = recievedIntent.getIntExtra("IngredientId", -1);
        //get name we passed in as an extra
        selectedIngredientName = recievedIntent.getStringExtra("IngredientName");
        //get the recipieID which is attached to the table...
        selectedRecipieID = recievedIntent.getIntExtra("RecipieId", -1);
        //set text to show current selected name
        editable_item.setText(selectedIngredientName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")) {
                    mDatabaseHelper.updateIngredientName(item, selectedIngredientID, selectedIngredientName, selectedRecipieID);
                    Log.d(TAG, "item is: " + item + ", " + "selectedIngredientID is: " + selectedIngredientID);
                    Log.d(TAG, "selectedName is: " + selectedIngredientName + " selectedRecipieID is: " + selectedRecipieID);
             //       Intent intent = new Intent(IngredientInfo.this, IngredientScreen.class);
                    Intent intent = new Intent(IngredientInfo.this, MainActivity.class);
                    intent.putExtra("IngredientId", selectedIngredientID);
                    intent.putExtra("IngredientName", selectedIngredientName);
                    intent.putExtra("RecipieId", selectedRecipieID);
                    startActivity(intent);
                } else {
                    toastMessage("Enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteIngredientName(selectedIngredientID, selectedIngredientName);
                editable_item.setText("");
                toastMessage("removed from database");
                /*
                Intent intent = new Intent(IngredientInfo.this, IngredientScreen.class);
                intent.putExtra("IngredientId", selectedIngredientID);
                intent.putExtra("IngredientName", selectedIngredientName);
                intent.putExtra("RecipieId", selectedRecipieID);
                */
                Intent intent = new Intent(IngredientInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
