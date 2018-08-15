package com.platzigram.rickandmorty.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platzigram.rickandmorty.R;
import com.platzigram.rickandmorty.adapter.PictureAdapterRecyclerView;
import com.platzigram.rickandmorty.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container,false);
        RecyclerView picturesRecycler = view.findViewById(R.id.pictureRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);
        return view;
    }

    public ArrayList<Picture> buildPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://static.shoplightspeed.com/shops/609903/files/002580469/my-dog-small-weimaraner.jpg","Vincent Restrepo","4 días","3 Me Gusta"));
        pictures.add(new Picture("http://static.shoplightspeed.com/shops/609903/files/002575392/holiday-scarf-s.jpg","Julián González","2 días","10 Me Gusta"));
        pictures.add(new Picture("https://images.freeimages.com/images/large-previews/981/cow-1380252.jpg","Carlos Sánchez","6 días","5 Me Gusta"));
        return pictures;
    }


}
