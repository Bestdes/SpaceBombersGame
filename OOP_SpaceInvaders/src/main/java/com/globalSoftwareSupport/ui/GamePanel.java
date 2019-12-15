package com.globalSoftwareSupport.ui;

import com.globalSoftwareSupport.callbacks.GameEventListener;
import com.globalSoftwareSupport.constants.Constants;
import com.globalSoftwareSupport.images.Image;
import com.globalSoftwareSupport.images.ImageFactory;
import com.globalSoftwareSupport.model.Bomb;
import com.globalSoftwareSupport.model.EnemyShip;
import com.globalSoftwareSupport.model.Laser;
import com.globalSoftwareSupport.model.Spaceship;
import com.globalSoftwareSupport.sound.SoundFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel {

    private ImageIcon backgroundImage;
    private SoundFactory soundFactory;
    private Timer timer;
    private Spaceship spaceship;
    private Boolean inGame = true;
    private Laser laser;
    private int direction = -1;
    private java.util.List<EnemyShip> enemyShipList;
    private java.util.List<Bomb> bombs;
    private Random generator;
    private String message;
    private int deadEnemies;
    private int shields = 2;
    private int score;

    public GamePanel() {
        initializeLayout();
        initializeVariables();
        initializeGame();
    }

    private void initializeLayout() {
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        /*
        Sets the preferred size of the JPanel
         */

        setFocusable(true);// Makes program focus on the GamePanel

        addKeyListener(new GameEventListener(this));
    }

    private void initializeVariables() {
        this.soundFactory = new SoundFactory();
        this.backgroundImage = ImageFactory.createImage(Image.BACKGROUND);
        this.timer = new Timer(Constants.GAME_SPEED, new GameLoop(this));
        this.timer.start();
        this.spaceship = new Spaceship();
        this.laser = new Laser();
        this.enemyShipList = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.generator = new Random();
        /*
        10 = after every 10 milliseconds swing -> calls gameLoop
         */
    }

    private void initializeGame() {
        for(int i = 0; i < Constants.ENEMY_SHIP_ROW; i++) {
            for (int j = 0; j < Constants.ENEMY_SHIP_COLUMN; j++) {
                EnemyShip enemyShip = new EnemyShip(Constants.ENEMY_SHIP_INIT_X + 50 * j, Constants.ENEMY_SHIP_INIT_Y + 50 * i);
                this.enemyShipList.add(enemyShip);
            }
        }
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
    }

    private void drawLaser(Graphics g) { // This is how to position the laser on the screen
        // if the laser is dead (outside scope of screen)
        if (!laser.isDead()) {
            g.drawImage(laser.getImage(), laser.getX(),laser.getY(),this);
        }
    }

    private void drawAliens(Graphics g) {
        for (EnemyShip enemyShip : this.enemyShipList) {
            if (enemyShip.isVisible()) {
                g.drawImage(enemyShip.getImage(), enemyShip.getX(), enemyShip.getY(),this);
            }
        }
    }

    private void drawBombs(Graphics g) {
        for (Bomb bomb : this.bombs) {
            if (!bomb.isDead()) {
                g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
            }
        }
    }

    private void drawGameOver(Graphics g) {

        g.drawImage(backgroundImage.getImage(), 0,0,null);

        Font font = new Font("Helvetica",Font.BOLD,50);
        FontMetrics fontMetrics = this.getFontMetrics(font);

        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString(message, (Constants.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2 , Constants.BOARD_HEIGHT / 2 -100);
        g.drawString("Score: " + score, (Constants.BOARD_WIDTH - 250) / 2, Constants.BOARD_HEIGHT / 2);
    }


    private void drawScore(Graphics g) {

        if (!inGame) {
            return;
        }
        Font font = new Font("Helvetica",Font.BOLD,20);
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString("SCORE: " + score, Constants.BOARD_WIDTH - 150, 50);
        g.drawString("Shields: " + shields, 50, 50);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            drawScore(g);
            drawPlayer(g);
            drawLaser(g);
            drawAliens(g);
            drawBombs(g);
        }
        else {
            if (timer.isRunning()) {
                timer.stop();
            }

            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync(); // Makes sure animations are in sync
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0,0, null);

        doDrawing(g);
    }

    public void doOneLoop() {
        update();
        repaint();
    }

    public void update() {

        if (spaceship.isDead()) {
            inGame = false;
            message = Constants.GAME_OVER;

        }

        if (deadEnemies == this.enemyShipList.size()) {
            inGame = false;
            message = Constants.WIN;
        }

        this.spaceship.move();

        if (!laser.isDead()) {

            int shotX = laser.getX();
            int shotY = laser.getY();

            for (EnemyShip alien : this.enemyShipList) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (!alien.isVisible()) {
                    continue;
                }

                // This is our collision detection algorithm
                if (shotX >= (alienX) && shotX <= (alienX + Constants.ENEMY_SHIP_WIDTH) &&
                        shotY >= (alienY) && shotY <= (alienY + Constants.ENEMY_SHIP_HEIGHT)) {
                    alien.setVisible(false);
                    soundFactory.explosion();
                    deadEnemies++;
                    laser.die();
                    score += 20;
                }
            }
            this.laser.move();
        }

        for (EnemyShip enemyShip : this.enemyShipList) {

            //Constraints on the right and left frames of the window
            if (enemyShip.getX() >= Constants.BOARD_WIDTH - 2 * Constants.BORDER_PADDING && direction != -1 || enemyShip.getX() <= Constants.BORDER_PADDING && direction != 1) {
                direction *= -1;

                Iterator<EnemyShip> ufoIterator = enemyShipList.iterator();

                while (ufoIterator.hasNext()) {
                    EnemyShip ufo = ufoIterator.next();
                    ufo.setY(ufo.getY() + Constants.GO_DOWN);
                }
            }
            if (enemyShip.isVisible()) {

                // If the ships reach the bottom of the screen: it is called invasion, thus instant death
                if (enemyShip.getY() > Constants.BOARD_HEIGHT - 100) {
                    spaceship.die();
                }

                enemyShip.move(direction);
            }
        }

        //Bombs are generated by enemy ships (Space Aliens (UFOs))
        for (EnemyShip enemyShip : this.enemyShipList) {
            if (enemyShip.isVisible() && generator.nextDouble() < Constants.BOMB_DROPPING_PROBABILITY) {
                Bomb bomb = new Bomb(enemyShip.getX(), enemyShip.getY());
                this.bombs.add(bomb);
            }
        }

        //This method describes the movement of the Bombs
        for (Bomb bomb : this.bombs) {

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int spaceshipX = spaceship.getX();
            int spaceshipY = spaceship.getY();

            // This describes the collision of a bomb and the user spaceship
            if (!bomb.isDead() && !spaceship.isDead()) {
                if (bombX >= (spaceshipX) && bombX <= (spaceshipX + Constants.SPACESHIP_WIDTH) &&
                        bombY >= (spaceshipY) && bombY <= (spaceshipY + Constants.SPACESHIP_HEIGHT)) {
                    bomb.die();
                    shields--;
                    soundFactory.explosion();

                    if (shields < 0) {
                        spaceship.die();
                    }
                }

                if (!bomb.isDead()) {
                    bomb.move();
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        this.spaceship.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        this.spaceship.keyPressed(e);

        // if the user hits "Space" then a new laser beam is generated
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            int laserX = this.spaceship.getX();
            int laserY = this.spaceship.getY();

            if (inGame && laser.isDead()) {
                soundFactory.laser();
                laser = new Laser(laserX, laserY);
                score -= 5;
            }
        }
    }

}
