package com.thonners.pubgolf;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RulesManagerActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

    private LinearLayout mainLayout ;
    private RulesManager rm ;
    private TextView promptTV ;
    private TextView standardTitle ;
    private TextView subtitle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_manager);

        // Toolbar admin
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.rules_editor_title);

        // Get the rules manager
        rm = new RulesManager(this) ;

        // Get the layout
        mainLayout = (LinearLayout) findViewById(R.id.main_layout) ;

        // Populate the text for the standard rules
        View standardRulesCard = findViewById(R.id.standard_rules);
        // Add the listeners
        standardRulesCard.setOnClickListener(this);
        standardRulesCard.setOnLongClickListener(this);
        // Get the textviews
        promptTV = (TextView) findViewById(R.id.rules_manager_title);
        standardTitle = (TextView) standardRulesCard.findViewById(R.id.title) ;
        subtitle = (TextView) standardRulesCard.findViewById(R.id.date) ;
        // Populate the main list view
        updateViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RulesManagerActivity", "onResume called");
        // Populate the main list view
        updateViews();
    }

    @Override
    public boolean onLongClick(View cardView) {
        TextView tv = (TextView) cardView.findViewById(R.id.title) ;
        String rulesTitle = tv.getText().toString() ;
        setAsDefaultRuleSet(rulesTitle) ;
        return true;
    }

    /**
     * When a rule set is clicked, open the editor
     * @param cardView The cardview that was clicked
     */
    @Override
    public void onClick(View cardView) {
        Intent editorIntent = new Intent(this, RulesEditorActivity.class) ;
        String ruleSetTitle = ((TextView) cardView.findViewById(R.id.title)).getText().toString() ;
        editorIntent.putExtra(RulesEditorActivity.INTENT_KEY,ruleSetTitle) ;
        startActivity(editorIntent);
    }

    /**
     * Shows a dialog to let the user set this rule set as the default rules, or delete the rule set
     */
    private void setAsDefaultRuleSet(String rulesTitle) {
        String message = String.format(getString(R.string.rules_manager_default_toast), rulesTitle) ;
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show(); ;
        rm.setDefaultRules(rulesTitle);
    }

    private void updateViews() {
        // Set the text
        if (rm.hasCustomSets()) {
            promptTV.setText(getString(R.string.rules_manager_available_rules));
        } else {
            promptTV.setText(getString(R.string.rules_manager_no_custom_sets));
        }
        standardTitle.setText(RulesManager.STANDARD_RULES);
        subtitle.setText(getString(R.string.rules_manager_standard_rules_subtitle));

        // Add the available rules
        for (String ruleSetTitle : rm.getAvailableRuleSets()) {
            // Create a card
            View cardView = getLayoutInflater().inflate(R.layout.card_rule_set,null) ;
            // Layout params
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
            int margin = getResources().getDimensionPixelOffset(R.dimen.card_outer_margin) ;
            lp.setMargins(margin,margin,margin,0);
            // Get the textviews
            TextView title = (TextView) cardView.findViewById(R.id.title) ;
            TextView date = (TextView) cardView.findViewById(R.id.date) ;
            // Set the text
            title.setText(ruleSetTitle);
            date.setText(rm.getRulesCreationDate(ruleSetTitle));
            // Add it to the view (don't add the standard rules as they're permanently displayed at the top
            if (!ruleSetTitle.matches(RulesManager.STANDARD_RULES)) {
                mainLayout.addView(cardView, lp);
            }
            // Add the listeners
            cardView.setOnClickListener(this);
            cardView.setOnLongClickListener(this);
        }
    }
}
