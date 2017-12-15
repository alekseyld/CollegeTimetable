package com.alekseyld.collegetimetable.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alekseyld on 08.10.2017.
 */

public class Utils {

    public static final String NAME_OF_CACHE_FILE = "cache_table.jpg";

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

    @SuppressLint("StaticFieldLeak")
    public static void downloadAudioByUrl(final String urlString, final String audioId, final String audioTitle, final String audioArtist) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... urls) {
                int count;
                try {
                    URL url = new URL(urls[0]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    // this will be useful so that you can show a tipical 0-100% progress bar
                    int lenghtOfFile = conexion.getContentLength();

                    // downlod the file
                    InputStream input = new BufferedInputStream(url.openStream());

                    File dir = new File(getPathToCacheDir()+"/music_cache/");
                    if (!dir.exists())
                        dir.mkdirs();

                    File file = new File(dir, audioArtist + " - " + audioTitle + "_" + audioId + ".mp3");
                    file.createNewFile();

                    OutputStream output = new FileOutputStream(file);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        //publishProgress((int)(total*100/lenghtOfFile));
                        Log.i("audio_download", audioTitle + " - " + (int)(total*100/lenghtOfFile) + "%");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.doInBackground(urlString);
    }

    public static Map<String, String> getCacheMusicList(){
        File dir = new File(getPathToCacheDir()+"/music_cache/");
        HashMap<String, String> cache = new HashMap<>();
        if (!dir.exists())
            return cache;

        String[] audios = dir.list();

        for (String audio: audios){
            String[] splits = audio.split("#");
            cache.put(splits[0], splits[1]);
        }

        return cache;
    }
}
