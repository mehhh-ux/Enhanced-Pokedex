/**
 * PokemonController.java is responsible for receiving and giving Pokemon data.
 * Responsible for saving the list of all moves in the Pokedex and adding
 new moves into the list.
 * Able to give a list of moves based on an operation or a method.
 */
package Pokemon;

import Move.Move;
import Move.MoveController;
import Item.Item;
import Item.ItemController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * PokemonController.java is responsible for managing Pokémon-related operations,
 * including adding, retrieving, searching, loading from file, saving to file, and
 * applying items to Pokémon. It maintains a master list of Pokémon in the system.
 */

public class PokemonController {
    /**
     * A list of pokemons.
     * The ArrayList pokemons is used to keep track of all pokemons in the Pokedex.
     * The ArrayList results is used to hold a list of moves after using an operation
     * (for example the search function).
     */
    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private ArrayList<Pokemon> results = new ArrayList<>();
    private MoveController mController;
    private ItemController iController;

    /**
     * Constructs a PokemonController with the given MoveController and ItemController.
     *
     * @param mController the controller managing move data
     * @param iController the controller managing item data
     */
    public PokemonController(MoveController mController, ItemController iController) {
        this.mController = mController;
        this.iController = iController;
    }

    /**
     * Adds a pokemon into the pokemons ArrayList.
     *
     * @param p is the pokemon to be saved.
     * @return whether adding of pokemon is successful or not
     */
    public boolean addPokemon(Pokemon p) {
        return pokemons.add(p);
    }

    /**
     * Getter
     *
     * @return the list of pokemons in the Pokedex.
     */
    public ArrayList<Pokemon> getAllPokemon() {
        return pokemons;
    }

    /**
     * Search for a pokemon or a list of pokemons based off from a key.
     *
     * @param key is the input of the user that will be used to search if
     *            a pokemon has an attribute that matches said key.
     * @return the resulting list of pokemons.
     */
    public ArrayList<Pokemon> searchPokemon(String key) {
        key = key.toLowerCase();
        for (Pokemon p : pokemons) {
            if (String.valueOf(p.getPokedexNum()).contains(key) ||
                    p.getName().toLowerCase().contains(key) ||
                    p.getType1().toLowerCase().contains(key) ||
                    (p.getType2() != null && p.getType2().toLowerCase().contains(key))) {
                results.add(p);
            }
        }
        return results;
    }

    /**
     * Checks if a Pokémon number already exists.
     *
     * @param pokedexNum the Pokedex number to check
     * @return true if duplicate
     */
    public boolean pokemonNumIsDup(int pokedexNum) {
        for (Pokemon p : pokemons) {
            if (p.getPokedexNum() == pokedexNum) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a Pokémon name already exists.
     *
     * @param name the name to check
     * @return true if duplicate
     */
    public boolean pokemonNameIsDup(String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a Pokémon by name.
     *
     * @param name the name of the Pokémon
     * @return the Pokémon object, or null if not found
     */
    public Pokemon getPokemonByName(String name) {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }

        return null;
    }

    /**
     * Loads Pokémon data from a file using a buffered reader.
     *
     * @param reader the reader used to read the file
     * @throws Exception if file format is invalid or an I/O error occurs
     */
    public void loadFromFile(BufferedReader reader) throws Exception {
        pokemons.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean inPokemonSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("POKEMON:")) {
                    inPokemonSection = true;
                    continue;
                }

                if (!inPokemonSection) {
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length < 14) {
                    throw new IllegalArgumentException("Invalid pokemon data format: " + line);
                }

                int pokedexNum = Integer.parseInt(parts[0]);
                String name = parts[1].trim();
                String type1 = parts[2].trim();
                String type2 = parts[3].trim();
                if (type2.equalsIgnoreCase("null") || type2.isEmpty()) {
                    type2 = null;
                }

                int baseLvl = Integer.parseInt(parts[4]);
                int evolvesFrom = Integer.parseInt(parts[5]);
                int evolveTo = Integer.parseInt(parts[6]);
                int evolutionLvl = Integer.parseInt(parts[7]);

                int hp = Integer.parseInt(parts[8]);
                int atk = Integer.parseInt(parts[9]);
                int def = Integer.parseInt(parts[10]);
                int spd = Integer.parseInt(parts[11]);

                Stats s = new Stats(hp, atk, def, spd);

                Pokemon p = new Pokemon(pokedexNum, name, type1, type2, baseLvl, evolvesFrom,
                        evolveTo, evolutionLvl, s);

                if (!parts[12].trim().isEmpty()) {
                    Item heldItem = iController.getItemByName(parts[12].trim());
                    if (heldItem != null) {
                        p.setHeldItem(heldItem);
                    }
                }

                if (!parts[13].trim().isEmpty()) {
                    String[] moveNames = parts[13].split("\\|");
                    for (String moveName : moveNames) {
                        Move m = mController.getMoveByName(moveName.trim());
                        if (m != null) {
                            p.getMoveSet().add(m);
                        }
                    }
                }

                pokemons.add(p);
            }
        }
    }

    /**
     * Saves Pokémon data to a file.
     *
     * @param filename the output file path
     * @throws Exception if writing fails
     */
    public void saveToFile(String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("POKEMON:\n");
            for (Pokemon p : pokemons) {
                StringBuilder sb = new StringBuilder();

                sb.append(p.getPokedexNum()).append(",");
                sb.append(p.getName()).append(",");
                sb.append(p.getType1()).append(",");
                sb.append(p.getType2() != null ? p.getType2() : "").append(",");
                sb.append(p.getBaseLvl()).append(",");
                sb.append(p.getEvolvesFrom()).append(",");
                sb.append(p.getEvolvesTo()).append(",");
                sb.append(p.getEvolutionLvl()).append(",");

                Stats s = p.getBaseStats();
                sb.append(s.getHp()).append(",");
                sb.append(s.getAtk()).append(",");
                sb.append(s.getDef()).append(",");
                sb.append(s.getSpd()).append(",");

                Item held = p.getHeldItem();
                sb.append(held != null ? held.getName() : "").append(",");

                ArrayList<Move> moveSet = p.getMoveSet();
                if (moveSet != null && !moveSet.isEmpty()) {
                    for (int i = 0; i < moveSet.size(); i++) {
                        sb.append(moveSet.get(i).getName());
                        if (i != moveSet.size() - 1) {
                            sb.append("|");
                        }
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}