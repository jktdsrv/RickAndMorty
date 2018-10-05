package com.platzigram.rickandmorty.login.repository;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platzigram.rickandmorty.login.presenter.LoginPresenter;

public class LoginRepositoryImpl implements LoginRepository {

    LoginPresenter loginPresenter;



    public LoginRepositoryImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void signIn(String name, String password, Activity activity, FirebaseAuth firebaseAuth) {

        firebaseAuth.signInWithEmailAndPassword(name,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginPresenter.loginSuccess();
                } else {
                    loginPresenter.loginFailed("Ocurrió un error");
                }
            }
        });
    }
}
