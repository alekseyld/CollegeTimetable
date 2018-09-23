package com.alekseyld.collegetimetable.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.alekseyld.collegetimetable.job.TimetableJob;
import com.evernote.android.job.JobRequest;

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
    public static final int SERVICE_TIMER = 5000;//30 * 60 * 1000

    public static File getPathToCacheDir(){
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
        file = new File(getPathToCacheDir(), Utils.NAME_OF_CACHE_FILE);

        if (file.exists())
            file.delete();
        file.createNewFile();

        fOut = new FileOutputStream(file);
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
        fOut.flush();
        fOut.close();

        return file;
    }

    public static JobRequest getTimeTableJob() {
        return getTimeTableJob(20_000l);
    }

    public static JobRequest getTimeTableJob(long timing) {
        return new JobRequest.Builder(TimetableJob.TAG)
                .setUpdateCurrent(true)
                .setExecutionWindow(timing, timing + 60_000L)
                .build();
    }


    public static void initTimeTableJob() {
        getTimeTableJob().schedule();
    }

    public static long getTimeTableJobLastRun() {
        return getTimeTableJob().getLastRun();
    }

}
