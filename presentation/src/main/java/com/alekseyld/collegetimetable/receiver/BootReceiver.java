package com.alekseyld.collegetimetable.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alekseyld.collegetimetable.service.UpdateTimetableService;

public class BootReceiver extends BroadcastReceiver
{
    public BootReceiver(){}
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent ishintent = new Intent(context, UpdateTimetableService.class);
        context.startService(ishintent);
    }
}
