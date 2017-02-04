package com.fabianuribe.newssearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabianuribe.newssearch.R;
import com.fabianuribe.newssearch.models.Doc;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by uribe on 2/3/17.
 */

public class DocumentsAdapter extends
        RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSnippet;
        public ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSnippet = (TextView) itemView.findViewById(R.id.tvSnippet);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
        }
    }

    private List<Doc> mDocuments;
    private Context mContext;

    public DocumentsAdapter(Context context, List<Doc> documents) {
        mDocuments = documents;
        mContext = context;
    }

    private Context getContext() {
                               return mContext;
                                               }

    @Override
    public DocumentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        final View contactView = inflater.inflate(R.layout.document_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DocumentsAdapter.ViewHolder viewHolder, int position) {
        Doc document = mDocuments.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.tvSnippet;
        textView.setText(document.getSnippet());

        ImageView thumbnail = viewHolder.ivThumbnail;
        Picasso.with(getContext()).load(document.getThumbnail())
                .fit()
                .centerCrop()
                .into(thumbnail);
    }

    @Override
    public int getItemCount() {
        return mDocuments.size();
    }
}
