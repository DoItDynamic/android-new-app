package com.smartstudio.sajmovi.eu.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartstudio.sajmovi.eu.R;
import com.smartstudio.sajmovi.eu.SearchResultsActivity;
import com.smartstudio.sajmovi.eu.function.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/**
 * Created by Boris on 5.6.2015..
 */
public class TabFragment2 extends Fragment implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, View.OnClickListener {

    // Constant for defining latitude and longitude
    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    static LatLng Pozicija ;
    String mapinfo;
    // GoogleMap class
    private GoogleMap googleMap;
    static FragmentActivity Fractivity;
    private View view;
    private Context context;
    public String title;
    public static GoogleMap map = null;
    public TabFragment2() {
        this.context = getActivity();
    }
    List<Address> Coordinates;
    double LATITUDE = 0.0;
    double LONGITUDE = 0.0;
    final int maxResult =1;
    TextView tvLocInfo;
    boolean markerClicked;
    Button btnInFragment;
    Boolean btnInFrag;
    Marker marker;
    TextView mapinfo_text;
    int CalendarId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.kalendar_tab_fragment_2, container, false);
        Bundle bundle = getArguments();
        String city = bundle.getString("city");
        String country_name = bundle.getString("country_name");
        int koordinate = bundle.getInt("koordinate");
        int koordinate_tmp = bundle.getInt("koordinate_tmp");
        CalendarId = bundle.getInt("CalendarId");
        Double langitude = bundle.getDouble("langitude");
        Double longitude = bundle.getDouble("longitude");
        Double langitude_tmp = bundle.getDouble("langitude_tmp");
        Double longitude_tmp = bundle.getDouble("longitude_tmp");
        String naziv_sajma = bundle.getString("naziv_sajma");
        Log.v("GET_BUNDLE", "#####  " + city +","+koordinate+"  #####");
        btnInFragment = (Button) view.findViewById(R.id.FragButton);
        // Obtain the MapFragment and set the async listener to be notified when the map is ready.
        try {
            if (googleMap == null) {
                googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();

            }
            /*Ukoliko koordinate nisu dostupne, dohvati koordinate prema nazivu grada, mjesta*/
            if(koordinate == 2 && koordinate_tmp == 2 ) {
                String MyPlace = city+","+country_name;
                Geocoder gc = new Geocoder(getActivity(), Locale.ENGLISH);

                Coordinates = gc.getFromLocationName(MyPlace, maxResult);
                Address address = Coordinates.get(0);
                    if (Coordinates.size() > 0) {
                        langitude = address.getLatitude();
                        longitude = address.getLongitude();
                        Pozicija = new LatLng(langitude, longitude);
                        mapinfo = getResources().getString(R.string.mapinfo_no);
                        btnInFragment.setVisibility(View.VISIBLE);
                        title = "Nepoznata lokacija";
                    }else{
                        mapinfo = getResources().getString(R.string.mapinfo_null);
                        title = "Nepoznata lokacija";
                        Pozicija = new LatLng(LATITUDE, LONGITUDE);
                    }
            /*Lokaciju koju je unijeo korisnik*/
            }else if(koordinate == 2 && koordinate_tmp == 1){
                Pozicija = new LatLng(langitude_tmp, longitude_tmp);
                mapinfo = getResources().getString(R.string.mapinfo_user);
                btnInFragment.setVisibility(View.INVISIBLE);
                title = getResources().getString(R.string.mapinfo_user);
            }
            else{
                Pozicija = new LatLng(langitude, longitude);
                mapinfo = getResources().getString(R.string.mapinfo_yes);
                btnInFragment.setVisibility(View.INVISIBLE);
                title = naziv_sajma;
            }
            mapinfo_text = (TextView) view.findViewById(R.id.mapinfo);
            mapinfo_text.setText(mapinfo);

            //Show a satellite map with roads
            //MAP_TYPE_NORMAL: Basic map with roads.
            //MAP_TYPE_SATELLITE: Satellite view with roads.
            //MAP_TYPE_TERRAIN: Terrain view without roads.
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            // Place dot on current location
            googleMap.setMyLocationEnabled(true);

            // Turns traffic layer on
            googleMap.setTrafficEnabled(true);

            // Enables indoor maps
            googleMap.setIndoorEnabled(true);

            // Turns on 3D buildings
            googleMap.setBuildingsEnabled(true);


            // Show Zoom buttons
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            // Create a marker in the map at a given position with a title
            marker = googleMap.addMarker(new MarkerOptions().
                    position(Pozicija).title(title));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Pozicija, 14));
            marker.showInfoWindow();
            //Log.v("POZICIJA->", "##" + Pozicija + "##");
            //marker.setDraggable(true);
            //googleMap.setOnMapLongClickListener();
            //googleMap.setOnMapLongClickListener();
            googleMap.setOnMapClickListener(this);
           // googleMap.setOnMapLongClickListener(this);
            googleMap.setOnMarkerDragListener(this);

            markerClicked = false;

            btnInFrag = false;
            btnInFragment.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FragButton:
                if(!btnInFrag) {
                    btnInFragment.setText("Završi s postavljanjem");
                    marker.setDraggable(true);
                    marker.setTitle("Premjesti oznaku na točnu lokaciju");
                    marker.showInfoWindow();
                    btnInFrag = true;
                }else{
                    btnInFragment.setText("Ispravi lokaciju");
                    marker.setDraggable(false);
                    marker.setTitle("Spremanje...");
                    //mapinfo_text.setText("Lokacija postavljena");
                    marker.showInfoWindow();
                    btnInFrag = false;
                    String NewPosition = marker.getPosition().toString();
                    Double latitude = marker.getPosition().latitude;
                    Double longitude = marker.getPosition().longitude;
                    SendNewPosition(CalendarId, latitude.toString(),longitude.toString());
                }
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onMapClick(LatLng point) {
        tvLocInfo.setText(point.toString());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));

        markerClicked = false;
    }

   /* @Override
    public void onMapLongClick(LatLng point) {
        //tvLocInfo.setText("New marker added@" + point.toString());
        googleMap.addMarker(new MarkerOptions()
                .position(point)
                .draggable(true));

        markerClicked = false;
    }
*/
    @Override
    public void onMarkerDrag(Marker marker) {
        //tvLocInfo.setText("Marker Drag@" + marker.getPosition());
        //Log.v("DRAG DRAG", marker.getPosition().toString() );
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //tvLocInfo.setText("Marker " + marker.getPosition() + " DragEnd");
        //Log.v("DRAG STOP", marker.getPosition().toString() );
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //tvLocInfo.setText("Marker " + marker.getPosition() + " DragStart");
        //Log.v("DRAG START", "START");
    }
    public void SendNewPosition(int CalendarId, String lat, String lng){
        String serverURL = "http://........json_map_position.php?id="+CalendarId+"&lat="+lat+"&lng="+lng;
        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONObject odgovor = response.getJSONObject("odgovor");
                            marker.hideInfoWindow();
                            marker.setTitle(response.getString("odgovor"));
                            mapinfo_text.setText(response.getString("odgovor"));
                            marker.showInfoWindow();

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);
    }
}
