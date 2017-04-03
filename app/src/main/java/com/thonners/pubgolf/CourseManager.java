package com.thonners.pubgolf;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class to manage sourcing, creating and downloading courses.
 *
 * @author M Thomas
 * @since 03/04/17
 */

public class CourseManager {

    private ArrayList<Course> courses = new ArrayList<>() ;

    /**
     * Constructor
     */
    public CourseManager() {
        createDefaultCourse() ;
    }

    /**
     * Method to create the default Marylebone Links course
     */
    private void createDefaultCourse() {
        Course mbLinks = new Course("Marylebone Links");
        mbLinks.addHole(new Hole(new Hole.Pub("The Barley Mow"), 1, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("Henry Holland"), 2, new Drink("Lager", Drink.Type.PINT,4)));
        mbLinks.addHole(new Hole(new Hole.Pub("Devonshire Arms"), 3, new Drink("Bitter", Drink.Type.PINT,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Coach Makers"), 4, new Drink("Cider", Drink.Type.PINT,5)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Golden Eagle"), 5, new Drink("Mulled wine", Drink.Type.WINE,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Angel in the Fields"), 6, new Drink("Guinness + G&T", Drink.Type.GUINNESS_AND_GNT,2)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Gunmakers"), 7, new Drink("Alcopop", Drink.Type.BOTTLE,1)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Marylebone"), 8, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("The King's Head"), 9, new Drink("IPA", Drink.Type.PINT,3)));

        // Add it to the list
        addCourse(mbLinks);

        Course mbLinks2 = new Course("Marylebone Links Back 9");
        mbLinks2.addHole(new Hole(new Hole.Pub("The Barley Mow"), 1, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Henry Holland"), 2, new Drink("Lager", Drink.Type.PINT,4)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Devonshire Arms"), 3, new Drink("Bitter", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("The Coach Makers"), 4, new Drink("Cider", Drink.Type.PINT,5)));
        mbLinks2.addHole(new Hole(new Hole.Pub("The Golden Eagle"), 5, new Drink("Mulled wine", Drink.Type.WINE,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("The Angel in the Fields"), 6, new Drink("Guinness + G&T", Drink.Type.GUINNESS_AND_GNT,2)));
        mbLinks2.addHole(new Hole(new Hole.Pub("The Gunmakers"), 7, new Drink("Alcopop", Drink.Type.BOTTLE,1)));
        mbLinks2.addHole(new Hole(new Hole.Pub("The Marylebone"), 8, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("The King's Head"), 9, new Drink("IPA", Drink.Type.PINT,3)));

        // Add it to the list
        addCourse(mbLinks2);
    }

    /**
     * Method to create a course from the JSON object
     *
     * TODO: Implement this or delete this method if it's not actually required.
     * @param courseObject The JSON representation of the course
     */
    public void createCourse(JSONObject courseObject) {

    }

    /**
     * @param course The course to be added to the list of available courses
     */
    public void addCourse(Course course) {
        courses.add(course) ;
    }

    /**
     * @return An ArrayList of all the available courses.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Method to add a hole to a course.
     * @param course The course to which the hole will be added
     * @param hole The hole to add to the course
     */
    public void addHoleToCourse(Course course, Hole hole) {
        course.addHole(hole);
    }


}
