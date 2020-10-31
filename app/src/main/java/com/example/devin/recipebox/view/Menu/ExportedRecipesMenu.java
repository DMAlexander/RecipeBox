package com.example.devin.recipebox.view.Menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.RecipeMenu;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;

public class ExportedRecipesMenu extends AppCompatActivity {

    private static final String TAG = "ExportedRecipesMenu";

    DatabaseHelper mDatabaseHelper;
    private ExportedRecipesAdapter mAdapter;
    RecyclerView recyclerView;
    private ImageButton mImageBtn;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_exported_recipes_menu );

        mDatabaseHelper = new DatabaseHelper(this );

        recyclerView = findViewById( R.id.rv );
        recyclerView.setLayoutManager( new LinearLayoutManager(this ) );
        mAdapter = new ExportedRecipesAdapter(this, getAllItems() );
        recyclerView.setAdapter( mAdapter );

        mAdapter.setOnItemClickListener(new ExportedRecipesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position ) {

            }

            @Override
            public void onDeleteClick( int position, String recipeName, String recipeId ) {
                makeDeleteDialog( position, recipeName, recipeId );
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
                Intent intent = new Intent(ExportedRecipesMenu.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = 0;
        shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );

    }

    public void removeItem( int position, String recipeName, String recipeId ) {

        Log.d( TAG, "recipeId: " + recipeId + " and recipeName name is: " + recipeName );

        mDatabaseHelper.deleteExportedRecipeRow( recipeId );

        mAdapter.notifyItemRemoved( position );
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0 );
        startActivity( getIntent() );
        overridePendingTransition(0, 0 );
        toastMessage( "Removed from database" );

    }

    public void makeDeleteDialog( final int position, final String recipeName, final String recipeId ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExportedRecipesMenu.this );
        builder.setTitle( "Delete Ingredient" );
        builder.setMessage( "Are you sure you want to delete the ingredient?" );

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                removeItem( position, recipeName, recipeId );
                Toast.makeText(ExportedRecipesMenu.this, "Thanks!", Toast.LENGTH_SHORT ).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                Toast.makeText(ExportedRecipesMenu.this, "Sorry.", Toast.LENGTH_SHORT ).show();
            }
        });

        builder.create().show();
    }

    private void toastMessage ( String message ){
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getExportedShoppingCartInfo();
    }
}
