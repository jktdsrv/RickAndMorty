package com.platzigram.rickandmorty.login.repository;

import com.platzigram.rickandmorty.login.presenter.LoginPresenter;

public class LoginRepositoryImpl implements LoginRepository {

    LoginPresenter loginPresenter;

    public LoginRepositoryImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void signIn(String name, String password) {
        boolean success = true;
        if (success) {
            loginPresenter.loginSuccess();

        } else {
            loginPresenter.loginFailed("OMG!");
        }

    }
}
