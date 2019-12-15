package com.globalSoftwareSupport.constants;

public class Constants {

    private Constants() {
    }

    public static final String TITLE = "Space Invasion";

    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 600;

    // Speed of the Application
    public static final int GAME_SPEED = 8;

    // Game message on win or lose (Game state messages)
    public static final String GAME_OVER = "GAME OVER";
    public static final String WIN = "YOU WIN!";

    public static final int SPACESHIP_WIDTH = 34;
    public static final int SPACESHIP_HEIGHT = 28;

    // Speed of the Laser
    public static final int LASER_HORIZONTAL_TRANSLATION = 10;

    // UFO related constants
    public static final int ENEMY_SHIP_HEIGHT = 24;
    public static final int ENEMY_SHIP_WIDTH = 32;
    public static final int ENEMY_SHIP_INIT_X = 200;
    public static final int ENEMY_SHIP_INIT_Y = 100;
    public static final int ENEMY_SHIP_ROW = 4;
    public static final int ENEMY_SHIP_COLUMN = 8;

    // This code prevents the enemy ships from leaving the visible screen (50px padding
    public static final int BORDER_PADDING = 50;

    //Every time the Enemy Ships hit one side of the frame they will increase 30 pixels
    public static final int GO_DOWN = 30;

    //This determines the Bomb constants
    public static final int BOMB_HEIGHT= 6;
    public static final double BOMB_DROPPING_PROBABILITY = .0015;


    // images for the objects
    public static final String UFO_IMAGE_URL = "src/images/newufo72.png";
    public static final String LASER_IMAGE_URL = "src/images/laser72.png";
    public static final String BOMB_IMAGE_URL = "src/images/newbomb72.png";
    public static final String BACKGROUND_IMAGE_URL = "src/images/background2.jpg";
    public static final String SPACESHIP_IMAGE_URL = "src/images/spaceship77.png";

}
