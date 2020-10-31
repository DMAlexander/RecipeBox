package com.example.devin.recipebox.view.PublishedIngredient;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.MainMenu;
import com.example.devin.recipebox.view.Menu.ExportedRecipesAdapter;
import com.example.devin.recipebox.view.RecipeFolders.RecipeFolder;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;

import java.io.File;

public class IngredientInfo extends AppCompatActivity {

    private static final String TAG = "IngredientInfo";

    DatabaseHelper mDatabaseHelper;
    private TextView editable_item;
    private IngredientInfoAdapter mAdapter;
    private String selectedIngredientName;
    private String selectedRecipeName;
    private int selectedIngredientID;
    private int selectedRecipeID;
    private TextView ingredients;
    private TextView instructions;
    private TextView instructionsEdit;
    private ImageButton mImageBtn;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;
    private Button mImageBtn2;
    MenuItem mMenuRoute;
    private static final String IMAGE_DIRECTORY = "/demonuts/";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ingredient_info );
        editable_item = (TextView) findViewById( R.id.editable_item );
        ingredients = (TextView) findViewById( R.id.ingredients );
        instructions = (TextView) findViewById( R.id.instructions );
        instructionsEdit = (TextView) findViewById( R.id.instructionsEdit );

        mDatabaseHelper = new DatabaseHelper(this );
        Intent recievedIntent = getIntent();
        selectedIngredientID = recievedIntent.getIntExtra("IngredientId", -1 );
        selectedIngredientName = recievedIntent.getStringExtra("IngredientName" );
        selectedRecipeName = recievedIntent.getStringExtra("RecipeName" );

        mImageBtn = (ImageButton) findViewById( R.id.iv );

        selectedRecipeID = recievedIntent.getIntExtra("RecipeId", -1 );
        editable_item.setText( selectedRecipeName );
        ingredients.setText( "Ingredients:" );
        instructions.setText( "Instructions:" );

        RecyclerView recyclerView = findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager(new LinearLayoutManager(this ) );
        mAdapter = new IngredientInfoAdapter(this, getAllItems() );
        recyclerView.setAdapter( mAdapter );

        Cursor data = mDatabaseHelper.getRecipeDescription( selectedRecipeName );
        String recipeDescription = "";
        while ( data.moveToNext() ) {
            recipeDescription = data.getString(0);
        }

        Log.d( TAG, "Description is: " + recipeDescription );

        instructionsEdit.setText( recipeDescription );

        mAdapter.setOnItemClickListener( new IngredientInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position ) {}

            @Override
            public void onDeleteClick( int position, String ingredientName ) {
                makeDialog( position, ingredientName );
            }
        });

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY );
        String fileName = "/myImage" + selectedRecipeName;

        File imgFile = new File(wallpaperDirectory + fileName + ".jpg" );
        if( imgFile.exists() ) {
            Bitmap bitmap = BitmapFactory.decodeFile( imgFile.getAbsolutePath() );
            mImageBtn = (ImageButton) findViewById( R.id.iv );
            mImageBtn.setImageBitmap( bitmap );
        }
    }

    private void toastMessage( String message ) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT ).show();
    }

    public void removeItem( int position, String ingredientName ) {

        Cursor data = mDatabaseHelper.getRecipeItemID( selectedRecipeName );
        int recipeID = -1;
        while ( data.moveToNext() ) {
            recipeID = data.getInt(0);
        }

        data = mDatabaseHelper.getIngredientItemID( ingredientName, recipeID );
        int itemID = -1;
        while ( data.moveToNext() ) {
            itemID = data.getInt(0);
        }

        Log.d( TAG, "ingredientId: " + itemID + " and ingredient name is: " + ingredientName );
        mDatabaseHelper.deleteIngredientName( itemID, ingredientName );

        mAdapter.notifyItemRemoved( position );
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        toastMessage( "Removed from database" );
    }

    public void makeDialog( final int position, final String ingredientName ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IngredientInfo.this );
        builder.setTitle( "Delete Ingredient" );
        builder.setMessage( "Are you sure you want to delete the ingredient?" );

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem( position, ingredientName );
                Toast.makeText(IngredientInfo.this, "Thanks!", Toast.LENGTH_SHORT ).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(IngredientInfo.this, "Sorry",Toast.LENGTH_SHORT ).show();
            }
        });

        builder.create().show();

    }

    /**
     * Need this method for shopping cart icon
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu, menu );

        mCartIconMenuItem = menu.findItem( R.id.cart_count_menu_item );
        mMenuRoute = menu.findItem( R.id.menuRoute );
        View actionView = mCartIconMenuItem.getActionView();
        View actionView2 = mMenuRoute.getActionView();

        if( actionView != null ) {
            mCountTv = actionView.findViewById( R.id.count_tv_layout );
            mImageBtn = actionView.findViewById( R.id.image_btn_layout );
        }
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(IngredientInfo.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });
        if (actionView2 != null ) {
            mImageBtn2 = actionView2.findViewById( R.id.ButtonTest );
        }
        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(IngredientInfo.this, MainMenu.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = 0;
        shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );
    }

    private Cursor getAllItems() {

        Cursor data = mDatabaseHelper.getRecipeItemID( selectedRecipeName );
        int itemID = -1;

        while ( data.moveToNext() ) {
            itemID = data.getInt(0);
        }

        if( itemID > 1 ) {

            Log.d( TAG, "The RecipeID is: " + itemID );

        }

        return mDatabaseHelper.getIngredientsBasedOnRecipeData( itemID );
    }
}
