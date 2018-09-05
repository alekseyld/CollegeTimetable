package com.alekseyld.collegetimetable.utils;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.TimeTable;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class DataUtils {

    public static Pattern groupPattern = Pattern.compile("[0-9]\\s[А-Я]{1,}[-][0-9]");
    public static Pattern groupPatternWithoutNum = Pattern.compile("[0-9]\\s[А-Я]{1,}([-][0-9]){0,}");
    public static Pattern fioPattern = Pattern.compile("([А-ЯЁа-яё]{1,}[\\s]([А-ЯЁа-яё]{1}[.]){2})");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static String getGroupUrl(String group) {
        return getGroupUrl("", group);
    }

    public static String getGroupUrl(String root, String group) {

        if (group == null || !groupPatternWithoutNum.matcher(group).matches())
            return "";

        String url = "";

        Set<String> neftGroups = new HashSet<String>() {{
            add("АПП");
            add("БНГ");
            add("В");
            add("ПНГ");
            add("ТАК");
            add("ТО");
            add("ТОВ");
            add("ЭНН");
            add("ЭННУ");
        }};

        String abbr = "";

        if (groupPatternWithoutNum.matcher(group).matches()) {
            abbr = group.split(" ")[1].split("-")[0];
        } else {
            abbr = group.split(" ")[1];
        }

        if (group.charAt(0) == '1' && neftGroups.contains(abbr)) {
            url = "neft/10_1_8.html";
        } else {
            url = switchAbbr(abbr);
        }

        if (root.equals(""))
            root = "http://uecoll.ru/wp-content/uploads/time/";

        return url.equals("") ? url : root + url;
    }

    private static String switchAbbr(String abbr) {
        switch (abbr) {
            case "Т":
                return "energy/10_1_8.html";
            case "Э":
                return "energy/10_1_7.html";
            case "С":
                return "energy/10_1_10.html";
            case "Б":
                return "energy/10_1_9.html";
            case "В":
                return "neft/10_1_4.html";
            case "Л":
                return "energy/10_1_3.html";
            case "Р":
                return "energy/10_1_4.html";
            case "АПП":
                return "neft/10_1_1.html";
            case "БНГ":
                return "neft/10_1_2.html";
            case "ТО":
                return "neft/10_1_3.html";
            case "ПНГ":
                return "neft/10_1_5.html";
            case "ЭНН":
                return "neft/10_1_6.html";
            case "ЭННУ":
                return "neft/10_1_6.html";
            case "ТОВ":
                return "neft/10_1_7.html";
            case "ИС":
                return "energy/10_1_1.html";
            case "ГС":
                return "energy/10_1_2.html";
            case "ГСУ":
                return "energy/10_1_2.html";
            case "РУ":
                return "energy/10_1_4.html";
            case "ПГ":
                return "energy/10_1_5.html";
            case "ТС":
                return "energy/10_1_6.html";
            case "ТАК":
                return "energy/10_1_9.html";
            default:
                return "";
        }
    }

    public static TimeTable parseDocument(Document document, String group) {

        if (document == null || group == null || !groupPatternWithoutNum.matcher(group).matches()) {
            return new TimeTable();
        }

        Elements table = document.select("tr").select("td");

        Pattern numberPattern = Pattern.compile("^[0-9]");
        Pattern dayPattern = Pattern.compile("[А-Я]\\s[А-Я]\\s\\b");

        TimeTable timeTable = new TimeTable()
                .setLastRefresh(dateFormat.format(new Date()))
                .setGroup(group);

        List<Lesson> lessons = new ArrayList<>();
//        HashMap<TimeTable.Day, String> days = new HashMap<>();
        String[] dayString = new String[]{"", ""};

        //Искать номер пары
        boolean space = false;

        //Первая итерация
        boolean first = true;

        //Пропуск до названия пары
        boolean spaceToLessonBlock = false;

        //Переход к номеру пары
        boolean toLesson = false;

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

            if (dayPattern.matcher(table.get(iterator).text()).find()) {
                dayString[0] = dayString[1];
                dayString[1] = table.get(iterator).text();
            }

            //Ищем начало групп
            if (table.get(iterator).text().equals("День/Пара") && first) {
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
            if (i == table.size() - 1 && day != -1) {
                timeTable.addDay(
                        new Day()
                                .setId(day)
                                .setDate(day == 0 && !dayString[0].equals("") || (day != 0 && !dayString[0].equals(timeTable.getDayList().get(day - 1).getDate()))  ? dayString[0] : dayString[1])
                                .setDayLessons(lessons)
                );

            }

            if (toLesson && numberPattern.matcher(table.get(iterator).text()).matches()) {
                toLesson = false;
                spaceToLessonBlock = true;
                lessonSpace = 0;

                lesson = Integer.parseInt(table.get(iterator).text());

                if (lesson == 0 && day != -1) {
                    timeTable.addDay(
                            new Day()
                                    .setId(day)
                                    .setDate(day == 0 && !dayString[0].equals("") || !dayString[0].equals(timeTable.getDayList().get(day - 1).getDate())  ? dayString[0] : dayString[1])
                                    .setDayLessons(lessons)
                    );

                    lessons = new ArrayList<>();
                    day++;
                }
                if (day == -1)
                    day++;
            }

            if (spaceToLessonBlock) {
                lessonSpace++;
            }

            if (lessonSpace == iSpace) {
                String text = table.get(iterator).text();

                boolean second = table.get(iterator).attr("colspan").equals("");
                String secondName = second ? table.get(iterator + 1).text() : null;

                boolean isChange = table.get(iterator).children().attr("color").equals("blue");

                String teacherName = getTeacherName(table.get(iterator).children(),
                        second ? table.get(iterator + 1).children() : null,
                        isChange);

                lessons.add(
                        new Lesson()
                                .setNumber(lesson)
                                .setName(text)
                                .setSecondName(secondName)
                                //todo Второй преподаватель
                                .setTeacher(teacherName)
                                .setChange(isChange)
                );

                lessonSpace = 0;
                toLesson = true;
                spaceToLessonBlock = false;
            } else {
                if (table.get(iterator).tagName().equals("td") &&
                        table.get(iterator).attr("colspan").equals("") &&
                        table.get(iterator).attr("rowspan").equals("") &&
                        !numberPattern.matcher(table.get(iterator).text()).matches() &&
                        firstDoubleLesson) {
                    lessonSpace--;
                    firstDoubleLesson = false;
                } else {
                    firstDoubleLesson = true;
                }
            }
        }

        return timeTable;
    }

    private static String getTeacherName(Elements childs, Elements secondChilds, boolean isChange) {
        String ret = "";

        if (isChange) {
            childs = childs.get(0).children();
        } else if (childs.size() < 3){
            return ret;
        }

        ret = childs.get(2).text();

        if (secondChilds != null){
            ret += " / " + secondChilds.get(2).text();
        }

        return ret;
    }

    public static TimeTable getEmptyWeekTimeTable() {
        TimeTable timeTable = new TimeTable()
                .setGroup("")
                .setLastRefresh(dateFormat.format(new Date()));

        for (int i = 0; i < 7; i++){
            Day day = new Day()
                    .setDate("")
                    .setId(i);

            for (int i1 = 0; i1 < 7; i1++) {
                day.getDayLessons()
                        .add(new Lesson()
                                .setName("")
                                .setTeacher("")
                                .setNumber(i1)
                                .setChange(false)
                                //fixme убрать этот null и переделать на ""
                                .setSecondName(null));
            }

            timeTable.addDay(day);
        }


        return timeTable;
    }

    public static TimeTable trimTimetable(TimeTable timeTable) {
        for (Day day: timeTable.getDayList()) {
            boolean emptyDay = true;

            for (Lesson lesson: day.getDayLessons()) {
                if (!lesson.getDoubleName().equals(""))
                    emptyDay = false;
            }

            if (emptyDay){
                timeTable.getDayList().remove(day);
            }
        }
        return timeTable;
    }

}