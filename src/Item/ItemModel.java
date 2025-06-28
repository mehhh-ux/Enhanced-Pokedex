/**
 * ItemModel.java serves as the blueprint for each instance (objects) of an Item.
 * Holds the properties and behaviours of an Item.
 */
package Item;

import java.util.ArrayList;

public class ItemModel {
    /**
     * Item Attributes
     */
    private String name, category, description, effect;
    private double buyingPrice, sellingPrice;

    /**
     * Item Constructor
     * @param name
     * @param category
     * @param description
     * @param effect
     * @param buyingPrice
     * @param sellingPrice
     */
    public ItemModel(String name, String category, String description, String effect,
                double buyingPrice, double sellingPrice) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.effect = effect;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    /**
     * Getter
     * @return the name of the item in the Pokedex.
     */
    public String getName(){
        return name;
    }

    /**
     * Getter
     * @return the category of the item in the Pokedex.
     */
    public String getCategory(){
        return category;
    }

    /**
     * Getter
     * @return the description of the item in the Pokedex.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Getter
     * @return the effect of the item in the Pokedex.
     */
    public String getEffect(){
        return effect;
    }

    /**
     * Getter
     * @return the buying price of the item in the Pokedex.
     */
    public double getBuyingPrice(){
        return buyingPrice;
    }

    /**
     * Getter
     * @return the selling price of the item in the Pokedex.
     */
    public double getSellingPrice(){
        return sellingPrice;
    }

    /**
     * Initializes the instances of each item (this list is fixed).
     * @return the list of items.
     */
    public static ArrayList<ItemModel> initializeItemList(){
        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("HP Up", "Vitamin", "A nutritious drink for Pokémon.", "+10 HP EVs",
                10000.00, 5000.00));
        items.add(new ItemModel("Protein", "Vitamin", "A nutritious drink for Pokémon.", "+10 Attack EVs",
                10000.00, 5000.00));
        items.add(new ItemModel("Iron", "Vitamin", "A nutritious drink for Pokémon.", "+10 Defense EVs",
                10000.00, 5000.00));
        items.add(new ItemModel("Carbos", "Vitamin", "A nutritious drink for Pokémon.", "+10 Speed EVs",
                10000.00, 5000.00));
        items.add(new ItemModel("Rare Candy", "Leveling Item", "A candy that is packed with energy.", "Increases level by 1",
                0.00, 2400.00));
        items.add(new ItemModel("Health Feather", "Feather", "A feather that slightly increases HP.", "+1 HP EV",
                300.00, 150.00));
        items.add(new ItemModel("Muscle Feather", "Feather", "A feather that slightly increases Attack.", "+1 Attack EV",
                300.00, 150.00));
        items.add(new ItemModel("Resist Feather", "Feather", "A feather that slightly increases Defense.", "+1 Defense EV",
                300.00, 150.00));
        items.add(new ItemModel("Swift Feather", "Feather", "A feather that slightly increases Speed.", "+1 Speed EV",
                300.00, 150.00));
        items.add(new ItemModel("Zinc", "Vitamin", "A nutritious drink for Pokémon.", "+10 Special Defense EVs",
                10000.00, 5000.00));
        items.add(new ItemModel("Fire Stone", "Evolution Stone", "A stone that radiates heat.", "Evolves Pokémon like Vulpix, Growlithe, Eevee (into Flareon), etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Water Stone", "Evolution Stone", "A stone with a blue, watery appearance.", "Evolves Pokémon like Poliwhirl, Shellder, Eevee (into Vaporeon), etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Thunder Stone", "Evolution Stone", "A stone that sparkles with electricity.", "Evolves Pokémon like Pikachu, Eevee (into Jolteon), Eelektrik, etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Leaf Stone", "Evolution Stone", "A stone with a leaf pattern.", "Evolves Pokémon like Gloom, Weepinbell, Exeggcute, etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Moon Stone", "Evolution Stone", "A stone that glows faintly in the moonlight.", "Evolves Pokémon like Nidorina, Clefairy, Jigglypuff, etc.",
                0.00, 1500.00));
        items.add(new ItemModel("Sun Stone", "Evolution Stone", "A stone that glows like the sun.", "Evolves Pokémon like Gloom (into Bellossom), Sunkern, Cottonee, etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Shiny Stone", "Evolution Stone", "A stone that sparkles brightly.", "Evolves Pokémon like Togetic, Roselia, Minccino, etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Dusk Stone", "Evolution Stone", "A dark stone that is ominous in appearance.", "Evolves Pokémon like Murkrow, Misdreavus, Doublade, etc.",
                5000.00, 1500.00));
        items.add(new ItemModel("Dawn Stone", "Evolution Stone", "A stone that sparkles like the morning sky.", "Evolves male Kirlia into Gallade, female Snorunt into Froslass.",
                5000.00, 1500.00));
        items.add(new ItemModel("Ice Stone", "Evolution Stone", "A stone that is cold to the touch.", "Evolves Pokémon like Alolan Vulpix, Galarian Darumaka, Eevee (into Glaceon)",
                5000.00, 1500.00));
        return items;
    }
}