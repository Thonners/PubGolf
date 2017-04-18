package com.thonners.pubgolf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Activity to show the game's rules and regulations.
 *
 * @author M Thomas
 * @since 18/04/17
 */
public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        // Toolbar admin
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.rules_activity_title));

        // Populate the rules...
        // Get the view instance
        TextView rulesBody = (TextView) findViewById(R.id.rules_body) ;
        // Get the rules
        String[] rulesArray = getResources().getStringArray(R.array.rules_entries) ;
        // Initialise the string we'll add to the textview
        String rules = "" ;
        for (String rule : rulesArray) {
            rules += rule + "\n" ;  // Add a new line to the end of the string
        }
        rulesBody.setText(rules);
    }

    /**
     * Handle menu clicks (or up/back clicks)
     * @param item The menu item clicked
     * @return Whether the click has been dealt with(?)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                // Someone pressed 'back' in the toolbar
                this.onBackPressed();
                return true ;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
