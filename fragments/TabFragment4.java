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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smartstudio.sajmovi.eu.CalendarFullActivity;
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
public class TabFragment4 extends ListFragment {

    ArrayList<CalendarData> valuess = new ArrayList<CalendarData>();
    ArrayList<CalendarData> kalendar_header_values = new ArrayList<CalendarData>();
    private static final String TAG = SearchResultsActivity.class.getSimpleName();

    public JSONArray kalendararray;
    JSONObject kalendar;
    private ProgressDialog pDialog;
    //URL makers
    public static final String ARG_SECTION_NUMBER = "section_number";
    private LayoutInflater inflater;
    public Integer calendarId;
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

        View rootView = inflater.inflate(R.layout.kalendar_tab_fragment_4,
                container, false);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Učitavanje...");
        //pDialog.show();
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        int CalendarId = bundle.getInt("CalendarId");
        String countrylist = bundle.getString("conutrylist");
        String serverURL = "http://.........php?id="+CalendarId+countrylist;
        Log.v("SIMILAR URL", serverURL);

        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        //Log.v("RESPONE", "###" + response + "###");
                        try {
                            JSONArray kalendararray = response.getJSONArray("KALENDARARRAY");
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
                        hidePDialog();
                        // Parsing json
                        /* Put collected data into adapter */
                        CalendarAdapter adapter = new CalendarAdapter(getActivity(),valuess);
                /* Set list to adapter */
                        setListAdapter(adapter);
                /*On listItems click get CalendarFullActivity*/
                        OnclickGetKalendarItems();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
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
                getActivity().finish();


                //startActivityForResult(intent, 0);
            }
        });
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
