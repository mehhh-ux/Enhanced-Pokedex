package Pokemon;

import Move.MoveController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.ArrayList;

public class PokemonView extends JFrame {
    private PokemonController controller;

    public PokemonView(PokemonController controller) {
        this.controller = controller;
        setTitle("Pokemon Menu");
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Pokemon", createAddPokemonPanel());
        tabbedPane.addTab("Show All Pokemon", createShowAllPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createAddPokemonPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField tfPokedexNum = new JTextField();
        JTextField tfName = new JTextField();
        JTextField tfType1 = new JTextField();
        JTextField tfType2 = new JTextField();
        JTextField tfBaseLvl = new JTextField();
        JTextField tfEvolvesFrom = new JTextField();
        JTextField tfEvolvesTo = new JTextField();
        JTextField tfEvolutionLvl = new JTextField();
        JTextField tfHP = new JTextField();
        JTextField tfATK = new JTextField();
        JTextField tfDEF = new JTextField();
        JTextField tfSPD = new JTextField();

        panel.add(new JLabel("Pokedex Number:"));
        panel.add(tfPokedexNum);
        panel.add(new JLabel("Name:"));
        panel.add(tfName);
        panel.add(new JLabel("Type 1:"));
        panel.add(tfType1);
        panel.add(new JLabel("Type 2 (optional):"));
        panel.add(tfType2);
        panel.add(new JLabel("Base Level:"));
        panel.add(tfBaseLvl);
        panel.add(new JLabel("Evolves From (Pokedex #):"));
        panel.add(tfEvolvesFrom);
        panel.add(new JLabel("Evolves To (Pokedex #):"));
        panel.add(tfEvolvesTo);
        panel.add(new JLabel("Evolution Level:"));
        panel.add(tfEvolutionLvl);
        panel.add(new JLabel("HP:"));
        panel.add(tfHP);
        panel.add(new JLabel("ATK:"));
        panel.add(tfATK);
        panel.add(new JLabel("DEF:"));
        panel.add(tfDEF);
        panel.add(new JLabel("SPD:"));
        panel.add(tfSPD);

        JButton btnAdd = new JButton("Add Pokemon");
        btnAdd.addActionListener((ActionEvent e) -> {
            try {
                int pokedexNum = Integer.parseInt(tfPokedexNum.getText());
                String name = tfName.getText();
                String type1 = tfType1.getText();
                String type2 = tfType2.getText().isEmpty() ? null : tfType2.getText();
                int baseLvl = Integer.parseInt(tfBaseLvl.getText());
                int evolvesFrom = Integer.parseInt(tfEvolvesFrom.getText());
                int evolvesTo = Integer.parseInt(tfEvolvesTo.getText());
                int evolutionLvl = Integer.parseInt(tfEvolutionLvl.getText());

                int hp = Integer.parseInt(tfHP.getText());
                int atk = Integer.parseInt(tfATK.getText());
                int def = Integer.parseInt(tfDEF.getText());
                int spd = Integer.parseInt(tfSPD.getText());

                Stats stats = new Stats(hp, atk, def, spd);

                if (controller.isDuplicate(pokedexNum, name)) {
                    JOptionPane.showMessageDialog(this, "Duplicate Pokedex number or name found!");
                    return;
                }

                Pokemon newPokemon = (type2 == null) ?
                        new Pokemon(pokedexNum, name, type1, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats) :
                        new Pokemon(pokedexNum, name, type1, type2, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats);

                controller.addPokemon(newPokemon);
                JOptionPane.showMessageDialog(this, "Successfully added " + name + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        panel.add(new JLabel());
        panel.add(btnAdd);

        return panel;
    }

    private JPanel createShowAllPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JButton refreshBtn = new JButton("Refresh");

        refreshBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Pokemon p : controller.getAllPokemon()) {
                sb.append("[" + p.getPokedexNum() + "] " + p.getName())
                        .append(" (" + p.getType1());
                if (p.getType2() != null) {
                    sb.append("/" + p.getType2());
                }
                sb.append(")\n");
            }
            textArea.setText(sb.toString());
        });

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshBtn);

        JButton searchBtn = new JButton("Search Pokemon");
        searchBtn.addActionListener(e -> {
            String key = JOptionPane.showInputDialog(this, "Enter a search keyword (ID, name, or type):");
            if (key == null || key.trim().isEmpty()) {
                return;
            }

            ArrayList<Pokemon> results = controller.searchPokemon(key.trim().toLowerCase());

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No pokemon containing the word '" + key.trim() + "' in the Pokedex.", "Search Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                for (Pokemon p : results) {
                    sb.append("[" + p.getPokedexNum() + "] " + p.getName())
                            .append(" (" + p.getType1());
                    if (p.getType2() != null) {
                        sb.append("/" + p.getType2());
                    }
                    sb.append(")\n");
                }

                JTextArea resultArea = new JTextArea(sb.toString());
                resultArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(resultArea);
                scrollPane.setPreferredSize(new Dimension(800, 600));
                JOptionPane.showMessageDialog(this, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(searchBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> {
            dispose();
            new Main.MainGUI(new PokemonController(), new MoveController());
        });

        buttonPanel.add(exitBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}
//    /**
//     * Header for displaying pokemons and their attributes.
//     */
//    @Override
//    public void printHeader() {
//        System.out.printf(
//                "\n%-15s%-20s%-15s%-15s%-12s%-15s%-15s%-18s%-5s%-5s%-5s%-5s\n",
//                "Pokedex Number", "Name", "Type 1", "Type 2", "Base Level",
//                "Evolves From", "Evolves To", "Evolution Level", "HP", "ATK", "DEF", "SPD");
//        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
//    }
//
//    /**
//     * Displays all attributes of the pokemon.
//     * @param pokemon contains the Pokemon class which the attributes will be printed
//     */
//    @Override
//    public void displayAttributes(Pokemon pokemon) {
//        System.out.printf(
//                "%-15d%-20s%-15s%-15s%-12d%-15s%-15s%-18s%-5d%-5d%-5d%-5d\n",
//                pokemon.getPokedexNum(), pokemon.getName(), pokemon.getType1(), pokemon.getType2() != null ? pokemon.getType2() : "------",
//                pokemon.getBaseLvl(), (pokemon.getEvolvesFrom() != 0 ? String.format("%d", pokemon.getEvolvesFrom()) : "------------"),
//                (pokemon.getEvolvesTo() != 0 ? String.format("%d", pokemon.getEvolvesTo()) : "----------"),
//                (pokemon.getEvolutionLvl() != 0 ? String.format("%d", pokemon.getEvolutionLvl()) : "---------------"),
//                pokemon.getBaseStats().getHp(), pokemon.getBaseStats().getAtk(), pokemon.getBaseStats().getDef(), pokemon.getBaseStats().getSpd());
//    }
//
//    /**
//     * promptPokedexNumber() asks the user for the Pokedex Number of the
//       pokemon they wish to add.
//     * @return the pokedexNum from user input.
//     */
//    public int promptPokedexNumber(){
//        System.out.print("Pokedex Number: ");
//        pokedexNum = scanner.nextInt();
//        scanner.nextLine();
//        return pokedexNum;
//    }
//
//    /**
//     * promptPokemonName() asks the user for the name of the
//       pokemon they wish to add.
//     * @return the name from user input.
//     */
//    public String promptPokemonName(){
//        System.out.print("Pokemon Name: ");
//        name = scanner.nextLine();
//        return name;
//    }
//
//    /**
//     * showDuplicationErrorMessage() prints an error message if one of the duplication
//       checks return true.
//     * @param prompt is a String depending on the error.
//     */
//    public void showDuplicationErrorMessage(String prompt){
//        System.out.println("Invalid " + prompt + ". No duplication of " + prompt + " allowed.");
//    }
//
//    /**
//     * successfulPokemonAddMessage() prints a message if the addition of pokemon is successful
//     * @param name is a String depending on the pokemon name.
//     */
//    public void successfulPokemonAddMessage(String name){
//        System.out.println("Successfully added " + name + "!");
//    }
//
//    /**
//     * promptRemainingPokemonData() asks the user to provide all the other information
//       required to make their desired pokemon.
//     * Once all required information is gathered, this method creates a new pokemon
//       object based off of the user's inputs.
//     * @param pokedexNum is the pokedexNum from the previous prompt.
//     * @param name is the pokemon name from the previous prompt.
//     * @return the newly created pokemon object.
//     */
//    public Pokemon promptRemainingPokemonData(int pokedexNum, String name) {
//        this.pokedexNum = pokedexNum;
//        this.name = name;
//        System.out.print("Type 1: ");
//        type1 = scanner.nextLine();
//        System.out.print("Type 2 (Press enter to skip): ");
//        type2 = scanner.nextLine();
//        type2 = type2.isEmpty() ? null : type2;
//        System.out.print("Base Level: ");
//        baseLvl = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Evolves From (Enter 0 if none): ");
//        evolvesFrom = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Evolves To (Enter 0 if none): ");
//        evolvesTo = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Evolution Level (Enter 0 if none): ");
//        evolutionLvl = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Base HP: ");
//        hp = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Base ATK: ");
//        atk = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Base DEF: ");
//        def = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Base SPD: ");
//        spd = scanner.nextInt();
//        scanner.nextLine();
//
//        stats = new Stats(hp, atk, def, spd);
//
//        return (type2 == null)
//                ? new Pokemon(pokedexNum, name, type1, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats)
//                : new Pokemon(pokedexNum, name, type1, type2, baseLvl, evolvesFrom, evolvesTo, evolutionLvl, stats);
//    }
//
//    @Override
//    public void printAll(ArrayList<Pokemon> pokemons, String key) {
//        if (pokemons.isEmpty()) {
//            System.out.println("No pokemon containing the word '" + key + "' in the Pokedex.");
//            return;
//        }else if(pokemons.isEmpty()) {
//            System.out.println("No pokemon to show in the pokedex.");
//            return;
//        }
//        printHeader();
//        for (Pokemon p : pokemons) {
//            displayAttributes(p);
//        }
//    }
//
//    @Override
//    public String promptForSearchKey() {
//        System.out.println("\nYou are in the process of searching a pokemon!");
//        System.out.println("---------------------------------------------");
//        System.out.print("Enter a search keyword: ");
//        return scanner.nextLine();
//    }
//
//    /**
//     * Asks the user to press enter to continue.
//     */
//    @Override
//    public void pressAnyKeyPrompt(){
//        System.out.print("Displayed all pokemon/s available in the Pokedex.\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//
//    /**
//     * Asks the user to press enter to continue after searching for a pokemon.
//     * @param key is a string that is searched by the user
//     */
//    @Override
//    public void pressAnyKeyPromptForSearch(String key){
//        System.out.print("Displayed all pokemon/s containing the word/number '" + key + "' in the Pokedex.\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//}