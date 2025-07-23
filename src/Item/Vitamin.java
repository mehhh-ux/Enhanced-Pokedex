package Item;
import java.util.ArrayList;

public class Vitamin extends Item {
    public Vitamin(String name, String description, String effect, double buyingPrice, double sellingPrice){
        super(name, "Vitamin", description, effect, buyingPrice, sellingPrice);
    }

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