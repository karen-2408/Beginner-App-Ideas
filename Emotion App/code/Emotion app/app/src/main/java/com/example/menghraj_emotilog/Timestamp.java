package com.example.menghraj_emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Timestamp extends AppCompatActivity {
    private TextView textView;
    private static ArrayList<ClickRecord> clickRecords = new ArrayList<>();
    private static String todayDate = getTodayDateStatic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timestamp);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to go to SecondActivity
                Intent intent = new Intent(Timestamp.this, Second.class);
                startActivity(intent); // Start the SecondActivity
            }
        });

        //Find TextView
        textView = findViewById(R.id.clickTextView);

        // Display click data
        displayClickData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayClickData();
    }

    //In this method, we log all button clicks and creates a new record on each line
    public static void logClick(String buttonName) {
        long currentTime = System.currentTimeMillis();
        String date = getTodayDateStatic();
        String time = formatTimeStatic(currentTime);

        // Get today's click count for this button
        int todayCount = getTodayClickCount(buttonName);

        // Get frequency for this button
        double frequency = calculateFrequency(buttonName);

        // Create new click record
        ClickRecord record = new ClickRecord(buttonName, date, time, todayCount + 1, frequency);
        clickRecords.add(record);
    }

    //Get today's click count for a specific button
    private static int getTodayClickCount(String buttonName) {
        int count = 0;
        String today = getTodayDateStatic();

        for (ClickRecord record : clickRecords) {
            if (record.buttonName.equals(buttonName) && record.date.equals(today)) {
                count++;
            }
        }

        return count;
    }

    //We calculate frequency for each button
    private static double calculateFrequency(String buttonName) {
        ArrayList<String> uniqueDays = new ArrayList<>();
        int totalClicks = 0;

        for (ClickRecord record : clickRecords) {
            if (record.buttonName.equals(buttonName)) {
                totalClicks++;
                if (!uniqueDays.contains(record.date)) {
                    uniqueDays.add(record.date);
                }
            }
        }

        String today = getTodayDateStatic();
        if (!uniqueDays.contains(today) && getTodayClickCount(buttonName) > 0) {
            uniqueDays.add(today);
        }

        int totalDays = uniqueDays.size();
        return totalDays > 0 ? (double) (totalClicks + 1) / totalDays : 1.0;
    }

    //To display all the records whether it is clicked yet or not
    private void displayClickData() {
        StringBuilder output = new StringBuilder();

        // Add column headers
        output.append(String.format("%-18s %-12s %-10s %-10s\n",
                "Emotion", "Time", "Clicks", "Frequency"));
        output.append("\n");

        if (clickRecords.isEmpty()) {
            output.append("\nNo click data recorded yet.\n");
        } else {
            for (ClickRecord record : clickRecords) {
                output.append(String.format("%-18s %-12s %-10d %-10.2f\n", record.buttonName, record.time, record.clickCount, record.frequency));
            }
        }

        output.append("\n");
        output.append("Date: ").append(todayDate).append("\n");
        output.append("Total Records: ").append(clickRecords.size()).append("\n");
        output.append("\n");

        textView.setText(output.toString());
    }

    //We get timestamps for when the button is clicked
    private static String formatTimeStatic(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    //We get today's date formatted
    private static String getTodayDateStatic() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    //Class to store each individual click record
    private static class ClickRecord {
        String buttonName;
        String date;
        String time;
        int clickCount;
        double frequency;

        ClickRecord(String buttonName, String date, String time, int clickCount, double frequency) {
            this.buttonName = buttonName;
            this.date = date;
            this.time = time;
            this.clickCount = clickCount;
            this.frequency = frequency;
        }
    }
}