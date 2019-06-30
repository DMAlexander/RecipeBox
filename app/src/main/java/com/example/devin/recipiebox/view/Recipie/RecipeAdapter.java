package com.example.devin.recipiebox.view.Recipie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.database.DatabaseHelper;
import com.example.devin.recipiebox.view.Ingredient.IngredientScreen;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfo;
import com.example.devin.recipiebox.view.ShoppingCart.ShoppingCartList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<RecipeItem> mRecipeList;
    private OnItemClickListener mListener;
    private OnLongClickListener mListener2;
    private Context mContext;
    private Cursor mCursor;
    public DatabaseHelper mDatabaseHelper;

    private static final String TAG = "RecipieAdapter";

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position, String recipeName);
    }

    public interface OnLongClickListener {
        boolean onLongClick(int position, String recipeName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnLongClickListener(OnLongClickListener listener2) {
        mListener2 = listener2;
    }

    public RecipeAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
//        mDatabaseHelper = new DatabaseHelper(context.getApplicationContext());
        mDatabaseHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public ImageView mDeleteImage;
        public RelativeLayout relativeLayout;


        public RecipeViewHolder(final View itemView, final OnItemClickListener listener, final OnLongClickListener listener2) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        System.out.println("position: " + position + "getItemViewType: " + getItemViewType());
                        //           int position = getItemViewType();
                        //           int position = getItemId();
                        //           int position = itemView;

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            //            }
                            //          int position = getLayoutPosition();
                            //            mCursor.moveToPosition(position);
                            //            mContext.startActivity(new Intent(mContext, IngredientScreen.class));
                            //The above is me attemtping to navigate to the next view using an intent...
                            //If this works, I should also try to pass some data...
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //This tells us the mDeleteImage can be deleted...
                    if (listener != null) {
                        final int position = getAdapterPosition();
                        String recipeName = mTextView1.getText().toString();
                        Log.d(TAG, "recipie Name is: " + recipeName);
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position, recipeName);
                        }
                    }
                }
            });
            /*
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    return true;
                }
            });
            */
            /*
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener2 != null) {
                        final int position = getAdapterPosition();
                        String recipieName = mTextView1.getText().toString();
                        Log.d(TAG, "recipie Name is: " + recipieName);
                        if (position != RecyclerView.NO_POSITION) {
                            listener2.onLongClick(position, recipieName);
                        }
                    }
                    return true;
                }
            }); */
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_recipe_item, parent, false);
        return new RecipeViewHolder(view, mListener, mListener2);
 //       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recipe_item, parent, false);
 //       RecipeViewHolder rvh = new RecipeViewHolder(v, mListener);
 //       return rvh;

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        final String recipieName = mCursor.getString(mCursor.getColumnIndex("RecipieName"));
   //     final int recipieId = mCursor.getInt(mCursor.getColumnIndex("RecipieId"));
   //     Log.d(TAG, "Recipie ID value is: " + recipieId);
    //    RecipeItem currentItem = mRecipeList.get(position);
 //       holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView1.setText(recipieName);
 //       holder.mTextView1.setText(mCursor.getPosition());
