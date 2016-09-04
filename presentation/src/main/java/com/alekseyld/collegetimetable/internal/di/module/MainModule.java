package com.alekseyld.collegetimetable.internal.di.module;

import com.alekseyld.collegetimetable.internal.di.PerActivity;
import com.alekseyld.collegetimetable.repository.TableRepositoryImpl;
import com.alekseyld.collegetimetable.repository.base.TableRepository;
import com.alekseyld.collegetimetable.service.TableService;
import com.alekseyld.collegetimetable.service.TableServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alekseyld on 02.09.2016.
 */

@Module
public class MainModule {

    @PerActivity @Provides
    TableService provideTableService(TableServiceImpl tableService){
        return tableService;
    }

    @PerActivity @Provides
    TableRepository provideTableRepository(TableRepositoryImpl tableRepository){
        return tableRepository;
    }
}
