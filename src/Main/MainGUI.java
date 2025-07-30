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

/**
 * MainGUI.java is the main menu GUI for the Enhanced Pokedex application.
 * This class initializes and displays the main window with menu buttons
 * to navigate between the different features: Pokémon, Moves, Items, Trainers,
 * file loading, and exiting the application.
 * It integrates the core controllers: PokemonController, MoveController,
 * ItemController, and TrainerController, and connects them to the appropriate views.
 * It also handles loading and saving via FileIO.
 */
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
        fileIO.loadItems(this);
        fileIO.loadMoves(this);
        fileIO.loadPokemons(this);
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
                        fileIO.saveAll();
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

        btnExit = new JButton("Exit");

        panel.add(btnPokemonMenu);
        panel.add(btnMoveMenu);
        panel.add(btnItemMenu);
        panel.add(btnTrainerMenu);
        panel.add(btnExit);

        add(panel, BorderLayout.CENTER);
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

        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }
}
