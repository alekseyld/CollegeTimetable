package com.alekseyld.collegetimetable.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class TimetableJobCreator implements JobCreator {

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case TimetableJob.TAG:
                return new TimetableJob();
            default:
                return null;
        }
    }
}
