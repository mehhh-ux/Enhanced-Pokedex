package Pokemon;

import Move.MoveModel;
import Item.ItemModel;
import java.util.ArrayList;

public class PokemonModel {
    //Attributes
    private int pokedexNum;
    private String name;
    private String type1;
    private String type2;
    private int baseLvl;
    private int evolvesFrom;
    private int evolveTo;
    private int evolutionLvl;
    private Stats baseStats;
    private ArrayList<MoveModel> moveSet = new ArrayList<>();
    private ItemModel heldItem;

    private ArrayList<MoveModel> getDefaultMoves() {
        ArrayList<MoveModel> moves = new ArrayList<>();
        MoveModel.initializeMoveList();
        return moves;
    }

    //Constructors
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

    public PokemonModel(int pokedexNum, String name, String type1, String type2, int baseLvl,
                   int evolvesFrom, int evolveTo, int evolutionLvl, Stats baseStats) {
        this(pokedexNum, name, type1, baseLvl, evolvesFrom, evolveTo, evolutionLvl, baseStats);
        this.moveSet = getDefaultMoves();
        this.type2 = type2;
    }

    //Getters
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

    public void cry() {
        System.out.println(name + " cries!");
    }
}