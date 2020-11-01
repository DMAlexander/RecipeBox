package com.example.devin.recipebox.view.PublishedIngredient;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipebox.R;

import java.util.ArrayList;

public class IngredientInfoAdapter extends RecyclerView.Adapter<IngredientInfoAdapter.IngredientInfoViewHolder> {
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    private static final String TAG = "IngredientInfoAdapter";

    public interface OnItemClickListener {
        void onItemClick( int position );

        void onDeleteClick( int position, String ingredientName );
    }

    public void setOnItemClickListener( OnItemClickListener listener ) { mListener = listener; }

    public IngredientInfoAdapter( Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class IngredientInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mDeleteImage;

        public IngredientInfoViewHolder( final View itemView, final OnItemClickListener listener ) {
            super( itemView );
            mTextView1 = itemView.findViewById( R.id.spinner1Value );
            mTextView2 = itemView.findViewById( R.id.spinner2Value );
            mTextView3 = itemView.findViewById( R.id.ingredientTextView );
            mDeleteImage = itemView.findViewById( R.id.image_delete );
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        System.out.println( "position: " + position );
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onItemClick( position );
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        String ingredientName = mTextView3.getText().toString();
                        Log.d( TAG, "ingredient name is: " + ingredientName );
                        if ( position != RecyclerView.NO_POSITION ) {
                            listener.onDeleteClick( position, ingredientName );
                        }
                    }
                }
            });
        }
    }

    @Override
    public IngredientInfoViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_ingredient_info_item, parent, false );
        return new IngredientInfoViewHolder( view, mListener );
    }

    @Override
    public void onBindViewHolder( IngredientInfoViewHolder holder, int position ) {
        if (!mCursor.moveToPosition( position ) ) {
            return;
        }

        final String spinner1ValString;
        final double spinner1Value = mCursor.getDouble(mCursor.getColumnIndex("Quantity" ) );
        if ( spinner1Value == 0.125 ) {
            spinner1ValString = "1/8";
            System.out.print( spinner1Value );
        } else if ( spinner1Value == 0.25 ) {
            spinner1ValString = "1/4";
            System.out.print( spinner1Value );
        } else if ( spinner1Value == 0.50 ) {
            spinner1ValString = "1/2";
            System.out.print( spinner1Value );
        } else if ( spinner1Value == 1.0 ) {
            spinner1ValString = "1";
            System.out.print( spinner1Value );
        } else if ( spinner1Value == 2.0 ) {
            spinner1ValString = "2";
            System.out.print( spinner1Value );
        } else if ( spinner1Value == 3.0 ) {
            spinner1ValString = "3";
            System.out.print( spinner1Value );
        } else {
            spinner1ValString = String.valueOf( spinner1Value );
            System.out.print( spinner1Value );
        }

        holder.mTextView1.setText( spinner1ValString );
        final String spinner2Value = mCursor.getString(mCursor.getColumnIndex("MeasurementType" ) );
        holder.mTextView2.setText( spinner2Value );
        final String ingredientName = mCursor.getString(mCursor.getColumnIndex("IngredientName" ) );
        holder.mTextView3.setText( ingredientName );

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
