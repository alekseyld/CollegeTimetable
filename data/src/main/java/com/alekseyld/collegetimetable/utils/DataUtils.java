package com.alekseyld.collegetimetable.utils;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class DataUtils {

    public static Pattern groupPattern = Pattern.compile("[0-9]\\s[А-Я]{1,}[-][0-9]");
    public static Pattern groupPatternWithoutNum = Pattern.compile("[0-9]\\s[А-Я]{1,}([-][0-9]){0,}");
    public static Pattern fioPattern = Pattern.compile("([А-ЯЁа-яё]{1,}[\\s]([А-ЯЁа-яё]{1}[.]){2})");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public static String switchAbbr(Map<String, String> abbreviationMap, String abbr) {
        if (abbreviationMap == null || abbreviationMap.size() == 0) {
            String json = "{\"1\": \"neft/10_1_10.html\",\"Т\": \"energy/10_1_8.html\",\"Э\": \"energy/10_1_7.html\",\"С\": \"energy/10_1_10.html\",\"Б\": \"energy/10_1_9.html\",\"В\": \"neft/10_1_4.html\",\"Л\": \"energy/10_1_3.html\",\"Р\": \"energy/10_1_4.html\",\"АПП\": \"neft/10_1_1.html\",\"БНГ\": \"neft/10_1_2.html\",\"ТО\": \"neft/10_1_3.html\",\"ПНГ\": \"neft/10_1_5.html\",\"ЭНН\": \"neft/10_1_6.html\",\"ЭННУ\": \"neft/10_1_6.html\",\"ТОВ\": \"neft/10_1_7.html\",\"ИС\": \"energy/10_1_1.html\",\"ГС\": \"energy/10_1_2.html\",\"ГСУ\": \"energy/10_1_2.html\",\"РУ\": \"energy/10_1_4.html\",\"ПГ\": \"energy/10_1_5.html\",\"ТС\": \"energy/10_1_6.html\",\"ТАК\": \"energy/10_1_8.html\"}";
            abbreviationMap = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {
            }.getType());
        }

        boolean contain = abbreviationMap.containsKey(abbr);

        if (!contain && abbr.charAt(abbr.length() - 1) == 'У') {
            abbr = abbr.substring(0, abbr.length() - 1);
            contain = abbreviationMap.containsKey(abbr);
        }

        return contain ? abbreviationMap.get(abbr) : null;
//        switch (abbr) {
//            case "Т":
//                return "energy/10_1_8.html";
//            case "Э":
//                return "energy/10_1_7.html";
//            case "С":
//                return "energy/10_1_10.html";
//            case "Б":
//                return "energy/10_1_9.html";
//            case "В":
//                return "neft/10_1_4.html";
//            case "Л":
//                return "energy/10_1_3.html";
//            case "Р":
//                return "energy/10_1_4.html";
//            case "АПП":
//                return "neft/10_1_1.html";
//            case "БНГ":
//                return "neft/10_1_2.html";
//            case "ТО":
//                return "neft/10_1_3.html";
//            case "ПНГ":
//                return "neft/10_1_5.html";
//            case "ЭНН":
//                return "neft/10_1_6.html";
//            case "ЭННУ":
//                return "neft/10_1_6.html";
//            case "ТОВ":
//                return "neft/10_1_7.html";
//            case "ИС":
//                return "energy/10_1_1.html";
//            case "ГС":
//                return "energy/10_1_2.html";
//            case "ГСУ":
//                return "energy/10_1_2.html";
//            case "РУ":
//                return "energy/10_1_4.html";
//            case "ПГ":
//                return "energy/10_1_5.html";
//            case "ТС":
//                return "energy/10_1_6.html";
//            case "ТАК":
//                return "energy/10_1_9.html";
//            default:
//                return "";
//        }
    }

    public static TimeTable parseDocument(Document document, String group) {

        if (document == null || group == null || !groupPatternWithoutNum.matcher(group).matches()) {
            return new TimeTable();
        }

        Elements table = document.select("tr").select("td");

        Pattern lessonNumPattern = Pattern.compile("^[0-9](.)?(.\\d?\\d.\\d\\d-\\d?\\d.\\d\\d)?");
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
        int lessonSpace = 0;

        //Счетчик дней
        int day = -1;
        //Счетчик пар
        int lesson = 0;

        int i = 0;

        String lessonTime = "";

        for (int iterator = 0; iterator < table.size(); iterator++) {

            if (dayPattern.matcher(table.get(iterator).text()).find()) {
                dayString[0] = dayString[1];
                dayString[1] = table.get(iterator).text();
            }

            //Ищем начало групп
            if (table.get(iterator).text().contains("День/Пара") && first) {
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
                                .setDate(day == 0 && !dayString[0].equals("") || (day != 0 && !dayString[0].equals(timeTable.getDayList().get(day - 1).getDate())) ? dayString[0] : dayString[1])
                                .setDayLessons(lessons)
                );

            }

            String lessonNumberText = table.get(iterator).text().replaceAll("\\u00a0", " ").trim();
            boolean isLessonNumberBloc = lessonNumPattern.matcher(lessonNumberText).matches();
            if (toLesson && isLessonNumberBloc) {
                toLesson = false;
                spaceToLessonBlock = true;
                lessonSpace = 0;

                lessonTime = "";

                if (lessonNumberText.length() == 1) {
                    lesson = Integer.parseInt(lessonNumberText);
                } else {
                    String substring = lessonNumberText.substring(0, 1);
                    lesson = Integer.parseInt(substring);

                    String[] splits = lessonNumberText.split(" ");
                    if (splits.length > 1) {
                        lessonTime = splits[1];
                    }
                }

                if (lesson == 0 && day != -1) {
                    timeTable.addDay(
                            new Day()
                                    .setId(day)
                                    .setDate(day == 0 && !dayString[0].equals("") || !dayString[0].equals(timeTable.getDayList().get(day - 1).getDate()) ? dayString[0] : dayString[1])
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
                                .setTeacher(teacherName)
                                .setChange(isChange)
                                .setTime(lessonTime)
                );

                lessonSpace = 0;
                toLesson = true;
                spaceToLessonBlock = false;
            } else {
                if (table.get(iterator).tagName().equals("td") &&
                        table.get(iterator).attr("colspan").equals("") &&
                        table.get(iterator).attr("rowspan").equals("") &&
                        !isLessonNumberBloc &&
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
        } else if (childs.size() < 3) {
            return ret;
        }

        ret = childs.get(2).text();

        if (secondChilds != null) {
            ret += " / " + secondChilds.get(2).text();
        }

        return ret;
    }

    public static TimeTable getEmptyWeekTimeTable(int countDays, int lessonPerDayCount, boolean mock) {
        TimeTable timeTable = new TimeTable()
                .setGroup("")
                .setLastRefresh(dateFormat.format(new Date()));

        timeTable.setDayList(getEmptyDayList(countDays, lessonPerDayCount, mock));
        return timeTable;
    }

    public static List<Day> getEmptyDayList(int count, int lessonPerDayCount, boolean mock) {
        List<Day> days = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            days.add(getEmptyDay(i, lessonPerDayCount));
        }
        return days;
    }

    public static List<Lesson> getEmptyLessonList(int count) {
        List<Lesson> lessons = new ArrayList<>();
        for (int i1 = 0; i1 < count; i1++) {
            lessons.add(getEmptyLesson(i1));
        }
        return lessons;
    }

    public static Day getEmptyDay(int num, int lessonCount) {
        return new Day()
                .setDate("")
                .setId(num)
                .setDayLessons(getEmptyLessonList(lessonCount));
    }

    public static Lesson getEmptyLesson(int num) {
        return new Lesson()
                .setName("")
                .setTeacher("")
                .setNumber(num)
                .setChange(false)
                //fixme убрать этот null и переделать на ""
                .setSecondName(null);
    }

    public static TimeTable trimTimetable(TimeTable timeTable) {
        for (int iDay = 0; iDay < timeTable.getDayList().size(); iDay++) {
            Day day = timeTable.getDayList().get(iDay);
            boolean emptyDay = true;

            for (Lesson lesson : day.getDayLessons()) {
                if (!lesson.getDoubleName().equals(""))
                    emptyDay = false;
            }

            if (emptyDay) {
                timeTable.getDayList().remove(day);
            }
        }
        return timeTable;
    }

}