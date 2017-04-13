package com.thonners.pubgolf;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng ;

/**
 * Class to hold all the details of a specific hole.
 *
 * @author M Thomas
 * @since 26/03/17
 */
public class Hole implements Parcelable{

    private int holeNo ;
    private Pub pub ;
    private Drink drink ;
    private int shots ;

    /**
     * Constructor
     */
    public Hole(Pub pub, int number, Drink drink) {
        this.pub = pub ;
        this.holeNo = number ;
        this.drink = drink ;
    }

    /**
     * Parcelable constructor
     * @param in The Parcel
     */
    public Hole(Parcel in) {
        this.holeNo = in.readInt() ;
        this.pub = in.readParcelable(Pub.class.getClassLoader()) ;
        this.drink = in.readParcelable(Drink.class.getClassLoader()) ;
        this.shots = in.readInt() ;
    }

    public String getPubName() {
        return pub.getName() ;
    }

    /**
     * @return The drink for this hole
     */
    public Drink getDrink() {
        return drink;
    }

    /**
     * @param drink The drink for this hole
     */
    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    /**
     * @return The hole number in the round
     */
    public int getHoleNo() {
        return holeNo;
    }

    /**
     * @param holeNo The hole number in the round.
     */
    public void setHoleNo(int holeNo) {
        this.holeNo = holeNo;
    }

    /**
     * @return The par value for this hole
     */
    public int getPar() {
        return drink.getPar();
    }

    /**
     * @return The number of shots taken by the player to complete the hole.
     */
    public int getShots() {
        return shots;
    }

    /**
     * @param shots The number of shots taken by the player to complete the hole.
     */
    public void setShots(int shots) {
        this.shots = shots;
    }

    public Pub getPub() {
        return pub;
    }


    // Parcelable stuff below...
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write the details to a parcel
     * @param dest The parcel to be written to
     * @param flags Parcel flags (?)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(holeNo);
        dest.writeParcelable(pub,flags);
        dest.writeParcelable(drink,flags);
        dest.writeInt(shots);
    }


    /**
     * Parcelable CREATOR for the Hole class.
     */
    public static final Hole.Creator<Hole> CREATOR = new Parcelable.Creator<Hole>() {
        /**
         * Calls the private constructor to make a Pub instance from a Parcel
         * @param in The Parel instance
         * @return The newly created Pub instance
         */
        @Override
        public Hole createFromParcel(Parcel in) {
            return new Hole(in) ;
        }

        /**
         * Constructor to create an array (? Not really sure what this is for)
         * @param size Size of the array to be created
         * @return The Pub Array
         */
        @Override
        public Hole[] newArray(int size) {
            return new Hole[size] ;
        }
    } ;

    /**
     * Class to hold the details of the hole's pub/bar
     */
    public static class Pub implements  Parcelable{

        private LatLng location ;
        private String name ;

        /**
         * Constructor
         */
        public Pub(String name, LatLng location) {
            this.name = name ;
            this.location = location ;
        }

        /**
         * @return Pub's location
         */
        public LatLng getLocation() {
            return location;
        }

        /**
         * @param location Pub's location
         */
        public void setLocation(LatLng location) {
            this.location = location;
        }

        /**
         * @return The name of the establishment
         */
        public String getName() {
            return name;
        }

        /**
         * @param name Name of the establishment
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Parcelable Writer
         * @param dest
         * @param flags
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeParcelable(location, flags);
        }

        /**
         * Parcelable writer - describe contents
         * @return
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Constructor for parcelable
         * @param in The Parcel containing the info to rebuild the instance
         */
        private Pub(Parcel in) {
            this.name = in.readString() ;
            this.location = in.readParcelable(LatLng.class.getClassLoader()) ;
        }

        /**
         * Parcelable CREATOR for the Pub class.
         */
        public static final Pub.Creator<Pub> CREATOR = new Parcelable.Creator<Pub>() {
            /**
             * Calls the private constructor to make a Pub instance from a Parcel
             * @param in The Parel instance
             * @return The newly created Pub instance
             */
            @Override
            public Pub createFromParcel(Parcel in) {
                return new Pub(in) ;
            }

            /**
             * Constructor to create an array (? Not really sure what this is for)
             * @param size Size of the array to be created
             * @return The Pub Array
             */
            @Override
            public Pub[] newArray(int size) {
                return new Pub[size] ;
            }
        } ;
    }




}
