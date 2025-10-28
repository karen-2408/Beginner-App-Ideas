package com.example.menghraj_emotilog;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Second extends AppCompatActivity {
    private Button Angry, Calm, Confused, Crying, Happy, InLove, Sad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button navigateButton = findViewById(R.id.timestamp);
        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to go to TimestampActivity
                Intent intent = new Intent(Second.this, Timestamp.class);
                startActivity(intent); // Start the SecondActivity
            }
        });

        // Find all 7 Buttons
        Angry = findViewById(R.id.Angry);
        Calm = findViewById(R.id.Calm);
        Confused = findViewById(R.id.Confused);
        Crying = findViewById(R.id.Crying);
        Happy = findViewById(R.id.Happy);
        InLove = findViewById(R.id.InLove);
        Sad = findViewById(R.id.Sad);

        // Create a common click listener
        View.OnClickListener clickListener = v -> {
            // Get the button name
            String buttonName = getResources().getResourceEntryName(v.getId());
            // Log the click
            Timestamp.logClick(buttonName);
        };

        // Set click listener for all 7 buttons
        Angry.setOnClickListener(clickListener);
        Calm.setOnClickListener(clickListener);
        Confused.setOnClickListener(clickListener);
        Crying.setOnClickListener(clickListener);
        Happy.setOnClickListener(clickListener);
        InLove.setOnClickListener(clickListener);
        Sad.setOnClickListener(clickListener);
    }

}