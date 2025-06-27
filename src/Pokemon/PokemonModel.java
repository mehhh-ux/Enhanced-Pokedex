/**
 * PokemonModel.java serves as the blueprint for each instance (objects) of a Pokemon.
 * Holds the properties and behaviours of a Pokemon.
 * Two move constructors (one for a single type pokemon and another for a double type).
 */
package Pokemon;

import Move.MoveModel;
import Item.ItemModel;
import java.util.ArrayList;

public class PokemonModel {
    /**
     * Pokemon Attributes
     */
    private int pokedexNum, baseLvl, evolvesFrom, evolveTo, evolutionLvl;
    private String name, type1, type2;
    private Stats baseStats;
    private ArrayList<MoveModel> moveSet = new ArrayList<>();
    private ItemModel heldItem;

    /**
     * getDefaultMoves() initializes the default moves (Tackle and Defend).
     * Uses initializeMoveList() from MoveModel.java (same purpose).
     * @return an ArrayList of moves.
     */
    private ArrayList<MoveModel> getDefaultMoves() {
        ArrayList<MoveModel> moves = new ArrayList<>();
        MoveModel.initializeMoveList();
        return moves;
    }

    /**
     * Pokemon Constructor (Single Type)
     * @param pokedexNum
     * @param name
     * @param type1
     * @param baseLvl
     * @param evolvesFrom
     * @param evolveTo
     * @param evolutionLvl
     * @param baseStats
     */
    public PokemonModel(int pokedexNum, String name, String type1, int baseLvl,
                   int evolvesFrom, int evolveTo, int evolutionLvl, Stats baseStats) {
        this.pokedexNum = pokedexNum;
        this.name = name;
        this.type1 = type1;
        this.baseLvl = baseLvl;
        this.evolvesFrom = evolvesFrom;
        this.evolveTo = evolveTo;
        this.evolutionLvl = evolutionLvl;
        this.baseStats = baseStats;
        this.moveSet = getDefaultMoves();
    }

    /**
     * Pokemon Constructor (Double Type)
     * @param pokedexNum
     * @param name
     * @param type1
     * @param type2
     * @param baseLvl
     * @param evolvesFrom
     * @param evolveTo
     * @param evolutionLvl
     * @param baseStats
     */
    public PokemonModel(int pokedexNum, String name, String type1, String type2, int baseLvl,
                   int evolvesFrom, int evolveTo, int evolutionLvl, Stats baseStats) {
        this(pokedexNum, name, type1, baseLvl, evolvesFrom, evolveTo, evolutionLvl, baseStats);
        this.moveSet = getDefaultMoves();
        this.type2 = type2;
    }

    /**
     * Getters
     */
    public int getPokedexNum() {
        return pokedexNum;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public int getBaseLvl() {
        return baseLvl;
    }

    public int getEvolvesFrom() {
        return evolvesFrom;
    }

    public int getEvolvesTo() {
        return evolveTo;
    }

    public int getEvolutionLvl() {
        return evolutionLvl;
    }

    public Stats getBaseStats() {
        return baseStats;
    }

    public ArrayList<MoveModel> getMoveSet() {
        return moveSet;
    }

    public ItemModel getHeldItem() {
        return heldItem;
    }

    /**
     * Prints a message that says that the pokemon cried.
     */
    public void cry() {
        System.out.println(name + " cries!");
    }
}