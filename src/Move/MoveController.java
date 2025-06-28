/**
 * MoveController.java is responsible for receiving and giving Move data.
 * Responsible for saving the list of all moves in the Pokedex and adding
   new moves into the list.
 * Able to give a list of moves based on an operation or a method.
 */
package Move;

import java.util.ArrayList;

public class MoveController {
    /**
     * A list of moves.
     * The ArrayList moves is used to keep track of all moves in the Pokedex.
     * The ArrayList results is used to hold a list of moves after using an operation
       (for example the search function).
     */
    private ArrayList<MoveModel> moves = MoveModel.initializeMoveList();
    private ArrayList<MoveModel> results = new ArrayList<>();

    /**
     * Adds a move into the moves ArrayList.
     * @param m is the move to be saved.
     * @return a String of the name of the last added move
     */
    public String addMove(MoveModel m) {
        moves.add(m);
        return m.getName();
    }

    /**
     * Getter
     * @return the list of moves in the Pokedex.
     */
    public ArrayList<MoveModel> getAllMoves() {
        return moves;
    }

    /**
     * Search for a move or a list of moves based off from a key.
     * @param key is the input of the user that will be used to search if
       a move has an attribute that matches said key.
     * @return the resulting list of moves.
     */
    public ArrayList<MoveModel> searchMove(String key) {
        key = key.toLowerCase();
        for (MoveModel m : moves) {
            if (m.getName().toLowerCase().contains(key) ||
                    (m.getClassification() != null && m.getClassification().toLowerCase().contains(key)) ||
                    m.getType1().toLowerCase().contains(key) ||
                    (m.getType2() != null && m.getType2().toLowerCase().contains(key))) {
                results.add(m);
            }
        }
        return results;
    }
}
