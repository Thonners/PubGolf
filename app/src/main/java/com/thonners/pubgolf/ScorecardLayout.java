package com.thonners.pubgolf;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class to manage displaying the appropriate views to create a scorecard
 *
 * @author M Thomas
 * @since 28/03/17.
 */

public class ScorecardLayout extends LinearLayout {

    private final String LOG_TAG = "ScorecardLayout" ;

    // Initialise to default of 9
    int noHoles = 9;
    private Context context ;
    private ArrayList<ScorecardRow> rows = new ArrayList<>() ;
    private ArrayList<Player> players = new ArrayList<>() ;

    /**
     * Constructor for use programatically
     * @param context Application context - required for the call to super()
     * @param noHoles The number of holes (and therefore rows) to be shown in the layout
     */
    public ScorecardLayout(Context context, int noHoles) {
        super(context) ;
        this.context = context ;
        this.noHoles = noHoles;
        initialise();
    }
    // Constructors for use in XML
    public ScorecardLayout(Context context) {
        this(context,null);
        this.context = context ;
        initialise();
    }
    public ScorecardLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context ;
        initialise() ;
    }
    public ScorecardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Method to create the grid as required for the scorecard
     */
    private void initialise() {
        Log.d(LOG_TAG, "ScorecardLayout.initialise() called...");
        // Force orientation to vertical
        this.setOrientation(VERTICAL);
        // Set the background so that there's an outline to the whole grid
        this.setBackground(getResources().getDrawable(R.drawable.cell_outlined));
        // Create the first row
        createRow(0);
    }

    private void createRow(int rowNo) {
        ScorecardRow newRow = new ScorecardRow(context, rowNo) ;
        rows.add(newRow) ;
        this.addView(rows.get(0));

    }

    /**
     * @return The number of holes (and therefore rows) to be shown in the layout
     */
    public int getNoHoles() {
        return noHoles;
    }

    /**
     * @param noHoles The number of holes (and therefore rows) to be shown in the layout
     */
    public void setNoHoles(int noHoles) {
        // TODO: Check whether number of holes is <> current number, and adjust the views to suit
        this.noHoles = noHoles;
    }

    /**
     * Basic class to hold the views in a row of the scorecard
     */
    private class ScorecardRow extends LinearLayout {

        private Context context ;
        protected int noColumns;
        protected int rowNo;

        public ScorecardRow(Context context, int rowNo) {
            super(context);
            this.context = context ;
            this.rowNo = rowNo;
            initialiseRow();
        }

        public ScorecardRow(Context context) {
            super(context);
        }

        public ScorecardRow(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * Method to create the grid as required for the scorecard
         */
        private void initialiseRow() {
            // Force orientation to horizontal
            this.setOrientation(HORIZONTAL);
            // The it's the header row. Create TextViews and populate them
            createViews(rowNo == 0) ;
        }

        private void createViews(boolean isHeaderRow) {
            // Get the standard header titles
            String[] headers = context.getResources().getStringArray(R.array.scorecard_headers) ;
            // Get the total number of columns - standard columns + no. of players
            noColumns = headers.length + players.size() ;
            // Loop through each column and create the appropriate view
            for (int i = 0 ; i < noColumns ; i++) {
                if (isHeaderRow) {
                    // Create TextView instance
                    TextView tv = new TextView(context) ;
                    // Set the style
                    if (Build.VERSION.SDK_INT < 23) {
                        tv.setTextAppearance(context, R.style.tv_scorecard_header);
                    } else {
                        tv.setTextAppearance(R.style.tv_scorecard_header);
                    }
                    // Get the appropriate text to display
                    String text ;
                    if (i < headers.length) {
                        text = headers[i] ;
                    } else {
                        text = players.get(i - headers.length).getName() ;
                    }
                    // Set the text
                    tv.setText(text);
                    // Add to the view
                    this.addView(tv);
                }
            }
        }
    }

}
