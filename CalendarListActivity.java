package com.smartstudio.sajmovi.eu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.smartstudio.sajmovi.eu.adapters.DrawerAdapterCalendar;
import com.smartstudio.sajmovi.eu.dialog.about;
import com.smartstudio.sajmovi.eu.fragments.CalendarFragment;
import com.smartstudio.sajmovi.eu.function.HandleCountryData;
import com.smartstudio.sajmovi.eu.holders.DrawerItemsCalendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 5.6.2015..
 */
public class CalendarListActivity extends ActionBarActivity {

    private static final int PAGE_LEFT = 0;
    private static final int PAGE_MIDDLE = 1;
    private static final int PAGE_RIGHT = 2;

    //drawer things
    public ListView mDrawerList;
    public LinearLayout sideMenu;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    public static String LRPosition = "null";
    public static int pageNumber = -1;

    //public static int pLeft = 0;
    //public static int pRight = 0;
    //public static int pMiddle = 0;
    public static String Mjesec;

    MyPagerAdapter adapter;
    private LayoutInflater mInflater;
    private int mSelectedPageIndex = 12;
    MyPagerAdapter mSectionsPagerAdapter;
    ViewPager viewPager;
    /*Novo dodano*/
    private List dataList;
    public static String MyCountry;
    private DrawerAdapterCalendar mDrawerAdapterCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        //----------------------------
        //drawer view
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout2);
        sideMenu = (LinearLayout)findViewById(R.id.side_menu2);
        mDrawerList = (ListView)findViewById(R.id.navList2);

        mActivityTitle = getTitle().toString();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //-----------------------------
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        mInflater = getLayoutInflater();

        new HandleCountryData(CalendarListActivity.this);
        SharedPreferences settings = this.getSharedPreferences("datacountry", Context.MODE_PRIVATE);
        boolean country1 = settings.getBoolean("country1", true);
        boolean country2 = settings.getBoolean("country2", true);
        boolean country3 = settings.getBoolean("country3", true);
        boolean country4 = settings.getBoolean("country4", true);
        boolean country5 = settings.getBoolean("country5", true);
        boolean country6 = settings.getBoolean("country6", true);
        MyCountry = "&c1="+country1+"&c2="+country2+"&c3="+country3+"&c4="+country4+"&c5="+country5+"&c6="+country6+"";

        mSectionsPagerAdapter = new MyPagerAdapter(
                this.getBaseContext(), getSupportFragmentManager(),pageNumber,LRPosition );

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mSectionsPagerAdapter);
        //viewPager.requestTransparentRegion(viewPager);
        viewPager.setCurrentItem(mSelectedPageIndex, false);

        //drawer view
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout2);
        sideMenu = (LinearLayout)findViewById(R.id.side_menu2);
        mDrawerList = (ListView)findViewById(R.id.navList2);

        mActivityTitle = getTitle().toString();

        //addDrawerItems();
        //setupDrawer();
        dataList = new ArrayList();
        addItemsToDataList();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position)
            {
                // mSelectedPageIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if(mSelectedPageIndex == PAGE_LEFT) {
                        //setContent(pMiddle - 1, null);
                        //pageNumber = pMiddle - 1;
                        LRPosition = "prev";
                    }
                    else if(mSelectedPageIndex == PAGE_RIGHT) {
                        //setContent(pMiddle + 1, null);
                        //pageNumber = pMiddle + 1;
                        LRPosition = "next";
                    }
                }
            }
        });
    }
     /*
     VIEPAGER ANIMATION
     */
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
    /*
    VIEWPAGER CUSTOM ADAPTER
     */
    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public Context mContext;
        public int indexx;
        public String poss;
        public int passed_page_number;
        public MyPagerAdapter(Context context, FragmentManager fm, int index, String pos) {
            super(fm);
            mContext = context;
            this.indexx = index;
            this.poss = pos;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new CalendarFragment();
            Bundle args = new Bundle();

            switch (LRPosition) {
                case "prev":
                    passed_page_number = pageNumber-1;
                    break;
                case "null":
                    passed_page_number = pageNumber+1;
                    break;
                case "next":
                    passed_page_number = pageNumber+1;
                    break;
            }
            //Log.v("Position", "++++"+mSelectedPageIndex+" / " +passed_page_number+" / "+ position + "++++");
            args.putInt(CalendarFragment.ARG_SECTION_NUMBER, position);
            args.putInt("PageNumber", position-mSelectedPageIndex);
            args.putString("Country", MyCountry);
            //args.putString("PagePosition", LRPosition);
            fragment.setArguments(args);
            // SetMonthName();
            return fragment;
        }
        /*@Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/
        @Override
        public int getCount() {
            // Show 24 total pages.
            return 24;
        }
    }

    /*#########################################################################################
    ##############################       Option menu      #####################################
    ###########################################################################################
    */
    private void addItemsToDataList() {
        mDrawerAdapterCalendar = new DrawerAdapterCalendar(this, R.layout.side_menu_kalendar, dataList);
        mDrawerList.setAdapter(mDrawerAdapterCalendar);

        dataList.add(new DrawerItemsCalendar("Hrvatska", R.drawable.flag1, "country1"));
        dataList.add(new DrawerItemsCalendar("Bosna i Hercegovina", R.drawable.flag2, "country2"));
        dataList.add(new DrawerItemsCalendar("Srbija", R.drawable.flag3, "country3"));
        dataList.add(new DrawerItemsCalendar("Crna Gora", R.drawable.flag4, "country4"));
        dataList.add(new DrawerItemsCalendar("Slovenia", R.drawable.flag5, "country5"));
        dataList.add(new DrawerItemsCalendar("Makedonija", R.drawable.flag6, "country6"));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        Toast.makeText(CalendarListActivity.this, "Treba nadograditi", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(CalendarListActivity.this, "Treba nadograditi2", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(CalendarListActivity.this, "Treba nadograditi3", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(CalendarListActivity.this, "Treba nadograditi4", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(CalendarListActivity.this, "Treba nadograditi5", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(view.getContext(), CalendarListActivity.class);
                startActivityForResult(intent, 0);
                finish();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.o_aplikaciji:
                new about(this);
                break;
            case R.id.izlaz:
                finish();
                break;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

  /* public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case R.id.item1:
               item.setChecked(!item.isChecked());
               break;
           case R.id.item2:
               item.setChecked(!item.isChecked());
               break;
           case R.id.item3:
               item.setChecked(!item.isChecked());
               break;
           case R.id.item4:
               item.setChecked(!item.isChecked());
               break;
           case R.id.item5:
               item.setChecked(!item.isChecked());
               break;
           case R.id.item6:
               item.setChecked(!item.isChecked());
               break;
           default:
               break;
       }


       return super.onOptionsItemSelected(item);
   }*/
  @Override
  public void onBackPressed() {
      // TODO Auto-generated method stub

      if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
          mDrawerLayout.closeDrawer(Gravity.LEFT);
      }else{
          super.onBackPressed();
      }
  }
}
