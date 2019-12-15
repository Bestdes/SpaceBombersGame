package com.globalSoftwareSupport.model;

import com.globalSoftwareSupport.constants.Constants;
import com.globalSoftwareSupport.images.Image;
import com.globalSoftwareSupport.images.ImageFactory;

import javax.swing.*;

public class Laser extends Sprite {

    public Laser() {
    }

    public Laser(int x, int y) {
        this.x = x;
        this.y = y;
        initialize();
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.LASER);
        setImage(imageIcon.getImage());

        setX(x + Constants.SPACESHIP_WIDTH/2); // This is because we want the laser to come from the middle of the spaceship
        setY(y);
    }

    @Override
    public void move() {
        this.y -= Constants.LASER_HORIZONTAL_TRANSLATION;
        // y must be decremented (y value decreases) to make the laser appear to move to top of screen
        if (this.y < 0) { // y < 0 means the laser is further than the top of the screen
            this.die();
        }
    }
}
