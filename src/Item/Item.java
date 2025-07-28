/**
 * Item.java serves as the blueprint for each instance (objects) of an Item.
 * Holds the properties and behaviours of an Item.
 */
package Item;

import java.util.ArrayList;

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
}