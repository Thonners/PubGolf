package com.thonners.pubgolf;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A Fragment to hold and display the scorecard.
 *
 * @author M Thomas
 * @since 28/03/17
 */
public class ScorecardFragment extends Fragment implements ScorecardLayout.OnScorecardLayoutInteractionListener{

    private final String LOG_TAG = "ScorecardFragment" ;
    private Course course ;
    private ScorecardLayout scorecardLayout ;

    private OnScorecardFragmentInteractionListener mListener;

    public ScorecardFragment() {
        // Required empty public constructor - called if the fragment is destroyed by the Android framework
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScorecardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScorecardFragment newInstance() {
        ScorecardFragment fragment = new ScorecardFragment();
       /* Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {/*
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scorecard, container, false);
        scorecardLayout = (ScorecardLayout) view.findViewById(R.id.scorecard_layout);
        course = mListener.getCourse() ;
        scorecardLayout.setOnScorecardLayoutInteractionListener(this);
        return view ;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScorecardFragmentInteractionListener) {
            mListener = (OnScorecardFragmentInteractionListener) context;
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnScorecardFragmentInteractionListener {
        Course getCourse();
        void goToPub(Hole.Pub pub) ;
    }

    /**
     * Method to trigger adding the views to the scorecard.
     *
     * Requires separate call so that the subsequent population of the scorecard can happen after
     * the header row has been drawn, and therefore the calls of getWidth() return correctly.
     */
    @Override
    public void populateScorecard() {
        Log.d(LOG_TAG, "populateScorecard called");
        for (Hole hole : course.getHoles()) {
            scorecardLayout.addHole(hole);
        }
    }


}
