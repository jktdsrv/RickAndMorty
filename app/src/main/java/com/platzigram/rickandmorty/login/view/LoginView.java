package com.platzigram.rickandmorty.login.view;

public interface LoginView {

    void goCreateAccount();
    void goHome();
    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();
    void loginError(String error);

}
