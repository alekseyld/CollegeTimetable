package com.alekseyld.collegetimetable.view.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.view.fragment.SettingsFavoriteFragment;
import com.alekseyld.collegetimetable.view.fragment.SettingsFragment;
import com.alekseyld.collegetimetable.view.widget.GroupInputWidget;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class GroupInputDialogFragment extends DialogFragment {

    private boolean teacherMode = false;

    public static GroupInputDialogFragment newInstance(boolean isFavorite, boolean teacherMode){
        GroupInputDialogFragment fragment = new GroupInputDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("isFavorite", isFavorite);
        bundle.putBoolean("teacherMode", teacherMode);

        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_group, null);

        final GroupInputWidget groupInputWidget =
                (GroupInputWidget) v.findViewById(R.id.group_widget);
        final EditText teacherInput = (EditText) v.findViewById(R.id.teacher_input);

        teacherMode = getArguments().getBoolean("teacherMode");

        if (teacherMode){
            groupInputWidget.setVisibility(View.GONE);
            teacherInput.setVisibility(View.VISIBLE);
        }

        String positiveText = "Сохранить";

        if (getArguments().getBoolean("isFavorite")){
            positiveText = "Добавить";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (teacherMode) {
                            ((SettingsFragment)getTargetFragment()).saveNotification(teacherInput.getText().toString());
                            return;
                        }

                        if (getTargetFragment() instanceof SettingsFragment){
                            ((SettingsFragment)getTargetFragment()).saveNotification(groupInputWidget.getGroup());
                        } else if (getTargetFragment() instanceof SettingsFavoriteFragment){
                            ((SettingsFavoriteFragment)getTargetFragment()).addFavoriteGroup(groupInputWidget.getGroup());
                        }

                    }
                })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }

}
