package com.py.third;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.security.MessageDigest;
import java.util.UUID;

public class TestUniqueIdActivity extends AppCompatActivity {
    @SuppressLint("LogNotTimber")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String deviceId = "";
        String macAddress = "";
        String androidId = "";
        String serialNum = "";
        String simSerialNum = "";

        android.telephony.TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (checkPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            deviceId = tm.getDeviceId();
//            deviceId = tm.getImei();
//            deviceId = tm.getMeid();
            simSerialNum = tm.getSimSerialNumber();

        }

        android.net.wifi.WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        macAddress = wifi.getConnectionInfo().getMacAddress();
        androidId = android.provider.Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        serialNum = Build.SERIAL;

        String deviceManufacturer = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        String deviceBrand = Build.BRAND;
        String device = Build.DEVICE;

        //==============
        Log.e("Identifier_Device_ID", validate(deviceId));
        Log.e("Identifier_Mac_Address", validate(macAddress));
        Log.e("Identifier_Android_ID", validate(androidId));
        Log.e("Identifier_Serial_Num", validate(serialNum));
        Log.e("Identifier_Sim_SN", validate(simSerialNum));

        Log.e("Identifier_Manufacturer", validate(deviceManufacturer));
        Log.e("Identifier_Model", validate(deviceModel));
        Log.e("Identifier_Brand", validate(deviceBrand));
        Log.e("Identifier_Device", validate(device));

        UUID deviceUserLifetimeUUID = new UUID(validate(androidId).hashCode(), ((long) validate(deviceId).hashCode() << 32) | validate(simSerialNum).hashCode());
        String deviceUserLifetimeId = deviceUserLifetimeUUID.toString();

        String deviceHardwareId = md5(validate(serialNum) + validate(deviceId) + validate(macAddress));
        ;

        Log.e("deviceUserLifetimeId", deviceUserLifetimeId);
        Log.e("deviceHardwareId", deviceHardwareId);
    }

    // MD5加密，32位小写
    public static String md5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        md5.update(str.getBytes());
        byte[] md5Bytes = md5.digest();
        StringBuilder hexValue = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    //检查权限，READ_PHONE_STATE在API>=23需要用户手动赋予权限
    @SuppressLint("ObsoleteSdkInt")
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    //判空
    private String validate(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

}





























