package com.irodyk.spyagent.observer.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.irodyk.spyagent.R;

public class AdminReceiver extends DeviceAdminReceiver {


    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.i("AdminReceiver", " Device Admin: enabled");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        showToast(context, String.format("%s Device Admin: disabled",context.getString(R.string.app_name)));
        return String.format("Warning: %s Device Admin is going to be disabled.", context.getString(R.string.app_name));
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Process.killProcess(Process.myPid());
        // This is only exit from application, value different then 0, means
        // that something goes wrong.
        System.exit(0);
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, String.format("%s Device Admin: password changed", context.getString(R.string.app_name)));
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, String.format("%s Device Admin: password failed", context.getString(R.string.app_name)));
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, String.format("%s Device Admin: password succeeded", context.getString(R.string.app_name)));
    }
}
