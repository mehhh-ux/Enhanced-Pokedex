package Item;
import java.util.ArrayList;

/**
 * Represents a Vitamin item used to increase a Pokémon's Effort Values (EVs).
 * <This class extends the Item class and defines a category of
 * stat-boosting items that improve a Pokémon's performance in specific areas.
 */
public class Vitamin extends Item {
    public Vitamin(String name, String description, String effect, double buyingPrice, double sellingPrice){
        super(name, "Vitamin", description, effect, buyingPrice, sellingPrice);
    }

    /**
     * Initializes and returns a list of predefined Vitamin items.
     *
     * @return a list containing all Vitamin items
     */
    public static ArrayList<Item> getAll() {
        ArrayList<Item> vitamins = new ArrayList<>();
        vitamins.add(new Vitamin("HP Up", "A nutritious drink for Pokémon.", "+10 HP EVs", 10000.00, 5000.00));
        vitamins.add(new Vitamin("Protein", "A nutritious drink for Pokémon.", "+10 Attack EVs", 10000.00, 5000.00));
        vitamins.add(new Vitamin("Iron", "A nutritious drink for Pokémon.", "+10 Defense EVs", 10000.00, 5000.00));
        vitamins.add(new Vitamin("Carbos", "A nutritious drink for Pokémon.", "+10 Speed EVs", 10000.00, 5000.00));
        vitamins.add(new Vitamin("Zinc", "A nutritious drink for Pokémon.", "+10 Special Defense EVs", 10000.00, 5000.00));
        return vitamins;
    }
}