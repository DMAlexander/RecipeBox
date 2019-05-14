package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

//Screen Where User can Add Recipies
public class RecipieScreen extends AppCompatActivity {

    private static final String TAG = "Recipie Screen";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnViewData;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_screen);



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
/*
    

}
