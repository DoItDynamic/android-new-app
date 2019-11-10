package com.smartstudio.sajmovi.eu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.smartstudio.sajmovi.eu.adapters.NewsListAdapter;
import com.smartstudio.sajmovi.eu.dialog.about;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.holders.NewsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewsListActivity extends ActionBarActivity {

    //URL definition s with helper variable url2 for conditions
    private static String url2;
    private static String url =null;

    // Log tag
    private static final String TAG = HomeActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<NewsData> newsDataList = new ArrayList<NewsData>();

    private ListView listView;
    private NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        //action bar support
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        listView = (ListView) findViewById(R.id.list);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //geting intent from HomeActivity
        Intent intent = getIntent();
        //get value from intent
        int message = intent.getIntExtra("button_id", -1);

        //----------------choose between vijesti;najave;reportaze----------------------

        if(message==1) {//vijesti
            url2="http://www..............json_final.php?cat=4";
            getSupportActionBar().setTitle("Vijesti");
        }//if
        else if (message==2){//najave
            url2="http://www...............json_final.php?cat=6";
            getSupportActionBar().setTitle("Najave");
        }//else
        else if (message==3){//reportaze
            url2="http://www...............json_final.php?cat=2";
            getSupportActionBar().setTitle("Reportaže");
        }//else

        adapter = new NewsListAdapter(this, newsDataList);

        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Učitavanje...");
        pDialog.show();

        // Creating volley request obj
        url = url2;

        JsonArrayRequest jsonReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject obj = response.getJSONObject(i);
                                NewsData newsData = new NewsData();
                                newsData.setNaslov(obj.getString("naslov"));
                                newsData.setId(obj.getInt("id"));
                                newsData.setNadnaslov(obj.getString("nadnaslov"));
                                newsData.setThumbnailUrl(obj.getString("image"));
                                newsData.setDatum(obj.getString("datum"));
                                newsData.setRubrika(obj.getString("sazetak"));
                                // adding newsData to movies array
                                newsDataList.add(newsData);
                                
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
        //setting onItemClickListener
        ListView list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), NewsFullActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("ListItem_ID", newsDataList.get(position).getId());
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_nosearchnoexit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
            case R.id.about:
                new about(this);
                break;
            case R.id.izlaz:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
