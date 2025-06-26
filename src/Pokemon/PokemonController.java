package Pokemon;

import java.util.ArrayList;

public class PokemonController {
    private ArrayList<PokemonModel> pokemons = new ArrayList<>();
    private ArrayList<PokemonModel> results = new ArrayList<>();

    public void addPokemon(PokemonModel p) {
        pokemons.add(p);
    }

    public ArrayList<PokemonModel> getAllPokemon() {
        return pokemons;
    }

    public ArrayList<PokemonModel> searchPokemon(String key) {
        key = key.toLowerCase();
        for (PokemonModel p : pokemons) {
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
        for (PokemonModel p: pokemons){
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
        for (PokemonModel p: pokemons){
            if (p.getName().equalsIgnoreCase(pokedexName)){
                return true;
            }
        }
        return false;
    }
}