package Move;

import java.util.ArrayList;

public class MoveController {
    private ArrayList<MoveModel> moves = new ArrayList<>();
    private ArrayList<MoveModel> results = new ArrayList<>();

    public void addMove(MoveModel m) {
        moves.add(m);
    }

    public ArrayList<MoveModel> getAllMoves() {
        return moves;
    }

    public ArrayList<MoveModel> searchMove(String key) {
        key = key.toLowerCase();
        for (MoveModel m : moves) {
            if (m.getName().contains(key) ||
                    m.getClassification().toLowerCase().contains(key) ||
                    m.getType1().toLowerCase().contains(key) ||
                    (m.getType2() != null && m.getType2().toLowerCase().contains(key))) {
                results.add(m);
            }
        }
        return results;
    }
}
