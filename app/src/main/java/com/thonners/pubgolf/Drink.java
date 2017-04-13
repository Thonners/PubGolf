package com.thonners.pubgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to hold the details of the hole's drink
 *
 * @author M Thomas
 * @since 28/03/17
 */
public class Drink implements Parcelable{

    private String name ;
    private int par ;
    private Type drinkType ;

    public enum Type {
        PINT (0),
        WINE (1),
        SHORT (2),
        LONG (3),
        SHOT (4),
        GUINNESS_AND_GNT (5),
        BOTTLE (6);

        private final int id ;

        Type(int drinkId) {
            this.id = drinkId ;
        }

        /**
         * @return The enum's ID
         */
        private int id() {
            return id ;
        }

        /**
         * Method to get the enum Type from the ID
         * @param id The ID of the enum
         * @return The {@link Type} of drink referred to by the ID
         */
        public static Type getByID(int id) {
            for (Type t : values()) {
                if (t.id == id) return  t ;
            }
            return null ;
        }
    }
    /**
     * Constructor
     */
    public Drink(String name, Type drinkType, int par) {
        this.name = name ;
        this.drinkType = drinkType ;
        this.par = par ;
    }

    /**
     * @param drinkType The type of drink - used for the icon
     */
    public void setDrinkType(Type drinkType) {
        this.drinkType = drinkType;
    }

    /**
     * @return The name of the drink
     */
    public String getName() {
        return name;
    }

    /**
     * @return The par value - i.e. expected number of 'shots'/sips - for this drink
     */
    public int getPar() {
        return par;
    }

    /**
     * @return The type of drink - used for the icon
     */
    public Type getDrinkType() {
        return drinkType;
    }

    // Parcelable stuff below

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(par);
        dest.writeInt(drinkType.id());
    }

    /**
     * Constructor, for use by the Parcelable creator
     * @param in The parcel containing the info
     */
    private Drink(Parcel in) {
        this.name = in.readString() ;
        this.par = in.readInt() ;
        this.drinkType = Type.getByID(in.readInt()) ;
    }


    /**
     * Parcelable CREATOR for the Drink class.
     */
    public static final Drink.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        /**
         * Calls the private constructor to make a Pub instance from a Parcel
         * @param in The Parcel instance
         * @return The newly created Drink instance
         */
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in) ;
        }

        /**
         * Constructor to create an array (? Not really sure what this is for)
         * @param size Size of the array to be created
         * @return The Drink Array
         */
        @Override
        public Drink[] newArray(int size) {
            return new Drink[size] ;
        }
    } ;

}
