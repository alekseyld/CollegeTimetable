package com.alekseyld.collegetimetable.utils;

import com.alekseyld.collegetimetable.entity.Day;
import com.alekseyld.collegetimetable.entity.Lesson;
import com.alekseyld.collegetimetable.entity.TimeTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Alekseyld on 10.09.2017.
 */
public class DataUtilsTest{

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
        assertTrue(DataUtils.getGroupUrl("2 ТАК-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_9.html"));
        assertTrue(DataUtils.getGroupUrl("2 ТОВ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_7.html"));

        assertTrue(DataUtils.getGroupUrl("1 ТО-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭННУ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭННУ").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭНН-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 АПП-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 БНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 В-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 ПНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТАК-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТОВ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_8.html"));
    }

    // 3 АПП-1
    @Test
    public void parseTestTimetable3APP1_7() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(3).getName().equals("\u00A0"));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(6).getDate().equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(6).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_1() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() == 1);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        // another group

        tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() == 1);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        // empty group

        tableWrapper = DataUtils.parseDocument(document, "4 ЭНН-2");
        assertTrue(tableWrapper.getDayList() == null);

        tableWrapper = DataUtils.parseDocument(null, "4 АПП-2");
        assertTrue(tableWrapper.getDayList() == null);

        tableWrapper = DataUtils.parseDocument(document, "111441");
        assertTrue(tableWrapper.getDayList() == null);
    }

    @Test
    public void parseTestTimetable3APP1_2() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_3() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_4() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_5() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(3).getName().equals("\u00A0"));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetable3APP1_6() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(2).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(1).getName().equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(1).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(2).getName().equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(3).getName().equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(2).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(3).getName().equals("\u00A0"));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(2).getName().equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    // 4 АПП-2
    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_1() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_2() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_3() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_4() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_5() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));
    }

    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_6() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(1).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

    @Test
    public void parseTestTimetableAPPallWeekWithDoubleLessons_7() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week").timeout(0).get();

        TimeTable tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getDayList() != null);

        List<Day> timeTable = tableWrapper.getDayList();

        assertTrue(timeTable.size() > 0);
        assertTrue(tableWrapper.getDayList().get(0).getDate().equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        List<Lesson> lessons = timeTable.get(0).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(1).getDate().equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(1).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(2).getDate().equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(2).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("\u00A0"));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(4).getName().equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(3).getDate().equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(3).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(4).getDate().equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(4).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(5).getDate().equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(5).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(1).getName().equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(2).getName().equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(3).getName().equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

        assertTrue(tableWrapper.getDayList().get(6).getDate().equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(6).getDayLessons();

        assertTrue(lessons.get(0).getName().equals("\u00A0"));
        assertTrue(lessons.get(1).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(2).getName().equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(3).getName().equals("Экономика организации Давыдова А.С."
                + "\n/\n" + "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(4).getName().equals("\u00A0"));
        assertTrue(lessons.get(5).getName().equals("\u00A0"));
        assertTrue(lessons.get(6).getName().equals("\u00A0"));

    }

}