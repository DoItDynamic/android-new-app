package com.smartstudio.sajmovi.eu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.SearchResultsActivity;
import com.smartstudio.sajmovi.eu.adapters.HotelAdapter;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.holders.NewsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 5.6.2015..
 */
public class TabFragment3 extends ListFragment {

    private List<NewsData> newsDataList = new ArrayList<NewsData>();
    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    HotelAdapter adapter;
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

        View rootView = inflater.inflate(R.layout.kalendar_tab_fragment_3,
                container, false);
        /* Put collected data into adapter */
        adapter = new HotelAdapter(getActivity(),newsDataList);
                        /* Set list to adapter */
        setListAdapter(adapter);

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
        String serverURL = "http://................php?id="+CalendarId;
        //Log.v("SIMILAR URL", serverURL);

        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        //Log.v("RESPONE", "###" + response + "###");
                        try {
                            JSONArray kalendararray = response.getJSONArray("HOTELARRAY");
                            for (int i = 0; i < kalendararray.length(); i++) {
                                JSONObject kalendar_object = kalendararray.getJSONObject(i);
                                /******* Fetch node values **********/
                                NewsData newsData = new NewsData();
                                newsData.setNaslov(kalendar_object.getString("naziv"));
                                newsData.setThumbnailUrl(kalendar_object.getString("url"));
                                // Log.v("JSON", "OK"+kalendar_object.getString("naziv"));
                                newsDataList.add(newsData);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hidePDialog();
                        // Parsing json

                        adapter.notifyDataSetChanged();
                        OnclickGoUrl();

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
    //Intent for fullkalendar
    public void OnclickGoUrl(){
        ListView list = getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsData m = newsDataList.get(position);
                //Log.v("ONCKICK", m.getThumbnailUrl());
                String Hotelurl = m.getThumbnailUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Hotelurl));
                startActivity(browserIntent);
            }
        });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
