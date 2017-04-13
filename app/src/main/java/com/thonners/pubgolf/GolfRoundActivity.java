package com.thonners.pubgolf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class GolfRoundActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  PubTextView.OnClickListener, Footer.FooterInteractionListener, ScorecardFragment.OnScorecardFragmentInteractionListener {

    private final static String LOG_TAG = "GolfRoundActivity" ;
    private static final int SCORECARD_FOOTER_BUTTON_ID = 0 ;
    private static final int MAP_FOOTER_BUTTON_ID = 1 ;

    public static final String COURSE = "com.thonners.pubgolf.COURSE" ;

    private Course course ;
    private Footer footer ;
    private ViewPager viewPager ;
    private GolfRoundViewPagerAdapter pagerAdapter ;
    private ScorecardFragment scorecardFragment ;
    private GolfCourseMapFragment mapFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golf_round);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get the intent extras
        Intent intent = getIntent() ;
        // Get the course
        course = intent.getParcelableExtra(COURSE) ;

        // Set the activity title
        getSupportActionBar().setTitle(course.getName());

        // Get the view instances
        viewPager = (ViewPager) findViewById(R.id.fragment_view_pager) ;
        footer = (Footer) findViewById(R.id.footer) ;
        footer.setFooterInteractionListener(this);

        // Create the pager adapter and set the viewPager to use it
        pagerAdapter = new GolfRoundViewPagerAdapter(getSupportFragmentManager(), course);
        viewPager.setAdapter(pagerAdapter);
        // Prevent the viewPager destroying fragments/views when they're offscreen
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        // Add the footer button as an onPageChange listener
        viewPager.addOnPageChangeListener(footer);
        // Create the footer buttons
        createFooterButtons() ;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.golf_round, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scorecard) {
            // Handle the camera action
        } else if (id == R.id.nav_map) {

        } else if (id == R.id.nav_rules) {

        } else if (id == R.id.nav_manage_course) {

        } else if (id == R.id.nav_manage_drinks) {

        } else if (id == R.id.nav_add_players) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void swapFragment(Class fragmentClass) {
        Fragment fragment = null ;
        try {
            fragment = (Fragment) fragmentClass.newInstance() ;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error creating launch activity fragment:");
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager() ;
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit();

    }

    /**
     * Method to populate the footer with the required buttons
     */
    private void createFooterButtons() {
        // Scorecard button
        footer.addButton(getString(R.string.footer_button_scorecard), R.drawable.ic_content_paste_black_36dp);
        // Map button
        footer.addButton(getString(R.string.footer_button_map), R.drawable.ic_golf_course_black_36dp);
    }

    @Override
    public void footerButtonClicked(int buttonID) {
        switch (buttonID) {
            default:
                // Catch anything else at this stage and let it fall through to scorecard
            case SCORECARD_FOOTER_BUTTON_ID:
                Log.d(LOG_TAG,"Scorecard footer button clicked") ;
                viewPager.setCurrentItem(SCORECARD_FOOTER_BUTTON_ID);
                break;
            case MAP_FOOTER_BUTTON_ID:
                Log.d(LOG_TAG,"Map footer button clicked") ;
                showMap() ;
                break;
        }
    }

    /**
     * Method to change view pager to display the Map fragment
     */
    public void showMap() {
        viewPager.setCurrentItem(MAP_FOOTER_BUTTON_ID);
    }

    @Override
    public void goToPub(Hole.Pub pub) {
            Log.d(LOG_TAG, "Calling goToPub on the mapFragment") ;
        ((GolfCourseMapFragment) pagerAdapter.getFragment(MAP_FOOTER_BUTTON_ID)).goToPub(pub);
            Log.d(LOG_TAG, "showing the map") ;
        showMap() ;
    }

    @Override
    public void onClick(View view) {
        // Pub clicks
        if (view instanceof PubTextView) {
            Log.d(LOG_TAG, "PubTextView was just clicked") ;
            goToPub(((PubTextView) view).getPub());
        } else {
            Log.d(LOG_TAG, "Unrecognised view was just clicked") ;
        }
    }
}
