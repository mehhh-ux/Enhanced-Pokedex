package Item;

import java.util.ArrayList;
import java.util.Scanner;

public class ItemView {
    private Scanner scanner = new Scanner(System.in);

    public void printAllItems(ArrayList<ItemModel> items) {
        if (items.isEmpty()) {
            System.out.println("No items in the Pokedex.");
            return;
        }

        items.getFirst().printHeader();
        for (ItemModel i : items) {
            i.displayItemAttributes();
        }
    }

    public String promptSearchKey() {
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }
}
