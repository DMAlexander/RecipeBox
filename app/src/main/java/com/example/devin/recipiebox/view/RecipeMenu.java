package com.example.devin.recipiebox.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

public class RecipeMenu extends AppCompatActivity {

    private Button btnAllRecipies, btnRecipeFolders;
    DatabaseHelper mDatabaseHelper;
    private TextView textView, tView1, tView2;
    private ImageView iView1, iView2;
    private CardView cView1, cView2;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recipe_menu );

 //       btnRecipeFolders = (Button) findViewById( R.id.recipeFolders );
 //       btnAllRecipies = (Button) findViewById( R.id.btnAllRecipies );
        mDatabaseHelper = new DatabaseHelper(this );
        textView = (TextView) findViewById(R.id.textView);
        tView1 = (TextView) findViewById(R.id.tView1);
        tView2 = (TextView) findViewById(R.id.tView2);
        iView1 = (ImageView) findViewById(R.id.iView1);
        iView2 = (ImageView) findViewById(R.id.iView2);
        cView1 = (CardView) findViewById(R.id.cView1);
        cView2 = (CardView) findViewById(R.id.cView2);

        cView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMenu.this, MainActivity.class );
                startActivity( intent );
            }
        });

        cView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMenu.this, RecipieFolder.class);
                startActivity(intent);
            }
        });
    }
}
