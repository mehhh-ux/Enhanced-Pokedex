package Item;

import Pokemon.Pokemon;
import Pokemon.PokemonController;
import Pokemon.Stats;
import java.util.ArrayList;

/**
 * Handles the logic for applying item effects to Pokémon.
 * This includes leveling up, evolving, stat enhancements (EV), and held item assignment.
 */
public class ItemEffectHandler {
    /** Controller used for retrieving Pokémon data, especially for evolutions. */
    PokemonController pokemonController;

    public ItemEffectHandler(PokemonController pokemonController) {
        this.pokemonController = pokemonController;
    }

    /**
     * Applies the effect of an item to a given Pokémon.
     * Handles leveling up, evolution by level or item, and EV/stat boosts.
     *
     * @param pokemon the Pokémon the item will be used on
     * @param item    the item to apply
     */
    public void useItem(Pokemon pokemon, Item item) {
        String effect = item.getEffect().toLowerCase();

        if (effect.contains("level")) {
            int oldLevel = pokemon.getBaseLvl();
            Stats oldStats = pokemon.getBaseStats().clone(); // ensure clone is implemented

            pokemon.setBaseLvl(oldLevel + 1);
            pokemon.getBaseStats().increaseByPercentage(0.10);

            if (pokemon.getEvolvesTo() != -1 && pokemon.getBaseLvl() >= pokemon.getEvolutionLvl()) {
                Pokemon evolvedForm = pokemonController.getPokemonByNumber(pokemon.getEvolvesTo());
                if (evolvedForm != null) {
                    System.out.println(pokemon.getName() + " evolved into " + evolvedForm.getName() + "!");

                    // Evolve: update name, dex number, and new evolveTo if applicable
                    pokemon.setName(evolvedForm.getName());
                    pokemon.setPokedexNum(evolvedForm.getPokedexNum());
                    pokemon.setEvolveTo(evolvedForm.getEvolvesTo());
                    pokemon.setEvolutionLvl(evolvedForm.getEvolutionLvl());
                    // base stats remain the same — no reset
                }
            }

            System.out.println(pokemon.getName() + " leveled up! " + oldLevel + " -> " + pokemon.getBaseLvl());
            System.out.println("Old Stats: " + oldStats);
            System.out.println("New Stats: " + pokemon.getBaseStats());
        } else if (effect.contains("evolves")) {
            applyEvolution(pokemon, item.getName());
        } else if (effect.contains("ev")) {
            applyStatBoost(pokemon, effect);
        } else {
            System.out.println("Item effect not recognized: " + item.getEffect());
        }
    }

    /**
     * Applies a stat (EV) boost to a Pokémon based on the effect string.
     *
     * @param pokemon the Pokémon whose stats will be boosted
     * @param effect  the effect string describing the boost (e.g., "+10 hp")
     */
    private static void applyStatBoost(Pokemon pokemon, String effect) {
        Stats stats = pokemon.getBaseStats();

        int value = parseValue(effect);

        if (effect.contains("hp")) {
            stats.setHp(stats.getHp() + value);
        } else if (effect.contains("attack")) {
            stats.setAtk(stats.getAtk() + value);
        } else if (effect.contains("defense")) {
            stats.setDef(stats.getDef() + value);
        } else if (effect.contains("speed")) {
            stats.setSpd(stats.getSpd() + value);
        } else {
            System.out.println("Unrecognized stat effect: " + effect);
        }
        System.out.println("Stat changed: " + pokemon.getName() + " now has stats: " + stats);
    }

    /**
     * Parses an integer value from the effect string (e.g., "+10").
     *
     * @param effect the effect string containing the value
     * @return the integer value parsed; returns 0 if parsing fails
     */
    private static int parseValue(String effect) {
        // Example: "+10", "+1" in the effect string
        String[] words = effect.split(" ");
        for (String word : words) {
            if (word.startsWith("+")) {
                try {
                    return Integer.parseInt(word.replace("+", ""));
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * Evolves the given Pokémon using the specified item.
     * This sets the evolved form's name, types, evolution target, evolution level, and updates stats.
     *
     * @param pokemon   the Pokémon to evolve
     * @param itemName  the name of the item triggering evolution
     */
    private void applyEvolution(Pokemon pokemon, String itemName) {
        ArrayList<Pokemon> allPokemon = pokemonController.getAllPokemon();
        Pokemon evolvedForm = null;

        System.out.println("Trying to evolve: " + pokemon.getName() + " -> evolveTo ID: " + pokemon.getEvolvesTo());
        System.out.println("allPokemon is " + (allPokemon == null ? "null" : "size: " + allPokemon.size()));

        // Get the evolved form using evolveTo ID
        for (Pokemon p : allPokemon) {
            System.out.println("Checking: " + p.getName() + " with ID: " + p.getPokedexNum());
            if (pokemon.getEvolvesTo() == p.getPokedexNum()) {
                evolvedForm = p;
                break;
            }
        }

        if (evolvedForm == null) {
            System.out.println(pokemon.getName() + " cannot evolve.");
            return;
        }

        String oldName = pokemon.getName();
        Stats oldStats = pokemon.getBaseStats().clone();

        // Set evolved properties
        pokemon.setName(evolvedForm.getName());
        pokemon.setType1(evolvedForm.getType1());
        pokemon.setType2(evolvedForm.getType2());
        pokemon.setEvolveTo(evolvedForm.getEvolvesTo());
        pokemon.setEvolutionLvl(evolvedForm.getEvolutionLvl());

        Stats evolvedStats = evolvedForm.getBaseStats();
        Stats currentStats = pokemon.getBaseStats();

        currentStats.setHp(Math.max(currentStats.getHp(), evolvedStats.getHp()));
        currentStats.setAtk(Math.max(currentStats.getAtk(), evolvedStats.getAtk()));
        currentStats.setDef(Math.max(currentStats.getDef(), evolvedStats.getDef()));
        currentStats.setSpd(Math.max(currentStats.getSpd(), evolvedStats.getSpd()));

        System.out.println(oldName + " evolved into " + pokemon.getName() + " using " + itemName + "!");
        System.out.println("Old Stats: " + oldStats);
        System.out.println("New Stats: " + currentStats);
    }

    /**
     * Checks if the Pokémon is eligible to evolve with a given item.
     * Currently assumes any item works as long as the Pokémon has a valid evolution target.
     *
     * @param pokemon   the Pokémon to check
     * @param itemName  the item to be used for evolution
     * @return true if the Pokémon can evolve using the item; false otherwise
     */
    private static boolean canEvolveWith(Pokemon pokemon, String itemName) {
        // Add custom logic to check item compatibility (Fire Stone only works on certain Pokémon, etc.)
        // For now, assume all items work on all Pokémon that can evolve
        return pokemon.getEvolvesTo() != -1;
    }

    /**
     * Assigns the given item as a held item to the specified Pokémon.
     *
     * @param pokemon the Pokémon to receive the held item
     * @param item    the item to be held
     */
    public static void giveHeldItem(Pokemon pokemon, Item item) {
        pokemon.setHeldItem(item);
        System.out.println(pokemon.getName() + " is now holding " + item.getName());
    }
}