/**
 * Move.java serves as the blueprint for each instance (objects) of a Move.
 * Holds the properties and behaviours of a Move.
 * Two move constructors (one for a single type move and another for a double type).
 */
package Move;

import java.util.ArrayList;

public class Move {
    /**
     * Move Attributes
     */
    private String name, description, classification, type1, type2;

    /**
     * Move Constructor (Single Type)
     * @param name
     * @param description
     * @param classification
     * @param type1
     */
    public Move(String name, String description, String classification, String type1) {
        this.name = name;
        this.description = description;
        this.classification = classification;
        this.type1 = type1;
    }

    /**
     * Move Constructor (Double Types)
     * @param name
     * @param description
     * @param classification
     * @param type1
     * @param type2
     */
    public Move(String name, String description, String classification, String type1, String type2) {
        this(name, description, classification, type1);
        this.type2 = type2;
    }
    /**
     * Getter
     * @return the name of the move in the Pokedex.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return the description of the move in the Pokedex.
     */
    public String getDescription() { return description; }

    /**
     * Getter
     * @return the classification of the move in the Pokedex.
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Getter
     * @return the type 1 of the move in the Pokedex.
     */
    public String getType1() {
        return type1;
    }

    /**
     * Getter
     * @return the type 2 of the move in the Pokedex.
     */
    public String getType2() {
        return type2;
    }


    /**
     * Initializes the instances of ethe default moves (Tackle and Defend).
     * @return the list of moves.
     */
    public static ArrayList<Move> initializeMoveList(){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move("Tackle", "The user charges and slams into the opponent with its body.", "TM", "Normal"));
        moves.add(new Move("Defend", "Enters a defensive stance.", "TM", "Normal"));
        return moves;
    }
}