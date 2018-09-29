package com.alekseyld.collegetimetable.presenter;

import android.content.SharedPreferences;

import com.alekseyld.collegetimetable.job.RecursiveJob;
import com.alekseyld.collegetimetable.job.TimetableJob;
import com.alekseyld.collegetimetable.presenter.base.BasePresenter;
import com.alekseyld.collegetimetable.utils.DataUtils;
import com.alekseyld.collegetimetable.view.AboutView;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Alekseyld on 05.11.2016.
 */

public class AboutPresenter extends BasePresenter<AboutView> {

    SharedPreferences mSharedPreferences;

    @Inject
    public AboutPresenter(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public String getDebugText() {
        StringBuilder keys = new StringBuilder();

        for (String key: mSharedPreferences.getAll().keySet())
            keys.append("-").append(key).append("\n");

        Set<JobRequest> timetableJob = JobManager.instance().getAllJobRequestsForTag(TimetableJob.TAG);
        String timetableJobDate = "";
        if (timetableJob.iterator().hasNext()) {
            timetableJobDate = DataUtils.dateFormat.format(new Date(timetableJob.iterator().next().getScheduledAt()));
        }

        Set<JobRequest> recursiveJob = JobManager.instance().getAllJobRequestsForTag(RecursiveJob.TAG);
        String recursiveJobDate = "";
        if (recursiveJob.iterator().hasNext()) {
            recursiveJobDate = DataUtils.dateFormat.format(new Date(recursiveJob.iterator().next().getLastRun()));
        }

        return  "TimetableJob count = "+ timetableJob.size()
                + "\n TimetableJob next at = " + timetableJobDate
                + "\n RecursiveJobDate count = " + recursiveJob.size()
                + "\n RecursiveJobDate last = " +  recursiveJobDate
                + "\n\n " + "Объекты в базе данных: "
                + "\n"  + keys.toString();
    }

}
