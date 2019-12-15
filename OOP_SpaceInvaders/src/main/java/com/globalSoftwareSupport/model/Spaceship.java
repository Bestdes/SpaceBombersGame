package com.globalSoftwareSupport.model;

import com.globalSoftwareSupport.constants.Constants;
import com.globalSoftwareSupport.images.Image;
import com.globalSoftwareSupport.images.ImageFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Spaceship extends Sprite {


    public Spaceship() {
        initialize();
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.SPACESHIP);
        setImage(imageIcon.getImage());

        int start_x = Constants.BOARD_WIDTH/2 - Constants.SPACESHIP_WIDTH/2;
        int start_y = Constants.BOARD_HEIGHT - 100;

        setX(start_x);
        setY(start_y);
    }

    @Override
    public void move() {
        x += dx;

        // Prevents user spaceship from going beyond the canvas (Left side)
        if (x < Constants.SPACESHIP_WIDTH) {
            x = Constants.SPACESHIP_WIDTH;
        }

        if (x > Constants.BOARD_WIDTH - 2 * Constants.SPACESHIP_WIDTH) {
            x = Constants.BOARD_WIDTH - 2 * Constants.SPACESHIP_WIDTH;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        // controls for standard W-A-S-D key movement
        if (key == KeyEvent.VK_A) {
            dx = 0;
        }
        if (key == KeyEvent.VK_D) {
            dx = 0;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -3;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = +3;
        }

        // controls for standard W-A-S-D key movement
        if (key == KeyEvent.VK_A) {
            dx = -3;
        }
        if (key == KeyEvent.VK_D) {
            dx = 3;
        }
    }

}
