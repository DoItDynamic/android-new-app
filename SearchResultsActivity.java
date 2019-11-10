package com.smartstudio.sajmovi.eu;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.smartstudio.sajmovi.eu.adapters.CalendarAdapter;
import com.smartstudio.sajmovi.eu.dialog.about;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.holders.CalendarData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchResultsActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    ArrayList<CalendarData> adapterDataList = new ArrayList<CalendarData>();
    public Integer calendarId;
    ListView listView;
    CalendarAdapter adapter;
    private static final String TAG = SearchResultsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        /* Set list to adapter */
        listView = (ListView) findViewById(android.R.id.list);
        adapter = new CalendarAdapter(this, adapterDataList);

        //action back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Intent intent = getIntent();
        String searchvalue = intent.getExtras().getString("searchvalue");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            new GetSearchResult(query);
        }else if(searchvalue != null){
            new GetSearchResult(searchvalue);Log.v("SEARCHED-2", searchvalue);
        }

        pDialog = new ProgressDialog(this);
        //Showing progress dialog before making http request
        pDialog.setMessage("Pretraživanje u tijeku...");
        pDialog.show();

    }

    @Override
    protected void onNewIntent(Intent intent) {

        //Intent SearchIntent = getIntent();
        String searchvalue = intent.getExtras().getString("searchvalue");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            new GetSearchResult(query);
        }else if(searchvalue != null){
            new GetSearchResult(searchvalue);
        }
    }


    //Intent for fullkalendar
    public void OnclickGetKalendarItems(){
        //ListView list = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), CalendarFullActivity.class);
                CalendarData m = adapterDataList.get(position);
                calendarId = m.getIdCalendar();
                Bundle extras = new Bundle();
                extras.putInt("calendarItem_ID", calendarId);
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });
    }
    /*#########################################################################################
    ##############################       Option menu      #####################################
    ###########################################################################################
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_noexit, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    public class GetSearchResult{
        public GetSearchResult(String query){
            String serverURL = "http://.......search.php?query="+query;

            JsonArrayRequest jsonReq = new JsonArrayRequest(serverURL,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();
                            // Parsing json
                            adapterDataList.clear();
                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    JSONObject SearchedResult = response.getJSONObject(i);
                                    /******* Fetch node values **********/
                                    CalendarData adapterData = new CalendarData();
                                    adapterData.setKalendar_naziv(SearchedResult.getString("naziv"));
                                    adapterData.setIdCalendar(SearchedResult.getInt("id"));
                                    adapterData.setKalendar_opis(SearchedResult.getString("opis"));
                                    adapterData.setEvent_start(SearchedResult.getString("event_start"));
                                    adapterData.setEvent_end(SearchedResult.getString("event_end"));
                                    adapterData.setKalendar_status(SearchedResult.getString("status"));
                                    adapterData.setKalendar_city(SearchedResult.getString("city"));
                                    adapterData.setKalendar_country(SearchedResult.getString("country_name"));
                                    adapterData.setKalendar_flag(SearchedResult.getString("country_flag"));
                                    adapterData.setBrojac(i+1);

                                    adapterDataList.add(adapterData);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            listView.setAdapter(adapter);
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
            case R.id.o_aplikaciji:
                new about(this);
                break;
            case R.id.izlaz:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}