package com.example.devin.recipiebox.view.NewIngredient;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientEditAdapter;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.MainMenu;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfo;
import com.example.devin.recipiebox.view.Recipie.MainActivity;
import com.example.devin.recipiebox.view.Recipie.RecipieInsert;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IngredientLayoutScreen extends AppCompatActivity {

    private static final String TAG = "IngredientLayoutScreen";
    private LinearLayout parentLinearLayout;
    int sizeOfList = 1; //tracks how large the list is...
    private Button btnIngredientAdd, btnIngredientInfo, btnSave;
    //    private EditText editable_recipie_item, editable_ingredient_item;
//    private ListView mListView;
    //   private ImageView imageView;

    DatabaseHelper mDatabaseHelper;
    private IngredientEditAdapter mAdapter;
    private EditText number_edit_text;
    private EditText price_edit_text;
    private EditText recipieDescription;
    private String selectedRecipieName;
    private int selectedRecipieID;
    private int selectedIngredientID;
    private static final int PICK_IMAGE = 100;
    private Spinner type_spinner, type_spinner2;
    Uri imageUri;
    RecyclerView recyclerView;
    ImageButton mImageBtn;
    Toolbar mMyToolbar;
    TextView mCountTv;
    TextView descriptionLabel;
    MenuItem mCartIconMenuItem;
    private ImageButton imageButton;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_layout_screen);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        number_edit_text = (EditText) findViewById(R.id.number_edit_text);
        price_edit_text = (EditText) findViewById(R.id.price_edit_text);
        recipieDescription = (EditText) findViewById(R.id.recipieDescription);
        type_spinner = (Spinner) findViewById(R.id.type_spinner);
        type_spinner2 = (Spinner) findViewById(R.id.type_spinner2);
        descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        descriptionLabel.setText("Recipe Description:");
        mDatabaseHelper = new DatabaseHelper(this);
        mMyToolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(mMyToolbar);
        mMyToolbar.setTitleTextColor(0xFFFFFFFF);

   //     requestMultiplePermissions();

        imageButton = (ImageButton) findViewById(R.id.iv);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        Intent receivedIntent = getIntent();
        selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);
        selectedRecipieName = receivedIntent.getStringExtra("RecipieName");
  //      getSupportActionBar().setTitle(selectedRecipieName + selectedRecipieID);
        final int childCount = parentLinearLayout.getChildCount();
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int childCount = parentLinearLayout.getChildCount();
                Intent receivedIntent = getIntent();
                selectedRecipieID = receivedIntent.getIntExtra("RecipieId", -1);
                for(int i=1; i<childCount-5; i++) {
                    View v = parentLinearLayout.getChildAt(i);
                    number_edit_text = (EditText) v.findViewById(R.id.number_edit_text);

                    String ingredientName = number_edit_text.getText().toString();

         //           View rv = v.getRootView()
                    Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);


                    //       for(int i=0; i<sizeOfList; i++) {
//                    View v = parentLinearLayout.getChildAt(i);

                    price_edit_text = (EditText) v.findViewById(R.id.price_edit_text);
                    String price = price_edit_text.getText().toString();
                    double convertedPrice = Double.parseDouble(price);

              //      String newEntry2 = type_spinner.getSelectedItem().toString();
                    type_spinner = (Spinner) v.findViewById(R.id.type_spinner);
                    String newEntry2 = type_spinner.getSelectedItem().toString();
                    type_spinner2 = (Spinner) v.findViewById(R.id.type_spinner2);
                    String newEntry3 = type_spinner2.getSelectedItem().toString();



                    if (number_edit_text.length() != 0) {
                        insertItem(ingredientName, convertedPrice, newEntry2, newEntry3, selectedRecipieID);
                        //               number_edit_text.setText("");
                    } else {
                        toastMessage("Please put something in the textbox!");
                    }
                }

                if (recipieDescription.length() != 0) {
                    String recipeDescription = recipieDescription.getText().toString();
                    mDatabaseHelper.updateRecipieDescription(recipeDescription, selectedRecipieID);
                } else {
                    toastMessage("Put something in the description text field!");
                }

