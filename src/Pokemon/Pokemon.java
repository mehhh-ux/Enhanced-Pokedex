/**
 * Pokemon.java serves as the blueprint for each instance (objects) of a Pokemon.
 * Holds the properties and behaviours of a Pokemon.
 * Two move constructors (one for a single type pokemon and another for a double type).
 */
package Pokemon;

import Move.Move;
import Item.Item;
import java.util.ArrayList;

/**
 * Pokemon.java serves as the blueprint for individual Pokemon objects.
 * It holds the properties and behaviors of a Pokemon, including its stats, type(s),
 * evolution details, held item, and move set.
 *
 * There are two constructors provided:
 * - One for single-type Pokémon
 * - One for dual-type Pokémon
 */

public class Pokemon {
    /**
     * Pokemon Attributes
     */
    private int pokedexNum, baseLvl, evolvesFrom, evolveTo, evolutionLvl;
    private String name, type1, type2;
    private Stats baseStats;
    private ArrayList<Move> moveSet = new ArrayList<>();
    private Item heldItem;

    /**
     * getDefaultMoves() initializes the default moves (Tackle and Defend).
     * Uses initializeMoveList() from Move.java (same purpose).
     * @return an ArrayList of moves.
     */
    private ArrayList<Move> getDefaultMoves() {
        ArrayList<Move> moves = Move.initializeMoveList();
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
    public Pokemon(int pokedexNum, String name, String type1, int baseLvl,
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
    public Pokemon(int pokedexNum, String name, String type1, String type2, int baseLvl,
                   int evolvesFrom, int evolveTo, int evolutionLvl, Stats baseStats) {
        this(pokedexNum, name, type1, baseLvl, evolvesFrom, evolveTo, evolutionLvl, baseStats);
        this.type2 = type2;
    }

    /**
     * Getter
     * @return the pokedex number of the pokemon in the Pokedex.
     */
    public int getPokedexNum() {
        return pokedexNum;
    }

    /**
     * Getter
     * @return the name of the pokemon in the Pokedex.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return the type 1 of the pokemon in the Pokedex.
     */
    public String getType1() {
        return type1;
    }

    /**
     * Getter
     * @return the type 2 of the pokemon in the Pokedex.
     */
    public String getType2() {
        return type2;
    }

    /**
     * Getter
     * @return the base level of the pokemon in the Pokedex.
     */
    public int getBaseLvl() {
        return baseLvl;
    }

    /**
     * Getter
     * @return the what pokedex number does the pokemon evolves from in the Pokedex.
     */
    public int getEvolvesFrom() {
        return evolvesFrom;
    }

    /**
     * Getter
     * @return the what pokedex number does the pokemon evolves to in the Pokedex.
     */
    public int getEvolvesTo() {
        return evolveTo;
    }

    /**
     * Getter
     * @return the what required level so that the pokemon evolves in the Pokedex.
     */
    public int getEvolutionLvl() {
        return evolutionLvl;
    }

    /**
     * Getter
     * @return the basic statistics of the pokemon in the Pokedex.
     */
    public Stats getBaseStats() {
        return baseStats;
    }

    /**
     * Getter
     * @return what moves does the pokemon have in the Pokedex.
     */
    public ArrayList<Move> getMoveSet() {
        return moveSet;
    }

    /**
     * Getter
     * @return what item does the pokemon held in the Pokedex.
     */
    public Item getHeldItem() {
        return heldItem;
    }

    // =================== Setters ===================
    public void setPokedexNum(int pokedexNum) {
        this.pokedexNum = pokedexNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType1(String type) {
        this.type1 = type;
    }

    public void setType2(String type) {
        this.type2 = type;
    }

    public void setBaseLvl(int level) {
        this.baseLvl = level;
    }

    public void setEvolveTo(int pokedexNum) {
        this.evolveTo = pokedexNum;
    }

    public void setEvolutionLvl(int level) {
        this.evolutionLvl = evolutionLvl;
    }

    /**
     * Setter
     * @set what item the pokemon helds
     */
    public void setHeldItem(Item item) {
        this.heldItem = item;
    }

    /**
     * Prints the cry of a specific pokemon
     */
    public void cry() {
        System.out.println(name + " cries!");
    }

    /**
     * Evolves the current Pokemon into a new form.
     * Only replaces name, types, evolution data, and stats.
     * Keeps level, held item, and move set unchanged.
     *
     * @param evolvedForm The evolved form to transform into.
     */
    public void evolveTo(Pokemon evolvedForm) {
        this.name = evolvedForm.getName();
        this.type1 = evolvedForm.getType1();
        this.type2 = evolvedForm.getType2();
        this.evolveTo = evolvedForm.getEvolvesTo();
        this.evolutionLvl = evolvedForm.getEvolutionLvl();
        this.baseStats = evolvedForm.getBaseStats().clone();
        // Keep baseLvl, heldItem, and moveSet unchanged
    }

    /**
     * Allows the user to have a clone or copy of a pokemon object. It prevents the modification of just one address
     * @return a copy/clone of a pokemon object
     */
    public Pokemon copy() {
        Pokemon copy;
        Stats copiedStats = new Stats(this.baseStats.getHp(), this.baseStats.getAtk(), this.baseStats.getDef(), this.baseStats.getSpd());

        if (this.type2 == null){
            copy = new Pokemon( this.pokedexNum, this.name, this.type1, this.baseLvl,
                    this.evolvesFrom, this.evolveTo, this.evolutionLvl, copiedStats);
        } else {
            copy = new Pokemon( this.pokedexNum, this.name, this.type1, this.type2, this.baseLvl,
                    this.evolvesFrom, this.evolveTo, this.evolutionLvl, copiedStats);
        }
        return copy;
    }

    /**
     * Returns the Pokémon's name, or optionally its level too.
     *
     * @return A string representation of the Pokémon.
     */
    @Override
    public String toString() {
        return name; // or name + " (Lv. " + baseLvl + ")" for Pokémon
    }
}