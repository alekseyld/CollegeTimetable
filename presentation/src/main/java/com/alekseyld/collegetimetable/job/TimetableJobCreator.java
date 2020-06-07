package com.alekseyld.collegetimetable.job;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class TimetableJobCreator implements JobCreator {

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case TimetableJob.TAG:
                return new TimetableJob();
            case RecursiveJob.TAG:
                return new RecursiveJob();
            default:
                return null;
        }
    }
}
