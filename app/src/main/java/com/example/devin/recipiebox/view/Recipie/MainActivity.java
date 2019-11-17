package com.example.devin.recipiebox.view.Recipie;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
//import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

//Screen that displays list of recipies --> ListDataActivity
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";
    private DatabaseHelper mDatabaseHelper;
    private RecipeAdapter mAdapter;
    private Button newRecipie;
    private int selectedRecipieFolderID;
    private String selectedRecipieFolderName;

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
        selectedRecipieFolderID = recievedIntent.getIntExtra("FolderID", -1 );
        Log.d( TAG, "recipie folder Id value is: " + selectedRecipieFolderID );
        selectedRecipieFolderName = recievedIntent.getStringExtra("RecipieFolderName" );

        recyclerView = findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager(this ) );
        mAdapter = new RecipeAdapter(this, getAllItems( selectedRecipieFolderID ) );

        Cursor data = mDatabaseHelper.getRecipieData();
        while ( data.moveToNext() ) {
            recipes.add( data.getString(1) );
        }

        recyclerView.setAdapter( mAdapter );
        newRecipie = (Button) findViewById( R.id.newRecipie );

        buildRecyclerView(); //buildRecyclerView();

    newRecipie.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent editScreenIntent = new Intent(MainActivity.this, RecipieInsert.class );
            editScreenIntent.putExtra("FolderID", selectedRecipieFolderID );
            startActivity( editScreenIntent );
        }
    });
    }

    public void removeItem( int position, String recipieName ) {
//        mRecipeList.remove(position);

        Cursor data =mDatabaseHelper.getRecipieItemID( recipieName );
        int itemID = -1;
        while( data.moveToNext() ) {
            itemID = data.getInt(0);
        }
        Log.d( TAG, "recipieId: " + itemID + " and recipie name is: " + recipieName );
//      mDatabaseHelper.deleteRecipieName(selectedRecipieID, selectedRecipieName);
        mDatabaseHelper.deleteRecipieName( itemID, recipieName );
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
                    Intent editScreenIntent = new Intent(MainActivity.this, RecipieFolder.class );
                    editScreenIntent.putExtra("RecipieId", itemID );
      //              editScreenIntent.putExtra("RecipieName", RecipieName);
                    startActivity( editScreenIntent );
                } else {
                    toastMessage( "No ID associated with that recipieName" );
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
        builder.setTitle( "Delete Recipie" );
        builder.setMessage( "Are you sure you want to delete the recipie?" );

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

    private Cursor getAllItems(int selectedRecipieFolderID) {

        if (selectedRecipieFolderID >0) {
            return mDatabaseHelper.getRecipiesByFolder( selectedRecipieFolderID );
        } else {
            return mDatabaseHelper.getRecipieData();
        }
    }

    private void addItem() {

 //       String recipieName = editTextInsert.getText().toString();
 //       mDatabaseHelper.addRecipieData(recipieName);
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

