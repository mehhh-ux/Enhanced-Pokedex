package Move;

import Item.ItemController;
import Pokemon.Pokemon;
import Pokemon.PokemonController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * MoveView is the GUI component responsible for managing the display and interaction
 * with Move data in the application.
 */
public class MoveView extends JFrame {
    private MoveController mController;
    private ItemController iController;
    private PokemonController pController;
    private TrainerController trainerController;

    private JTabbedPane tabbedPane;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton addBtn, searchBtn, exitBtn;

    private JTextField tfName, tfDesc, tfType1, tfType2;

    private JComboBox<String> classComboBox;

    /**
     * Constructs the GUI view for Move, including tabbed panels for input and listing.
     */
    public MoveView(PokemonController pokemonController, MoveController moveController, ItemController itemController, TrainerController trainerController) {
        this.mController = moveController;
        this.pController = pokemonController;
        this.iController = itemController;
        this.trainerController = trainerController;

        setTitle("Move Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Add Move", createAddMovePanel());
        tabbedPane.addTab("Show All Moves", createShowAllPanel());

        add(tabbedPane);
        setVisible(true);
    }

    /**
     * Creates and returns the JPanel for adding a new Move.
     *
     * @return a JPanel with input fields and buttons to add a Move
     */
    private JPanel createAddMovePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(300, 200));

        tfName = new JTextField();
        tfDesc = new JTextField();
        tfType1 = new JTextField();
        tfType2 = new JTextField();

        String[] classifications = { "— Select —", "HM", "TM"};
        classComboBox = new JComboBox<>(classifications);

        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Description:"));
        panel.add(tfDesc);
        panel.add(new JLabel("Classification (HM/TM):"));
        panel.add(classComboBox);
        panel.add(new JLabel("Type 1:"));
        panel.add(tfType1);
        panel.add(new JLabel("Type 2 (optional):"));
        panel.add(tfType2);

        JButton addBtn = getAddMoveButton();
        JButton exitBtn = getExitButton();

        panel.add(addBtn);
        panel.add(exitBtn);

        return panel;
    }

    /**
     * Returns the button that triggers the addition of a Move based on form input.
     *
     * @return the "Add Move" JButton with action logic
     */
    private JButton getAddMoveButton() {
        JButton btnAdd = new JButton("Add Move");
        btnAdd.addActionListener((e) -> {
            try {
                handleAddMove();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        return btnAdd;
    }

    /**
     * Returns the button used to navigate back to the main menu.
     *
     * @return the "Exit" JButton
     */
    private JButton getExitButton() {
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> {
            dispose();
            new Main.MainGUI(this.pController, this.mController, this.iController, this.trainerController);
        });

        return exitBtn;
    }

    /**
     * Creates and returns the panel displaying all Move and associated buttons.
     *
     * @return the JPanel containing the table and search/return buttons, everything that enables the move menu
     * to run.
     */
    private JPanel createShowAllPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPnl = new JPanel();

        JScrollPane scrollPane = getJScrollPane(mController.getAllMoves());
        panel.add(scrollPane, BorderLayout.CENTER);

        searchBtn = createSearchMoveButton();
        exitBtn = getExitButton();

        buttonPnl.add(searchBtn);
        buttonPnl.add(exitBtn);

        panel.add(buttonPnl, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Returns the search button that prompts for input and shows matched Move.
     *
     * @return the "Search Move" JButton with logic
     */
    private JButton createSearchMoveButton() {
        JButton searchBtn = new JButton("Search Move");
        searchBtn.addActionListener(e -> {
            handleSearchMove();
        });
        return searchBtn;
    }

    /**
     * Returns a scrollable JTable with searched MOve results.
     *
     * @param moves the list of matched Moves
     * @return a JScrollPane containing the JTable
     */
    private JScrollPane getJScrollPane(ArrayList<Move> moves) {
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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        return scrollPane;
    }

    /**
     * Handles the logic of adding a move to the database.
     *
     * prints a successful message if addition of move is successful
     */
    private void handleAddMove() {
        String name = tfName.getText().trim();
        String description = tfDesc.getText().trim();
        String classification = (String) classComboBox.getSelectedItem();
        String type1 = tfType1.getText().trim();
        String type2 = tfType2.getText().trim();

        if (mController.moveNameIsDup(name)) {
            JOptionPane.showMessageDialog(this, "Move name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.isEmpty() || description.isEmpty() || classification.equals("— Select —") || type1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Move newMove = new Move(name, description, classification, type1, type2.isEmpty() ? null : type2);
        mController.addMove(newMove);
        JOptionPane.showMessageDialog(this, "Successfully added " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
        showAllMoves();
    }

    /**
     * Handles the logic of searching a move from the database.
     *
     * prints a the result for searching for a move/s
     */
    private void handleSearchMove() {
        String key = JOptionPane.showInputDialog(this, "Enter a search keyword:", "Search Move", JOptionPane.QUESTION_MESSAGE);
        if (key == null || key.trim().isEmpty()) {
            return;
        }

        ArrayList<Move> results = mController.searchMove(key.trim().toLowerCase());

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No move containing the word '" + key.trim() + "' in the Pokedex.", "Search Failed", JOptionPane.ERROR_MESSAGE);
        } else {
            JScrollPane resultScroll = getJScrollPane(results);
            JOptionPane.showMessageDialog(this, resultScroll, "Search Results for '" + key.trim() + "'", JOptionPane.INFORMATION_MESSAGE);
        }

        results.clear();
    }

    /**
     * Converts a list of Move into a 2D array for use in a JTable, specifically for showing a specific data
     * , the one that the user searched for (basic info).
     *
     * @param moves the list of Move
     * @return a 2D Object array representing table rows and columns
     */
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

    /**
     * Responsible for retrieving the Move object/s
     */
    private void showAllMoves() {
        ArrayList<Move> moves = mController.getAllMoves();
        displayMoves(moves);
    }

    /**
     * Display all the move objects and their corresponding information.
     */
    private void displayMoves(ArrayList<Move> moves) {
        tableModel.setRowCount(0);
        for (Move m : moves) {
            tableModel.addRow(new Object[] {
                    m.getName(),
                    m.getDescription(),
                    m.getClassification(),
                    m.getType1(),
                    m.getType2() != null ? m.getType2() : "------"
            });
        }
    }

    /**
     * Clears all text fields in the add Move form.
     */
    private void clearFields() {
        tfName.setText("");
        tfDesc.setText("");
        tfType1.setText("");
        tfType2.setText("");
    }
}