package com.platzigram.rickandmorty.post.view;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.platzigram.rickandmorty.R;
import com.platzigram.rickandmorty.RickAndMortyApplication;
import com.squareup.picasso.Picasso;

public class PictureDetailActivity extends AppCompatActivity {

    private ImageView imageHeader;
    private RickAndMortyApplication app;
    private StorageReference storageReference;
    private String photoName = "JPEG_20181008_16-57-25_1048516241.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        app = (RickAndMortyApplication) getApplicationContext();
        storageReference = app.getStorageReference();

        imageHeader = (ImageView) findViewById(R.id.image_header);

        showToolbar("",true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
            getWindow().setStatusBarColor(getColor(android.R.color.transparent));
        }

        showData();



    }

    private void showData() {
        storageReference.child("postImages/" + photoName)
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(PictureDetailActivity.this).load(uri.toString()).into(imageHeader);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PictureDetailActivity.this, "Ocurrió un error al traer la foto", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }


    public void showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
    }
}
