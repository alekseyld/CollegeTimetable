package com.alekseyld.collegetimetable.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.GROUP_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.TIME_KEY;
import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.URL_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.DAYS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.TIMETABLE_KEY;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class UpdateTimetableService extends IntentService {
    private final String LOG_TAG = "ServiceLog";
    public static boolean isRunning = false;

    private SharedPreferences mPref;
    private Gson mGson;
    private Type mType;
    private Type mTypeDay;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * UpdateTimetableService Used to name the worker thread, important only for debugging.
     */
    public UpdateTimetableService() {
        super("DataService");

        mGson = new Gson();
        mType = new TypeToken<HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>>>(){}.getType();
        mTypeDay = new TypeToken<HashMap<TableWrapper.Day, String>>(){}.getType();
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
        isRunning = true;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
        isRunning = false;
    }

    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent");

        //FIXME get data from usecase
        mPref = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        int time = mPref.getInt(TIME_KEY, 5);
        NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = getNotif("Изменение в расписании");
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if(isOnline()){
            Document document;
            try {
                document = Jsoup.connect(mPref.getString(URL_KEY, "")).get();
                TableWrapper tableWrapper = parseDocument(document, mPref.getString(GROUP_KEY, ""));
                if(!tableWrapper.equals(getTimeTable())){
                    putTimeTable(tableWrapper);
                    n.notify("com.alekseyld.collegetimetable", 5, notification);
                    v.vibrate(300);
                }
            } catch (Exception e) {
                stopSelf();
            }
        }else {
            stopSelf();
        }
    }

    private Notification getNotif(String s) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.android_logo)
                        .setContentTitle(s)
                        .setContentText("Изменение в расписании");
