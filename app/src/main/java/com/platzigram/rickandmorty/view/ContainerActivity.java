package com.platzigram.rickandmorty.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platzigram.rickandmorty.R;
import com.platzigram.rickandmorty.login.view.CreateAccountActivity;
import com.platzigram.rickandmorty.login.view.LoginActivity;
import com.platzigram.rickandmorty.post.view.HomeFragment;
import com.platzigram.rickandmorty.view.fragment.ProfileFragment;
import com.platzigram.rickandmorty.view.fragment.SearchFragment;

public class ContainerActivity extends AppCompatActivity {

    private static final String TAG = "ContainerActivity";
    private BottomNavigationView bottomNavigationView;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        fireBaseInitilize();

        HomeFragment anotherHomeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, anotherHomeFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.search:
                        SearchFragment searchFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;

                }

                return false;
            }
        });
    }

    private void fireBaseInitilize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "Usuario logueado" + firebaseUser.getEmail());
                } else {
                    Log.w(TAG, "Usuario NO logueado");
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sign_out:
                firebaseAuth.signOut();
                Toast.makeText(this, "Se cerró la sesión", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ContainerActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_about:
                Toast.makeText(this, "RickAndMorty by Vince", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
