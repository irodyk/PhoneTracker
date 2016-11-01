package com.irodyk.spyagent.control.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

class SmsUtils {

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
    static String convertTime(long millis){

        String date = "";
        String time = "";

        String.format(Locale.getDefault(), "%d:%d:%d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );



        return null;
    }
}
