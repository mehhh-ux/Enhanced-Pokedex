package Main;

import Pokemon.PokemonController;
import Move.MoveController;
import Item.ItemController;
import Trainer.TrainerController;

import javax.swing.*;

/**
 * FileIO class handles the loading and saving of game data
 * from and to a text file. It delegates the reading and writing of each
 * component (Items, Moves, Pokémons, and Trainers) to their respective controllers.
 * Used by MainGUI to persist game state between sessions.
 */
public class FileIO {
    private PokemonController pController;
    private MoveController mController;
    private ItemController iController;
    private TrainerController tController;

    public FileIO(PokemonController pController, MoveController mController, ItemController iController, TrainerController tController) {
        this.pController = pController;
        this.mController = mController;
        this.iController = iController;
        this.tController = tController;
    }

    /**
     * Loads saved data from the specified text file and initializes game state.
     * Delegates the parsing of ITEM section
     * to their respective controllers.
     * @param parent the parent frame for dialog display
     */
    public void loadItems(JFrame parent) {
        String sFileName = "items.txt";

        try {
            iController.loadFromFile(sFileName);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to load item file.\n" + exception.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads saved data from the specified text file and initializes game state.
     * Delegates the parsing of Move section
     * to their respective controllers.
     * @param parent the parent frame for dialog display
     */
    public void loadMoves(JFrame parent) {
        String sFileName = "moves.txt";

        try {
            mController.loadFromFile(sFileName);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to load move file.\n" + exception.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads saved data from the specified text file and initializes game state.
     * Delegates the parsing of Pokemon section
     * to their respective controllers.
     * @param parent the parent frame for dialog display
     */
    public void loadPokemons(JFrame parent) {
        String sFileName = "pokemons.txt";

        try {
            pController.loadFromFile(sFileName);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to load pokemon file.\n" + exception.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads saved data from the specified text file and initializes game state.
     * Delegates the parsing of Trainer section
     * to their respective controllers.
     * @param parent the parent frame for dialog display
     */
    public void loadTrainers(JFrame parent) {
        String sFileName = "trainers.txt";

        try {
            tController.loadFromFile(sFileName);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to load trainer file.\n" + exception.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves all items to a predefined text file using the ItemController.
     * If an error occurs during the process, a message dialog is shown to the user.
     */
    public void saveItems() {
        String filename = "items.txt";

        try {
            iController.saveToFile(filename);
            System.out.println("Saving to " + filename);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save pokemons.\n" + exception.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves all moves to a predefined text file using the MoveController.
     * Displays an error dialog if the operation fails.
     */
    public void saveMoves() {
        String filename = "moves.txt";

        try {
            mController.saveToFile(filename);
            System.out.println("Saving to " + filename);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save moves.\n" + exception.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves all Pokémon to a predefined text file using the PokemonController.
     * Shows an error dialog if an exception is thrown during the process.
     */
    public void savePokemons() {
        String filename = "pokemons.txt";

        try {
            pController.saveToFile(filename);
            System.out.println("Saving to " + filename);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save pokemons.\n" + exception.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves all trainers to a predefined text file using the TrainerController.
     * In case of failure, an error message is displayed to the user.
     */
    public  void saveTrainers() {
        String filename = "trainers.txt";

        try {
            tController.saveToFile(filename);
            System.out.println("Saving to " + filename);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save trainers.\n" + exception.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves the current game state by delegating save operations to all
     * respective controllers: items, moves, Pokémon, and trainers.
     * This method ensures all core data components are persisted.
     */
    public void saveAll() {
        saveItems();
        saveMoves();
        savePokemons();
        saveTrainers();
    }
}
