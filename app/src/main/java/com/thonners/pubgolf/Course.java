package com.thonners.pubgolf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class to hold all the details required for a course.
 *
 * @author M Thomas
 * @since 26/03/17
 */

public class Course  {

    private final int defaultCourseLength = 9 ;

    private String name ;
    private ArrayList<Hole> course = new ArrayList<>();

    public Course(String name) {
        this.name = name ;
    }

    /**
     * @param newHole Hole to be added to the course
     */
    public void addHole(Hole newHole) {
        course.add(newHole);
    }

    /**
     * Method to return the hole instance for a given hole number. Returns the Hole at the index of
     * holeNo - 1 as holes are base 1, whilst indices are base 0.
     *
     * @param holeNo The number of the hole in the course
     * @return The {@link Hole} instance of the appropriate number
     */
    public Hole getHole(int holeNo) {
        return course.get(holeNo - 1);
    }

    /**
     * @return The ArrayList of all the {@link Hole}s in the course
     */
    public ArrayList<Hole> getCourse() {
        return course ;
    }

    /**
     * @return The name of the course
     */
    public String getName() {
        return name;
    }

    /*
     * DELETE THE BELOW IF PARCELABLE NOT USED
     *
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeParcelable(course);
    }
     *
     */
}
