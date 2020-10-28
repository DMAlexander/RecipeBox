package com.example.devin.recipebox.view.RecipeFolders;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.recipebox.R;
import com.example.devin.recipebox.database.DatabaseHelper;
import com.example.devin.recipebox.view.Recipe.MainActivity;

import java.io.File;
import java.util.ArrayList;

public class RecipeFolderAdapter extends RecyclerView.Adapter<RecipeFolderAdapter.RecipeFolderViewHolder> {
    private ArrayList<RecipeFolderItem> mRecipeFolderList;
    private OnItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;
    private DatabaseHelper mDatabaseHelper;

    private  static final String TAG = "RecipeFolderAdapter";
    private static final String IMAGE_DIRECTORY = "/demonuts";

    public interface OnItemClickListener {
        void onItemClick( int position );

    }

    public void setOnItemClickListener( OnItemClickListener listener ) { mListener = listener; }

    public RecipeFolderAdapter(Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public static class RecipeFolderViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public CardView mCardView;
        public ImageView mImageView;

        public RecipeFolderViewHolder( final View itemView, final OnItemClickListener listener ) {
            super( itemView );
            mTextView1 = itemView.findViewById( R.id.textView );
            mCardView = itemView.findViewById( R.id.cardview_id );
            mImageView = itemView.findViewById( R.id.iv );

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    if ( listener != null ) {
                        int position = getAdapterPosition();
                        System.out.println( "position: " + position );
                        if( position != RecyclerView.NO_POSITION ) {
                            listener.onItemClick( position );
                        }
                    }
                }
            });
        }
    }

    public RecipeFolderViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from( mContext );
        View view = inflater.inflate( R.layout.activity_recipe_folder_item, parent, false );
        return new RecipeFolderViewHolder( view, mListener );
    }

    @Override
    public void onBindViewHolder( RecipeFolderViewHolder holder, int position ) {
        if ( !mCursor.moveToPosition( position ) ) {
            return;
        }

        final int recipeFolderID = mCursor.getInt( mCursor.getColumnIndex("FolderID" ) );
        final String recipeFolderName = mCursor.getString( mCursor.getColumnIndex("RecipeFolderName" ) );
        holder.mTextView1.setText(recipeFolderName);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d( TAG, "onClick: clicked on: " + mCursor.getPosition() );
                Log.d( TAG, "onClick: recipeFolderID is: " + recipeFolderID );
                Intent intent = new Intent(mContext, MainActivity.class );
                intent.putExtra("FolderID", recipeFolderID );
                intent.putExtra("RecipeFolderName", recipeFolderName );
                mContext.startActivity( intent );
            }
        });

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY );
        String fileName = "/myImage" + recipeFolderID;
        //    File imgFile = new File("/storage/emulated/0/demonuts/" + fileName + ".jpg");
        File imgFile = new File(wallpaperDirectory + fileName + ".jpg" );
        if(imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile( imgFile.getAbsolutePath() );
            holder.mImageView.setImageBitmap( bitmap );
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
