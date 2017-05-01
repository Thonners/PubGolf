package com.thonners.pubgolf;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Class to hold the details of a player
 *
 * @author M Thomas
 * @since 28/03/17.
 */

public class Player implements Parcelable {

    /**
     * The player type - either local or remote.
     * Includes method to turn player type into int, and getTypeFromInt, in order to make parcelling easier/more efficient
     * getTypeFromID(int id) throws IllegalArguementException if the id given does not match a known type.
     */
    public enum Type {
        LOCAL (0),
        REMOTE (1);

        private final int id;

        Type(int id) {
            this.id = id ;
        }

        public int getId() {
            return id ;
        }

        public static Type getTypeFromID(int id) {
            for (Type type : Type.values()) {
                if (type.getId() == id) {
                    return type ;
                }
            }
            throw new IllegalArgumentException("Unrecognised int for ID of Type enum: "  + id) ;
        }
    }

    private String name ;
    private Type type ;

    private final String LOG_TAG = "Player" ;

    /**
     * Constructor
     * @param name The name of the player to be created
     * @param type The {@link Type} of player to be created
     */
    public Player(String name, Type type) {
        this.name = name ;
        this.type = type ;
    }

    /**
     * Constructor, defaults to {link Type#LOCAL} player type
     * @param name The name of the player to be created
     */
    public Player(String name) {
        this.name = name ;
        this.type = Type.LOCAL ;
    }

    public Player(Parcel in) {
        this.name = in.readString();
        try {
            this.type = Type.getTypeFromID(in.readInt());
        } catch (IllegalArgumentException e) {
            Log.e(LOG_TAG, "Error parsing player type from parcelable. Exception error:\n" + e.getMessage()) ;
            Log.d(LOG_TAG, "Setting type to local as type couldn't be parsed from parcel.") ;
            this.type = Type.LOCAL;
        }
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(type.getId());
    }


    /**
     * Parcelable CREATOR for the Course class.
     */
    public static final Player.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        /**
         * Calls the private constructor to make a Course instance from a Parcel
         * @param in The Parel instance
         * @return The newly created Course instance
         */
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in) ;
        }

        /**
         * Constructor to create an array (? Not really sure what this is for)
         * @param size Size of the array to be created
         * @return The Player Array
         */
        @Override
        public Player[] newArray(int size) {
            return new Player[size] ;
        }
    } ;


}
