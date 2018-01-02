package com.alekseyld.collegetimetable.view.fragment;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.TimeTable;
import com.alekseyld.collegetimetable.utils.DataUtils;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.alekseyld.collegetimetable.R.id.lesson_name;
import static com.alekseyld.collegetimetable.R.id.recView;
import static com.alekseyld.collegetimetable.utils.Utils.withDayLesson;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Alekseyld on 16.09.2017.
 */

@RunWith(AndroidJUnit4.class)
public class TableFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    private static TimeTable timeTableAppAllWeek;

    private static TimeTable timeTableAppWithDouble7;
    private static TimeTable timeTableAppWithDouble6;
    private static TimeTable timeTableAppWithDouble5;
    private static TimeTable timeTableAppWithDouble4;
    private static TimeTable timeTableAppWithDouble3;
    private static TimeTable timeTableAppWithDouble2;
    private static TimeTable timeTableAppWithDouble1;

    @BeforeClass
    public static void initClass() throws IOException {
        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week").timeout(0).get();
        timeTableAppAllWeek = DataUtils.parseDocument(document, "3 АПП-1");
        timeTableAppWithDouble7 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1").timeout(0).get();
        timeTableAppWithDouble1 = DataUtils.parseDocument(document, "3 АПП-1");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2").timeout(0).get();
        timeTableAppWithDouble2 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3").timeout(0).get();
        timeTableAppWithDouble3 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4").timeout(0).get();
        timeTableAppWithDouble4 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5").timeout(0).get();
        timeTableAppWithDouble5 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6").timeout(0).get();
        timeTableAppWithDouble6 = DataUtils.parseDocument(document, "4 АПП-2");
    }

    private void setTestTimeTable(final TimeTable testTimeTable) {
        activityTestRule.launchActivity(new Intent());

        final RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recView);
        final TextView message = (TextView) activityTestRule.getActivity().findViewById(R.id.message);

        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                message.setVisibility(View.GONE);
                recyclerView.setAdapter(new TableAdapter(activityTestRule.getActivity(), testTimeTable));
            }
        });

        Utils.sleepEspresso(1, TimeUnit.MICROSECONDS);
