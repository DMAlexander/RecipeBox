package com.example.devin.recipebox.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.Recipe.MainActivity;
import com.example.devin.recipebox.view.RecipeFolders.RecipeFolder;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;

public class RecipeMenu extends AppCompatActivity {

    private Button btnAllRecipes, btnRecipeFolders;
    DatabaseHelper mDatabaseHelper;
    private TextView textView, tView1, tView2;
    private ImageView iView1, iView2;
    private CardView cView1, cView2;
    private ImageButton mImageBtn;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recipe_menu );

 //       btnRecipeFolders = (Button) findViewById( R.id.recipeFolders );
 //       btnAllRecipes = (Button) findViewById( R.id.btnAllRecipes );
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
                Intent intent = new Intent(RecipeMenu.this, RecipeFolder.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu, menu );
        mCartIconMenuItem = menu.findItem( R.id.cart_count_menu_item );
        View actionView = mCartIconMenuItem.getActionView();

        if( actionView != null ) {
            mCountTv = actionView.findViewById( R.id.count_tv_layout );
            mImageBtn = actionView.findViewById( R.id.image_btn_layout );
        }
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(RecipeMenu.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = 0;
        shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );

    }
}
