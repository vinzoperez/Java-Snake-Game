package me.vinzo.com;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/*
    Main Board Class That Holds The Game.
    This class holds everything about the game this is the game functionality.
    Hopefully will organize this code across multiple classes soon... lol

*/

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 20; // Higher the less spaces, Lower more spaces
    private int ALL_DOTS = 900; // Default is 900
    private final int RAND_POS = 29;
    private int DELAY = 200;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private int dots;
    List<int[]> appleLocations = new ArrayList<>();

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean onSameTile = false;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    // Settings
    Settings UserSettings = new Settings();

    // Constructor Class
    public Board() {

        initBoard();
    }

    // Set up The Board For The Game.
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    // Get Images Of Snake And Apples.
    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        ball = iid.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);

        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        apple = iia.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);

        ImageIcon iih = new ImageIcon("src/main/resources/head.png");
        head = iih.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);

        if (ball == null || apple == null || head == null) {
            System.out.println("Error loading images!");
        }
    }

    // Starts The Game dots = Snakes Length, for loop is the starting position, locate apples is how many apples to start with, and add a timer.
    private void initGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        // Load The User Settings

        locateApple(UserSettings.getSETTING_APPLE_SPAWN_RATE());
        UserSettings.setSETTING_APPLE_SPAWN_RATE(5);
        UserSettings.setMOVEMENT_SPEED(.5);
        UserSettings.setBOARD_COLOR("OCEAN");

        DELAY /= UserSettings.getMOVEMENT_SPEED();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    // Override so we can add our own drawing func.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //drawCheckeredBackground(g);

        doDrawing(g);
    }

    private void drawCheckeredBackground(Graphics g)
    {
        if (!inGame) {return;}

        int squareSize = DOT_SIZE;
        String boardColor = UserSettings.getBOARD_COLOR();
        Color colorOne = null;
        Color colorTwo = null;



        if (boardColor.equals("GRASS"))
        {
            colorOne = new Color(160,255,152);
            colorTwo = new Color(119,184,113);
        } else if (boardColor.equals("OCEAN")) {
            colorOne = new Color(127,214,235);
            colorTwo = new Color(55,103,221);
        } else if (boardColor.equals("GRAY"))
        {
            colorOne = new Color(180,180,170);
            colorTwo = new Color(120,120,120);
        }

        for (int i =0; i < B_WIDTH; i += squareSize)
        {
            for (int j=0; j < B_HEIGHT; j += squareSize)
            {
                if ((i / squareSize + j / squareSize) % 2 == 0)
                {

                    g.setColor(colorOne);
                } else {
                    g.setColor(colorTwo);
                }
                g.fillRect(i,j,squareSize,squareSize);
            }
        }

    }

    // For EACH loop gets all the apple locations inside the arraylist, then draws them to the board.
    // Second for loop draws the snake
    private void doDrawing(Graphics g) {
        if (!inGame) { gameOver(g); return; }

        for (int[] appleLocation : appleLocations) {
            g.drawImage(apple, appleLocation[0], appleLocation[1], this);  // Draw each apple
        }

        for (int z = 0; z < dots; z++) {
            if (z == 0) {
                g.drawImage(head, x[z], y[z], this);
            } else {
                g.drawImage(ball, x[z], y[z], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();

    }

    // This is the game over screen when you lose
    private void gameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);

        String playAgainText = "Press Space To Play Again";
        g.setColor(Color.GREEN);
        g.setFont(small);
        g.drawString(playAgainText, (B_WIDTH - metr.stringWidth(playAgainText)) /2, B_HEIGHT - 40);

    }

    // Check the apple location and see if the snake eats one.
    // For loop checks all the apples location in array list
    // Since we eat an apple we spawn in just 1 and not the settings value.
    private void checkApple() {

        for (int i = 0; i < appleLocations.size(); i++) {
            // Get the current apple's position
            int[] appleLocation = appleLocations.get(i);

            // Check if the snake's head overlaps with the apple's position
            if (x[0] == appleLocation[0] && y[0] == appleLocation[1]) {
                dots++;  // Increase the size of the snake

                // Remove the eaten apple from the list
                appleLocations.remove(i);

                // Generate a new apple to replace the eaten one
                locateApple(1);  // Spawn one new apple (or you can pass the desired number of apples to spawn)

                break;  // Exit the loop once we find an apple that the snake eats
            }
        }
    }

    // Movement Handler For Snake
    private void move() {
        // Move the body segments (start from the tail and move each to the previous position)
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        // Move the head by the required amount (taking into account USER_MOVEMENT_SPEED)
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }

        x[0] = (x[0] / DOT_SIZE) * DOT_SIZE;
        y[0] = (y[0] / DOT_SIZE) * DOT_SIZE;

        onSameTile = false;
    }

    // Checks the collision
    // First for loop checks snakes collision if its hitting itself
    // and if statements just check if snake is in bounds of game window.
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
                break;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    // Locate (create) the apple little more complex so ill give it step by step inside
    private void locateApple(int value) {
        boolean validSquare = false;

        for (int j = 0; j < value; j++) {
            validSquare = false;

            while (!validSquare) {
                int[] appleLocation = new int[2]; // [0] for x, [1] for y

                // Randomly generate x and y coordinates for the apple
                for (int i = 0; i < 2; i++) {
                    int r = (int) (Math.random() * RAND_POS);
                    appleLocation[i] = r * DOT_SIZE;  // Ensure apple is placed on a grid multiple of DOT_SIZE
                }

                validSquare = true;

                // Check if the apple is on the snake or in another apple's location
                for (int z = 0; z < dots; z++) {
                    if (x[z] == appleLocation[0] && y[z] == appleLocation[1]) {
                        validSquare = false;
                        break;
                    }
                }

                // Check if the apple is in any existing apple location
                for (int[] loc : appleLocations) {
                    if (loc[0] == appleLocation[0] && loc[1] == appleLocation[1]) {
                        validSquare = false;
                        break;
                    }
                }

                if (validSquare) {
                    appleLocations.add(appleLocation);
                }
            }
        }
    }

    private void restartGame() {
        inGame = true;
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;

        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * 10; y[i] = 50;
        }
        locateApple(UserSettings.getSETTING_APPLE_SPAWN_RATE());
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    // Listens to User Inputs and uses a switch statement for optimization!
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection) && (!onSameTile)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                onSameTile = true;
            }
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection) && (!onSameTile)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                onSameTile = true;
            }
            if ((key == KeyEvent.VK_UP) && (!downDirection) && (!onSameTile)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                onSameTile = true;
            }
            if ((key == KeyEvent.VK_DOWN) && (!upDirection) && (!onSameTile)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                onSameTile = true;
            }
            if ((key == KeyEvent.VK_SPACE)) {
                restartGame();

            }
        }
    }
}
