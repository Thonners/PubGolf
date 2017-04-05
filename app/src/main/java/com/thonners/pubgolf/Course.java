package com.thonners.pubgolf;

import java.util.ArrayList;

/**
 * Class to hold all the details required for a holes.
 *
 * @author M Thomas
 * @since 26/03/17
 */

public class Course  {

    private final int defaultCourseLength = 9 ;

    private String name ;
    private ArrayList<Hole> holes = new ArrayList<>();

    public Course(String name) {
        this.name = name ;
    }

    /**
     * @param newHole Hole to be added to the holes
     */
    public void addHole(Hole newHole) {
        holes.add(newHole);
    }

    /**
     * Method to return the hole instance for a given hole number. Returns the Hole at the index of
     * holeNo - 1 as holes are base 1, whilst indices are base 0.
     *
     * @param holeNo The number of the hole in the holes
     * @return The {@link Hole} instance of the appropriate number
     */
    public Hole getHole(int holeNo) {
        return holes.get(holeNo - 1);
    }

    /**
     * @return The ArrayList of all the {@link Hole}s in the holes
     */
    public ArrayList<Hole> getHoles() {
        return holes;
    }

    /**
     * @return The name of the holes
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
        //dest.writeParcelable(holes);
    }
     *
     */
}
