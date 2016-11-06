package com.alekseyld.collegetimetable.utils;

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

        if(group.contains(" Т")){
            url = "energy/10_1_8.html";
        }
        if(group.contains(" Э")){
            url = "energy/10_1_7.html";
        }
        if(group.contains(" С")){
            url = "energy/10_1_10.html";
        }
        if(group.contains(" Б")){
            url = "energy/10_1_9.html";
        }
        if(group.contains("В")){
            url = "neft/10_1_4.html";
        }
        if(group.contains(" Л")){
            url = "energy/10_1_3.html";
        }
        if(group.contains(" Р")){
            url = "energy/10_1_4.html";
        }
        if(group.contains("АПП")) {
            url = "neft/10_1_1.html";
        }
        if(group.contains("БНГ-")){
            url = "neft/10_1_2.html";
        }
        if(group.contains(" ТО-")){
            url = "neft/10_1_3.html";
        }
        if(group.contains("ПНГ-")){
            url = "neft/10_1_5.html";
        }
        if(group.contains("ЭНН-")){
            url = "neft/10_1_6.html";
        }
        if(group.contains("ЭННУ")){
            url = "neft/10_1_6.html";
        }
        if(group.contains(" ТОВ")){
            url = "neft/10_1_7.html";
        }
        if(group.contains("ИС")) {
            url = "energy/10_1_1.html";
        }
        if(group.contains("ГС-")){
            url = "energy/10_1_2.html";
        }
        if(group.contains("ГСУ")){
            url = "energy/10_1_2.html";
        }
        if(group.contains(" РУ")){
            url = "energy/10_1_4.html";
        }
        if(group.contains(" ПГ")){
            url = "energy/10_1_5.html";
        }
        if(group.contains("ТС-")){
            url = "energy/10_1_6.html";
        }

        return "http://uecoll.ru/wp-content/uploads/time/" + url;
    }

    public static TableWrapper parseDocument(Document document, String group){

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
}
    /*
     *  Здесь был Константин(нет).
     *  Он думал над этим классом 5 часов без перерыва(нет)
     *  И в итоге получил груду костылей(ага)
     */
