package com.thonners.pubgolf;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, DialogInterface.OnClickListener {

    private final String LOG_TAG = "LaunchActivityFragment" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Create the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        // Populate the cards
        populateCard((CardView) findViewById(R.id.card_play_golf)) ;
        populateCard((CardView) findViewById(R.id.card_scorecard_library)) ;
        populateCard((CardView) findViewById(R.id.card_download_courses)) ;
        populateCard((CardView) findViewById(R.id.card_custom_rules)) ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Method to populate the views and apply the onClickListener to the given CardView
     * @param view The instance of the launch_card_view to be populated
     */
    private void populateCard(CardView view) {
        // Get instances of the nested views
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_main_title) ;
        TextView tvSubtitle = (TextView) view.findViewById(R.id.tv_subtitle) ;
        ImageView img = (ImageView) view.findViewById(R.id.img_card_icon) ;
        // Apply the correct text / image to the views
        switch (view.getId()) {
            case R.id.card_play_golf:
                tvTitle.setText(getString(R.string.card_play_golf_title));
                tvSubtitle.setText(getString(R.string.card_play_golf_subtitle));
                img.setImageResource(R.drawable.ic_pub_golf);
                break ;
            case R.id.card_scorecard_library:
                tvTitle.setText(getString(R.string.card_scorecard_library_title));
                tvSubtitle.setText(getString(R.string.card_scorecard_library_subtitle));
                img.setImageResource(R.drawable.ic_library_books_black_36dp);
                break ;
            case R.id.card_download_courses:
                tvTitle.setText(getString(R.string.card_download_courses_title));
                tvSubtitle.setText(getString(R.string.card_download_courses_subtitle));
                img.setImageResource(R.drawable.ic_golf_course_black_36dp);
                break ;
            case R.id.card_custom_rules:
                tvTitle.setText(getString(R.string.card_custom_rules_title));
                tvSubtitle.setText(getString(R.string.card_custom_rules_subtitle));
                img.setImageResource(R.drawable.ic_content_paste_black_36dp);
                break ;
        }
        // Set the onClickListener
        view.setOnClickListener(this);
    }

    /**
     * Method to show a dialog box to determine the type of game to begin.
     */
    private void createPlayGolfPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        // Populate the popup
        builder.setTitle(R.string.dialog_new_round_title)
                .setMessage(R.string.dialog_new_round_message)
                .setPositiveButton(R.string.dialog_new_round_create, this)
                .setNegativeButton(R.string.dialog_new_round_join, this);
        // Create & show the dialog
        builder.create().show();
    }

    /**
     * Method to handle the user clicking on a CardView in the main layout
     * @param view The view which has been clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_play_golf:
                Log.d(LOG_TAG,"Play Golf Clicked.");
                createPlayGolfPopup();
                break ;
            case R.id.card_scorecard_library:
                Log.d(LOG_TAG,"Scorecard Library Clicked.");
                break ;
            case R.id.card_download_courses:
                Log.d(LOG_TAG,"Download Courses Clicked.");
                break ;
            case R.id.card_custom_rules:
                Log.d(LOG_TAG,"Custom Rules Clicked.");
                break ;
        }
    }

    /**
     * Method to handle the outcome of the user selecting to create a new game or join an existing one.
     * @param dialog The dialog interface.
     * @param id The ID of the button clicked
     */
    @Override
    public void onClick(DialogInterface dialog, int id) {
        switch (id) {
            case BUTTON_NEGATIVE:
                // Join existing game
                Log.d(LOG_TAG, "Join game selected.") ;
                Toast.makeText(this,"Functionality not yet live. Please create a new game.",Toast.LENGTH_LONG).show();
                break ;
            case BUTTON_POSITIVE:
                Log.d(LOG_TAG, "Create new game selected.") ;

                break ;
        }
    }
}
