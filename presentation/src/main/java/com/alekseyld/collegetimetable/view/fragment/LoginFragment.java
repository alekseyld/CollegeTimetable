package com.alekseyld.collegetimetable.view.fragment;

import android.content.Intent;
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

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.LoginComponent;
import com.alekseyld.collegetimetable.presenter.LoginPresenter;
import com.alekseyld.collegetimetable.view.LoginView;
import com.alekseyld.collegetimetable.view.activity.MainActivity;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alekseyld on 02.01.2018.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

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
                //todo click action_settings
                showError("Еще не реализовано");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loginSuccessfully() {
        getBaseActivity().startActivity(new Intent(getBaseActivity(), MainActivity.class));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void initializeInjections() {
        getComponent(LoginComponent.class).inject(this);
    }

}
