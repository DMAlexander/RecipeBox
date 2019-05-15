package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

//EditDataActivity's future home...
public class IngredientScreen extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //      setContentView(R.layout.activity_ingredient_screen);
        setContentView(R.layout.activity_ingredient_screen);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        //get intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //get the itemID we passed as extra
        selectedID = receivedIntent.getIntExtra("id", -1);

        //get name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //set text to show current selected name
        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if (!item.equals("")) {
                    mDatabaseHelper.updateName(item, selectedID, selectedName);
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
                mDatabaseHelper.deleteName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("removed from database");

                Intent intent = new Intent(IngredientScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
        /*
        TextView text1 = (TextView) findViewById(R.id.ingredientName);
        TextView text2 = (TextView) findViewById(R.id.indivPrice);
        TextView text3 = (TextView) findViewById(R.id.bulkPrice);
        EditText edit1 = (EditText) findViewById(R.id.editText);
        EditText edit2 = (EditText) findViewById(R.id.editText2);
        EditText edit3 = (EditText) findViewById(R.id.editText3);

        configureSaveButton();
        */
    }
/*
    private void configureSaveButton() {
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                startActivity(new Intent(IngredientScreen.this, RecipieScreen.class));
            }

        });
    } */
}
