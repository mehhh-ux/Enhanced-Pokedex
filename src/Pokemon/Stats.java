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

    // =================== Setters ===================
    public void setHp(int hp) { this.hp = hp; }
    public void setAtk(int atk) { this.atk = atk; }
    public void setDef(int def) { this.def = def; }
    public void setSpd(int spd) { this.spd = spd; }

    /**
     * Increases all base stats (HP, ATK, DEF, SPD) of this Pokémon
     * by the given percentage.
     *
     * @param percent A decimal representing the percentage increase
     *                (e.g., 0.10 for +10%, 0.25 for +25%)
     */
    public void increaseByPercentage(double percent) {
        this.hp = (int) Math.round(this.hp * (1 + percent));
        this.atk = (int) Math.round(this.atk * (1 + percent));
        this.def = (int) Math.round(this.def * (1 + percent));
        this.spd = (int) Math.round(this.spd * (1 + percent));
    }

    /**
     * Returns a string representation of the stats in the format:
     * "HP: [hp], ATK: [atk], DEF: [def], SPD: [spd]".
     *
     * @return A formatted string representing the current stat values.
     */
    @Override
    public String toString() {
        return String.format("HP: %d, ATK: %d, DEF: %d, SPD: %d", hp, atk, def, spd);
    }

    /**
     * Creates and returns a deep copy of this Stats object.
     *
     * @return A new Stats instance with the same values for HP, ATK, DEF, and SPD.
     */
    @Override
    public Stats clone() {
        return new Stats(hp, atk, def, spd);
    }
}