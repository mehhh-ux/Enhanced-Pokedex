package Pokemon;

import Item.ItemController;
import Move.MoveController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PokemonView extends JFrame {
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField tfPokedexNum, tfName, tfType1, tfType2, tfBaseLvl;
    private JTextField tfEvolvesFrom, tfEvolvesTo, tfEvolutionLvl;
    private JTextField tfHP, tfATK, tfDEF, tfSPD;

    public PokemonView(PokemonController controller, MoveController moveController, ItemController itemController) {
        this.pokemonController = controller;
        this.moveController = moveController;
        this.itemController = itemController;

        setTitle("Pokemon Menu");
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
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return btnAdd;
    }

    private JButton getReturnButton(){
        JButton exitBtn = new JButton("Return to Pokedex Main Menu");
        exitBtn.addActionListener(e -> {
            dispose();
            new Main.MainGUI(this.pokemonController, this.moveController, this.itemController);
        });

        return exitBtn;
    }

    private Object[][] getPokemonTableData(ArrayList<Pokemon> pokemons){
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

    private JScrollPane getJScrollPane(ArrayList<Pokemon> pokemons) {
        String[] columnNames = {
                "Pokedex Number", "Name", "Type 1", "Type 2", "Base Level", "Evolves From",
                "Evolves To", "Evolution Level", "HP", "ATK", "DEF", "SPD"
        };

        Object[][] data = getPokemonTableData(pokemons);

        tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,300));

        return scrollPane;
    }

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

    private void refreshTableData(){
        if (tableModel != null){
            Object[][] newData = getPokemonTableData(pokemonController.getAllPokemon());
            tableModel.setRowCount(0);
            for (Object[] row: newData) {
                tableModel.addRow(row);
            }
        }
    }

    private JButton getJButton() {
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
                JScrollPane resultScroll = getJScrollPane(results);
                JOptionPane.showMessageDialog(this, resultScroll, "Search Results for '" + key.trim() + "'", JOptionPane.INFORMATION_MESSAGE);
            }

            results.clear();
        });
        return searchBtn;
    }

    private JPanel createShowAllPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        JScrollPane scrollPane = getJScrollPane(pokemonController.getAllPokemon());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton searchBtn = getJButton();

        buttonPanel.add(searchBtn);

        JButton rtnBtn = getReturnButton();

        buttonPanel.add(rtnBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}