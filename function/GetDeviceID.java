package com.smartstudio.sajmovi.eu.function;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.smartstudio.sajmovi.eu.HomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by Sanja on 16.6.2015..
 */
public class GetDeviceID extends HomeActivity {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    //get phone's ID
    public synchronized static String GetDeviceID(final Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()){
                    writeInstallationFile(context,installation);
                }
                sID = readInstallationFile(installation);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();

        return new String(bytes);

    }

    private static void writeInstallationFile(final Context context,File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        StringBuffer buf=new StringBuffer();
        buf.append(Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID));
        //mobile phone spec's
        Log.d("***Device ID333333*****", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        Log.i("++++ManuFacturer :+++++", Build.MANUFACTURER);
        Log.i("''''''Board : '''''''''", Build.BOARD);
        Log.i("*****Display : *****", Build.DISPLAY);
        Log.i("*****Model : *****", Build.MODEL);
        Log.i("*****Brand : *****", Build.BRAND);

      /*String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;*/

        buf.append("-");
        buf.append(UUID.randomUUID().toString());
        out.write(buf.toString().getBytes());

        out.close();
    }// get phone ID
}
