package com.platzigram.rickandmorty.login.presenter;

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
    public void signIn(String username, String password) {

        loginView.disableInputs();
        loginView.showProgressBar();
        loginInteractor.signIn(username, password);
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
