package com.platzigram.rickandmorty.login.view;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platzigram.rickandmorty.R;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateAccountActivity";
    private FirebaseAuth fireBaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button btnJoinUs;
    private TextInputEditText tietEmail, tietPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        showToolbar(getResources().getString(R.string.toolbar_title_createaccount),true);

        btnJoinUs    = (Button) findViewById(R.id.join_us);
        tietEmail    = (TextInputEditText) findViewById(R.id.email);
        tietPassword = (TextInputEditText) findViewById(R.id.password_createaccount);



        //Este método va al método Json y se conecta con FireBase
        fireBaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "Usuario logueado" + firebaseUser.getEmail());
                    Log.w("TAG", "Usuario logueado" + firebaseUser.getDisplayName());
                } else {
                    Log.w("TAG", "Usuario NO logueado");
                }
            }
        };

        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createAccount();

            }
        });
    }

    private void createAccount() {
        String email = tietEmail.getText().toString();
        String password = tietPassword.getText().toString();
        fireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccountActivity.this,"Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccountActivity.this,"Ocurrió un error al crear la cuenta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
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
}
