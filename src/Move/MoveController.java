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

    public void addMove(MoveModel m) {
        moves.add(m);
        return m.getName();
    }

    public ArrayList<MoveModel> getAllMoves() {
        return moves;
    }

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
