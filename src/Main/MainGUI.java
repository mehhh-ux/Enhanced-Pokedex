package Main;

import Pokemon.PokemonController;
import Pokemon.PokemonView;
import Move.MoveController;
import Move.MoveView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame {
    private JButton btnPokemonMenu, btnMoveMenu, btnExit;
    private PokemonController pController;
    private MoveController mController;

    public MainGUI(PokemonController pController, MoveController mController) {
        this.pController = pController;
        this.mController = mController;

        iniGUI();
    }

    private void iniGUI() {
        setTitle("Enhanced Pokedex - Main Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        btnPokemonMenu = new JButton("Pokemon Menu");
        btnMoveMenu = new JButton("Move Menu");
        btnExit = new JButton("Exit");

        panel.add(btnPokemonMenu);
        panel.add(btnMoveMenu);
        panel.add(btnExit);

        add(panel);
        setVisible(true);

        btnPokemonMenu.addActionListener(e -> {
            new PokemonView(pController);
            this.dispose();
        });

        btnMoveMenu.addActionListener(e -> {
            new MoveView(mController);
            this.dispose();
        });

        btnExit.addActionListener(e -> System.exit(0));
    }
}
