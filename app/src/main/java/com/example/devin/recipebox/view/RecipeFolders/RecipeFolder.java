package com.example.devin.recipebox.view.RecipeFolders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.MainMenu;
import com.example.devin.recipebox.view.Menu.ExportedRecipesAdapter;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;

public class RecipeFolder extends AppCompatActivity {

    private static final String TAG = "RecipeFolderActivity";
    RecyclerView recyclerView;
    private EditText editable_recipe_folder_item;
    private DatabaseHelper mDatabaseHelper;
    private RecipeFolderAdapter mAdapter;
    private Button btnRecipeFolderAdd;
    int sizeOfList = 1;
    ImageView mImageBtn;
    MenuItem mCartIconMenuItem;
    TextView mCountTv;
    private Button mImageBtn2;
    MenuItem mMenuRoute;
    private RelativeLayout parentRelativeLayout;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recipe_folder);
        parentRelativeLayout = (RelativeLayout) findViewById( R.id.parentRelativeLayout );
        btnRecipeFolderAdd = (Button) findViewById( R.id.btnRecipeFolderAdd );
        editable_recipe_folder_item = (EditText) findViewById( R.id.editable_recipe_folder_item );
        mDatabaseHelper = new DatabaseHelper(this );

        recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new RecipeFolderAdapter(this, getAllItems());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(mAdapter);

        /*
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false );
        recyclerView.setLayoutManager( manager );
        mAdapter = new RecipeFolderAdapter(this, getAllItems() );
        recyclerView.setAdapter( mAdapter );
        */

        final int childCount = parentRelativeLayout.getChildCount();

        btnRecipeFolderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeFolder.this, RecipeAdd.class );
                startActivity( intent );
            }
        });
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getRecipeFolderData();
    }

    public void insertItem(String recipeFolderName) {
        String newEntry = editable_recipe_folder_item.getText().toString();
        if( editable_recipe_folder_item != null ) {
            boolean insertData = mDatabaseHelper.addRecipeFolderData( newEntry );
            if ( insertData ) {
                toastMessage( "Data successfully inserted!" );
                mAdapter.notifyDataSetChanged();
                finish();
                startActivity( getIntent() );
            } else {
                toastMessage( "Something went wrong!" );
            }
        } else {
            toastMessage( "Put something in the text field!" );
        }
    }

    //Need this method for shopping cart icon
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
                Intent intent = new Intent(RecipeFolder.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });
        if (actionView2 != null ) {
            mImageBtn2 = actionView2.findViewById( R.id.ButtonTest );
        }
        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(RecipeFolder.this, MainMenu.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = 0;
        shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );
    }
/*
    public void setButtons() {
        btnRecipeFolderAdd = findViewById(R.id.btnRecipeFolderAdd);
        btnRecipeFolderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeFolderName = editable_recipe_folder_item.getText().toString();
                if (editable_recipe_folder_item.length() !=0) {
                    insertItem(recipeFolderName);
                    editable_recipe_folder_item.setText("");
                } else {
                    toastMessage("Please put something in the textbox!");
                }
            }
        });
    }
*/
    /**
     *
     * @param v
     * Add the new row before the add field button
     */
    public void onAddField( View v ) {
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View rowView = inflater.inflate( R.layout.activity_ingredient_layout_field, null );
        sizeOfList++;
        parentRelativeLayout.addView( rowView, parentRelativeLayout.getChildCount() -1 );
    }
    public void onDelete( View v ) {
        parentRelativeLayout.removeView( (View) v.getParent() );
        sizeOfList--;
    }

    private void toastMessage( String message ) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT ).show();
    }
}
