package com.calenderappdemo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This activity returns all calender Attendees details including
 * attendees name,id,email etc.
 */
public class AttendeesDetailsActivity extends Activity {
    private ListView gmailList, globantList;
    private String attendeeEmail;
    private ArrayList<String> result1, result2;
    private String attendeeName;
    private String eventId;
    private Cursor cursor;
    private ArrayAdapter<String> adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees);
        gmailList = (ListView) findViewById(R.id.list1);
        globantList = (ListView) findViewById(R.id.list2);
        getAttendeesDetails();
    }

    public void getAttendeesDetails() {
        final String[] attendeeProjection = new String[]{
                CalendarContract.Attendees._ID,
                CalendarContract.Attendees.EVENT_ID,
                CalendarContract.Attendees.ATTENDEE_NAME,
                CalendarContract.Attendees.ATTENDEE_EMAIL,
        };
        String query = "(" + CalendarContract.Attendees.EVENT_ID + " = ?)";
        String[] args = new String[]{query};

        cursor = getContentResolver().query(CalendarContract.Attendees.CONTENT_URI,
                attendeeProjection, null, null, null);
        // Initialize the result set.
        result1 = new ArrayList<>();
        result2 = new ArrayList<>();

        // Iterate over the result Cursor.
        while (cursor.moveToNext()) {
            // Extract the details from cursor.
            eventId = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events._ID));
            //String attendeeId = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Attendees.ORGANIZER));
            attendeeName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Attendees.ATTENDEE_NAME));
            attendeeEmail = cursor.getString(cursor.getColumnIndex(CalendarContract.Attendees.ATTENDEE_EMAIL));

            if (attendeeEmail.contains("gmail.com")) {
                result1.add(attendeeName + "  " + "(" + eventId + ")" + "  " + attendeeEmail);

            } else if (attendeeEmail.contains("globant.com")) {
                result2.add(attendeeName + "  " + "(" + eventId + ")" + "  " + attendeeEmail);
            }

        }
        // Close the Cursor.
        cursor.close();

        // Assign adapter to ListView
        adapter1 = new ArrayAdapter<String>(AttendeesDetailsActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, result1);
        gmailList.setAdapter(adapter1);

        // Assign adapter to ListView
        adapter2 = new ArrayAdapter<String>(AttendeesDetailsActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, result2);
        globantList.setAdapter(adapter2);

    }


}
