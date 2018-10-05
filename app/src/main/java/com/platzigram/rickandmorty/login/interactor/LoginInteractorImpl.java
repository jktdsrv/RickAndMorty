package com.platzigram.rickandmorty.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.platzigram.rickandmorty.login.presenter.LoginPresenter;
import com.platzigram.rickandmorty.login.repository.LoginRepository;
import com.platzigram.rickandmorty.login.repository.LoginRepositoryImpl;

public class LoginInteractorImpl implements LoginInteractor{

    private LoginRepository loginRepository;

    private LoginPresenter loginPresenter;

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        loginRepository = new LoginRepositoryImpl(loginPresenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginRepository.signIn(username, password, activity, firebaseAuth);
    }
}
