package com.alekseyld.collegetimetable.dto;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alekseyld on 15.06.2024.
 */
public class DayDto {

    @SerializedName("id")
    private final int id;

    @SerializedName("date")
    private final String date;

    @SerializedName("dayLessons")
    private final List<LessonDto> dayLessons;

    public DayDto(Day day) {
        this.id = day.getId();
        this.date = day.getDate();
        this.dayLessons = new ArrayList<>(day.getDayLessons().size());

        for (Lesson lesson : day.getDayLessons()) {
            this.dayLessons.add(new LessonDto(lesson));
        }
    }

    public Day toEntity() {
        List<Lesson> dayLessons = new ArrayList<>(this.dayLessons.size());
        for (LessonDto lessonDto : this.dayLessons) {
            dayLessons.add(lessonDto.toEntity());
        }

        return new Day()
            .setId(id)
            .setDate(date)
            .setDayLessons(dayLessons);
    }
}
