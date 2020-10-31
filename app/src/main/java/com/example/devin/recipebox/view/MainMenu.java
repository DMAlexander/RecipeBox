package com.example.devin.recipebox.view;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.devin.recipebox.view.Menu.ExportedRecipesMenu;
import com.example.devin.recipebox.view.Recipe.MainActivity;
import com.example.devin.recipebox.view.RecipeFolders.RecipeFolder;
import com.example.devin.recipebox.view.ShoppingCart.ShoppingCartList;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainMenu extends AppCompatActivity {

    private Button btnShoppingCart, btnRecipeFolders, btnAllRecipes, recipeMenu;
    private TextView textView, tView1, tView2, tView3, tView4;
    private ImageView iView1, iView2, iView3, iView4;
    private CardView cView1, cView2, cView3, cView4;
    private ImageButton mImageBtn;
    private Button mImageBtn2;
    TextView mCountTv;
    MenuItem mCartIconMenuItem, mMenuRoute;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );
        textView = (TextView) findViewById(R.id.textView);
        tView1 = (TextView) findViewById(R.id.tView1);
        tView2 = (TextView) findViewById(R.id.tView2);
        tView3 = (TextView) findViewById(R.id.tView3);
        tView4 = (TextView) findViewById(R.id.tView4);
        iView1 = (ImageView) findViewById(R.id.iView1);
        iView2 = (ImageView) findViewById(R.id.iView2);
        iView3 = (ImageView) findViewById(R.id.iView3);
        iView4 = (ImageView) findViewById(R.id.iView4);
        cView1 = (CardView) findViewById(R.id.cView1);
        cView2 = (CardView) findViewById(R.id.cView2);
        cView3 = (CardView) findViewById(R.id.cView3);
        cView4 = (CardView) findViewById(R.id.cView4);

//        btnAllRecipes = (Button) findViewById( R.id.btnAllRecipes );
//        recipeMenu = (Button) findViewById( R.id.recipeMenu );
//        btnShoppingCart = (Button) findViewById( R.id.btnShoppingCart );
        mDatabaseHelper = new DatabaseHelper(this );

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false );

        cView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class );
                startActivity( intent );
            }
        });

        cView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ExportedRecipesMenu.class );
                startActivity( intent );
            }
        });

        cView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, RecipeFolder.class );
                startActivity( intent );
            }
        });

        /*
        cView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, MainActivity.class );
                startActivity( intent );
            }
        });
        */
        
        /*
        btnAllRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, RecipeMenu.class );
                startActivity( intent );
            }
        });

        recipeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ExportedRecipesMenu.class );
                startActivity( intent );
            }
        });

        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });
        */
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
                Intent intent = new Intent(MainMenu.this, ShoppingCartList.class );
                startActivity( intent );
            }
        });
        if (actionView2 != null ) {
            mImageBtn2 = actionView2.findViewById( R.id.ButtonTest );
        }
        mImageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(MainMenu.this, MainMenu.class );
                startActivity( intent );
            }
        });

        int shoppingCartCount = 0;
        shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf( shoppingCartCount );
        mCountTv.setText( shoppingCartString );

        return super.onCreateOptionsMenu( menu );
    }

    private void requestMultiplePermissions(){
        Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                }

                //check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied()) {
                    //show alert dialog navigating to Settings
                    //openSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

}
