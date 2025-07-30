/**
 * PokedexDriver.java is the driver app and controls the main loop of the program.
 * It is responsible for managing each menu in the Pokedex based on user input.
 * It is also responsible for the termination of the program.
 */
package Main;

import Item.ItemController;
import Move.MoveController;
import Pokemon.PokemonController;
import Trainer.TrainerController;

/**
 * PokedexDriver.java is the entry point of the application.
 * It initializes all major controllers (Move, Item, Pokemon, Trainer)
 * and passes them to the MainGUI to start the graphical user interface.
 * It also handles basic error reporting via exception printing.
 */
public class PokedexDriver {
    public static void main(String[] args) {
        try {
            MoveController mController = new MoveController();
            ItemController iController = new ItemController();
            PokemonController pController = new PokemonController(mController, iController);
            TrainerController tController = new TrainerController(pController, mController, iController);

            new MainGUI(pController, mController, iController, tController);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}