package com.thonners.pubgolf;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView ;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

/**
 * TextView to show the total of a scorecard column.
 * Implements the TextWatcher interface, which will listen for changes to the EditTexts which make up
 * the score entry, and trigger a recount when appropriate.
 *
 * @author M Thomas
 * @since 20/04/17
 */

public class ScorecardTotalTextView extends AppCompatTextView {

    private static final String LOG_TAG = "ScorecardTotalTextView" ;

    private ScorecardLayout.scoreTextWatcher textWatcher = new ScorecardLayout.scoreTextWatcher(this) ;

    private int total = 0;
    private int columnNo ;
    private final HashSet<EditText> scoreEditTexts = new HashSet<>() ;
    private final HashSet<TextView> scoreViews = new HashSet<>() ;

    public ScorecardTotalTextView(Context context) {
        super(context);
        initialise();
    }

    public ScorecardTotalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    public ScorecardTotalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise();
    }

    /**
     * Initialise the view.
     */
    private void initialise() {
        // Set the value to 0
        this.setText(String.valueOf(total));
        // Set the style
        TextViewCompat.setTextAppearance(this,R.style.tv_scorecard_header);
        // Center the text
        this.setGravity(Gravity.CENTER);
        // Set the padding
        int padding = getContext().getResources().getDimensionPixelOffset(R.dimen.sc_text_padding) ;
        this.setPadding(padding, padding, padding, padding);
        // Set the background
        this.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_outlined));
    }

    /**
     * @param view The TextView (or EditText) of the player's score
     */
    public void addScoreView(TextView view) {
        // Add it to the collection
        scoreViews.add(view) ; // If it's already there, this won't do anything
        // Apply the textWatcher
        view.addTextChangedListener(textWatcher);
    }

    /**
     * Recalculates the total in the score EditTexts of this column.
     */
    public void recalculateTotal() {
        total = 0 ;
        if (scoreEditTexts != null) {   // Test for null as this is triggered when first created
            for (EditText et : scoreEditTexts) {
                total += getScore(et);
            }
        }
        // Set the display to that of the total
        this.setText(String.valueOf(total));
    }

    /**
     * Parses the text in an EditText to an int. Returns 0 if no input found.
     * @param editText The EditText to parse
     * @return The integer value of the parsed text from the EditText
     */
    private int getScore(EditText editText) {
        try {
            // Get the text
            String input = editText.getText().toString() ;
            // If there's nothing, return 0
            if (input.isEmpty()) return 0;
            // Otherwise, parse it to an int
            return Integer.parseInt(input) ;
        } catch (Exception e) {
            Log.e(LOG_TAG,"Error parsing int from editText: " + e.getMessage()) ;
            return 0 ;
        }
    }


}
