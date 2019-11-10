package com.smartstudio.sajmovi.eu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.holders.NewsData;

import java.util.List;

/**
 * Created by Boris on 5.6.2015..
 */
public class HotelAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NewsData> newsDataItems;

    public HotelAdapter(Activity activity, List<NewsData> newsDataItems) {
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
            convertView = inflater.inflate(R.layout.calendar_hotels_items, null);

        TextView nazivHotela = (TextView) convertView.findViewById(R.id.hotel_name);
        // getting news data for the row
        NewsData m = newsDataItems.get(position);
        // Naziv hotela
        nazivHotela.setText(m.getNaslov());

        return convertView;
    }

}
