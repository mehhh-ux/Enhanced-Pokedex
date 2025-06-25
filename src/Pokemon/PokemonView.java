package Pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class PokemonView {
    private Scanner scanner = new Scanner(System.in);
    private PokemonModel.Stats stats;
    private String name, type1, type2;
    private int pokedexNum, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, hp, atk, def, spd;

    public PokemonModel promptPokemonData() {
        System.out.print("Pokedex Number: ");
        pokedexNum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Type 1: ");
        type1 = scanner.nextLine();
        System.out.print("Type 2 (Press enter to skip): ");
        type2 = scanner.nextLine();
        type2 = type2.isEmpty() ? null : type2;
        System.out.print("Base Level: ");
        baseLvl = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Evolves From (Enter 0 if none): ");
        evolvesFrom = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Evolves To (Enter 0 if none): ");
        evolvesTo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Evolution Level: ");
        evolutionLvl = scanner.nextInt();
        System.out.print("Base HP: ");
        hp = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base ATK: ");
        atk = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base DEF: ");
        def = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base SPD: ");
        spd = scanner.nextInt();
        scanner.nextLine();

        stats = new PokemonModel.Stats(hp, atk, def, spd);

        return (type2 == null)
                ? new PokemonModel(pokedexNum, name, type1, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats)
                : new PokemonModel(pokedexNum, name, type1, type2, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats);
    }

    public void printAllPokemons(ArrayList<PokemonModel> pokemons) {
        if (pokemons.isEmpty()) {
            System.out.println("No pokemons in the Pokedex.");
            return;
        }

        pokemons.getFirst().printHeader();
        for (PokemonModel p : pokemons) {
            p.displayPokemonAttributes();
        }
    }

    public String promptSearchKey() {
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }
}