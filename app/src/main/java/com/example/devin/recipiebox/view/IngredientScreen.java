package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import com.example.devin.recipiebox.R;

public class IngredientScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_screen);

        TextView text1 = (TextView) findViewById(R.id.ingredientName);
        TextView text2 = (TextView) findViewById(R.id.indivPrice);
        TextView text3 = (TextView) findViewById(R.id.bulkPrice);
        EditText edit1 = (EditText) findViewById(R.id.editText);
        EditText edit2 = (EditText) findViewById(R.id.editText2);
        EditText edit3 = (EditText) findViewById(R.id.editText3);

        configureSaveButton();
    }

    private void configureSaveButton() {
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                startActivity(new Intent(IngredientScreen.this, RecipieScreen.class));
            }

        });
    }
}
