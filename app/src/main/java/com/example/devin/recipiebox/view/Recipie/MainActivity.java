package com.example.devin.recipiebox.view.Recipie;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

import java.util.ArrayList;

//Screen that displays list of recipies --> ListDataActivity
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView, ch1;
    private Button btnNavigate, btnShoppingCart, btnClearShoppingCart;
    private EditText editable_recipie_counter_item;

    ArrayList<String> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        btnNavigate = (Button) findViewById(R.id.btnNavigate);
        btnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);
        btnClearShoppingCart = (Button) findViewById(R.id.btnClearShoppingCart);
        editable_recipie_counter_item = (EditText) findViewById(R.id.editable_recipie_counter_item);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //      mListView.setLongClickable(true);
        populateListView();
        //     configureNextButton();
        //    configureShoppingCartButton();

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipieScreen.class);
                startActivity(intent);
            }
        });

        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingCartIntent = new Intent(MainActivity.this, ShoppingCartList.class);
                startActivity(shoppingCartIntent);
            }

        });

        btnClearShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteShoppingCartRecipieData(); //delete all shopping cart recipies, clearing cart
            }
        });
/*
        editable_recipie_counter_item.setText("");
        btnCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = editable_recipie_counter_item.getText().toString();
                if(!num.equals("")) {

                }
            }
        });
*/

    }

    private void populateListView() { //Original populateListView
        Log.d(TAG, "populateListView: Displaying data in the ListView");
        //get data and append to a list

        Cursor data = mDatabaseHelper.getRecipieData(); // get all recipies
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get value from database in column then add it to arraylist
            listData.add(data.getString(1));
        }
        //Create list adapter and set adapter //possibly change android.R.layout to rowlayout...
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.rowlayout, listData);
        mListView.setAdapter(adapter);
        //     ListAdapter adapter1 = new ArrayAdapter<>(this, android.R.layout.)
        //Set onItemClickListener to the ListView

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String RecipieName = adapterView.getItemAtPosition(i).toString();
                String numValue = editable_recipie_counter_item.getText().toString();
                Log.d(TAG, "String numValue is: " + numValue);
                int num = 1;
                if(!"".equals(numValue)) {
                    num = Integer.parseInt(numValue);
                    Log.d(TAG, "String numValue is: " + numValue + " AND num is: " + num);
                }
                Log.d(TAG, "onItemClick: You clicked on " + RecipieName);
                Cursor data = mDatabaseHelper.getRecipieItemID(RecipieName); //get id associated with RecipieName
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    String selectedItem = ((TextView) view).getText().toString();
                    if (selectedItems.contains(selectedItem)) {
                        Log.d(TAG, "The selected Item ID is " + itemID + " And RecipieName is : " + RecipieName);
                        selectedItems.remove(selectedItem);
            //          mDatabaseHelper.deleteShoppingCartRecipie(itemID, RecipieName);
                        //                 deleteShoppingCartDelete(itemID, RecipieName);
                    } else {
                        Log.d(TAG, "The selected Item ID is: " + itemID + " And RecipieName is : " + RecipieName);
                        selectedItems.add(selectedItem);

    //                  Cursor data2 = mDatabaseHelper.getIngredientsBasedOnRecipieData(selectedRecipieID);
                        Cursor data2 = mDatabaseHelper.getIngredientsBasedOnRecipieData(itemID);
                        ArrayList<String> listData = new ArrayList<>(); //populate a list w/ingredients...
                        while(data2.moveToNext()) {
                            String ingredient = data2.getString(1);
                            listData.add(ingredient);
                        }

                        for (int j=0; j<listData.size();j++){
                            System.out.println(listData.get(j));
                            String ingredientName = listData.get(j);
                            addShoppingCartData(ingredientName);
                        }
                    }
                }
                toastMessage("Short click selected!");
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String RecipieName = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + RecipieName);
                Cursor data = mDatabaseHelper.getRecipieItemID(RecipieName); //get id associated with RecipieName
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1 ) {
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent editScreenIntent = new Intent(MainActivity.this, IngredientScreen.class);
                    editScreenIntent.putExtra("RecipieId", itemID);
                    editScreenIntent.putExtra("RecipieName", RecipieName);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated with that RecipieName");
                }
                toastMessage("Long click selected!");
                return true;
            }
        });
    }


        public void addShoppingCartData(String newEntry){
            boolean insertData = mDatabaseHelper.addShoppingCartData(newEntry);

            if (insertData) {
                toastMessage("Data will be added to shoppingCartList!");
            } else {
                toastMessage("Something went wrong!");
            }
        }

/*
    public void addShoppingCartData(int newEntry){
        boolean insertData = mDatabaseHelper.addShoppingCartData(newEntry);

        if (insertData) {
            toastMessage("Data will be added to shoppingCartList!");
        } else {
            toastMessage("Something went wrong!");
        }
    }
*/

        private void toastMessage (String message){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }