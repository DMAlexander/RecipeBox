package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.Recipie.RecipieScreen;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

public class MainMenu extends AppCompatActivity {

    private Button btnShoppingCart, btnRecipie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);
        btnRecipie = (Button) findViewById(R.id.btnRecipie);

        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });

        btnRecipie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
