package com.alekseyld.collegetimetable.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.alekseyld.collegetimetable.BuildConfig;
import com.alekseyld.collegetimetable.job.RecursiveJob;
import com.alekseyld.collegetimetable.job.TimetableJob;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import androidx.core.content.FileProvider;

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

    public static File getImageFile(Bitmap pictureBitmap, File cacheDir) throws IOException, NullPointerException {
        File file = null;

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

    public static void toggleRecursiveJob(boolean notifOn) {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(RecursiveJob.TAG);

        if (notifOn && jobRequests.size() == 0) {
            new JobRequest.Builder(RecursiveJob.TAG)
                    .setUpdateCurrent(true)
                    .setPeriodic(45 * 60 * 1000)
                    .build()
                    .schedule();
        } else {
            if (jobRequests.size() != 0) {
                for (JobRequest jobRequest: jobRequests) {
                    jobRequest.cancelAndEdit();
                }
            }
        }
    }

    public static long getTimeTableJobLastRun() {
        return getTimeTableJob().getLastRun();
    }

}
