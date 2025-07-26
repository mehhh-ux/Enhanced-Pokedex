/**
 * MoveView.java is responsible for all outputs in the move menu.
 */
package Move;

import Item.ItemController;
import Pokemon.Pokemon;
import Pokemon.PokemonController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MoveView extends JFrame {
    private MoveController mController;
    private ItemController iController;
    private PokemonController pController;

    private JTabbedPane tabbedPane;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton addBtn, searchBtn, exitBtn;

    private JTextField tfName, tfDesc, tfClass, tfType1, tfType2;

    public MoveView(PokemonController pController, MoveController mController, ItemController iController) {
        this.mController = mController;
        this.pController = pController;
        this.iController = iController;

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

    private JPanel createAddMovePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfName = new JTextField();
        tfDesc = new JTextField();
        tfClass = new JTextField();
        tfType1 = new JTextField();
        tfType2 = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Description:"));
        panel.add(tfDesc);
        panel.add(new JLabel("Classification (HM/TM):"));
        panel.add(tfClass);
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

    private JButton getExitButton() {
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> {
            dispose();
            new Main.MainGUI(this.pController, this.mController, this.iController);
        });

        return exitBtn;
    }

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

    private JButton createSearchMoveButton() {
        JButton searchBtn = new JButton("Search Move");
        searchBtn.addActionListener(e -> {
            handleSearchMove();
        });
        return searchBtn;
    }

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

    private void handleAddMove() {
        String name = tfName.getText().trim();
        String description = tfDesc.getText().trim();
        String classification = tfClass.getText().trim();
        String type1 = tfType1.getText().trim();
        String type2 = tfType2.getText().trim();

        if (mController.moveNameIsDup(name)) {
            JOptionPane.showMessageDialog(this, "Move name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (name.isEmpty() || description.isEmpty() || classification.isEmpty() || type1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Move newMove = new Move(name, description, classification, type1, type2.isEmpty() ? null : type2);
        mController.addMove(newMove);
        JOptionPane.showMessageDialog(this, "Successfully added " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
        showAllMoves();
    }

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

    private Object[][] getMoveTableData(ArrayList<Move> moves) {
        Object[][] data = new Object[moves.size()][5];

        for (int i = 0; i < moves.size(); i++) {
            Move m = moves.get(i);
            data[i][0] = m.getName();
            data[i][1] = reduceDescription(60, m.getDescription());
            data[i][2] = m.getClassification();
            data[i][3] = m.getType1();
            data[i][4] = m.getType2() != null ? m.getType2() : "------";
        }

        return data;
    }

    private void showAllMoves() {
        ArrayList<Move> moves = mController.getAllMoves();
        displayMoves(moves, null);
    }

    private void displayMoves(ArrayList<Move> moves, String key) {
        tableModel.setRowCount(0);
        for (Move m : moves) {
            tableModel.addRow(new Object[] {
                    m.getName(),
                    reduceDescription(60, m.getDescription()),
                    m.getClassification(),
                    m.getType1(),
                    m.getType2() != null ? m.getType2() : "------"
            });
        }
    }

    private void clearFields() {
        tfName.setText("");
        tfDesc.setText("");
        tfClass.setText("");
        tfType1.setText("");
        tfType2.setText("");
    }

    /**
     //     * Shorten move descriptions if too long.
     //     * @param maxLength is the cut off.
     //     * @return the formatted description (shortened/reduced)
     //     */
    public String reduceDescription(int maxLength, String description) {
        if (description.length() > maxLength) {
            return description.substring(0, maxLength - 3) + "...";
        } else {
            return description;
        }
    }

//    /**
//     * Variable initialization for input scanning.
//     */
//    private Scanner scanner = new Scanner(System.in);
//    private String name, description, classification, type1, type2;
//
//    /**
//     * promptMoveData() asks the user information about the move they want to add.
//     * Once all information is gathered, this method then creates a new move object based
//     off of the user's input.
//     * @return the newly created move object.
//     */
//    public Move promptMoveData(){
//        System.out.println("\nYou are in the process of adding a move!");
//        System.out.println("-------------------------------------------");
//        System.out.print("Name: ");
//        name = scanner.nextLine();
//        System.out.print("Description: ");
//        description = scanner.nextLine();
//        System.out.print("Classification (HM/TM): ");
//        classification = scanner.nextLine();
//        System.out.print("Type 1: ");
//        type1 = scanner.nextLine();
//        System.out.print("Type 2 (Press Enter to skip): ");
//        type2 = scanner.nextLine();
//        type2 = type2.isEmpty() ? null : type2;
//
//        return (type2 == null)
//                ? new Move(name, description, classification, type1)
//                : new Move(name, description, classification, type1, type2);
//    }
//
//    /**
//     * Header for displaying moves and their attributes.
//     */
//    @Override
//    public void printHeader() {
//        System.out.printf("\n%-30s%-10s%-15s%-15s%-60s\n", "Name", "Class", "Type 1",
//                "Type 2", "Description");
//        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
//    }
//
//    /**
//     * Displays all attributes of the move.
//     * @param move is a class of Move which contains the move itself
//     */
//    @Override
//    public void displayAttributes(Move move) {
//        System.out.printf("%-30s%-10s%-15s%-15s%-70s\n",
//                move.getName(), move.getClassification() != null ? move.getClassification() : "-----",
//                move.getType1(), move.getType2() != null ? move.getType2() : "------", reduceDescription(60, move.getDescription()));
//    }
//
//    /**
//     * successfulPokemonAddMessage() prints a message if the addition of move is successful
//     * @param name is a String depending on the move name.
//     */
//    public void successfulMoveAddMessage(String name){
//        System.out.println("Successfully added " + name + "!");
//    }
//
//    /**
//     * Prints and displays the attributes of every move objects in an ArrayList.
//     * @param moves is a list of move objects.
//     * @param key is a string that is searched by the user (this is used with the search method)
//     */
//    @Override
//    public void printAll(ArrayList<Move> moves, String key) {
//        if (moves.isEmpty() && !key.isEmpty()) {
//            System.out.println("No move containing the word '" + key + "' in the Pokedex.");
//            return;
//        }else if (moves.isEmpty()) {
//            System.out.println("No move to show in the pokedex");
//            return;
//        }
//
//        printHeader();
//        for (Move m : moves){
//            displayAttributes(m);
//        }
//    }
//
//    /**
//     * Asks the user to enter their desired keyword for filter.
//     * @return user's input.
//     */
//    @Override
//    public String promptForSearchKey() {
//        System.out.println("\nYou are in the process of searching a move!");
//        System.out.println("--------------------------------------------");
//        System.out.print("Enter a search keyword: ");
//        return scanner.nextLine();
//    }
//
//    /**
//     * Asks the user to press enter to continue.
//     */
//    @Override
//    public void pressAnyKeyPrompt(){
//        System.out.print("Displayed all move/s available in the Pokedex.\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//
//    /**
//     * Asks the user to press enter to continue after searching for a move.
//     * @param key is a string that is searched by the user
//     */
//    @Override
//    public void pressAnyKeyPromptForSearch(String key){
//        System.out.print("Displayed all move/s containing the word/number '" + key + "' in the Pokedex.\nPress Enter to continue...");
//        scanner.nextLine();
//    }
}