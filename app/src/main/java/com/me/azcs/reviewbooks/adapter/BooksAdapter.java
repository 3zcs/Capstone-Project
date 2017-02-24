package com.me.azcs.reviewbooks.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.me.azcs.reviewbooks.R;
import com.me.azcs.reviewbooks.activities.ResultActivity;
import com.me.azcs.reviewbooks.activities.ResultBooksActivity;
import com.me.azcs.reviewbooks.models.Item;
import com.me.azcs.reviewbooks.models.VolumeInfo;
import com.squareup.picasso.Picasso;
import java.util.List;

import static com.me.azcs.reviewbooks.Constant.DATA;

/**
 * Created by azcs on 13/02/17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    List<Item> mDataset;
    Context mContext;

    public BooksAdapter(List<Item> itemList,Context context) {
        mDataset = itemList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card_view , parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(mDataset.get(position).getVolumeInfo().getTitle()))
            holder.mName.setText(mDataset.get(position).getVolumeInfo().getTitle());
        if(mDataset.get(position).getVolumeInfo().getAuthors() != null)
            if (!TextUtils.isEmpty(mDataset.get(position).getVolumeInfo().getAuthors().get(0)))
                holder.mAuthor.setText(mDataset.get(position).getVolumeInfo().getAuthors().get(0));
        holder.item = mDataset.get(position);

        if (mDataset.get(position).getVolumeInfo().getImageLinks() != null)
            Picasso.with(mContext)
                    .load(mDataset.get(position).getVolumeInfo().getImageLinks().getThumbnail())
                    .placeholder(R.drawable.defimage)
                    .into(holder.mBookCover);
        else
            Picasso.with(mContext)
                    .load(R.drawable.defimage)
                    .placeholder(R.drawable.defimage)
                    .into(holder.mBookCover);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mBookCardView;
        TextView mName ,mAuthor;
        ImageView mBookCover;
        Item item ;
        public ViewHolder(View itemView) {
            super(itemView);
            mBookCardView = (CardView)itemView.findViewById(R.id.book_cardview);
            mName = (TextView)itemView.findViewById(R.id.bookName);
            mAuthor = (TextView)itemView.findViewById(R.id.authorName);
            mBookCover = (ImageView)itemView.findViewById(R.id.book_cover);

            mBookCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ResultBooksActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra(DATA,item));
                }
            });
        }
    }
}
