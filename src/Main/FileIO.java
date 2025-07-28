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

    public void loadSaveFile(JFrame parent) {
        String sFileName = "data.txt";

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

    public void saveToFile() {
        String filename = "data.txt";

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
