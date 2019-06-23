package com.example.devin.recipiebox.view.Ingredient;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.view.Recipie.RecipeAdapter;

import java.util.ArrayList;

public class IngredientEditAdapter extends RecyclerView.Adapter<IngredientEditAdapter.IngredientViewHolder> {
    private ArrayList<IngredientItem> mIngredientList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "IngredientAdapter";

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position, String ingredientName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

    public IngredientEditAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mDeleteImage;

        public IngredientViewHolder(final View itemView, final OnItemClickListener listener) {
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
                        System.out.println("position: " + position);
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
                        String ingredientName = mTextView3.getText().toString();
                        Log.d(TAG, "ingredient name is: " + ingredientName);
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position, ingredientName);
                        }
                    }
                }
            });
        }
    }


    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //NOTE: I NEED TO MAKE AN 'activity_ingredient_item' XML file...
        View view = inflater.inflate(R.layout.activity_ingredient_item, parent, false);
        return new IngredientViewHolder(view, mListener);
    }


    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        /*
        mTextView1 = itemView.findViewById(R.id.spinner1Value);
            mTextView2 = itemView.findViewById(R.id.spinner2Value);
            mTextView3 = itemView.findViewById(R.id.ingredientTextView);
         */

        final String spinner1Value = mCursor.getString(mCursor.getColumnIndex("Quantity"));
        holder.mTextView1.setText(spinner1Value);
        final String spinner2Value = mCursor.getString(mCursor.getColumnIndex("MeasurementType"));
        holder.mTextView2.setText(spinner2Value);
        final String ingredientName = mCursor.getString(mCursor.getColumnIndex("IngredientName"));
        holder.mTextView3.setText(ingredientName);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
