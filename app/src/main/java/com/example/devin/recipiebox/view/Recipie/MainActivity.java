package com.example.devin.recipiebox.view.Recipie;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.database.model.RecipieDatabase;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Screen that displays list of recipies --> ListDataActivity
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";
    private DatabaseHelper mDatabaseHelper;
  //  private SQLiteDatabase mDatabaseHelper;
    private RecipeAdapter mAdapter;
    private Button buttonInsert;
    private EditText editTextInsert;

   private ListView mListView, ch1;
    private Button btnNavigate, btnShoppingCart, btnClearShoppingCart;
    private EditText editable_recipie_counter_item;
//    private ImageView;

    ArrayList<String> selectedItems = new ArrayList<>();
    private ArrayList<RecipeItem> mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   //     DatabaseHelper dbHelper = new DatabaseHelper(this);
   //     mDatabaseHelper = dbHelper.getWritableDatabase();
        mDatabaseHelper = new DatabaseHelper(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecipeAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

//        mListView = (ListView) findViewById(R.id.listView);
//        mDatabaseHelper = new DatabaseHelper(this);
 //       btnNavigate = (Button) findViewById(R.id.btnNavigate);
 //       btnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);
 //       btnClearShoppingCart = (Button) findViewById(R.id.btnClearShoppingCart);
 //       editable_recipie_counter_item = (EditText) findViewById(R.id.editable_recipie_counter_item);

 //       createExampleList(); //just to throw some data in there...
        buildRecyclerView(); //buildRecyclerView();
        setButtons();

//        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //      mListView.setLongClickable(true);
    //    populateListView();

        //     configureNextButton();
        //    configureShoppingCartButton();



    /*
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipieScreen.class);
                startActivity(intent);
            }
        }); */
/*
        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intnt shoppingCartIntent = new Intent(MainActivity.this, ShoppingCartList.class);
                startActivity(eshoppingCartIntent);
            }

        });
*/

/*
        btnClearShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteShoppingCartRecipieData(); //delete all shopping cart recipies, clearing cart
            }
        });
        */
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
/*
    public void insertItem(int position) {
//        mRecipeList.add(position, new RecipeItem(R.drawable.ic_delete, "New Item At Position" + position, "This is line 2"));
        mRecipeList.add(new RecipeItem(RecipieName)); //NOTE: The mDatabaseHelper Add method will likely go here...
        mAdapter.notifyItemInserted(position);
    }
    */

    public void insertItem(String recipieName) {
  //      mRecipeList.add(new RecipieItem(RecipieName));
        boolean insertData = mDatabaseHelper.addRecipieData(recipieName);

        if (insertData) {
            toastMessage("Data successfully inserted!");
        } else {
            toastMessage("Something went wrong!");
        }
    }

    public void removeItem(int position) {
//        mRecipeList.remove(position);
//        mAdapter.notifyItemRemoved(position);
 //       mDatabaseHelper.deleteRecipieName(selectedRecipieID, selectedRecipieName);
        toastMessage("Removed from database");
    }

    public void changeItem(int position, String text) {
        mRecipeList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

/*
    public void createExampleList() {
        mRecipeList = new ArrayList<>();
        /*
        mRecipeList.add(new RecipeItem(R.drawable.ic_android, "Line 1", "Line 2"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_delete, "Line 3", "Line 4"));
        mRecipeList.add(new RecipeItem(R.drawable.ic_android, "Line 5", "Line 6"));
        */
/*
   //     mRecipeList.add(new RecipeItem(RecipieName));
        Cursor data = mDatabaseHelper.getRecipieData();
  //      ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
//            listData.add(data.getString(1));
            mRecipeList.add(new RecipeItem(data.getString(1)));
        }
  //      Collections.sort(listData);
        //Create list adapter and set adapter
     //   ListAdapter adapter = new ArrayAdapter<>()(this, R.layout.rowlayout, listData);
*/
//    }



/*
    private void populateListView() { //Original populateListView
        Log.d(TAG, "populateListView: Displaying data in the ListView");
        //get data and append to a list

        Cursor data = mDatabaseHelper.getRecipieData(); // get all recipies
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get value from database in column then add it to arraylist
            listData.add(data.getString(1));
        }
        Collections.sort(listData);
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
*/

    private void buildRecyclerView() {

        Log.d(TAG, "populateRecyclerView: Displaying data in the RecyclerView");

 //       Cursor data = mDatabaseHelper.getRecipieData();
 //       ArrayList<String> listData = new ArrayList<>();
  //      while (data.moveToNext()) {
  //          listData.add(data.getString(1));
   //     }
    //    Collections.sort(listData);

    //    mRecyclerView.setLayoutManager(mLayoutManager);
   //     mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*
                String RecipieName =
    //            String RecipieName = position.get
    //            String RecipieName = adapterView.getItemAtPosition(i).toString();
//                String RecipieName = mRecyclerView.get
    //            View v = mRecyclerView.findViewById(position);
    //            String RecipieName = mRecipeList.get(position).toString();
    //            String RecipieName =
   //             Log.d(TAG, "onItemClick: You clicked on " + RecipieName);
   //             Cursor data = mDatabaseHelper.getRecipieItemID(RecipieName);
                int itemID = -1;
    //            while (data.moveToNext()) {
  //                  itemID = data.getInt(0);
  //              }
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent editScreenIntent = new Intent(MainActivity.this, IngredientScreen.class);
                    editScreenIntent.putExtra("RecipieId", itemID);
      //              editScreenIntent.putExtra("RecipieName", RecipieName);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("No ID associated with that recipieName");
                }

                //        mRecipeList.get(position).changeText1(text);
            */
          //      changeItem(position, "Clicked!");
//                RecipeAdapter.RecipeViewHolder
           //     String recipieName = ((TextView) itemView

            }

            @Override
            public void onDeleteClick(int position) {
                makeDialog(position);
      //          removeItem(position);
            }
        });
    }

    public void makeDialog(final int position) {
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete Recipie");
        builder.setMessage("Are you sure you want to delete the recipie?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position);
                Toast.makeText(MainActivity.this, "Thanks!",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "I will try hard and make better videos!",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void setButtons() {
        buttonInsert = findViewById(R.id.button_insert);
        editTextInsert = findViewById(R.id.edittext_insert);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  int position = Integer.parseInt(editTextInsert.getText().toString());
                //   insertItem(position);
                String recipieName = editTextInsert.getText().toString();
                if (editTextInsert.length() != 0) {
                    insertItem(recipieName);
                    editTextInsert.setText("");
                } else {
                    toastMessage("Please put something in the textbox!");
                }
            }
        });
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getRecipieData();
    }

    private void addItem() {

        String recipieName = editTextInsert.getText().toString();
        mDatabaseHelper.addRecipieData(recipieName);
   //     mAdapter.swapCursor(getAllItems());

        editTextInsert.getText().clear();


    }

/*
        public void addShoppingCartData(String newEntry){
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