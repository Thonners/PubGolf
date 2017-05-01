package com.thonners.pubgolf;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation_fade_in_slide_out;
        dialog.show() ;
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
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation_slide_in_slide_out;
        dialog.show() ;
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
                Log.d(LOG_TAG, "Create new game selected.");
                showSelectCourseDialog();
                break ;
            case BUTTON_NEUTRAL:
                // Select course Dialog - get more courses selected
                mListener.getMoreCourses() ;
                break;
            default :
                // Assume that this must be from the select course dialog, and that a course has been selected
                // Might be quite a risky way of doing things
                DialogFragment addPlayersDialog = AddPlayersDialog.newInstance(availableCourses.get(id)) ;
                addPlayersDialog.show(getFragmentManager(), "AddPlayersDialog");
        }
    }

    /**
     * AlertDialog to allow user to enter player names. Shows a single row to begin with, with a
     * button to add more rows for more players if required.
     */
    public static class AddPlayersDialog extends DialogFragment implements View.OnClickListener{

        private final static String LOG_TAG = "AddPlayersDialog" ;
        private final static String COURSE = "com.thonners.pubgolf.course" ;

        private LinearLayout mainLayout ;
        private final ArrayList<EditText> playerNameETs = new ArrayList<>() ;
        private AddPlayersDialogListener mListener = null;
        private Course course ;

        public interface AddPlayersDialogListener {
            void launchNewGame(Course course) ;
        }

        /**
         * newInstance constructor
         * @param course the golf course to be loaded
         * @return the fragment
         */
        public static AddPlayersDialog newInstance(Course course) {
            AddPlayersDialog frag = new AddPlayersDialog() ;
            Bundle args = new Bundle() ;
            args.putParcelable(COURSE, course);
            frag.setArguments(args);
            return frag ;
        }

        // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            // Verify that the host activity implements the callback interface
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (AddPlayersDialogListener) context;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(context.toString()
                        + " must implement NoticeDialogListener");
            }
        }

        /**
         * Set the positive button View.onClickListener() to be this
         *
         * onStart() is where dialog.show() is actually called on the underlying dialog, so we have
         * to do it there or later in the lifecycle.
         * Doing it in onResume() makes sure that even if there is a config change environment that
         * skips onStart then the dialog will still be functioning properly after a rotation.
         */
        @Override
        public void onResume()
        {
            super.onResume();
            final AlertDialog d = (AlertDialog)getDialog();
            if(d != null) {
                Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setId(BUTTON_POSITIVE);
                positiveButton.setOnClickListener(this);
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the course
            this.course = getArguments().getParcelable(COURSE);
// Debugging
Log.d(LOG_TAG, "When creating player name dialog, there are currently " + this.course.getPlayers().size() + " players in the course already.") ;
            try {
                Log.d(LOG_TAG, "Creating AddPlayersDialog...") ;
                // Get the listener
               // clickListener = (DialogInterface.OnClickListener) getParentFragment() ;
                // Create the parent view
                Log.d(LOG_TAG, "Creating main layout...") ;
                mainLayout = new LinearLayout(getContext());
                mainLayout.setOrientation(LinearLayout.VERTICAL);
                // Create the first row
                Log.d(LOG_TAG, "Adding first row...") ;
                addPlayerRow();

                // Use builder for convenient construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Set the content
                builder.setTitle(R.string.dialog_add_players_title)
                        .setView(mainLayout)
                        .setPositiveButton(R.string.dialog_play, null)
                        .setNegativeButton(R.string.dialog_cancel, null) ;

                // Set the entry / exit animation
                AlertDialog dialog = builder.create() ;
                dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation_slide_in_fade_out;

                return dialog ;

            } catch (ClassCastException e) {
                Log.d(LOG_TAG, "Error casting parent fragment to DialogInterface.OnClickListener when creating AddPlayersDialog: " + e.getMessage()) ;
            }
            return null ;
        }

        /**
         * Add a row to the view, populate it with the correct number, set the onClickListener for
         * the button, and retain the
         */
        private void addPlayerRow() {
            // Create the view
            View newRow = getActivity().getLayoutInflater().inflate(R.layout.dialog_frag_add_players,null) ;
            // Get the sub-view instances
            TextView tvNumber = (TextView) newRow.findViewById(R.id.tv_number) ;
            EditText etPlayerName = (EditText) newRow.findViewById(R.id.et_new_player) ;
            ImageView iv = (ImageView) newRow.findViewById(R.id.add_player_button) ;
            // Set the ID of the iv so we can get the appropriate edittext later
            iv.setId(playerNameETs.size());
            // Add the editText to the arrayList
            playerNameETs.add(etPlayerName) ;
            // Request focus for ET
            etPlayerName.requestFocus();
            // Set the numberTV to the size of the playerNameETs arrayList - this will give the player number, whilst its index is this number - 1
            tvNumber.setText(playerNameETs.size() + ": ");
            // Set the onClickListener for the button
            iv.setOnClickListener(this);
            // Add it to the main view
            newRow.setAlpha(0.0f);
            newRow.setTranslationY(-1 * getResources().getDimension(R.dimen.dialog_entry_anim_y_offset));
            mainLayout.addView(newRow);
            // Animate its entry
            newRow.animate()
                    .setInterpolator(new DecelerateInterpolator())
                    .alpha(1.0f)
                    .translationY(0.0f)
                    .setDuration(300)
                    .start();
        }

        /**
         * The onClick called when a user clicks the 'add new row'. Adds a new row and animates
         * the exit of the button just clicked.
         * @param view The 'add player' button which has just been clicked.
         */
        @Override
        public void onClick(View view) {
            if (view instanceof ImageView) {
                // Check editText isn't empty. If it is, show Toast and return (i.e. take no further action)
                if (playerNameETs.get(view.getId()).getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), R.string.dialog_add_players_empty_name_toast, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Add clicked, so add another row
                addPlayerRow();
                // Hide this button
                view.animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .start();
                view.setOnClickListener(null);
            } else if (view instanceof Button){
                // It's a button on the dialog - switch to find out which one
                switch (view.getId()) {
                    case BUTTON_POSITIVE:
                        ArrayList<String> playerNames = getPlayerNames();
                        if (!playerNames.isEmpty()) {
                            // Hide this dialog
                            if (getDialog() != null ) getDialog().dismiss();
                            // If there's at least one name, add players to the course/game
                            course.addPlayers(playerNames);
                            // TODO: Remove this once remote players implemented properly
                            course.addDEBUGRemotePlayers();
                            // Start the game!
                            mListener.launchNewGame(course);
                        } else {
                            // Show a toast that we need a name!
                            Toast.makeText(getContext(), R.string.dialog_add_players_empty_name_toast_play, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Log.d(LOG_TAG, "Unrecognised id of view clicked... ID = " + view.getId()) ;
                        break;
                }

            }
        }

        /**
         * @return An ArrayList of the names entered in the EditTexts
         */
        public ArrayList<String> getPlayerNames() {
            ArrayList<String> playerNames = new ArrayList<>(playerNameETs.size()) ;
            for (EditText et : playerNameETs) {
                String name = et.getText().toString() ;
                if (!name.isEmpty()) {
                    playerNames.add(name) ;
                } else {
                    Log.d(LOG_TAG,"Name extracted from EditText was empty, so won't be added to the final names list");
                }
            }
            return playerNames;
        }

        public Course getCourse() {
            return course;
        }

    }
}
