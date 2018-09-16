package com.alekseyld.collegetimetable.utils;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.Settings;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.service.TableServiceImpl;
import com.alekseyld.collegetimetable.utils.api.ProxyApiMock;
import com.alekseyld.collegetimetable.utils.api.SettingsApiMock;
import com.alekseyld.collegetimetable.utils.repository.SettingsRepositoryMock;
import com.alekseyld.collegetimetable.utils.repository.TableRepositoryMock;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alekseyld on 10.09.2017.
 */
public class DataUtilsTest{

    @Test
    public void getEmptyWeekTimeTable() throws Exception {

        assertTrue(DataUtils.getEmptyWeekTimeTable(7, 7, true).getDayList().size() == 7);
    }

    @Test
    public void checkSettingsApiMock() throws Exception {

        assertTrue(
                new SettingsApiMock().getSettings().toBlocking().first().getRootUrl()
                        .equals("http://109.195.146.243/wp-content/uploads/time/"));
    }

    @Test
    public void getGroupUrl() throws Exception {
        assertTrue(DataUtils.getGroupUrl("").equals(""));
        assertTrue(DataUtils.getGroupUrl(" ").equals(""));
        assertTrue(DataUtils.getGroupUrl("            ").equals(""));
        assertTrue(DataUtils.getGroupUrl("     ").equals(""));
        assertTrue(DataUtils.getGroupUrl("24141").equals(""));
        assertTrue(DataUtils.getGroupUrl("3 ЖАК-2").equals(""));
        assertTrue(DataUtils.getGroupUrl("3 АПП-2 14141").equals(""));
        assertTrue(DataUtils.getGroupUrl("3 АПП-214141").equals(""));
        assertTrue(DataUtils.getGroupUrl("3 АПП-214141 2").equals(""));
        assertTrue(DataUtils.getGroupUrl("3 АПП-2 фаыф24").equals(""));
        assertTrue(DataUtils.getGroupUrl("   2 АПП-1  ").equals(""));
        assertTrue(DataUtils.getGroupUrl("ТО").equals(""));

        assertTrue(DataUtils.getGroupUrl("1 Т-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 Э-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_7.html"));
        assertTrue(DataUtils.getGroupUrl("1 С-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 Б-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_9.html"));
        assertTrue(DataUtils.getGroupUrl("1 Л-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_3.html"));
        assertTrue(DataUtils.getGroupUrl("1 Р-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_4.html"));
        assertTrue(DataUtils.getGroupUrl("1 ИС-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_1.html"));
        assertTrue(DataUtils.getGroupUrl("1 ИС").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_1.html"));
        assertTrue(DataUtils.getGroupUrl("1 ГС-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("1 ГСУ-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("1 ГСУ").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("1 РУ-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_4.html"));
        assertTrue(DataUtils.getGroupUrl("1 ПГ-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_5.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТС-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_6.html"));

        assertTrue(DataUtils.getGroupUrl("2 ТО-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_3.html"));
        assertTrue(DataUtils.getGroupUrl("2 ЭННУ").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html"));
        assertTrue(DataUtils.getGroupUrl("2 ЭННУ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html"));
        assertTrue(DataUtils.getGroupUrl("2 ЭНН-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html"));
        assertTrue(DataUtils.getGroupUrl("2 АПП-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_1.html"));
        assertTrue(DataUtils.getGroupUrl("2 БНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("2 В-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_4.html"));
        assertTrue(DataUtils.getGroupUrl("2 ПНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_5.html"));
        assertTrue(DataUtils.getGroupUrl("2 ТАК-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("2 ТОВ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_7.html"));

        assertTrue(DataUtils.getGroupUrl("1 ТО-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭННУ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭННУ").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭНН-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 АПП-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 БНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 В-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 ПНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТАК-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТОВ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_10.html"));
    }

    private TableService getMockTableService(String group, String url) {
        SettingsRepositoryMock settingsRepositoryMock = new SettingsRepositoryMock();

        Settings settings = settingsRepositoryMock.getSettings();
        settings.setNotificationGroup(group);
        return new TableServiceImpl(
                new TableRepositoryMock(),
                settingsRepositoryMock,
                new ProxyApiMock().setFromTestPages(true).setTestPages(url),
                new SettingsApiMock()
        );
    }

    // 3 АПП-1
    @Test
    public void parseTestTimetable3APP1_1() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(false, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() == 1);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(0).getTeacher().contains("Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getTeacher().contains("Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(2).getTeacher().contains("Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getTeacher().contains("Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getTeacher().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getTeacher().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getTeacher().equals(""));

        // another group

        tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() == 1);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(0).getTeacher().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(1).getTeacher().contains("Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getTeacher().contains("Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getTeacher().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getTeacher().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getTeacher().equals(""));
    }

    @Test
    public void tesDataUtilsParseDocumentt() throws Exception {
        // empty group

        TimeTable tableWrapper = DataUtils.parseDocument(null, "4 ЭНН-2");
        assertTrue(tableWrapper.getDayList().size() == 0);

        tableWrapper = DataUtils.parseDocument(null, "4 АПП-2");
        assertTrue(tableWrapper.getDayList().size() == 0);

        tableWrapper = DataUtils.parseDocument(null, "111441");
        assertTrue(tableWrapper.getDayList().size() == 0);
    }

    @Test
    public void parseTestTimetable3APP1_2() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(false, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_3() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(0).getTeacher().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(1).getTeacher().contains("Захарова И.В."));
        assertTrue(lessons.get(1).getTeacher().contains("Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getTeacher().contains("Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(3).getTeacher().contains("Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getTeacher().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getTeacher().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getTeacher().equals(""));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getDoubleName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_4() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getDoubleName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_5() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getDoubleName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_6() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getDoubleName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_7() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "3 АПП-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getDoubleName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(6).getDate().equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(6).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getDoubleName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    // 4 АПП-2
    @Test
    public void parseTestTimetable4APP2_1() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

    }

    @Test
    public void parseTestTimetable4APP2_2() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable4APP2_3() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getSecondName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable4APP2_4() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(4).getTeacher().contains("Коршунова Н.Е."));
        assertTrue(lessons.get(4).getTeacher().contains("Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(2).getTeacher().contains("Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getTeacher().contains("Милованов А.С."));
        assertTrue(lessons.get(3).getTeacher().contains("Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(4).getTeacher().contains("Кайниев А.А."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getTeacher().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getTeacher().equals(""));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable4APP2_5() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));
    }

    @Test
    public void parseTestTimetable4APP2_6() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable4APP2_7() throws Exception {

        TableService tableService = getMockTableService("3 АПП-1","https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "4 АПП-2").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(1).isChange());
        assertTrue(lessons.get(2).isChange());
        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getDoubleName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(6).getDate().equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(6).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(2).getDoubleName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getDoubleName().equals("Экономика организации Давыдова А.С."
                + "\n/\n" + "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetableTeacherVershinia_7() throws Exception {

        SettingsRepositoryMock settingsRepositoryMock = new SettingsRepositoryMock();

        Settings settings = settingsRepositoryMock.getSettings();
        settings.addTeacherGroup("3 АПП-1");
        settings.addTeacherGroup("3 АПП-2");
        settings.addTeacherGroup("3 АПП-3");
        settings.setTeacherMode(true);
        settings.setNotificationGroup("Вершинина Н.П.");

        TableServiceImpl tableRepository = new TableServiceImpl(
                new TableRepositoryMock(),
                settingsRepositoryMock,
                new ProxyApiMock().setFromTestPages(true),
                new SettingsApiMock()
        );

        TimeTable tableWrapper = tableRepository.getTeacherTimeTable(false, settings.getNotificationGroup(), settings.getTeacherGroups()).toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals("3 АПП-3\n" + "Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("3 АПП-2\n" + "Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(1).getDoubleName().equals("3 АПП-2\n" + "Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals(""));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals(""));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals(""));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("3 АПП-3\n" + "Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(1).getDoubleName().equals("3 АПП-1\n" + "Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

        assertTrue(tableWrapper.getDayList().get(6).getDate().equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(6).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals(""));
        assertTrue(lessons.get(1).getDoubleName().equals("3 АПП-3\n" + "Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getDoubleName().equals(""));
        assertTrue(lessons.get(3).getDoubleName().equals(""));
        assertTrue(lessons.get(4).getDoubleName().equals(""));
        assertTrue(lessons.get(5).getDoubleName().equals(""));
        assertTrue(lessons.get(6).getDoubleName().equals(""));

    }

    // 1 ПНГ-1
    @Test
    public void parseTestTimetable1PNG1_7() throws Exception {

        TableService tableService = getMockTableService("1 ПНГ-1","https://alekseyld.github.io/CollegeTimetable/timetable_1.html");
        TimeTable tableWrapper = tableService.getTimetableFromOnline(true, "1 ПНГ-1").toBlocking().first();

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("Информатика Шайбакова Л.М."));
        assertTrue(lessons.get(3).getDoubleName().equals("Химия Журавлева А.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("Классный час Алаторцева Н.Е."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   19.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Физическая культура Мусин Т.Р."));
        assertTrue(lessons.get(2).getDoubleName().equals("Физика Хакимова А.Ш."));
        assertTrue(lessons.get(3).getDoubleName().equals("Биология Шаймарданова А.Р."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(3).isChange());

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   20.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Биология Шаймарданова А.Р."));
        assertTrue(lessons.get(2).getDoubleName().equals("Русский язык Хажиева Г.Ф."));
        assertTrue(lessons.get(3).getDoubleName().equals("Математика: алгебра, начала математического анализа, геометрия Алаторцева Н.Е."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   21.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Литература Хажиева Г.Ф."));
        assertTrue(lessons.get(2).getDoubleName().equals("Обществознание (включая экономику и право) Ризаева А.З."));
        assertTrue(lessons.get(3).getDoubleName().equals("Математика: алгебра, начала математического анализа, геометрия Алаторцева Н.Е."));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   22.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("Башкирский язык Ахмадеева Л.И. /Ульябаева М.Р."));
        assertTrue(lessons.get(2).getDoubleName().equals("Химия Журавлева А.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("Физика Хакимова А.Ш."));
        assertTrue(lessons.get(4).getDoubleName().equals("Иностранный язык Захарова И.В. /Галиева И.В."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   23.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("Иностранный язык Галиева И.В."));
        assertTrue(lessons.get(1).getDoubleName().equals("Обществознание (включая экономику и право) Ризаева А.З."));
        assertTrue(lessons.get(2).getDoubleName().equals("История Сафаргалеева М.А."));
        assertTrue(lessons.get(3).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(4).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(6).getDate().equals("П О Н Е Д Е Л Ь Н И К   25.09.2017"));
        lessons = timeTable.get(6).getDayLessons();

        assertTrue(lessons.get(0).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(1).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(2).getDoubleName().equals("Математика: алгебра, начала математического анализа, геометрия Алаторцева Н.Е."));
        assertTrue(lessons.get(3).getDoubleName().equals("Химия Журавлева А.А."));
        assertTrue(lessons.get(4).getDoubleName().equals("История Сафаргалеева М.А."));
        assertTrue(lessons.get(5).getDoubleName().equals("\u00A0"));
        assertTrue(lessons.get(6).getDoubleName().equals("\u00A0"));

        assertTrue(lessons.get(2).isChange());

    }

}