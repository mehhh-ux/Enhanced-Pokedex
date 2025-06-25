package Move;

import java.util.ArrayList;
import java.util.Scanner;

public class MoveView {
    private Scanner scanner = new Scanner(System.in);
    private String name, description, classification, type1, type2;

    public MoveModel promptMoveData(){
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

    public void printAllMoves(ArrayList<MoveModel> moves){
        if (moves.isEmpty()){
            System.out.println("No moves in the Pokedex.");
            return;
        }

        moves.getFirst().printHeader();
        for (MoveModel m : moves){
            m.displayMoveAttributes();
        }
    }

    public String promptSearchKey() {
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }
}
