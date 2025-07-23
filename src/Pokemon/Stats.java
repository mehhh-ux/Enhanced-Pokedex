package Pokemon;

/**
 * Stats.java serves as the blueprint for each instance (objects) of a BaseStats/Stats on Pokemon.Java.
 * Acts as the base stats of a pokemon.
 * Holds each base stat (or simplicity).
 */
public class Stats {
    /**
     * Stats Attributes
     */
    private int hp, atk, def, spd;

    /**
     * Stats Constructor
     * @param hp
     * @param atk
     * @param def
     * @param spd
     */
    public Stats(int hp, int atk, int def, int spd) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
    }

    /**
     * Getters
     */
    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getSpd() {
        return spd;
    }
}