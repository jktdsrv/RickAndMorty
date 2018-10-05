package com.platzigram.rickandmorty.login.repository;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginRepository {
    void signIn(String name, String password, Activity activity, FirebaseAuth firebaseAuth);

}