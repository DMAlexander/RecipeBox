package com.example.devin.recipiebox.view.Ingredient;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfo;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfoAdapter;
import com.example.devin.recipiebox.view.Recipie.MainActivity;

import java.util.ArrayList;
import java.util.Collections;

//EditDataActivity's future home...
public class IngredientScreen extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

//    private Button btnSave,btnDelete,btnIngredientAdd, addImage;
    private Button btnIngredientAdd, btnIngredientInfo;
//    private EditText editable_recipie_item, editable_ingredient_item;
    private EditText editable_ingredient_item;
//    private ListView mListView;
 //   private ImageView imageView;

    DatabaseHelper mDatabaseHelper;
    private IngredientEditAdapter mAdapter;

    private String selectedRecipieName;
    private int selectedRecipieID;
    private int selectedIngredientID;
    private static final int PICK_IMAGE = 100;
    private Spinner spinner, spinner2;
    Uri imageUri;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //      setContentView(R.layout.activity_ingredient_screen);
        setContentView(R.layout.activity_ingredient_screen);
 //       btnSave = (Button) findViewById(R.id.btnSave);
 //       btnDelete = (Button) findViewById(R.id.btnDelete);
        btnIngredientAdd = (Button) findViewById(R.id.btnIngredientAdd);
 //       addImage = (Button) findViewById(R.id.addImage);
 //       imageView = (ImageView) findViewById(R.id.imageView);
 //       editable_recipie_item = (EditText) findViewById(R.id.editable_recipie_item);
        editable_ingredient_item = (EditText) findViewById(R.id.editable_ingredient_item);
 //       mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        setButtons();

     //   spinner.setOnItemSelectedListener(this);
 //       spinner2.setOnItemSelectedListener(this);

//        populateIngredientListView();
        //get intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();
        //get the itemID we passed as extra (From main screen)
        selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);
        //get name we passed as an extra (From main screen)
  //      selectedRecipieName = receivedIntent.getStringExtra("RecipieName");
    //    //get the itemId we passed as an extra (From Ingredient Info screen)
        selectedIngredientID = receivedIntent.getIntExtra("IngredientId", -1);
        selectedRecipieName = receivedIntent.getStringExtra("RecipieName");
        //set text to show current selected name
//        editable_recipie_item.setText(selectedRecipieName);
        getSupportActionBar().setTitle(selectedRecipieName);

        //Recycler View Declaration...
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IngredientEditAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new IngredientEditAdapter.OnItemClickListener() {
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
        }); */
/*
        btnIngredientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                String newEntry2 = spinner.getSelectedItem().toString();
                int num = 1;
                num = Integer.parseInt(newEntry2);
                String newEntry3 = spinner2.getSelectedItem().toString();
                String newEntry = editable_ingredient_item.getText().toString();
                if (editable_ingredient_item.length() != 0) {
//                    mDatabaseHelper.addIngredientData(newEntry, num, newEntry3, selectedRecipieID);
                    mDatabaseHelper.addIngredientData(newEntry, num, newEntry3, itemID);
//                    toastMessage("Values added: " + newEntry + " , " + num + ", " + newEntry3 + ", " + selectedRecipieID);
                    toastMessage("Values added: " + newEntry + " , " + num + ", " + newEntry3 + ", " + itemID);
                    toastMessage("Data successfully inserted!");
                    finish();
                    startActivity(getIntent());
                } else {
                    toastMessage("Put something in the text field!");
                }
            }
        });
        */

        //Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.measurements_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears...
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String measurementQuantity = adapterView.getItemAtPosition(i).toString();
                int num = 1;
                Log.d(TAG,"onItemClick: you clicked on: " + measurementQuantity);
 //               if(!"".equals(measurementQuantity)) {
 //                   num = Integer.parseInt(measurementQuantity);
 //                   Log.d(TAG, "String measurementQuantity: is" + measurementQuantity);
 //               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.measurement_type_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String measurementType = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG,"onItemClick: you clicked on: " + measurementType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    //Everything in this view should be ingredient related....
    /*
    private void populateIngredientListView() {
        Intent receivedIntent = getIntent();
        //get the itemID we passed as extra
        selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);
        //get name we passed as an extra
        selectedRecipieName = receivedIntent.getStringExtra("RecipieName");

        Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
        int itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if (itemID > 1) {
            Log.d(TAG, "The RecipieID is: " + itemID);
        }

        Log.d(TAG, "populate ingredient listview: Displaying data in the ingredient listview");
 //       Log.d(TAG, "This data is based on recipieID: " + selectedRecipieID + " And recipieName: " + selectedRecipieName);
        Log.d(TAG, "This data is based on recipieID: " + selectedRecipieID + " and recipieName: " + itemID);
        //get data and append to a list
  //      data = mDatabaseHelper.getIngredientsBasedOnRecipieData(selectedRecipieID);
        data = mDatabaseHelper.getIngredientsBasedOnRecipieData(itemID);
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
    */
/*
    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addIngredientData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    } */

    public void insertItem(String ingredientName) {

        Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        String newEntry2 = spinner.getSelectedItem().toString();


        int num = 1;
        num = Integer.parseInt(newEntry2);
        String newEntry3 = spinner2.getSelectedItem().toString();
  //      String newEntry = editable_ingredient_item.getText().toString();
        if (editable_ingredient_item != null) {
            boolean insertData = mDatabaseHelper.addIngredientData(ingredientName, num, newEntry3, itemID); //we need all 3 parameters here...
            if (insertData) {
                toastMessage("Data successfully inserted!");
                mAdapter.notifyDataSetChanged();
                finish();
                startActivity(getIntent());
            } else {
                toastMessage("Something went wrong!");
            }
        } else {
            toastMessage("Put something in the text field!");
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(IngredientScreen.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, ingredientName);
                Toast.makeText(IngredientScreen.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(IngredientScreen.this, "Sorry",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void setButtons() {
        btnIngredientAdd = findViewById(R.id.btnIngredientAdd);
        btnIngredientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientName = editable_ingredient_item.getText().toString();
                if (editable_ingredient_item.length() != 0) {
                    insertItem(ingredientName);
                    editable_ingredient_item.setText("");
                } else {
                    toastMessage("Please put something in the textbox!");
                }
            }
        });

        btnIngredientInfo = findViewById(R.id.btnIngredientInfo);
        btnIngredientInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if(itemID > 1) {
                    Log.d(TAG, "The RecipieID is: " + itemID);
                }
       //         return mDatabaseHelper.getIngredientsBasedOnRecipieData(itemID);

                Intent intent = new Intent(IngredientScreen.this, IngredientInfo.class);
                intent.putExtra("RecipieId", itemID);
                intent.putExtra("RecipieName", selectedRecipieName);
                Log.d(TAG, "The RecipieId is: " + itemID);
                startActivity(intent);
            }
        });

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

    //Method in progress..
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_ingredient_item, null);
        recyclerView.addView(rowView, recyclerView.getChildCount()-1);
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
