package com.example.devin.recipebox.view.ShoppingCart;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devin.recipebox.R;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "ShoppingCartAdapter";

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onQuantityClick( int position, String ingredientName );

        void onDeleteClick( int position, String ingredientName, String recipeName );
    }

    public void setOnItemClickListener( OnItemClickListener listener ) { mListener = listener; }

    public ShoppingCartAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView3;
        public TextView mTextView4;
        public ImageView mDeleteImage;
        public RelativeLayout relativeLayout;

        public ShoppingCartViewHolder( final View itemView, final OnItemClickListener listener ) {
            super(itemView);
            mTextView3 = itemView.findViewById( R.id.ingredientTextView );
            mTextView4 = itemView.findViewById( R.id.ingredientQuantity );
            mDeleteImage = itemView.findViewById( R.id.image_delete );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onItemClick( position );
                        }
                    }
                }
            });

            mTextView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //This tells us the mDeleteImage can be deleted...
                    if ( listener != null ) {
                        final int position = getAdapterPosition();
                        String ingredientName = mTextView3.getText().toString();
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onQuantityClick( position, ingredientName );
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        final int position = getAdapterPosition();
                        String IngredientName = mTextView3.getText().toString();
                        String recipeName = mTextView3.getText().toString();
                        Log.d( TAG, "ingredient name is: " + IngredientName );
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onDeleteClick( position, IngredientName, recipeName );
                        }
                    }
                }
            });

        }
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_shopping_cart_item, parent, false );
        return new ShoppingCartViewHolder( view, mListener );

    }

    @Override
    public void onBindViewHolder( ShoppingCartViewHolder holder, final int position ) {
        if ( !mCursor.moveToPosition( position ) ) {
            return;
        }

        String hasIngredientsIndicator = mCursor.getString( mCursor.getColumnIndex("RecipeHasIngredients" ) );
        if( hasIngredientsIndicator.equalsIgnoreCase("Y" ) ) {

            String ingredientName = mCursor.getString( mCursor.getColumnIndex("IngredientName" ) );
            holder.mTextView3.setText(ingredientName );
            String recipeQuantity = mCursor.getString( mCursor.getColumnIndex("RecipeQuantity" ) );
            holder.mTextView4.setText( "(" + recipeQuantity + ")" );

        } else {

            String recipeName = mCursor.getString( mCursor.getColumnIndex("RecipeName" ) );
            holder.mTextView3.setText( recipeName );
            holder.mTextView4.setText( null );

        }
    }

    @Override
    public int getItemCount() { return mCursor.getCount(); }




}
