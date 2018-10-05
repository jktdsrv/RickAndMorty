package com.platzigram.rickandmorty.post.view;


import android.content.Intent;
import android.graphics.Camera;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.platzigram.rickandmorty.R;
import com.platzigram.rickandmorty.adapter.PictureAdapterRecyclerView;
import com.platzigram.rickandmorty.model.Picture;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.menu_home), false, view);
        RecyclerView picturesRecycler = view.findViewById(R.id.pictureRecycler);

        fabCamera = view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);


        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();

            }
        });

        return view;
    }

    private void takePicture() {
        String packageName = getActivity().getPackageName();
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null){

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (Exception e){
                e.printStackTrace();
            }

            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(), packageName, photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imageFileName, ".jpg", storageDir);
        photoPathTemp = "file:" + photo.getAbsolutePath();
        return photo;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("vince2", "CAMERA OK!! :)");
            Intent intent = new Intent(getActivity(), NewPostActivity.class);
            intent.putExtra("PHOTO_PATH_TEMP", photoPathTemp);
            startActivity(intent);
        }
    }

    public ArrayList<Picture> buildPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://static.shoplightspeed.com/shops/609903/files/002580469/my-dog-small-weimaraner.jpg","Vincent Restrepo","4 días","3 Me Gusta"));
        pictures.add(new Picture("http://static.shoplightspeed.com/shops/609903/files/002575392/holiday-scarf-s.jpg","Julián González","2 días","10 Me Gusta"));
        pictures.add(new Picture("https://images.freeimages.com/images/large-previews/981/cow-1380252.jpg","Carlos Sánchez","6 días","5 Me Gusta"));
        return pictures;
    }


    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
