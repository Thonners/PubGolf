package com.thonners.pubgolf;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * A fragment containing the main options from the home/launcher activity.
 *
 * @author M Thomas
 * @since 25/03/17
 */
public class LaunchActivityFragment extends Fragment implements View.OnClickListener,  DialogInterface.OnClickListener {

    private final String LOG_TAG = "LaunchActivityFragment" ;
    private OnLaunchFragmentInteractionListener mListener;

    private ArrayList<Course> availableCourses ;

    public LaunchActivityFragment() {
    }

    public static LaunchActivityFragment newInstance(String param1, String param2) {
        LaunchActivityFragment fragment = new LaunchActivityFragment();
        /*Bundle args = new Bundle();
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
        // Create the view
        View view = inflater.inflate(R.layout.fragment_launch, container, false);

        // Populate the cards with the appropriate text / icons
        populateCard((CardView) view.findViewById(R.id.card_play_golf)) ;
        populateCard((CardView) view.findViewById(R.id.card_scorecard_library)) ;
        populateCard((CardView) view.findViewById(R.id.card_download_courses)) ;
        populateCard((CardView) view.findViewById(R.id.card_custom_rules)) ;

        return view ;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLaunchFragmentInteractionListener) {
            mListener = (OnLaunchFragmentInteractionListener) context;
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
     */
    public interface OnLaunchFragmentInteractionListener {
        void launchNewGame(Course courseToLoad);
        void joinGame() ;
        ArrayList<Course> getCourses() ;
        void getMoreCourses() ;
    }
    /**
     * Method to show a dialog box to determine the type of game to begin.
     */
    private void showPlayGolfDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()) ;
        // Populate the popup
        builder.setTitle(R.string.dialog_new_round_title)
                .setMessage(R.string.dialog_new_round_message)
                .setPositiveButton(R.string.dialog_new_round_create, this)
                .setNegativeButton(R.string.dialog_new_round_join, this);
        // Create & show the dialog
        builder.create().show();
    }

    /**
     * Method to show a list of available courses
     */
    private void showSelectCourseDialog() {
        // Get the courses
        availableCourses = mListener.getCourses() ;
        // Turn them into a list we can use
        ArrayList<String> availableCourseNames = new ArrayList<>(availableCourses.size()) ;
        for (int i = 0 ; i < availableCourses.size() ; i++) {
            availableCourseNames.add(availableCourses.get(i).getName()) ;
        }
        CharSequence[] courses = availableCourseNames.toArray(new CharSequence[availableCourseNames.size()]) ;

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()) ;
        // Populate the popup
        builder.setTitle(R.string.dialog_select_course_title)
                .setItems(courses, this)
                .setNeutralButton("GET MORE COURSES", this);
        // Create & show the dialog
        builder.create().show();
    }
    /**
     * Method to populate the views and apply the onClickListener to the given CardView
     * @param view The instance of the launch_card_view to be populated
     */
    private void populateCard(CardView view) {
        // Get instances of the nested views
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_main_title) ;
        TextView tvSubtitle = (TextView) view.findViewById(R.id.tv_subtitle) ;
        ImageView img = (ImageView) view.findViewById(R.id.img_card_icon) ;
        // Apply the correct text / image to the views
        switch (view.getId()) {
            case R.id.card_play_golf:
                tvTitle.setText(getString(R.string.card_play_golf_title));
                tvSubtitle.setText(getString(R.string.card_play_golf_subtitle));
                img.setImageResource(R.drawable.ic_pub_golf);
                break ;
            case R.id.card_scorecard_library:
                tvTitle.setText(getString(R.string.card_scorecard_library_title));
                tvSubtitle.setText(getString(R.string.card_scorecard_library_subtitle));
                img.setImageResource(R.drawable.ic_library_books_black_36dp);
                break ;
            case R.id.card_download_courses:
                tvTitle.setText(getString(R.string.card_download_courses_title));
                tvSubtitle.setText(getString(R.string.card_download_courses_subtitle));
                img.setImageResource(R.drawable.ic_golf_course_black_36dp);
                break ;
            case R.id.card_custom_rules:
                tvTitle.setText(getString(R.string.card_custom_rules_title));
                tvSubtitle.setText(getString(R.string.card_custom_rules_subtitle));
                img.setImageResource(R.drawable.ic_content_paste_black_36dp);
                break ;
        }
        // Set the onClickListener
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_play_golf:
                Log.d(LOG_TAG,"Play Golf Clicked.");
                showPlayGolfDialog();
                break ;
            case R.id.card_scorecard_library:
                Log.d(LOG_TAG,"Scorecard Library Clicked.");
                break ;
            case R.id.card_download_courses:
                Log.d(LOG_TAG,"Download Courses Clicked.");
                break ;
            case R.id.card_custom_rules:
                Log.d(LOG_TAG,"Custom Rules Clicked.");
                break ;
        }
    }
    /**
     * Method to handle the outcome of the user selecting to create a new game or join an existing one.
     * @param dialog The dialog interface.
     * @param id The ID of the button clicked
     */
    @Override
    public void onClick(DialogInterface dialog, int id) {
        switch (id) {
            case BUTTON_NEGATIVE:
                // PlayGolfDialog - Join existing game
                Log.d(LOG_TAG, "Join game selected.") ;
                mListener.joinGame();
                break ;
            case BUTTON_POSITIVE:
                // PlayGolfDialog - Create game selected
                Log.d(LOG_TAG, "Create new game selected.") ;
                showSelectCourseDialog() ;
                break ;
            case BUTTON_NEUTRAL:
                // Select course Dialog - get more courses selected
                mListener.getMoreCourses() ;
                break;
            default :
                // Assume that this must be from the select course dialog, and that a course has been selected
                // Might be quite a risky way of doing things
                // Load the course selected
                mListener.launchNewGame(availableCourses.get(id));
        }
    }

}
