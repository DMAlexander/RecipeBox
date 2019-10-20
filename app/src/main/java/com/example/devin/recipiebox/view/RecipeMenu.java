package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

public class RecipeMenu extends AppCompatActivity {

    private Button btnShoppingCart, btnAllRecipies, btnRecipeFolders;
    ImageButton mImageBtn; //Shopping Cart button in toolbar...
    Toolbar mMyToolbar;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menu);
        //     btnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);
        btnRecipeFolders = (Button) findViewById(R.id.recipeFolders);
        btnAllRecipies = (Button) findViewById(R.id.btnAllRecipies);
        mDatabaseHelper = new DatabaseHelper(this);

        //    mMyToolbar = findViewById(R.id.myToolBar); (might need to uncomment this)
        //    setSupportActionBar(mMyToolbar);
        //    mMyToolbar.setTitleTextColor(0xFFFFFFFF);

        btnAllRecipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRecipeFolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMenu.this, RecipieFolder.class);
                startActivity(intent);
            }
        });
    }
}
