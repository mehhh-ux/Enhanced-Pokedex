package Main;

import Item.ItemController;
import Item.ItemView;
import Pokemon.PokemonController;
import Pokemon.PokemonView;
import Move.MoveController;
import Move.MoveView;
import Trainer.TrainerController;
import Trainer.TrainerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame {
    private JButton btnPokemonMenu, btnMoveMenu, btnItemMenu, btnTrainerMenu, btnExit;
    private PokemonController pController;
    private MoveController mController;
    private ItemController iController;
    private TrainerController tController;
    private FileIO fileIO;

    /**
     * Constructs the MainGUI with the provided controllers.
     *
     * @param pController the PokemonController instance
     * @param mController the MoveController instance
     * @param iController the ItemController instance
     * @param tController the TrainerController instance
     */
    public MainGUI(PokemonController pController, MoveController mController, ItemController iController, TrainerController tController) {
        this.pController = pController;
        this.mController = mController;
        this.iController = iController;
        this.tController = tController;
        this.fileIO = new FileIO(pController, mController, iController, tController);
        fileIO.loadSaveFile(this);
        fileIO.loadTrainers(this);
        iniGUI();
    }

    /**
     * Initializes the GUI components, layout, and button actions for the main menu.
     * Sets up window closing behavior to optionally save before exiting.
     */
    private void iniGUI() {
        setTitle("Enhanced Pokedex - Main Menu");
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setLayout(new GridLayout(4, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int confirm = JOptionPane.showConfirmDialog(MainGUI.this,
                        "Do you want to exit and save your data?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        fileIO.saveToFile();
                        fileIO.saveTrainers();
                        System.exit(0);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(MainGUI.this,
                                "Failed to save data: " + exception.getMessage(),
                                "Save Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        });

        btnPokemonMenu = new JButton("Pokemon Menu");
        btnMoveMenu = new JButton("Move Menu");
        btnItemMenu = new JButton("Item Menu");
        btnTrainerMenu = new JButton("Trainer Menu");

        btnLoadFile = new JButton("Load Save File");
        btnExit = new JButton("Exit");

        panel.add(btnPokemonMenu);
        panel.add(btnMoveMenu);
        panel.add(btnItemMenu);
        panel.add(btnTrainerMenu);
        panel.add(btnLoadFile);
        panel.add(btnExit);

        add(panel);
        setVisible(true);

        btnPokemonMenu.addActionListener(e -> {
            new PokemonView(pController, mController, iController, tController);
            this.dispose();
        });

        btnMoveMenu.addActionListener(e -> {
            new MoveView(pController, mController, iController, tController);
            this.dispose();
        });

        btnItemMenu.addActionListener(e -> {
            new ItemView(pController, mController, iController, tController);
            this.dispose();
        });

        btnTrainerMenu.addActionListener(e -> {
            new TrainerView(pController, mController, iController, tController);
            this.dispose();
        });

        btnLoadFile.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(null, "Input load filename:");
            if (input == null || input.trim().isEmpty()) {
                return;
            }

            filename = input.trim();
            if (!filename.endsWith(".txt")) {
                filename += ".txt";
            }

            File file = new File(filename);

            if (file.exists()) {
                try {
                    fileIO.loadSaveFile(this, filename);
                    fileIO.saveTrainers()
                    JOptionPane.showMessageDialog(null, "File loaded Successfully.");
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error loading file: " + exception.getMessage());
                }
            } else {
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        JOptionPane.showMessageDialog(null, "File not found. New file created: " + filename);
                    } else {
                        JOptionPane.showMessageDialog(null, "File could not be created.");
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error creating file: " + exception.getMessage());
                }
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }
}
