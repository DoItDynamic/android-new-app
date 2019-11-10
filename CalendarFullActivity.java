package com.smartstudio.sajmovi.eu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smartstudio.sajmovi.eu.dialog.about;
import com.smartstudio.sajmovi.eu.fragments.TabFragment1;
import com.smartstudio.sajmovi.eu.fragments.TabFragment2;
import com.smartstudio.sajmovi.eu.fragments.TabFragment3;
import com.smartstudio.sajmovi.eu.fragments.TabFragment4;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.sliders.SlidingTabLayout;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Prostudio on 5.6.2015..
 */
public class CalendarFullActivity extends ActionBarActivity {
    public int CalendarId;

    public int koordinate;
    public int koordinate_tmp;
    public String city;
    public String country_name;
    public String naziv_sajma;
    public Double langitude = 0.0;
    public Double longitude = 0.0;
    public Double langitude_tmp = 0.0;
    public Double longitude_tmp = 0.0;
    public FragmentManager fragmentManager;
    String MyCountry;
    public ViewPager viewPager;
    private static final String TAG = CalendarFullActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_full);

        // Layout manager that allows the user to flip through the pages
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Intent i = getIntent();
        CalendarId = i.getIntExtra("calendarItem_ID",-1);

        //ActionBar home back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        SharedPreferences settings = this.getSharedPreferences("datacountry", Context.MODE_PRIVATE);
        boolean country1 = settings.getBoolean("country1", true);
        boolean country2 = settings.getBoolean("country2", true);
        boolean country3 = settings.getBoolean("country3", true);
        boolean country4 = settings.getBoolean("country4", true);
        boolean country5 = settings.getBoolean("country5", true);
        boolean country6 = settings.getBoolean("country6", true);
        MyCountry = "&c1="+country1+"&c2="+country2+"&c3="+country3+"&c4="+country4+"&c5="+country5+"&c6="+country6+"";
        //pDialog = new ProgressDialog(this);
        //pDialog.setMessage("Učitavanje...");
        //pDialog.show();
        String serverURL = "http://........php?id="+CalendarId+"&data=basicdata";
        //Log.v("URL", "##" + serverURL + "##");

        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        //hidePDialog();
                        try {

                            koordinate = response.getInt("koordinate");
                            koordinate_tmp = response.getInt("koordinate_tmp");
                            city = response.getString("city");
                            country_name = response.getString("country_name");
                            naziv_sajma = response.getString("naziv");
                            if(koordinate == 1){
                                langitude = response.getDouble("langitude");
                                longitude = response.getDouble("longitude");
                            }
                            if(koordinate_tmp == 1){
                                langitude_tmp = response.getDouble("langitude_tmp");
                                longitude_tmp = response.getDouble("longitude_tmp");
                            }
                           // Log.v("JSON->", "##" + response + "##");

                            // getSupportFragmentManager allows use to interact with the fragments
                            // MyFragmentPagerAdapter will return a fragment based on an index that is passed
                            viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),
                                    CalendarFullActivity.this));
                            viewPager.setOffscreenPageLimit(3);
                            // Initialize the Sliding Tab Layout
                            SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
                            // Connect the viewPager with the sliding tab layout
                            slidingTabLayout.setViewPager(viewPager);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


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
    /*#####################################################################
    * ## TabFragmentAdapter ###############################################
    * #####################################################################*/
    public class TabFragmentAdapter extends FragmentPagerAdapter {

        // Holds tab titles
        Resources res = getResources();
        private String[] tabTitles = res.getStringArray(R.array.tabfragment);
        private Context context;

        public TabFragmentAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return 4;
        }

        // Return the correct Fragment based on index
        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt("CalendarId", CalendarId);
            args.putInt("koordinate", koordinate);
            args.putString("city", city);
            args.putString("country_name", country_name);
            args.putString("conutrylist",MyCountry);
            args.putDouble("langitude", langitude);
            args.putDouble("longitude", longitude);
            args.putInt("koordinate_tmp", koordinate_tmp);
            args.putDouble("langitude_tmp", langitude_tmp);
            args.putDouble("longitude_tmp", longitude_tmp);
            args.putString("naziv_sajma",naziv_sajma);
            Log.v("ARGS", "##" + args + "##");
            if(position == 0){
                Fragment fragment1 = new TabFragment1();
                fragment1.setArguments(args);
                return fragment1;
            } else if(position == 1) {
                Fragment fragment2 = new TabFragment2();
                fragment2.setArguments(args);
                return fragment2;
            } else if(position == 2) {
                Fragment fragment3 = new TabFragment3();
                fragment3.setArguments(args);
                return fragment3;
            } else if(position == 3) {
                Fragment fragment4 = new TabFragment4();
                fragment4.setArguments(args);
                return fragment4;
            }

            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Return the tab title to SlidingTabLayout
            return tabTitles[position];
        }
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_nosearchnoexit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.about:
                new about(this);
                break;
            case R.id.izlaz:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
