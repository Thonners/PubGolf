package com.thonners.pubgolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Fragment to show the GoogleMaps widget, on which the course will be plotted.
 *
 * @author M Thomas
 * @since 05/04/17
 */

public class GolfCourseMapFragment extends Fragment implements OnMapReadyCallback{

    private final static String COURSE_PARCELABLE = "com.thonners.pubgolf.GCFragment.COURSE" ;
    private GoogleMap map ;

    private Course course ;
    private final ArrayList<Marker> holeMarkers = new ArrayList<>() ;
    private final int[] holeMarkerDrawables = new int[19] ;

    private OnGCMapFragmentInteraction mListener ;

    public interface OnGCMapFragmentInteraction {
//        Course getCourse() ;
//        void setGolfCourseMapFragment(GolfCourseMapFragment gcMapFragment) ;
    }

    /**
     * Required empty constructor. (Will be called if Android destroys then recreates the fragment)
     */
    public GolfCourseMapFragment() {
    }

    /**
     * Method to create a new instance and pass variables if required.
     * These can then be added to a bundle and recovered in onCreate() in case the empty constructor
     * is called by the framework.
     * @return An instance of type GolfCourseMapFragment, with arguments attached in a bundle.
     */
    public static GolfCourseMapFragment newInstance(Course course) {
        GolfCourseMapFragment fragment = new GolfCourseMapFragment() ;
        Bundle args = new Bundle();
        args.putParcelable(COURSE_PARCELABLE, course);
        fragment.setArguments(args);
        return fragment ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.course = getArguments().getParcelable(COURSE_PARCELABLE) ;
        }
        // Populate the drawable resource references
        getHoleMarkerDrawableResources() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        return view ;
    }

    @Override
    public void onResume() {
        super.onResume();
        SupportMapFragment smf = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)) ;
        smf.getMapAsync(this);
    }

    /**
     * Overriding the onStart call to set the golfCourseMapFragment of the HomeActivity when this
     * has been created
     */
    @Override
    public void onStart() {
        super.onStart();
  //      mListener.setGolfCourseMapFragment(this);
    }

    /**
     * Overriding the onStop call to remove (i.e. set = null) the golfCourseMapFragment of the
     * HomeActivity when this fragment is stopped
     */
    @Override
    public void onStop() {
        super.onStop();
    //    mListener.setGolfCourseMapFragment(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnGCMapFragmentInteraction) {
            mListener = (OnGCMapFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLaunchFragmentInteractionListener");
        } //*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
  //      mListener = null;
    }
    /**
     * Callback method for when the map is ready
     * @param googleMap the {@link GoogleMap} instance which will be used in the fragment
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
            Log.d("MapFrag", "Map ready ");
        this.map = googleMap ;
        addCourseMarkers() ;
    }

    /**
     * Method to add all the markers for a course
     */
    private void addCourseMarkers() {
        for (Hole hole : course.getHoles()) {
            addHoleMarker(hole);
        }
    }

    /**
     * TODO: Implement
     * Method to add a marker to the map
     */
    public void addHoleMarker(Hole hole) {
        MarkerOptions mops = new MarkerOptions() ;
        mops.position(hole.getPub().getLocation())
                .title("" + hole.getHoleNo())
                .snippet(hole.getPubName())
                .icon(BitmapDescriptorFactory.fromResource(getHoldMarkerDrawable(hole.getHoleNo()))) ;
        Marker holeMarker = map.addMarker(mops) ;
    }

    /**
     * Gets the drawable resource int of the approprite flag for the given hole number. Should the
     * hole number be >18, the returned flag will be blank
     * @param holeNo The number of the hole for which this drawable will be displayed on the map
     * @return The drawable resource int of the appropriate flag
     */
    private int getHoldMarkerDrawable(int holeNo) {
        if (holeNo <= holeMarkerDrawables.length) {
            return holeMarkerDrawables[holeNo] ;
        } else {
            return holeMarkerDrawables[0] ;
        }
    }

    /**
     * Populates the holeMarkerDrawables Array with the Resource int of the corresponding drawables
     */
    private void getHoleMarkerDrawableResources() {
        holeMarkerDrawables[0] = R.drawable.ic_golf_flag_basic ;
        holeMarkerDrawables[1] = R.drawable.ic_golf_flag_1 ;
        holeMarkerDrawables[2] = R.drawable.ic_golf_flag_2 ;
        holeMarkerDrawables[3] = R.drawable.ic_golf_flag_3 ;
        holeMarkerDrawables[4] = R.drawable.ic_golf_flag_4 ;
        holeMarkerDrawables[5] = R.drawable.ic_golf_flag_5 ;
        holeMarkerDrawables[6] = R.drawable.ic_golf_flag_6 ;
        holeMarkerDrawables[7] = R.drawable.ic_golf_flag_7 ;
        holeMarkerDrawables[8] = R.drawable.ic_golf_flag_8 ;
        holeMarkerDrawables[9] = R.drawable.ic_golf_flag_9 ;
        holeMarkerDrawables[10] = R.drawable.ic_golf_flag_10 ;
        holeMarkerDrawables[11] = R.drawable.ic_golf_flag_11 ;
        holeMarkerDrawables[12] = R.drawable.ic_golf_flag_12 ;
        holeMarkerDrawables[13] = R.drawable.ic_golf_flag_13 ;
        holeMarkerDrawables[14] = R.drawable.ic_golf_flag_14 ;
        holeMarkerDrawables[15] = R.drawable.ic_golf_flag_15 ;
        holeMarkerDrawables[16] = R.drawable.ic_golf_flag_16 ;
        holeMarkerDrawables[17] = R.drawable.ic_golf_flag_17 ;
        holeMarkerDrawables[18] = R.drawable.ic_golf_flag_18 ;
    }

    /**
     * @param pub The pub to which the map should now focus
     */
    public void goToPub(Hole.Pub pub) {
        if (map != null) {
            Log.d("MapFrag", "goToPub called for: " + pub.getName()) ;
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pub.getLocation(), 17f));
        } else {
            Log.d("MapFrag", "Map is null ");
        }
    }
}
