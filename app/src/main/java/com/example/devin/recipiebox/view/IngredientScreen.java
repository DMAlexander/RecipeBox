package com.example.devin.recipiebox.view;

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
import android.widget.TextView;
import android.widget.Toast;


import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;

import java.util.ArrayList;

//EditDataActivity's future home...
public class IngredientScreen extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;
    private EditText editable_item;
    private ListView mListView;

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
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        populateIngredientListView();

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
                    mDatabaseHelper.updateRecipieName(item, selectedID, selectedName);
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
                mDatabaseHelper.deleteRecipieName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("removed from database");
                Intent intent = new Intent(IngredientScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    //Everything in this view should be ingredient related....
    private void populateIngredientListView() {
        Log.d(TAG, "populate ingredient listview: Displaying data in the ingredient listview");
        //get data and append to a list
        Cursor data = mDatabaseHelper.getIngredientData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get value from database in column then add it to arraylist...
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
        //set onItemClickListener to the listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemCLick: you clicked on " + name);
                Cursor data = mDatabaseHelper.getIngredientItemID(name); //get id associated with name;
                int itemID = -1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent ingredientScreenintent = new Intent(IngredientScreen.this, IngredientInfo.class);
                    ingredientScreenintent.putExtra("id",itemID);
                    ingredientScreenintent.putExtra("name",name);
                    startActivity(ingredientScreenintent);
                } else {
                    toastMessage("No ID associated with that name");
                }
            }
        });

    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addIngredientData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