/*
                Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if(itemID > 1) {
                    Log.d(TAG, "The RecipieID is: " + itemID);
                }
                */
                //         return mDatabaseHelper.getIngredientsBasedOnRecipieData(itemID);

         //       imageButton.buildDrawingCache();
         //       Bitmap bitmap = imageButton.getDrawingCache();

                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                Intent intent = new Intent(IngredientLayoutScreen.this, IngredientInfo.class);
        //       intent.putExtra("RecipieId", itemID);


                intent.putExtra("BitmapImage", bitmap);
                intent.putExtra("RecipieName", selectedRecipieName);
         //       Log.d(TAG, "The RecipieId is: " + itemID);
                startActivity(intent);

       //         Intent editScreenIntent = new Intent(IngredientLayoutScreen.this, IngredientInfo.class);
                //         editScreenIntent.putExtra("FolderID", selectedRecipieFolderID);
      //          startActivity(editScreenIntent);

            }
        });


    }
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_ingredient_layout_field, null);
        //Add the new row before the add field button
        sizeOfList++;
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 5);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
        sizeOfList--;
    }
    public void insertItem(String ingredientName, double convertedPrice, String newEntry2, String newEntry3, int selectedRecipieID) {
    /*
        Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        } */
        double convertedSpinner = 0;
   //     String newEntry2 = type_spinner.getSelectedItem().toString();
   //     String newEntry3 = type_spinner2.getSelectedItem().toString();

        if (newEntry2.equalsIgnoreCase("1/8")) {
      //      convertedSpinner = Double.parseDouble(newEntry2);
            convertedSpinner = 0.125;
            System.out.print(convertedSpinner);
        } else if (newEntry2.equalsIgnoreCase("1/4")) {
            convertedSpinner = 0.25;
            System.out.print(convertedSpinner);
        } else if (newEntry2.equalsIgnoreCase("1/2")) {
            convertedSpinner = 0.5;
            System.out.print(convertedSpinner);
        } else if (newEntry2.equalsIgnoreCase("1")) {
            convertedSpinner = 1;
            System.out.print(convertedSpinner);
        } else if (newEntry2.equalsIgnoreCase("2")) {
            convertedSpinner = 2;
            System.out.print(convertedSpinner);
        } else if (newEntry2.equalsIgnoreCase("3")) {
            convertedSpinner = 3;
            System.out.print(convertedSpinner);
        } else {
       //     convertedSpinner = 0;
            convertedSpinner = Double.valueOf(newEntry2);
            System.out.print(convertedSpinner);
        }

        /*
        View v = parentLinearLayout.getChildAt(i);
        price_edit_text = (EditText) v.findViewById(R.id.price_edit_text);
        String price = price_edit_text.getText().toString();
        double convertedPrice = Double.parseDouble(price);
        */

        //      String newEntry = editable_ingredient_item.getText().toString();
        if (number_edit_text.length() != 0) {
            Log.d(TAG, "ingredientName: " + ingredientName + " num: " + convertedSpinner + " newEntry3: " + newEntry3 + "recipieId :" + selectedRecipieID);
            boolean insertData = mDatabaseHelper.addIngredientData(ingredientName, convertedSpinner, newEntry3, convertedPrice, selectedRecipieID); //we need all 4 parameters here...
            if (insertData) {
                toastMessage("Data successfully inserted!");
            } else {
                toastMessage("Something went wrong!");
            }
        } else {
            toastMessage("Put something in the text field!");
        }

        String currentPrice2 = "";

        Cursor data = mDatabaseHelper.getRecipePrice(selectedRecipieName);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            currentPrice2 = data.getString(0);
            listData.add(currentPrice2);
        }
        currentPrice2 = listData.get(0);
        Double convertedPrice2 = Double.parseDouble(currentPrice2);
        Double convertedPrice3 = convertedPrice + convertedPrice2;

        mDatabaseHelper.updateRecipePrice(convertedPrice3, selectedRecipieName);
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
                Intent intent = new Intent(IngredientLayoutScreen.this, ShoppingCartList.class);
                startActivity(intent);
            }
        });
        int shoppingCartCount = mDatabaseHelper.getShoppingCartCount();
        String shoppingCartString = String.valueOf(shoppingCartCount);
        mCountTv.setText(shoppingCartString);

        return super.onCreateOptionsMenu(menu);
    }

    public void removeItem(int position, String ingredientName) {

        Cursor data = mDatabaseHelper.getRecipieItemID(selectedRecipieName);
        int recipieID = -1;
        while (data.moveToNext()) {
            recipieID = data.getInt(0);
        }
        data = mDatabaseHelper.getIngredientItemID(ingredientName, recipieID);
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        Log.d(TAG, "ingredientId: " + itemID + " and ingredient name is: " + ingredientName);
        mDatabaseHelper.deleteIngredientName(itemID, ingredientName);

        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        toastMessage("Removed from database");
    }
    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if(requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(IngredientLayoutScreen.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //   imageView.setImageBitmap(bitmap);
                    imageButton.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();;
                    Toast.makeText(IngredientLayoutScreen.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //   imageView.setImageBitmap(thumbnail);
            imageButton.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(IngredientLayoutScreen.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure if needed
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdir();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()},new String[]{"image/jpeg"},null);
            fo.close();
            Log.d("TAG", "File Saved::-->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException el) {
            el.printStackTrace();
        }
        return "";
    }
        /*
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
    */
}
