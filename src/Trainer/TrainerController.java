package Trainer;

import Item.Item;
import Move.Move;
import Pokemon.Pokemon;

import java.util.ArrayList;

public class TrainerController {
    private ArrayList<Trainer> trainers;
    private ArrayList<Trainer> results;

    public boolean addTrainer(Trainer t) {
        return trainers.add(t);
    }

    public ArrayList<Trainer> getAllTrainer(){
        return trainers;
    }

    public ArrayList<Trainer> searchTrainer(String key){
        key = key.toLowerCase();
        for (Trainer t : trainers) {
            if (String.valueOf(t.getTrainerId()).contains(key) ||
                    t.getName().toLowerCase().contains(key) ||
                    t.getSex().toLowerCase().contains(key) ||
                    t.getHometown().toLowerCase().contains(key) ||
                    t.getDescription().toLowerCase().contains(key) ||
                    String.valueOf(t.getMoney()).contains(key) ||
                    t.getBirthDate().toString().contains(key)) {
                results.add(t);
            }
        }
        return results;
    }

    public boolean buyItem(Trainer t, Item i, double price){
        ArrayList<Item> bag = t.getItems();
        int totalItems;
        long uniqueItems;
        boolean alreadyHasUniqueItem;

        totalItems = bag.size();
        uniqueItems = bag.stream().map(item -> item.getName()).distinct().count();
        alreadyHasUniqueItem = bag.stream().anyMatch(existing -> existing.getName().equalsIgnoreCase(i.getName()));

        if (t.getMoney() < price){
            System.out.println("Insufficient money, can't buy '" + i.getName() + "'.");
            return false;
        }
        if (totalItems >= 50){
            System.out.println("Bag is full (50 items). Please discard items before buying new ones.");
            return false;
        }
        if (!alreadyHasUniqueItem && uniqueItems >= 10){
            System.out.println("Limit of 10 unique items reached. Can't add a new unique item.");
            return false;
        }

        t.setMoney(t.getMoney() - price);
        bag.add(i);
        return true;
    }

    public boolean sellItem(Trainer t, Item i){
        for (Item item: t.getItems()){
            if (item.getName().equalsIgnoreCase(i.getName())){
                t.getItems().remove(item);
                t.setMoney(t.getMoney() + i.getSellingPrice());
                return true;
            }
        }
        System.out.println(t.getName() + " currently does not have " + i.getName() + ".");
        return false;
    }

    public boolean addPokemonToLineup(Trainer t, Pokemon p){
        Pokemon uniqueInstance = p.copy();
        if (t.getLineup().size() >= 6){
            System.out.println("Error: " + t.getName() + " already have a maximum of 6 pokemons in their lineup.");
            return false;
        } else {
            t.getLineup().add(uniqueInstance);
            return true;
        }
    }

    public boolean addPokemonToStorage(Trainer t, Pokemon p){
        Pokemon uniqueInstance = p.copy();
        if (t.getStorage().size() >= 30){
            System.out.println("Error: " + t.getName() + " already have a maximum of 30 pokemons in their storage.");
            return false;
        } else {
            t.getStorage().add(uniqueInstance);
            return true;
        }
    }

    public boolean switchPokemonFromStorage(Trainer t, Pokemon pLineup, Pokemon pStorage){
        boolean existsInTheStorage;
        boolean existsInTheLineup;
        Pokemon toRemoveFromLineup = null;
        Pokemon toRemoveFromStorage = null;

        existsInTheStorage = t.getStorage().stream().anyMatch(existing -> existing.getName().equalsIgnoreCase(pStorage.getName()));
        existsInTheLineup = t.getLineup().stream().anyMatch(existing -> existing.getName().equalsIgnoreCase(pLineup.getName()));

        if (!existsInTheStorage){
            System.out.println(pStorage.getName() + " currently isn't in " + t.getName() + "'s storage.");
            return false;
        }
        if (!existsInTheLineup){
            System.out.println(pLineup.getName() + " currently isn't in " + t.getName() + "'s Lineup.");
            return false;
        }
        for (Pokemon p: t.getLineup()){
            if (p.getName().equalsIgnoreCase(pLineup.getName())){
                toRemoveFromLineup = p;
                break;
            }
        }

        for (Pokemon p: t.getStorage()){
            if (p.getName().equalsIgnoreCase(pStorage.getName())){
                toRemoveFromStorage = p;
                break;
            }
        }

        t.getLineup().remove(toRemoveFromLineup);
        t.getStorage().remove(toRemoveFromStorage);

        t.getLineup().add(toRemoveFromStorage);
        t.getStorage().add(toRemoveFromLineup);
        return true;
    }

