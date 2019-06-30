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
import com.example.devin.recipiebox.view.RecipieFolders.RecipieFolderAdapter;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {
    private ArrayList<ShoppingCartItem> mShoppingCartList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "ShoppingCartAdapter";

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position, String ingredientName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

    public ShoppingCartAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView1;
        public final TextView mTextView2;
        public final TextView mTextView3;
        public ImageView mDeleteImage;
        public RelativeLayout relativeLayout;

        public ShoppingCartViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.spinner1Value);
            mTextView2 = itemView.findViewById(R.id.spinner2Value);
            mTextView3 = itemView.findViewById(R.id.ingredientTextView);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
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
                        Log.d(TAG, "ingredient name is: " + IngredientName);
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position, IngredientName);
                        }
                    }
                }
            });

        }
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_shopping_cart_item, parent, false);
        return new ShoppingCartViewHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(ShoppingCartViewHolder holder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
    /*
        final String ingredientName = mCursor.getString(mCursor.getColumnIndex("IngredientName"));
        holder.mTextView1.setText(ingredientName);
        Log.d(TAG, "Ingredient name value is: " + ingredientName);
    */
//        holder.mTextView1.setOnClickView(new View())

        final String spinner1ValString;
        final double spinner1Value = mCursor.getDouble(mCursor.getColumnIndex("Quantity"));
        if (spinner1Value == 0.125) {
            spinner1ValString = "1/8";
            System.out.print(spinner1ValString);
        } else if (spinner1Value == 0.25) {
            spinner1ValString = "1/4";
            System.out.print(spinner1ValString);
        } else if (spinner1Value == 0.50) {
            spinner1ValString = "1/2";
            System.out.print(spinner1ValString);
        } else if (spinner1Value == 1.0) {
            spinner1ValString = "1";
            System.out.print(spinner1ValString);
        } else if (spinner1Value == 2.0) {
            spinner1ValString = "2";
            System.out.print(spinner1ValString);
        } else if (spinner1Value == 3.0) {
            spinner1ValString = "3";
            System.out.print(spinner1ValString);
        } else {
            spinner1ValString = "0";
            System.out.print(spinner1ValString);
        }


        holder.mTextView1.setText(spinner1ValString);
        final String spinner2Value = mCursor.getString(mCursor.getColumnIndex("MeasurementType"));
        holder.mTextView2.setText(spinner2Value);
        final String ingredientName = mCursor.getString(mCursor.getColumnIndex("IngredientName"));
        holder.mTextView3.setText(ingredientName);
    }

    @Override
    public int getItemCount() { return mCursor.getCount(); }




}