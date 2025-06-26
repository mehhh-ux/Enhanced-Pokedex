package Menu;

import Pokemon.PokemonController;
import Pokemon.PokemonModel;
import Pokemon.PokemonView;

import java.util.ArrayList;
import java.util.Scanner;

public class PokemonMenu {
    private PokemonController controller = new PokemonController();
    private Scanner scanner = new Scanner(System.in);
    private int option, pokedexNum;
    private String key, name;
    private ArrayList<PokemonModel> results = new ArrayList<>();
    boolean running;

    /**
     * The run() method is responsible for displaying the Pokemon Management Menu.
     */
    public void run(){
        running = true;

        while (running) {
            System.out.println("\n=========================================");
            System.out.println("              Pokemon Menu               ");
            System.out.println("=========================================");
            System.out.println("1. Add Pokémon");
            System.out.println("2. Show All Pokémon");
            System.out.println("3. Search Pokémon");
            System.out.println("4. Exit");
            System.out.println("-----------------------------------------");
            System.out.print("Enter your choice: ");

            option = scanner.nextInt();

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
                        }
                        if (controller.addPokemon(view.promptRemainingPokemonData(pokedexNum, name))) {
                            view.successfulPokemonAddMessage(name);
                        }
                    }
                    break;
                case 2:
                    view.printAllPokemons(controller.getAllPokemon());
                    view.pressAnyKeyPrompt();
                    break;
                case 3:
                    key = view.promptSearchKey();
                    results = controller.searchPokemon(key);
                    view.printAllPokemons(results);
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
