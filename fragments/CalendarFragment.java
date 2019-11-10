package com.smartstudio.sajmovi.eu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smartstudio.sajmovi.eu.CalendarFullActivity;
import com.smartstudio.sajmovi.eu.CalendarListActivity;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.SearchResultsActivity;
import com.smartstudio.sajmovi.eu.adapters.CalendarAdapter;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.holders.CalendarData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Boris on 5.6.2015..
 */
public class CalendarFragment extends ListFragment {

    ArrayList<CalendarData> valuess = new ArrayList<CalendarData>();
    ArrayList<CalendarData> kalendar_header_values = new ArrayList<CalendarData>();
    private static final String TAG = SearchResultsActivity.class.getSimpleName();

    public JSONArray kalendararray;
    JSONObject  kalendar;
    private ProgressDialog pDialog;
    //URL makers
    public String LRPosition;
    public TextView kalendar_mjesec;
    public Integer pageNumber;
    public static final String ARG_SECTION_NUMBER = "section_number";
    private LayoutInflater inflater;
    public Integer calendarId;
    CalendarAdapter adapter;
    ProgressBar pb;
    RelativeLayout layout;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calendar_pagerview_list,
                container, false);
        kalendar_mjesec = (TextView) rootView.findViewById(R.id.kalendar_mjesec);
        adapter = new CalendarAdapter(getActivity(),valuess);
        setListAdapter(adapter);
        pb = (ProgressBar) rootView.findViewById(R.id.pbLoading_progress);
        layout = (RelativeLayout) rootView.findViewById(R.id.pbLoading);
        layout.setVisibility(View.VISIBLE);

        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        Integer pagenumber =  bundle.getInt("PageNumber");
        String pageposition = bundle.getString("PagePosition");
        String country = bundle.getString("Country");

        String serverURL = "http://.....t.php?pagenumber="+pagenumber+"&pageposition="+pageposition+country;
        Log.v("URL", serverURL);
        //new GetFromWebParse().execute(serverURL);

        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray pagedata_array = response.getJSONArray("PAGEDATA");
                            JSONObject pagedata_object = pagedata_array.getJSONObject(0);
                            CalendarListActivity.pageNumber = pagedata_object.getInt("pagenumber");
                            kalendar_mjesec.setText(pagedata_object.getString("kalendar_mjesec"));

                            JSONArray kalendararray = response.getJSONArray("KALENDARARRAY");
                           // Log.v("kalendararray", "###" + kalendararray + "###");
                            //JSONArray jsonMainNode1 = jsonResponse.optJSONArray("KALENDARARRAY");
                            for (int i = 0; i < kalendararray.length(); i++) {
                                JSONObject kalendar_object = kalendararray.getJSONObject(i);
                                /******* Fetch node values **********/
                                CalendarData adapterData = new CalendarData();
                                adapterData.setKalendar_naziv(kalendar_object.getString("naziv"));
                                adapterData.setIdCalendar(kalendar_object.getInt("id"));
                                adapterData.setKalendar_opis(kalendar_object.getString("opis"));
                                adapterData.setEvent_start(kalendar_object.getString("event_start"));
                                adapterData.setEvent_end(kalendar_object.getString("event_end"));
                                adapterData.setKalendar_status(kalendar_object.getString("status"));
                                adapterData.setKalendar_city(kalendar_object.getString("city"));
                                adapterData.setKalendar_country(kalendar_object.getString("country_name"));
                                adapterData.setKalendar_flag(kalendar_object.getString("country_flag"));
                                adapterData.setBrojac(i + 1);
                                // Log.v("JSON", "OK"+kalendar_object.getString("naziv"));
                                valuess.add(adapterData);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Parsing json
                        /* Put collected data into adapter */
                        adapter.notifyDataSetChanged();
                        // run a background job and once complete
                        layout.setVisibility(View.GONE);
                        OnclickGetKalendarItems();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //Intent for fullkalendar
    public void OnclickGetKalendarItems(){

        try {
            ListView list = getListView();
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), CalendarFullActivity.class);
                    CalendarData m = valuess.get(position);
                    calendarId = m.getIdCalendar();
                    Bundle extras = new Bundle();
                    extras.putInt("calendarItem_ID", calendarId);
                    intent.putExtras(extras);
                    startActivityForResult(intent, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}