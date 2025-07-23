/**
 * PokemonController.java is responsible for receiving and giving Pokemon data.
 * Responsible for saving the list of all moves in the Pokedex and adding
   new moves into the list.
 * Able to give a list of moves based on an operation or a method.
 */
package Pokemon;

import java.util.ArrayList;

public class PokemonController {
    /**
     * A list of pokemons.
     * The ArrayList pokemons is used to keep track of all pokemons in the Pokedex.
     * The ArrayList results is used to hold a list of moves after using an operation
       (for example the search function).
     */
    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private ArrayList<Pokemon> results = new ArrayList<>();

    /**
     * Adds a pokemon into the pokemons ArrayList.
     * @param p is the pokemon to be saved.
     * @return whether adding of pokemon is successful or not
     */
    public boolean addPokemon(Pokemon p) {
        return pokemons.add(p);
    }

    /**
     * Getter
     * @return the list of pokemons in the Pokedex.
     */
    public ArrayList<Pokemon> getAllPokemon() {
        return pokemons;
    }

    /**
     * Search for a pokemon or a list of pokemons based off from a key.
     * @param key is the input of the user that will be used to search if
       a pokemon has an attribute that matches said key.
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
     * pokemonNumIsDup() checks through the ArrayList pokemons if there are any
       pokemon objects with the same pokedexNum.
     * @param pokedexNum is the number to check
       (this comes from the user back in PokemonMenu.java).
     * @return a boolean value.
     */
    public boolean pokemonNumIsDup(int pokedexNum){
        for (Pokemon p: pokemons){
            if (p.getPokedexNum() == pokedexNum){
                return true;
            }
        }
        return false;
    }

    /**
     * pokemonNameIsDup() checks through the ArrayList pokemons if there are any
       pokemon objects with the same pokedexName.
     * @param pokedexName is the name (String) to check
       (this comes from the user back in PokemonMenu.java).
     * @return a boolean value.
     */
    public boolean pokemonNameIsDup(String pokedexName){
        for (Pokemon p: pokemons){
            if (p.getName().equalsIgnoreCase(pokedexName)){
                return true;
            }
        }
        return false;
    }
}