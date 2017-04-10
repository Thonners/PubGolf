package com.thonners.pubgolf;

import com.google.android.gms.maps.model.LatLng;

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
        mbLinks.addHole(new Hole(new Hole.Pub("The Barley Mow", new LatLng(51.512913,-0.150978)), 1, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("Henry Holland", new LatLng(51.514689, -0.151431)), 2, new Drink("Lager", Drink.Type.PINT,4)));
        mbLinks.addHole(new Hole(new Hole.Pub("Devonshire Arms", new LatLng(51.51602, -0.152169)), 3, new Drink("Bitter", Drink.Type.PINT,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Coach Makers", new LatLng(51.51717, -0.150576)), 4, new Drink("Cider", Drink.Type.PINT,5)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Golden Eagle", new LatLng(51.517681,-0.150804)), 5, new Drink("Mulled wine", Drink.Type.WINE,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Angel in the Fields", new LatLng(51.518093,-0.151408)), 6, new Drink("Guinness + G&T", Drink.Type.GUINNESS_AND_GNT,2)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Gunmakers", new LatLng(51.51923,-0.153173)), 7, new Drink("Alcopop", Drink.Type.BOTTLE,1)));
        mbLinks.addHole(new Hole(new Hole.Pub("The Marylebone", new LatLng(51.519906,-0.15205)), 8, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks.addHole(new Hole(new Hole.Pub("The King's Head", new LatLng(51.519653,-0.150181)), 9, new Drink("IPA", Drink.Type.PINT,3)));

        // Add it to the list
        addCourse(mbLinks);

        Course mbLinks2 = new Course("Marylebone Links Back 9");
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 1, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 2, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 3, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 4, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 5, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 6, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 7, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 8, new Drink("Ale", Drink.Type.PINT,3)));
        mbLinks2.addHole(new Hole(new Hole.Pub("Bloomsbury Lanes", new LatLng(51.5239426,-0.128537)), 9, new Drink("Ale", Drink.Type.PINT,3)));

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
