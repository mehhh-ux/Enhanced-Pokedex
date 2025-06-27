/**
 * MoveModel.java serves as the blueprint for each instance (objects) of a Move.
 * Holds the properties and behaviours of a Move.
 * Two move constructors (one for a single type move and another for a double type).
 */
package Move;

import java.util.ArrayList;

public class MoveModel {
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
    public MoveModel(String name, String description, String classification, String type1) {
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
    public MoveModel(String name, String description, String classification, String type1, String type2) {
        this(name, description, classification, type1);
        this.type2 = type2;
    }
    /**
     * Getters
     */
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public String getClassification() {
        return classification;
    }
    public String getType1() {
        return type1;
    }
    public String getType2() {
        return type2;
    }


    /**
     * Initializes the instances of ethe default moves (Tackle and Defend).
     * @return the list of moves.
     */
    public static ArrayList<MoveModel> initializeMoveList(){
        ArrayList<MoveModel> moves = new ArrayList<>();
        moves.add(new MoveModel("Tackle", "The user charges and slams into the opponent with its body.", null, "Normal"));
        moves.add(new MoveModel("Defend", "Enters a defensive stance.", null, "Normal"));
        return moves;
    }
}
