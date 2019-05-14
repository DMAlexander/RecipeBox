package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

//Screen Where User can Add Recipies
public class RecipieScreen extends AppCompatActivity {

    private static final String TAG = "MainActivity/Recipie Screen";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_screen);
        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    AddData(newEntry);
                    editText.setText("");
                } else {
                    toastMessage("Put something in the text field!");
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipieScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

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
    
