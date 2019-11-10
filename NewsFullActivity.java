package com.smartstudio.sajmovi.eu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.smartstudio.sajmovi.eu.dialog.about;
import com.smartstudio.sajmovi.eu.fragments.FullNewsFragment;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.holders.NewsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Prostudio on 5.6.2015..
 */
public class NewsFullActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    private static String[] MyPhotoData ;
    MyAdapter mSectionsPagerAdapter;
    ViewPager viewPager;
    public static int photoNumber;
    private static final String TAG = NewsFullActivity.class.getSimpleName();
    public ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private List<NewsData> newsDataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news);
        //default animation
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //geting intent from prevorius Activity
        Intent intent = getIntent();

        //get id value from intent
        int ListItem_ID = intent.getIntExtra("ListItem_ID",-1);
        String serverURL = "http://www......................json_fullnews.php?id="+ListItem_ID;

        //actionBar and back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        JsonObjectRequest jsonReq = new JsonObjectRequest(serverURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            TextView fullnewsnaslov = (TextView) findViewById(R.id.fullnewsnaslov);
                            fullnewsnaslov.setText(response.getString("naslov"));

                            TextView fullnewsnadnaslov = (TextView) findViewById(R.id.fullnewsnadnaslov);
                            fullnewsnadnaslov.setText(response.getString("nadnaslov"));

                            //AppController image
                            NetworkImageView thumbNail = (NetworkImageView)findViewById(R.id.fullnewsimage);
                            thumbNail.setImageUrl(response.getString("image"), imageLoader);

                            TextView fullnewssazetak = (TextView) findViewById(R.id.fullnewssazetak);
                            fullnewssazetak.setText(Html.fromHtml(response.getString("sazetak")));

                            TextView fullnewsdatum = (TextView) findViewById(R.id.fullnewsdatum);
                            fullnewsdatum.setText(response.getString("datum"));

                            TextView fullnewskategorija = (TextView) findViewById(R.id.fullnewskategorija);
                            fullnewskategorija.setText(response.getString("rubrika"));
                            getSupportActionBar().setTitle(response.getString("rubrika"));

                            TextView fullnewsclanak = (TextView) findViewById(R.id.fullnewsclanak);
                            fullnewsclanak.setText(Html.fromHtml(response.getString("clanak")));
                            fullnewsclanak.setMovementMethod(LinkMovementMethod.getInstance());

                            String check = response.getString("gallery");
                            LinearLayout viewpager_layout = (LinearLayout) findViewById(R.id.viewpager_layout);
                            if ("da".equals(check)) {
                                // turning on layout visibility
                                viewpager_layout.setVisibility(View.VISIBLE);

                                //filling NewsData with URLs
                                JSONArray galleryArray = response.optJSONArray("gallery_items");

                                int lengthJsonArr = galleryArray.length();

                                MyPhotoData = new String[lengthJsonArr];
                                photoNumber=lengthJsonArr;
                                for (int i = 0; i < lengthJsonArr; i++)
                                {
                                    JSONObject obj = galleryArray.getJSONObject(i);
                                    MyPhotoData[i] = obj.getString("imgurl");
                                }
                                viewPager.setAdapter(mSectionsPagerAdapter);
                            }else{

                                viewPager.setVisibility(View.INVISIBLE);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hidePDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);

        // My adapter
        mSectionsPagerAdapter = new MyAdapter(getBaseContext(), getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.full_news_pager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOffscreenPageLimit(2);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Učitavanje...");
        pDialog.show();
    }
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


    //------------------- pagerAdapter -------------------
    public static class MyAdapter extends FragmentPagerAdapter {
        Context context;

        public MyAdapter(Context context,FragmentManager fm) {
            super(fm);
            context=context;
        }

        @Override
        public int getCount() {
            return photoNumber;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new FullNewsFragment();
            Bundle args = new Bundle();
            args.putString("galerija_URL",MyPhotoData[position]);
            fragment.setArguments(args);

            return fragment;
        }
    }
    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_full_news, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
            hidePDialog();
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            //overridePendingTransition(R.animator.slide_out_right, R.animator.slide_in_left);
        }*/
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
