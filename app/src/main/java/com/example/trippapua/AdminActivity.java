package com.example.trippapua;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
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

public class AdminActivity extends AppCompatActivity {

    private SearchView searchViewAdmin;
    private RecyclerView recyclerViewAdmin;
    private DestinationAdapter adapter;
    private List<Destination> destinationList;

    private ImageButton profileButton, settingsButton, likeButton, addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        searchViewAdmin = findViewById(R.id.searchViewAdmin);
        recyclerViewAdmin = findViewById(R.id.recyclerViewAdmin);
        profileButton = findViewById(R.id.profileButtonAdmin);
        settingsButton = findViewById(R.id.settingsButtonAdmin);
        likeButton = findViewById(R.id.likeButtonAdmin);
        addButton = findViewById(R.id.addButtonAdmin);

        recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(this));
        destinationList = new ArrayList<>();
        adapter = new DestinationAdapter(destinationList, this, true);
        recyclerViewAdmin.setAdapter(adapter);

        loadDestinations();

        searchViewAdmin.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterDestinations(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDestinations(newText);
                return false;
            }
        });

        profileButton.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, ProfileActivity.class)));
        settingsButton.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, SettingsActivity.class)));
        likeButton.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, LikeActivity.class)));
        addButton.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, AddDestinationActivity.class)));
    }

    private void loadDestinations() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Destinations");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                destinationList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Destination destination = data.getValue(Destination.class);
                    destinationList.add(destination);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    private void filterDestinations(String query) {
        List<Destination> filteredList = new ArrayList<>();
        for (Destination destination : destinationList) {
            if (destination.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    destination.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(destination);
            }
        }
        adapter.filterList(filteredList);
    }
}
