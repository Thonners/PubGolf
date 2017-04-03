package com.thonners.pubgolf;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView ;

/**
 * TextView which can also hold the details of the pub to which it refers.
 *
 * @author M Thomas
 * @since 02/04/17.
 */

public class PubTextView extends AppCompatTextView {

    Hole.Pub pub ;

    public PubTextView(Context context, Hole.Pub pub) {
        super(context);
        this.pub = pub ;
    }

    public PubTextView(Context context) {
        super(context) ;
    }
    public PubTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public PubTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @return The pub to which this TextView refers
     */
    public Hole.Pub getPub() {
        return pub;
    }
}
