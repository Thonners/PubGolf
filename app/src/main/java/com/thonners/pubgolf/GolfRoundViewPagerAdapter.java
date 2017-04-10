package com.thonners.pubgolf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * FragmentPagerAdapter to provide the fragments for the ViewPager in the {@link GolfRoundActivityFragment}
 *
 * @author M Thomas
 * @since 05/04/17
 */

public class GolfRoundViewPagerAdapter extends FragmentPagerAdapter {

    private final static int NUM_ITEMS = 2;
    public final static int SCORECARD_FRAGMENT = 0;
    public final static int MAP_FRAGMENT = 1;

    /**
     * Constructor
     *
     * @param fragmentManager
     */
    public GolfRoundViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     * @return Total number of pages
     */
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    /**
     * Returns the fragment to display
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case SCORECARD_FRAGMENT:
                return ScorecardFragment.newInstance();
            case MAP_FRAGMENT:
                return GolfCourseMapFragment.newInstance();
            default:
                return null;
        }
    }

    /**
     * Returns the title of the fragment to display
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Scorecard";
            case 1:
                return "Map";
            default:
                return null;
        }
    }
}