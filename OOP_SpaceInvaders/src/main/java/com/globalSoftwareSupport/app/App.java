package com.globalSoftwareSupport.app;

import com.globalSoftwareSupport.ui.GameMainFrame;

import java.awt.*;

public class App {

    public static void main(String[] args) {

        EventQueue.invokeLater( () -> { // This creates a new thread
            new GameMainFrame();
        });
    }
}
