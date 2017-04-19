package com.thonners.pubgolf;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.thonners.pubgolf.R.id.textView;

/**
 * Class to easily and quickly add a row for an entry in the rules.
 * Creates two
 *
 * @author M Thomas
 * @since 19/04/17
 */

public class RulesEntry extends LinearLayout {

    private TextView tvNumber ;
    private TextView tvRules  ;

    /**
     * Constructor
     * @param context Activity context
     * @param ruleNo The rule number to be shown in the first TextView
     * @param rule The details of the rule to be shown in the second TextView
     */
    public RulesEntry(Context context, int ruleNo, String rule) {
        super(context);
        initialise(context, null, 0);
        setRuleNo(ruleNo);
        setRuleText(rule) ;
    }

    /**
     * Default Constructors
     * @param context Activity context
     */
    public RulesEntry(Context context) {
        super(context);
        initialise(context, null, 0);
    }

    public RulesEntry(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs, 0);
    }

    public RulesEntry(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs, defStyleAttr);
    }

    /**
     * Initialise the LinearLayout - set its orientation to horizontal, and create the instances of
     * the TextViews required for displaying the number and the details of the rule.
     *
     * @param context Activity context
     * @param attrs Attribute set for views
     * @param defStyleAttr Style for views
     */
    private void initialise(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        // Force orientation horizontal
        this.setOrientation(HORIZONTAL);
        // Set to match parent width, and wrap height
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        this.setLayoutParams(lp);
        // Create the textView instances
        tvNumber = new TextView(context) ;
        tvRules = new TextView(context) ;
        // Set the text appearance
        TextViewCompat.setTextAppearance(tvNumber,R.style.rules_body);
        TextViewCompat.setTextAppearance(tvRules,R.style.rules_body);
        // Create the layout params
        LayoutParams lpNo = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
        LayoutParams lpRules = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f) ;
        // Add them to the view
        this.addView(tvNumber, lpNo);
        this.addView(tvRules, lpRules);
    }

    /**
     * Sets the tvNumber text view's text to match the rule number, formatted to follow it by ').'
     * Also adds an extra trailing space for numbers < 10 to ensure width is consistent.
     * @param ruleNo The rule number to be shown in the first TextView
     */
    public void setRuleNo(int ruleNo) {
        String ruleNoString ;
        if (ruleNo < 10) {
            ruleNoString = ruleNo + ").   " ;
        } else {
            ruleNoString = ruleNo + "). " ;
        }
        tvNumber.setText(ruleNoString);
    }

    /**
     * @param ruleText The String to be displayed explaining the details of the rule
     */
    public void setRuleText(String ruleText) {
        tvRules.setText(ruleText);
    }
}
