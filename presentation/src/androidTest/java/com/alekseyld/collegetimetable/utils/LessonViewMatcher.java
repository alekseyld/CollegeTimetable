package com.alekseyld.collegetimetable.utils;

import android.content.res.Resources;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alekseyld.collegetimetable.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Alekseyld on 16.09.2017.
 */

public class LessonViewMatcher {

    private final int tableRecycleViewId = R.id.recView;
    private final int dayPos;
    private final int lessonRecycleViewId = R.id.lesson_list;

    public LessonViewMatcher(int dayPos){
        this.dayPos = dayPos;
    }

    public Matcher<View> atLessonPosition(final int lessonPos, final int viewId){
        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(viewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(viewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                new Object[] { Integer.valueOf
                                        (viewId) });
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            (RecyclerView) view.getRootView().findViewById(tableRecycleViewId);
                    if (recyclerView != null && recyclerView.getId() == tableRecycleViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(dayPos).itemView;
                    }
                    else {
                        return false;
                    }
                }

                RecyclerView lessonRecycleView = (RecyclerView) childView.findViewById(lessonRecycleViewId);
                View lessonView = lessonRecycleView.findViewHolderForAdapterPosition(lessonPos).itemView;

                if (viewId == -1) {
                    return view == lessonView;
                } else {
                    View targetView = lessonView.findViewById(viewId);
                    return view == targetView;
                }

            }
        };
    }

    public Matcher<View> atTablePosition(final int viewId){
        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(viewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(viewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                new Object[] { Integer.valueOf
                                        (viewId) });
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                            (RecyclerView) view.getRootView().findViewById(tableRecycleViewId);
                    if (recyclerView != null && recyclerView.getId() == tableRecycleViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(dayPos).itemView;
                    }
                    else {
                        return false;
                    }
                }

                if (viewId == -1) {
                    return view == childView;
                } else {
                    View targetView = childView.findViewById(viewId);
                    return view == targetView;
                }

            }
        };
    }

}
