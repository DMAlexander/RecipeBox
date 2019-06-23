package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.view.NewIngredient.IngredientLayoutScreen;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.Recipie.RecipieScreen;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

public class MainMenu extends AppCompatActivity {

    private Button btnShoppingCart, btnRecipieFolders, btnAllRecipies, btnIngredientTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);
        btnRecipieFolders = (Button) findViewById(R.id.btnRecipieFolders);
        btnAllRecipies = (Button) findViewById(R.id.btnAllRecipies);
        btnIngredientTest = (Button) findViewById(R.id.btnIngredientTest);

  //      ImageView myImageView = (ImageView) findViewById(R.id.my_image_view);
  //      myImageView.setImageResource(R.drawable.androiddeletepicture);


        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });

        btnAllRecipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRecipieFolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, RecipieFolder.class);
                startActivity(intent);
            }
        });

        btnIngredientTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, IngredientLayoutScreen.class);
                startActivity(intent);
            }
        });


    }
}
