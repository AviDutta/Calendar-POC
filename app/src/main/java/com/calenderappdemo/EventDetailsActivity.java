package com.calenderappdemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This activity returns all calender event details including
 * event name.
 */
public class EventDetailsActivity extends Activity {

    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        eventList = (ListView) findViewById(R.id.list);
        getEventDetailsList();
        getAccounts();
    }

    /**
     * @return All google accounts from yor device
     */
    public Account[] getAccounts() {
        AccountManager accountManager = AccountManager.get(this);
        final android.accounts.Account[] account = accountManager.getAccounts();
        Account[] accounts = null;
        if (account.length > 0) {
            accounts = new Account[account.length];
            for (int i = 0; i < accounts.length; i++) {
                accounts[i] = new Account(account[i].name, account[i].type);
                Log.d("Account", "All Google Account :- " + accounts[i]);
            }
        }
        return accounts;
    }

    public void getEventDetailsList() {
        String[] projection = {CalendarContract.Events._ID,
                CalendarContract.Events.TITLE, CalendarContract.Events.ACCOUNT_NAME,
                CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};
        // Get a Cursor over the Events Provider.
        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, null, null, null);
        // Initialize the result set.
        String[] result = new String[cursor.getCount()];
        // Iterate over the result Cursor.
        while (cursor.moveToNext()) {
            // Extract the details from cursor.
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.TITLE));
            String eventId = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events._ID));
            String eventAccountName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.ACCOUNT_NAME));
            long eventBeginTime = cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART));
            String eventStartDateTime = getDate(eventBeginTime);
            long eventEndTime = cursor.getLong(cursor.getColumnIndexOrThrow(CalendarContract.Events.DTEND));
            String eventEndDateTime = getDate(eventEndTime);
            result[cursor.getPosition()] = eventName + "  " + "(" + eventId + ")" + "  " + eventAccountName +
                    "      " + "(" + eventStartDateTime +
                    ")" + "  " + "(" + eventEndDateTime + ")";
        }
        // Define the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, result);
        // Assign adapter to ListView
        eventList.setAdapter(adapter);
        // Close the Cursor.
        cursor.close();
    }

    /**
     * @param milliSeconds
     * @return Convert Time and date of milliSeconds into SimpleDateFormat
     */
    public String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
