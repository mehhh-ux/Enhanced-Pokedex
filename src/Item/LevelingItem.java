package Item;

import java.util.ArrayList;

public class LevelingItem extends Item {
    public LevelingItem(String name, String description, String effect, double buyingPrice, double sellingPrice){
        super(name, "Leveling Item", description, effect, buyingPrice, sellingPrice);
    }

    public static ArrayList<Item> getAll(){
        ArrayList<Item> levelingItems = new ArrayList<>();
        levelingItems.add(new LevelingItem("Rare Candy","A candy that is packed with energy.", "Increases level by 1",
                0.00, 2400.00));
        return levelingItems;
    }
}
