package Trainer;

import Main.TextAreaRenderer;
import Item.Item;
import Item.ItemController;
import Move.Move;
import Move.MoveController;
import Pokemon.Pokemon;
import Pokemon.PokemonController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TrainerView extends JFrame {
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;
    private TrainerController trainerController;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAddTrainer, btnShowAllTrainer, btnReturnMenu;
    private JTextField tfTrainerID, tfName, tfBirthdate, tfSex, tfHometown, tfDescription;

    public TrainerView(PokemonController pokemonController, MoveController moveController, ItemController itemController, TrainerController trainerController) {
        this.pokemonController = pokemonController;
        this.moveController = moveController;
        this.itemController = itemController;
        this.trainerController = trainerController;

        setTitle("Item Menu");
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        btnAddTrainer = new JButton("Add Trainer");
        btnShowAllTrainer = new JButton("Show All Trainer");
        btnReturnMenu = new JButton("Return to Pokedex Main Menu");

        panel.add(btnAddTrainer);
        panel.add(btnShowAllTrainer);
        panel.add(btnReturnMenu);

        add(panel);
        setVisible(true);

        btnAddTrainer.addActionListener(e -> {
            JPanel addPanel = createAddTrainerPanel();
            setContentPane(addPanel);
            revalidate();
            repaint();
        });

        btnShowAllTrainer.addActionListener(e -> {
            JPanel showAllTrainersPanel = createShowAllTrainerPanel();
            setContentPane(showAllTrainersPanel);
            refreshTableData();
            revalidate();
            repaint();
        });

        btnReturnMenu.addActionListener(e ->{
            dispose();
            new Main.MainGUI(this.pokemonController, this.moveController, this.itemController, this.trainerController);
        });
    }

    private JPanel createAddTrainerPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfTrainerID = new JTextField();
        tfName = new JTextField();
        tfBirthdate = new JTextField();
        tfSex = new JTextField();
        tfHometown = new JTextField();
        tfDescription = new JTextField();

        panel.add(new JLabel("Trainer ID:"));
        panel.add(tfTrainerID);
        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Birthdate (yyyy-mm-dd):"));
        panel.add(tfBirthdate);
        panel.add(new JLabel("Sex:"));
        panel.add(tfSex);
        panel.add(new JLabel("Hometown:"));
        panel.add(tfHometown);
        panel.add(new JLabel("Description:"));
        panel.add(tfDescription);

        JButton btnAdd = getAddTrainerButton();
        JButton rtnBtn = getReturnButton();

        panel.add(rtnBtn);
        panel.add(btnAdd);

        return panel;
    }

    private JButton getAddTrainerButton() {
        JButton btnAdd = new JButton("Add Trainer");
        btnAdd.addActionListener((e) -> {
            try {
                int trainerId = Integer.parseInt(tfTrainerID.getText());
                String name = tfName.getText();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate birthDate = LocalDate.parse(tfBirthdate.getText().trim(), formatter);

                String sex = tfSex.getText();
                String hometown = tfHometown.getText();
                String description = tfDescription.getText();

                if (trainerController.trainerIdIsDup(trainerId)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Trainer ID number found!");
                    return;
                }

                if (trainerController.trainerNameIsDup(name)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Trainer name found!");
                    return;
                }

                Trainer newTrainer = new Trainer(trainerId, name, sex, hometown, description, birthDate);

                trainerController.addTrainer(newTrainer);
                JOptionPane.showMessageDialog(this, "Successfully added " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return btnAdd;
    }

    public Object[][] showTrainerTableData(ArrayList<Trainer> trainers){
        Object[][] data = new Object[trainers.size()][7];

        for (int i = 0; i < trainers.size(); i++) {
            Trainer trainer = trainers.get(i);
            data[i][0] = trainer.getTrainerId();
            data[i][1] = trainer.getName();
            data[i][2] = trainer.getBirthDate();
            data[i][3] = trainer.getSex();
            data[i][4] = trainer.getHometown();
            data[i][5] = trainer.getDescription();
            data[i][6] = String.format("₱%.2f", trainer.getMoney());
        }
        return data;
    }

    public Object[][] getItemTableData(ArrayList<Item> items){
        Object[][] data = new Object[items.size()][6];

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            data[i][0] = item.getName();
            data[i][1] = item.getCategory();
            data[i][2] = item.getDescription();
            data[i][3] = item.getEffect();
            data[i][4] = item.getBuyingPrice() == 0.0 ? "Not Sold" : String.format("₱%.2f", (item.getBuyingPrice()));
            data[i][5] = String.format("₱%.2f", item.getSellingPrice());
        }
        return data;
    }

    public JTable getItemTable(ArrayList<Item> items) {
        String[] columnNames = {
                "Name", "Category", "Description", "Effect", "Buying Price", "Selling Price"
        };

        Object[][] data = getItemTableData(items);

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

        table.getColumnModel().getColumn(2).setCellRenderer(new TextAreaRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());

        return table;
    }

    public Object[][] showPokemonTableData(ArrayList<Pokemon> pokemons){
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

    public JTable getPokemonTable(ArrayList<Pokemon> pokemons) {
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

        return table;
    }

    private Object[][] getMoveTableData(ArrayList<Move> moves) {
        Object[][] data = new Object[moves.size()][5];

        for (int i = 0; i < moves.size(); i++) {
            Move m = moves.get(i);
            data[i][0] = m.getName();
            data[i][1] = m.getDescription();
            data[i][2] = m.getClassification();
            data[i][3] = m.getType1();
            data[i][4] = m.getType2() != null ? m.getType2() : "------";
        }

        return data;
    }

    public JTable getMoveTable(ArrayList<Move> moves) {
        String[] columns = { "Name", "Description", "Classification", "Type 1", "Type 2" };

        Object[][] data = getMoveTableData(moves);

        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int columns) {
                return false;
            }
        };

        table = new JTable(tableModel);
            table.setAutoCreateRowSorter(true);
            table.setFillsViewportHeight(true);
            table.getTableHeader().setReorderingAllowed(false);

            table.getColumnModel().getColumn(1).setCellRenderer(new TextAreaRenderer());

            return table;
    }

    private MouseAdapter trainerIdMouseAdapter(JTable table) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0 && e.getClickCount() == 2) {
                    String sTrainerID = table.getValueAt(row, 0).toString();
                    try {
                        int nTrainerID = Integer.parseInt(sTrainerID);
                        Trainer trainer = trainerController.getTrainerById(nTrainerID);
                        if (trainer != null) {
                            JPanel simulationPanel = createTrainerSimulationPanel(trainer);
                            JOptionPane.showMessageDialog(null, simulationPanel, "Simulate Trainer: " + trainer.getName(), JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid Trainer ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
    }

    private JScrollPane showScrollPane(ArrayList<Trainer> trainers) {
        String[] columnNames = {
                "Trainer ID", "Name", "Birthdate", "Sex", "Hometown", "Description", "Money"
        };

        Object[][] data = showTrainerTableData(trainers);

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

        table.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());
        table.setToolTipText("Double click a row to simulate a trainer.");

        table.addMouseListener(trainerIdMouseAdapter(table));

        return new JScrollPane(table);
    }

    public Object[][] searchTrainerTableData(ArrayList<Trainer> trainers){
        Object[][] data = new Object[trainers.size()][9];
        ArrayList<Pokemon> lineUp;
        ArrayList<Item> itemInventory;
        StringBuilder itemInventoryString = new StringBuilder();
        StringBuilder lineUpString = new StringBuilder();

        for (int i = 0; i < trainers.size(); i++) {
            Trainer trainer = trainers.get(i);
            lineUpString.setLength(0);
            itemInventoryString.setLength(0);
            data[i][0] = trainer.getTrainerId();
            data[i][1] = trainer.getName();
            data[i][2] = trainer.getBirthDate();
            data[i][3] = trainer.getSex();
            data[i][4] = trainer.getHometown();
            data[i][5] = trainer.getDescription();
            data[i][6] = String.format("₱%.2f", trainer.getMoney());
            data[i][7] = trainer.getLineup();
            data[i][8] = trainer.getItems();

            lineUp = trainer.getLineup();
            if (lineUp != null && !lineUp.isEmpty()) {
                for (int j = 0; j < lineUp.size(); j++) {
                    lineUpString.append(lineUp.get(j).getName());
                    if (j < lineUp.size() - 1) {
                        lineUpString.append(", ");
                    }
                }
                data[i][7] = lineUp.toString();
            } else {
                data[i][7] = "";
            }

            itemInventory = trainer.getItems();
            if (itemInventory != null && !itemInventory.isEmpty()) {
                for (int j = 0; j < itemInventory.size(); j++) {
                    itemInventoryString.append(itemInventory.get(j).getName());
                    if (j < itemInventory.size() - 1) {
                        lineUpString.append(", ");
                    }
                }
                data[i][8] = itemInventoryString.toString();
            } else {
                data[i][8] = "";
            }
        }
        return data;
    }

    private JScrollPane searchScrollPane(ArrayList<Trainer> trainers) {
        String[] columnNames = {
                "Trainer ID", "Name", "Birthdate", "Sex", "Hometown", "Description", "Money", "Pokemon Line Up", "Item Inventory"
        };

        Object[][] data = searchTrainerTableData(trainers);

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

        table.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());
        table.getColumnModel().getColumn(7).setCellRenderer(new TextAreaRenderer());
        table.getColumnModel().getColumn(8).setCellRenderer(new TextAreaRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,300));

        table.setToolTipText("Double click a row to simulate a trainer.");
        table.addMouseListener(trainerIdMouseAdapter(table));

        return scrollPane;
    }

    private void clearFields(){
        tfTrainerID.setText("");
        tfName.setText("");
        tfBirthdate.setText("");
        tfSex.setText("");
        tfHometown.setText("");
        tfDescription.setText("");
    }

    private void refreshTableData(){
        if (tableModel != null){
            Object[][] newData = showTrainerTableData(trainerController.getAllTrainer());
            tableModel.setRowCount(0);
            for (Object[] row: newData) {
                tableModel.addRow(row);
            }
        }
    }

    private void itemBuyQuantity(Item item, Trainer trainer){
        String input = JOptionPane.showInputDialog(this, "How many of " + item.getName() + " would you like to purchase?");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int quantity = Integer.parseInt(input);

            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number.", "Negative Quantity", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price = item.getBuyingPrice();
            boolean successfulPurchase = trainerController.buyItem(trainer, item, price, quantity);

            if (successfulPurchase) {
                JOptionPane.showMessageDialog(this,
                        "Successfully purchased " + quantity + "x " + item.getName() + ".\n" +
                        "Remaining Money: " + trainer.getMoney(), "Purchase Successful!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Purchase failed.\nCheck if your bag is full, item limits are reached, or you have insufficient funds.",
                        "Purchase Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.", "Buying Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton buyItemButton(Trainer trainer){
        JButton buyBtn = new JButton("Buy Item");
        buyBtn.addActionListener(e ->{
            JTable itemTable = getItemTable(itemController.getAllItems());
            JScrollPane itemScrollPane = new JScrollPane(itemTable);
            itemScrollPane.setPreferredSize(new Dimension(500, 300));

            int option = JOptionPane.showConfirmDialog(null,
                    itemScrollPane,
                    "Select an item to buy (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String itemName = itemTable.getValueAt(selectedRow, 0).toString();
                Item item = itemController.getItemByName(itemName);
                itemBuyQuantity(item, trainer);
            }
                });
        return buyBtn;
    }

    private void sellItemQuantity(Item item, Trainer trainer){
        String input = JOptionPane.showInputDialog(this, "How many of " + item.getName() + " would you like to sell?");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int quantity = Integer.parseInt(input);

            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number.", "Negative Quantity", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean soldSuccessful = trainerController.sellItem(trainer, item, quantity);

            if (soldSuccessful) {
                JOptionPane.showMessageDialog(this,
                        "Successfully sold " + quantity + "x " + item.getName() + ".\n" +
                                "Remaining Money: " + trainer.getMoney(), "Selling Successful!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selling failed.\nCheck if the quantity of the item that you will sell is lesser than or equal to the item that you have.",
                        "Selling Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.", "Selling Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton sellItemButton(Trainer trainer){
        JButton sellBtn = new JButton("Sell Item");
        sellBtn.addActionListener(e ->{
            JTable table = getItemTable(trainer.getItems());
            JScrollPane itemScrollPane = new JScrollPane(table);
            itemScrollPane.setPreferredSize(new Dimension(500, 300));

            int option = JOptionPane.showConfirmDialog(null,
                    itemScrollPane,
                    "Select an item to sell (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String itemName = table.getValueAt(selectedRow, 0).toString();
                Item item = itemController.getItemByName(itemName);
                sellItemQuantity(item, trainer);
            }
        });
        return sellBtn;
    }

    private JButton addPkmnToLnUpButton(Trainer trainer){
        JButton addPkmnLnUpBtn = new JButton("Add Pokemon to Lineup");
        addPkmnLnUpBtn.addActionListener(e ->{
            JTable table = getPokemonTable(pokemonController.getAllPokemon());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int option = JOptionPane.showConfirmDialog(null,
                    scrollPane,
                    "Select a pokemon to add to Lineup (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Pokemon not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonName = table.getValueAt(selectedRow, 1).toString();
                Pokemon pokemon = pokemonController.getPokemonByName(pokemonName);
                boolean successAddPokemon = trainerController.addPokemonToLineup(trainer, pokemon);
                if (successAddPokemon) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully added " + pokemon.getName() + " to the Lineup.\n",
                            "Pokemon Added",
                            JOptionPane.INFORMATION_MESSAGE);
                    viewPokemonsInLineup(trainer);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Pokemon adding failed.\nCheck if the you already have a maximum of 6 pokemons in your lineup.",
                            "Pokemon Adding Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return addPkmnLnUpBtn;
    }

    private JButton addPkmnToStrg(Trainer trainer) {
        JButton addPkmnToStrgBtn = new JButton("Add Pokemon to Storage");
        addPkmnToStrgBtn.addActionListener(e -> {
            JTable table = getPokemonTable(pokemonController.getAllPokemon());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int option = JOptionPane.showConfirmDialog(null,
                    scrollPane,
                    "Select a Pokemon to add to storage (Select then press OK).",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Pokemon not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonName = table.getValueAt(selectedRow, 1).toString();
                Pokemon pokemon = pokemonController.getPokemonByName(pokemonName);
                boolean successAddPokemon = trainerController.addPokemonToStorage(trainer, pokemon);
                if (successAddPokemon) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully added " + pokemon.getName() + " to Storage.\n",
                            "Pokemon Added",
                            JOptionPane.INFORMATION_MESSAGE);
                    viewPokemonsInStorage(trainer);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Pokemon adding failed.\nCheck if the you already have a maximum of 30 pokemons in your storage.",
                            "Pokemon Adding Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return addPkmnToStrgBtn;
    }
    
    private JButton viewPokemonsInLineup(Trainer trainer){
        JButton viewPokemonsInLineupBtn = new JButton("View Pokemon in Lineup");
        viewPokemonsInLineupBtn.addActionListener(e -> {
            JTable table = getPokemonTable(trainer.getLineup());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));
        });
        return viewPokemonsInLineupBtn;
    }

    private JButton viewPokemonsInStorage(Trainer trainer){
        JButton viewPokemonsInStorageBtn = new JButton("View Pokemon in Storage");
        viewPokemonsInStorageBtn.addActionListener(e -> {
            JTable table = getPokemonTable(trainer.getStorage());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));
        });
        return viewPokemonsInStorageBtn;
    }

    private JButton viewAllPokemonsofTrainer(Trainer trainer){
        JButton viewAllPokemonsofTrainerBtn = new JButton("View All Pokemon of the Trainer");
        viewAllPokemonsofTrainerBtn.addActionListener(e -> {
            JTable table = getPokemonTable(trainer.getAllPokemon());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));
        });
        return viewAllPokemonsofTrainerBtn;
    }

    private JButton releasePkmnFromLnUpButton(Trainer trainer){
        JButton releasePkmnLnUpBtn = new JButton("Release Pokemon From Lineup");
        releasePkmnLnUpBtn.addActionListener(e ->{
            JTable table = getPokemonTable(trainer.getLineup());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int option = JOptionPane.showConfirmDialog(null,
                    scrollPane,
                    "Select a pokemon to remove from Lineup (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Pokemon not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonName = table.getValueAt(selectedRow, 1).toString();
                Pokemon pokemon = pokemonController.getPokemonByName(pokemonName);
                boolean releasePokemon = trainerController.releasePokemonFromLineup(trainer, pokemon);
                if (releasePokemon) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully removed " + pokemon.getName() + " from Lineup.\n",
                            "Pokemon Removed",
                            JOptionPane.INFORMATION_MESSAGE);
                    //viewPokemoninLineup method
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Releasing Pokemon Failed.\nCheck if there is still a pokemon in your Lineup.",
                            "Releasing Pokemon Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return releasePkmnLnUpBtn;
    }

    private JButton releasePkmnFromStrgButton(Trainer trainer){
        JButton releasePkmnStrgBtn = new JButton("Release Pokemon From Storage");
        releasePkmnStrgBtn.addActionListener(e ->{
            JTable table = getPokemonTable(trainer.getAllPokemon());
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            int option = JOptionPane.showConfirmDialog(null,
                    scrollPane,
                    "Select a pokemon to remove from Storage (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Pokemon not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonName = table.getValueAt(selectedRow, 1).toString();
                Pokemon pokemon = pokemonController.getPokemonByName(pokemonName);
                boolean releasePokemon = trainerController.releasePokemonFromStorage(trainer, pokemon);
                if (releasePokemon) {
                    JOptionPane.showMessageDialog(this,
                            "Successfully removed " + pokemon.getName() + " from Storage.\n",
                            "Pokemon Removed",
                            JOptionPane.INFORMATION_MESSAGE);
                    //viewPokemoninLineup method
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Releasing Pokemon Failed.\nCheck if there is still a pokemon in your Storage.",
                            "Releasing Pokemon Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return releasePkmnStrgBtn;
    }

    private JButton switchPokemonFromStorage(Trainer trainer){
        JButton switchPokemonFromStorageBtn = new JButton("Switch Pokemon From Storage");
        switchPokemonFromStorageBtn.addActionListener(e ->{
            JTable tableStorage = getPokemonTable(trainer.getStorage());
            JScrollPane scrollPaneStorage = new JScrollPane(tableStorage);
            scrollPaneStorage.setPreferredSize(new Dimension(500, 300));
            int optionStorage = JOptionPane.showConfirmDialog(null,
                    scrollPaneStorage,
                    "Select a Pokemon to switch from Storage (Select then press OK).",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (optionStorage == JOptionPane.OK_OPTION) {
                int selectedRowStorage = table.getSelectedRow();
                if (selectedRowStorage == -1) {
                    JOptionPane.showMessageDialog(null, "Pokemon not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonNameStorage = table.getValueAt(selectedRowStorage, 1).toString();
                Pokemon pokemonStorage = pokemonController.getPokemonByName(pokemonNameStorage);

                JTable tableLineup = getPokemonTable(trainer.getLineup());
                JScrollPane scrollPaneLineup = new JScrollPane(tableLineup);
                scrollPaneLineup.setPreferredSize(new Dimension(500, 300));
                int optionLineup = JOptionPane.showConfirmDialog(null,
                        scrollPaneStorage,
                        "Select a Pokemon to switch from Lineup (Select then press OK).",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (optionLineup == JOptionPane.OK_OPTION) {
                    int selectedRowLineup = table.getSelectedRow();
                    if (selectedRowLineup == -1) {
                        JOptionPane.showMessageDialog(null, "Pokemon not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String pokemonNameLineup = table.getValueAt(selectedRowLineup, 1).toString();
                    Pokemon pokemonLineup = pokemonController.getPokemonByName(pokemonNameStorage);

                    boolean successSwitchPokemon = trainerController.switchPokemonFromStorge(trainer, pokemonLineup, pokemonStorage);
                    if (successSwitchPokemon) {
                        JOptionPane.showMessageDialog(this,
                                "Successfully switched " + pokemonStorage.getName() + " with " + pokemonLineup.getName() + ".\n",
                                "Pokemon Switched",
                                JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Pokemon Switching failed.\nCheck if the you have a pokemon from either your lineup or storage.",
                                "Pokemon Switching Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });
        return switchPokemonFromStorageBtn;
    }

    private JButton teachMoveButton(Trainer trainer){
        JButton teachMoveBtn = new JButton("Teach Move to a Pokemon");
        teachMoveBtn.addActionListener(e ->{
            JTable pokemonTable = getPokemonTable(trainer.getAllPokemon());
            JScrollPane pokemonScrollPane = new JScrollPane(pokemonTable);
            pokemonScrollPane.setPreferredSize(new Dimension(500, 300));

            int pokemonOption = JOptionPane.showConfirmDialog(null,
                    pokemonScrollPane,
                    "Select a pokemon to teach a move (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (pokemonOption == JOptionPane.OK_OPTION) {
                int pokemonSelectedRow = pokemonTable.getSelectedRow();  // FIXED
                if (pokemonSelectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Pokemon selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonName = pokemonTable.getValueAt(pokemonSelectedRow, 1).toString();  // column 1 is name
                Pokemon pokemon = pokemonController.getPokemonByName(pokemonName);
                if (pokemon == null) {
                    JOptionPane.showMessageDialog(null, "Could not find Pokemon.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JTable moveTable = getMoveTable(moveController.getAllMoves());
                JScrollPane moveScrollPane = new JScrollPane(moveTable);
                moveScrollPane.setPreferredSize(new Dimension(500, 300));

                int moveOption = JOptionPane.showConfirmDialog(null,
                        moveScrollPane,
                        "Select a move to teach to " + pokemonName + " (select then press OK)",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (moveOption == JOptionPane.OK_OPTION) {
                    int moveSelectedRow = moveTable.getSelectedRow();  // FIXED
                    if (moveSelectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "No move selected.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String moveName = moveTable.getValueAt(moveSelectedRow, 0).toString();  // column 0 is name
                    Move move = moveController.getMoveByName(moveName);
                    if (move == null) {
                        JOptionPane.showMessageDialog(null, "Could not find move.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean moveTaught = trainerController.teachMoves(trainer, pokemon, move);
                    if (moveTaught) {
                        JOptionPane.showMessageDialog(null,
                                "Successfully taught " + pokemon.getName() + " the move " + move.getName() + ".",
                                "Move Taught",
                                JOptionPane.INFORMATION_MESSAGE);
                        viewAllPokemonsofTrainer(trainer);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Teaching move failed. Check if the Pokemon already knows 4 moves.",
                                "Move Teaching Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        return teachMoveBtn;
    }

    private JButton removeMoveButton(Trainer trainer){
        JButton removeMoveBtn = new JButton("Remove a Move from a Pokemon");
        removeMoveBtn.addActionListener(e ->{
            JTable pokemonTable = getPokemonTable(trainer.getAllPokemon());
            JScrollPane pokemonScrollPane = new JScrollPane(pokemonTable);
            pokemonScrollPane.setPreferredSize(new Dimension(500, 300));

            int pokemonOption = JOptionPane.showConfirmDialog(null,
                    pokemonScrollPane,
                    "Select a pokemon to remove a move (select then press OK)",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (pokemonOption == JOptionPane.OK_OPTION) {
                int pokemonSelectedRow = pokemonTable.getSelectedRow();  // FIXED
                if (pokemonSelectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "No Pokemon selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String pokemonName = pokemonTable.getValueAt(pokemonSelectedRow, 1).toString();  // column 1 is name
                Pokemon pokemon = pokemonController.getPokemonByName(pokemonName);
                if (pokemon == null) {
                    JOptionPane.showMessageDialog(null, "Could not find Pokemon.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JTable moveTable = getMoveTable(pokemon.getMoveSet());
                JScrollPane moveScrollPane = new JScrollPane(moveTable);
                moveScrollPane.setPreferredSize(new Dimension(500, 300));

                int moveOption = JOptionPane.showConfirmDialog(null,
                        moveScrollPane,
                        "Select a move to remove from " + pokemonName + " (select then press OK)",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (moveOption == JOptionPane.OK_OPTION) {
                    int moveSelectedRow = moveTable.getSelectedRow();  // FIXED
                    if (moveSelectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "No move selected.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String moveName = moveTable.getValueAt(moveSelectedRow, 0).toString();  // column 0 is name
                    Move move = moveController.getMoveByName(moveName);
                    if (move == null) {
                        JOptionPane.showMessageDialog(null, "Could not find move.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    boolean moveRemoved = trainerController.removeMove(trainer, pokemon, move);
                    if (moveRemoved) {
                        JOptionPane.showMessageDialog(null,
                                "Successfully remove " + pokemon.getName() + " the move " + move.getName() + ".",
                                "Move Removed",
                                JOptionPane.INFORMATION_MESSAGE);
                        viewAllPokemonsofTrainer(trainer);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Removing move failed. Check if the Pokemon has still a move or if the chosen move to be removed is an HM Classification.",
                                "Move Removing Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        return removeMoveBtn;
    }

    private JPanel createTrainerSimulationPanel(Trainer trainer){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JButton buyBtn = buyItemButton(trainer);
        JButton sellBtn = sellItemButton(trainer);
        JButton addLineupBtn = addPkmnToLnUpButton(trainer);
        JButton viewPokemonsInLineupBtn = viewPokemonsInLineup(trainer);
        JButton addStorageBtn = addPkmnToStrg(trainer);
        JButton viewPokemonsInStorageBtn = viewPokemonsInStorage(trainer);
        JButton releaseLineupBtn = releasePkmnFromLnUpButton(trainer);
        JButton releaseStorageBtn = releasePkmnFromStrgButton(trainer);
        JButton teachMoveBtn = teachMoveButton(trainer);
        JButton removeMoveButtonBtn = removeMoveButton(trainer);
        JButton viewAllPokemonsofTrainerBtn = viewAllPokemonsofTrainer(trainer);
        JButton switchPokemonFromStorageBtn = switchPokemonFromStorage(trainer);


        panel.add(buyBtn);
        panel.add(sellBtn);
        //panel.add(useBtn);
        panel.add(addLineupBtn);
        panel.add(viewPokemonsInLineupBtn);
        panel.add(releaseLineupBtn);
        panel.add(addStorageBtn);
        panel.add(viewPokemonsInStorageBtn);
        panel.add(releaseStorageBtn);
        panel.add(switchPokemonFromStorageBtn);
        panel.add(viewAllPokemonsofTrainerBtn);
        panel.add(teachMoveBtn);
        panel.add(removeMoveButtonBtn);


        return panel;
    }

    private JButton searchButton() {
        JButton searchBtn = new JButton("Search Trainer");
        searchBtn.addActionListener(e -> {
            String key = JOptionPane.showInputDialog(this, "Enter a search keyword (name, trainer id, hometown, or sex):");
            if (key == null || key.trim().isEmpty()) {
                return;
            }

            ArrayList<Trainer> results = trainerController.searchTrainer(key.trim().toLowerCase());

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No trainer containing the word '" + key.trim() + "' in the Pokedex.", "Search Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                JScrollPane resultScroll = searchScrollPane(results);
                JOptionPane.showMessageDialog(this, resultScroll, "Search Results for '" + key.trim() + "'", JOptionPane.INFORMATION_MESSAGE);
            }

            results.clear();
        });
        return searchBtn;
    }

    private JButton getReturnButton(){
        JButton exitBtn = new JButton("Return to Trainer Menu");
        exitBtn.addActionListener(e -> {
            dispose();
            new TrainerView(this.pokemonController, this.moveController, this.itemController, this.trainerController);
        });

        return exitBtn;
    }

    private JPanel createShowAllTrainerPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // spacing & alignment

        JScrollPane scrollPane = showScrollPane(trainerController.getAllTrainer());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton searchBtn = searchButton();
        JButton returnBtn = getReturnButton();

        buttonPanel.add(searchBtn);
        buttonPanel.add(returnBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}