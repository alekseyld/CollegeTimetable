package com.alekseyld.collegetimetable.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.entity.User;
import com.alekseyld.collegetimetable.internal.di.component.LoginComponent;
import com.alekseyld.collegetimetable.presenter.LoginPresenter;
import com.alekseyld.collegetimetable.view.LoginView;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;
import static com.alekseyld.collegetimetable.repository.base.TableRepository.NAME_FILE;
import static com.alekseyld.collegetimetable.repository.base.UserRepository.USER_KEY;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @BindView(R.id.linearlayout)
    LinearLayout linearLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.login)
    MaterialEditText loginEditText;

    @BindView(R.id.password)
    MaterialEditText passwordEditText;

    @BindView(R.id.enter_button)
    Button enterButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(getString(R.string.login_title));

        enterButton.getBackground().setColorFilter(0xFF4CAF50, PorterDuff.Mode.MULTIPLY);

        setHasOptionsMenu(true);

        //fixme
        loginEditText.setText("Alekseyld");
        passwordEditText.setText("12345678");

        return v;
    }

    @OnClick(R.id.enter_button)
    void enterClick(){
        mPresenter.login(
                loginEditText.getText().toString(),
                passwordEditText.getText().toString()
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_login, menu);

        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                getBaseActivity().replaceFragment(SettingsFragment.newInstance(true));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loginSuccessfully() {
        getBaseActivity().startActivity(new Intent(getBaseActivity(), MainActivity.class));
    }

    @Override
    public void loginDemonstrative() {
        //FIXME Этого метода быть не должно

        User user = new User().setAuthKey("db182d2552835bec774847e06406bfa2")
                .setGroup("3 АПП-1")
                .setName("Алексей")
                .setSurname("Лысов")
                .setPatronymic("Дмитриевич")
                .setStudentId("4708");

        SharedPreferences preferences = getBaseActivity().getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        String json = new Gson().toJson(user);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(USER_KEY, json);
        ed.apply();

        loginSuccessfully();
        showError("Авторизация в демонстрационном режиме. Некоторые функции могут не работать.");
    }

    @Override
    public void showLoading() {
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initializeInjections() {
        getComponent(LoginComponent.class).inject(this);
    }

}
