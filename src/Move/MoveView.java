/**
 * MoveView.java is responsible for all outputs in the move menu.
 */
package Move;

import Pokemon.PokemonController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MoveView extends JFrame {
    private MoveController controller;
    private JTextField nameFld, descFld, type1Fld, type2Fld, classFld, searchFld;
    private JTextArea output;
    private JButton addBtn, searchBtn, showAllBtn, exitBtn;

    public MoveView(MoveController controller) {
        this.controller = controller;
        iniGUI();
    }

    private void iniGUI() {
        setTitle("Move Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPnl = new JPanel(new BorderLayout(20, 20));
        mainPnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPnl = new JPanel(new GridLayout(6, 2 , 10, 10));

        inputPnl.add(new JLabel("Name:"));
        nameFld = new JTextField();
        inputPnl.add(nameFld);

        inputPnl.add(new JLabel("Description:"));
        descFld = new JTextField();
        inputPnl.add(descFld);

        inputPnl.add(new JLabel("Classification (HM/TM):"));
        classFld = new JTextField();
        inputPnl.add(classFld);

        inputPnl.add(new JLabel("Type 1:"));
        type1Fld = new JTextField();
        inputPnl.add(type1Fld);

        inputPnl.add(new JLabel("Type 2 (optional):"));
        type2Fld = new JTextField();
        inputPnl.add(type2Fld);

        addBtn = new JButton("Add Move");
        inputPnl.add(addBtn);

        JPanel searchPnl = new JPanel(new BorderLayout(10, 10));
        searchPnl.setBorder(BorderFactory.createTitledBorder("Search Move"));

        searchFld = new JTextField();
        searchPnl.add(searchFld, BorderLayout.SOUTH);

        output = new JTextArea(15, 40);
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);

        exitBtn = new JButton("Exit");

        mainPnl.add(inputPnl, BorderLayout.WEST);
        mainPnl.add(searchPnl, BorderLayout.EAST);
        mainPnl.add(scrollPane, BorderLayout.CENTER);
        mainPnl.add(exitBtn, BorderLayout.SOUTH);

        setContentPane(mainPnl);
        pack();
        setVisible(true);

        addBtn.addActionListener(e -> handleAddMove());
        searchBtn.addActionListener(e -> handleSearchMove());
        showAllBtn.addActionListener(e -> showAllMoves());
        exitBtn.addActionListener(e -> {
            dispose();
            new Main.MainGUI(new PokemonController(), new MoveController());
        });
    }

    private void handleAddMove() {
        String name = nameFld.getText().trim();
        String description = descFld.getText().trim();
        String classification = classFld.getText().trim();
        String type1 = type1Fld.getText().trim();
        String type2 = type2Fld.getText().trim();

        if (controller.moveNameIsDup(name)) {
            JOptionPane.showMessageDialog(this, "Move name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Move newMove = new Move(name, description, classification, type1, type2.isEmpty() ? null : type2);
        controller.addMove(newMove);
        JOptionPane.showMessageDialog(this, "Successfully added " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void handleSearchMove() {
        String key = searchFld.getText().trim().toLowerCase();
        ArrayList<Move> results = controller.searchMove(key);
        displayMoves(results, key);
    }

    private void showAllMoves() {
        ArrayList<Move> moves = controller.getAllMoves();
        displayMoves(moves, null);
    }

    private void displayMoves(ArrayList<Move> moves, String key) {
        output.setText("");
        for (Move m : moves) {
            output.append(
                    "Name: " + m.getName() + "\n" +
                    "Description: " + m.getDescription() + "\n" +
                    "Classification: " + m.getClassification() + "\n" +
                    "Type 1: " + m.getType1() + "\n" +
                    "Type 2: " + (m.getType2() != null ? m.getType2() : "------") + "\n" +
                    "----------------------------\n"
            );
        }
    }

    private void clearFields() {
        nameFld.setText("");
        descFld.setText("");
        classFld.setText("");
        type1Fld.setText("");
        type2Fld.setText("");
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
//     * Shorten move descriptions if too long.
//     * @param maxLength is the cut off.
//     * @return the formatted description (shortened/reduced)
//     */
//    public String reduceDescription(int maxLength, String description) {
//        if (description.length() > maxLength) {
//            return description.substring(0, maxLength - 3) + "...";
//        } else {
//            return description;
//        }
//    }
//
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