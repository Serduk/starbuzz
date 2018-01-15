package com.serdyuk.starbuzz;

/**
 * Simple class where from we fetch drinks data
 * Each object contain drink name, description, and resource ID for IMG
 *
 * Created by sserdiuk on 1/15/18.
 */

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

//    drinks -> array with Drinks elements
    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk",
                    R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam",
                    R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh",
                    R.drawable.filter)
    };

//    for each drinks saved names, description and resources
    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String toString() {
        return this.name;
    }
}
