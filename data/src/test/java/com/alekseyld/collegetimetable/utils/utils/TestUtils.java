package com.alekseyld.collegetimetable.utils.utils;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.TimeTable;

public class TestUtils {

    public static String getNormalizeDate(String date) {
        String[] splits = date.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splits.length; i++) {
            if (splits[i].length() > 1)
                sb.append(" ");
            sb.append(i == 0 ? splits[i] : splits[i].toLowerCase());
        }
        return sb.toString();
    }

    public static String convertTimeTableToText(TimeTable timeTable) {

        StringBuilder stringBuilder = new StringBuilder();

        //stringBuilder.append("Расписание группы ").append(timeTable.getGroup()).append("\n\n");

        for (Day day: timeTable.getDayList()) {
            String dayName = getNormalizeDate(day.getDate());
            stringBuilder.append(dayName).append("\n\n");
            boolean needEmptyNum = true;

            for (Lesson lesson: day.getDayLessons()){
                if (!lesson.getName().equals(" ") && !lesson.getName().equals("")) {
                    needEmptyNum = false;
                    stringBuilder.append(lesson.getNumber()).append(". ").append(lesson.getDoubleName());
                    if (lesson.isChange())
                        stringBuilder.append(" ").append("(замена)");
                    stringBuilder.append("\n\n");
                } else {
                    if (needEmptyNum) {
                        stringBuilder.append(lesson.getNumber()).append(". ").append(lesson.getDoubleName()).append("-").append("\n\n");
                    }
                }
            }
            if (!dayName.equals("") && day.getId() != timeTable.getDayList().get(timeTable.getDayList().size() - 1).getId())
                stringBuilder.append("----------------------------------------\n");
        }

        return stringBuilder.toString().trim();
    }

}
