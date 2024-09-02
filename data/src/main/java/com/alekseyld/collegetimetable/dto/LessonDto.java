package com.alekseyld.collegetimetable.dto;

import com.alekseyld.collegetimetable.entity.Lesson;
import com.google.gson.annotations.SerializedName;

public class LessonDto {

    @SerializedName("number")
    private final int number;

    @SerializedName("name")
    private final String name;

    @SerializedName("secondName")
    private final String secondName;

    @SerializedName("teacher")
    private final String teacher;

    @SerializedName("time")
    private final String time;

    @SerializedName("change")
    private final boolean change;

    public LessonDto(Lesson lesson) {
        this.number = lesson.getNumber();
        this.name = lesson.getName();
        this.secondName = lesson.getSecondName();
        this.teacher = lesson.getTeacher();
        this.time = lesson.getTime();
        this.change = lesson.isChange();
    }

    public Lesson toEntity() {
        return new Lesson()
            .setNumber(number)
            .setName(name)
            .setSecondName(secondName)
            .setTeacher(teacher)
            .setTime(time)
            .setChange(change);
    }
}
