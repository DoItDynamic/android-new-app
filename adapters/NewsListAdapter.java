package com.smartstudio.sajmovi.eu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.function.CircleImageView;
import com.smartstudio.sajmovi.eu.holders.NewsData;

import java.util.List;

/**
 * Created by Boris on 5.6.2015..
 */
public class NewsListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NewsData> newsDataItems;

    public ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public NewsListAdapter(Activity activity, List<NewsData> newsDataItems) {
        this.activity = activity;
        this.newsDataItems = newsDataItems;
    }

    @Override
    public int getCount() {
        return newsDataItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsDataItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.v("ON GETview", "*********************** ON GETview :  " + position + "  *************************");
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.news_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        CircleImageView thumbNail = (CircleImageView) convertView
                .findViewById(R.id.topthumbnail);
        //ImageView thumbNail = (ImageView) convertView.findViewById(R.id.topthumbnail);
        TextView naslov = (TextView) convertView.findViewById(R.id.naslov);
        TextView datum = (TextView) convertView.findViewById(R.id.datum);
        TextView nadnaslov = (TextView) convertView.findViewById(R.id.nadnaslov);
        TextView rubrika = (TextView) convertView.findViewById(R.id.sazetak);
        // getting news data for the row
        NewsData m = newsDataItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        // naslov
        naslov.setText(m.getNaslov());
        //nadnaslov
        nadnaslov.setText(m.getNadnaslov());
        //rubrika
        rubrika.setText(m.getRubrika());
        //datum
        datum.setText(String.valueOf(m.getDatum()));

        //Log.v("convertView", "*********************** convertView :  " + convertView + "  *************************");
        return convertView;
    }

}
