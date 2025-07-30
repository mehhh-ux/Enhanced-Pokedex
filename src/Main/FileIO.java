package Main;

import Pokemon.PokemonController;
import Move.MoveController;
import Item.ItemController;

import javax.swing.*;

public class FileIO {
    private PokemonController pController;
    private MoveController mController;
    private ItemController iController;

    public FileIO(PokemonController pController, MoveController mController, ItemController iController) {
        this.pController = pController;
        this.mController = mController;
        this.iController = iController;
    }

    /**
     * Loads saved data from the specified text file and initializes game state.
     * Delegates the parsing of each section (ITEM, MOVE, POKEMON, TRAINER)
     * to their respective controllers.
     * @param parent   the parent frame for dialog display
     * @param filename the name of the save file to load
     */
    public void loadSaveFile(JFrame parent, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

        try {
            iController.loadFromFile(sFileName);
            mController.loadFromFile(sFileName);
            pController.loadFromFile(sFileName);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(parent,
                    "Failed to load save file.\n" + exception.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves the current game state to the specified file by delegating to the
     * respective controller's {@code saveToFile} method.
     * @param filename the name of the file to save the game state to
     */
    public void saveToFile(String filename) {
        try {
            iController.saveToFile(filename);
            mController.saveToFile(filename);
            pController.saveToFile(filename);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Failed to save file.\n" + exception.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
