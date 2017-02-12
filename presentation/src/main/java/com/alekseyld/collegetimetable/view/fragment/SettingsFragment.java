package com.alekseyld.collegetimetable.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.SettingsPresenter;
import com.alekseyld.collegetimetable.view.SettingsView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alekseyld.collegetimetable.repository.base.SettingsRepository.FAVORITEGROUPS_KEY;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;

/**
 * Created by Alekseyld on 04.09.2016.
 */

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @BindView(R.id.addFarvorite)
    TextView addFarvorite;

    @BindView(R.id.addNotif)
    TextView addNotif;

    @BindView(R.id.alarmMode)
    Switch alarmMode;

    @BindView(R.id.notifOn)
    Switch notifOn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.action_settings);

        addFarvorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFavoriteDialog();
            }
        });

        addNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNotif();
            }
        });

        alarmMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveAlarmMode(
                        alarmMode.isChecked()
                );
            }
        });

        notifOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.saveNotifOn(
                        notifOn.isChecked()
                );
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            addFarvorite.setBackgroundResource(outValue.resourceId);
            addNotif.setBackgroundResource(outValue.resourceId);
        }

        return v;
    }

    @Override
    public void presenterReady() {
        alarmMode.setChecked(
                mPresenter.getAlarmMode()
        );

        notifOn.setChecked(
                mPresenter.getNotifOn()
        );
    }

    private void showAddNotif(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dialog_notification, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final AutoCompleteTextView autoTextView =
                (AutoCompleteTextView) promptView.findViewById(R.id.autoTextView);

        Resources res = getResources();
        autoTextView.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, res.getStringArray(R.array.groupsList)));//simple_spinner_item

        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresenter.saveNotification(autoTextView.getText());
                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.show();
    }

    private void showAddFavoriteDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.dialog_favorite, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

         String json = getActivity()
                .getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE)
                .getString(FAVORITEGROUPS_KEY, "");

        Set<String> json1= new Gson().fromJson(json,
                new TypeToken<Set<String>>(){}.getType());
        final Set<String> chosenGroups;
        if(json1 != null) {
            chosenGroups = json1;
        }else {
            chosenGroups = new HashSet<>();
        }

        Resources res = getResources();
        final String[] list = res.getStringArray(R.array.groupsList);
        final ListView listGroup = (ListView) promptView.findViewById(R.id.listGroup);
        listGroup.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_multiple_choice, list));
        listGroup.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if(!chosenGroups.isEmpty())
            for(int i = 0; i < list.length; i++){
                if(chosenGroups.contains(list[i])){
                    listGroup.setItemChecked(i, true);
                }else {
                    listGroup.setItemChecked(i, false);
                }
            }

        listGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
            int position, long id) {
                SparseBooleanArray chosen = ((ListView) parent).getCheckedItemPositions();
                for (int i = 0; i < chosen.size(); i++) {
                    if (chosen.valueAt(i)) {
                        if(!chosenGroups.contains(list[chosen.keyAt(i)])) {
                            chosenGroups.add(list[chosen.keyAt(i)]);
                        }
                    }else {
                        if(chosenGroups.contains(list[chosen.keyAt(i)])) {
                            chosenGroups.remove(list[chosen.keyAt(i)]);
                        }
                    }
                }
            }
        });

        alertDialogBuilder.setCancelable(true)
                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mPresenter.saveFavorite(chosenGroups);
                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alert.show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }

}
