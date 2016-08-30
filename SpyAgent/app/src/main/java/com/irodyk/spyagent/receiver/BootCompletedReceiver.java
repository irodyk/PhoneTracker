package com.irodyk.spyagent.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.irodyk.spyagent.SpyService;

/**
 * Created by i.rodyk on 8/30/16.
 */
public class BootCompletedReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("LOGGGGGG", "RECCEIVED");
        context.startService(new Intent(context, SpyService.class));
    }
}
