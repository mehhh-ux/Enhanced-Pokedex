package Move;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveView {
    private Scanner scanner = new Scanner(System.in);
    private String name, description, classification, type1, type2;

    public MoveModel promptMoveData(){
        System.out.println("\nYou are in the process of adding a move!");
        System.out.println("-------------------------------------------");
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Description: ");
        description = scanner.nextLine();
        System.out.print("Classification (HM/TM): ");
        classification = scanner.nextLine();
        System.out.print("Type 1: ");
        type1 = scanner.nextLine();
        System.out.print("Type 2 (Press Enter to skip): ");
        type2 = scanner.nextLine();
        type2 = type2.isEmpty() ? null : type2;

        return (type2 == null)
                ? new MoveModel(name, description, classification, type1)
                : new MoveModel(name, description, classification, type1, type2);
    }

    /**
     * Header for displaying moves and their attributes.
     */
    public void printHeader() {
        System.out.printf("\n%-30s%-10s%-15s%-15s%-60s\n", "Name", "Class", "Type 1",
                "Type 2", "Description");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays all attributes of the move.
     */
    public void displayMoveAttributes(MoveModel move) {
        System.out.printf("%-30s%-10s%-15s%-15s%-70s\n",
                move.getName(), move.getClassification() != null ? move.getClassification() : "-----",
                move.getType1(), move.getType2() != null ? move.getType2() : "------", reduceDescription(60, move.getDescription()));
    }

    /**
     * Shorten move descriptions if too long.
     * @param maxLength is the cut off.
     * @return the formatted description (shortened/reduced)
     */
    public String reduceDescription(int maxLength, String description) {
        if (description.length() > maxLength) {
            return description.substring(0, maxLength - 3) + "...";
        } else {
            return description;
        }
    }

    public void successfulMoveAddMessage(String name){
        System.out.println("Successfully added " + name + "!");
    }

    public void printAllMoves(ArrayList<MoveModel> moves){
        if (moves.isEmpty()){
            System.out.println("No moves in the Pokedex.");
            return;
        }

        moves.getFirst().printHeader();
        for (MoveModel m : moves){
            displayMoveAttributes(m);
        }
    }

    public String promptSearchKey() {
        System.out.println("\nYou are in the process of searching a move!");
        System.out.println("--------------------------------------------");
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue.
     */
    public void pressAnyKeyPrompt(){
        System.out.print("Displayed all move/s available in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue after searching for a move.
     */
    public void pressAnyKeyPromptSearch(String key){
        System.out.print("Displayed all move/s containing the word '" + key + "' in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }
}
