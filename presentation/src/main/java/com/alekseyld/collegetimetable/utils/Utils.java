package com.alekseyld.collegetimetable.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.alekseyld.collegetimetable.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Alekseyld on 08.10.2017.
 */

public class Utils {

    public static final String NAME_OF_CACHE_FILE = "cache_table.jpg";
    public static final int TIMETABLE_NOTIFICATION_ID = 42;

    public static File getImageFile(Bitmap pictureBitmap, File cacheDir) throws IOException, NullPointerException {
        File file;

        OutputStream fOut;
        file = new File(cacheDir, Utils.NAME_OF_CACHE_FILE);

        if (file.exists())
            file.delete();
        file.createNewFile();

        fOut = new FileOutputStream(file);
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        fOut.flush();
        fOut.close();

        return file;
    }

    public static Uri getImageFileUri(Context context, Bitmap pictureBitmap, File cacheDir) throws IOException, NullPointerException {

        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, getImageFile(pictureBitmap, cacheDir));
    }
}
