/**
 * PokemonMenu.java is the class that controls the main loop of the pokemon menu.
 * An MVC design-based management system for Pokemon.
 * Also responsible for exiting out of the pokemon menu.
 */
package Menu;

/**
 * Import Pokemon MVC.
 */
import Pokemon.PokemonController;
import Pokemon.PokemonModel;
import Pokemon.PokemonView;

import java.util.ArrayList;
import java.util.Scanner;

public class PokemonMenu {
    /**
     * Instantiation and initialization of objects and variables.
     */
    private PokemonView view = new PokemonView();
    private PokemonController controller = new PokemonController();
    private Scanner scanner = new Scanner(System.in);
    private int option, pokedexNum;
    private String key = "", name;
    private ArrayList<PokemonModel> results = new ArrayList<>();
    boolean running;

    /**
     * The run() method is responsible for displaying the Pokemon Management Menu.
     */
    public void run(){
        running = true;

        /**
         * This is the main loop of the menu.
         * Runs the appropriate method based on user input.
         * If 1, then create and add a new pokemon into the Pokedex.
         * If 2, then print all pokemons in the Pokedex.
         * If 3, then search for a pokemon based on the user's input (key) then print results.
         * If 4, then return to main menu (PokedexDriver.java).
         */
        while (running) {
            System.out.println("\n=========================================");
            System.out.println("       Welcome to the Pokemon Menu!      ");
            System.out.println("=========================================");
            System.out.println("1. Add Pokémon");
            System.out.println("2. Show All Pokémon");
            System.out.println("3. Search Pokémon");
            System.out.println("4. Exit");
            System.out.println("-----------------------------------------");
            System.out.print("Enter your choice: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\nYou are in the process of adding a pokemon!");
                    System.out.println("----------------------------------------------");
                    pokedexNum = view.promptPokedexNumber();
                    if (controller.pokemonNumIsDup(pokedexNum))
                    {
                        view.showDuplicationErrorMessage("number");
                    }else{
                        name = view.promptPokemonName();
                        if (controller.pokemonNameIsDup(name)) {
                            view.showDuplicationErrorMessage("name");
                        }else if (controller.addPokemon(view.promptRemainingPokemonData(pokedexNum, name))) {
                            view.successfulPokemonAddMessage(name);
                        }
                    }
                    break;
                case 2:
                    view.printAllPokemons(controller.getAllPokemon(), key);
                    view.pressAnyKeyPrompt();
                    break;
                case 3:
                    key = view.promptSearchKey();
                    results = controller.searchPokemon(key);
                    view.printAllPokemons(results, key);
                    if (!results.isEmpty()) {
                        view.pressAnyKeyPromptSearch(key);
                    }
                    results.clear();
                    break;
                case 4:
                    System.out.println("Exiting Pokemon Menu. Bye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
