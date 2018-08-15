package com.platzigram.rickandmorty.login.presenter;

public interface LoginPresenter {

    void signIn(String username, String password); // Interactor
    void loginSuccess();
    void loginFailed(String error);



}
