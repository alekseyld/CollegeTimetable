package com.alekseyld.collegetimetable.repository;

import android.app.IntentService;

import com.alekseyld.collegetimetable.repository.base.BaseSettingsRepository;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 05.11.2016.
 */

public class ServiceSettingsRepository extends BaseSettingsRepository {

    private IntentService mService;

    @Inject
    ServiceSettingsRepository(IntentService service){
        super(service.getSharedPreferences(NAME_FILE, MODE_PRIVATE));
        mService = service;
    }
}
