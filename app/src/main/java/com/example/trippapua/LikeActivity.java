package com.example.trippapua;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LikeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LikedItemsAdapter adapter;
    private List<Destination> likedItems; // Use the Destination model
    private DatabaseReference likeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        recyclerView = findViewById(R.id.recyclerViewLikes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        likedItems = new ArrayList<>();
        adapter = new LikedItemsAdapter(likedItems);
        recyclerView.setAdapter(adapter);

        likeRef = FirebaseDatabase.getInstance().getReference("Likes");

        // Fetch data from Firebase
        likeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                likedItems.clear(); // Clear the list to avoid duplication
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Destination likedItem = dataSnapshot.getValue(Destination.class);
                    if (likedItem != null) {
                        likedItems.add(likedItem);
                    }
                }
                adapter.notifyDataSetChanged(); // Refresh the RecyclerView
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LikeActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
