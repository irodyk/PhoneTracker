package com.irodyk.spyagent.control.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

class SmsUtils {

    private static final int MILLIS_TIME_LENGTH = 14;

    @Nullable
    static String getContactName(Context context, String phoneNumber) {
        String contactName = null;

        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = null;

        try {
            cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor == null) {
                return null;
            }

            if(cursor.moveToFirst()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
        }finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contactName;
    }

    @Nullable
    static String convertTime(String millis){
        if (millis == null || !millis.matches("[0-9]+") || millis.length() != MILLIS_TIME_LENGTH)
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(millis));

        return formatter.format(calendar.getTime());
    }
}
