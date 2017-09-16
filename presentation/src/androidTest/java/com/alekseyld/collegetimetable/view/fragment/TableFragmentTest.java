package com.alekseyld.collegetimetable.view.fragment;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.TableWrapper;
import com.alekseyld.collegetimetable.Utils;
import com.alekseyld.collegetimetable.utils.DataUtils;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.alekseyld.collegetimetable.view.adapter.TableAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Alekseyld on 16.09.2017.
 */
public class TableFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);



    @Test
    public void testTimeTableAppWithDoubleLesson1() throws Exception{

        Document document = Jsoup.connect("https://alekseyld.github.io/CollegeTimetable/timetable_app_days_1").timeout(0).get();
        final TableWrapper timeTableAppWithDoubleLesson1 = DataUtils.parseDocument(document, "3 АПП-1");

        activityTestRule.launchActivity(new Intent());

        final RecyclerView recyclerView = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recView);
        final TextView message = (TextView) activityTestRule.getActivity().findViewById(R.id.message);

        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                message.setVisibility(View.GONE);
                recyclerView.setAdapter(new TableAdapter(timeTableAppWithDoubleLesson1));
            }
        });

        Utils.sleepEspresso(200, TimeUnit.MILLISECONDS);

        assertTrue(recyclerView.getAdapter().getItemCount() == 1);

    }
}