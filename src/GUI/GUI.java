/**
 * The GUI class serves as the main Graphical User Interface (GUI) for the Enhanced Pokedex.
 * ...
 * Uses CardLayout to be able to switch menus without exiting the window or creating a new one.
 * All interactions are handled within this single class.
 */

package GUI;

import Menu.ItemMenu;
import Menu.MoveMenu;
import Menu.PokemonMenu;
import Pokemon.Pokemon;
import Pokemon.PokemonController;
import Pokemon.PokemonView;
import Pokemon.Stats;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.IOException;

public class GUI {
    /**
     * Initializations
     * mainMenuFrame - Main window
     * cardLayout - Layout manager for menu panels.
     * mainPanel - Main container of the submenus.
     * mainMenuPanel - Main menu (visuals)
     * pokemonPanel - Pokemon menu
     * pokemons - List of all pokemons in the pokedex.
     * pController - Data controller for pokemons
     * pView -
     */
    private JFrame mainMenuFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel mainMenuPanel;
    private JPanel pokemonPanel;
    private ArrayList<Pokemon> pokemons;
    private PokemonController pController = new PokemonController();
    private PokemonView pView = new PokemonView();

    /**
     * Constructor
     */
    public GUI() {
        this.pokemons = pController.getAllPokemon();
        iniWindow();
    }

    /**
     * Initializes the main window
     * Makes the window visible in full screen.
     */
    private void iniWindow() {
        mainMenuFrame = new JFrame("Pokedex");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(0, 0);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createMainMenuPanel();
        createPokemonMenuPanel();

        mainPanel.add(mainMenuPanel, "Main Menu");
        mainPanel.add(pokemonPanel, "Pokemon Menu");

        mainMenuFrame.add(mainPanel);

        mainMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainMenuFrame.setVisible(true);
    }

    /**
     * Creates the main menu panel with buttons.
     * Allows navigation to different submenus.
     */
    private void createMainMenuPanel() {
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainMenuPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel title = new JLabel("Welcome to the Pokedex!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        mainMenuPanel.add(title);

        JButton pMenuButton = new JButton("Pokemon Menu");
        JButton mMenuButton = new JButton("Move Menu");
        JButton iMenuButton = new JButton("Item Menu");
        JButton tMenuButton = new JButton("Trainer Menu");
        JButton exitButton = new JButton("Exit");

        pMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "Pokemon Menu"));
        mMenuButton.addActionListener(e -> JOptionPane.showMessageDialog(mainMenuFrame, "asasd"));
        iMenuButton.addActionListener(e -> JOptionPane.showMessageDialog(mainMenuFrame, "asasd"));
        tMenuButton.addActionListener(e -> JOptionPane.showMessageDialog(mainMenuFrame, "asasd"));
        exitButton.addActionListener(e -> System.exit(0));

        mainMenuPanel.add(pMenuButton);
        mainMenuPanel.add(mMenuButton);
        mainMenuPanel.add(iMenuButton);
        mainMenuPanel.add(tMenuButton);
        mainMenuPanel.add(exitButton);
    }

    /**
     * Creates the pokemon menu panel
     * Option 1: Add Pokemon
     * Option 2: Show All Pokemon
     * Option 3: Search Pokemon
     * Option 4: Exit Menu
     */
    private void createPokemonMenuPanel() {
        pokemonPanel = new JPanel(new BorderLayout());

        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        pokemonPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Pokemon");
        JButton displayButton = new JButton("Show All Pokemon");
        JButton searchButton = new JButton("Search Pokemon");
        JButton exitButton = new JButton("Exit");

        displayButton.addActionListener(e -> {
            output.setText("");
            for (Pokemon p : pokemons) {
                output.append(p.toString() + "\n");
            }
        });

        exitButton.addActionListener(e -> cardLayout.show(mainPanel, "Main Menu"));
        addButton.addActionListener(e -> showAddPDialog());

        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exitButton);

        pokemonPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showAddPDialog() {
        JDialog dialog = new JDialog(mainMenuFrame, "Add Pokemon", true);
        dialog.setLayout(new GridLayout(9, 2, 10, 10));
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(mainMenuFrame);

        JTextField idInput = new JTextField();
        JTextField nameInput = new JTextField();
        JTextField type1Input = new JTextField();
        JTextField type2Input = new JTextField();
        JTextField baseLvlInput = new JTextField();
        JTextField evolvesFromInput = new JTextField();
        JTextField evolveToInput = new JTextField();
        JTextField evolutionLvlInput = new JTextField();
        JTextField hpInput = new JTextField();
        JTextField atkInput = new JTextField();
        JTextField defInput = new JTextField();
        JTextField spdInput = new JTextField();

        dialog.add(new JLabel("Pokedex Number:"));
        dialog.add(idInput);
        dialog.add(new JLabel("Pokemon Name:"));
        dialog.add(nameInput);
        dialog.add(new JLabel("Type 1:"));
        dialog.add(type1Input);
        dialog.add(new JLabel("Type 2 (Press enter to skip):"));
        dialog.add(type2Input);
        dialog.add(new JLabel("Base Level:"));
        dialog.add(baseLvlInput);
        dialog.add(new JLabel("Evolves From (Enter 0 if none):"));
        dialog.add(evolvesFromInput);
        dialog.add(new JLabel("Evolves To (Enter 0 if none):"));
        dialog.add(evolveToInput);
        dialog.add(new JLabel("Evolution Level (Enter 0 if none):"));
        dialog.add(evolutionLvlInput);
        dialog.add(new JLabel("Base HP:"));
        dialog.add(hpInput);
        dialog.add(new JLabel("Base ATK:"));
        dialog.add(atkInput);
        dialog.add(new JLabel("Base DEF:"));
        dialog.add(defInput);
        dialog.add(new JLabel("Base SPD:"));
        dialog.add(spdInput);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            try {
                int pokedexNum = Integer.parseInt(idInput.getText());
                String name = nameInput.getText();
                String type1 = type1Input.getText();
                String type2 = type2Input.getText();
                int baseLvl = Integer.parseInt(baseLvlInput.getText());
                int evolvesFrom = Integer.parseInt(evolvesFromInput.getText());
                int evolveTo = Integer.parseInt(evolveToInput.getText());
                int evolutionLvL = Integer.parseInt(evolutionLvlInput.getText());
                int hp = Integer.parseInt(hpInput.getText());
                int atk = Integer.parseInt(atkInput.getText());
                int def = Integer.parseInt(defInput.getText());
                int spd = Integer.parseInt(spdInput.getText());

                Stats stats = new Stats(hp, atk, def, spd);
                Pokemon p = new Pokemon(pokedexNum, name, type1, type2, baseLvl, evolvesFrom, evolveTo, evolutionLvL, stats);
                pController.addPokemon(p);
                JOptionPane.showMessageDialog(dialog, "Successfully added " + name + "!");

                dialog.dispose();
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(dialog, "Invalid input.");
            }
        });

        dialog.add(new JLabel());
        dialog.add(confirmButton);

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}