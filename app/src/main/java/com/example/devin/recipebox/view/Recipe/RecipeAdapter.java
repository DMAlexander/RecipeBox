package com.example.devin.recipebox.view.Recipe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.NewIngredient.IngredientLayoutScreen;
import com.example.devin.recipebox.view.PublishedIngredient.IngredientInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private OnItemClickListener mListener;
    private OnLongClickListener mListener2;
    private Context mContext;
    private Cursor mCursor;
    public DatabaseHelper mDatabaseHelper;
    private static final String IMAGE_DIRECTORY = "/demonuts";

    private static final String TAG = "RecipeAdapter";

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onDeleteClick( int position, String recipeName );
    }

    public interface OnLongClickListener {
        boolean onLongClick( int position, String recipeName );
    }

    public void setOnItemClickListener( OnItemClickListener listener ) {
        mListener = listener;
    }

    public void setOnLongClickListener( OnLongClickListener listener2 ) {
        mListener2 = listener2;
    }

    public RecipeAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
        mDatabaseHelper = new DatabaseHelper( context.getApplicationContext() );
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public ImageView mEditImage;
        public ImageView mDeleteImage;
        public ImageView mRecipeImage;


        public RecipeViewHolder( final View itemView, final OnItemClickListener listener, final OnLongClickListener listener2 ) {
            super( itemView );
            mTextView1 = itemView.findViewById( R.id.textView );
            mEditImage = itemView.findViewById( R.id.image_edit );
            mDeleteImage = itemView.findViewById( R.id.image_delete );
            mRecipeImage = itemView.findViewById( R.id.recipeImage );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        System.out.println( "position: " + position + "getItemViewType: " + getItemViewType() );

                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onItemClick( position );
                        }
                    }
                }
            });

            /**
             * Tells us the mDeleteImage can be deleted...
             */
            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( listener != null ) {
                        final int position = getAdapterPosition();
                        String recipeName = mTextView1.getText().toString();
                        Log.d( TAG, "recipe Name is: " + recipeName );
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onDeleteClick( position, recipeName );
                        }
                    }
                }
            });
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_recipe_item, parent, false );
        return new RecipeViewHolder( view, mListener, mListener2 );
    }

    @Override
    public void onBindViewHolder( RecipeViewHolder holder, final int position ) {
        if ( !mCursor.moveToPosition( position ) ) {
            return;
        }

        final String recipeName = mCursor.getString( mCursor.getColumnIndex("RecipeName" ) );
        holder.mTextView1.setText( recipeName );

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY );
        String fileName = "/myImage" + recipeName;
        File imgFile = new File(wallpaperDirectory + fileName + ".jpg" );
        if( imgFile.exists() ) {
            Bitmap bitmap = BitmapFactory.decodeFile( imgFile.getAbsolutePath() );
            holder.mRecipeImage.setImageBitmap( bitmap );
        }

        Log.d( TAG, "Recipe Name value is: " + recipeName );

        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d( TAG, "onClick: clicked on: " + mCursor.getPosition() );
                Intent intent = new Intent( mContext, IngredientInfo.class ); //we're re-routing to IngredientInfo instead...
                intent.putExtra( "RecipeName", recipeName );
                mContext.startActivity( intent );

            }

        });

        String recipeHasIngredients = mCursor.getString( mCursor.getColumnIndex("RecipeHasIngredients" ) );
        if(recipeHasIngredients.equalsIgnoreCase("N" ) ) {
            holder.mEditImage.setVisibility( View.GONE );
        }

        holder.mEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor data = mDatabaseHelper.getRecipeItemID( recipeName );
                int itemID = -1;
                while ( data.moveToNext() ) {
                    itemID = data.getInt(0);
                }

                Intent intent = new Intent( mContext, IngredientLayoutScreen.class );
                intent.putExtra("RecipeName", recipeName );
                intent.putExtra("RecipeId", itemID );
                mContext.startActivity( intent );
            }
        });

        holder.mTextView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mListener != null) {
                    Log.d( TAG, "recipe Name is: " + recipeName );
                    String measurementType = "";
                    String quantityString;
                    String ingredientName = "";
                    String measurementType2 = "";
                    String quantityString2;
                    String ingredientName2 = "";
                    String ingredientQuantity = "";
                    String hasIngredients = "Y"; //Dictates if it has ingredients or not...

                    Cursor data6 = mDatabaseHelper.getRecipeItemID( recipeName );
                    Cursor data9 = mDatabaseHelper.getRecipeHasIngredients( recipeName );
                    int itemID2 = -1;
                    while ( data6.moveToNext() ) {
                        itemID2 = data6.getInt(0);
                    }
                    while ( data9.moveToNext() ) {
                        hasIngredients = data9.getString(0);
                    }

                    if (itemID2 > -1) {
                        Log.d(TAG, "onLongClick: The ID is: " + itemID2);

                        Cursor data7 = mDatabaseHelper.getIngredientsBasedOnRecipeData( itemID2 );
                        ArrayList<String> listData4 = new ArrayList<>();
                        while ( data7.moveToNext() ) {
                            ingredientName = data7.getString(1);
                            ingredientQuantity = data7.getString(2);
                            measurementType = data7.getString(3);
                            listData4.add( ingredientName );
                            listData4.add( ingredientQuantity );
                            listData4.add( measurementType );
                        }
                    }

                    /**
                     * Decides if exported recipe has ingredients or not
                     */
                    if ( hasIngredients.equalsIgnoreCase("Y" ) ) {
                        boolean insertData2 = mDatabaseHelper.addExportedRecipeData( recipeName, ingredientName, ingredientQuantity, measurementType );

                        if ( insertData2 ) {
                            Log.d( TAG, "Data is Exported to the Shopping Cart" );
                        } else {
                            Log.d( TAG, "Something went wrong" );
                        }
                    } else {
                        boolean insertData2 = mDatabaseHelper.addExportedRecipeData( recipeName, null, null, null );

                        if ( insertData2 ) {
                            Log.d( TAG, "Data is Exported to the Shopping Cart - No Ingredients" );
                        } else {
                            Log.d( TAG, "Something went wrong" );
                        }
                    }

                    Cursor data = mDatabaseHelper.getRecipeItemID( recipeName );
                    int itemID = -1;
                    while ( data.moveToNext() ) {
                        itemID = data.getInt(0);
                    }
                    if ( itemID > -1 ) {
                        Log.d( TAG, "onLongClick: The ID is: " + itemID );

                        Cursor data2 = mDatabaseHelper.getIngredientsBasedOnRecipeData( itemID );
                        ArrayList<String> listData = new ArrayList<>();
                        while ( data2.moveToNext() ) {
                            ingredientName = data2.getString(1);
                            quantityString = data2.getString(2);
                            measurementType = data2.getString(3);
                            listData.add( ingredientName );
                            listData.add( quantityString );
                            listData.add( measurementType );
                        }

                        //If exported recipe has no ingredients, we will need to export into the shopping cart list differently
                        if (hasIngredients.equalsIgnoreCase("N" ) ) {
                            String fillerQuantity = "0";
                            Double fillerQuantityVal = Double.parseDouble( fillerQuantity );
                            boolean insertData = mDatabaseHelper.addShoppingCartData( recipeName, null, fillerQuantityVal, null, 0, hasIngredients );

                            if ( insertData ) {
                                Log.d( TAG, "Data is added to shopping cart list" );
                            } else {
                                Log.d( TAG, "Something went wrong" );
                            }

                        } else {

                            for (int j = 0; j < listData.size() / 3; j++ ) {
                                System.out.println( listData.get( j ) );
                                ingredientName = listData.get( 0 + (j * 3) );
                                quantityString = listData.get( 1 + (j * 3) );
                                measurementType = listData.get( 2 + (j * 3) );
                                Double convertedQuantity = Double.parseDouble( quantityString );

                                Cursor data4 = mDatabaseHelper.getShoppingCartIngredient( ingredientName );
                                ArrayList<String> listData3 = new ArrayList<>();
                                while ( data4.moveToNext() ) {
                                    ingredientName2 = data4.getString(0);
                                    listData3.add( ingredientName2 );
                                }

                                /**
                                 * Get all exported shopping cart data
                                 */
                                Cursor data10 = mDatabaseHelper.getExportedShoppingCartInfo();
                                int exportedRecipeCount = 0;
                                String ingredientName11 = "";
                                while ( data10.moveToNext() ) { //goes through every exported recipe
                                    String recipeName10 = data10.getString(1); //gets recipeName
                                    //get recipeId from recipeName
                                    Cursor data11 = mDatabaseHelper.getRecipeItemID( recipeName10 );
                                    while( data11.moveToNext() ) {
                                        int recipeID = data11.getInt(0);
                                        Cursor data12 = mDatabaseHelper.getIngredientsBasedOnRecipeData( recipeID );
                                        while( data12.moveToNext() ) {
                                            ingredientName11 = data12.getString(1);
                                            if(ingredientName11.equalsIgnoreCase( ingredientName ) ) {
                                                exportedRecipeCount++;
                                            }
                                        }
                                    }
                                }

                            //    exportedRecipeCount = mDatabaseHelper.getExportedRecipeCount(recipeName);
                                mDatabaseHelper.updateShoppingCartRecipeCount( exportedRecipeCount, ingredientName );

                                if ( listData3.size() != 0 ) { //ingredient name already exists in shopping cart
                                    //we will need to put in the information for the specific row...
                                    Cursor data5 = mDatabaseHelper.getShoppingCartDataRow( ingredientName );
                                    ArrayList<String> listData7 = new ArrayList<>();
                                    while ( data5.moveToNext() ) {
                                        ingredientName2 = data5.getString(2);
                                        quantityString2 = data5.getString(3);
                                        measurementType2 = data5.getString(5);
                                        listData7.add( ingredientName2 );
                                        listData7.add( quantityString2 );
                                        listData7.add( measurementType2 );
                                    }
                                    ingredientName2 = listData7.get(0);
                                    quantityString2 = listData7.get(1);
                                    measurementType2 = listData7.get(2);

                                    Double convertedQuantity2 = Double.parseDouble( quantityString2 );
                                    //                 Double convertedPrice2 = Double.parseDouble(price2);
                                    System.out.println( "Ingredient Name: " + ingredientName2 + "Quantity: " + quantityString2 + " measurement type: " + measurementType2 );

                                    //Measurement Type conversions
                                    Double convertedMeasurementType1 = 1.0;
                                    Double convertedMeasurementType2 = 1.0;

                                    //"MeasurementType" is from the recipe Info being added
                                    if ( measurementType.equals("tsp") ) {
                                        convertedMeasurementType1 = 1.0;
                                    } else if ( measurementType.equals( "tbsp" ) ) {
                                        convertedMeasurementType1 = 3.0;
                                    } else if ( measurementType.equals( "c" ) ) {
                                        convertedMeasurementType1 = 48.0;
                                    } else if ( measurementType.equals( "pt" ) ) {
                                        convertedMeasurementType1 = 96.0;
                                    } else if ( measurementType.equals( "qt" ) ) {
                                        convertedMeasurementType1 = 192.0;
                                    } else if ( measurementType.equals( "gal" ) ) {
                                        convertedMeasurementType1 = 768.0;
                                    }

                                    //"MeasurementType2" is from the Shopping Cart row that already exists
                                    if ( measurementType2.equals( "tsp" ) ) {
                                        convertedMeasurementType2 = 1.0;
                                    } else if ( measurementType2.equals( "tbsp") ) {
                                        convertedMeasurementType2 = 3.0;
                                    } else if ( measurementType2.equals( "c" ) ) {
                                        convertedMeasurementType2 = 48.0;
                                    } else if ( measurementType2.equals( "pt" ) ) {
                                        convertedMeasurementType2 = 96.0;
                                    } else if ( measurementType2.equals( "qt" ) ) {
                                        convertedMeasurementType2 = 192.0;
                                    } else if ( measurementType2.equals( "gal" ) ) {
                                        convertedMeasurementType2 = 768.0;
                                    }

                                    //Make factor based on measurementType(s) on both added recipe and shoppingCart
                                    Double measurementFactor = convertedMeasurementType1 / convertedMeasurementType2;
                                    //How many are added to the shoppingCart times the factor
                                    //Ex) 1/3 * 6 = 2.0
                                    Double convertedAddedQuantity = measurementFactor * convertedQuantity;
                                    //Add quantity from line above to existing ShoppingCart row
                                    Double sCartQuantity = convertedAddedQuantity + convertedQuantity2;

                                    Double newQuantity = sCartQuantity;
                                    //If sCartQuantity is above a certain threshold, we want to change the measurementType
                                    if ( sCartQuantity > 3 && measurementType2.equals( "tsp" ) ) {
                                        newQuantity = sCartQuantity / 3;
                                        measurementType = "tbsp";
                                    } else if ( sCartQuantity > 16 && measurementType2.equals( "tbsp" ) ) {
                                        newQuantity = sCartQuantity / 16;
                                        measurementType = "c";
                                    } else if ( sCartQuantity > 2 && measurementType2.equals( "c" ) ) {
                                        newQuantity = sCartQuantity / 2;
                                        measurementType = "pt";
                                    } else if ( sCartQuantity > 2 && measurementType2.equals( "pt" ) ) {
                                        newQuantity = sCartQuantity / 2;
                                        measurementType = "qt";
                                    } else if ( sCartQuantity > 4 && measurementType2.equals( "qt" ) ) {
                                        newQuantity = sCartQuantity / 4;
                                        measurementType = "gal";
                                    } else {
                                        measurementType = measurementType2;
                                    }

                                    mDatabaseHelper.updateShoppingCartQuantity( newQuantity, ingredientName );
                                    mDatabaseHelper.updateShoppingCartMeasurementType( measurementType, ingredientName );
                                } else {

                                    boolean insertData = mDatabaseHelper.addShoppingCartData( recipeName, ingredientName, convertedQuantity, measurementType, exportedRecipeCount, hasIngredients );

                                    if ( insertData ) {
                                        Log.d( TAG, "Data is added to shopping cart list" );
                                    } else {
                                        Log.d( TAG, "Something went wrong" );
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }

        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
