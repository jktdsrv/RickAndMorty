package com.platzigram.rickandmorty.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.platzigram.rickandmorty.login.interactor.LoginInteractor;
import com.platzigram.rickandmorty.login.interactor.LoginInteractorImpl;
import com.platzigram.rickandmorty.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl(this);     }


    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {

        loginView.disableInputs();
        loginView.showProgressBar();
        loginInteractor.signIn(username, password, activity, firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();
        loginView.hideProgressBar();
    }

    @Override
    public void loginFailed(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }
}
