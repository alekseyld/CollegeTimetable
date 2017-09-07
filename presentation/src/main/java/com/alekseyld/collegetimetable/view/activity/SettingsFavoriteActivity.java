package com.alekseyld.collegetimetable.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.DaggerSettingsFavoriteComponent;
import com.alekseyld.collegetimetable.internal.di.component.SettingsFavoriteComponent;
import com.alekseyld.collegetimetable.internal.di.module.MainModule;
import com.alekseyld.collegetimetable.view.activity.base.BaseInjectorActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 07.09.2017.
 */

public class SettingsFavoriteActivity extends BaseInjectorActivity<SettingsFavoriteComponent> {

    @BindView(R.id.listGroup)
    RecyclerView listGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.settings_favorite_activity_title);
        setContentView(R.layout.activity_settings_favorite);
        ButterKnife.bind(this);


    }

    private void showAddFavoriteDialog(){

//        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//        View promptView = layoutInflater.inflate(R.layout.dialog_favorite, null);
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setView(promptView);
//
//         String json = getActivity()
//                .getSharedPreferences(NAME_FILE, Context.MODE_PRIVATE)
//                .getString(FAVORITEGROUPS_KEY, "");
//
//        Set<String> json1= new Gson().fromJson(json,
//                new TypeToken<Set<String>>(){}.getType());
//        final Set<String> chosenGroups;
//        if(json1 != null) {
//            chosenGroups = json1;
//        }else {
//            chosenGroups = new HashSet<>();
//        }
//
//        Resources res = getResources();
//        final String[] list = res.getStringArray(R.array.groupsList);
//        final ListView listGroup = (ListView) promptView.findViewById(R.id.listGroup);
//        listGroup.setAdapter(new ArrayAdapter<>(getContext(),
//                android.R.layout.simple_list_item_multiple_choice, list));
//        listGroup.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        if(!chosenGroups.isEmpty())
//            for(int i = 0; i < list.length; i++){
//                if(chosenGroups.contains(list[i])){
//                    listGroup.setItemChecked(i, true);
//                }else {
//                    listGroup.setItemChecked(i, false);
//                }
//            }
//
//        listGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v,
//            int position, long id) {
//                SparseBooleanArray chosen = ((ListView) parent).getCheckedItemPositions();
//                for (int i = 0; i < chosen.size(); i++) {
//                    if (chosen.valueAt(i)) {
//                        if(!chosenGroups.contains(list[chosen.keyAt(i)])) {
//                            chosenGroups.add(list[chosen.keyAt(i)]);
//                        }
//                    }else {
//                        if(chosenGroups.contains(list[chosen.keyAt(i)])) {
//                            chosenGroups.remove(list[chosen.keyAt(i)]);
//                        }
//                    }
//                }
//            }
//        });
//
//        alertDialogBuilder.setCancelable(true)
//                .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        mPresenter.saveFavorite(chosenGroups);
//                    }
//                })
//                .setNegativeButton("Отмена",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//
//        alert.show();
    }

    @Override
    protected SettingsFavoriteComponent initializeInjections() {
        return DaggerSettingsFavoriteComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(new MainModule(this))
                .build();
    }
}
