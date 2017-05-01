package com.thonners.pubgolf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * FragmentPagerAdapter to provide the fragments for the ViewPager in the {@link GolfRoundActivity}
 *
 * @author M Thomas
 * @since 05/04/17
 */

public class GolfRoundViewPagerAdapter extends FragmentPagerAdapter {

    private final static int NUM_ITEMS = 2;
    public final static int SCORECARD_FRAGMENT = 0;
    public final static int MAP_FRAGMENT = 1;

    private ScorecardFragment scorecardFragment ;
    private GolfCourseMapFragment mapFragment ;

    private HashMap<Integer, Fragment> pageReferenceMap = new HashMap<>() ;

    private Course course ;

    /**
     * Constructor
     *
     * @param fragmentManager
     */
    public GolfRoundViewPagerAdapter(FragmentManager fragmentManager, Course course) {
        super(fragmentManager);
        this.course = course ;
// Debugging
Log.d("GRViewPager", "When creating GolfRoundViewPagerAdapter, there are now " + this.course.getPlayers().size() + " players in the course already.") ;
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
        Fragment fragment ;
        switch (position) {
            case SCORECARD_FRAGMENT:
                fragment = ScorecardFragment.newInstance(course);
                break;
            case MAP_FRAGMENT:
                fragment = GolfCourseMapFragment.newInstance(course);
                break;
            default:
                return null;
        }
        pageReferenceMap.put(position,fragment);
        return fragment ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        pageReferenceMap.remove(position) ;
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

    public Fragment getFragment(int index) {
        return pageReferenceMap.get(index) ;
    }

}