/**
 * ItemView.java is responsible for all outputs in the item menu.
 */
package Item;

import Move.MoveController;
import Pokemon.Pokemon;
import Pokemon.PokemonController;
import Trainer.TrainerController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class ItemView extends JFrame{
    private PokemonController pokemonController;
    private MoveController moveController;
    private ItemController itemController;
    private TrainerController trainerController;

    private JButton btnShowAllItem, btnSearchItem, btnReturnMenu;
    private JTable table;
    private DefaultTableModel tableModel;

    public ItemView(PokemonController pokemonController, MoveController moveController, ItemController itemController, TrainerController trainerController) {
        this.pokemonController = pokemonController;
        this.moveController = moveController;
        this.itemController = itemController;
        this.trainerController = trainerController;

        setTitle("Item Menu");
        setResizable(false);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        btnShowAllItem = new JButton("Show All Item");
        btnSearchItem = new JButton("Search Item");
        btnReturnMenu = new JButton("Return to Pokedex Main Menu");

        panel.add(btnShowAllItem);
        panel.add(btnSearchItem);
        panel.add(btnReturnMenu);

        add(panel);
        setVisible(true);

        btnShowAllItem.addActionListener(e ->{
            JPanel showAllItemsPanel = createShowAllItemPanel();
            setContentPane(showAllItemsPanel);
            revalidate();
            repaint();
        });

        btnSearchItem.addActionListener(e ->{
            showSearchDialog();
        });

        btnReturnMenu.addActionListener(e ->{
            dispose();
            new Main.MainGUI(this.pokemonController, this.moveController, this.itemController, this.trainerController);
        });
    }

    public Object[][] getItemTableData(ArrayList<Item> items){
        Object[][] data = new Object[items.size()][6];

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            data[i][0] = item.getName();
            data[i][1] = item.getCategory();
            data[i][2] = item.getDescription();
            data[i][3] = item.getEffect();
            data[i][4] = item.getBuyingPrice() == 0.0 ? String.format("₱%.2f", (item.getSellingPrice() * 2)) : String.format("₱%.2f", (item.getBuyingPrice()));
            data[i][5] = String.format("₱%.2f", item.getSellingPrice());
        }
        return data;
    }

    public JScrollPane getJScrollPane(JTable table) {
        table = getJTable(itemController.getAllItems());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,300));

        return scrollPane;
    }

    private void showSearchDialog(){
        String key = JOptionPane.showInputDialog(this, "Enter a search keyword (name, category, or effect):");
        if (key == null || key.trim().isEmpty()) {
            return;
        }

        ArrayList<Item> results = itemController.searchItem(key.trim().toLowerCase());

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No item containing the word '" + key.trim() + "' in the Pokedex.", "Search Failed", JOptionPane.ERROR_MESSAGE);
        } else {
            JTable table = getJTable(results);
            JScrollPane resultScroll = getJScrollPane(table);
            JOptionPane.showMessageDialog(this, resultScroll, "Search Results for '" + key.trim() + "'", JOptionPane.INFORMATION_MESSAGE);
        }

        results.clear();
    }

    private JButton getReturnButton(){
        JButton exitBtn = new JButton("Return to Item Menu");
        exitBtn.addActionListener(e -> {
            dispose();
            new ItemView(this.pokemonController, this.moveController, this.itemController, this.trainerController);
        });

        return exitBtn;
    }

    private JPanel createShowAllItemPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();

        JScrollPane scrollPane = getJScrollPane(itemController.getAllItems());
        panel.add(scrollPane, BorderLayout.CENTER);
        JButton returnBtn = getReturnButton();
        buttonPanel.add(returnBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}