package com.alekseyld.collegetimetable.utils;

import org.junit.Test;

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

        assertTrue(DataUtils.getGroupUrl("2 АПП-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_11.html"));
        assertTrue(DataUtils.getGroupUrl("1 Т-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_8.html"));
        assertTrue(DataUtils.getGroupUrl("1 Э-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_7.html"));
        assertTrue(DataUtils.getGroupUrl("2 С-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_10.html"));
        assertTrue(DataUtils.getGroupUrl("2 Б-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_9.html"));
        assertTrue(DataUtils.getGroupUrl("2 В-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_4.html"));
        assertTrue(DataUtils.getGroupUrl("2 Л-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_3.html"));
        assertTrue(DataUtils.getGroupUrl("2 Р-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_4.html"));
        assertTrue(DataUtils.getGroupUrl("2 БНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТО-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_3.html"));
        assertTrue(DataUtils.getGroupUrl("2 ПНГ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_5.html"));
        assertTrue(DataUtils.getGroupUrl("2 ЭНН-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html"));
        assertTrue(DataUtils.getGroupUrl("1 ЭННУ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_6.html"));
        assertTrue(DataUtils.getGroupUrl("2 ТОВ-1").equals("http://uecoll.ru/wp-content/uploads/time/neft/10_1_7.html"));
        assertTrue(DataUtils.getGroupUrl("2 ИС-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_1.html"));
        assertTrue(DataUtils.getGroupUrl("2 ГС-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("3 ГСУ-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_2.html"));
        assertTrue(DataUtils.getGroupUrl("2 РУ-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_4.html"));
        assertTrue(DataUtils.getGroupUrl("3 ПГ-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_5.html"));
        assertTrue(DataUtils.getGroupUrl("1 ТС-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_6.html"));
        assertTrue(DataUtils.getGroupUrl("4 ТАК-1").equals("http://uecoll.ru/wp-content/uploads/time/energy/10_1_8.html"));
    }

    @Test
    public void parseDocument() throws Exception {

    }

}