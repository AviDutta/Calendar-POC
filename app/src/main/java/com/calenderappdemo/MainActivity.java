package com.calenderappdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * This activity show event and attendees buttons
 * and onClick of that buttons it navigate to particular list screen
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button eventButton = (Button) findViewById(R.id.eventBtn);
        Button attendeesButton = (Button) findViewById(R.id.attendeesBtn);

        /**
         * onClick of eventButton it Navigate to EventDetailsActivity screen
         */
        eventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent eventIntent = new Intent(MainActivity.this, EventDetailsActivity.class);
                startActivity(eventIntent);
            }

        });
        /**
         * onClick of attendeesButton it Nevigate to AttendeesDetailsActivity screen
         */
        attendeesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent attendeesIntent = new Intent(MainActivity.this, AttendeesDetailsActivity.class);
                startActivity(attendeesIntent);
            }

        });
    }
}