/**
 * Item.java serves as the blueprint for each instance (objects) of an Item.
 * Holds the properties and behaviours of an Item.
 */
package Item;

import java.util.ArrayList;

/**
 * Represents a general Item in the Pokémon system with basic properties such as name,
 * category, description, effect, and pricing.
 * This class serves as the superclass for more specific item types like
 * Vitamins, Feathers, LevelingItems, and EvolutionStones.
 */
public class Item {
    /**
     * Item Attributes
     */
    protected String name, category, description, effect;
    protected double buyingPrice, sellingPrice;

    /**
     * Item Constructor
     * @param name
     * @param category
     * @param description
     * @param effect
     * @param buyingPrice
     * @param sellingPrice
     */
    public Item(String name, String category, String description, String effect,
                double buyingPrice, double sellingPrice) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.effect = effect;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    /**
     * Creates a copy of this item.
     *
     * @return a new Item with the same properties
     */
    public Item clone() {
        return new Item(this.getName(), this.getCategory(), this.description, this.getEffect(), this.getBuyingPrice(), this.getSellingPrice());
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
    public static ArrayList<Item> initializeItemList(){
        ArrayList<Item> allItems = new ArrayList<>();
        allItems.addAll(Vitamin.getAll());
        allItems.addAll(LevelingItem.getAll());
        allItems.addAll(Feather.getAll());
        allItems.addAll(EvolutionStone.getAll());
        return allItems;
    }

    /**
     * Returns a string representation of this object, typically used when
     * displaying it in UI components such as combo boxes or lists.
     *
     * @return the name of the object.
     */
    @Override
    public String toString() {
        return name; // or name + " (Lv. " + baseLvl + ")" for Pokémon
    }
}