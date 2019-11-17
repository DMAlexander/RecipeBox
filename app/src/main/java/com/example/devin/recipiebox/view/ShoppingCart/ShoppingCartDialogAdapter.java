package com.example.devin.recipiebox.view.ShoppingCart;

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

import com.example.devin.recipiebox.R;

import java.util.ArrayList;

public class ShoppingCartDialogAdapter extends RecyclerView.Adapter<ShoppingCartDialogAdapter.ShoppingCartViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "ShoppingCartAdapter";

    public ShoppingCartDialogAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public RelativeLayout relativeLayout;

        public ShoppingCartViewHolder( final View itemView ) {
            super(itemView);
            mTextView1 = itemView.findViewById( R.id.recipeItem );
            mTextView2 = itemView.findViewById( R.id.recipeQuantityItem );
            mTextView3 = itemView.findViewById( R.id.measurementType );
        }
    }

    @Override
    public ShoppingCartDialogAdapter.ShoppingCartViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_shopping_cart_dialog_item, parent, false );
        return new ShoppingCartDialogAdapter.ShoppingCartViewHolder( view );

    }

    @Override
    public void onBindViewHolder( ShoppingCartDialogAdapter.ShoppingCartViewHolder holder, final int position ) {
        if (!mCursor.moveToPosition( position ) ) {
            return;
        }

        String RecipeName = mCursor.getString( mCursor.getColumnIndex("RecipieName" ) );
        holder.mTextView1.setText( RecipeName );
        String recipieQuantity = mCursor.getString(mCursor.getColumnIndex("Quantity" ) );
        holder.mTextView2.setText( recipieQuantity );
        String measurementType = mCursor.getString(mCursor.getColumnIndex("MeasurementType" ) );
        holder.mTextView3.setText( measurementType );

    }

    @Override
    public int getItemCount() { return mCursor.getCount(); }
}
