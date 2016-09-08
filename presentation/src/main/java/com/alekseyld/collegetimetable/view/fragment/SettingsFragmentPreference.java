package com.alekseyld.collegetimetable.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.view.activity.base.BaseActivity;

/**
 * Created by Alekseyld on 08.09.2016.
 */

public class SettingsFragmentPreference extends PreferenceFragmentCompat {

    public static SettingsFragmentPreference newInstance(){
        return new SettingsFragmentPreference();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
        ((BaseActivity)getActivity()).getActionBarBase().setDisplayHomeAsUpEnabled(true);
    }
}
