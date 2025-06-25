import java.util.Scanner;

import Menu.ItemMenu;
import Menu.PokemonMenu;
import Menu.MoveMenu;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PokemonMenu pokemonMenu = new PokemonMenu();
        MoveMenu moveMenu = new MoveMenu();
        ItemMenu itemMenu = new ItemMenu();
        boolean exit = false;
        int choice;
        while(!exit){
            System.out.println("\n=== POKEDEX MENU ===");
            System.out.println("1. Manage Pokemons");
            System.out.println("2. Manage Moves");
            System.out.println("3. Manage Items");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    pokemonMenu.run();
                    break;
                case 2:
                    moveMenu.run();
                    break;
                case 3:
                    itemMenu.run();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting Pokedex. See you again!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
