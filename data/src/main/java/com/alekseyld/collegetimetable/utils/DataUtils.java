package com.alekseyld.collegetimetable.utils;

import com.alekseyld.collegetimetable.TableWrapper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class DataUtils {

    public static Pattern groupPattern = Pattern.compile("[0-9]\\s[А-Я]{1,}[-][0-9]");

    public static String getGroupUrl(String group){

        if (group == null || !groupPattern.matcher(group).matches())
            return "";

        String url = "";

        String abbr = group.split(" ")[1].split("-")[0];

        switch (abbr) {
            case "Т":
                url = "energy/10_1_8.html";
                break;
            case "Э":
                url = "energy/10_1_7.html";
                break;
            case "С":
                url = "energy/10_1_10.html";
                break;
            case "Б":
                url = "energy/10_1_9.html";
                break;
            case "В":
                url = "neft/10_1_4.html";
                break;
            case "Л":
                url = "energy/10_1_3.html";
                break;
            case "Р":
                url = "energy/10_1_4.html";
                break;
            case "АПП":
                url = "neft/10_1_11.html";
                break;
            case "БНГ":
                url = "neft/10_1_2.html";
                break;
            case "ТО":
                url = "neft/10_1_3.html";
                break;
            case "ПНГ":
                url = "neft/10_1_5.html";
                break;
            case "ЭНН":
                url = "neft/10_1_6.html";
                break;
            case "ЭННУ":
                url = "neft/10_1_6.html";
                break;
            case "ТОВ":
                url = "neft/10_1_7.html";
                break;
            case "ИС":
                url = "energy/10_1_1.html";
                break;
            case "ГС":
                url = "energy/10_1_2.html";
                break;
            case "ГСУ":
                url = "energy/10_1_2.html";
                break;
            case "РУ":
                url = "energy/10_1_4.html";
                break;
            case "ПГ":
                url = "energy/10_1_5.html";
                break;
            case "ТС":
                url = "energy/10_1_6.html";
                break;
            case "ТАК":
                url = "energy/10_1_8.html";
                break;
            default:
                url = "";
                break;
        }

        return url.equals("") ? url : "http://uecoll.ru/wp-content/uploads/time/" + url;
    }

    public static TableWrapper parseDocument(Document document, String group){

        if (document == null || group == null || !groupPattern.matcher(group).matches()){
            return new TableWrapper();
        }

        Elements table = document.select("tr").select("td");

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

        boolean firstDoubleLesson = true;

        //Пропуск группы
        int iSpace = 0;
        int lessonSpace = -1;

        //Счетчик дней
        int day = -1;
        //Счетчик пар
        int lesson = 0;

        int i = 0;

        for (int iterator = 0; iterator < table.size(); iterator++) {

            if(dayPattern.matcher(table.get(iterator).text()).find()){
                dayString[0] = dayString [1];
                dayString[1] = table.get(iterator).text();
            }

            //Ищем начало групп
            if(table.get(iterator).text().equals("День/Пара") && first){
                space = true;
                first = false;
            }
            //Считаем положение группы в таблице
            if (space) {
                iSpace++;
            }

            //Если элемент совпадает с название группы, то останавливаем счетчик
            if (table.get(iterator).text().equals(group)) {
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

            if(toLesson && numberPattern.matcher(table.get(iterator).text()).matches()){
                toLesson = false;
                spaceToLessonBlock = true;
                lessonSpace = 0;

                lesson = Integer.parseInt(table.get(iterator).text());

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

            if(lessonSpace == iSpace) {
                String text = table.get(iterator).text();

                if (table.get(iterator).attr("colspan").equals("")) {
                    text = table.get(iterator).text() + "\n/\n" +
                            table.get(iterator + 1).text();
                }

                switch (lesson) {
                    case 0:
                        lessons.put(TableWrapper.Lesson.lesson0, text);
                        break;
                    case 1:
                        lessons.put(TableWrapper.Lesson.lesson1, text);
                        break;
                    case 2:
                        lessons.put(TableWrapper.Lesson.lesson2, text);
                        break;
                    case 3:
                        lessons.put(TableWrapper.Lesson.lesson3, text);
                        break;
                    case 4:
                        lessons.put(TableWrapper.Lesson.lesson4, text);
                        break;
                    case 5:
                        lessons.put(TableWrapper.Lesson.lesson5, text);
                        break;
                    case 6:
                        lessons.put(TableWrapper.Lesson.lesson6, text);
                        break;
                }

                lessonSpace = 0;
                toLesson = true;
                spaceToLessonBlock = false;
            }else {
                if(table.get(iterator).tagName().equals("td")     &&
                        table.get(iterator).attr("colspan").equals("") &&
                        table.get(iterator).attr("rowspan").equals("") &&
                        !numberPattern.matcher(table.get(iterator).text()).matches() &&
                        firstDoubleLesson){
                    lessonSpace--;
                    firstDoubleLesson = false;
                }else {
                    firstDoubleLesson = true;
                }
            }
        }

        timeTable.setTimeTable(time);
        timeTable.setDays(days);

        return timeTable;
    }
}