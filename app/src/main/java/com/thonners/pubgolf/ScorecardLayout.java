package com.thonners.pubgolf;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
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

    private final int PUB_COLUMN = 1 ;
    private final int DRINK_COLUMN = 2 ;

    // Initialise to default of 9
    private int noHoles = 9;
    private Context context ;
    private ArrayList<ScorecardRow> rows = new ArrayList<>() ;
    private ArrayList<Player> players = new ArrayList<>() ;

    private OnScorecardLayoutInteractionListener mListener ;

    public interface OnScorecardLayoutInteractionListener {
        void populateScorecard() ;
    }
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
        initialise();
    }
    public ScorecardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context ;
        initialise();
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
        createHeaderRow();

        // Debugging - print the
        Log.d(LOG_TAG,"Context is of class: " + getContext().getClass() + " whilst passed context is of type: " + context.getClass()) ;
    }

    public void setOnScorecardLayoutInteractionListener(OnScorecardLayoutInteractionListener listener) {
        this.mListener = listener ;
    }

    /**
     * Creates the header row for the scorecard. When the scorecard row is created and has been drawn
     * this method calls populateScorecard on the listener, which then creates the rest of the scorecard's views.
     * This delay is required so that the calls to 'getWidth()' on the header row's views return correctly.
     * If there is no delay, the width is reported as '0', as the call happens before the drawing has finished.
     */
    private void createHeaderRow() {
        ScorecardRow newRow = new ScorecardRow(context, 0) ;
        rows.add(newRow) ;
        this.addView(rows.get(0));
        newRow.post(new Runnable() {
            @Override
            public void run() {
                mListener.populateScorecard() ;
            }
        }) ;
    }

    /**
     * Method creates a new {@link ScorecardRow} instance, and animates its addition to the main {@link ScorecardLayout}
     * @param hole The hole for which the scorecard row is to be created
     */
    private void createRow(Hole hole) {
        ScorecardRow newRow = new ScorecardRow(context, hole) ;
        newRow.setAlpha(0.0f);
        newRow.setTranslationY((getResources().getDimension(R.dimen.sc_row_height)));
        rows.add(newRow) ;
        this.addView(rows.get(hole.getHoleNo()));
        newRow.animate()
                .alpha(1.0f)
                .translationY(0)
                .setDuration(300)
                .setStartDelay(100*hole.getHoleNo())
                .start();
    }

    /**
     * @param hole The hole to be added to the scorecard
     */
    public void addHole(Hole hole) {
        Log.d(LOG_TAG, "Creating row for hole: " + hole.getHoleNo() + ": " + hole.getPubName()) ;
        createRow(hole);
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
        private Hole hole ;

        public ScorecardRow(Context context, int rowNo) {
            super(context);
            this.context = context ;
            this.rowNo = 0 ;
            initialiseRow();
        }
        public ScorecardRow(Context context, Hole hole) {
            super(context);
            this.context = context ;
            if (hole == null) {
                Log.d(LOG_TAG, "Hole is coming through as null");
                // Then it's the header row
                this.rowNo = 1 ;
            } else {
                // The hole number corresponds to its row number
                this.rowNo = hole.getHoleNo();
            }
            this.hole = hole ;
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
            // Set the height
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelOffset(R.dimen.sc_row_height)) ;
            this.setLayoutParams(lp);
            // If it's the header row, create TextViews and populate them
            createViews() ;
        }

        private void createViews() {
            // Get the standard header titles
            String[] headers = context.getResources().getStringArray(R.array.scorecard_headers) ;
            // Get the total number of columns - standard columns + no. of players
            noColumns = headers.length + players.size() ;
            String text ;
            View view ;
            // Loop through each column and create the appropriate view
            for (int col = 0 ; col < noColumns ; col++) {
                if (hole == null) {
                    // Get the appropriate text to display
                    if (col < headers.length) {
                        text = headers[col] ;
                    } else {
                        text = players.get(col - headers.length).getName() ;
                    }
                    // Create TextView instance
                    view = createTextView(text, col) ;
                    // Set the layout parameters
                    view.setLayoutParams(getLayoutParamsPG(0,col));
                } else {
                    int rowNo = hole.getHoleNo() ;
                    switch (col) {
                        case 0:
                            text = hole.getHoleNo() + "" ;
                            // Create TextView instance
                            view = createTextView(text, col) ;
                            break;
                        case 1:
                            //text = hole.getPubName() ;
                            // Create TextView instance
                            //view = createTextView(text, col) ;
                            view = createPubTextView(hole.getPub()) ;
                            view.setOnClickListener((HomeActivity) context);
                            break;
                        case 2:
                            text = hole.getDrink().getName() ;
                            // Create TextView instance
                            view = createTextView(text, col) ;
                            break;
                        case 3:
                            text = hole.getDrink().getPar() + "" ;
                            // Create TextView instance
                            view = createTextView(text, col) ;
                            break;
                        default:
                            view = createEditText(col) ;
                            break;
                    }
                    view.setLayoutParams(getLayoutParamsPG(rowNo,col));
                }
                // Add to the view
                this.addView(view);
            }
        }

        /**
         * Method to handle creating an appropriate text view with the appropriate formatting
         * @param text The text to be displayed
         * @param column The column number to be used as the integer ID of the view
         * @return The created and populated TextView
         */
        private TextView createTextView(String text, int column) {
            // Create TextView instance
            TextView tv = new TextView(context);
            // Populate it
            populateTextView(tv, text, column);

            return tv ;
        }
        private void populateTextView(TextView tv, String text, int column) {
            // Set the view ID
            tv.setId(column);
            // Set the style
            if (Build.VERSION.SDK_INT < 23) {
                tv.setTextAppearance(context, R.style.tv_scorecard_header);
            } else {
                tv.setTextAppearance(R.style.tv_scorecard_header);
            }
            // Set the text
            tv.setText(text);
            // Set the text appearance
      //      tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
      //      tv.setGravity(Gravity.CENTER);
            if (hole == null) tv.setTypeface(Typeface.DEFAULT_BOLD);
            // Set the padding
            int padding = context.getResources().getDimensionPixelOffset(R.dimen.sc_text_padding) ;
            tv.setPadding(padding, padding, padding, padding);
            // Set the background
            tv.setBackground(ContextCompat.getDrawable(context, R.drawable.cell_outlined));
        }

        private PubTextView createPubTextView(Hole.Pub pub) {
            // Create the instance
            PubTextView ptv = new PubTextView(context, pub) ;
            // Populate it
            populateTextView(ptv, pub.getName(), PUB_COLUMN);

            return ptv ;
        }

        /**
         * Create an EditText into which the user can put their score. EditText will be formatted
         * using the appropriate style, etc.
         *
         * TODO: Add a listener to update the users score with the value entered
         * @param viewID The ID of the view to be created (ie. the column number)
         * @return The EditText
         */
        private EditText createEditText(int viewID) {
            EditText et = new EditText(context) ;
            // Set the view ID
            et.setId(viewID);
            // Set the style
            if (Build.VERSION.SDK_INT < 23) {
                et.setTextAppearance(context, R.style.tv_scorecard_header);
            } else {
                et.setTextAppearance(R.style.tv_scorecard_header);
            }
            // Set the text appearance
            et.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            et.setTypeface(Typeface.DEFAULT_BOLD);
            // Set the padding
            int padding = context.getResources().getDimensionPixelOffset(R.dimen.sc_text_padding) ;
            et.setPadding(padding, padding, padding, padding);
            // Set the background
            et.setBackground(getResources().getDrawable(R.drawable.cell_outlined));
            // Force to numbers
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            // Force max number of digits to 2
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

            // Set the onClickListener

            // Return the EditText
            return et ;
        }

        /**
         * Method to return the appropriate LayoutParams, so that all lower rows follow the same width as the top row
         * @param row The row number of this row
         * @param col The column number of the view to which these LayoutParams will be applied
         * @return The correctly populated LayoutParams
         */
        private LayoutParams getLayoutParamsPG(int row, int col) {
            LayoutParams lp ;
            if (row == 0) {
                if (col == PUB_COLUMN || col == DRINK_COLUMN) {
                    lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                    lp.weight = 1.0f;
                } else {
                    lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
            } else {
                int width = rows.get(0).getView(col).getWidth();
                lp = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT) ;
            }
            return lp ;
        }

        /**
         * @param viewID ID of the view to be returned
         * @return The instance of the View with the given ID
         */
        public View getView(int viewID) {
            return this.findViewById(viewID) ;
        }

    }

}
