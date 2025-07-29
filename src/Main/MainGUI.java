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
    private FileIO fileIO;

    public MainGUI(PokemonController pController, MoveController mController, ItemController iController) {
        this.pController = pController;
        this.mController = mController;
        this.iController = iController;
        this.fileIO = new FileIO(pController, mController, iController);
        fileIO.loadSaveFile(this);
        iniGUI();
    }

    private void iniGUI() {
        setTitle("Enhanced Pokedex - Main Menu");
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setLayout(new GridLayout(4, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                int confirm = JOptionPane.showConfirmDialog(MainGUI.this,
                        "Do you want to exit and save your data?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        fileIO.saveToFile();
                        System.exit(0);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(MainGUI.this,
                                "Failed to save data: " + exception.getMessage(),
                                "Save Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        });

        btnPokemonMenu = new JButton("Pokemon Menu");
        btnMoveMenu = new JButton("Move Menu");
        btnItemMenu = new JButton("Item Menu");

        btnExit = new JButton("Exit");

        panel.add(btnPokemonMenu);
        panel.add(btnMoveMenu);
        panel.add(btnItemMenu);
        panel.add(btnExit);

        add(panel, BorderLayout.CENTER);
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

        btnExit.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
    }
}
