package com.alekseyld.collegetimetable.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.service.GroupService;
import com.alekseyld.collegetimetable.service.GroupServiceImpl;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.service.TableServiceImpl;
import com.alekseyld.collegetimetable.utils.api.SettingsApiMock;
import com.alekseyld.collegetimetable.utils.repository.SettingsRepositoryMock;
import com.alekseyld.collegetimetable.utils.repository.TableRepositoryMock;
import com.alekseyld.collegetimetable.utils.service.GroupServiceMock;

import org.junit.Test;

import java.util.List;

/**
 * Created by Alekseyld on 10.09.2017.
 */
public class DataUtilsTest {

    @Test
    public void getEmptyWeekTimeTable() {

        assertEquals(7, DataUtils.getEmptyWeekTimeTable(7, 7, true).getDayList().size());
    }

    @Test
    public void checkSettingsApiMock() {

        assertEquals("http://109.195.146.243/wp-content/uploads/time/", new SettingsApiMock().getSettings().toBlocking().first().getRootUrl());
    }

    @Test
    public void getGroupUrl() {
        SettingsRepositoryMock settingsRepositoryMock = new SettingsRepositoryMock();
        settingsRepositoryMock.getSettings().setRootUrl("http://uecoll.ru/wp-content/uploads/time/");

        GroupService groupService = new GroupServiceImpl(settingsRepositoryMock);

        assertEquals("", groupService.getGroupUrl(""));
        assertEquals("", groupService.getGroupUrl(" "));
        assertEquals("", groupService.getGroupUrl("            "));
        assertEquals("", groupService.getGroupUrl("     "));
        assertEquals("", groupService.getGroupUrl("24141"));
        assertEquals("", groupService.getGroupUrl("3 ЖАК-2"));
        assertEquals("", groupService.getGroupUrl("3 АПП-2 14141"));
        assertEquals("", groupService.getGroupUrl("3 АПП-214141"));
        assertEquals("", groupService.getGroupUrl("3 АПП-214141 2"));
        assertEquals("", groupService.getGroupUrl("3 АПП-2 фаыф24"));
        assertEquals("", groupService.getGroupUrl("   2 АПП-1  "));
        assertEquals("", groupService.getGroupUrl("ТО"));

        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_8.html", groupService.getGroupUrl("1 Т-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_7.html", groupService.getGroupUrl("1 Э-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_10.html", groupService.getGroupUrl("1 С-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_9.html", groupService.getGroupUrl("1 Б-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_3.html", groupService.getGroupUrl("1 Л-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_4.html", groupService.getGroupUrl("1 Р-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_1.html", groupService.getGroupUrl("1 ИС-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_1.html", groupService.getGroupUrl("1 ИС"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html", groupService.getGroupUrl("1 ГС-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html", groupService.getGroupUrl("1 ГСУ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html", groupService.getGroupUrl("1 ГСУ"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_4.html", groupService.getGroupUrl("1 РУ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_5.html", groupService.getGroupUrl("1 ПГ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_6.html", groupService.getGroupUrl("1 ТС-1"));

        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_3.html", groupService.getGroupUrl("2 ТО-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html", groupService.getGroupUrl("2 ЭННУ"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html", groupService.getGroupUrl("2 ЭННУ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html", groupService.getGroupUrl("2 ЭНН-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_1.html", groupService.getGroupUrl("2 АПП-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_2.html", groupService.getGroupUrl("2 БНГ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_4.html", groupService.getGroupUrl("2 В-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_5.html", groupService.getGroupUrl("2 ПНГ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_8.html", groupService.getGroupUrl("2 ТАК-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_7.html", groupService.getGroupUrl("2 ТОВ-1"));

        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ТО-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ЭННУ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ЭННУ"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ЭНН-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 АПП-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 БНГ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 В-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ПНГ-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ТАК-1"));
        assertEquals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html", groupService.getGroupUrl("1 ТОВ-1"));
    }

    private TableService getMockTableService(String group, String url) {
        SettingsRepositoryMock settingsRepositoryMock = new SettingsRepositoryMock();

        Settings settings = settingsRepositoryMock.getSettings();
        settings.setNotificationGroup(group);
        settings.setRootUrl(url);

        return new TableServiceImpl(
                new TableRepositoryMock(),
                settingsRepositoryMock,
                new SettingsApiMock(),
                new GroupServiceMock()
        );
    }

    // 3 АПП-1
    @Test
    public void parseTestTimetable3APP1_1() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(false, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertEquals(1, timeTable.size());
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertTrue(lessons.get(0).getTeacher().contains("Баймухаметова Ю.У."));
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertTrue(lessons.get(1).getTeacher().contains("Сагдеева Г.А."));
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertTrue(lessons.get(2).getTeacher().contains("Корепанова И.А."));
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertTrue(lessons.get(3).getTeacher().contains("Баймухаметова Ю.У."));
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(4).getTeacher());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(5).getTeacher());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("", lessons.get(6).getTeacher());

        // another group

        tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        timeTable = tableWrapper.getDayList();

        assertEquals(1, timeTable.size());
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("", lessons.get(0).getTeacher());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertTrue(lessons.get(1).getTeacher().contains("Милованова М.И."));
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertTrue(lessons.get(3).getTeacher().contains("Давыдова А.С."));
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(4).getTeacher());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(5).getTeacher());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("", lessons.get(6).getTeacher());
    }

    @Test
    public void tesDataUtilsParseDocumentt() {
        // empty group

        TimeTable tableWrapper = DataUtils.parseDocument(null, "4 ЭНН-2");
        assertEquals(0, tableWrapper.getDayList().size());

        tableWrapper = DataUtils.parseDocument(null, "4 АПП-2");
        assertEquals(0, tableWrapper.getDayList().size());

        tableWrapper = DataUtils.parseDocument(null, "111441");
        assertEquals(0, tableWrapper.getDayList().size());
    }

    @Test
    public void parseTestTimetable3APP1_2() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(false, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. /Кусякова Л.Ф.", lessons.get(1).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable3APP1_3() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("", lessons.get(0).getTeacher());
        assertEquals("Иностранный язык Захарова И.В. /Кусякова Л.Ф.", lessons.get(1).getDoubleName());
        assertTrue(lessons.get(1).getTeacher().contains("Захарова И.В."));
        assertTrue(lessons.get(1).getTeacher().contains("Кусякова Л.Ф."));
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(2).getDoubleName());
        assertTrue(lessons.get(2).getTeacher().contains("Гадельбаева Р.А."));
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(3).getDoubleName());
        assertTrue(lessons.get(3).getTeacher().contains("Милованова М.И."));
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(4).getTeacher());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(5).getTeacher());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("", lessons.get(6).getTeacher());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Федяева Ю.В.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable3APP1_4() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. /Кусякова Л.Ф.", lessons.get(1).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Федяева Ю.В.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable3APP1_5() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. /Кусякова Л.Ф.", lessons.get(1).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Федяева Ю.В.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable3APP1_6() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. /Кусякова Л.Ф.", lessons.get(1).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Федяева Ю.В.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С У Б Б О Т А   16.09.2017", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Основы философии Вершинина Н.П.", lessons.get(1).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable3APP1_7() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. /Кусякова Л.Ф.", lessons.get(1).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Федяева Ю.В.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Компьютерное моделирование Гадельбаева Р.А.", lessons.get(1).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С У Б Б О Т А   16.09.2017", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Основы философии Вершинина Н.П.", lessons.get(1).getDoubleName());
        assertEquals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П О Н Е Д Е Л Ь Н И К   18.09.2017", tableWrapper.getDayList().get(6).getDate());
        lessons = timeTable.get(6).getDayLessons();

        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(0).getDoubleName());
        assertEquals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.", lessons.get(1).getDoubleName());
        assertEquals("Основные процессы и технологии ТЭК Корепанова И.А.", lessons.get(2).getDoubleName());
        assertEquals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    // 4 АПП-2
    @Test
    public void parseTestTimetable4APP2_1() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

    }

    @Test
    public void parseTestTimetable4APP2_2() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Коршунова Н.Е. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable4APP2_3() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Коршунова Н.Е. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals(lessons.get(3).getDoubleName(), "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С.");
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getSecondName());
        assertEquals("Физическая культура Кайниев А.А.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable4APP2_4() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Коршунова Н.Е. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertTrue(lessons.get(4).getTeacher().contains("Коршунова Н.Е."));
        assertTrue(lessons.get(4).getTeacher().contains("Галиева И.В."));
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertTrue(lessons.get(2).getTeacher().contains("Милованов А.С."));
        assertEquals(lessons.get(3).getDoubleName(), "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С.");
        assertTrue(lessons.get(3).getTeacher().contains("Милованов А.С."));
        assertTrue(lessons.get(3).getTeacher().contains("Давыдова А.С."));
        assertEquals("Физическая культура Кайниев А.А.", lessons.get(4).getDoubleName());
        assertTrue(lessons.get(4).getTeacher().contains("Кайниев А.А."));
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(5).getTeacher());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("", lessons.get(6).getTeacher());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable4APP2_5() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Коршунова Н.Е. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals(lessons.get(3).getDoubleName(), "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С.");
        assertEquals("Физическая культура Кайниев А.А.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
    }

    @Test
    public void parseTestTimetable4APP2_6() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Коршунова Н.Е. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals(lessons.get(3).getDoubleName(), "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С.");
        assertEquals("Физическая культура Кайниев А.А.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С У Б Б О Т А   16.09.2017", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.", lessons.get(0).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetable4APP2_7() {

        TableService tableService = getMockTableService("3 АПП-1", "https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Коршунова Н.Е. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals(lessons.get(3).getDoubleName(), "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С.");
        assertEquals("Физическая культура Кайниев А.А.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С У Б Б О Т А   16.09.2017", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.", lessons.get(0).getDoubleName());
        assertEquals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.", lessons.get(1).getDoubleName());
        assertEquals("Экономика организации Давыдова А.С.", lessons.get(2).getDoubleName());
        assertEquals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П О Н Е Д Е Л Ь Н И К   18.09.2017", tableWrapper.getDayList().get(6).getDate());
        lessons = timeTable.get(6).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(1).getDoubleName());
        assertEquals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.", lessons.get(2).getDoubleName());
        assertEquals(lessons.get(3).getDoubleName(), "Экономика организации Давыдова А.С."
                + "\n/\n" + "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.");
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

    }

    @Test
    public void parseTestTimetableTeacherVershinia_7() {

        SettingsRepositoryMock settingsRepositoryMock = new SettingsRepositoryMock();

        Settings settings = settingsRepositoryMock.getSettings();
        settings.addTeacherGroup("3 АПП-1");
        settings.addTeacherGroup("3 АПП-2");
        settings.addTeacherGroup("3 АПП-3");
        settings.setTeacherMode(true);
        settings.setNotificationGroup("Вершинина Н.П.");
        settings.setRootUrl("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week");

        TableServiceImpl tableRepository = new TableServiceImpl(
                new TableRepositoryMock(),
                settingsRepositoryMock,
                new SettingsApiMock(),
                new GroupServiceMock()
        );

        TimeTable tableWrapper = tableRepository.getTeacherTimeTable(false, settings.getNotificationGroup(), settings.getTeacherGroups()).toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   11.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("", lessons.get(0).getDoubleName());
        assertEquals(lessons.get(1).getDoubleName(), "3 АПП-3\n" + "Основы философии Вершинина Н.П.");
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   12.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals(lessons.get(0).getDoubleName(), "3 АПП-2\n" + "Основы философии Вершинина Н.П.");
        assertEquals(lessons.get(1).getDoubleName(), "3 АПП-2\n" + "Основы философии Вершинина Н.П.");
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

        assertEquals("С Р Е Д А   13.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("", lessons.get(0).getDoubleName());
        assertEquals("", lessons.get(1).getDoubleName());
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   14.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("", lessons.get(0).getDoubleName());
        assertEquals("", lessons.get(1).getDoubleName());
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   15.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("", lessons.get(0).getDoubleName());
        assertEquals("", lessons.get(1).getDoubleName());
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

        assertEquals("С У Б Б О Т А   16.09.2017", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals(lessons.get(0).getDoubleName(), "3 АПП-3\n" + "Основы философии Вершинина Н.П.");
        assertEquals(lessons.get(1).getDoubleName(), "3 АПП-1\n" + "Основы философии Вершинина Н.П.");
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

        assertEquals("П О Н Е Д Е Л Ь Н И К   18.09.2017", tableWrapper.getDayList().get(6).getDate());
        lessons = timeTable.get(6).getDayLessons();

        assertEquals("", lessons.get(0).getDoubleName());
        assertEquals(lessons.get(1).getDoubleName(), "3 АПП-3\n" + "Основы философии Вершинина Н.П.");
        assertEquals("", lessons.get(2).getDoubleName());
        assertEquals("", lessons.get(3).getDoubleName());
        assertEquals("", lessons.get(4).getDoubleName());
        assertEquals("", lessons.get(5).getDoubleName());
        assertEquals("", lessons.get(6).getDoubleName());

    }

    // 1 ПНГ-1
    @Test
    public void parseTestTimetable1PNG1_7() {

        TableService tableService = getMockTableService("1 ПНГ-1", "https://alekseyld.github.io/CollegeTimetable/timetable_1.html");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "1 ПНГ-1").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   18.09.2017", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("Информатика Шайбакова Л.М.", lessons.get(2).getDoubleName());
        assertEquals("Химия Журавлева А.А.", lessons.get(3).getDoubleName());
        assertEquals("Классный час Алаторцева Н.Е.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("В Т О Р Н И К   19.09.2017", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Мусин Т.Р.", lessons.get(1).getDoubleName());
        assertEquals("Физика Хакимова А.Ш.", lessons.get(2).getDoubleName());
        assertEquals("Биология Шаймарданова А.Р.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(3).isChange());

        assertEquals("С Р Е Д А   20.09.2017", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Биология Шаймарданова А.Р.", lessons.get(1).getDoubleName());
        assertEquals("Русский язык Хажиева Г.Ф.", lessons.get(2).getDoubleName());
        assertEquals("Математика: алгебра, начала математического анализа, геометрия Алаторцева Н.Е.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   21.09.2017", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Литература Хажиева Г.Ф.", lessons.get(1).getDoubleName());
        assertEquals("Обществознание (включая экономику и право) Ризаева А.З.", lessons.get(2).getDoubleName());
        assertEquals("Математика: алгебра, начала математического анализа, геометрия Алаторцева Н.Е.", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П Я Т Н И Ц А   22.09.2017", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Башкирский язык Ахмадеева Л.И. /Ульябаева М.Р.", lessons.get(1).getDoubleName());
        assertEquals("Химия Журавлева А.А.", lessons.get(2).getDoubleName());
        assertEquals("Физика Хакимова А.Ш.", lessons.get(3).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. /Галиева И.В.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("С У Б Б О Т А   23.09.2017", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals("Иностранный язык Галиева И.В.", lessons.get(0).getDoubleName());
        assertEquals("Обществознание (включая экономику и право) Ризаева А.З.", lessons.get(1).getDoubleName());
        assertEquals("История Сафаргалеева М.А.", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertEquals("П О Н Е Д Е Л Ь Н И К   25.09.2017", tableWrapper.getDayList().get(6).getDate());
        lessons = timeTable.get(6).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("Математика: алгебра, начала математического анализа, геометрия Алаторцева Н.Е.", lessons.get(2).getDoubleName());
        assertEquals("Химия Журавлева А.А.", lessons.get(3).getDoubleName());
        assertEquals("История Сафаргалеева М.А.", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());

        assertTrue(lessons.get(2).isChange());

    }

    @Test
    public void parseNewTimetable() {

        TableService tableService = getMockTableService("2 ТОВ-2", "https://alekseyld.github.io/CollegeTimetable/new_timetable_all_week.html");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "2 ТОВ-2").toBlocking().first();

        assertNotNull(tableWrapper.getDayList());

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertEquals("П О Н Е Д Е Л Ь Н И К   30.08.2021", tableWrapper.getDayList().get(0).getDate());

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("\u00A0", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("Органическая химия Зайнуллина Л.Ф. -", lessons.get(4).getDoubleName());
        assertEquals("Башкирский язык Ахмадеева Л.И. -", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

        assertEquals("В Т О Р Н И К   31.08.2021", tableWrapper.getDayList().get(1).getDate());
        lessons = timeTable.get(1).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Математика Валиахметова Г.В. -", lessons.get(1).getDoubleName());
        assertEquals("Иностранный язык Захарова И.В. - /Салимова Г.Р. -", lessons.get(2).getDoubleName());
        assertEquals("Математика Валиахметова Г.В. -", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

        assertEquals("С Р Е Д А   01.09.2021", tableWrapper.getDayList().get(2).getDate());
        lessons = timeTable.get(2).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("Теоретические основы химической технологии Мунасыпова А.М. -", lessons.get(2).getDoubleName());
        assertEquals("Физическая и коллодная химия Файзуллина С.Р. -", lessons.get(3).getDoubleName());
        assertEquals("Общая и неорганическая химия Зайнуллина Л.Ф. -", lessons.get(4).getDoubleName());
        assertEquals("Башкирский язык Ахмадеева Л.И. -", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

        assertEquals("Ч Е Т В Е Р Г   02.09.2021", tableWrapper.getDayList().get(3).getDate());
        lessons = timeTable.get(3).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Информационные технологии в профессиональной деятельности Галина Р.Р. -", lessons.get(1).getName());
        assertEquals("-", lessons.get(1).getSecondName());
        assertEquals("Информационные технологии в профессиональной деятельности Галина Р.Р. -", lessons.get(2).getName());
        assertEquals("-", lessons.get(2).getSecondName());
        assertEquals("Теоретические основы химической технологии Мунасыпова А.М. -", lessons.get(3).getDoubleName());
        assertEquals("Информационные технологии в профессиональной деятельности Галина Р.Р. -", lessons.get(4).getSecondName());
        assertEquals("Информационные технологии в профессиональной деятельности Галина Р.Р. -", lessons.get(5).getSecondName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

        assertEquals("П Я Т Н И Ц А   03.09.2021", tableWrapper.getDayList().get(4).getDate());
        lessons = timeTable.get(4).getDayLessons();

        assertEquals("История Кадочникова М.Р. -", lessons.get(0).getDoubleName());
        assertEquals("Физическая культура Кайниев А.А. -", lessons.get(1).getDoubleName());
        assertEquals("Экологические основы природопользования Соломатина М.С. -", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

        assertEquals("С У Б Б О Т А   04.09.2021", tableWrapper.getDayList().get(5).getDate());
        lessons = timeTable.get(5).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("Аналитическая химия Зайнуллина Л.Ф. -", lessons.get(1).getDoubleName());
        assertEquals("Органическая химия Зайнуллина Л.Ф. -", lessons.get(2).getDoubleName());
        assertEquals("Общая и неорганическая химия Зайнуллина Л.Ф. -", lessons.get(3).getDoubleName());
        assertEquals("\u00A0", lessons.get(4).getDoubleName());
        assertEquals("\u00A0", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

        assertEquals("П О Н Е Д Е Л Ь Н И К   06.09.2021", tableWrapper.getDayList().get(6).getDate());
        lessons = timeTable.get(6).getDayLessons();

        assertEquals("\u00A0", lessons.get(0).getDoubleName());
        assertEquals("\u00A0", lessons.get(1).getDoubleName());
        assertEquals("\u00A0", lessons.get(2).getDoubleName());
        assertEquals("\u00A0", lessons.get(3).getDoubleName());
        assertEquals("Органическая химия Зайнуллина Л.Ф. -", lessons.get(4).getDoubleName());
        assertEquals("Башкирский язык Ахмадеева Л.И. -", lessons.get(5).getDoubleName());
        assertEquals("\u00A0", lessons.get(6).getDoubleName());
        assertEquals("\u00A0", lessons.get(7).getDoubleName());

    }

}