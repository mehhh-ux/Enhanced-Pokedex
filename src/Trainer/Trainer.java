package Trainer;

import Item.Item;
import Pokemon.Pokemon;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a Pokemon Trainer with personal information, Pokemon lineup and storage,
 * owned items, and financial details.
 * Each Trainer has a unique ID, personal details (name, sex, hometown, description, and birthdate),
 * as well as a list of Pokemon (lineup and storage) and a collection of items.
 */
public class Trainer {
    private int trainerId;
    private String name, sex, hometown, description;
    private LocalDate birthDate;
    private double money;
    private ArrayList<Pokemon> lineup, storage;
    private ArrayList<Item> items;

    /**
     * Constructs a new Trainer with the specified personal details.
     * Initializes the trainer with 1,000,000 in money, and empty Pokemon lineup, storage, and items list
     * @param trainerId    the unique ID of the trainer
     * @param name         the name of the trainer
     * @param sex          the sex of the trainer
     * @param hometown     the hometown of the trainer
     * @param description  a short description or background of the trainer
     * @param birthDate    the trainer's birth date
     */
    public Trainer(int trainerId, String name, String sex, String hometown, String description, LocalDate birthDate){
        this.trainerId = trainerId;
        this.name = name;
        this.sex = sex;
        this.hometown = hometown;
        this.description = description;
        this.birthDate = birthDate;
        this.money = 1000000.00;
        this.lineup = new ArrayList<>();
        this.storage = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    /**
     * Returns the unique trainer ID.
     *
     * @return the trainer ID
     */
    public int getTrainerId(){
        return trainerId;
    }

    /**
     * Returns the name of the trainer.
     *
     * @return the trainer's name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the sex of the trainer.
     *
     * @return the trainer's sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * Returns the hometown of the trainer.
     *
     * @return the trainer's hometown
     */
    public String getHometown(){
        return hometown;
    }

    /**
     * Returns the description or background of the trainer.
     *
     * @return the trainer's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the current amount of money the trainer has.
     *
     * @return the trainer's money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Returns the birth date of the trainer.
     *
     * @return the trainer's birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Returns a combined list of all Pokemon the trainer owns,
     * including both active lineup and storage.
     *
     * @return a list containing all Pokemon
     */
    public ArrayList<Pokemon> getAllPokemon() {
        ArrayList<Pokemon> allPokemon = new ArrayList<>();
        allPokemon.addAll(lineup);
        allPokemon.addAll(storage);
        return allPokemon;
    }

    /**
     * Returns the trainer's active Pokemon lineup.
     *
     * @return a list of Pokemon currently in the trainer's lineup
     */
    public ArrayList<Pokemon> getLineup() {
        return lineup;
    }

    /**
     * Returns the trainer's stored Pokemon not in the active lineup.
     *
     * @return a list of stored Pokemon
     */
    public ArrayList<Pokemon> getStorage() {
        return storage;
    }

    /**
     * Returns the list of items owned by the trainer.
     *
     * @return the trainer's items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Updates the amount of money the trainer currently has.
     *
     * @param money the new amount of money
     */
    public void setMoney(double money){
        this.money = money;
    }
}