package Item;

public class ItemModel {
    private String name;
    private String category;
    private String description;
    private String effect;
    private double buyingPrice;
    private double sellingPrice;

    //Constructor
    public ItemModel(String name, String category, String description, String effect,
                double buyingPrice, double sellingPrice) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.effect = effect;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    //Getters
    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    public String getEffect(){
        return effect;
    }

    public double getBuyingPrice(){
        return buyingPrice;
    }

    public double getSellingPrice(){
        return sellingPrice;
    }

    //Methods
    public void printHeader() {
        System.out.printf(
                "%-20s%-15s%-35s%-25s%-15s%-15s\n",
                "Name", "Category", "Description", "Effects", "Buying Price", "Selling Price"
        );
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    public void displayItemAttributes() {
        System.out.printf(
                "%-20s%-15s%-35s%-25s%-15s%-15s\n",
                name, category, description, effect, buyingPrice, sellingPrice);
    }
}
