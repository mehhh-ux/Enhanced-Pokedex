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
}