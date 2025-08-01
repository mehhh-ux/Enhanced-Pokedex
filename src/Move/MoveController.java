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

    /**
     * Checks if a Move name already exists.
     *
     * @param name the name to check
     * @return true if duplicate, otherwise return false
     */
    public boolean moveNameIsDup(String name) {
        for (Move m : moves) {
            if (m.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a Move by name.
     *
     * @param name the name of the Move
     * @return the MOve object, or null if not found
     */
    public Move getMoveByName(String name) {
        for (Move m : moves) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }

        return null;
    }

    /**
     * Loads Move data from a file using a buffered reader.
     *
     * @param filename the name of the data file
     * @throws Exception if file format is invalid or an I/O error occurs
     */
    public void loadFromFile(String filename) throws Exception {
        moves.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length < 5) {
                    continue;
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

    /**
     * Saves Pokémon data to a file.
     *
     * @param filename the output file path
     * @throws Exception if writing fails
     */
    public void saveToFile(String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Move m : moves) {
                StringBuilder sb = new StringBuilder();

                sb.append(m.getName()).append("|");
                sb.append(m.getDescription()).append("|");
                sb.append(m.getClassification()).append("|");
                sb.append(m.getType1()).append("|");
                sb.append(m.getType2() != null ? m.getType2() : "");

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}