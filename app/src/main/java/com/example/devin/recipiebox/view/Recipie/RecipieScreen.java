package com.example.devin.recipiebox.view.Recipie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;

//Screen Where User can Add Recipies
public class RecipieScreen extends AppCompatActivity {

    private static final String TAG = "MainActivity/Recipie Screen";

    DatabaseHelper mDatabaseHelper;
    private Button btnRecipieAdd, btnIngredientAdd, btnViewRecipieData, btnViewIngredientData;
    private EditText editRecipieText, editIngredientText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        editRecipieText = (EditText) findViewById(R.id.editRecipieText);
        editIngredientText = (EditText) findViewById(R.id.editIngredientText);
        btnRecipieAdd = (Button) findViewById(R.id.btnRecipieAdd);
        btnIngredientAdd = (Button) findViewById(R.id.btnIngredientAdd);
        btnViewRecipieData = (Button) findViewById(R.id.btnRecipieView);
        btnViewIngredientData = (Button) findViewById(R.id.btnIngredientView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnRecipieAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editRecipieText.getText().toString();
                if (editRecipieText.length() != 0) {
                    AddRecipieData(newEntry);
                    editRecipieText.setText("");
                } else {
                    toastMessage("Put something in the text field!");
                }
            }
        });
/*
        btnIngredientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editIngredientText.getText().toString();
                if (editIngredientText.length() != 0) {
                    AddIngredientData(newEntry);
                    editIngredientText.setText("");
                } else {
                    toastMessage("Put something in the text field!");
                }
            }
        });
*/
        btnViewRecipieData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipieScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnViewIngredientData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipieScreen.this, IngredientScreen.class);
                startActivity(intent);
            }
        });
    }


    public void AddRecipieData(String newEntry) {
        boolean insertData = mDatabaseHelper.addRecipieData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }
/*
    public void AddIngredientData(String newEntry) {
        boolean insertData = mDatabaseHelper.addIngredientData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Somthing went wrong!");
        }
    } */

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



  //      configureAddNewIngredientButton();
  //      configureSaveButton();

    }
/*
    private void configureAddNewIngredientButton() {
        Button nextButton = (Button) findViewById(R.id.addNewIngredient);
        nextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(RecipieScreen.this,
                        IngredientScreen.class));
            }

        });
    }
*/
/*
    private void configureSaveButton() {
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                startActivity(new Intent(RecipieScreen.this, MainActivity.class));
            }

        });
    }
*/

