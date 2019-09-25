package com.example.devin.recipiebox.view.ShoppingCart;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShoppingCartList extends AppCompatActivity {

    private static final String TAG = "ShoppingCartList";

    DatabaseHelper mDatabaseHelper;
  //  private ListView mListView;
    private Button btnNavigate;
    private String selectedName;
    private int selectedID;
    String selectedRecipieName;
    private ShoppingCartAdapter mAdapter;
    ImageButton mImageBtn; //Shopping Cart button in toolbar...
    Toolbar mMyToolbar;
    TextView mCountTv;
//    TextView totalPrice;
    MenuItem mCartIconMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_list);
 //       totalPrice = findViewById(R.id.totalPrice);
    //    mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        btnNavigate = (Button) findViewById(R.id.btnNavigate);
    //    getSupportActionBar().setTitle("Shopping Cart List");
        mDatabaseHelper = new DatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent recievedIntent = getIntent();
        selectedRecipieName = recievedIntent.getStringExtra("RecipieName");
   //     getSupportActionBar().setTitle("Shopping Cart"); // I need to pass in the Folder Name...
    //    mMyToolbar = findViewById(R.id.myToolBar); (might need to uncomment this)
  //      setSupportActionBar(mMyToolbar);
  //      mMyToolbar.setTitleTextColor(0xFFFFFFFF);

        mAdapter = new ShoppingCartAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

     //   setTotalPrice();


        mAdapter.setOnItemClickListener(new ShoppingCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position, String ingredientName) {
                makeDialog(position, ingredientName);
            }
        });


   //     populateListView();

  //      Intent receivedIntent = getIntent();

   //     selectedID = receivedIntent.getIntExtra("id", -1);
    //    selectedName = receivedIntent.getStringExtra("name");

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
    Some sorting Psuedocode...
    for

     */
    /*
    private void sortingAlgorithm () {
/*
        for (int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                if(names[i].compareTo(names[j])>0) {
                    temp = names[i];
                    names[i] = names[j];
                    names[j] = temp;
                }
            }
        } */
    /*
        List<String> contactList = new ArrayList<>();

    Collections.sort(contactList);
    } */
/*
    private void populateListView() { //Original populateListView
        Log.d(TAG, "populateListView: Displaying data in the listview");
        Cursor data = mDatabaseHelper.getShoppingCartData();
        ArrayList<String> listData = new ArrayList<>();
//        ArrayList<String> listData = Arrays.asList(names.s)
 //       ArrayList<String> sorted = Arrays.asList(listData.stream().sorted((s1,s2) -> s1.compareToIgnoreCase(s2)).toArray(String[]::new));

        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        Collections.sort(listData);

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG,"onItemClick: You clicked on " + name);
                Cursor data = mDatabaseHelper.getShoppingCartItemID(name);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent shoppingCartIntent = new Intent(ShoppingCartList.this, EditShoppingCart.class);
                    shoppingCartIntent.putExtra("id", itemID);
                    shoppingCartIntent.putExtra("name", name);
                    startActivity(shoppingCartIntent);
                } else {
                    toastMessage("No ShoppingCart ID associated with that name");
                }
                toastMessage("Has been clicked!");
            }
        });
    }
*/
    public void removeItem(int position, String ingredientName) {

        Cursor data = mDatabaseHelper.getShoppingCartItemID(ingredientName);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        Log.d(TAG, "ingredientId: " + itemID + " and ingredient name is: " + ingredientName);

        mDatabaseHelper.deleteShoppingCartIngredient(itemID, ingredientName);

        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        toastMessage("Removed from database");

    }

    public void makeDialog(final int position, final String ingredientName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartList.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, ingredientName);
                Toast.makeText(ShoppingCartList.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ShoppingCartList.this, "Sorry.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();

    }

    //Need this method for shopping cart icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mCartIconMenuItem = menu.findItem(R.id.cart_count_menu_item);
        View actionView = mCartIconMenuItem.getActionView();

        if(actionView != null) {
            mCountTv = actionView.findViewById(R.id.count_tv_layout);
            mImageBtn = actionView.findViewById(R.id.image_btn_layout);
        }
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartList.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf(shoppingCartCount);
        mCountTv.setText(shoppingCartString);

        return super.onCreateOptionsMenu(menu);
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getShoppingCartData();

    }

  //  private void setTotalPrice() {
  //      Double priceTotal = mDatabaseHelper.getShoppingCartPriceSum();
  //      ArrayList<String> listData = new ArrayList();
  //      Cursor priceTottal = mDatabaseHelper.getShoppingCartPriceSum();
  //      while(priceTottal.moveToNext()) {
  //          String priceTottalString = priceTottal.getString(0);
  //          totalPrice.setText(priceTottalString);
  //      }
  //      listData.add(priceTottal);
      //  String totalPriceString = String.valueOf(priceTotal);
  //      totalPrice.setText(totalPriceString);
  //  }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
