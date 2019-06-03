package com.example.devin.recipiebox.view.ShoppingCart;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;

import java.util.ArrayList;

public class ShoppingCartList extends AppCompatActivity {

    private static final String TAG = "ShoppingCartList";

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    private Button btnNavigate;
    private String selectedName;
    private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_list);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        btnNavigate = (Button) findViewById(R.id.btnNavigate);
        populateListView();

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedName = receivedIntent.getStringExtra("name");

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateListView() { //Original populateListView
        Log.d(TAG, "populateListView: Displaying data in the listview");
        Cursor data = mDatabaseHelper.getShoppingCartData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
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

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
