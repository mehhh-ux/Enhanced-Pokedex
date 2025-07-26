package Main;

import Item.ItemController;
import Item.ItemView;
import Pokemon.PokemonController;
import Pokemon.PokemonView;
import Move.MoveController;
import Move.MoveView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame {
    private JButton btnPokemonMenu, btnMoveMenu, btnItemMenu, btnExit;
    private PokemonController pController;
    private MoveController mController;
    private ItemController iController;

    public MainGUI(PokemonController pController, MoveController mController, ItemController iController) {
        this.pController = pController;
        this.mController = mController;
        this.iController = iController;

        iniGUI();
    }

    private void iniGUI() {
        setTitle("Enhanced Pokedex - Main Menu");
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        btnPokemonMenu = new JButton("Pokemon Menu");
        btnMoveMenu = new JButton("Move Menu");
        btnItemMenu = new JButton("Item Menu");

        btnExit = new JButton("Exit");

        panel.add(btnPokemonMenu);
        panel.add(btnMoveMenu);
        panel.add(btnItemMenu);
        panel.add(btnExit);

        add(panel);
        setVisible(true);

        btnPokemonMenu.addActionListener(e -> {
            new PokemonView(pController, mController, iController);
            this.dispose();
        });

        btnMoveMenu.addActionListener(e -> {
            new MoveView(pController, mController, iController);
            this.dispose();
        });

        btnItemMenu.addActionListener(e -> {
            new ItemView(pController, mController, iController);
            this.dispose();
        });



        btnExit.addActionListener(e -> System.exit(0));
    }
}
