package Item;

import java.util.ArrayList;

public class EvolutionStone extends Item {
    public EvolutionStone(String name, String description, String effect, double buyingPrice, double sellingPrice){
        super(name, "Evolution Stone", description, effect, buyingPrice, sellingPrice);
    }

    public static ArrayList<Item> getAll(){
        ArrayList<Item> evolutionStones = new ArrayList<>();
        evolutionStones.add(new EvolutionStone("Fire Stone","A stone that radiates heat.", "Evolves Pokémon like Vulpix, Growlithe, Eevee (into Flareon), etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Water Stone", "A stone with a blue, watery appearance.", "Evolves Pokémon like Poliwhirl, Shellder, Eevee (into Vaporeon), etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Thunder Stone", "A stone that sparkles with electricity.", "Evolves Pokémon like Pikachu, Eevee (into Jolteon), Eelektrik, etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Leaf Stone", "A stone with a leaf pattern.", "Evolves Pokémon like Gloom, Weepinbell, Exeggcute, etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Moon Stone", "A stone that glows faintly in the moonlight.", "Evolves Pokémon like Nidorina, Clefairy, Jigglypuff, etc.",
                0.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Sun Stone", "A stone that glows like the sun.", "Evolves Pokémon like Gloom (into Bellossom), Sunkern, Cottonee, etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Shiny Stone", "A stone that sparkles brightly.", "Evolves Pokémon like Togetic, Roselia, Minccino, etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Dusk Stone", "A dark stone that is ominous in appearance.", "Evolves Pokémon like Murkrow, Misdreavus, Doublade, etc.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Dawn Stone", "A stone that sparkles like the morning sky.", "Evolves male Kirlia into Gallade, female Snorunt into Froslass.",
                5000.00, 1500.00));
        evolutionStones.add(new EvolutionStone("Ice Stone", "A stone that is cold to the touch.", "Evolves Pokémon like Alolan Vulpix, Galarian Darumaka, Eevee (into Glaceon)",
                5000.00, 1500.00));
        return evolutionStones;
    }
}