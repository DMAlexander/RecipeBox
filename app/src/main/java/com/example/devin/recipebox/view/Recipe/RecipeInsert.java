package com.example.devin.recipebox.view.Recipe;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.MainMenu;
import com.example.devin.recipebox.view.Menu.ExportedRecipesAdapter;
import com.example.devin.recipebox.view.NewIngredient.IngredientLayoutScreen;
import com.example.devin.recipebox.view.RecipeFolders.RecipeFolder;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;

public class RecipeInsert extends AppCompatActivity {

    private static final String TAG = "RecipeInsert";

    DatabaseHelper mDatabaseHelper;
    private Button btnRecipeAdd, mImageBtn2;
    private EditText editRecipeText;
    private int selectedRecipeFolderID;
    ImageButton mImageBtn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView mCountTv;
    MenuItem mCartIconMenuItem, mMenuRoute;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recipe_insert);
        btnRecipeAdd = (Button) findViewById( R.id.btnRecipeAdd );
        editRecipeText = (EditText) findViewById( R.id.editRecipeText );
        mDatabaseHelper = new DatabaseHelper(this );

        radioGroup = findViewById( R.id.radioGroup );

        btnRecipeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent receivedIntent = getIntent();
                selectedRecipeFolderID = receivedIntent.getIntExtra("FolderID", -1 );
                Log.d( TAG, "recipe folder id value is: " + selectedRecipeFolderID );

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById( radioId );
                Log.d( TAG, "Your choice: " + radioButton.getText() );
                String recipeName = editRecipeText.getText().toString();
                if ( radioButton.getText().equals( "Ingredients" ) ) {
                    if ( editRecipeText.length() != 0 ) {
                        insertItem( recipeName, selectedRecipeFolderID );
                        editRecipeText.setText( "" );
                    } else {
                        toastMessage( "Please put something in the textbox!" );
                    }
                } else {
                    if ( editRecipeText.length() != 0 ) {
                        insertRecipe( recipeName, selectedRecipeFolderID );
                    }
                }
            }
        });
    }

    public void insertItem( String recipeName, int selectedRecipeFolderID ) {

        String lowerCaseRecipe = recipeName.toLowerCase();

        boolean insertData = mDatabaseHelper.addRecipeData( lowerCaseRecipe, null, "Y", selectedRecipeFolderID );

        if ( insertData ) {
            toastMessage( "Data successfully inserted!" );

            Cursor data = mDatabaseHelper.getRecipeItemID( lowerCaseRecipe );
            int itemID = -1;
            while ( data.moveToNext() ) {
                itemID = data.getInt(0);
            }
            toastMessage( "The recipeID is: " + itemID );

            Intent intent = new Intent(RecipeInsert.this, IngredientLayoutScreen.class );
            intent.putExtra("RecipeName", lowerCaseRecipe );
            intent.putExtra("RecipeId", itemID );

            startActivity( intent );
        } else {
            toastMessage( "Something went wrong!" );
        }
    }

    public void insertRecipe( String recipeName, int selectedRecipeFolderID ) {

        String lowerCaseRecipe = recipeName.toLowerCase();

        boolean insertData = mDatabaseHelper.addRecipeData( lowerCaseRecipe, null, "N", selectedRecipeFolderID );

        if ( insertData ) {
            toastMessage( "Data successfully inserted!" );

            Cursor data = mDatabaseHelper.getRecipeItemID( lowerCaseRecipe );
            int itemID = -1;
            while ( data.moveToNext() ) {
                itemID = data.getInt(0);
            }
            toastMessage( "The recipeID is: " + itemID );

            Intent intent = new Intent(RecipeInsert.this, MainActivity.class );
            intent.putExtra("RecipeName", lowerCaseRecipe );
            intent.putExtra("RecipeId", itemID );
            startActivity( intent );

        } else {

            toastMessage( "Something went wrong!" );
        }

    }

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
                Intent intent = new Intent(RecipeInsert.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });
        if (actionView2 != null ) {
            mImageBtn2 = actionView2.findViewById( R.id.ButtonTest );
        }
        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(RecipeInsert.this, MainMenu.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );
    }

    public void checkButton( View v ) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById( radioId );
        toastMessage( "Selected radio Button: " + radioButton.getText() );
    }

    private void toastMessage( String message ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }
}
