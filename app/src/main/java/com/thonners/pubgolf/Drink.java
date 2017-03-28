package com.thonners.pubgolf;

/**
 * Class to hold the details of the hole's drink
 *
 * @author M Thomas
 * @since 28/03/17
 */
public class Drink {

    private String name ;
    private int par ;
    private Type drinkType ;

    public enum Type {
        PINT, WINE, SHORT, LONG, SHOT, GUINNESS_AND_GNT
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

}
