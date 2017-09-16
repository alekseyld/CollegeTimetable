package com.alekseyld.collegetimetable.utils;

import com.alekseyld.collegetimetable.TableWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.HashMap;

import static com.alekseyld.collegetimetable.TableWrapper.Day.Friday;
import static com.alekseyld.collegetimetable.TableWrapper.Day.Mon;
import static com.alekseyld.collegetimetable.TableWrapper.Day.Mon2;
import static com.alekseyld.collegetimetable.TableWrapper.Day.Saturday;
import static com.alekseyld.collegetimetable.TableWrapper.Day.Thu;
import static com.alekseyld.collegetimetable.TableWrapper.Day.Tue;
import static com.alekseyld.collegetimetable.TableWrapper.Day.Wed;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson0;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson1;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson2;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson3;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson4;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson5;
import static com.alekseyld.collegetimetable.TableWrapper.Lesson.lesson6;
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

    @Test
    public void testParseTimetableAPPallWeek() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson1).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson2).equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(lesson3).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("Иностранный язык Захарова И.В. /Кусякова Л.Ф."));
        assertTrue(lessons.get(lesson2).equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Wed).equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(Wed);

        assertTrue(lessons.get(lesson0).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson1).equals("Физическая культура Федяева Ю.В."));
        assertTrue(lessons.get(lesson2).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson3).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Thu).equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(Thu);

        assertTrue(lessons.get(lesson0).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson1).equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(lesson2).equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(lesson3).equals("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Friday).equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(Friday);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("Компьютерное моделирование Гадельбаева Р.А."));
        assertTrue(lessons.get(lesson2).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson3).equals("\u00A0"));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Saturday).equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(Saturday);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("Основы философии Вершинина Н.П."));
        assertTrue(lessons.get(lesson2).equals("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Mon2).equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(Mon2);

        assertTrue(lessons.get(lesson0).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson1).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson2).equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(lesson3).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_1() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "3 АПП-1");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() == 1);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson1).equals("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А."));
        assertTrue(lessons.get(lesson2).equals("Основные процессы и технологии ТЭК Корепанова И.А."));
        assertTrue(lessons.get(lesson3).equals("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        // another group

        tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() == 1);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        // empty group

        tableWrapper = DataUtils.parseDocument(document, "4 ЭНН-2");
        assertTrue(tableWrapper.getTimeTable().keySet().size() == 0);

        tableWrapper = DataUtils.parseDocument(null, "4 АПП-2");
        assertTrue(tableWrapper.getTimeTable() == null);

        tableWrapper = DataUtils.parseDocument(document, "111441");
        assertTrue(tableWrapper.getTimeTable() == null);
    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_2() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_3() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Wed).equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(Wed);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_4() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Wed).equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(Wed);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Thu).equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(Thu);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_5() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Wed).equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(Wed);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Thu).equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(Thu);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Friday).equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(Friday);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));
    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_6() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Wed).equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(Wed);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Thu).equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(Thu);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Friday).equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(Friday);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Saturday).equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(Saturday);

        assertTrue(lessons.get(lesson0).equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(lesson1).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

    }

    @Test
    public void testParseTimetableAPPallWeekWithDoubleLessons_7() throws Exception {

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week").timeout(0).get();

        TableWrapper tableWrapper = DataUtils.parseDocument(document, "4 АПП-2");

        assertTrue(tableWrapper.getTimeTable() != null);

        HashMap<TableWrapper.Day, HashMap<TableWrapper.Lesson, String>> timeTable = tableWrapper.getTimeTable();

        assertTrue(timeTable.keySet().size() > 0);
        assertTrue(timeTable.keySet().contains(Mon));
        assertTrue(tableWrapper.getDays().get(Mon).equals("П О Н Е Д Е Л Ь Н И К   11.09.2017"));

        HashMap<TableWrapper.Lesson, String> lessons = timeTable.get(Mon);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Tue).equals("В Т О Р Н И К   12.09.2017"));
        lessons = timeTable.get(Tue);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Иностранный язык Коршунова Н.Е. /Галиева И.В."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Wed).equals("С Р Е Д А   13.09.2017"));
        lessons = timeTable.get(Wed);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("\u00A0"));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                + "\n/\n" + "Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson4).equals("Физическая культура Кайниев А.А."));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Thu).equals("Ч Е Т В Е Р Г   14.09.2017"));
        lessons = timeTable.get(Thu);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Friday).equals("П Я Т Н И Ц А   15.09.2017"));
        lessons = timeTable.get(Friday);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Saturday).equals("С У Б Б О Т А   16.09.2017"));
        lessons = timeTable.get(Saturday);

        assertTrue(lessons.get(lesson0).equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(lesson1).equals("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И."));
        assertTrue(lessons.get(lesson2).equals("Экономика организации Давыдова А.С."));
        assertTrue(lessons.get(lesson3).equals("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

        assertTrue(tableWrapper.getDays().get(Mon2).equals("П О Н Е Д Е Л Ь Н И К   18.09.2017"));
        lessons = timeTable.get(Mon2);

        assertTrue(lessons.get(lesson0).equals("\u00A0"));
        assertTrue(lessons.get(lesson1).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson2).equals("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson3).equals("Экономика организации Давыдова А.С."
                + "\n/\n" + "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."));
        assertTrue(lessons.get(lesson4).equals("\u00A0"));
        assertTrue(lessons.get(lesson5).equals("\u00A0"));
        assertTrue(lessons.get(lesson6).equals("\u00A0"));

    }
}