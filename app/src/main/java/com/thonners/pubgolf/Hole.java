package com.thonners.pubgolf;

import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng ;

/**
 * Class to hold all the details of a specific hole.
 *
 * @author M Thomas
 * @since 26/03/17
 */
public class Hole {

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

    /**
     * Class to hold the details of the hole's pub/bar
     */
    public static class Pub {

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

        public void setName(String name) {
            this.name = name;
        }
    }




}
