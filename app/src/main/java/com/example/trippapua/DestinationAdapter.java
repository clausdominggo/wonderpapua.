package com.example.trippapua;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    private List<Destination> destinationList;
    private Context context;
    private boolean isAdmin;

    public DestinationAdapter(List<Destination> destinationList, Context context, boolean isAdmin) {
        this.destinationList = destinationList;
        this.context = context;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_destination, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.titleTextView.setText(destination.getTitle());
        holder.descriptionTextView.setText(destination.getDescription());
        Glide.with(context).load(destination.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            if (isAdmin) {
                intent = new Intent(context, AdminDestinationDetailActivity.class);
            } else {
                intent = new Intent(context, UserDestinationDetailActivity.class);
            }
            intent.putExtra("id", destination.getId());
            intent.putExtra("title", destination.getTitle());
            intent.putExtra("description", destination.getDescription());
            intent.putExtra("imageUrl", destination.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public void filterList(List<Destination> filteredList) {
        destinationList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
