package com.smartstudio.sajmovi.eu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.holders.CalendarData;

import java.util.List;

/**
 * Created by Boris on 5.6.2015..
 */
public class CalendarAdapter extends BaseAdapter {
    private Activity context;
    private LayoutInflater inflater;
    private List<CalendarData> values;
    public ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    //private String[] adapterDataItems;
    public CalendarAdapter(Activity context, List<CalendarData> values) {
        //super();
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int location) {
        return values.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.calendar_pagerview_list_items, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView kalendar_flag = (NetworkImageView) convertView
                .findViewById(R.id.kalendar_flag);

        TextView kalendar_naziv = (TextView) convertView.findViewById(R.id.kalendar_naziv);
        TextView kalendar_opis = (TextView) convertView.findViewById(R.id.kalendar_opis);
        TextView event_start = (TextView) convertView.findViewById(R.id.event_start);
        TextView event_end = (TextView) convertView.findViewById(R.id.event_end);

        //TextView kalendar_status = (TextView) convertView.findViewById(R.id.kalendar_status);
        TextView kalendar_city = (TextView) convertView.findViewById(R.id.kalendar_city);
        //TextView kalendar_country = (TextView) convertView.findViewById(R.id.kalendar_country);
        //ImageView kalendar_flag = (ImageView) convertView.findViewById(R.id.kalendar_flag);
        TextView brojac = (TextView) convertView.findViewById(R.id.calendar_counter);

        // getting news data for the row
        CalendarData m = values.get(position);
        //Log.v("M", "###### "+m+"######");
        // naslov
        kalendar_naziv.setText(m.getKalendar_naziv());
        //nadnaslov
        kalendar_opis.setText(m.getKalendar_opis());
        //rubrika
        event_start.setText(m.getEvent_start());
        //datum
        event_end.setText(m.getEvent_end());
        //status
        //kalendar_status.setText(m.getKalendar_status());
        //city
        kalendar_city.setText(m.getKalendar_city());
        //country
        //kalendar_country.setText(m.getKalendar_country());
        //flag
        kalendar_flag.setImageUrl(m.getKalendar_flag(), imageLoader);
        //brojac
        brojac.setText(String.valueOf(m.getBrojac()));

        return convertView;
    }
    public void clearData() {
        // clear the data
        this.values.clear();
    }

}