package Pokemon;

import Main.MainGUI;
import Main.TextAreaRenderer;
import Move.Move;
import Move.MoveController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * PokemonView is the GUI component responsible for managing the display and interaction
 * with Pokémon data in the application.
 */
public class PokemonView extends JFrame {
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;
    private TrainerController trainerController;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField tfPokedexNum, tfName, tfType1, tfType2, tfBaseLvl;
    private JTextField tfEvolvesFrom, tfEvolvesTo, tfEvolutionLvl;
    private JTextField tfHP, tfATK, tfDEF, tfSPD;

    /**
     * Constructs the GUI view for Pokémon, including tabbed panels for input and listing.
     */
    public PokemonView(PokemonController pokemonController, MoveController moveController, ItemController itemController, TrainerController trainerController) {
        this.pokemonController = pokemonController;
        this.moveController = moveController;
        this.itemController = itemController;
        this.trainerController = trainerController;

        setTitle("Pokemon Menu");
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Add Pokemon", createAddPokemonPanel());
        tabbedPane.addTab("Show All Pokemon", createShowAllPanel());

        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
            if (selectedTitle.equals("Add Pokemon")) {
                clearFields();
            }
            else if (selectedTitle.equals("Show All Pokemon")){
                refreshTableData();
            }
        });

        add(tabbedPane);
        setVisible(true);
    }

    /**
     * Creates and returns the JPanel for adding a new Pokémon.
     *
     * @return a JPanel with input fields and buttons to add a Pokémon
     */
    private JPanel createAddPokemonPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfPokedexNum = new JTextField();
        tfName = new JTextField();
        tfType1 = new JTextField();
        tfType2 = new JTextField();
        tfBaseLvl = new JTextField();
        tfEvolvesFrom = new JTextField();
        tfEvolvesTo = new JTextField();
        tfEvolutionLvl = new JTextField();
        tfHP = new JTextField();
        tfATK = new JTextField();
        tfDEF = new JTextField();
        tfSPD = new JTextField();

        panel.add(new JLabel("Pokedex Number:"));
        panel.add(tfPokedexNum);
        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Type 1:"));
        panel.add(tfType1);
        panel.add(new JLabel("Type 2 (optional):"));
        panel.add(tfType2);
        panel.add(new JLabel("Base Level:"));
        panel.add(tfBaseLvl);
        panel.add(new JLabel("Evolves From (Enter Pokedex Num [0 if none]):"));
        panel.add(tfEvolvesFrom);
        panel.add(new JLabel("Evolves To (Enter Pokedex Num [0 if none]):"));
        panel.add(tfEvolvesTo);
        panel.add(new JLabel("Evolution Level (Enter 0 if none):"));
        panel.add(tfEvolutionLvl);
        panel.add(new JLabel("HP:"));
        panel.add(tfHP);
        panel.add(new JLabel("ATK:"));
        panel.add(tfATK);
        panel.add(new JLabel("DEF:"));
        panel.add(tfDEF);
        panel.add(new JLabel("SPD:"));
        panel.add(tfSPD);

        JButton btnAdd = getAddPokemonButton();
        JButton rtnBtn = getReturnButton();

        panel.add(rtnBtn);
        panel.add(btnAdd);

        return panel;
    }

    /**
     * Returns the button that triggers the addition of a Pokémon based on form input.
     *
     * @return the "Add Pokemon" JButton with action logic
     */
    private JButton getAddPokemonButton() {
        JButton btnAdd = new JButton("Add Pokemon");
        btnAdd.addActionListener((e) -> {
            try {
                int pokedexNum = Integer.parseInt(tfPokedexNum.getText());
                String name = tfName.getText();
                String type1 = tfType1.getText();
                String type2 = tfType2.getText().isEmpty() ? null : tfType2.getText();
                int baseLvl = Integer.parseInt(tfBaseLvl.getText());
                int evolvesFrom = Integer.parseInt(tfEvolvesFrom.getText());
                int evolvesTo = Integer.parseInt(tfEvolvesTo.getText());
                int evolutionLvl = Integer.parseInt(tfEvolutionLvl.getText());

                int hp = Integer.parseInt(tfHP.getText());
                int atk = Integer.parseInt(tfATK.getText());
                int def = Integer.parseInt(tfDEF.getText());
                int spd = Integer.parseInt(tfSPD.getText());

                Stats stats = new Stats(hp, atk, def, spd);

                if (pokemonController.pokemonNumIsDup(pokedexNum)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Pokedex number found!");
                    return;
                }

                if (pokemonController.pokemonNameIsDup(name)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Pokemon name found!");
                    return;
                }

                Pokemon newPokemon = (type2 == null) ?
                        new Pokemon(pokedexNum, name, type1, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats) :
                        new Pokemon(pokedexNum, name, type1, type2, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats);

                pokemonController.addPokemon(newPokemon);
                JOptionPane.showMessageDialog(this, "Successfully added " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return btnAdd;
    }

    /**
     * Returns the button used to navigate back to the main menu.
     *
     * @return the "Return" JButton
     */
    private JButton getReturnButton(){
        JButton exitBtn = new JButton("Return to Pokedex Main Menu");
        exitBtn.addActionListener(e -> {
            dispose();
            new MainGUI(this.pokemonController, this.moveController, this.itemController, this.trainerController);
        });

        return exitBtn;
    }

    /**
     * Converts a list of Pokemon into a 2D array for use in a JTable, specifically for showing all the data
     * regarding pokemon(basic info).
     *
     * @param pokemons the list of Pokemon
     * @return a 2D Object array representing table rows and columns
     */
    private Object[][] showPokemonTableData(ArrayList<Pokemon> pokemons){
        Object[][] data = new Object[pokemons.size()][12];

        for (int i = 0; i < pokemons.size(); i++) {
            Pokemon p = pokemons.get(i);
            data[i][0] = String.format("%04d", p.getPokedexNum());
            data[i][1] = p.getName();
            data[i][2] = p.getType1();
            data[i][3] = (p.getType2() != null) ? p.getType2() : "---";
            data[i][4] = p.getBaseLvl();
            data[i][5] = (p.getEvolvesFrom() != 0) ? String.format("%04d", p.getEvolvesFrom()) : "---";
            data[i][6] = (p.getEvolvesTo() != 0) ? String.format("%04d", p.getEvolvesTo()) : "---";
            data[i][7] = (p.getEvolutionLvl() != 0) ? p.getEvolutionLvl() : "-";
            data[i][8] = p.getBaseStats().getHp();
            data[i][9] = p.getBaseStats().getAtk();
            data[i][10] = p.getBaseStats().getDef();
            data[i][11] = p.getBaseStats().getSpd();
        }

        return data;
    }

    /**
     * Converts a list of Pokemon into a 2D array for use in a JTable, specifically for showing a specific data
     * , the one that the user searched for(basic info).
     *
     * @param pokemons the list of Pokemon
     * @return a 2D Object array representing table rows and columns including moves
     */
    private Object[][] searchPokemonTableData(ArrayList<Pokemon> pokemons){
        Object[][] data = new Object[pokemons.size()][13];
        ArrayList<Move> moveSet = null;
        StringBuilder string = new StringBuilder();


        for (int i = 0; i < pokemons.size(); i++) {
            Pokemon p = pokemons.get(i);
            data[i][0] = String.format("%04d", p.getPokedexNum());
            data[i][1] = p.getName();
            data[i][2] = p.getType1();
            data[i][3] = (p.getType2() != null) ? p.getType2() : "---";
            data[i][4] = p.getBaseLvl();
            data[i][5] = (p.getEvolvesFrom() != 0) ? String.format("%04d", p.getEvolvesFrom()) : "---";
            data[i][6] = (p.getEvolvesTo() != 0) ? String.format("%04d", p.getEvolvesTo()) : "---";
            data[i][7] = (p.getEvolutionLvl() != 0) ? p.getEvolutionLvl() : "-";
            data[i][8] = p.getBaseStats().getHp();
            data[i][9] = p.getBaseStats().getAtk();
            data[i][10] = p.getBaseStats().getDef();
            data[i][11] = p.getBaseStats().getSpd();
            data[i][12] = p.getMoveSet();

            moveSet = p.getMoveSet();
            if (moveSet != null && !moveSet.isEmpty()) {
                for (int j = 0; j < moveSet.size(); j++) {
                    string.append(moveSet.get(j).getName());
                    if (j < moveSet.size() - 1) {
                        string.append(", ");
                    }
                }
                data[i][12] = string.toString();
            } else {
                data[i][12] = "";
            }
        }
        return data;
    }

    /**
     * Returns a scrollable JTable with all Pokémon displayed in tabular format.
     *
     * @param pokemons the list of Pokémon to display
     * @return a JScrollPane containing the JTable
     */
    private JScrollPane showScrollPane(ArrayList<Pokemon> pokemons) {
        String[] columnNames = {
                "Pokedex Number", "Name", "Type 1", "Type 2", "Base Level", "Evolves From",
                "Evolves To", "Evolution Level", "HP", "ATK", "DEF", "SPD"
        };

        Object[][] data = showPokemonTableData(pokemons);

        tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(table);
    }

    /**
     * Returns a scrollable JTable with searched Pokemon results.
     *
     * @param pokemons the list of matched Pokemons
     * @return a JScrollPane containing the JTable with move column
     */
    public JScrollPane searchScrollPane(ArrayList<Pokemon> pokemons) {
        String[] columnNames = {
                "Pokedex Number", "Name", "Type 1", "Type 2", "Base Level", "Evolves From",
                "Evolves To", "Evolution Level", "HP", "ATK", "DEF", "SPD", "Move"
        };

        Object[][] data = searchPokemonTableData(pokemons);

        tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(12).setCellRenderer(new TextAreaRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,300));

        return scrollPane;
    }

    /**
     * Clears all text fields in the add Pokémon form.
     */
    private void clearFields(){
        tfPokedexNum.setText("");
        tfName.setText("");
        tfType1.setText("");
        tfType2.setText("");
        tfBaseLvl.setText("");
        tfEvolvesFrom.setText("");
        tfEvolvesTo.setText("");
        tfEvolutionLvl.setText("");
        tfHP.setText("");
        tfATK.setText("");
        tfDEF.setText("");
        tfSPD.setText("");
    }

    /**
     * Refreshes the data in the "Show All Pokemon" table with the latest list.
     */
    private void refreshTableData(){
        if (tableModel != null){
            Object[][] newData = showPokemonTableData(pokemonController.getAllPokemon());
            tableModel.setRowCount(0);
            for (Object[] row: newData) {
                tableModel.addRow(row);
            }
        }
    }

    /**
     * Returns the search button that prompts for input and shows matched Pokémon.
     *
     * @return the "Search Pokemon" JButton with logic
     */
    private JButton searchButton() {
        JButton searchBtn = new JButton("Search Pokemon");
        searchBtn.addActionListener(e -> {
            String key = JOptionPane.showInputDialog(this, "Enter a search keyword (ID, name, or type):");
            if (key == null || key.trim().isEmpty()) {
                return;
            }

            ArrayList<Pokemon> results = pokemonController.searchPokemon(key.trim().toLowerCase());

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No pokemon containing the word '" + key.trim() + "' in the Pokedex.", "Search Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JScrollPane resultScroll = searchScrollPane(results);
                JOptionPane.showMessageDialog(this, resultScroll, "Search Results for '" + key.trim() + "'", JOptionPane.INFORMATION_MESSAGE);
            }

            results.clear();
        });
        return searchBtn;
    }

    /**
     * Creates and returns the panel displaying all Pokémon and associated buttons.
     *
     * @return the JPanel containing the table and search/return buttons, everything that enables the pokemon menu
     * to run.
     */
    private JPanel createShowAllPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        JScrollPane scrollPane = showScrollPane(pokemonController.getAllPokemon());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton searchBtn = searchButton();

        buttonPanel.add(searchBtn);

        JButton rtnBtn = getReturnButton();

        buttonPanel.add(rtnBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}