package com.thonners.pubgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to hold the details of a player
 *
 * @author M Thomas
 * @since 28/03/17.
 */

public class Player implements Parcelable {

    public enum Type {
        LOCAL,
        REMOTE
    }

    private String name ;
    private Type type ;

    public Player(String name) {
        this.name = name ;
    }

    public Player(Parcel in) {
        this.name = in.readString();
        this.type = (Type) in.readSerializable() ;
    }

    public String getName() {
        return name;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeSerializable(type);
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
