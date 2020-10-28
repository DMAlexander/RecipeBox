package com.example.devin.recipebox.view.Recipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
//import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.RecipeFolders.RecipeFolder;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;

import java.util.ArrayList;

//Screen that displays list of recipes --> ListDataActivity
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";
    private DatabaseHelper mDatabaseHelper;
    private RecipeAdapter mAdapter;
    private Button newRecipe;
    private int selectedRecipeFolderID;
    private String selectedRecipeFolderName;

    ArrayList<String> selectedItems = new ArrayList<>();
    ImageButton mImageBtn;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;
    RecyclerView recyclerView;
    private ArrayList<String> recipes = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mDatabaseHelper = new DatabaseHelper(this );

        Intent recievedIntent = getIntent();
        selectedRecipeFolderID = recievedIntent.getIntExtra("FolderID", -1 );
        Log.d( TAG, "recipe folder Id value is: " + selectedRecipeFolderID );
        selectedRecipeFolderName = recievedIntent.getStringExtra("RecipeFolderName" );

        recyclerView = findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager(this ) );
        mAdapter = new RecipeAdapter(this, getAllItems( selectedRecipeFolderID ) );

        Cursor data = mDatabaseHelper.getRecipeData();
        while ( data.moveToNext() ) {
            recipes.add( data.getString(1) );
        }

        recyclerView.setAdapter( mAdapter );
        newRecipe = (Button) findViewById( R.id.newRecipe );

        buildRecyclerView(); //buildRecyclerView();

    newRecipe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent editScreenIntent = new Intent(MainActivity.this, RecipeInsert.class );
            editScreenIntent.putExtra("FolderID", selectedRecipeFolderID );
            startActivity( editScreenIntent );
        }
    });
    }

    public void removeItem( int position, String recipeName ) {
//        mRecipeList.remove(position);

        Cursor data =mDatabaseHelper.getRecipeItemID( recipeName );
        int itemID = -1;
        while( data.moveToNext() ) {
            itemID = data.getInt(0);
        }
        Log.d( TAG, "recipeId: " + itemID + " and recipe name is: " + recipeName );
//      mDatabaseHelper.deleteRecipeName(selectedRecipeID, selectedRecipeName);
        mDatabaseHelper.deleteRecipeName( itemID, recipeName );
        mAdapter.notifyItemRemoved( position );
        mAdapter.notifyDataSetChanged();
//        mRecipeList.remove(position);
//        mAdapter.notifyItemRemoved(position);
        finish();
        overridePendingTransition(0, 0); //Get rid of blink after activity recreation
        startActivity( getIntent() );
        overridePendingTransition(0, 0);//Get rid of blink after activity recreation
        toastMessage( "Removed from database" );

    }

    private void buildRecyclerView() {

        Log.d( TAG, "populateRecyclerView: Displaying data in the RecyclerView" );

        mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position ) {

                int itemID = -1;

                if (itemID > -1) {
                    Log.d( TAG, "onItemClick: The ID is " + itemID );
                    Intent editScreenIntent = new Intent(MainActivity.this, RecipeFolder.class );
                    editScreenIntent.putExtra("RecipeId", itemID );
      //              editScreenIntent.putExtra("RecipeName", RecipeName);
                    startActivity( editScreenIntent );
                } else {
                    toastMessage( "No ID associated with that recipeName" );
                }
            }

            @Override
            public void onDeleteClick( int position, String recipeName ) {
                makeDialog( position, recipeName );
            }

        });
    }

    public void makeDialog( final int position, final String recipeName ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this );
        builder.setTitle( "Delete Recipe" );
        builder.setMessage( "Are you sure you want to delete the recipe?" );

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                removeItem( position, recipeName );
                Toast.makeText(MainActivity.this, "Item has been removed!!",Toast.LENGTH_SHORT ).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialogInterface, int i ) {
                Toast.makeText(MainActivity.this, "I will try hard and make better videos!",Toast.LENGTH_SHORT ).show();
            }
        });

        builder.create().show();

    }

    //Need this method for shopping cart icon
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu, menu );
        mCartIconMenuItem = menu.findItem( R.id.cart_count_menu_item );
        View actionView = mCartIconMenuItem.getActionView();

        if( actionView != null ) {
            mCountTv = actionView.findViewById( R.id.count_tv_layout );
            mImageBtn = actionView.findViewById( R.id.image_btn_layout );
        }

        mImageBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(MainActivity.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );
    }

    private Cursor getAllItems(int selectedRecipeFolderID) {

        if (selectedRecipeFolderID >0) {
            return mDatabaseHelper.getRecipesByFolder( selectedRecipeFolderID );
        } else {
            return mDatabaseHelper.getRecipeData();
        }
    }

    private void addItem() {

 //       String recipeName = editTextInsert.getText().toString();
 //       mDatabaseHelper.addRecipeData(recipeName);
   //     mAdapter.swapCursor(getAllItems());

   //     editTextInsert.getText().clear();


    }

    /*
        public void addShoppingCartData(String newEntry){
            boolean insertData = mDatabaseHelper.addShoppingCartData(newEntry);

            if (insertData) {
                toastMessage("Data will be added to shoppingCartList!");
            } else {
                toastMessage("Something went wrong!");
            }
        } */

        private void toastMessage ( String message ){
            Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
        }

        /*
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            String userInput = newText.toLowerCase();

            mAdapter = new RecipeAdapter(this, mDatabaseHelper.getRecipeDataRowSubstr(userInput));
            recyclerView.setAdapter(mAdapter);

            return true;
        }
        */
    }

