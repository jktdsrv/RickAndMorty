package com.platzigram.rickandmorty.login.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platzigram.rickandmorty.R;
import com.platzigram.rickandmorty.login.presenter.LoginPresenter;
import com.platzigram.rickandmorty.login.presenter.LoginPresenterImpl;
import com.platzigram.rickandmorty.view.ContainerActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText username, password;
    private Button login;
    private LoginPresenter loginPresenter;
    private ProgressBar progressBarLogin;

    private static final String TAG = "LoginActivity";
    private FirebaseAuth fireBaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Este método va al método Json y se conecta con FireBase
        fireBaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w("TAG", "Usuario logueado " + firebaseUser.getEmail());
                    goHome();
                } else {
                    Log.w("TAG", "Usuario NO logueado");
                }
            }
        };

        username =  findViewById(R.id.username);
        password =  findViewById(R.id.password);
        login =  findViewById(R.id.login);
        progressBarLogin =  findViewById(R.id.progressbarLogin);
        loginPresenter = new LoginPresenterImpl(this);
        hideProgressBar();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ToDo Hacer validaciones


                signIn(username.getText().toString(), password.getText().toString());

            }
        });
    }

    private void signIn(String username, String password) {
        loginPresenter.signIn(username, password, this, fireBaseAuth);
    }


    public void goCreateAccount(View view){
        goCreateAccount();
    }


    public void goURL(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"));
        startActivity(intent);
    }

    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fireBaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        fireBaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, getString(R.string.login_error) + " " + error, Toast.LENGTH_SHORT).show();
    }


}
