package com.platzigram.rickandmorty.post.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.platzigram.rickandmorty.R;
import com.platzigram.rickandmorty.RickAndMortyApplication;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {

    private ImageView imgPhoto;
    private Button btnCreatePost;
    private String photoPath;
    private RickAndMortyApplication app;
    private StorageReference storageReference;
    private final String TAG = "NewPostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
// Se debe inicializar el App
        app = (RickAndMortyApplication) getApplicationContext();

        storageReference = app.getStorageReference();

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);

        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        if(getIntent().getExtras() != null){
            photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });



    }

    private void uploadPhoto() {
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();

        Bitmap bitmap = imgPhoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] photoByte = baos.toByteArray();
        String photoName = photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length());

        final StorageReference photoReference = storageReference.child("postImages" + "/" + photoName);

        UploadTask uploadTask = photoReference.putBytes(photoByte);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Photo upload error " + e.toString());
                e.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // photo upload success
                photoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uriPhoto){
                        String photoURL = uriPhoto.toString();
                        Toast.makeText(app, "Fotografía cargada con éxito", Toast.LENGTH_SHORT).show();
                        Log.w(TAG,"URL Photo > " + photoURL);
                    }
                });
                //destruyo el activity luego de subir la foto
                finish();
            }
        });

    }

    private void showPhoto(){
        Picasso.with(this).load(photoPath).into(imgPhoto);
    }
}
