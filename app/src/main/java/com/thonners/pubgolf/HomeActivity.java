package com.thonners.pubgolf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LaunchActivityFragment.OnLaunchFragmentInteractionListener, /*ScorecardFragment.OnScorecardFragmentInteractionListener, *//*PubTextView.OnClickListener,*/ GolfCourseMapFragment.OnGCMapFragmentInteraction, LaunchActivityFragment.AddPlayersDialog.AddPlayersDialogListener {

    private final String LOG_TAG = "LaunchActivityFragment" ;

    private CourseManager cm = new CourseManager() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create an instance of the main launch fragment
        swapFragment(LaunchActivityFragment.class) ;
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        switch (id) {
            case R.id.nav_scorecard:
                break;
            case R.id.nav_map:
                break;
            case R.id.nav_rules:
                break;
            case R.id.nav_manage_course:
                break;
            case R.id.nav_manage_drinks:
                break;
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

    @Override
    public void joinGame() {
        Toast.makeText(this,"Functionality not yet live.\nPlease create a new game.", Toast.LENGTH_LONG).show();
    }

    /**
     * Interface method to be called from the LaunchActivityFragment to create a new scorecard
     */
    @Override
    public void launchNewGame(Course courseToLoad) {
        getSupportActionBar().setTitle(courseToLoad.getName());
        Intent newRound = new Intent(this,GolfRoundActivity.class) ;
        newRound.putExtra(GolfRoundActivity.COURSE,courseToLoad) ;
        startActivity(newRound);
    }
    /**
     * @return An ArrayList of the available courses
     */
    @Override
    public ArrayList<Course> getCourses() {
        return cm.getCourses() ;
    }

    /**
     * Method to launch the course manager
     */
    @Override
    public void getMoreCourses() {
        //TODO: Implement this method
        Log.d(LOG_TAG, "Would switch to the CourseManager fragment now...") ;
    }
}
