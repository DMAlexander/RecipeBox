package com.example.devin.recipiebox.view.PublishedIngredient;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientEditAdapter;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.Recipie.MainActivity;

import java.util.ArrayList;
import java.util.Collections;

//Class that allows for deleting and saving information-related data...
public class IngredientInfo extends AppCompatActivity {

    private static final String TAG = "IngredientInfo";

    DatabaseHelper mDatabaseHelper;
//    private Button btnSave,btnDelete;
//    private EditText editable_item;
    private TextView editable_item;
    private IngredientInfoAdapter mAdapter;
    private String selectedIngredientName;
    private String selectedRecipieName;
    private int selectedIngredientID;
    private int selectedRecipieID;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_info);
        //      btnSave = (Button) findViewById(R.id.btnSave);
        //      btnDelete = (Button) findViewById(R.id.btnDelete);
//        editable_item = (EditText) findViewById(R.id.editable_item);
        editable_item = (TextView) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);
        mListView = (ListView) findViewById(R.id.listView);
 //       populateListView();



        //get intent extra from IngredientScreenActivity
        Intent recievedIntent = getIntent();
        //get the itemID we passed in as an extra
        selectedIngredientID = recievedIntent.getIntExtra("IngredientId", -1);
        //get name we passed in as an extra
        selectedIngredientName = recievedIntent.getStringExtra("IngredientName");
        selectedRecipieName = recievedIntent.getStringExtra("RecipieName");

        //get the recipieID which is attached to the table...
        selectedRecipieID = recievedIntent.getIntExtra("RecipieId", -1);
        //set text to show current selected name
        editable_item.setText(selectedRecipieName);

        //Recycler View Declaration...
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IngredientInfoAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new IngredientInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position, String ingredientName) {
                makeDialog(position, ingredientName);
            }
        });

/*
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
/*
                Intent intent = new Intent(IngredientInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
*/
/*
    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the listView");
        Intent receivedIntent = getIntent();
        selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);

        Cursor data = mDatabaseHelper.getIngredientsBasedOnRecipieData(selectedRecipieID);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));
   //         listData.add(data.getString(1));
   //         listData.add(data.getString(1));
   //         listData.add(data.getString(2));
        }
        Collections.sort(listData);
        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.ingredientrowlayout, R.id.textView1,listData);
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }
*/
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void removeItem(int position, String ingredientName) {

        Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
        int recipieID = -1;
        while (data.moveToNext()) {
            recipieID = data.getInt(0);
        }
        data = mDatabaseHelper.getIngredientItemID(ingredientName, recipieID);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        Log.d(TAG, "ingredientId: " + itemID + " and ingredient name is: " + ingredientName);
        mDatabaseHelper.deleteIngredientName(itemID, ingredientName);

        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        toastMessage("Removed from database");
    }

    public void makeDialog(final int position, final String ingredientName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IngredientInfo.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, ingredientName);
                Toast.makeText(IngredientInfo.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(IngredientInfo.this, "Sorry",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private Cursor getAllItems() {
        Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > 1) {
            Log.d(TAG, "The RecipieID is: " + itemID);
        }
        return mDatabaseHelper.getIngredientsBasedOnRecipieData(itemID);
    }

}