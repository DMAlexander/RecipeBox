package com.example.devin.recipiebox.view;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.database.model.RecipieDatabase;
import com.example.devin.recipiebox.database.model.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;
//Screen that displays list of recipies --> ListDataActivity
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;
    private Button btnNavigate;
/*
    private List<RecipieDatabase> databaseList = new ArrayList<>();
    private DatabaseHelper db;
    private TextView noRecipieView;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        btnNavigate = (Button) findViewById(R.id.btnNavigate);

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

    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        //get data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get value from database in column 1
            //then add it to arraylist
            listData.add(data.getString(1));
        }
        //Create list adapter and set adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //Set onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + name);

                Cursor data = mDatabaseHelper.getItemID(name); //get id associated with name
                int itemID = -1;
                while (data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is " + itemID);
                    Intent editScreenIntent = new Intent(MainActivity.this, IngredientScreen.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }
                else {
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }



    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

/*
    private void createRecipie(String recipie) {
        long id = db.insertRecipie(recipie);

        RecipieDatabase n = db.getRecipie(id);

        if (n!=null) {
            databaseList.add(0, n);

            toggleEmptyRecipies();
        }
    } */
/*
    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.devinsButton);
        nextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        RecipieScreen.class));
            }
        });
    }

    private void configureShoppingCartButton() {
        Button shoppingCartButton = (Button) findViewById(R.id.shoppingCartButton);
        shoppingCartButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        ShoppingCartList.class));
            }
        });
    }

    private void toggleEmptyRecipies() {
        if (db.getRecipieCount() > 0) {
            noRecipieView.setVisibility(View.GONE);
        } else {
            noRecipieView.setVisibility(View.VISIBLE);
        }
    }
*/
}
