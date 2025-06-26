import java.util.Scanner;

import Menu.ItemMenu;
import Menu.PokemonMenu;
import Menu.MoveMenu;

public class PokedexDriver {
    /**
     * Instantiate an object for each class.
     */
    private PokemonMenu pokemonMenu = new PokemonMenu();
    private MoveMenu moveMenu = new MoveMenu();
    private ItemMenu itemMenu = new ItemMenu();
    private boolean exit = false;
    private int choice;
    private Scanner scanner = new Scanner(System.in);

    /**
     * The run() method is responsible for displaying the Pokedex Management Menu.
     */
    public void run(){

        while(!exit){
            System.out.println("=========================================");
            System.out.println("         Welcome to the Pokedex!         ");
            System.out.println("=========================================");
            System.out.println("1. Pokemon Menu");
            System.out.println("2. Move Menu");
            System.out.println("3. Item Menu");
            System.out.println("4. Exit");
            System.out.println("-----------------------------------------");
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
                    System.out.println("Invalid choice. Try again.\n");
            }
        }
    }

    public static void main(String[] args) {
        PokedexDriver driver = new PokedexDriver();
        driver.run();
    }
}
