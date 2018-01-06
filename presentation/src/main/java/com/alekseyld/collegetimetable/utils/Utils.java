package com.alekseyld.collegetimetable.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Alekseyld on 08.10.2017.
 */

public class Utils {

    public static final String NAME_OF_CACHE_FILE = "cache_table.jpg";

    public static File getPathToCacheDir() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "CollegeTimetable";
        File pathFile = new File(path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();

            try {
                new File(pathFile, ".nomedia").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pathFile;
    }

    public static File getImageFile(Bitmap pictureBitmap) throws IOException, NullPointerException {
        File file = null;

        OutputStream fOut;
        file = new File(Utils.getPathToCacheDir(), Utils.NAME_OF_CACHE_FILE);

        if (file.exists())
            file.delete();
        file.createNewFile();

        fOut = new FileOutputStream(file);
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        fOut.flush();
        fOut.close();

        return file;
    }

    public static boolean isNetworkAvailable(Context mContext) {
        Context context = mContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
