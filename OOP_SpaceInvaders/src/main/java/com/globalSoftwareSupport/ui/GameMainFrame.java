package com.globalSoftwareSupport.ui;

import com.globalSoftwareSupport.constants.Constants;
import com.globalSoftwareSupport.images.Image;
import com.globalSoftwareSupport.images.ImageFactory;

import javax.swing.*;

public class GameMainFrame extends JFrame {

    public GameMainFrame() {
        initializeLayout() ;
    }

    private void initializeLayout() {
        add(new GamePanel());

        setTitle(Constants.TITLE); // Sets the title of the Windrow Frame
        setIconImage(ImageFactory.createImage(Image.SPACESHIP).getImage());

        pack(); // Make sure the JFrame is as large as the JPanel

        setDefaultCloseOperation(EXIT_ON_CLOSE); // Sets the program to end if the frame is closed.
        setLocationRelativeTo(null); // Sets the application to run in the center of the screen
        setResizable(false); // Sets the resize function of the Windrow Frame to false
        setVisible(true); // Set the frame to be visible by user
    }

}
