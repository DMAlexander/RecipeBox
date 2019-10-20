package com.example.devin.recipiebox.view;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.NewIngredient.IngredientLayoutScreen;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.Recipie.RecipieScreen;
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolder;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainMenu extends AppCompatActivity {

    private Button btnShoppingCart, btnRecipieFolders, btnAllRecipies, recipeMenu;
    ImageButton mImageBtn; //Shopping Cart button in toolbar...
    Toolbar mMyToolbar;
    TextView mCountTv;
    MenuItem mCartIconMenuItem;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btnAllRecipies = (Button) findViewById(R.id.btnAllRecipies);
        recipeMenu = (Button) findViewById(R.id.recipeMenu);
        btnShoppingCart = (Button) findViewById(R.id.btnShoppingCart);
        mDatabaseHelper = new DatabaseHelper(this);

    //    mMyToolbar = findViewById(R.id.myToolBar); (might need to uncomment this)
    //    setSupportActionBar(mMyToolbar);
    //    mMyToolbar.setTitleTextColor(0xFFFFFFFF);

        btnAllRecipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, RecipeMenu.class);
                startActivity(intent);
            }
        });

        recipeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //I need to change this...
                Intent intent = new Intent(MainMenu.this, RecipieFolder.class);
                startActivity(intent);
            }
        });

        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
    }

    //Need this method for shopping cart icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mCartIconMenuItem = menu.findItem(R.id.cart_count_menu_item);
        View actionView = mCartIconMenuItem.getActionView();

        if(actionView != null) {
            mCountTv = actionView.findViewById(R.id.count_tv_layout);
            mImageBtn = actionView.findViewById(R.id.image_btn_layout);
        }
        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf(shoppingCartCount);
        mCountTv.setText(shoppingCartString);

        return super.onCreateOptionsMenu(menu);
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
