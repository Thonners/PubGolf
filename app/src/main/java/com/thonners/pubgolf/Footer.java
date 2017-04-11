package com.thonners.pubgolf;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A footer view, to be added to the bottom of a layout, containing buttons.
 *
 * User interaction with the buttons is passed back to the parent Activity/Fragment through the
 * #FooterInteractionListener interface
 *
 * @author M Thomas
 * @since 04/04/17
 */

public class Footer extends LinearLayout implements LinearLayout.OnClickListener, ViewPager.OnPageChangeListener {

    private final String LOG_TAG = "Footer" ;

    private Context context ;
    private int activeButtonID = 0 ;
    private final ArrayList<FooterButton> buttons = new ArrayList<>() ;

    private FooterInteractionListener mListener ;

    /**
     * Interface for interacting with the fragment to which this Footer belongs
     */
    public interface FooterInteractionListener {
        void footerButtonClicked(int buttonID) ;
    }
    /**
     * Default constructors
     * @param context Activity context
     */
    public Footer(Context context) {
        super(context) ;
        this.context = context ;
        initialise() ;
    }
    public Footer(Context context, AttributeSet attrs) {
        super(context, attrs) ;
        this.context = context ;
        initialise() ;
    }
    public Footer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle) ;
        this.context = context ;
        initialise() ;
    }

    /**
     * Initialise the view - not really necessary at the moment.
     */
    private void initialise() {
        // Set the orientation
        this.setOrientation(HORIZONTAL);
    }

    /**
     * @param listener The listener for the footerInteraction interface, so clicks can be passed back to the parent activity/fragment
     */
    public void setFooterInteractionListener(FooterInteractionListener listener) {
        mListener = listener ;
    }

    /**
     * Method to add a button to the footer
     * @param label The text to be shown
     * @param iconResource The drawable resource of the icon to be shown
     */
    public void addButton(String label, int iconResource) {
        // Create the button instance
        int id = buttons.size() ; // Get the index of the next button to be added
        FooterButton button = new FooterButton(context, id, label,iconResource,this) ;
        this.addView(button);
        buttons.add(button) ;
        // If first button, set active
        if (id == 0) button.setIsFocused(true);
    }

    /**
     * @param buttonID The identifier of the footer button to be focused
     */
    public void setButtonFocused(int buttonID) {
        // If button already active, ignore click
        if (buttonID == activeButtonID) {
            Log.d(LOG_TAG,"Same button clicked, so doing nothing. Button ID = " + buttonID) ;
        } else {
            Log.d(LOG_TAG,"Button with ID: + " + buttonID + " clicked, so calling the footerInteractionListener") ;
            // Set the previously active button to not focused
            buttons.get(activeButtonID).setIsFocused(false);
            // Set the new button to focused
            buttons.get(buttonID).setIsFocused(true);
            // Update the activeButtonID
            activeButtonID = buttonID ;
        }
    }

    /**
     * OnClickListener to manage clicks of FooterButtons
     * @param view The FooterButton which was clicked
     */
    @Override
    public void onClick(View view) {
        // Safe to cast to FooterButton, since that is the only type of view to which this onClickListener is watching. Checking the classtype seems wasteful & unnecessary.
        FooterButton button = (FooterButton) view ;
        // Call the footerInteractionListener
        mListener.footerButtonClicked(button.getIdentifier());
    }

    /**
     * Method from {@link android.support.v4.view.ViewPager.OnPageChangeListener} to respond to a different
     * page being selected. This simply calls {@link #setButtonFocused(int)} to apply focus to the
     * appropriate footer button.
     * @param position The index of the selected page
     */
    @Override
    public void onPageSelected(int position) {
        setButtonFocused(position);
    }

    /**
     * Method from {@link android.support.v4.view.ViewPager.OnPageChangeListener}
     * @param position The index of the page being scrolled
     */
    @Override
    public void onPageScrollStateChanged(int position) {

    }

    /**
     * Method from {@link android.support.v4.view.ViewPager.OnPageChangeListener}      *
     * @param position The index of the page being scrolled
     * @param positionOffset The amount it's been scrolled (?)
     * @param positionOffsetPixels The amount it's been scrolled (?)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * Class representing one of the buttons to be added to the footer.
     * The created button has an ID, so it can be identified by the onClickListener.
     *
     * @author M Thomas
     * @since 04/04/17
     */
    private class FooterButton extends LinearLayout{
        private Context context ;
        private int identifier ;
        private TextView tv = null ;
        private ImageView iv = null ;

        /**
         * Constructor, but doesn't set icon.
         * @see #FooterButton(Context, int, String, int, OnClickListener)
         */
        public FooterButton(Context context, int id, String textToDisplay, LinearLayout.OnClickListener onClickListener)  {
            super(context) ;
            this.context = context ;
            this.identifier = id ;
            this.setOnClickListener(onClickListener);
            initialise();
            setText(textToDisplay);
        }

        /**
         * Constructor
         * @param context Application context
         * @param id ID of the button, so it can be identified by the onClickListener
         * @param textToDisplay The label for the button
         * @param drawableID The drawable resource ID of the icon
         * @param onClickListener The onClickListener which will respond to user interaction
         */
        public FooterButton(Context context, int id, String textToDisplay, int drawableID, LinearLayout.OnClickListener onClickListener)  {
            super(context) ;
            this.context = context ;
            this.identifier = id ;
            this.setOnClickListener(onClickListener);
            initialise();
            setIcon(drawableID) ;
            addSpace() ;
            setText(textToDisplay);
        }
        /**
         * Default constructors
         * @param context Activity context
         */
        public FooterButton(Context context)  {
            super(context) ;
            this.context = context ;
            initialise();
        }
        public FooterButton(Context context, AttributeSet attrs) {
            super(context, attrs) ;
            this.context = context ;
            initialise();
        }
        public FooterButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle) ;
            this.context = context ;
            initialise();
        }

        /**
         * Initialises the button, creating layout parameters to give each view a weight of 1,
         * setting the internal LinearLayout orientation to vertical, and the background colour to primary_dark.
          */
        private void initialise() {
            // Set the orientation
            this.setOrientation(VERTICAL);
            // Create & set the layout params (match parent height, width weight = 1
            LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1) ;
            this.setLayoutParams(lp);
            // Set background colour
            this.setBackgroundColor(ContextCompat.getColor(context,R.color.primary_dark));
        }

        /**
         * Method to add a lightweight {@link Space} to the view, to ensure icon and text are positioned according to
         * their top/bottom padding settings respectively.
         */
        private void addSpace() {
            Space space = new Space(context) ;
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            space.setLayoutParams(lp);
            this.addView(space);
        }

        /**
         * Method to set the text of the footer button. Creates an instance of a TextView if one
         * doesn't already exist, and formats the text appropriately.
         *
         * @param text The text to be displayed as the button's label
         */
        private void setText(String text) {
            // Initialise the textView if it doesn't already exist
            if (tv == null) {
                tv = new TextView(context) ;
                this.addView(tv);
            }
            // Set the text
            tv.setText(text);
            // Set the style
            if (Build.VERSION.SDK_INT < 23) {
                tv.setTextAppearance(context, R.style.footer_button_text);
            } else {
                tv.setTextAppearance(R.style.footer_button_text);
            }
            // Force colour
            tv.setTextColor(ContextCompat.getColor(context,R.color.primary_light));
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) ;
            lp.gravity = Gravity.CENTER ;
            tv.setLayoutParams(lp);
            tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            tv.setPadding(context.getResources().getDimensionPixelOffset(R.dimen.footer_button_side_padding),0,context.getResources().getDimensionPixelOffset(R.dimen.footer_button_side_padding),context.getResources().getDimensionPixelOffset(R.dimen.footer_button_text_bottom_padding));
        }

        /**
         * Method to set the icon of a footer button. Creates an instance of the ImageView if one
         * doesn't already exist, and formats the icon appropriately.
         *
         * @param drawableResource The resource ID of the icon to be applied
         */
        private void setIcon(int drawableResource) {
            // Create the ImageView if it doesn't already exist
            if (iv == null)  {
                iv = new ImageView(context) ;
                this.addView(iv);
            }
            // Set the icon
            iv.setImageResource(drawableResource) ;
            // Set its colour
            iv.setColorFilter(ContextCompat.getColor(context,R.color.primary_light));
            // Set its size
            LayoutParams lp = (LayoutParams) iv.getLayoutParams() ;
            lp.width = context.getResources().getDimensionPixelOffset(R.dimen.footer_button_icon);
            lp.height = context.getResources().getDimensionPixelOffset(R.dimen.footer_button_icon);
            lp.gravity = Gravity.CENTER_HORIZONTAL ;
            iv.setLayoutParams(lp);
            // Set the top padding
            iv.setPadding(0, context.getResources().getDimensionPixelOffset(R.dimen.footer_button_icon_top_padding_not_focused),0,0);
        }

        /**
         * @return The identifier for this view - so it aan be identified 'onClick'
         */
        public int getIdentifier() {
            return identifier;
        }

        /**
         * Method to set the appearance of the button - icon/text size and colouring depending on
         * whether the button is in focus or not.
         *
         * @param isFocused Whether the button is in focus
         */
        public void setIsFocused(boolean isFocused) {
            if (isFocused) {
                // Set text size
                if (tv != null) tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.footer_button_text_size_focused));
                // Set icon top padding
                if (iv != null) iv.setPadding(0,context.getResources().getDimensionPixelSize(R.dimen.footer_button_icon_top_padding_focused),0,0);
                // Set background colour
                this.setBackgroundColor(ContextCompat.getColor(context,R.color.primary));
            } else {
                // Set text size
                if (tv != null) tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.footer_button_text_size_not_focused));
                // Set icon top padding
                if (iv != null) iv.setPadding(0,context.getResources().getDimensionPixelSize(R.dimen.footer_button_icon_top_padding_not_focused),0,0);
                // Set background colour
                this.setBackgroundColor(ContextCompat.getColor(context,R.color.primary_dark));
            }
        }
    }
}
