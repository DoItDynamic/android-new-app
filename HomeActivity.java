package com.smartstudio.sajmovi.eu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.smartstudio.sajmovi.eu.adapters.DrawerAdapterHome;
import com.smartstudio.sajmovi.eu.dialog.Impresum;
import com.smartstudio.sajmovi.eu.dialog.Kontakt;
import com.smartstudio.sajmovi.eu.dialog.O_Nama;
import com.smartstudio.sajmovi.eu.dialog.about;
import com.smartstudio.sajmovi.eu.dialog.agreement;
import com.smartstudio.sajmovi.eu.dialog.search;
import com.smartstudio.sajmovi.eu.function.AppController;
import com.smartstudio.sajmovi.eu.function.GetDeviceID;
import com.smartstudio.sajmovi.eu.holders.DrawerItems;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

    Button btnStartAnotherActivity;
    public Integer idHomeScreenImage1;
    public Integer idHomeScreenImage2;
    public Integer idHomeScreenImage3;
    public NetworkImageView thumbNail1,thumbNail2,thumbNail3;
    public ImageLoader imageLoader ;
    public ListView mDrawerList;
    public LinearLayout sideMenu;
    private DrawerLayout mDrawerLayout;
    //private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    public String string;
    public String temp="";
    /*New Added*/
    private List dataList;
    private DrawerAdapterHome mDrawerAdapterHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        SharedPreferences settings_agreement = this.getSharedPreferences("data_agreement", Context.MODE_PRIVATE);

        /*Check if Agreement data exists, if not, write default value false*/
        if(!settings_agreement.contains("agreement"))
            settings_agreement.edit().putBoolean("agreement", false).apply();

        //check if user agreed with terms
        boolean agreement = settings_agreement.getBoolean("agreement", true);
        if(!agreement) {
            new agreement(this);
        }
        /*######################################################
        #######################################################*/
        //drawer view
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        sideMenu = (LinearLayout)findViewById(R.id.side_menu);
        mDrawerList = (ListView)findViewById(R.id.navList);

        mActivityTitle = getTitle().toString();

        dataList = new ArrayList();
        addItemsToDataList();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
            thumbNail1 = (NetworkImageView)findViewById(R.id.imageViewTop);
            thumbNail2 = (NetworkImageView)findViewById(R.id.imageViewMiddle);
            thumbNail3 = (NetworkImageView)findViewById(R.id.imageViewBottom);
        }

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //getSupportActionBar().hide();

        //button OnClick home_screen
        btnStartAnotherActivity = (Button) findViewById(R.id.Button1);
        btnStartAnotherActivity = (Button) findViewById(R.id.Button2);
        btnStartAnotherActivity = (Button) findViewById(R.id.Button3);
        btnStartAnotherActivity = (Button) findViewById(R.id.Button1A);
        btnStartAnotherActivity = (Button) findViewById(R.id.Button2A);
        btnStartAnotherActivity = (Button) findViewById(R.id.Button3A);
        btnStartAnotherActivity = (Button) findViewById(R.id.searchbutton);
        btnStartAnotherActivity = (Button) findViewById(R.id.dodaj_sajam);


        btnStartAnotherActivity.setOnClickListener(this);
        //overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);

        //geting intent from SplahScreen
        Intent WebData = getIntent();

        TextView topNews = (TextView) findViewById(R.id.toptextview);
        topNews.setText(WebData.getExtras().getString("box1_naslov"));
        thumbNail1.setImageUrl(WebData.getExtras().getString("box1_image"), imageLoader);

        idHomeScreenImage1 = WebData.getExtras().getInt("box1_id");

        TextView middletext = (TextView) findViewById(R.id.middletextview);
        middletext.setText(WebData.getExtras().getString("box2_naslov"));
        thumbNail2.setImageUrl(WebData.getExtras().getString("box2_image"), imageLoader);

        idHomeScreenImage2 = WebData.getExtras().getInt("box2_id");

        TextView bottomtext = (TextView) findViewById(R.id.bottomtextview);
        bottomtext.setText(WebData.getExtras().getString("box3_naslov"));
        thumbNail3.setImageUrl(WebData.getExtras().getString("box3_image"), imageLoader);

        idHomeScreenImage3 = WebData.getExtras().getInt("box3_id");

        //get phone ID
        GetDeviceID.GetDeviceID(HomeActivity.this);
        //write phoneID in mobile storage
        SharedPreferences phoneID = HomeActivity.this.getSharedPreferences("phoneID", Context.MODE_PRIVATE);
        phoneID.edit().putString("phoneID", GetDeviceID.GetDeviceID(HomeActivity.this)).apply();
    }

    @Override
    public void onClick(View view) {
        //Switch- home_buttons  vijesti,najave,reportaze and picture news
        switch (view.getId()) {
            case R.id.Button1:
                Intent intent = new Intent(view.getContext(), NewsListActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("button_id", 1);
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
                break;
            case R.id.Button2:
                Intent intent2 = new Intent(view.getContext(), NewsListActivity.class);
                Bundle extras2 = new Bundle();
                extras2.putInt("button_id", 2);
                intent2.putExtras(extras2);
                startActivityForResult(intent2, 0);
                break;
            case R.id.Button3:
                Intent intent3 = new Intent(view.getContext(), NewsListActivity.class);
                Bundle extras3 = new Bundle();
                intent3.putExtra("button_id", 3);
                intent3.putExtras(extras3);
                startActivityForResult(intent3, 0);
                break;

            case R.id.Button1A:
                Intent intent4 = new Intent(view.getContext(), NewsFullActivity.class);
                Bundle extras4 = new Bundle();
                extras4.putInt("ListItem_ID", idHomeScreenImage1);
                intent4.putExtras(extras4);
                startActivityForResult(intent4, 0);
                break;
            case R.id.Button2A:
                Intent intent5 = new Intent(view.getContext(), NewsFullActivity.class);
                Bundle extras5 = new Bundle();
                extras5.putInt("ListItem_ID", idHomeScreenImage2);
                intent5.putExtras(extras5);
                startActivityForResult(intent5, 0);
                break;
            case R.id.Button3A:
                Intent intent6 = new Intent(view.getContext(), NewsFullActivity.class);
                Bundle extras6 = new Bundle();
                intent6.putExtra("ListItem_ID", idHomeScreenImage3);
                intent6.putExtras(extras6);
                startActivityForResult(intent6, 0);
                break;
            case R.id.KalendarButton:
                Intent KalendarIntent = new Intent(view.getContext(), CalendarListActivity.class);
                startActivityForResult(KalendarIntent, 0);
                break;
            case R.id.searchbutton:
                new search(this);
                break;
            case R.id.dodaj_sajam:
                Intent intent7 = new Intent(HomeActivity.this, SendPhotoNews.class);
                startActivity(intent7);
                break;
        }//switch
        //opening transition animations
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void addItemsToDataList() {


        mDrawerAdapterHome = new DrawerAdapterHome(this, R.layout.home_side_menu_items, dataList);
        mDrawerList.setAdapter(mDrawerAdapterHome);

        dataList.add(new DrawerItems("O nama", R.drawable.dw_onama));
        dataList.add(new DrawerItems("Impresum", R.drawable.dw_impresum));
        dataList.add(new DrawerItems("Kontakt",R.drawable.dw_kontakt));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        new O_Nama(HomeActivity.this);
                        //Toast.makeText(HomeActivity.this, "Treba nadograditi", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        new Impresum(HomeActivity.this);
                        //Toast.makeText(HomeActivity.this, "Treba nadograditi2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        new Kontakt(HomeActivity.this);
                        //Toast.makeText(HomeActivity.this, "Treba nadograditi3", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        setupDrawer();
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Izbornik");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.about:
                new about(this);
                break;
            case R.id.izlaz:
                finish();
                break;
            default:
        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}