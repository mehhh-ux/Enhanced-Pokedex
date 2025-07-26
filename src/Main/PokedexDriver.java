/**
 * PokedexDriver.java is the driver app and controls the main loop of the program.
 * It is responsible for managing each menu in the Pokedex based on user input.
 * It is also responsible for the termination of the program.
 */
package Main;

import Item.ItemController;
import Move.MoveController;
import Pokemon.PokemonController;

public class PokedexDriver {
    public static void main(String[] args) {
        PokemonController pController = new PokemonController();
        MoveController mController = new MoveController();
        ItemController iController = new ItemController();

        new MainGUI(pController, mController, iController);
    }
}