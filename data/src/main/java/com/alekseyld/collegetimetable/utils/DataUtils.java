package com.alekseyld.collegetimetable.utils;

import android.util.Log;

import com.alekseyld.collegetimetable.TableWrapper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class DataUtils {

    public static String getGroupUrl(String group){

        String url = "";


        if(group.contains("Т")){
            url = "energy/10_1_8.html";
        }
        if(group.contains("Б")){
            url = "energy/10_1_9.html";
        }
        if(group.contains("АПП-")) {
            url = "neft/10_1_1.html";
        }
        if(group.contains("БНГ-")){
            url = "neft/10_1_2.html";
        }
        if(group.contains("ТО-")){
            url = "neft/10_1_3.html";
        }
        if(group.contains("B-")){
            url = "neft/10_1_4.html";
        }
        if(group.contains("ПНГ-")){
            url = "neft/10_1_5.html";
        }
        if(group.contains("ЭНН-")){
            url = "neft/10_1_6.html";
        }
        if(group.contains("ТОВ-")){
            url = "neft/10_1_7.html";
        }
        if(group.contains("ИС")) {
            url = "energy/10_1_1.html";
        }
        if(group.contains("ГС-")){
            url = "energy/10_1_2.html";
        }
        if(group.contains("Л-")){
            url = "energy/10_1_3.html";
        }
        if(group.contains("Р-")){
            url = "energy/10_1_4.html";
        }
        if(group.contains("ПГ-")){
            url = "energy/10_1_5.html";
        }
        if(group.contains("ТС-")){
            url = "energy/10_1_6.html";
        }
        if(group.contains("Э-")){
            url = "energy/10_1_7.html";
        }
        if(group.contains(" С-")){
            url = "energy/10_1_10.html";
        }

        return "http://uecoll.ru/wp-content/uploads/time/" + url;
    }

    public static TableWrapper parseDocument(Document document, String group){

        if (document == null){
            return new TableWrapper();
        }

        Elements table =document.select("tr").select("td");

        Pattern numberPattern = Pattern.compile("^[0-9]");

        TableWrapper timeTable = new TableWrapper();

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> time = new HashMap<>();
        HashMap<TableWrapper.Lesson, String> lessons = new HashMap<>();

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
//            Log.d("all", element.text());

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
                switch (day) {
                    case 0:
                        time.put(TableWrapper.Day.Mon, lessons);
                        break;
                    case 1:
                        time.put(TableWrapper.Day.Tue, lessons);
                        break;
                    case 2:
                        time.put(TableWrapper.Day.Wed, lessons);
                        break;
                    case 3:
                        time.put(TableWrapper.Day.Thu, lessons);
                        break;
                    case 4:
                        time.put(TableWrapper.Day.Friday, lessons);
                        break;
                    case 5:
                        time.put(TableWrapper.Day.Saturday, lessons);
                        break;
                    case 6:
                        time.put(TableWrapper.Day.Mon2, lessons);
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
                            break;
                        case 1:
                            time.put(TableWrapper.Day.Tue, lessons);
                            break;
                        case 2:
                            time.put(TableWrapper.Day.Wed, lessons);
                            break;
                        case 3:
                            time.put(TableWrapper.Day.Thu, lessons);
                            break;
                        case 4:
                            time.put(TableWrapper.Day.Friday, lessons);
                            break;
                        case 5:
                            time.put(TableWrapper.Day.Saturday, lessons);
                            break;
                        case 6:
                            time.put(TableWrapper.Day.Mon2, lessons);
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

//                Log.d("timetable", "size "+time.size());

                lessonSpace = 0;
                toLesson = true;
                spaceToLessonBlock = false;
            }
        }

        timeTable.setTimeTable(time);

        return timeTable;
    }
}
