package me.vinzo.com;

import java.awt.*;

/*
    Your classic, getters and setters class.

    Get a value using getVARIABLE_NAME;
    Set a value using setVARIABLE_NAME(value);

    Add a setting by using a variable below
     and add it to the initGame() function in Board.Java

    As far as variable naming convention please just make it all caps i guess?
     Unless there is a rule for it somewhere in a big thick java book
     cause technically they aren't constants but,
      "I think it looks cool being all caps" - Vinzo
*/

public class Settings {
    // All Settings
    private int SETTING_APPLE_SPAWN_RATE;
    private double MOVEMENT_SPEED;

    // Unused Settings / Future Settings
    private Color SNAKE_COLOR;
    private String BOARD_COLOR;
    private final String[] BOARD_COLORS_SELECTION = {"GRASS", "OCEAN", "GRAY"};
    public Settings()
    {
        // Loads default settings.
        SETTING_APPLE_SPAWN_RATE = 1;
        MOVEMENT_SPEED = 1;
        BOARD_COLOR = "GRASS";
    }

    public int getSETTING_APPLE_SPAWN_RATE()
    {
        return SETTING_APPLE_SPAWN_RATE;
    }

    public void setSETTING_APPLE_SPAWN_RATE(int value)
    {
        SETTING_APPLE_SPAWN_RATE = value;
    }

    public double getMOVEMENT_SPEED()
    {
        return MOVEMENT_SPEED;
    }

    public void setMOVEMENT_SPEED(double value)
    {
        MOVEMENT_SPEED = value;
    }

    public String getBOARD_COLOR()
    {
        return BOARD_COLOR;
    }

    public void setBOARD_COLOR(String newValue)
    {
        for (String boardColor : BOARD_COLORS_SELECTION)
        {
            if (boardColor.equals(newValue))
            {
                BOARD_COLOR = newValue;
            }
        }
    }
}