//        holder.mTextView1.setText(String.valueOf(mCursor.getPosition()));
//        holder.mTextView1.setText(mCursor.getColumnIndex("ID"));
        Log.d(TAG, "Recipie Name value is: " + recipieName);

        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mCursor.getPosition());
     //           Intent intent = new Intent(mContext, IngredientScreen.class);
      //          Intent intent = new Intent(mContext, IngredientScreen.class);
                Intent intent = new Intent(mContext, IngredientInfo.class); //we're re-routing to IngredientInfo instead...
                intent.putExtra("RecipieName", recipieName);
                mContext.startActivity(intent);



            }

        });

        holder.mTextView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mListener != null) {
                    Log.d(TAG, "recipie Name is: " + recipieName);
                    String measurementType = "";
                    double quantity;
                    String quantityString;
                    String ingredientName = "";
                    String measurementType2 = "";
                    String quantityString2;
                    String ingredientName2 = "";
                    String quantityString3;
                    String ingredientName3 = "";

                    Cursor data = mDatabaseHelper.getRecipieItemID(recipieName);
                    int itemID = -1;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }
                    if (itemID > -1) {
                        Log.d(TAG, "onLongClick: The ID is: " + itemID);

                        Cursor data2 = mDatabaseHelper.getIngredientsBasedOnRecipieData(itemID);
                        ArrayList<String> listData = new ArrayList<>();
                        while(data2.moveToNext()) {
                            ingredientName = data2.getString(1);
                            quantityString = data2.getString(2);
              //              quantity = Integer.parseInt(q);
                            measurementType = data2.getString(3);
                            listData.add(ingredientName);
                            listData.add(quantityString);
                            listData.add(measurementType);
                        }

                            for (int j = 0; j < listData.size() / 3; j++) {
                                System.out.println(listData.get(j)); //0  //3   //6
                                ingredientName = listData.get(0 + (j * 3));     //1  //4  //7
                                quantityString = listData.get(1 + (j * 3));     //2  //5  //8
                                measurementType = listData.get(2 + (j * 3));
                                Double convertedQuantity = Double.parseDouble(quantityString);

                                Cursor data4 = mDatabaseHelper.getShoppingCartIngredient(ingredientName);
                                ArrayList<String> listData3 = new ArrayList<>();
                                while (data4.moveToNext()) {
                                    ingredientName2 = data4.getString(0);
                                    listData3.add(ingredientName2);
                                }

                                if (listData3.size() != 0) { //ingredient name already exists in shopping cart
                                    //we will need to put in the information for the specific row...
                                    Cursor data5 = mDatabaseHelper.getShoppingCartDataRow(ingredientName);
                                    ArrayList<String> listData4 = new ArrayList<>();
                                    while (data5.moveToNext()) {
                                        ingredientName2 = data5.getString(1);
                                        quantityString2 = data5.getString(2);
                                        measurementType2 = data5.getString(3);
                                        listData4.add(ingredientName2);
                                        listData4.add(quantityString2);
                                        listData4.add(measurementType2);
                                    }
                                    ingredientName2 = listData4.get(0);
                                    quantityString2 = listData4.get(1);
                                    measurementType2 = listData4.get(2);
                                    Double convertedQuantity2 = Double.parseDouble(quantityString2);
                                    System.out.println("Ingredient Name: " + ingredientName2 + "Quantity: " + quantityString2 + " measurement type: " + measurementType2);
                                    Double convertedQuantity3 = convertedQuantity + convertedQuantity2;
                                    mDatabaseHelper.updateShoppingCartQuantity(convertedQuantity3, ingredientName);
                                } else {
                                    boolean insertData = mDatabaseHelper.addShoppingCartData(ingredientName, convertedQuantity, measurementType);

                                    if (insertData) {
                                        Log.d(TAG, "Data is added to shopping cart list");
                                    } else {
                                        Log.d(TAG, "Something went wrong");
                                    }
                                }
/*
                                Cursor data3 = mDatabaseHelper.getShoppingCartData();
                                ArrayList<String> listData2 = new ArrayList<>();
                                while (data3.moveToNext()) {
                                    ingredientName2 = data3.getString(1);
                                    quantityString2 = data3.getString(2);
                                    measurementType2 = data3.getString(3);
                                    listData2.add(ingredientName2);
                                    listData2.add(quantityString2);
                                    listData2.add(measurementType2);
                                }

                                if (listData2.size() != 0) {
                                    for (int k = 0; k<listData2.size()/3; k++) {
                                        ingredientName2 = listData2.get(0 + (k * 3));
                                        quantityString2 = listData2.get(1 + (k * 3));
                                        measurementType2 = listData2.get(2+ (k * 3));
                                        Double convertedQuantity2 = Double.parseDouble(quantityString2);
                                        if (ingredientName.equalsIgnoreCase(ingredientName2)) {
                                            System.out.println("Quantity: " + quantityString2 + " measurement type: " + measurementType2);
                                            Double convertedQuantity3 = convertedQuantity + convertedQuantity2;
                                            mDatabaseHelper.updateShoppingCartQuantity(convertedQuantity3, ingredientName);
                                        } else {
                                            boolean insertData = mDatabaseHelper.addShoppingCartData(ingredientName, convertedQuantity, measurementType);

                                            if (insertData) {
                                                Log.d(TAG, "Data is added to shopping cart list");
                                            } else {
                                                Log.d(TAG, "Something went wrong");
                                            }
                                        }
                                    }
                                } else {
                                    boolean insertData = mDatabaseHelper.addShoppingCartData(ingredientName, convertedQuantity, measurementType);

                                    if (insertData) {
                                        Log.d(TAG, "Data is added to shopping cart list");
                                    } else {
                                        Log.d(TAG, "Something went wrong");
                                    }
                                } */
                            }
                    }
                }
                return true;
            }

        });
    }

/*
                Intent intent = new Intent(mContext, ShoppingCartList.class);
                intent.putExtra("RecipieName", recipieName);

                mContext.startActivity(intent);
                return true;
            }
        }); */

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /*
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
    */
}
