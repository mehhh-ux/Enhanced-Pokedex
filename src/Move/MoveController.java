/**
 * MoveController.java is responsible for receiving and giving Move data.
 * Responsible for saving the list of all moves in the Pokedex and adding
 new moves into the list.
 * Able to give a list of moves based on an operation or a method.
 */
package Move;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class MoveController {
    /**
     * A list of moves.
     * The ArrayList moves is used to keep track of all moves in the Pokedex.
     * The ArrayList results is used to hold a list of moves after using an operation
     (for example the search function).
     */
    private ArrayList<Move> moves = Move.initializeMoveList();
    private ArrayList<Move> results = new ArrayList<>();

    /**
     * Adds a move into the moves ArrayList.
     * @param m is the move to be saved.
     * @return a String of the name of the last added move
     */
    public String addMove(Move m) {
        moves.add(m);
        return m.getName();
    }

    /**
     * Getter
     * @return the list of moves in the Pokedex.
     */
    public ArrayList<Move> getAllMoves() {
        return moves;
    }

    /**
     * Search for a move or a list of moves based off from a key.
     * @param key is the input of the user that will be used to search if
    a move has an attribute that matches said key.
     * @return the resulting list of moves.
     */
    public ArrayList<Move> searchMove(String key) {
        ArrayList<Move> results = new ArrayList<>();
        key = key.toLowerCase();
        for (Move m : moves) {
            if (m.getName().toLowerCase().contains(key) ||
                    (m.getClassification() != null && m.getClassification().toLowerCase().contains(key)) ||
                    m.getType1().toLowerCase().contains(key) ||
                    (m.getType2() != null && m.getType2().toLowerCase().contains(key))) {
                results.add(m);
            }
        }
        return results;
    }

    public boolean moveNameIsDup(String name) {
        for (Move m : moves) {
            if (m.getName() == name) {
                return true;
            }
        }

        return false;
    }

    public Move getMoveByName(String name) {
        for (Move m : moves) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }

        return null;
    }

    public void loadFromFile(String filename) throws Exception {
        moves.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean inMoveSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("MOVE:")) {
                    inMoveSection = true;
                    continue;
                }

                if (!inMoveSection) {
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length < 4) {
                    throw new Exception("Invalid move data: " + line);
                }

                String name = parts[0].trim();
                String desc = parts[1].trim();
                String classification = parts[2].trim();
                String type1 = parts[3].trim();
                String type2 = parts[4].trim();
                if (type2.equalsIgnoreCase("null") || type2.isEmpty()) {
                    type2 = null;
                }

                Move m = new Move(name, desc, classification, type1, type2);
                moves.add(m);
            }
        }
    }

    public void saveToFile(String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Move m : moves) {
                StringBuilder sb = new StringBuilder();
                writer.write("MOVE:\n");

                sb.append(m.getName()).append(",");
                sb.append(m.getDescription()).append(",");
                sb.append(m.getClassification()).append(",");
                sb.append(m.getType1()).append(",");
                sb.append(m.getType2() != null ? m.getType2() : "");

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}