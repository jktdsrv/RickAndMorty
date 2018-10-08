package com.platzigram.rickandmorty.login.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
    public void signIn(String name, String password, final Activity activity, FirebaseAuth firebaseAuth) {

        firebaseAuth.signInWithEmailAndPassword(name,password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    SharedPreferences preferences = activity.getSharedPreferences("USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", user.getEmail());
                    editor.putString("name", user.getDisplayName());
                    editor.apply();

                    loginPresenter.loginSuccess();
                } else {
                    loginPresenter.loginFailed("Ocurrió un error");
                }
            }
        });
    }
}
