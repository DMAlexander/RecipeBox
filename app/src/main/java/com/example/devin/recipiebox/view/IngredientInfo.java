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

//Class that allows for deleting and saving information-related data...
public class IngredientInfo extends AppCompatActivity {

    private static final String TAG = "IngredientInfo";

    DatabaseHelper mDatabaseHelper;
    private Button btnSave,btnDelete;
    private EditText editable_item;

    private String selectedName;
    private int selectedID;


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
        selectedID = recievedIntent.getIntExtra("id", -1);
        //get name we passed in as an extra
        selectedName = recievedIntent.getStringExtra("name");
        //set text to show current selected name
        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                if(!item.equals("")) {
                    mDatabaseHelper.updateIngredientName(item, selectedID, selectedName);
                    Intent intent = new Intent(IngredientInfo.this, IngredientScreen.class);
                    startActivity(intent);
                } else {
                    toastMessage("Enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteIngredientName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
                Intent intent = new Intent(IngredientInfo.this, IngredientScreen.class);
                startActivity(intent);
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
