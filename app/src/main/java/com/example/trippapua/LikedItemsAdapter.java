package com.example.trippapua;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LikedItemsAdapter extends RecyclerView.Adapter<LikedItemsAdapter.LikedItemViewHolder> {

    private List<Destination> likedItems;

    public LikedItemsAdapter(List<Destination> likedItems) {
        this.likedItems = likedItems;
    }

    @NonNull
    @Override
    public LikedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false);
        return new LikedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikedItemViewHolder holder, int position) {
        Destination currentItem = likedItems.get(position);
        holder.likeTitleTextView.setText(currentItem.getTitle());
        holder.likeDescriptionTextView.setText(currentItem.getDescription());
        Picasso.get().load(currentItem.getImageUrl()).into(holder.likeImageView);
    }

    @Override
    public int getItemCount() {
        return likedItems.size();
    }

    static class LikedItemViewHolder extends RecyclerView.ViewHolder {
        TextView likeTitleTextView, likeDescriptionTextView;
        ImageView likeImageView;

        public LikedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            likeTitleTextView = itemView.findViewById(R.id.likeTitleTextView);
            likeDescriptionTextView = itemView.findViewById(R.id.likeDescriptionTextView);
            likeImageView = itemView.findViewById(R.id.likeImageView);
        }
    }
}
