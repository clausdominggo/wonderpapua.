package com.example.trippapua;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDescriptionActivity extends AppCompatActivity {

    private EditText editTextDescription;
    private Button buttonSaveDescription;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);

        // Initialize views
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSaveDescription = findViewById(R.id.buttonSaveDescription);

        // Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("Destinations");

        // Enable Save button when there is text in the description field
        editTextDescription.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!TextUtils.isEmpty(charSequence)) {
                    buttonSaveDescription.setEnabled(true);
                } else {
                    buttonSaveDescription.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });

        // Set up click listener for the save button
        buttonSaveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editTextDescription.getText().toString().trim();

                if (TextUtils.isEmpty(description)) {
                    Toast.makeText(AddDescriptionActivity.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save description to Firebase
                saveDescription(description);
            }
        });
    }

    private void saveDescription(String description) {
        String destinationId = "some_unique_id";  // This should be replaced by a unique ID for each destination
        // Create a new map or object to store the description
        databaseRef.child(destinationId).child("long_description").setValue(description)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddDescriptionActivity.this, "Description saved successfully", Toast.LENGTH_SHORT).show();
                        // Clear input field after saving
                        editTextDescription.setText("");
                    } else {
                        Toast.makeText(AddDescriptionActivity.this, "Failed to save description", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
