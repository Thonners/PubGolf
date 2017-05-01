package com.thonners.pubgolf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to hold all the details required for a holes.
 *
 * @author M Thomas
 * @since 26/03/17
 */

public class Course implements Parcelable {

    private final int defaultCourseLength = 9 ;

    private int id ;
    private String name ;
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
//    private HashMap<Player, ArrayList<Integer>> scores = new HashMap<>() ;  // Key=Player, ArrayList<Integer> = scores for each hole. Hole1=Index0 - need to implement writing/recovering from

    /**
     * Constructor
     * @param id Course Unique ID
     * @param name Name of the course
     */
    public Course(int id, String name) {
        this.id = id ;
        this.name = name ;
    }
    /**
     * Parcelable Constructor
     * @param in The Parcel containing the info
     */
    public Course(Parcel in) {
        this.id = in.readInt() ;
        this.name = in.readString() ;
        in.readTypedList(this.holes, Hole.CREATOR) ;
        in.readTypedList(this.players, Player.CREATOR);
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

    /**
     * @return This course's unique ID
     */
    public int getId() {
        return id;
    }

    public void addPlayers(ArrayList<String> playerNames) {
        for (String name : playerNames) {
            players.add(new Player(name)) ;
            //scores.put(new Player(name), new ArrayList<Integer>()) ;
        }
    }

    /**
     * METHOD ONLY FOR TESTING REMOTE PLAYERS IN THE LAYOUT. REMOVE THIS ONCE REMOTE PLAYERS IMPLEMENTED PROPERLY.
     */
    public void addDEBUGRemotePlayers(){
        players.add(new Player("Dave", Player.Type.REMOTE)) ;
        players.add(new Player("Barry", Player.Type.REMOTE)) ;
    }

    public ArrayList<Player> getPlayers() {
        return players;
        //return new ArrayList<>(scores.keySet()) ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(holes);
        dest.writeTypedList(players);

    }

    /**
     * Parcelable CREATOR for the Player class.
     */
    public static final Course.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        /**
         * Calls the private constructor to make a Course instance from a Parcel
         * @param in The Parcel instance
         * @return The newly created Course instance
         */
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in) ;
        }

        /**
         * Constructor to create an array (? Not really sure what this is for)
         * @param size Size of the array to be created
         * @return The Course Array
         */
        @Override
        public Course[] newArray(int size) {
            return new Course[size] ;
        }
    } ;

}
