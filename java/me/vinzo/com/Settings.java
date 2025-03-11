package me.vinzo.com;

import java.awt.*;

public class Settings {
    // All Settings
    public int SETTING_APPLE_SPAWN_RATE;
    public int MOVEMENT_SPEED;
    // Unused Settings / Future Settings
    public Color SNAKE_COLOR;
    public Settings()
    {
        // Loads default settings.
        SETTING_APPLE_SPAWN_RATE = 1;
        MOVEMENT_SPEED = 1;
    }

    public int getSETTING_APPLE_SPAWN_RATE()
    {
        return SETTING_APPLE_SPAWN_RATE;
    }

    public void setSETTING_APPLE_SPAWN_RATE(int value)
    {
        SETTING_APPLE_SPAWN_RATE = value;
    }

    public int getMOVEMENT_SPEED()
    {
        return MOVEMENT_SPEED;
    }

    public void setMOVEMENT_SPEED(int value)
    {
        MOVEMENT_SPEED = value;
    }
}
