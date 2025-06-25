package Menu;

import Pokemon.PokemonController;
import Pokemon.PokemonModel;
import Pokemon.PokemonView;

import java.util.ArrayList;
import java.util.Scanner;

public class PokemonMenu {
    private PokemonController controller = new PokemonController();
    private PokemonView view = new PokemonView();
    private ArrayList<PokemonModel> results = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private int option;
    private String key;

    public void run(){
        System.out.println("--- Welcome to the Pokémon Menu ---");
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Pokémon");
            System.out.println("2. Show All Pokémon");
            System.out.println("3. Search Pokémon");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    controller.addPokemon(view.promptPokemonData());
                    break;
                case 2:
                    view.printAllPokemons(controller.getAllPokemon());
                    break;
                case 3:
                    key = view.promptSearchKey();
                    results = controller.searchPokemon(key);
                    view.printAllPokemons(results);
                    break;
                case 4:
                    System.out.println("Exiting Pokemon Menu. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