//        Utils.sleepEspresso(10, TimeUnit.SECONDS);
    }

    @Test
    public void testViewTimetableAPPallWeek() throws Exception {
        setTestTimeTable(timeTableAppAllWeek);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 7);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Основные процессы и технологии ТЭК Корепанова И.А.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("Иностранный язык Захарова И.В. /Кусякова Л.Ф.")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Компьютерное моделирование Гадельбаева Р.А.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(2));

        onView(withDayLesson(2)
                .atTablePosition(R.id.date))
                .check(matches(withText("С р е д а   13.09.2017")));

        onView(withDayLesson(2)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("Физическая культура Федяева Ю.В.")));
        onView(withDayLesson(2)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(2)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(3));

        onView(withDayLesson(3)
                .atTablePosition(R.id.date))
                .check(matches(withText("Ч е т в е р г   14.09.2017")));

        onView(withDayLesson(3)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(3)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.")));
        onView(withDayLesson(3)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Основные процессы и технологии ТЭК Корепанова И.А.")));
        onView(withDayLesson(3)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.05.01. Теоретические основы обеспечения надежности систем автоматизации и модулей мехатронных систем Милованов А.С.")));
        onView(withDayLesson(3)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(4));

        onView(withDayLesson(4)
                .atTablePosition(R.id.date))
                .check(matches(withText("П я т н и ц а   15.09.2017")));

        onView(withDayLesson(4)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("Компьютерное моделирование Гадельбаева Р.А.")));
        onView(withDayLesson(4)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(4)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(5));

        onView(withDayLesson(5)
                .atTablePosition(R.id.date))
                .check(matches(withText("С у б б о т а   16.09.2017")));

        onView(withDayLesson(5)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("Основы философии Вершинина Н.П.")));
        onView(withDayLesson(5)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.04.01. Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Милованова М.И.")));
        onView(withDayLesson(5)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(5)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(6));

        onView(withDayLesson(6)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   18.09.2017")));

        onView(withDayLesson(6)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(6)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(6)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Основные процессы и технологии ТЭК Корепанова И.А.")));
        onView(withDayLesson(6)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(6)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(6)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(6)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_1() throws Exception {
        setTestTimeTable(timeTableAppWithDouble1);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 1);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.01.03. Теоретические основы контроля и анализа функционирования систем автоматического управления Сагдеева Г.А.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Основные процессы и технологии ТЭК Корепанова И.А.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));
    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_2() throws Exception {
        setTestTimeTable(timeTableAppWithDouble2);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 2);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Иностранный язык Коршунова Н.Е. /Галиева И.В.")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_3() throws Exception {
        setTestTimeTable(timeTableAppWithDouble3);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 3);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Иностранный язык Коршунова Н.Е. /Галиева И.В.")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(2));

        onView(withDayLesson(2)
                .atTablePosition(R.id.date))
                .check(matches(withText("С р е д а   13.09.2017")));

        onView(withDayLesson(2)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                        + "\n/\n" + "Экономика организации Давыдова А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Физическая культура Кайниев А.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_4() throws Exception {
        setTestTimeTable(timeTableAppWithDouble4);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 4);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Иностранный язык Коршунова Н.Е. /Галиева И.В.")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(2));

        onView(withDayLesson(2)
                .atTablePosition(R.id.date))
                .check(matches(withText("С р е д а   13.09.2017")));

        onView(withDayLesson(2)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                        + "\n/\n" + "Экономика организации Давыдова А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Физическая культура Кайниев А.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(3));

        onView(withDayLesson(3)
                .atTablePosition(R.id.date))
                .check(matches(withText("Ч е т в е р г   14.09.2017")));

        onView(withDayLesson(3)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(3)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_5() throws Exception {
        setTestTimeTable(timeTableAppWithDouble5);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 5);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Иностранный язык Коршунова Н.Е. /Галиева И.В.")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(2));

        onView(withDayLesson(2)
                .atTablePosition(R.id.date))
                .check(matches(withText("С р е д а   13.09.2017")));

        onView(withDayLesson(2)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                        + "\n/\n" + "Экономика организации Давыдова А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Физическая культура Кайниев А.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(3));

        onView(withDayLesson(3)
                .atTablePosition(R.id.date))
                .check(matches(withText("Ч е т в е р г   14.09.2017")));

        onView(withDayLesson(3)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(3)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(4));

        onView(withDayLesson(4)
                .atTablePosition(R.id.date))
                .check(matches(withText("П я т н и ц а   15.09.2017")));

        onView(withDayLesson(4)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));
    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_6() throws Exception {
        setTestTimeTable(timeTableAppWithDouble6);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 6);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Иностранный язык Коршунова Н.Е. /Галиева И.В.")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(2));

        onView(withDayLesson(2)
                .atTablePosition(R.id.date))
                .check(matches(withText("С р е д а   13.09.2017")));

        onView(withDayLesson(2)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                        + "\n/\n" + "Экономика организации Давыдова А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Физическая культура Кайниев А.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(3));

        onView(withDayLesson(3)
                .atTablePosition(R.id.date))
                .check(matches(withText("Ч е т в е р г   14.09.2017")));

        onView(withDayLesson(3)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(3)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(4));

        onView(withDayLesson(4)
                .atTablePosition(R.id.date))
                .check(matches(withText("П я т н и ц а   15.09.2017")));

        onView(withDayLesson(4)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(5));

        onView(withDayLesson(5)
                .atTablePosition(R.id.date))
                .check(matches(withText("С у б б о т а   16.09.2017")));

        onView(withDayLesson(5)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.")));
        onView(withDayLesson(5)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(5)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(5)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.")));
        onView(withDayLesson(5)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

    }

    @Test
    public void viewTestTimetableAPPallWeekWithDoubleLessons_7() throws Exception {
        setTestTimeTable(timeTableAppWithDouble7);

        RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(recView);
        assertTrue(recyclerView.getAdapter().getItemCount() == 7);

        onView(withDayLesson(0)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   11.09.2017")));

        onView(withDayLesson(0)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(0)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(0)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(0)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(1));

        onView(withDayLesson(1)
                .atTablePosition(R.id.date))
                .check(matches(withText("В т о р н и к   12.09.2017")));

        onView(withDayLesson(1)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(1)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(1)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Иностранный язык Коршунова Н.Е. /Галиева И.В.")));
        onView(withDayLesson(1)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(1)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(2));

        onView(withDayLesson(2)
                .atTablePosition(R.id.date))
                .check(matches(withText("С р е д а   13.09.2017")));

        onView(withDayLesson(2)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С."
                        + "\n/\n" + "Экономика организации Давыдова А.С.")));
        onView(withDayLesson(2)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("Физическая культура Кайниев А.А.")));
        onView(withDayLesson(2)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(2)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(3));

        onView(withDayLesson(3)
                .atTablePosition(R.id.date))
                .check(matches(withText("Ч е т в е р г   14.09.2017")));

        onView(withDayLesson(3)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(3)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(3)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(3)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(4));

        onView(withDayLesson(4)
                .atTablePosition(R.id.date))
                .check(matches(withText("П я т н и ц а   15.09.2017")));

        onView(withDayLesson(4)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.02.01.Теоретические основы организации монтажа, ремонта, наладки систем автоматического управления, средств измерений и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(4)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(4)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(5));

        onView(withDayLesson(5)
                .atTablePosition(R.id.date))
                .check(matches(withText("С у б б о т а   16.09.2017")));

        onView(withDayLesson(5)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.")));
        onView(withDayLesson(5)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.04.02. Теоретические основы разработки и моделирования отдельных несложных модулей и мехатронных систем Милованова М.И.")));
        onView(withDayLesson(5)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С.")));
        onView(withDayLesson(5)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("МДК.04.01.Теоретические основы разработки и моделирования несложных систем автоматизации с учетом специфики технологических процессов Давлетшина Э.Р.")));
        onView(withDayLesson(5)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(5)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

        onView(withId(recView))
                .perform(scrollToPosition(6));

        onView(withDayLesson(6)
                .atTablePosition(R.id.date))
                .check(matches(withText("П о н е д е л ь н и к   18.09.2017")));

        onView(withDayLesson(6)
                .atLessonPosition(0, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(6)
                .atLessonPosition(1, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(6)
                .atLessonPosition(2, lesson_name))
                .check(matches(withText("МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(6)
                .atLessonPosition(3, lesson_name))
                .check(matches(withText("Экономика организации Давыдова А.С."
                        + "\n/\n" + "МДК.03.01.Теоретические основы технического обслуживания и эксплуатации автоматических и мехатронных систем управления Милованов А.С.")));
        onView(withDayLesson(6)
                .atLessonPosition(4, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(6)
                .atLessonPosition(5, lesson_name))
                .check(matches(withText("\u00A0")));
        onView(withDayLesson(6)
                .atLessonPosition(6, lesson_name))
                .check(matches(withText("\u00A0")));

    }

}