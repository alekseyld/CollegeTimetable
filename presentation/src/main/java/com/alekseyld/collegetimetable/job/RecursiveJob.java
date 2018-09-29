package com.alekseyld.collegetimetable.job;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.alekseyld.collegetimetable.repository.base.SettingsRepository;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.utils.Utils;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.Set;

public class RecursiveJob extends Job {
    public static final String TAG = "RecursiveJob_Tag";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(TimetableJob.TAG);
        SharedPreferences preferences = getContext().getSharedPreferences(TableRepository.NAME_FILE, Context.MODE_PRIVATE);
        if (preferences.getBoolean(SettingsRepository.NOTIFON_KEY, false) &&
                jobRequests.size() == 0) {
            Utils.getTimeTableJob().schedule();
        }
        return Result.SUCCESS;
    }
}
