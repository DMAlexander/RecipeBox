package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.database.model.RecipieDatabase;
import com.example.devin.recipiebox.database.model.IngredientDatabase;

import java.util.ArrayList;
import java.util.List;
//Screen that displays list of recipies
public class MainActivity extends AppCompatActivity {



    private List<RecipieDatabase> databaseList = new ArrayList<>();
    private DatabaseHelper db;
    private TextView noRecipieView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNextButton();
        configureShoppingCartButton();
    }

    private void createRecipie(String recipie) {
        long id = db.insertRecipie(recipie);

        RecipieDatabase n = db.getRecipie(id);

        if (n!=null) {
            databaseList.add(0, n);

            toggleEmptyRecipies();
        }
    }

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

}
