package com.smartstudio.sajmovi.eu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.function.AppController;

/**
 * Created by Boris on 5.6.2015..
 */
public class FullNewsFragment extends android.support.v4.app.Fragment {
    public ImageView galerija;
    public ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_full_news,
                container, false);
        Bundle WebData = getArguments();

        NetworkImageView thumbNail = (NetworkImageView)rootView.findViewById(R.id.galerija_image);
        thumbNail.setImageUrl(WebData.getString("galerija_URL"), imageLoader);

        return rootView;
    }
}