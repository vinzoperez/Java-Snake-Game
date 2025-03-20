package me.vinzo.com;

import javax.swing.*;
import java.awt.*;

/*
    This class basically creates the window of the game.
 */

public class Snake extends JFrame {

    public Snake() {
        initUI();
    }

    private void initUI()
    {
        Board board = new Board();
        add(board);
        board.initGame();
        //JButton playButton = new JButton("Play");
        //playButton.addActionListener(e -> startGame(playButton, board));

        //add(playButton);

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void startGame(JButton playButton, Board board)
    {
        playButton.setVisible(false);
        board.initGame();

    }

    private void settingsMenu(JButton settingsButton)
    {

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });

    }
}