    public boolean releasePokemonFromStorage(Trainer t, Pokemon p){
        Pokemon toRemoveFromStorage = null;

        if (t.getStorage().isEmpty()){
            System.out.println("Error: There is no pokemon to release from " + t.getName() + "'s storage.");
            return false;
        }
        for (Pokemon pokemon: t.getStorage()){
            if (pokemon.getName().equalsIgnoreCase(p.getName())){
                toRemoveFromStorage = pokemon;
                break;
            }
        }
        t.getStorage().remove(toRemoveFromStorage);
        return true;
    }

    public boolean releasePokemonFromLineup(Trainer t, Pokemon p){
        Pokemon toRemoveFromLineup = null;

        if (t.getLineup().isEmpty()){
            System.out.println("Error: There is no pokemon to release from " + t.getName() + "'s lineup.");
            return false;
        }
        for (Pokemon pokemon: t.getLineup()){
            if (pokemon.getName().equalsIgnoreCase(p.getName())){
                toRemoveFromLineup = pokemon;
                break;
            }
        }
        t.getLineup().remove(toRemoveFromLineup);
        return true;
    }


    public Pokemon pokemonFromStorage(Trainer t, Pokemon p){
        for (Pokemon poke: t.getStorage()){
            if (poke.getName().equalsIgnoreCase(p.getName())){
                return poke;
            }
        }
        return null;
    }

    public Pokemon pokemonFromLineup(Trainer t, Pokemon p){
        for (Pokemon poke: t.getLineup()){
            if (poke.getName().equalsIgnoreCase(p.getName())){
                return poke;
            }
        }
        return null;
    }

    public boolean teachMoves(Trainer t, Pokemon p, Move m){
        Pokemon actual = null;

        actual = pokemonFromLineup(t, p);

        if (actual == null){
            actual = pokemonFromStorage(t, p);
        }

        if (actual == null) {
            System.out.println("Error: " + p.getName() + " is not in " + t.getName() + "'s lineup or storage.");
            return false;
        }

        for (Move move: actual.getMoveSet()){
            if (move.getName().equalsIgnoreCase(m.getName()) && move.getClassification().equalsIgnoreCase(m.getClassification())){
                System.out.println(actual.getName() + " already knows " + m.getName());
                return false;
            }
        }

        if (actual.getMoveSet().size() >= 4){
            System.out.println(p.getName() + " already learned a maximum of 4 moves. Remove a move first.");
            return false;
        }
        if (!(actual.getType1().equalsIgnoreCase(m.getClassification()) || (actual.getType2() != null && actual.getType2().equalsIgnoreCase(m.getClassification())))){
            System.out.println("Error: " + m.getName() + " is incomaptible with " + actual.getName() + ".");
            return false;
        }

        actual.getMoveSet().add(m);
        return true;
    }

    public boolean removeMove(Trainer t, Pokemon p, Move m){
        Pokemon actual = null;
        Move toRemove = null;

        actual = pokemonFromLineup(t, p);

        if (actual == null){
            actual = pokemonFromStorage(t, p);
        }

        if (actual == null) {
            System.out.println("Error: " + p.getName() + " is not in " + t.getName() + "'s lineup or storage.");
            return false;
        }

        for (Move move: actual.getMoveSet()){
            if (move.getName().equalsIgnoreCase(m.getName()) && move.getClassification().equalsIgnoreCase(m.getClassification())){
                toRemove = move;
                break;
            }
        }

        if (toRemove == null){
            System.out.println(p.getName() + " does not know the move " + m.getName() + ".");
            return false;
        }
        if (actual.getMoveSet().isEmpty()){
            System.out.println(p.getName() + " already forgot all of the moves. Add a move first.");
            return false;
        }
        if (toRemove.getClassification().equalsIgnoreCase("HM")){
            System.out.println("Unable to forget " + m.getName() + "since it is of" + m.getClassification() + "classification.");
            return false;
        }

        actual.getMoveSet().remove(toRemove);
        return true;
    }
}