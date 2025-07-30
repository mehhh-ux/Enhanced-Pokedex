package Trainer;

import Item.Item;
import Item.ItemController;
import Move.Move;
import Pokemon.Pokemon;
import Pokemon.PokemonController;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrainerController {
    private ArrayList<Trainer> trainers = new ArrayList<>();
    private ArrayList<Trainer> results = new ArrayList<>();
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;

    public TrainerController(PokemonController pokemonController, MoveController moveController, ItemController itemController) {
        this.pokemonController = pokemonController;
        this.moveController = moveController;
        this.itemController = itemController;
    }

    public boolean trainerIdIsDup(int trainerId) {
        for (Trainer t : trainers) {
            if (t.getTrainerId() == trainerId) {
                return true;
            }
        }

        return false;
    }

    public boolean trainerNameIsDup(String name) {
        for (Trainer t : trainers) {
            if (t.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean addTrainer(Trainer t) {
        return trainers.add(t);
    }

    public ArrayList<Trainer> getAllTrainer(){
        return trainers;
    }

    public Trainer getTrainerById(int trainerId) {
        for (Trainer t : trainers) {
            if (t.getTrainerId() == trainerId) {
                return t;
            }
        }

        return null;
    }

    public ArrayList<Trainer> searchTrainer(String key){
        key = key.toLowerCase();
        results.clear();
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

    public boolean buyItem(Trainer t, Item i, double price, int quantity){
        ArrayList<Item> bag = t.getItems();
        int totalItems = 0;
        long uniqueItems = 0, countSameItem = 0;
        boolean alreadyHasUniqueItem = false;
        double totalCost = 0.0;

        totalItems = bag.size();
        uniqueItems = bag.stream().map(item -> item.getName()).distinct().count();
        countSameItem = bag.stream().filter(existing -> existing.getName().equalsIgnoreCase(i.getName())).count();
        alreadyHasUniqueItem = countSameItem > 0;

        totalCost = price * quantity;

        if (t.getMoney() < totalCost) {
            System.out.println("Insufficient money. You need ₱" + totalCost + " to buy " + quantity + " of '" + i.getName() + "'.");
            return false;
        }

        if (totalItems + quantity > 500){
            System.out.println("Bag is full (500 items). Please discard items before buying new ones.");
            return false;
        }
        if (!alreadyHasUniqueItem && uniqueItems >= 10){
            System.out.println("Limit of 10 unique items reached. Can't add a new unique item.");
            return false;
        }

        if (countSameItem + quantity > 50) {
            System.out.println("Cannot have more than 50 copies of '" + i.getName() + "'. Currently have: " + countSameItem);
            return false;
        }

        t.setMoney(t.getMoney() - totalCost);
        for (int j = 0; j < quantity; j++){
            bag.add(i.clone());
        }
        return true;
    }

    public boolean sellItem(Trainer t, Item i, int quantity) {
        ArrayList<Item> bag = t.getItems();
        int removed = 0;
        long ownedCount;
        double totalGain = 0.0;

        ownedCount = bag.stream().filter(item -> item.getName().equalsIgnoreCase(i.getName())).count();

        if (ownedCount < quantity) {
            System.out.println(t.getName() + " does not have " + quantity + " copies of " + i.getName() + ".");
            return false;
        }

        for (int j = 0; j < bag.size() && removed < quantity; j++) {
            Item item = bag.get(j);
            if (item.getName().equalsIgnoreCase(i.getName())) {
                bag.remove(j);
                j--;
                removed++;
            }
        }

        totalGain = i.getSellingPrice() * quantity;
        t.setMoney(t.getMoney() + totalGain);
        return true;
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

    public boolean switchPokemonFromStorge(Trainer t, Pokemon pLineup, Pokemon pStorage){
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
        if ((actual.getType1().equalsIgnoreCase(m.getClassification()) || (actual.getType2() != null && actual.getType2().equalsIgnoreCase(m.getClassification())))){
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