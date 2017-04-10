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

    private GoogleMap map ;

    private Course course ;
    private final ArrayList<Marker> holeMarkers = new ArrayList<>() ;

    private OnGCMapFragmentInteraction mListener ;

    public interface OnGCMapFragmentInteraction {
        Course getCourse() ;
        void setGolfCourseMapFragment(GolfCourseMapFragment gcMapFragment) ;
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
    public static GolfCourseMapFragment newInstance() {
        GolfCourseMapFragment fragment = new GolfCourseMapFragment() ;
        return fragment ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get arguments here if required
        }
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
        mListener.setGolfCourseMapFragment(this);
    }

    /**
     * Overriding the onStop call to remove (i.e. set = null) the golfCourseMapFragment of the
     * HomeActivity when this fragment is stopped
     */
    @Override
    public void onStop() {
        super.onStop();
        mListener.setGolfCourseMapFragment(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGCMapFragmentInteraction) {
            mListener = (OnGCMapFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLaunchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * Callback method for when the map is ready
     * @param googleMap the {@link GoogleMap} instance which will be used in the fragment
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap ;
        addCourseMarkers() ;
    }

    /**
     * Method to add all the markers for a course
     */
    private void addCourseMarkers() {
        course = mListener.getCourse() ;
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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_golf_course_black_36dp)) ;
        Marker holeMarker = map.addMarker(mops) ;
    }

    /**
     * @param pub The pub to which the map should now focus
     */
    public void goToPub(Hole.Pub pub) {
        if (map != null) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pub.getLocation(), 17f));
        }
    }
}
