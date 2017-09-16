package com.alekseyld.collegetimetable.view.fragment;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.utils.DataUtils;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.alekseyld.collegetimetable.utils.Utils.withRecyclerView;

/**
 * Created by Alekseyld on 16.09.2017.
 */
public class TableFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    private static TableWrapper timeTableAppAllWeek;

    private static TableWrapper timeTableAppWithDoubleLesson7;
    private static TableWrapper timeTableAppWithDoubleLesson6;
    private static TableWrapper timeTableAppWithDoubleLesson5;
    private static TableWrapper timeTableAppWithDoubleLesson4;
    private static TableWrapper timeTableAppWithDoubleLesson3;
    private static TableWrapper timeTableAppWithDoubleLesson2;
    private static TableWrapper timeTableAppWithDoubleLesson1;

    @BeforeClass
    public static void initClass() throws IOException {
        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_all_week").timeout(0).get();
        timeTableAppAllWeek = DataUtils.parseDocument(document, "3 АПП-1");
        timeTableAppWithDoubleLesson7 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1").timeout(0).get();
        timeTableAppWithDoubleLesson1 = DataUtils.parseDocument(document, "3 АПП-1");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_2").timeout(0).get();
        timeTableAppWithDoubleLesson2 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_3").timeout(0).get();
        timeTableAppWithDoubleLesson3 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_4").timeout(0).get();
        timeTableAppWithDoubleLesson4 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_5").timeout(0).get();
        timeTableAppWithDoubleLesson5 = DataUtils.parseDocument(document, "4 АПП-2");

        document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_6").timeout(0).get();
        timeTableAppWithDoubleLesson6 = DataUtils.parseDocument(document, "4 АПП-2");
    }

    private void setTestTimeTable(final TableWrapper testTimeTable){
        activityTestRule.launchActivity(new Intent());

        final RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recView);
        final TextView message = (TextView) activityTestRule.getActivity().findViewById(R.id.message);

        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                message.setVisibility(View.GONE);
                recyclerView.setAdapter(new TableAdapter(testTimeTable));
            }
        });

        Utils.sleepEspresso(100, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testTimeTableAppWithDoubleLesson1() throws Exception{

        setTestTimeTable(timeTableAppWithDoubleLesson1);

        onView(withRecyclerView(R.id.recView)
                .atPositionOnView(0, R.id.lesson0))
                .check(matches(withText("МДК.01.02. Методы осуществления стандартных и сертификационных испытаний, метрологических поверок средств измерений Баймухаметова Ю.У.")));

    }
}