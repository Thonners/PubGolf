package com.thonners.pubgolf;

import java.util.ArrayList;

/**
 * Class to hold all the details required for a course.
 *
 * @author M Thomas
 * @since 26/03/17
 */

public class Course {

    private final int defaultCourseLength = 9 ;

    private ArrayList<Hole> course = new ArrayList<>();

    public Course() {

    }

    /**
     * @param newHole Hole to be added to the course
     */
    public void addHole(Hole newHole) {
        course.add(newHole);
    }

    /**
     * @return The ArrayList of all the {@link Hole}s in the course
     */
    public ArrayList<Hole> getCourse() {
        return course ;
    }
}
