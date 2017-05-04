package com.thonners.pubgolf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Activity to show the game's rules and regulations.
 *
 * @author M Thomas
 * @since 18/04/17
 */
public class RulesActivity extends AppCompatActivity {

    public static final String RULES_EXTRA = "com.thonners.pubgolf.rules" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        // Toolbar admin
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.rules_activity_title));

        // Populate the rules...
        RulesManager rm = new RulesManager(this) ;
        // Get the view instance
        LinearLayout layout = (LinearLayout) findViewById(R.id.rules_linear_layout) ;
        // Initialise the rule counter
        int ruleNo = 1 ;
        for (String rule : rm.getDefaultRuleSet()) {
          //  rules += rule + "\n" ;  // Add a new line to the end of the string
            RulesEntry entry = new RulesEntry(this,ruleNo,rule) ;
            layout.addView(entry);
            ruleNo++ ;
        }
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
