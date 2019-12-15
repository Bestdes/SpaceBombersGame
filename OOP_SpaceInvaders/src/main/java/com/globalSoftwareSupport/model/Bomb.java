package com.globalSoftwareSupport.model;

import com.globalSoftwareSupport.constants.Constants;
import com.globalSoftwareSupport.images.Image;
import com.globalSoftwareSupport.images.ImageFactory;

import javax.swing.*;

public class Bomb extends Sprite {

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        initialize();
    }

    @Override
    public void move() {

        this.y++;

        //Checking the Constraints (bottom of the canvas)
        if (y >= Constants.BOARD_HEIGHT - Constants.BOMB_HEIGHT) {
            die();
        }
    }

    private void initialize() {
        ImageIcon imageIcon = ImageFactory.createImage(Image.BOMB);
        setImage(imageIcon.getImage());
    }

}
