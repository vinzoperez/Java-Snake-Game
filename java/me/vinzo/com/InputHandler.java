package me.vinzo.com;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/*
    The InputHandler class takes care of all the User Input, with that being said
    It takes the board instance and we set it to a variable. See Board.InitBoard func.

 */

public class InputHandler extends KeyAdapter {
    private Board board;

    public InputHandler(Board boardpass)
    {
        board = boardpass;
    }
    @Override

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT) && (!board.getDirection("right")) && (!board.getOnSameTile())) {
            board.setDirection("left", true);
            board.setDirection("down", false);
            board.setDirection("up", false);
            board.setOnSameTile(true);
        }
        if ((key == KeyEvent.VK_RIGHT) && (!board.getDirection("left")) && (!board.getOnSameTile())) {
            board.setDirection("right", true);
            board.setDirection("down", false);
            board.setDirection("up", false);
            board.setOnSameTile(true);
        }
        if ((key == KeyEvent.VK_UP) && (!board.getDirection("down")) && (!board.getOnSameTile())) {
            board.setDirection("up", true);
            board.setDirection("right", false);
            board.setDirection("left", false);
            board.setOnSameTile(true);
        }
        if ((key == KeyEvent.VK_DOWN) && (!board.getDirection("up")) && (!board.getOnSameTile())) {
            board.setDirection("down", true);
            board.setDirection("right", false);
            board.setDirection("left", false);
            board.setOnSameTile(true);
        }
        if ((key == KeyEvent.VK_SPACE)) {
            if (!board.getInGame()) {
                board.restartGame();
            }
        }
    }
}

