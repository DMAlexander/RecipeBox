package com.example.devin.recipiebox.view.Menu;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipiebox.R;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfoAdapter;
import com.example.devin.recipiebox.view.PublishedIngredient.IngredientInfoItem;

import java.util.ArrayList;

public class ExportedRecipesAdapter extends RecyclerView.Adapter<ExportedRecipesAdapter.ExportedRecipesViewHolder> {

  //  private ArrayList<IngredientInfoItem> mIngredientList;
    private ExportedRecipesAdapter.OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "ExportedRecipesAdapter";

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position, String recipeName, String recipeId);
    }

    public void setOnItemClickListener(ExportedRecipesAdapter.OnItemClickListener listener) { mListener = listener; }

    public ExportedRecipesAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class ExportedRecipesViewHolder extends RecyclerView.ViewHolder {
        public TextView mRecipeIdView;
        public TextView mTextView1;
        public ImageView mDeleteImage;

        public ExportedRecipesViewHolder(final View itemView, final ExportedRecipesAdapter.OnItemClickListener listener) {
            super(itemView);
            mRecipeIdView = itemView.findViewById(R.id.recipeId);
            mTextView1 = itemView.findViewById(R.id.recipe);
            //         mTextView4 = itemView.findViewById(R.id.price_edit_text);
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
                        int position = getAdapterPosition();
                        String recipeName = mTextView1.getText().toString();
                        String recipeId = mRecipeIdView.getText().toString();
                        listener.onDeleteClick(position, recipeName, recipeId);
                    }
                }
            });
        }
    }

    @Override
    public ExportedRecipesAdapter.ExportedRecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //NOTE: I NEED TO MAKE AN 'activity_ingredient_item' XML file...
        View view = inflater.inflate(R.layout.activity_exported_recipes_menu_item, parent, false);
        return new ExportedRecipesAdapter.ExportedRecipesViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ExportedRecipesAdapter.ExportedRecipesViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        final String recipeId = mCursor.getString(mCursor.getColumnIndex("ExportedRecipeID"));
        holder.mRecipeIdView.setText(recipeId);

        final String recipeName = mCursor.getString(mCursor.getColumnIndex("RecipieName"));
        holder.mTextView1.setText(recipeName);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