//                        .setContentText("name = "+intent.getStringExtra("name")+"; "+
//                                        "number = "+intent.getIntExtra("number", 0));
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());
        return mBuilder.build();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void putTimeTable(TableWrapper tableWrapper) {
        String json = mGson.toJson(tableWrapper.getmTimeTable());
        editDays(tableWrapper.getDays());
        String json2 = mGson.toJson(tableWrapper.getDays());
        SharedPreferences.Editor ed = mPref.edit();
        ed.putString(TIMETABLE_KEY, json);
        ed.putString(DAYS_KEY, json2);
        ed.apply();
    }
    private void editDays(HashMap<TableWrapper.Day, String> days) {
        for(TableWrapper.Day d: days.keySet()){
            days.put(d, firstUpperCase(days.get(d).toLowerCase()));
        }
    }

    private String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";//или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private static TableWrapper parseDocument(Document document, String group){

        if (document == null){
            return new TableWrapper();
        }

        Elements table =document.select("tr").select("td");

        Pattern numberPattern = Pattern.compile("^[0-9]");
        Pattern dayPattern = Pattern.compile("[А-Я]\\s[А-Я]\\s\\b");

        TableWrapper timeTable = new TableWrapper();

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> time = new HashMap<>();
        HashMap<TableWrapper.Lesson, String> lessons = new HashMap<>();
        HashMap<TableWrapper.Day, String> days = new HashMap<>();
        String[] dayString = new String[]{"", ""};

        //Искать номер пары
        boolean space = false;

        //Первая итерация
        boolean first = true;

        //Пропуск до названия пары
        boolean spaceToLessonBlock = false;

        //Переход к номеру пары
        boolean toLesson =false;

        //Пропуск группы
        int iSpace = 0;
        int lessonSpace = -1;

        //Счетчик дней
        int day = -1;
        //Счетчик пар
        int lesson = 0;

        int i = 0;

        for (Element element: table) {

            if(dayPattern.matcher(element.text()).find()){
                dayString[0] = dayString [1];
                dayString[1] = element.text();
            }

            //Ищем начало групп
            if(element.text().equals("День/Пара") && first){
                space = true;
                first = false;
            }
            //Считаем положение группы в таблице
            if (space) {
                iSpace++;
            }

            //Если элемент совпадает с название группы, то останавливаем счетчик
            if (element.text().equals(group)) {
                space = false;
                toLesson = true;
            }

            i++;
            if(i == table.size()-1){
                switch (day){
                    case 0:
                        time.put(TableWrapper.Day.Mon, lessons);
                        days.put(TableWrapper.Day.Mon, dayString[0].equals("") ? dayString[1] : dayString[0]);
                        break;
                    case 1:
                        time.put(TableWrapper.Day.Tue, lessons);
                        days.put(TableWrapper.Day.Tue, dayString[0]);
                        break;
                    case 2:
                        time.put(TableWrapper.Day.Wed, lessons);
                        days.put(TableWrapper.Day.Wed, dayString[0]);
                        break;
                    case 3:
                        time.put(TableWrapper.Day.Thu, lessons);
                        days.put(TableWrapper.Day.Thu, dayString[0]);
                        break;
                    case 4:
                        time.put(TableWrapper.Day.Friday, lessons);
                        days.put(TableWrapper.Day.Friday, dayString[0]);
                        break;
                    case 5:
                        time.put(TableWrapper.Day.Saturday, lessons);
                        days.put(TableWrapper.Day.Saturday, dayString[0]);
                        break;
                    case 6:
                        time.put(TableWrapper.Day.Mon2, lessons);
                        days.put(TableWrapper.Day.Mon2, dayString[1]);
                        break;
                }
            }

            if(toLesson && numberPattern.matcher(element.text()).matches()){
//                Log.d("toLesson", element.text());

                toLesson = false;
                spaceToLessonBlock = true;
                lessonSpace = 0;

                lesson = Integer.parseInt(element.text());

                if(lesson == 0){
                    switch (day){
                        case 0:
                            time.put(TableWrapper.Day.Mon, lessons);
                            days.put(TableWrapper.Day.Mon, dayString[0]);
                            break;
                        case 1:
                            time.put(TableWrapper.Day.Tue, lessons);
                            days.put(TableWrapper.Day.Tue, dayString[0]);
                            break;
                        case 2:
                            time.put(TableWrapper.Day.Wed, lessons);
                            days.put(TableWrapper.Day.Wed, dayString[0]);
                            break;
                        case 3:
                            time.put(TableWrapper.Day.Thu, lessons);
                            days.put(TableWrapper.Day.Thu, dayString[0]);
                            break;
                        case 4:
                            time.put(TableWrapper.Day.Friday, lessons);
                            days.put(TableWrapper.Day.Friday, dayString[0]);
                            break;
                        case 5:
                            time.put(TableWrapper.Day.Saturday, lessons);
                            days.put(TableWrapper.Day.Saturday, dayString[0]);
                            break;
                        case 6:
                            time.put(TableWrapper.Day.Mon2, lessons);
                            days.put(TableWrapper.Day.Mon2, dayString[1]);
                            break;
                    }
                    lessons = new HashMap<>();
                    day++;
                }
            }
            if(spaceToLessonBlock){
                lessonSpace++;
            }
            if(lessonSpace == iSpace){
//                Log.d("toLesson", element.text());

                switch (lesson){
                    case 0:
                        lessons.put(TableWrapper.Lesson.lesson0, element.text());
                        break;
                    case 1:
                        lessons.put(TableWrapper.Lesson.lesson1, element.text());
                        break;
                    case 2:
                        lessons.put(TableWrapper.Lesson.lesson2, element.text());
                        break;
                    case 3:
                        lessons.put(TableWrapper.Lesson.lesson3, element.text());
                        break;
                    case 4:
                        lessons.put(TableWrapper.Lesson.lesson4, element.text());
                        break;
                    case 5:
                        lessons.put(TableWrapper.Lesson.lesson5, element.text());
                        break;
                    case 6:
                        lessons.put(TableWrapper.Lesson.lesson6, element.text());
                        break;
                }

//                Log.d(TIMETABLE_KEY, "size "+time.size());

                lessonSpace = 0;
                toLesson = true;
                spaceToLessonBlock = false;
            }
        }

        timeTable.setTimeTable(time);
        timeTable.setDays(days);

        return timeTable;
    }

    private TableWrapper getTimeTable() {
        String s = mPref.getString(TIMETABLE_KEY, "");
        String d = mPref.getString(DAYS_KEY, "");
        TableWrapper tableWrapper = new TableWrapper();
        tableWrapper.setTimeTable((HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>>) mGson.fromJson(s, mType));
        tableWrapper.setDays((HashMap<TableWrapper.Day, String>) mGson.fromJson(d, mTypeDay));
        return tableWrapper;
    }
}
