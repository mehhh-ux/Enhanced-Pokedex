package Trainer;

import Item.Item;
import Item.ItemController;
import Move.Move;
import Move.MoveController;
import Pokemon.Pokemon;
import Pokemon.PokemonController;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The TrainerController class manages a collection of Trainer objects and their related operations.
 * This includes adding, searching, and retrieving trainers, managing Pokemon lineups and storage,
 * buying and selling items, teaching and removing moves, and saving/loading trainer data to/from a file.
 * This class interacts with the PokemonController}, MoveController, and ItemController
 * to manage dependencies between trainers and other game components.
 */
public class TrainerController {
    private ArrayList<Trainer> trainers = new ArrayList<>();
    private ArrayList<Trainer> results = new ArrayList<>();
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;

    /**
     * Constructs a TrainerController with the specified controllers for Pokemon, moves, and items.
     *
     * @param pokemonController controller to manage Pokemon data
     * @param moveController    controller to manage Move data
     * @param itemController    controller to manage Item data
     */
    public TrainerController(PokemonController pokemonController, MoveController moveController, ItemController itemController) {
        this.pokemonController = pokemonController;
        this.moveController = moveController;
        this.itemController = itemController;
    }

    /**
     * Checks if a trainer ID already exists in the system.
     *
     * @param trainerId the trainer ID to check
     * @return true if the ID exists, false otherwise
     */
    public boolean trainerIdIsDup(int trainerId) {
        for (Trainer t : trainers) {
            if (t.getTrainerId() == trainerId) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a trainer name already exists in the system (case-insensitive).
     *
     * @param name the trainer name to check
     * @return true if the name exists, false otherwise
     */
    public boolean trainerNameIsDup(String name) {
        for (Trainer t : trainers) {
            if (t.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a new trainer to the system.
     *
     * @param t the trainer to add
     * @return true if added successfully
     */
    public boolean addTrainer(Trainer t) {
        return trainers.add(t);
    }

    /**
     * Returns a list of all trainers in the system.
     *
     * @return list of all trainers
     */
    public ArrayList<Trainer> getAllTrainer(){
        return trainers;
    }

    /**
     * Retrieves a trainer by their unique ID.
     *
     * @param trainerId the trainer's ID
     * @return the matching trainer, or null if not found
     */
    public Trainer getTrainerById(int trainerId) {
        for (Trainer t : trainers) {
            if (t.getTrainerId() == trainerId) {
                return t;
            }
        }

        return null;
    }

    /**
     * Searches for trainers that match the given keyword in any field.
     *
     * @param key the search keyword
     * @return a list of matching trainers
     */
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

    /**
     * Allows a trainer to buy a specified quantity of an item if constraints are met.
     *
     * @param t        the trainer buying the item
     * @param i        the item to be bought
     * @param price    price per item
     * @param quantity quantity to buy
     * @return true if the purchase was successful
     */
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

    /**
     * Allows a trainer to sell a specified quantity of an item.
     *
     * @param t        the trainer selling the item
     * @param i        the item to be sold
     * @param quantity quantity to sell
     * @return true if the sale was successful
     */
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

    /**
     * Adds a Pokemon to the trainer's lineup (max 6).
     *
     * @param t the trainer
     * @param p the Pokémon to add
     * @return true if added, false if lineup is full
     */
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

    /**
     * Adds a Pokemon to the trainer's storage (max 30).
     *
     * @param t the trainer
     * @param p the Pokémon to add
     * @return true if added, false if storage is full
     */
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

    /**
     * Switches a Pokemon between lineup and storage.
     *
     * @param t        the trainer
     * @param pLineup  Pokémon to move to storage
     * @param pStorage Pokémon to move to lineup
     * @return true if switch was successful
     */
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

    /**
     * Releases a Pokemon from the trainer's storage.
     *
     * @param t the trainer
     * @param p the Pokémon to release
     * @return true if release was successful
     */
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

    /**
     * Releases a Pokemon from the trainer's lineup.
     *
     * @param t the trainer
     * @param p the Pokémon to release
     * @return true if release was successful
     */
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

    /**
     * Retrieves a Pokemon from the trainer's storage by name.
     *
     * @param t the trainer
     * @param p the Pokémon to search for
     * @return the matching Pokémon or null
     */
    public Pokemon pokemonFromStorage(Trainer t, Pokemon p){
        for (Pokemon poke: t.getStorage()){
            if (poke.getName().equalsIgnoreCase(p.getName())){
                return poke;
            }
        }
        return null;
    }

    /**
     * Retrieves a Pokemon from the trainer's lineup by name.
     *
     * @param t the trainer
     * @param p the Pokémon to search for
     * @return the matching Pokémon or null
     */
    public Pokemon pokemonFromLineup(Trainer t, Pokemon p){
        for (Pokemon poke: t.getLineup()){
            if (poke.getName().equalsIgnoreCase(p.getName())){
                return poke;
            }
        }
        return null;
    }

    /**
     * Teaches a move to a Pokemon if it's valid and the Pokémon has fewer than 4 moves.
     *
     * @param t the trainer
     * @param p the Pokémon to teach the move to
     * @param m the move to teach
     * @return true if successful
     */
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

    /**
     * Removes a move from a Pokemon, if not a required move (like HM).
     *
     * @param t the trainer
     * @param p the Pokémon to remove the move from
     * @param m the move to remove
     * @return true if successful
     */
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


    /**
     * Loads trainer data from a file and populates the internal trainer list.
     * Also populates lineup, storage, and items using respective controllers.
     *
     * @param filename the file to load from
     * @throws Exception if file loading fails
     */
    public void loadFromFile(String filename) throws Exception {
        trainers.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length < 7) {
                    continue;
                }

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String sex = parts[2].trim();
                String hometown = parts[3].trim();
                String desc = parts[4].trim();
                LocalDate birth = LocalDate.parse(parts[5].trim());
                double money = Double.parseDouble(parts[6].trim());

                Trainer t = new Trainer(id, name, sex, hometown, desc, birth);
                t.setMoney(money);

                String lineupLine = reader.readLine().substring(8);
                for (String pName : lineupLine.split(",")) {
                    if (!pName.isEmpty()) {
                        t.getLineup().add(pokemonController.getPokemonByName(pName));
                    }
                }

                String storageLine = reader.readLine().substring(9);
                for (String pName : storageLine.split(",")) {
                    if (!pName.isEmpty()) {
                        t.getStorage().add(pokemonController.getPokemonByName(pName));
                    }
                }

                String itemLine = reader.readLine().substring(7);
                for (String iName : itemLine.split(",")) {
                    if (!iName.isEmpty()) {
                        t.getItems().add(itemController.getItemByName(iName));
                    }
                }

                trainers.add(t);
                reader.readLine();
            }
        }
    }

    /**
     * Saves current trainer data to a specified file, including Pokémon and items.
     *
     * @param filename the file to write to
     * @throws Exception if file writing fails
     */
    public void saveToFile(String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Trainer t : trainers) {
                writer.write(t.getTrainerId() + "|" +
                        t.getName() + "|" +
                        t.getSex() + "|" +
                        t.getHometown() + "|" +
                        t.getDescription() + "|" +
                        t.getBirthDate().toString() + "|" +
                        t.getMoney());
                writer.newLine();

                writer.write("Lineup: ");
                for (int i = 0; i < t.getLineup().size(); i++) {
                    writer.write(t.getLineup().get(i).getName());
                    if (i < t.getLineup().size() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();

                writer.write("Storage: ");
                for (int i = 0; i < t.getStorage().size(); i++) {
                    writer.write(t.getStorage().get(i).getName());
                    if (i < t.getStorage().size() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();

                writer.write("Items: ");
                for (int i = 0; i < t.getItems().size(); i++) {
                    writer.write(t.getItems().get(i).getName());
                    if (i < t.getItems().size() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
                writer.newLine();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("Failed to save trainers to file.");
        }
    }
}