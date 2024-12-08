package com.example.trippapua;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserDestinationDetailActivity extends AppCompatActivity {

    private ImageView imageViewDestination;
    private TextView textViewTitle, textViewLongDescription;
    private ImageButton buttonLike;
    private DatabaseReference databaseRef;
    private ImageButton iconProfile, settingsIcon, likeIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_destination_detail);

        imageViewDestination = findViewById(R.id.imageViewDestination);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewLongDescription = findViewById(R.id.textViewLongDescription);
        buttonLike = findViewById(R.id.buttonLike);
        iconProfile = findViewById(R.id.iconProfile);
        settingsIcon = findViewById(R.id.iconSettings);
        likeIcon = findViewById(R.id.iconLike);
        // Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("Destinations");

        // Get data from intent
        String destinationId = getIntent().getStringExtra("destinationId");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String title = getIntent().getStringExtra("title");
        String longDescription = getIntent().getStringExtra("longDescription");

        // Set data to views
        Picasso.get().load(imageUrl).into(imageViewDestination);
        textViewTitle.setText(title);
        textViewLongDescription.setText(longDescription);
        // Set click listeners for icons
        iconProfile.setOnClickListener(v -> {
            Intent intent = new Intent(UserDestinationDetailActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        settingsIcon.setOnClickListener(v -> {
            Intent intent = new Intent(UserDestinationDetailActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        likeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(UserDestinationDetailActivity.this, LikeActivity.class);
            startActivity(intent);
        });

        buttonLike.setOnClickListener(v -> {

            SharedPreferences sharedPreferences = getSharedPreferences("Likes", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("likedTitle", title);
            editor.putString("likedDescription", longDescription);
            editor.putString("likedImageUrl", imageUrl);
            editor.apply();
            Log.d("LikeActivity", "Title: " + title);
            Log.d("LikeActivity", "Description: " + longDescription);
            Log.d("LikeActivity", "ImageUrl: " + imageUrl);


        });

    }
}
