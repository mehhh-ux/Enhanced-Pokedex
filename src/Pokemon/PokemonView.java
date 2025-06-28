package Pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class PokemonView {
    private Scanner scanner = new Scanner(System.in);
    private Stats stats;
    private String name, type1, type2;
    private int pokedexNum, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, hp, atk, def, spd;

    /**
     * Header for displaying pokemons and their attributes.
     */
    public void printHeader() {
        System.out.printf(
                "\n%-15s%-20s%-15s%-15s%-12s%-15s%-15s%-18s%-5s%-5s%-5s%-5s\n",
                "Pokedex Number", "Name", "Type 1", "Type 2", "Base Level",
                "Evolves From", "Evolves To", "Evolution Level", "HP", "ATK", "DEF", "SPD");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Displays all attributes of the pokemon.
     */
    public void displayPokemonAttributes(PokemonModel pokemon) {
        System.out.printf(
                "%-15d%-20s%-15s%-15s%-12d%-15s%-15s%-18s%-5d%-5d%-5d%-5d\n",
                pokemon.getPokedexNum(), pokemon.getName(), pokemon.getType1(), pokemon.getType2() != null ? pokemon.getType2() : "------",
                pokemon.getBaseLvl(), (pokemon.getEvolvesFrom() != 0 ? String.format("%d", pokemon.getEvolvesFrom()) : "------------"),
                (pokemon.getEvolvesTo() != 0 ? String.format("%d", pokemon.getEvolvesTo()) : "----------"),
                (pokemon.getEvolutionLvl() != 0 ? String.format("%d", pokemon.getEvolutionLvl()) : "---------------"),
                pokemon.getBaseStats().getHp(), pokemon.getBaseStats().getAtk(), pokemon.getBaseStats().getDef(), pokemon.getBaseStats().getSpd());
    }

    /**
     * promptPokedexNumber() asks the user for the Pokedex Number of the
       pokemon they wish to add.
     * @return the pokedexNum from user input.
     */
    public int promptPokedexNumber(){
        System.out.print("Pokedex Number: ");
        pokedexNum = scanner.nextInt();
        scanner.nextLine();
        return pokedexNum;
    }

    /**
     * promptPokemonName() asks the user for the name of the
       pokemon they wish to add.
     * @return the name from user input.
     */
    public String promptPokemonName(){
        System.out.print("Pokedex Name: ");
        name = scanner.nextLine();
        return name;
    }

    /**
     * showDuplicationErrorMessage() prints an error message if one of the duplication
       checks return true.
     * @param prompt is a String depending on the error.
     */
    public void showDuplicationErrorMessage(String prompt){
        System.out.println("Invalid " + prompt + ". No duplication of " + prompt + " allowed.");
    }

    /**
     * successfulPokemonAddMessage() prints a message if the addition of pokemon is successful
     * @param name is a String depending on the pokemon name.
     */
    public void successfulPokemonAddMessage(String name){
        System.out.println("Successfully added " + name + "!");
    }

    /**
     * promptRemainingPokemonData() asks the user to provide all the other information
       required to make their desired pokemon.
     * Once all required information is gathered, this method creates a new pokemon
       object based off of the user's inputs.
     * @param pokedexNum is the pokedexNum from the previous prompt.
     * @param name is the pokemon name from the previous prompt.
     * @return the newly created pokemon object.
     */
    public PokemonModel promptRemainingPokemonData(int pokedexNum, String name) {
        this.pokedexNum = pokedexNum;
        this.name = name;
        System.out.print("Type 1: ");
        type1 = scanner.nextLine();
        System.out.print("Type 2 (Press enter to skip): ");
        type2 = scanner.nextLine();
        type2 = type2.isEmpty() ? null : type2;
        System.out.print("Base Level: ");
        baseLvl = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Evolves From (Enter 0 if none): ");
        evolvesFrom = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Evolves To (Enter 0 if none): ");
        evolvesTo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Evolution Level (Enter 0 if none): ");
        evolutionLvl = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base HP: ");
        hp = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base ATK: ");
        atk = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base DEF: ");
        def = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Base SPD: ");
        spd = scanner.nextInt();
        scanner.nextLine();

        stats = new Stats(hp, atk, def, spd);

        return (type2 == null)
                ? new PokemonModel(pokedexNum, name, type1, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats)
                : new PokemonModel(pokedexNum, name, type1, type2, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats);
    }

    public void printAllPokemons(ArrayList<PokemonModel> pokemons) {
        if (pokemons.isEmpty()) {
            System.out.println("No pokemon containing the word '" + key + "' in the Pokedex.");
            return;
        }

        printHeader();
        for (PokemonModel p : pokemons) {
            displayPokemonAttributes(p);
        }
    }

    public String promptSearchKey() {
        System.out.println("\nYou are in the process of searching a pokemon!");
        System.out.println("---------------------------------------------");
        System.out.print("Enter a search keyword: ");
        return scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue.
     */
    public void pressAnyKeyPrompt(){
        System.out.print("Displayed all pokemon/s available in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Asks the user to press enter to continue after searching for a pokemon.
     * @param key is a string that is searched by the user
     */
    public void pressAnyKeyPromptSearch(String key){
        System.out.print("Displayed all pokemon/s containing the word '" + key + "' in the Pokedex.\nPress Enter to continue...");
        scanner.nextLine();
    }
}