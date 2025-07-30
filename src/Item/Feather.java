package Item;

import java.util.ArrayList;

/**
 * The Feather class represents a consumable item used to slightly increase
 * a specific EV (Effort Value) stat of a Pokémon.
 */
public class Feather extends Item {
    public Feather(String name, String description, String effect, double buyingPrice, double sellingPrice){
        super(name, "Feather", description, effect, buyingPrice, sellingPrice);
    }

    /**
     * Returns a list of all predefined feather items.
     */
    public static ArrayList<Item> getAll() {
        ArrayList<Item> feathers = new ArrayList<>();
        feathers.add(new Feather("Health Feather","A feather that slightly increases HP.", "+1 HP EV",
                300.00, 150.00));
        feathers.add(new Feather("Muscle Feather", "A feather that slightly increases Attack.", "+1 Attack EV",
                300.00, 150.00));
        feathers.add(new Feather("Resist Feather", "A feather that slightly increases Defense.", "+1 Defense EV",
                300.00, 150.00));
        feathers.add(new Feather("Swift Feather", "A feather that slightly increases Speed.", "+1 Speed EV",
                300.00, 150.00));
        return feathers;
    }
}