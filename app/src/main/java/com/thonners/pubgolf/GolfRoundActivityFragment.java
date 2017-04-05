package com.thonners.pubgolf;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment to hold the tabbed viewpager containing Scorecard and Map layout fragments.
 *
 * @author M Thomas
 * @since 04/04/17
 */
public class GolfRoundActivityFragment extends Fragment implements Footer.FooterInteractionListener {

    private final String LOG_TAG = "GolfRoundFragment" ;
    private static final int SCORECARD_FOOTER_BUTTON_ID = 0 ;
    private static final int MAP_FOOTER_BUTTON_ID = 1 ;

    private Footer footer ;
    private ViewPager viewPager ;
    private GolfRoundViewPagerAdapter pagerAdapter ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

  //  private OnFragmentInteractionListener mListener;

    public GolfRoundActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GolfRoundActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GolfRoundActivityFragment newInstance(String param1, String param2) {
        GolfRoundActivityFragment fragment = new GolfRoundActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_golf_round, container, false);

        // Get the view instances
        viewPager = (ViewPager) view.findViewById(R.id.fragment_view_pager) ;
        footer = (Footer) view.findViewById(R.id.footer) ;
        footer.setFooterInteractionListener(this);

        // Create the pager adapter and set the viewPager to use it
        pagerAdapter = new GolfRoundViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        // Prevent the viewPager destroying fragments/views when they're offscreen
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        // Create the footer buttons
        createFooterButtons() ;

        return view ;
    }

    /**
     * Method to populate the footer with the required buttons
     */
    private void createFooterButtons() {
        // Scorecard button
        footer.addButton(getString(R.string.footer_button_scorecard), R.drawable.ic_content_paste_black_36dp);
        // Map button
        footer.addButton(getString(R.string.footer_button_map), R.drawable.ic_golf_course_black_36dp);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    /*    if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void footerButtonClicked(int buttonID) {
        switch (buttonID) {
            default:
                // Catch anything else at this stage and let it fall through to scorecard
            case SCORECARD_FOOTER_BUTTON_ID:
                Log.d(LOG_TAG,"Scorecard footer button clicked") ;
                viewPager.setCurrentItem(SCORECARD_FOOTER_BUTTON_ID);
                break;
            case MAP_FOOTER_BUTTON_ID:
                Log.d(LOG_TAG,"Map footer button clicked") ;
                viewPager.setCurrentItem(MAP_FOOTER_BUTTON_ID);
                break;
        }
    }
}
