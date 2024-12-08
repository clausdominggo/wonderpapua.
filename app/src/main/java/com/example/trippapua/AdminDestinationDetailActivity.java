package com.example.trippapua;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminDestinationDetailActivity extends AppCompatActivity {

    private ImageView imageViewDestination;
    private TextView textViewTitle, textViewLongDescription;
    private ImageButton buttonLike, buttonAddDescription;
    private ImageButton iconProfile, settingsIcon, likeIcon;
    private DatabaseReference likeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_destination_detail);

        imageViewDestination = findViewById(R.id.imageViewDestination);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewLongDescription = findViewById(R.id.textViewLongDescription);
        buttonLike = findViewById(R.id.buttonLike);
        buttonAddDescription = findViewById(R.id.buttonAddDescription);

        likeRef = FirebaseDatabase.getInstance().getReference("Likes");
        iconProfile = findViewById(R.id.iconProfile);
        settingsIcon = findViewById(R.id.iconSettings);
        likeIcon = findViewById(R.id.iconLike);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        textViewTitle.setText(title);
        textViewLongDescription.setText(description);
        Picasso.get().load(imageUrl).into(imageViewDestination);

        buttonLike.setOnClickListener(v -> {
            String likeId = likeRef.push().getKey();
            Destination like = new Destination(likeId, title, description, imageUrl);
            likeRef.child(likeId).setValue(like)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminDestinationDetailActivity.this, "Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminDestinationDetailActivity.this, "Failed to like", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        iconProfile.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDestinationDetailActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        settingsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDestinationDetailActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        likeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDestinationDetailActivity.this, LikeActivity.class);
            startActivity(intent);
        });
    }
}
