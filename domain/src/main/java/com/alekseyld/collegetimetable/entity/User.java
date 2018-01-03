package com.alekseyld.collegetimetable.entity;

/**
 * Created by Alekseyld on 03.01.2018.
 */

public class User {

    private String studentId;

    private String group;

    private String name;

    private String surname;

    private String patronymic;

    private String authKey;

    public String getStudentId() {
        return studentId;
    }

    public User setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public User setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public User setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public String getAuthKey() {
        return authKey;
    }

    public User setAuthKey(String authKey) {
        this.authKey = authKey;
        return this;
    }

    public User update(User newUser) {

        if (newUser.getStudentId() != null) {
            this.setStudentId(newUser.getStudentId());
        }

        if (newUser.getGroup() != null) {
            this.setGroup(newUser.getGroup());
        }

        if (newUser.getName() != null) {
            this.setName(newUser.getName());
        }

        if (newUser.getSurname() != null) {
            this.setSurname(newUser.getSurname());
        }

        if (newUser.getPatronymic() != null) {
            this.setPatronymic(newUser.getPatronymic());
        }

        if (newUser.getAuthKey() != null) {
            this.setAuthKey(newUser.getAuthKey());
        }

        return this;
    }

    public String getNameWithSurname(){
        return getSurname() + " " + getName().charAt(0) + "." + getPatronymic().charAt(0) + ".";
    }

}
